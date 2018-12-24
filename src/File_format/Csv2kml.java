package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import GIS.GIS_layer;

public class Csv2kml {
	
	/**
	 * gets a csv file name and convert to kml file
	 * @param file
	 */
	public static void csv2kml(String file)
	{
		GIS_layer layer= cvs2layer.csv2layer(file);  // create a new layer
		
    	String name= file.substring(0, file.length()-4);
		PrintWriter pw = null;

		try 
		{
			pw = new PrintWriter(new File(name+".kml"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}

		kml.startWrite(pw);		
		String defultColor= "red";
		kml.addLayer(pw, layer,defultColor);
		kml.closeKml(pw);
		
	}
}
