import java.util.ArrayList;

// TODO : change Nodes from node[] to PriorityQueue

/**
 * This class will find the solution of the Blobworld problem.
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
//In the current iteration, each blob will be given a weight equal to the largest solution it has been in.
//findSolution will choose nodes to add to the graph randomly, prioritizing nodes with higher weights.
public class Blobworld {

	private static Graph graph;

	private static boolean[] available;
	
	private static ArrayList<Node> blobsToSend;

    private static ArrayList<Node> bestSolution;

    public static void main(String[] args) 
    {		
		//Initialize the variables.
    	graph = new Graph();
    	System.out.println("Graph declared");
        bestSolution = new ArrayList<Node>(0);
        System.out.println("bestSolution declared");
        
        //Run findSolution as many times as you can in the given time, storing the best solution so far.
        long startTime = System.nanoTime();
        for(int run = 1; run <= 20; run++)
        {
        	//DEBUG: Print the number of nodes in the current bestSolution
            System.out.println(bestSolution.size());
            
            //Reset the variables for the next findSolution call.
            available = new boolean[graph.numberOfNodes];
            for(int i = 0; i < available.length; i++)
                available[i] = true;
            blobsToSend = new ArrayList<Node>(graph.numberOfNodes);
            
            findSolution();
        }

        System.out.println("Blobs to send size: " + bestSolution.size());
		sortNodeNumber(bestSolution);
        for(int i = 0; i < bestSolution.size(); i++)
            System.out.println(bestSolution.get(i).nodeNumber);
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution() 
	{
        //Make a copy of the array with all the nodes.
		Node[] nodeArr = new Node[graph.nodes.length];
		System.arraycopy(graph.nodes, 0, nodeArr, 0, graph.nodes.length);
		
		//Choose the nodes to include in the solution.
		int unavailableNodes = 0;
        while(unavailableNodes < graph.numberOfNodes)
        {
            Node selectedNode = chooseByWeight(nodeArr);
            blobsToSend.add(selectedNode);
            
            //Make the node we're taking and its adjacencies unavailable.
            available[selectedNode.nodeNumber] = false;
            nodeArr[selectedNode.nodeNumber] = null;
            for(int i = 0; i < graph.adjacencyMatrix.length; i++)
            {
            	if(graph.adjacencyMatrix[selectedNode.nodeNumber][i])
            	{
            		nodeArr[i] = null;
            		unavailableNodes++;
            	}
            }
        }
        //Add the weights to the nodes used in the solution.
        for(Node node : blobsToSend)
        {
        	node.weight++;
        }
        if(blobsToSend.size() > bestSolution.size())
        {
            bestSolution = blobsToSend;
        }
	}

	public static Node chooseByWeight(ArrayList<Node> nodes, int endIndex) throws Exception {
		if(endIndex < 0)
			throw new Exception();
		if(endIndex == 0)
			return nodes.get(0);
		Node[] n = new Node[endIndex];
		for(int i = 0; i < endIndex; i++) {
			n[i] = nodes.get(i);
		}
		return chooseByWeight(n);
	}

	public static Node chooseByWeight(Node[] nodes) {
		int total = 0;
		for(Node n: nodes) 
        {
			if(n != null)
				total += n.weight;
		}
		double rand = Math.random() * total;
		double count = 0.0;
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) 
            {
				if(count < rand && rand <= (count + nodes[i].weight))
					return nodes[i];
				count += nodes[i].weight;
			}
		}
		return null;
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

