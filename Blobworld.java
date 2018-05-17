import java.util.*;

// TODO : change Nodes from node[] to PriorityQueue

/**
 * This class handles the problem solving of the Blobworld problem.
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Blobworld {

	private static int numberOfNodes;

	private static Graph graph;

	private static boolean[] available;
	
	private static ArrayList<Node> blobsToSend;

	public static void main(String[] args) 
    {		
		//Initialize the variables.
		graph = new Graph();
		available = new boolean[graph.numberOfNodes];

		for(int i = 0; i < available.length; i++) {
			available[i] = true;
		}

		findSolution();
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution() 
	{
		sortDegree(graph.nodes);
		for(int i = 0; i < graph.numberOfNodes; i++)
		{
			if(available[i])
			{
				blobsToSend.add(graph.nodes[i]);
				//available[i] does not need to be set to false because it will never be visited again.
				//Scan through the adjacency matrix and set all adjacent nodes to unavailable.
				for(int j = 0; j < graph.adjacencyMatrix[i].length; j++)
				{
					if(graph.adjacencyMatrix[i][j])
					{
						available[j] = false;
						updateAdjacentDegrees(graph.nodes[j]);
					}
				}
				updateAdjacentDegrees(graph.nodes[i]);
			}
		}
		
		Collections.sort(blobsToSend);
        for(int i = 0; i < blobsToSend.size(); i++)
            System.out.println(blobsToSend.get(i));

	}

    public static void sortNodeNumber(ArrayList<Node> list)
    {
        for(int i = 1; i < list.size(); i++)
        {
			for(int j = i - 1; j < list.size(); j++)
            {
				if(list.get(j).nodeNumber > list.get(i).degree)
                {					
                    Node temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
    }	

    public static void sortDegree(Node[] list)
    {
        for(int i = 1; i < list.length; i++)
        {
			for(int j = i - 1; j < list.length; j++)
            {
				if(list[j].degree > list[i].degree)
                {					
                    Node temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }
    }	
    
    /*
    public static void sortAscending(Node[] arr) {
		for(int i = 1; i < arr.length; i++) {
			Node currentNode = arr[i];
			int j = i - 1;
			for(; j >= 0; j--) {
				if(arr[j].degree > currentNode.degree)
					arr[j+1] = arr[j];
			}
			arr[j] = currentNode;
		}
	}

	
	public static void sortAscending(ArrayList<Node> arr) {
		for(int i = 1; i < arr.size(); i++) {
			Node currentNode = arr.get(i);
			int j = i - 1;
			for(; j >= 0; j--) {
				if(arr.get(j).degree > currentNode.degree)
					arr.get(j+1) = arr.get(j);
			}
			arr.get(j) = currentNode;
		}
	}*/
	
	public static void updateAdjacentDegrees(Node node)
	{
		for(int i = 0; i < graph.numberOfNodes; i++)
		{
			//If this node is adjacent to the now unavailable node, decrease its degree by one.
			if(graph.adjacencyMatrix[node.nodeNumber][i])
			{
				graph.nodes[i].degree--;
			}
		}
	}
}
