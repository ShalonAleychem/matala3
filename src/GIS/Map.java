package GIS;

import java.awt.image.BufferedImage;

import Coords.MyCoords;
import Geom.Point3D;
import Coords.MyCoords;
import javafx.scene.image.Image;

public class Map {

	private BufferedImage image;
	private Point3D buttom_right = new Point3D(32.102091,35.212225);
	private Point3D upper_left = new Point3D(32.105767,35.201958);	
	private Point3D gps3;
	private double _height;
	private double _width;
	private MyCoords my_Coords;	
	private int pix_height = 642;
	private int pix_width = 1433;

	private double indicatorX ; 
	private double indicatorY ;


	private double indicatorX1 ;
	private double indicatorY1 ;
	
	

	
	public Map(BufferedImage myImage) {
		this.image = myImage;
		this._height = myImage.getHeight();
		this._width = this.image.getWidth();
		gps3 = new Point3D(this.upper_left.x(), this.buttom_right.y());
		my_Coords = new MyCoords();
		
		System.out.println("unitx" + indicatorX);
		System.out.println("unity" + indicatorY);
	}

	public Point3D Coords2pixels(Point3D gps, int height,int width ) {

	 indicatorX = width / (Math.abs(buttom_right.y() - upper_left.y()));
	 indicatorY = height / (Math.abs(buttom_right.x() - upper_left.x()));
		
		double disY = Math.abs(gps.x() - upper_left.x());
		double disX = Math.abs(gps.y() - upper_left.y());
		double x = disX * indicatorX;
		double y = disY * indicatorY;


		return new Point3D(x,y);
	}

	public Point3D Pixels2Coords(Point3D temp, int height,int width) {
		
		 indicatorX1 = (Math.abs(buttom_right.y() - upper_left.y())) / width;
		 indicatorY1 = (Math.abs(buttom_right.x() - upper_left.x())) / height;
		
		double y= upper_left.y() - temp.x()*indicatorX1;
		System.out.println("gps1: "+ upper_left.x() +"gps.x*unitX = "+(temp.x()*indicatorX));
		double x= upper_left.x() - temp.y()*indicatorY1;
		System.out.println("x : " + x);
		System.out.println("-->Coords , x: " +x + "to y: " + y);
		

		return new Point3D(x,y);
	}
	
	public double _Azimut(Point3D p1, Point3D p2) 
	{
		Point3D point1 = Pixels2Coords(p1,642,1433);
		Point3D point2 = Pixels2Coords(p2,642,1433);
		MyCoords myCoords = new MyCoords();
				
		double azi_muth = myCoords.azimuth_elevation_dist(point1, point2)[0];
		return azi_muth;
	}

	public double distance(Point3D p1, Point3D p2) {
		return p1.distance2D(p2);
	}

	public BufferedImage getImage() {
		return image;
	}

}