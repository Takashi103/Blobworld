import java.util.*;

// TODO : change Nodes from node[] to PriorityQueue

/**
 * This class handles the problem solving of the Blobworld problem.
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Blobworld {

	private static Graph graph;

	private static boolean[] available;
	
	private static ArrayList<Node> blobsToSend;

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

        //Store the number of blobs.
		int n = input.nextInt();
        
        //Initialize the variables.;
		graph = new Graph(n);
		blobsToSend = new ArrayList<Node>(n);

		while(input.hasNextInt()) {

			int startNode = input.nextInt();
			int endNode = input.nextInt();

			graph.addEdge(startNode, endNode);

		}

		available = new boolean[n];

		for(int i = 0; i < available.length; i++) {
			available[i] = true;
		}

		findSolution();
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution() 
	{
		Node[] nodeArray = graph.getNodes();
        sortAscending(nodeArray);
        for(int i = 0; i < nodeArray.length; i++)
        {
            if(available[i])
            {
               	blobsToSend.add(nodeArray[i]);
            	//available[i] does not need to be set to false because it will never be visited again.
               	//Scan through the adjacency matrix and set all adjacent nodes to unavailable.
               	for(int j = 0; j < graph.adjacencyMatrix[i].length; j++)
               	{
               		if(graph.adjacencyMatrix[j])
               		{
						available[j] = false;
					}
               	}
            }
        }
        
        sortAscending(blobsToSend);

	}

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
}
