package File_format;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Algorithm.ShortestPathAlgo;
import Coords.MyCoords;
import GIS.Game;
import GIS.Path;
import Geom.Point3D;

public class Path2Kml {
	
	public Path2Kml(String out ,ArrayList<Path> paths){
		
		PrintWriter writer = null;
		
		ArrayList<String> color= new ArrayList<>();
		String [] tag = {"Red" , "Yellow", "Blue","Green","Purple","Orange", "Brown", "Pink"};
		
		try {
			writer = new PrintWriter(new File(out+".kml"));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		writer.println("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		writer.println("<Document>");
		writer.println("<Folder>");
		int i=0 ,j=1 , pacmanIndex = 0;
		writer.println("<name>Paths</name>");

		for (Path current : paths) {
			writer.println("<name>Paths["+(i++)+"]</name>");
			writer.println("<Folder>");
			writer.println("<Style id=\"getcolor\">");
			writer.println("<LineStyle>");
			String s =get_color();
			color.add(s);
			writer.println("<color>"+s+"</color>");
			writer.println("<width>4</width>");
			writer.println( "</LineStyle>");
			writer.println("</Style>");
			writer.println("<Placemark>");
			writer.println("<name>Absolute Extruded</name>");
			writer.println("<styleUrl>#getcolor</styleUrl>");
			writer.println("<LineString>");
			writer.println("<coordinates>");

			for(int pl = 0; pl < current.size();pl++) {
				Point3D point = current.get(pl);
				writer.println(""+point.y()+","+point.x()+","+point.z());

			}
			writer.println("</coordinates>");
			writer.println("</LineString>");
			writer.println("</Placemark>");
			writer.println("</Folder>");
		}
		i=0;
		int c=0;
		int po=1;
		for (Path current : paths) {
			writer.println("<Folder>");
			writer.println("<name>Path["+(i++)+"]</name>");
			String 	C2L =color.get(c++);
			double[] times = current.get_duration();
			for(int placemark = 0; placemark < current.size(); placemark++) {
				Point3D point = current.get(placemark);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				double sum =times[placemark];
				Date date = new Date((long) (sum * 1000000) + System.currentTimeMillis());
				System.out.println(date);
				System.out.println(times[placemark]);
				writer.println("<Placemark>");
				if (placemark==0) {
					writer.println("<name>Pack["+(po++)+"]</name>");

				}
				else {
				writer.println("<name>Fruit["+(j++)+"]</name>");
				}
				writer.println("<description><![CDATA[location: <b>"+point.y() +","+point.x()+"</b><br/>Date: <b>"+ df.format(date)+ "</b>]]></description>");

				writer.println("<Point>");
				writer.println("<coordinates>"+point.y() +","+point.x()+"</coordinates>");
				writer.println("</Point>");
				writer.println("<styleUrl>#"+C2L+"</styleUrl>");
				if (placemark!=0) {
				writer.println("<TimeStamp>");
				writer.println("<when>"+df.format(date).toString().replace(" ", "T")+"</when>");
				writer.println("</TimeStamp>");}
				writer.println("</Placemark>");
				
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				}
			writer.println("</Folder>");
			}
		for(int m = 0 ; m<8 ; m++) {
			String link =linkcolor(tag[m]);
			writer.println("<Style id=\""+tag[m]+"\">");
			writer.println("<IconStyle>");
			writer.println("<Icon>");
			writer.println("<href>"+link+"</href>");
			writer.println( "</Icon>");
			writer.println("</IconStyle>");
			writer.println("</Style>");
			}
		writer.println();
		writer.println("</Folder>");
		writer.println("</Document>");
		writer.println("</kml>");
		writer.close();
	}
	
	public  String get_color(){
		double f = Math.random();
		f=f*7;
		String [] color= {"ff0000ff","ff00ffff","ffff0000","ff00ff00","ff800080","ff0080ff","ff336699","ffff00ff"};
		return color[(int)f];
	}
	
	public  String linkcolor (String color) {
		int i =0;
		String linkincolor="";
		String Red="ff0000ff";
		String Yellow  ="ff00ffff";;
		String Blue ="ffff0000";
		String Green ="ff00ff00";
		String Purple ="ff800080";
		String Orange ="ff0080ff";
		String Brown ="ff336699";
		String Pink ="ffff00ff";
		if(color.equals(Red))i=0;
		if(color.equals(Yellow))i=1;
		if(color.equals(Blue))i=2;
		if(color.equals(Green))i=3;
		if(color.equals(Purple))i=4;
		if(color.equals(Orange))i=5;
		if(color.equals(Brown))i=6;
		if(color.equals(Pink))i=7;
		switch (i) {
		case 0: linkincolor= "http://maps.google.com/mapfiles/kml/paddle/red-circle.png";
		case 1: linkincolor="http://maps.google.com/mapfiles/kml/paddle/ylw-circle.png";
		case 2: linkincolor="http://maps.google.com/mapfiles/kml/paddle/blu-circle.png";
		case 3: linkincolor="http://maps.google.com/mapfiles/kml/paddle/grn-circle.png";
		case 4: linkincolor="http://maps.google.com/mapfiles/kml/paddle/purple-square.png";
		case 5: linkincolor="http://maps.google.com/mapfiles/kml/paddle/wht-blank.png";
		case 6: linkincolor="http://maps.google.com/mapfiles/kml/paddle/wht-blank.png";
		case 7: linkincolor="http://maps.google.com/mapfiles/kml/paddle/pink-circle.png";
		}
		return linkincolor;
	}
	
	

	
}