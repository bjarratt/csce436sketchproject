import java.util.UUID;

/**
 * Class to store data from "point" tag
 * - contains x ,y , time 
 * - add variables to include pressure, tilt ,...
 * @author manoj
 *
 */
public class Point
{
	double x_coor=0; // x co - ordinate
	double y_coor=0; // y co - ordinate
	double time =0; // time
    double pressure = 0; //pressure
    double speed = 0;
    
	String id = ""; // Unique id


        Point(){

        }
	
	/**
	 * Point constructor - useful while reading the xml file
	 * @param x - x co ordinate
	 * @param y - y co ordinate
	 * @param t - time component
	 * @param id - unique ID
	 */
	Point(double x, double y, double t, double p, String id)
	{
		x_coor=x;
		y_coor=y;
		time=t;
        pressure=p;
        speed=0;
		this.id = id;
	}
	
	/**
	 * Point Constructor that generates Unique ID for the object 
	 * @param x - x co ordinate
	 * @param y - y co ordinate
	 * @param t - time component
	 */
	Point(double x, double y, double t, double p, double speed)
	{
		x_coor=x;
		y_coor=y;
		time=t;
        pressure = p;
        this.speed = speed;
		this.id = UUID.randomUUID().toString();
	}

	
	
	/**
	 * Function to convert the Point object to XML string.
	 * @return - xml representation of the object
	 */
	public String toXML()
	{
		String xml = "<point ";
		xml += "id = \""+ id + "\"";
		xml += " x = \"" + x_coor + "\"";
		xml += " y = \"" + y_coor + "\"";
		xml += " t = \"" + time + "\"";
		xml += "/>";
		
		return xml;
	}
	
}