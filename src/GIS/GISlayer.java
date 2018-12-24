package GIS;

import java.util.HashSet;
import Geom.Point3D;

public class GISlayer extends HashSet<GIS_element> implements GIS_layer {

	HashSet<GIS_element> set = new HashSet<>();  // create a new set
	private long creatTime;
	
	public GISlayer() {
        creatTime = System.currentTimeMillis();
    }

	@Override
	public Meta_data get_Meta_data() { 
		
		return new Meta_data() {
            @Override
            public long getUTC() {
                return creatTime;
            }

            @Override
            public Point3D get_Orientation() {
                return null;
            }
        };
	}

}
