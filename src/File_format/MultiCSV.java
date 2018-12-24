package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GISproject;


public class MultiCSV {
	// File object 

	/**
	 *this function creats a project and then send to function to create one kml file to all the project
	 * @param name
	 * @return the 
	 */
	public static GIS_project dir2kml(String name) 
	{ 
		File maindir = new File(name); 
		ArrayList <File> csv_files= new ArrayList<File>();	
		ArrayList<File> result = findCSVs(maindir, csv_files);

		GIS_project project= new GISproject();

		Iterator<File> it= result.iterator();	

		while(it.hasNext()) {
			File file= it.next();
			GIS_layer layer= cvs2layer.csv2layer(file.getPath());
			project.add(layer);
		}

		project2kml(project);

		return project;
	}

/**
 * this function gets a path to directory and Recursively passes all folders and files in it to find the csv files 
 * @param maindir - a path to the directory
 * @param csv_files - arraylist to store csv files
 * @return
 */
	public static ArrayList<File> findCSVs(File maindir, ArrayList<File> csv_files)
	{
		File[] filesInDir = maindir.listFiles();
		for(int i=0; i<filesInDir.length ; i++)
		{
			if(filesInDir[i].isFile() && filesInDir[i].getName().endsWith(".csv"))
			{
				if(isRightFormat(filesInDir[i])) {
					csv_files.add(filesInDir[i]);
				}
				if(filesInDir[i].isDirectory())
				{
					findCSVs(filesInDir[i], csv_files);
				}

			}
		}
		return csv_files;
	}

/**
 * this function gets a csv file and checks whether the format is appropriate
 * @param file 
 * @return true if the format appropriate
 */
	public static boolean isRightFormat(File file) {
		String line = "";
		String[] csvFormat = {"MAC","SSID","AuthMode","FirstSeen","Channel","RSSI","CurrentLatitude","CurrentLongitude","AltitudeMeters","AccuracyMeters","Type"};

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			line = br.readLine();
			String [] format = line.split(",");

			return Arrays.equals(format, csvFormat);

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

/**
 * this function creats kml file ( and the layer to kml.
 * @param project
 */
	public static void project2kml(GIS_project project) {

		PrintWriter pw = null;
		try 
		{
			pw = new PrintWriter(new File("project.kml"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}

		kml.startWrite(pw);//

		Iterator<GIS_layer> it= project.iterator();//creates layers from each csv file	
		while(it.hasNext()) {
			GIS_layer layer= it.next();
			kml.addLayer(pw, layer,getRandomColor()); //each layer sends to create KML file
		}

		kml.closeKml(pw);
	}

	/**
	 * The function randomly selects the color from the three colors set to KML
	 * 	
	 * @return String with the color that chosen
	 */
	public static String getRandomColor() {
		
		int choise = new Random().nextInt(3); 
		String color = "red";
		switch(choise) {
		case 0 : color = "red";
		break;
		case 1 : color = "green";
		break;
		case 2 : color = "yellow";
		break;
		}
		
		return color;
	}
}
