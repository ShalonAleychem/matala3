package File_format;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GISelement;
import GIS.GISlayer;
import GIS.Meta_data;
import GIS.metaData;
import Geom.Point3D;

public class cvs2layer {

	/**
	 * gets a csv file name and create a layer of elements
	 * @param file
	 * @return layer , hash set of elements
	 */
    public static GIS_layer csv2layer(String file) {
    	
        String[] lines = CSVReader.Reader(file);  // read the file
        GIS_layer Layer = new GISlayer();    // create a new layer
        String cvsSplitBy = ",";

        // loop until the array is finished
        for (int i = 2; i < lines.length; i++) {
        	
        	String[] Info = lines[i].split(cvsSplitBy);  // split the string array
        	
        	String name= Info[1];   // gets the name 
            String time = Info[3];  // gets the time

            double lat = Double.parseDouble(Info[6]);
            double lon = Double.parseDouble(Info[7]);
            double alt = Double.parseDouble(Info[8]);
            Point3D geom = new Point3D(lat, lon, alt);  // create the point
            
            // create the time and the utc
            DateTimeFormatter parseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(time, parseFormatter);
            long UTC = dateTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
            
            Meta_data data = new metaData(); // create the data 
            
            GIS_element element = new GISelement(geom, data, name);   // create a new element
            Layer.add(element); // add to the layer
        }
        
        return Layer;
    }
	
}
