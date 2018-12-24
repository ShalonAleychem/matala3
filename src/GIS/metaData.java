package GIS;

import Geom.Point3D;

public class metaData implements Meta_data  {

	private long UTC;
	private Point3D orientation= null;

	
	public metaData() {
		this.UTC = System.currentTimeMillis();

	}
	
	public metaData(Point3D orientation) {
		this.UTC= System.currentTimeMillis();
		this.orientation= orientation;
	}
	
	@Override
	public long getUTC() {
		return this.UTC;
	}

	@Override
	public String toString() {
		return "UTC:"+getUTC();
	}
	
	@Override
	public Point3D get_Orientation() {
		return orientation;
        }

}
