import java.util.ArrayList;
import java.util.UUID;
import java.lang.Math;

/**
 * Stroke class - collection of points between pen/ mouse down and the corresponding pen/ mouse up event
 * - list of points and Unique id
 * @author manoj
 *
 */
public class Stroke 
{
	ArrayList<Point> dataPoints; // Collection of points
	String id =""; // Unique ID for the object
        
        double start_cos = 0; //cosine of the starting angle
        double start_sin = 0; //sine of the starting angle
        Vector start_vector; //starting vector

        double end_cos = 0; //cosine of the starting angle
        double end_sin = 0; //sine of the starting angle        
        Vector end_vector;//ending vector


        double x_max; //maximum x-value of the stroke
        double x_min; //minimum x-value of the stroke
        double y_max; //maximum y-value of the stroke
        double y_min; //minimum y-value of the stroke

        double avg_slope = 0; //avg slope of all segments in stroke


	/**
	 * Constructor to create stroke with unique ID
	 * @param dataPoints - collection of points 
	 * @param id - unique ID for the stroke
	 */
	Stroke(ArrayList<Point> dataPoints, String id)
	{
		this.dataPoints = dataPoints;
		this.id = id;
	}
	
	/**
	 * Constructor to create stroke 
	 * generates a unique ID for the stroke object
	 * @param dataPoints - collection of points
	 */
	Stroke(ArrayList<Point> dataPoints)
	{
		this.dataPoints = dataPoints;
		this.id = UUID.randomUUID().toString();
	}
	
	/**
	 * Constructor to create stroke
	 */
	Stroke(){	
		dataPoints = new ArrayList<Point>();
		this.id = UUID.randomUUID().toString();
	}
        
        void calculateStartingAngles(){
            if(dataPoints.size() > 2){
                double x0 = dataPoints.get(0).x_coor;
                double x2 = dataPoints.get(2).x_coor;
                double y0 = dataPoints.get(0).y_coor;
                double y2 = dataPoints.get(2).y_coor;

                start_cos = (x2-x0)/Math.sqrt(Math.pow(y2-y0,2)+ Math.pow(x2-x0,2));
                start_sin = (y2-y0)/Math.sqrt(Math.pow(y2-y0,2)+ Math.pow(x2-x0,2));

                x0 = dataPoints.get(dataPoints.size()-3).x_coor;
                x2 = dataPoints.get(dataPoints.size()-1).x_coor;
                y0 = dataPoints.get(dataPoints.size()-3).y_coor;
                y2 = dataPoints.get(dataPoints.size()-1).y_coor;

                end_cos = (x2-x0)/Math.sqrt(Math.pow(y2-y0,2)+ Math.pow(x2-x0,2));
                end_sin = (y2-y0)/Math.sqrt(Math.pow(y2-y0,2)+ Math.pow(x2-x0,2));
            
            }

            else{
                start_cos = 0;
                start_sin = 0;
            }

            
        }

        double get_XMin(){
            x_min = dataPoints.get(0).x_coor;
            for(int i=0; i<dataPoints.size(); i++){
                if(dataPoints.get(i).x_coor < x_min){
                    x_min = dataPoints.get(i).x_coor;
                }
                else
                    continue;
            }
            return x_min;
        }

        double get_XMax(){
            x_max = dataPoints.get(0).x_coor;
            for(int i=0; i<dataPoints.size(); i++){
                if(dataPoints.get(i).x_coor > x_max){
                    x_max = dataPoints.get(i).x_coor;
                }
                else
                    continue;
            }
            return x_max;
        }

        double get_YMin(){
            y_min = dataPoints.get(0).y_coor;
            for(int i=0; i<dataPoints.size(); i++){
                if(dataPoints.get(i).y_coor < y_min){
                    y_min = dataPoints.get(i).y_coor;
                }
                else
                    continue;
            }
            return y_min;
        }

        double get_YMax(){
            y_max = dataPoints.get(0).y_coor;
            for(int i=0; i<dataPoints.size(); i++){
                if(dataPoints.get(i).y_coor > y_max){
                    y_max = dataPoints.get(i).y_coor;
                }
                else
                    continue;
            }
            return y_max;
        }

        double diagonalOfStrokeBoundingBox(){
            double ymax = get_YMax();
            double ymin = get_YMin();
            double xmax = get_XMax();
            double xmin = get_XMin();
            double val1 = Math.pow((ymax-ymin), 2.0);
            double val2 = Math.pow((xmax-xmin), 2.0);

            double diagonal = Math.sqrt(val1+val2);

            return diagonal;
    }

        double getEuclideanDistance(){
            double firstx = dataPoints.get(0).x_coor;
            double lastx = dataPoints.get(dataPoints.size()-1).x_coor;
            double firsty = dataPoints.get(0).y_coor;
            double lasty = dataPoints.get(dataPoints.size()-1).y_coor;
            double val1 = Math.pow((lastx-firstx), 2.0);
            double val2 = Math.pow((lasty-firsty), 2.0);
            double distance = Math.sqrt(val1+val2);

            return distance;
        }

        double getStrokeLength(){
            double length = 0;
            for(int i=1; i<dataPoints.size(); i++){
                double firstx = dataPoints.get(i-1).x_coor;
                double lastx = dataPoints.get(i).x_coor;
                double firsty = dataPoints.get(i-1).y_coor;
                double lasty = dataPoints.get(i).y_coor;
                double val1 = Math.pow((lastx-firstx), 2.0);
                double val2 = Math.pow((lasty-firsty), 2.0);
                length += Math.sqrt(val1+val2);
            }
            return length;
        }

        void calcAvgSlope(){
            if(dataPoints.size() > 2){
                double sum = 0;
                double points = 0;
                for(int c = 1; c<dataPoints.size(); c++){
                    double x0 = dataPoints.get(c-1).x_coor;
                    double y0 = dataPoints.get(c-1).y_coor;
                    double x1 = dataPoints.get(c).x_coor;
                    double y1 = dataPoints.get(c).y_coor;
                    double slope = (y1-y0)/(x1-x0);
                    //this prevents averaging with infinity
                    if(slope>0 && slope <1000){
                        sum += slope;      
                        points++;
                    }     
                }

                avg_slope = sum/points; 
                //System.out.println(avg_slope);
            }
           
        }

        void setVectors(){
             start_vector = new Vector(dataPoints.get(0), dataPoints.get(1));
             end_vector = new Vector(dataPoints.get(dataPoints.size()-2), dataPoints.get(dataPoints.size()-1));

        }
	
	/**
	 * Function to convert the Stroke object to corresponding XML 
	 * @return - XML representation of the stroke object
	 */
	public String toXML()
	{
		String xml="";
		
		for(int i = 0 ; i < dataPoints.size(); i++)
		{
			xml += dataPoints.get(i).toXML();
		}
		
		xml += "<stroke id = \"" + id + "\">";
		
		for(int i = 0 ; i < dataPoints.size(); i++)
		{
			xml += " <arg type = \"point\">" + dataPoints.get(i).id + "</arg>";		
		}
		
		xml += "</stroke>";
		
		return xml;
	}	
	
}
