package GIS;

import Geom.Point3D;

public class Packman {

	private int id;
	private Point3D coord;
	private int velocity;
	private int eating_radius;
    private double score = 0;
    private fruit closeFruit;
    private double time=0;
    private double timeToFruit;
    private Path path;
	
	public Packman(int id, Point3D coord, int velocity, int eating_radius) {
		this.id= id;
		this.coord= coord;
		this.velocity= velocity;
		this.eating_radius= eating_radius;
		path= new Path();
		path.add(coord);
	}

	public int getId() {
		return id;
	}
	
	public Point3D getcoord() {
		return coord;
	}

	public void setcoord(Point3D coord) {
		this.coord = coord;
	}

	public int get_velocity() {
		return this.velocity;
	}

	public int get_eating_radius() {
		return this.eating_radius ;
	}

	public void setRadius(int _radius) {
		this.eating_radius = _radius;
	}
	
	public double getScore() {
        return score;
    }

    public void addScore(int scoreToAdd) {
        score += scoreToAdd;
    }
	
	public void setScore(double d) {
        this.score= d;
    }
    
    public fruit getCloseFruit() {
    	return this.closeFruit;
    }
    
    public void setCloseFruit(fruit f) {
    	this.closeFruit= f;
    }
    
    public double getTime() {
    	return this.time;       // זמן שהוא כבר עבר
    }
    
    public void setTime(double t) {
    	this.time+= t;       // זמן שהוא כבר עבר
    }
    
    public double getTimeToFruit() {
    	return this.timeToFruit;
    }
    
    public void setTimeToFruit(double t) {
    	this.timeToFruit= t;
    }
	
    public Path getPath() {
    	return this.path;
    }
    
}
