import java.io.File;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Sketch class - List of Strokes 
 * - unique ID for the object
 * @author manoj
 *
 */
public class Sketch 
{
	ArrayList<Stroke> strokeList; // List of strokes in a sketch
	 
	String id = ""; // Unique ID for the object
	
	/**
	 * Constructor to create Sketch object with a list of strokes
	 * - generates unique ID
	 * @param strokeList
	 */
	public Sketch(ArrayList<Stroke> strokeList) 
	{
		this.strokeList = strokeList;
		this.id = UUID.randomUUID().toString();
	}
	
	/**
	 * Constructor to create empty Sketch 
	 * - generates Unique ID
	 */
	public Sketch()
	{
		strokeList = new ArrayList<Stroke>();
		this.id = UUID.randomUUID().toString();
	}
	
	
	/**
	 * Function to load a sketch xml file into Sketch object
	 * @param file - File handle to the XML file
	 */
	 public void loadSketch (File file)
	    { 
	    	
	    	strokeList.clear();
	    	HashMap<String,Point> pointTable = new HashMap<String,Point>();
	    	
	    	try {
	            	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();           		 	        		 	           
	            	Document doc = docBuilder.parse (file);
	            	
	            	
	            	NodeList pList = doc.getElementsByTagName("point");
	    	   		for(int s=0; s<pList.getLength() ; s++)
	            	  {
	            		
	            		Node pnt = pList.item(s);
	     		            	
	     		        NamedNodeMap a = pnt.getAttributes();
	     		        
	     		        String id = a.item(0).getNodeValue();
	     		        double t= Double.parseDouble(a.item(1).getNodeValue());  
	     		        double x= Double.parseDouble(a.item(2).getNodeValue());
	     		        double y= Double.parseDouble(a.item(3).getNodeValue());
	     		        
	     		        pointTable.put(id, new Point(x,y,t,1,1));
	     		            	
	                  }         
	                        
	                       
	            	
	            	NodeList strokeListnode = doc.getElementsByTagName("stroke");
	    	   		for(int s=0; s<strokeListnode.getLength() ; s++)
	            	  {
	            		
	            		Node stroke = strokeListnode.item(s);
	            		
	                    if(stroke.getNodeType() == Node.ELEMENT_NODE)
	                    {
	                     		            	
	 		            	NamedNodeMap strokeAttrib = stroke.getAttributes();
	                        Element strokeElement = (Element)stroke;
	                        NodeList pointList = strokeElement.getElementsByTagName("arg");
	                        
	                        ArrayList<Point> dataPoints = new ArrayList<Point>();
	                        
	                        for(int i=0;i<pointList.getLength();i++)
	                        {
	                        	Node pnt = pointList.item(i);
	                        	String id = pnt.getTextContent();
	     		            	dataPoints.add(pointTable.get(id));          	
	                        }
	                        
	                        
	                        
	                                                
	                        Stroke currStroke = new Stroke();
	                        
	                        currStroke.dataPoints = dataPoints;
	                        
	                        strokeList.add(currStroke);	                        
	                        	
	                     }                
	            	  }     
	            	
	         	
	    		}
	    	
	        catch (SAXParseException err) 
	        	{
	        		System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
	        		System.out.println(" " + err.getMessage ());

	        	}
	        catch (SAXException e)
	        	{
	        		Exception x = e.getException ();
	        		((x == null) ? e : x).printStackTrace ();

	        	}
	        
	        catch (Throwable t) 
	        	{
	        		t.printStackTrace ();
	        	} 
	        	
	    }
	 
	 /**
	  * Function to convert the Sketch object to XML string
	  * @return - XML representation of the Sketch object
	  */
	 public String toXML()
	 {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xml += "<sketch id = \"" + this.id + "\">";
		
		for(int i =0; i < strokeList.size(); i++)
		{
			xml += strokeList.get(i).toXML();
		}
		
		xml += "</sketch>";
		return xml;
	 }
}
