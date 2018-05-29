import java.util.ArrayList;
//TODO: Add less than 1 weights, add weight degradation?, and try profiling code speed.

/**
 * This class will find the solution of the Blobworld problem.
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
//In the current iteration, each blob will be given a weight equal to the largest solution it has been in.
//findSolution will choose nodes to add to the graph randomly, prioritizing nodes with higher weights.
public class Blobworld 
{

	private static Graph graph;

	private static boolean[] available;
	
	private static ArrayList<Node> blobsToSend;

    public static void main(String[] args)
    {		
		//Initialize the variables.
    	graph = new Graph();
    	blobsToSend = new ArrayList<Node>(graph.numberOfNodes);
    	available = new boolean[graph.numberOfNodes];
    	for(int i = 0; i < available.length; i++)
    		available[i] = true;
        
    	findSolution();	
    	
		sortNodeNumber(blobsToSend);
        for(int i = 0; i < blobsToSend.size(); i++)
            System.out.println(blobsToSend.get(i).nodeNumber);
        
        //DEBUG
        checkSolution();
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution() 
	{
        //Make a copy of the array with all the nodes.
		Node[] nodeArr = new Node[graph.nodes.length];
		System.arraycopy(graph.nodes, 0, nodeArr, 0, graph.nodes.length);
		
		//Ripple out the heats of all of the nodes.
		for(Node node : nodeArr)
		{
			ripple(node);
		}
		
		//Choose the nodes based on heat.
		sortHeat(nodeArr);
		for(int i = 0; i < nodeArr.length; i++)
		{
			if(available[nodeArr[i].nodeNumber]) 
			{
				blobsToSend.add(nodeArr[i]);
				for(Node neighbour : graph.adjacencyList[nodeArr[i].nodeNumber])
				{
					available[neighbour.nodeNumber] = false;
				}
			}
		}
		
		//DEBUG
		/*
		for(int i = 0; i < nodeArr.length; i++)
    		System.out.println("Node " + nodeArr[i].nodeNumber + " has a degree of " + nodeArr[i].degree + " and a heat of " + nodeArr[i].heat + ".");
    	*/
		
	}

	public static void ripple(Node node)
	{
		//Ripple the node's heat outwards, increasing the heat of adjacent nodes and decreasing the heat of their adjacent nodes.
		for(Node neighbour : graph.adjacencyList[node.nodeNumber])
		{
			if(node.heat != 0)
				neighbour.heat += node.heat * 0.1;
		}
		for(Node neighbour : graph.adjacencyList[node.nodeNumber])
		{
			for(Node secondNeighbour : graph.adjacencyList[neighbour.nodeNumber])
			{
				if(node.heat != 0)
					secondNeighbour.heat -= node.heat * 0.1;
			}
		}
	}
	
	public static void sortNodeNumber(ArrayList<Node> list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			for(int j = 1; j < list.size(); j++)
			{
				if(list.get(j).nodeNumber < list.get(j - 1).nodeNumber)
				{					
					Node temp = list.get(j);
					list.set(j, list.get(j - 1));
					list.set(j - 1, temp);
				}
			}
		}
	}
	
	public static void sortDegree(Node[] list)
	{
		for(int i = 0; i < list.length; i++)
		{
			for(int j = 1; j < list.length; j++)
			{
				if(list[j].degree > list[j - 1].degree)
				{					
					Node temp = list[j];
					list[j] = list[j - 1];
					list[j - 1] = temp;
				}
			}
		}
	}
	
	public static void sortHeat(Node[] list)
	{
		for(int i = 0; i < list.length; i++)
		{
			for(int j = 1; j < list.length; j++)
			{
				if(list[j].heat > list[j - 1].heat)
				{					
					Node temp = list[j];
					list[j] = list[j - 1];
					list[j - 1] = temp;
				}
			}
		}
	}
			
	public static void checkSolution()
	{
		for(Node node : blobsToSend)
		{
			for(int i = 0; i < blobsToSend.size(); i++)
			{
				if(graph.adjacencyMatrix[node.nodeNumber][blobsToSend.get(i).nodeNumber])
				{
                    //System.out.println("Vertices " + node.nodeNumber + " and " + blobsToSend.get(i) + " are adjacent.");
                    return;
				}
			}
		}
		//System.out.print("This solution with " +  blobsToSend.size() + " nodes is valid!");
		
	}
	
}

