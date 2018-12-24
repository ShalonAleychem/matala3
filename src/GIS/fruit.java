package GIS;

import Geom.Point3D;

public class fruit
{

	private int id;
	private Point3D coord;
	private int weigth;

	public fruit(int id, Point3D coord, int weigth) 
	{
		this.id= id;
		this.coord= coord;
		this.weigth= weigth;
	}

	public int get_id() 
	{
		return id;
	}
	
	public Point3D getcoord()
	{
		return coord;
	}

	public int get_weigth()
	{
		return weigth;
	}

}
