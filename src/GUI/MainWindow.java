package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algorithm.ShortestPathAlgo;
import File_format.Path2Kml;
import GIS.Game;
import GIS.Map;
import GIS.Packman;
import GIS.Path;
import GIS.fruit;
import Geom.Point3D;


public class MainWindow extends JFrame implements MouseListener
{
	public BufferedImage myImage ;
	public BufferedImage fruitImage ;
	public BufferedImage pacImage ;
	
	
	Map map;
	ArrayList<Path> arr_paths;
	ArrayList<Path> arr2;
	boolean indicator = false;
	int selection = 0 ;
	Game configuration = new Game();
	double x ;
	double y; 

	

	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this); 
	}

	private ArrayList<Path> copyArray(ArrayList sample){
		ArrayList<Path> testing = new ArrayList<Path>();
		for(int i=0; i<sample.size(); i++) {
			testing.add((Path) sample.get(i));
		}

		return testing; 
	}

	private void initGUI() 
	{
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Game"); 
		Menu menu1 = new Menu("Insert"); 
		Menu menu2 = new Menu("Kml");
		MenuItem item1 = new MenuItem("Pacman");
		MenuItem item2 = new MenuItem("Fruit");
		MenuItem item3 = new MenuItem("run");
		MenuItem item4 = new MenuItem("clear board");
		MenuItem item5 = new MenuItem("load");
		MenuItem item6 = new MenuItem("save");
		MenuItem item7 = new MenuItem("game over");
		MenuItem item8 = new MenuItem("Convert to Kml");

		menuBar.add(menu);
		menuBar.add(menu1);
		menuBar.add(menu2);

		menu1.add(item1);
		menu1.add(item2);
		menu.add(item3);
		menu1.add(item4);
		menu.add(item5);
		menu.add(item6);
		menu.add(item7);
		menu2.add(item8);

		this.setMenuBar(menuBar);

		
		//Each menu item corresponds to his specific action listener


		item1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent listnerP)
			{
				if (listnerP.getActionCommand().equals("Pacman")) 
				{
					System.out.println("you chose pacman.");
					selection = 1; 
				}
			}
			});

		item2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent listnerF) 
			{
				if (listnerF.getActionCommand().equals("Fruit"))
				{
					System.out.println("you chose fruit.");
					selection = 2; 
				}
			}
			});

		item3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent listnerPlay) 
			{
				if (listnerPlay.getActionCommand().equals("run"))
				{
					selection = 3; 
					System.out.println("you have chosen 'run' button");

					if(configuration.isEmpty())
						System.out.println("empty game won't load");
					else
					{
						arr_paths= new ShortestPathAlgo(configuration).getSolution();
				}
					repaint();

				}

			}
			});

		item4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent listenClear)
			{
				if (listenClear.getActionCommand().equals("clear board"))
				{
					System.out.println("you chose to clear the board");
					selection = 4; //clears the array lists and the photo 
					configuration.get_fruits().clear();
					configuration.get_packmans().clear();
					repaint();
					if(arr_paths!=null)
						arr_paths.clear();
				}
			}}
		);

		item5.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("CSV file", "csv"));//only csv files
				chooser.showOpenDialog(null);
				File chosenFile = chooser.getSelectedFile();
				configuration = new Game(chosenFile.getPath());
				selection = 5;
				repaint();
			}
		});

		item6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent saveGame) 
			{
				if (saveGame.getActionCommand().equals("save")) 
				{
					if(configuration.isEmpty())
						System.out.println("can't save an empty game.");
					else
						configuration.toCSV("new game");
				}
			}});

		item7.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent saveGame)
			{
				if (saveGame.getActionCommand().equals("game over")) 
				{
					System.exit(0);
				}
			}
			});

		item8.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent saveGame) 
			{
				if (saveGame.getActionCommand().equals("Convert to Kml")) 
				{
					Game game= new Game("new game.csv");
					ArrayList<Path> crt= new ShortestPathAlgo(game).getSolution();
					Path2Kml p= new Path2Kml("kmlFile", crt);
					System.out.println("file of kml format has been created");
				}
			}
			});

		try {
			myImage = ImageIO.read(new File("C:\\Users\\Owner\\Desktop\\Ariel1.png"));
			fruitImage = ImageIO.read(new File("C:\\Users\\Owner\\Desktop\\images\\fruit.png"));
			pacImage = ImageIO.read(new File("C:\\Users\\Owner\\Desktop\\images\\yellow_pac.png"));
			map = new Map(myImage);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public void paint(Graphics g)
	{
		g.drawImage(myImage,0,0,this.getWidth(),this.getHeight(),this); 
		
		Iterator <Packman> it = configuration.get_packmans().iterator();
		Iterator<fruit> it2 = configuration.get_fruits().iterator();

		int ri_Pi= 30;
		int r_Fi = 20;

		Color line = new Color(255, 255, 255); //white color


		if(selection!=4) {
			while(it.hasNext()) {
				Packman pacman = it.next();
				Point3D pacmancoord = pacman.getcoord();
				Point3D pacmanPix= map.Coords2pixels(pacmancoord,this.getHeight(),this.getWidth());
				int pX = (int)pacmanPix.x();
				int pY = (int)pacmanPix.y();
				g.drawImage(pacImage, pX, pY,40,40,this);
			}

			while(it2.hasNext()) {
				fruit fruit =it2.next();
				
				Point3D fruitGPS = fruit.getcoord();

				Point3D fruitPIX = map.Coords2pixels(fruitGPS,this.getHeight(),this.getWidth());

				int fX = (int)fruitPIX.x();
				int fY = (int)fruitPIX.y();
				g.drawImage(fruitImage, fX, fY,30,30,this);


			}
		}

		if(selection==3)
		{//loops through each packman's special path
			Iterator<Path> it1= arr_paths.iterator();
			while(it1.hasNext()) {  
				Path path = it1.next();
				for(int i=1; i<path.size();i++) {
					Point3D sec = path.get(i);
					sec= map.Coords2pixels(sec,this.getHeight(),this.getWidth());
					Point3D first = path.get(i-1);
					first = map.Coords2pixels(first,this.getHeight(),this.getWidth());
					g.setColor(line);
					g.drawLine((int)first.x(),(int) first.y(),(int) sec.x(), (int)sec.y());
				}
			}	
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg) {
		System.out.println("mouse Clicked");

		int _X= arg.getX();
		int _Y= arg.getY();

		System.out.println("("+ _X + "," + _Y +")");


		if(selection == 1) {
			Point3D p = new Point3D(_X,_Y,0);
			Point3D p2c= map.Pixels2Coords(p,this.getHeight(),this.getWidth());
			System.out.println("to coord, x:"+p2c.x()+", y:"+p2c.y());
			Packman pac = new Packman (1, p2c,1,1);
			configuration.get_packmans().add(pac);
		}

		if(selection == 2) {
			Point3D p = new Point3D(_X,_Y,0);
			Point3D p2c= map.Pixels2Coords(p,this.getHeight(),this.getWidth());
			fruit fr = new fruit (1, p2c,1);
			configuration.get_fruits().add(fr);
		}





		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("mouse entered");

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}



}