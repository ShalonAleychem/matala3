package GIS;

import java.util.HashSet;
import Geom.Point3D;

public class GISproject extends HashSet<GIS_layer> implements GIS_project {
	
	HashSet<GIS_layer> set = new HashSet<>();
    private long creatTime;

    public GISproject() {
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
