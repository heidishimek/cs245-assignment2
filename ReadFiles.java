import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.*;

public class ReadFiles
{
	ArrayList<String> list = new ArrayList<>(10);

	String attraction;
	String place;
	String vertex;
	String vertex2;
	int distance;


	/* READS ROADS FILE */
	public void roads() throws IOException
	{
		String roads = "roads.csv";
		
		BufferedReader reader = new BufferedReader(new FileReader(roads));

		String lines;
		while ((lines = reader.readLine()) != null)
		{
			String[] temp = lines.split(",");
			vertex = temp[0];
			vertex2 = temp[1];
			distance = Integer.parseInt(temp[2]);

			if (!list.contains(vertex))
			{
				list.add(vertex);
			}
			if (!list.contains(vertex2))
			{
				list.add(vertex);
			}
			lines = reader.readLine();
		}
	}
	
	/* READS ATTRACTIONS FILE */
	public void attractions() throws IOException
	{
		Hashtable<String, String> hash = new Hashtable<String, String>();

		String attractions = "attractions.csv";
	
		BufferedReader reader = new BufferedReader(new FileReader(attractions));

		String lines;
		while ((lines = reader.readLine()) != null)
		{
			String[] temp = lines.split(",");
			attraction = temp[0];
			place = temp[1];
			hash.put(attraction, place);
		}
		System.out.println("Attractions: " + hash.toString());
	}
}