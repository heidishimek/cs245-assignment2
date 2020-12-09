import java.util.*;
import java.io.*;
import java.io.BufferedReader;


public class Roadtrip extends Graph
{
	Hashtable<String, String> place;
	Hashtable<String, String> previousPlace;
	Hashtable<String, Boolean> visited;
	Hashtable<String, Integer> distance;

	HashSet<String> placesList;
	int totalDistance;
	int time;
	theGraph graph;

	public Roadtrip()
	{
		place = new Hashtable<>();
		previousPlace = new Hashtable<>();
		visited = new Hashtable<>();
		distance = new Hashtable<>();
		placesList = new HashSet<>();
		totalDistance = 0;
		time = 0;
	}

	/* ROUTE FUNCTION, RETURNS SHORTEST ROUTE BASED ON STARTING/ENDING CITIES
	AND ATTRACTIONS */
	List<String> route(String starting_city, String ending_city, List<String> attractions)
	{
		ArrayList<String> path = new ArrayList<>();
		graph.addEdge(starting_city, starting_city, 0, 0);

		Iterator<String> index = placesList.iterator();

		/* DIJKSTRA'S ALGORITHM */
		while (index.hasNext())
		{
			String city = index.next();
			if (city != null)
			{
				visited.put(city, false);
				distance.put(city, Integer.MAX_VALUE);
			}
		}
		distance.put(starting_city, 0);

		visit(placesList);

		ArrayList<Integer> sortedAttractions = new ArrayList<>();
		Hashtable<Integer, String> distanceEstimate = new Hashtable<>();
		Iterator<String> attractionsList = attractions.iterator();
		ArrayList<String> sortedCities = new ArrayList<>();

		while (attractionsList.hasNext())
		{
			String index2 = attractionsList.next();
			sortedAttractions.add(distance.get(place.get(index2)));
			distanceEstimate.put(distance.get(place.get(index2)), index2);
		}

		Collections.sort(sortedAttractions);

		for (int i : sortedAttractions)
		{
			sortedCities.add(place.get(distanceEstimate.get(i)));
		}

		sortedCities.add(0, starting_city);

		if (sortedCities.contains(ending_city))
		{
			sortedCities.remove(ending_city);
			sortedCities.add(ending_city);
		}
		else
		{
			sortedCities.add(ending_city);
		}

		Stack placesList2 = new Stack();
		for (int i = 0; i < sortedCities.size() - 1; i++)
		{
			String curr = sortedCities.get(i);
			String next = sortedCities.get(i + 1);
			String temp = sortedCities.get(i + 1);
			placesList2.push(next);

			while (!curr.equals(next))
			{
				String prev = previousPlace.get(next);
				totalDistance += getDistance(next, prev);
				time += getTime(next, prev);
				placesList2.add(prev);
			}

			while (!placesList2.isEmpty())
			{
				path.add((String)placesList2.pop());
			}

			visited = new Hashtable<>();
			previousPlace = new Hashtable<>();
			distance = new Hashtable<>();

			for (String place : placesList)
			{
				if (place != null)
				{
					visited.put(place, false);
					distance.put(place, Integer.MAX_VALUE);
				}
			}
			distance.put(temp, 0);
			visit(placesList);
		}
		return path;
	}

	/* AMOUNT OF TIME BETWEEN VERTICIES */
	private int getTime(String vertex3, String vertex4)
	{
		int time = 0;
		for (Edge edge : adjList2)
		{
			if (edge.vertex.equals(vertex3) && edge.vertex2.equals(vertex4))
			{
				return edge.time;
			}
			else if (edge.vertex.equals(vertex4) && edge.vertex2.equals(vertex3))
			{
				return edge.time;
			}
		}
		return time;
	}

	/* HELPER FUNCTION FOR DIJKSTRA'S */
	public String smallestVertex()
	{
		String vertex = "";
		int i = Integer.MAX_VALUE;
		for (String place : list)
		{
			if (!visited.get(place) && distance.get(place) <= i)
			{
				i = distance.get(place);
				vertex = place;
			}
		}
		return vertex;
	}

	/* UPDATES HASHTABLE */
	public void visit(HashSet<String> list)
	{
		for (String place : placesList)
		{
			while (!visited.get(place))
			{
				String vertex = smallestVertex();
				knownLoc(vertex);

				for (String i : placesList)
				{
					int j = getDistance(vertex, i);
					if (distance.get(i) > distance.get(vertex) + j && !i.equals(vertex))
					{
						distance.put(i, distance.get(vertex) + j);
						previousPlace.put(i, vertex);
					}
				}
			}
		}
	}

	/* RETURNS DISTANCE BETWEEN VERTICIES */
	private int getDistance(String vertex3, String vertex4)
	{
		int distance = 0;
		for (Edge edge : adjList2)
		{
			if (edge.vertex.equals(vertex3) && edge.vertex2.equals(vertex3))
			{
				return edge.distance;
			}
			else if (edge.vertex.equals(vertex4) && edge.vertex2.equals(vertex3))
			{
				return edge.distance;
			}
			return distance;
		}
		return distance;
	}

	/* KEEPS TRACK OF WHAT LOCATIONS HAVE BEEN VISITED ALREADY */
	/* HELPER FUNCTION FOR DIJKSTRA'S */
	public void knownLoc(String vertex)
	{
		if (vertex != null)
		{
			visited.put(vertex, true);
		}
	}

	/* PRINT FUNCTION */
	public void printOutput(List<String> roads)
	{
		System.out.println("Route to take: " + roads.toString());
		System.out.println("Time the route will take: " + Integer.toString(time));
		System.out.println("Distance the route will take: " + Integer.toString(totalDistance));

	}

	public static void main(String[] args) throws IOException
	{
		ReadFiles read = new ReadFiles();
		read.roads();
		read.attractions();
		
		Roadtrip trip = new Roadtrip();
		trip.attractions();
		trip.roads();

		List<String> attractionsToVisit = new ArrayList<>();
		attractionsToVisit.add("Alcatraz,San Francisco CA");

		List<String> theRoute = trip.route("Des Moines IA", "Salinas CA", attractionsToVisit);

		trip.printOutput(theRoute);
	}
}