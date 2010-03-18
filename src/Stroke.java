import java.util.ArrayList;
import java.util.UUID;

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
