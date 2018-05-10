//@author Max Potter
public class Graph
{
    public boolean[][] adjacencyMatrix;
    public Node[] nodes;
    
    public Graph(int numberOfNodes)
    {
    	//Initialize the matrix.
        adjacencyMatrix = new boolean[numberOfNodes][numberOfNodes];
        for(boolean[] edgeStart : adjacencyMatrix)
        {
            for(int edgeEnd = 0; edgeEnd < numberOfNodes; edgeEnd++)
            {
                edgeStart[edgeEnd] = false;
            }
        }
        
        //Initialize the nodes array.
        nodes = new Node[numberOfNodes];
        for(int nodeNumber = 0; nodeNumber < numberOfNodes; nodeNumber++)
        {
        	nodes[nodeNumber].nodeNumber = nodeNumber;
        	nodes[nodeNumber].degree = 0;
        }
    }
    
    public void addEdge(int edgeStart, int edgeEnd)
    {
    	//If this edge does not exist already...
    	if(!adjacencyMatrix[edgeStart][edgeEnd])
    	{
    		//...add the edge and update the degree counts.
    		//Create the edge twice so that it can be accessed with the 
    		//edgeStart and edgeEnd in either order.
        	adjacencyMatrix[edgeStart][edgeEnd] = true;
        	adjacencyMatrix[edgeEnd][edgeStart] = true;
        	nodes[edgeStart].degree++;
        	nodes[edgeEnd].degree++;
        }
    }
    
    public Node[] getNodes()
    {
    	return nodes;
    }
}
