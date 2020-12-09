import java.util.LinkedList;
import java.util.*;

/* GRAPH CLASS */
public class Graph extends ReadFiles
{
	LinkedList<Edge> adjList2 = new LinkedList<Edge>();

	public Graph()
	{
		adjList2 = new LinkedList<Edge>();
	}

	public class theGraph
	{
		int vertex;
		LinkedList<Edge>[] adjList;

		theGraph(int vertex)
		{
			this.vertex = vertex;
			adjList = new LinkedList[vertex];

			for (int i = 0; i < vertex; i++)
			{
				adjList[i] = new LinkedList<>();
			}
		}
		
		/* ADDS AN EDGE TO THE EXISTING GRAPH */
		public void addEdge(String vertex1, String vertex2, int distance, int time)
		{
			Edge edge = new Edge(vertex1, vertex2, distance, time);
			adjList[vertex].add(edge);
		}
	}

	static class Edge
	{
		String vertex;
		String vertex2;
		int distance;
		int time;

		public Edge(String vertex, String vertex2, int distance, int time)
		{
			this.vertex = vertex;
			this.vertex2 = vertex2;
			this.distance = distance;
			this.time = time;
		}
	}
}


