import java.util.*;

// TODO : change Nodes from node[] to PriorityQueue

/**
 * This class will find the solution of the Blobworld problem.
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

        blobsToSend = new ArrayList<Node>(graph.numberOfNodes);
        
		findSolution();
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution() 
	{
		Node[] nodeArr = new Node[graph.nodes.length];
		System.arraycopy(graph.nodes, 0, nodeArr, 0, graph.nodes.length);
		
		int unavailableNodes = 0;
        while(unavailableNodes < graph.numberOfNodes)
        {
        	sortDegree(nodeArr);
        	for(int i = 0;; i++)
        	{
        		if(available[nodeArr[i].nodeNumber])
        		{
            		blobsToSend.add(nodeArr[i]);
            		available[nodeArr[i].nodeNumber] = false;
					//available[i] does not need to be set to false because it will never be visited again.
					//Scan through the adjacency matrix and set all adjacent nodes to unavailable.
					for(int j = 0; j < graph.adjacencyMatrix[i].length; j++)
					{
			    		if(graph.adjacencyMatrix[i][j])
						{
							available[i] = false;
							unavailableNodes++;
							updateAdjacentDegrees(nodeArr, graph.nodes[j]);
						}
					}
					updateAdjacentDegrees(nodeArr, graph.nodes[nodeArr[i].nodeNumber]);
					break;
				}
			}
        }
		
		sortNodeNumber(blobsToSend);
        for(int i = 0; i < blobsToSend.size(); i++)
            System.out.println(blobsToSend.get(i).nodeNumber);

	}

    public static void sortNodeNumber(ArrayList<Node> list)
    {
        for(int i = 1; i < list.size(); i++)
        {
			for(int j = i - 1; j < list.size(); j++)
            {
				if(list.get(j).nodeNumber > list.get(i).nodeNumber)
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
    
	public static void updateAdjacentDegrees(Node[] nodeArr, Node node)
	{
		for(int i = 0; i < graph.numberOfNodes; i++)
		{
			//If this node is adjacent to the now unavailable node, decrease its degree by one.
			if(graph.adjacencyMatrix[node.nodeNumber][i])
			{
				graph.nodes[i].degree--;
				if(graph.nodes[i].degree < 0)
					graph.nodes[i].degree = 0;
			}
		}
	}
}

