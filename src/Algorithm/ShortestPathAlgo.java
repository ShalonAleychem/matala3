package Algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import GIS.GIS_element;
import GIS.GIS_layer;
import GIS.GIS_project;
import GIS.GISelement;
import GIS.GISlayer;
import GIS.GISproject;
import GIS.Game;
import GIS.Meta_data;
import GIS.Packman;
import GIS.Path;
import GIS.fruit;
import GIS.metaData;
import Geom.Geom_element;
import Geom.Point3D;

public class ShortestPathAlgo extends ArrayList<Path> {
	
	static ArrayList<Path> Paths = new ArrayList<Path>();
	ArrayList<fruit> tempFruits;
	ArrayList<Packman> tempPackmans;
	
	public ShortestPathAlgo(Game game) {
		
		tempFruits = new ArrayList<fruit>(game.get_fruits());
		tempPackmans = new ArrayList<Packman>(game.get_packmans());
		
		while(!tempFruits.isEmpty()) {
			
			Iterator<Packman> itP= game.get_packmans().iterator();
			Iterator<Packman> temp_itP= tempPackmans.iterator();

			double minimal= 1000009999;
			Packman optional_Pac= itP.next();
			
			while(temp_itP.hasNext())
			{
				Iterator<fruit> itF= tempFruits.iterator();	
				
				Packman p= temp_itP.next();
				double minTimeToFruit= 1000000000;
				
				while(itF.hasNext()) {
					fruit runner= itF.next();
					double time= p.getcoord().distance2D(runner.getcoord());
					time= time/p.get_velocity();
					if(time<= minTimeToFruit) {
						minTimeToFruit= time;
						p.setTimeToFruit(time);
						p.setCloseFruit(runner);
					}
				}	
				
				if(p.getTimeToFruit()<= minimal) {
					minimal= p.getTimeToFruit();
					optional_Pac= p;
				}
			}
			
			double time= optional_Pac.getTimeToFruit();
			optional_Pac.setcoord(optional_Pac.getCloseFruit().getcoord());
			optional_Pac.setTime(time);
			optional_Pac.getPath().add(optional_Pac.getCloseFruit().getcoord());
			optional_Pac.getPath().addPoint_duration_time(time);
			int i= tempFruits.indexOf(optional_Pac.getCloseFruit());
			tempFruits.remove(i);

			optional_Pac.addScore(optional_Pac.getCloseFruit().get_weigth());
		}
		
		Iterator<Packman> temp= tempPackmans.iterator();
		while(temp.hasNext()) {
			Packman p= temp.next();
			Paths.add(p.getPath());
		}
		addScores(game, tempPackmans);
	}
	
	public ArrayList<Path> getSolution(){
		return Paths;
	}

	public void addScores(Game game, ArrayList<Packman> tempPackmans) {
		
		for(int i=0; i<tempPackmans.size(); i++) {
			game.get_packmans().get(i).setScore(tempPackmans.get(i).getScore());
			
		}
	}
	

}
