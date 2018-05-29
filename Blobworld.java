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

    	for(Node node : graph.nodes)
    		System.out.println("Node " + node.nodeNumber + " has a degree of " + node.degree + " and a heat of " + node.heat + ".");
    		
    	
        System.out.println("Blobs to send size: " + blobsToSend.size());
		sortNodeNumber(blobsToSend);
        for(int i = 0; i < blobsToSend.size(); i++)
            System.out.println(blobsToSend.get(i).nodeNumber);
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
		
	}

	public static void ripple(Node node)
	{
		//Ripple the node's heat outwards, increasing the heat of adjacent nodes and decreasing the heat of their adjacent nodes.
		for(Node neighbour : graph.adjacencyList[node.nodeNumber])
		{
			neighbour.heat += node.heat * 0.1;
		}
		for(Node neighbour : graph.adjacencyList[node.nodeNumber])
		{
			for(Node secondNeighbour : graph.adjacencyList[neighbour.nodeNumber])
			{
				neighbour.heat -= node.heat * 0.1;
			}
		}
	}
	
	public static void sortNodeNumber(ArrayList<Node> list)
	{
		for(int i = 1; i < list.size(); i++)
		{
			for(int j = i - 1; j < list.size(); j++)
			{
				if(list.get(i).nodeNumber < list.get(j).nodeNumber)
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
				if(list[i].degree < list[j].degree)
				{					
					Node temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}
	
	public static void sortHeat(Node[] list)
	{
		for(int i = 1; i < list.length; i++)
		{
			for(int j = i - 1; j < list.length; j++)
			{
				if(list[i].heat < list[j].heat)
				{					
					Node temp = list[i];
					list[i] = list[j];
					list[j] = temp;
				}
			}
		}
	}

}

