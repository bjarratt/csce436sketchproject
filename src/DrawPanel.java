import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import jpen.*;

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
        

        double jitter = -1;
        int numSegmentsToClamp = -1;
        boolean strobe = false;
        boolean colorBand = true;

        int colorsIndex = 0;
        Color[] colors = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.MAGENTA};
   
        Persona Stan = new Persona(jitter,numSegmentsToClamp);

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
                Stroke modifiedStroke = Stan.Morph(s.strokeList.get(s.strokeList.size()-1));
                modifiedStroke.calculateStartingAngles();
                modifiedStroke.setVectors();
                System.out.println("diagonal of bounding box: " +
                        modifiedStroke.diagonalOfStrokeBoundingBox());
                System.out.println("euclidean distance between endpoints: " +
                        modifiedStroke.getEuclideanDistance());
                System.out.println("total stroke length: " +
                        modifiedStroke.getStrokeLength());
                s.strokeList.set(s.strokeList.size()-1, modifiedStroke);
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
		
		// Creating a point
		Point pnt = new Point(x,y,t,1);
		
		// Adding the point to the current stroke
		Stroke currStroke = s.strokeList.get(s.strokeList.size()-1);		
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
					if(strobe && !colorBand){
                                            if(colorsIndex > colors.length-1)
                                                colorsIndex = 0;
                                            Color col = colors[colorsIndex];
                                            g2d.setColor(col);
                                            colorsIndex++;
                                        }
                                        else if(!strobe && colorBand){
                                            int bandHeight = 600/colors.length;
                                            int band = y/bandHeight;
                                            g2d.setColor(colors[band]);
                                        }
					g2d.drawLine(x,y,x1,y1);

				}
			}
                        colorsIndex=0;
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
