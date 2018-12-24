package GIS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import Geom.Point3D;

public class Path extends ArrayList<Point3D> {

	ArrayList<Point3D> path;
	int i=0;
	double[] duration;
	

	public Path() {
		path = new ArrayList<Point3D>();
		duration = new double[120];
	}

	public double length() {
		double sum=0;
		Iterator<Point3D> it= path.iterator();	
		Point3D p= it.next(); 

		while(it.hasNext())   // while the layer has more elements
		{
			Point3D p2= it.next();
			sum+= p.distance2D(p2);
			p= p2;
		}
		return sum;
	}

	public ArrayList<Point3D> getPath() {
		return path;
	}
	
	public void addPoint_duration_time(double t) {//-->passed time by packman
		duration[i+1] = duration[i]+ t; 
		i++; 
	}

	public double[] get_duration() {//-->passed time by packman
		return this.duration;      
	}

	public double get_duration_to_fruit_time(int _i) {
		return duration[_i];
	}

	

}