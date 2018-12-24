package GIS;

import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class GISelement implements GIS_element {

	private Geom_element geom;
	private Meta_data data;
	private String name;
	
	/**
	 * constructor to create a new element
	 * @param geom
	 * @param data
	 * @param name
	 */
	public GISelement(Geom_element geom, Meta_data data, String name) {
		this.geom= geom;
		this.data= data;
		this.name= name;
	}
	
	public GISelement(Geom_element geom, Meta_data data) {
		this.geom= geom;
		this.data= data;
	}
	
	@Override
	public Geom_element getGeom() {
		return this.geom;
	}

	@Override
	public Meta_data getData() {
		return this.data;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void translate(Point3D vec) { 
        MyCoords myCoords = new MyCoords();
        this.geom= myCoords.add((Point3D)this.geom, vec);
	}

}
