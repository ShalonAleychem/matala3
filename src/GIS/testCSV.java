package GIS;

import java.util.ArrayList;
import java.util.Iterator;

import Algorithm.ShortestPathAlgo;
import File_format.MultiCSV;
import File_format.Path2Kml;

public class testCSV {

	public static void main(String[] args) {
		
		Game game= new Game("C:\\Users\\Owner\\Desktop\\game_1543684662657.csv");
		
		ArrayList<Path> try_this= new ShortestPathAlgo(game).getSolution();

		
		
		Path2Kml p= new Path2Kml("kmlFile", try_this);

		Iterator<Path> it= try_this.iterator();
		while(it.hasNext()) {
			Path path = it.next();
			for(int i=1; i<path.size();i++) {
				System.out.println(path.get(i));
			}
			System.out.println();

		}	
	}
}
