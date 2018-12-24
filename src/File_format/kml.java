package File_format;

import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import GIS.GIS_element;
import GIS.GIS_layer;
import Geom.Point3D;
import java.util.Iterator;

public class kml {

	/**
	 * write the start of a kml file
	 * @param pw
	 */
	public static void startWrite(PrintWriter pw) {

		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");	

		pw.print("<Document><Style id=");
		pw.print("\"red\"");
		pw.print("><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style><Style id=" );
		pw.print("\"yellow\"");		
		pw.print("><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style><Style id=" );
		pw.print("\"green\"");		
		pw.print("><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>" );
		pw.print("<Folder>");
		pw.print("<name>FOLDER</name>");
	}

	/**
	 * add a layer to the kml file with color
	 * @param pw
	 * @param layer
	 * @param color
	 */
	public static void addLayer(PrintWriter pw, GIS_layer layer, String color) {

		Iterator<GIS_element> it= layer.iterator();		
		while(it.hasNext())   // while the layer has more elements
		{
			GIS_element element= it.next();  
			addElement(pw, element,color); 
		}
	}

	/**
	 * add an element to the kml file
	 * @param pw
	 * @param element
	 * @param color
	 */
	public static void addElement(PrintWriter pw, GIS_element element,String color) {

		if(!(element.getGeom() instanceof Point3D)) return;
		Point3D point = (Point3D)element.getGeom();

		pw.println("<Placemark>");
//		pw.println("<name>"+ element.getName()+ "</name>");

		Date date = new Date(element.getData().getUTC());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		pw.println("<description><![CDATA[Timestamp: <b>"+ element.getData().getUTC()+ "</b><br/>Date: <b>"+ df.format(date)+ "</b>]]></description>");
		
		pw.println("<TimeStamp><when>"+ df.format(date)+"</when></TimeStamp>");

		pw.println("<styleUrl>#"+color+"</styleUrl>");
		pw.println("<Point>");
		pw.println("<coordinates>"+ point.y() +","+ point.x()+ ","+ point.z()+ "</coordinates>");
		pw.println("</Point>");
		pw.println("</Placemark>");

	}

	/**
	 * write the finish of the kml file
	 * @param pw
	 */
	public static void closeKml(PrintWriter pw) {

		pw.println("</Folder></Document></kml>");
		pw.close();
	}

}
