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
        
        //Initialize the variables.
		graph = new Graph(n);
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
       	Collections.sort(nodeArray);
        for(int i = 0; i < nodeArray.length; i++)
        {
            if(available[i])
            {
               	blobsToSend.add(nodeArray[i]);
            	//available[i] does not need to be set to false because it will never be visited again.
               	//Scan through the adjacency matrix and set all adjacent nodes to unavailable.
               	for(int j = 0; j < graph.adjacencyMatrix[i].length; j++)
               	{
               		if(graph.adjacencyMatrix[i][j])
               		{
						available[j] = false;
						updateAdjacentDegrees(graph.Nodes[j]);
					}
               	}
               	updateAdjacentDegrees(nodeArray[i]);
            }
        }
        
       	Collections.sort(blobsToSend);

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
	
	/*public static void sortAscending(ArrayList<Node> arr) {
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
		for(int i = 0; i < nodeArray.length; i++)
		{
			//If this node is adjacent to the now unavailable node, decrease its degree by one.
			if(graph.adjacencyMatrix[node.nodeNumber][i])
			{
				graph.Nodes[i].degree--;
			}
		}
	}
}
























