import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * Simple sketch panel - to draw / record user sketch 
 * - only x , y , time data recorded
 * - Pressure , Tilt support can be added 
 * @author manoj
 *
 */
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;	
	
	double totalTime;
	
	Sketch s; // sketch object to record the strokes drawn

        double jitter = 0;
        int numSegmentsToClamp = -1;
        boolean rainbow = false;
        boolean discoPen = false;
        boolean colorBand = false;
        boolean doubleStroke = false;
        boolean mirrorStroke = false;
        boolean speedSize = false;

        int colorsIndex = 0;
        Color[] colors = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA};
   
        Persona Stan = new Persona(jitter,numSegmentsToClamp,doubleStroke,mirrorStroke);

	DrawPanel()
	{		
		s = new Sketch();	
		
		addMouseListener(this);
		addMouseMotionListener(this);		
	}	

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) 
	{
		
		//try{d.persist();}catch(Exception m){;}
		repaint();
	}
	public void mouseMoved(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) 
	{	
		
	}
	
	public void mousePressed(MouseEvent e)
	{		
		// Mouse down is equivalent to the Pen down event
		// - creating a new Stroke object for the pen down
		ArrayList<Point> dataPoints = new ArrayList<Point>();
		Stroke newStroke = new Stroke(dataPoints);
		s.strokeList.add(newStroke);			
	}

	
	public void mouseReleased(MouseEvent e) 
        {
            if(s.strokeList.size() >0){
                Stan.windowWidth = this.getWidth();
                Stroke modifiedStroke = new Stroke();
                modifiedStroke = s.strokeList.get(s.strokeList.size()-1);
                modifiedStroke = Stan.Morph(modifiedStroke);
                modifiedStroke.calculateStartingAngles();
                modifiedStroke.setVectors();

                System.out.println("diagonal of bounding box: " +
                        modifiedStroke.diagonalOfStrokeBoundingBox());
                System.out.println("euclidean distance between endpoints: " +
                        modifiedStroke.getEuclideanDistance());
                System.out.println("total stroke length: " +
                        modifiedStroke.getStrokeLength());
                //s.strokeList.set(s.strokeList.size()-1, modifiedStroke);
                System.out.println("average slope: " +
                        modifiedStroke.getAvgSlope());
                System.out.println();
                
                s.strokeList.set(s.strokeList.size()-1, modifiedStroke);

                if(doubleStroke){
                	Stroke newStroke = new Stroke();
                    for(int c = 0;c<modifiedStroke.dataPoints.size();c++){
                        double newX = modifiedStroke.dataPoints.get(c).x_coor + 10;

                        Point dataPoint = new Point(newX,modifiedStroke.dataPoints.get(c).y_coor,0,0,modifiedStroke.dataPoints.get(c).speed);
                        newStroke.dataPoints.add(dataPoint);
                    }
                    s.strokeList.add(newStroke);
                }
                else if (mirrorStroke)
                {
                    Stroke newStroke = new Stroke();

                    for(int c = 0;c<modifiedStroke.dataPoints.size();c++){
                        double newX = Stan.windowWidth - modifiedStroke.dataPoints.get(c).x_coor;

                        Point dataPoint = new Point(newX,modifiedStroke.dataPoints.get(c).y_coor,0,0,modifiedStroke.dataPoints.get(c).speed);
                        newStroke.dataPoints.add(dataPoint);
                    }
                    s.strokeList.add(newStroke);
                }
            }

            this.repaint();
	}

	/**
	 * Collecting all the points between pen down and pen up - which is Mouse drag event
	 */
	public void mouseDragged(MouseEvent e) 
	{		
		// Recording the current mouse point and time
		int x=e.getX();
		int y=e.getY();
		double t = System.currentTimeMillis();		
		
		// Adding the point to the current stroke
        
		Stroke currStroke = s.strokeList.get(s.strokeList.size()-1);

        Point pnt;
        if(speedSize){
            if (currStroke.dataPoints.size() > 0){
                Point prevPoint = (currStroke.dataPoints.get(currStroke.dataPoints.size()-1));
                double positionDelta = Math.sqrt((x-prevPoint.x_coor)*(x-prevPoint.x_coor) +
                                                 (y-prevPoint.y_coor)*(y-prevPoint.y_coor));
                double speed;
                if ( (t - prevPoint.time) == 0 ) {
                    speed = prevPoint.speed;
                }
                else {
                    speed = positionDelta / (t - prevPoint.time);
                }

                pnt = new Point(x,y,t,1,speed);
            }
            else {
            pnt = new Point(x,y,t,1,.5);
        }
        }
        else {
            pnt = new Point(x,y,t,1,.5);
        }
        // Creating a point

		currStroke.dataPoints.add(pnt);
		this.repaint();		
	}
	
	/**
	 * Function to draw the strokes on the panel
	 * @param g2d
	 */

	public void drawRawDiagram(Graphics2D g2d)
	{
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		    		
		ArrayList<Stroke> sl = s.strokeList;
		
		// Setting the color of the sketch
               
                g2d.setColor(Color.BLACK);
		
		try
		{
			// Rendering each stroke on the panel
			for(int j=0;j<sl.size();j++)
			{
				
				Stroke s = sl.get(j);
				ArrayList <Point> dataPoints = s.dataPoints;
				for(int k=0;k<dataPoints.size()-1;k++)
				{
					Point p = dataPoints.get(k);
					Point p1 = dataPoints.get(k+1);
					
					int x = (int) p.x_coor;
					int y = (int) p.y_coor;
					int x1 = (int) p1.x_coor;
					int y1 = (int) p1.y_coor;
					if((discoPen || rainbow)&& !colorBand){
                                            
                                            if(colorsIndex > colors.length-1)
                                                colorsIndex = 0;
                                            Color col = colors[colorsIndex];
                                            g2d.setColor(col);
                                            colorsIndex++;
                                        }
                                        else if(!(discoPen || rainbow) && colorBand){
                                            int bandHeight = this.getHeight()/colors.length;
                                            int band = y/bandHeight;
                                            if(band>=colors.length)
                                                band = colors.length-1;
                                            else if(band<0){
                                                band = 0;
                                            }
                                            g2d.setColor(colors[band]);
                                        }
                    // Catches NaN speeds
                    if (!(p1.speed > 0)){
                        if ((p.speed > 0)){
                            p1.speed = p.speed;
                        }
                        else{
                            p1.speed = 0;
                        }
                    }
                    g2d.setStroke(new BasicStroke((float)(p1.speed * 5)));
					g2d.drawLine(x,y,x1,y1);

				}
			}
                        if(rainbow)
                            colorsIndex=0;//take this line out to discoPen while drawing.
		}catch(NullPointerException e){}	
	}
	
	
	public void paint(Graphics g1)
	{
		super.paint(g1);
		Graphics2D g2d = (Graphics2D) g1;
		g2d.setColor(new Color(0,0,0));
		
		//Redrawing the strokes on the panel.
		drawRawDiagram(g2d);
	}
}
