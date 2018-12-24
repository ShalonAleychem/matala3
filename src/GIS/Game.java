package GIS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import File_format.CSVReader;
import Geom.Point3D;

public class Game extends ArrayList<Object> {

	ArrayList<fruit> fruits = new ArrayList<fruit>();
	ArrayList<Packman> Packmans = new ArrayList<Packman>();

	public Game()
	{
	}

	public Game(String csvFile) {

		File file = new File(csvFile);

		if (isCsv(file)) 
		{

			String[] lines= CSVReader.Reader(csvFile);

			for (int i=1; i<lines.length; i++)
			{

				String[] _info = lines[i].split(",");

				String type= _info[0];
				int id= Integer.parseInt(_info[1]);
				double lat = Double.parseDouble(_info[2]);
				double lon = Double.parseDouble(_info[3]);
				double alt = Double.parseDouble(_info[4]);

				Point3D gps_coord = new Point3D(lat, lon, alt);  // instanzise the point

				int speed_weight= Integer.parseInt(_info[5]);

				if(type.equals("P")) {
					int eat_radius= Integer.parseInt(_info[6]);
					Packman p= new Packman(id, gps_coord, speed_weight, eat_radius);
					Packmans.add(p);
				}

				else 
				{
					fruit f= new fruit(id, gps_coord, speed_weight);
					fruits.add(f);
				}
			}
		}

		else
			System.err.print("input is invalid!");
	}

	private boolean isCsv(File fileType){
		return fileType.getName().endsWith(".csv");
	}
	
	public void toCSV(String name) {
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(name+".csv"));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        StringBuilder sb = new StringBuilder();
        start(sb);
        addPackmanSet(sb);
        addFruitSet(sb);

        pw.write(sb.toString());
        pw.close();
		
	}
	
	public void start(StringBuilder sb) {
		sb.append("Type");
        sb.append(",");
		sb.append("id");
        sb.append(",");
        sb.append("Lat");
        sb.append(',');
        sb.append("Lon");
        sb.append(',');
        sb.append("Alt");
        sb.append(',');
        sb.append("Speed/Weight");
        sb.append(',');
        sb.append("Radius");
        sb.append(',');
        sb.append(Packmans.size());
        sb.append(',');
        sb.append(fruits.size());
        sb.append('\n');
	}

	public void addPackmanSet(StringBuilder sb) {
		
		Iterator<Packman> it= Packmans.iterator();		
		while(it.hasNext())   // as long as layer left with variables
		{
			Packman p= it.next();  
			addPackman(sb, p); 
		}
	}
	
	public void addFruitSet(StringBuilder sb) {
		
		Iterator<fruit> it= fruits.iterator();		
		while(it.hasNext())   // while the layer has more elements
		{
			fruit f= it.next();  
			addFruit(sb, f); 
		}
	}
	
	public void addPackman(StringBuilder sb, Packman p) {
        sb.append("P");
        sb.append(',');
        sb.append(p.getId());
        sb.append(',');
        sb.append(p.getcoord().x());
        sb.append(',');
        sb.append(p.getcoord().y());
        sb.append(',');
        sb.append(p.getcoord().z());
        sb.append(',');
        sb.append(p.get_velocity());
        sb.append(',');
        sb.append(p.get_eating_radius());

        sb.append('\n');
	}
	
	public void addFruit(StringBuilder sb, fruit f) {
        sb.append("F");
        sb.append(',');
        sb.append(f.get_id());
        sb.append(',');
        sb.append(f.getcoord().x());
        sb.append(',');
        sb.append(f.getcoord().y());
        sb.append(',');
        sb.append(f.getcoord().z());
        sb.append(',');
        sb.append(f.get_weigth());
        sb.append(',');

        sb.append('\n');
	}

	public ArrayList<fruit> get_fruits() {
		return fruits;
	}

	public ArrayList<Packman> get_packmans() {
		return Packmans;
	}
	
	public boolean isEmpty() {
		return fruits.isEmpty()&Packmans.isEmpty();
	}
	
}
