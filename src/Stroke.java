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
