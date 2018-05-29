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

    private static ArrayList<Node> bestSolution;
    
    private static int noImprovementCount = 0;

    public static void main(String[] args)
    {		
		//Initialize the variables.
    	graph = new Graph();
        bestSolution = new ArrayList<Node>(0);
        
        //Run findSolution a set number of times, storing the best solution so far.
        int run = 1;
        for(; run <= 100; run++)
        {
            //Reset the variables for the next findSolution call.
            available = new boolean[graph.numberOfNodes];
            for(int i = 0; i < available.length; i++)
                available[i] = true;
            blobsToSend = new ArrayList<Node>(graph.numberOfNodes);
            
            findSolution(false);
        }
        //TODO RESET RUN NUMBER TO 100000.
        for(; run <= 100000; run++)
        {	
        	if(noImprovementCount > 1000)
        	{
        		noImprovementCount = 0;
        		for(Node node : graph.nodes)
        			node.weight = 1;
        	}
        	
            //Reset the variables for the next findSolution call.
            available = new boolean[graph.numberOfNodes];
            for(int i = 0; i < available.length; i++)
                available[i] = true;
            blobsToSend = new ArrayList<Node>(graph.numberOfNodes);
            
            findSolution(true);
        }

        System.out.println("Blobs to send size: " + bestSolution.size());
		sortNodeNumber(bestSolution);
        for(int i = 0; i < bestSolution.size(); i++)
            System.out.println(bestSolution.get(i).nodeNumber);
	}
	
	//Fills the blobsToSend list with the blobs that should be sent then sorts the list.
	public static void findSolution(boolean withWeights) 
	{
        //Make a copy of the array with all the nodes.
		Node[] nodeArr = new Node[graph.nodes.length];
		System.arraycopy(graph.nodes, 0, nodeArr, 0, graph.nodes.length);
		
		//Choose the nodes to include in the solution.
		int unavailableNodes = 0;
        while(unavailableNodes < graph.numberOfNodes)
        {
            Node selectedNode;
			if(withWeights)
			{
				selectedNode = chooseByWeight(nodeArr);
			}
			else
			{
				selectedNode = chooseRandom(nodeArr);
			}
            blobsToSend.add(selectedNode);
            
            //Make the node we're taking and its adjacencies unavailable.
            available[selectedNode.nodeNumber] = false;
            unavailableNodes++;
            nodeArr[selectedNode.nodeNumber] = null;
            for(Node node : graph.adjacencyList[selectedNode.nodeNumber])
            {
            	if(available [node.nodeNumber])
            	{
            		nodeArr[node.nodeNumber] = null;
            		unavailableNodes++;
            	}
            }
        }
        //Add the weights to the nodes used in the solution.
        for(Node node : blobsToSend)
        {
			if(node.weight < 1.0 + (double)(blobsToSend.size()) / 100.0)
				node.weight = 1.0 + (double)(blobsToSend.size()) / 100.0;
        }
        if(blobsToSend.size() > bestSolution.size())
        {
            bestSolution = blobsToSend;
            noImprovementCount = 0;
        }
        else
        {
        	noImprovementCount++;
        }
	}

	public static Node chooseByWeight(Node[] nodes) {
		double total = 0;
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
				if(count <= rand && rand <= (count + nodes[i].weight))
					return nodes[i];
				count += nodes[i].weight;
			}
		}
		for(Node n : nodes)
			System.out.print(n + " ");
		return null;
	}

	public static Node chooseRandom(Node[] nodes) {
		int rand = (int)(Math.random() * (double)nodes.length);
		while(nodes[rand] == null)
		{
			rand = (int)(Math.random() * (double)nodes.length);
		}
		return nodes[rand];
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

}

