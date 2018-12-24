package File_format;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	/**
	 * gets a csv file name and create a string array with the lines in the file
	 * @param csvFile the name of the file
	 * @return array with the lines in the file
	 */
	public  static String[] Reader(String csvFile){

		String line = "";
		
		ArrayList<String> data=new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) 
		{ 

		while ((line = br.readLine()) != null) 
			{
			data.add(line);
			
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return data.toArray(new String[data.size()]);
	}

}
