import java.util.*;

/**
 * Graph Object
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Graph  {

	public boolean[][] adjacencyMatrix;
	public Node[][] adjacencyList;

	public int numberOfNodes;

	public Node[] nodes;

	public boolean useList = true;

	public Graph() {

		Scanner input = new Scanner(System.in);

		numberOfNodes = input.nextInt();

		//Initialize the Node Array.
		nodes = new Node[numberOfNodes];
		//Create all the Nodes.
		for(int i = 0; i < numberOfNodes; i++)
			nodes[i] = new Node(i);

		//Initialize the matrix.
		adjacencyMatrix = new boolean[numberOfNodes][numberOfNodes];
		//Set all edges to false.
		for(int i = 0; i < numberOfNodes; i++)
			for(int j = 0; j < numberOfNodes; j++)
				adjacencyMatrix[i][j] = false;

		//Read in each edge.
		while(input.hasNext()) {
			int edgeStart = input.nextInt();
			int edgeEnd = input.nextInt();
			//If this edge does not exist already...
			if(!adjacencyMatrix[edgeStart][edgeEnd] && !adjacencyMatrix[edgeEnd][edgeStart]) {
				//...add the edge and update the degree counts.
				//Create the edge twice so that it can be accessed with the 
				//edgeStart and edgeEnd in either order.
				adjacencyMatrix[edgeStart][edgeEnd] = true;
				adjacencyMatrix[edgeEnd][edgeStart] = true;
				nodes[edgeStart].degree++;
				nodes[edgeEnd].degree++;
			}
		}
		
		input.close();

		//Initialize the List.
		adjacencyList = new Node[numberOfNodes][];
		for(int i = 0; i < numberOfNodes; i++)
			adjacencyList[i] = new Node[nodes[i].degree];

		//Add the edges in the adjacency Matrix to the list.
		for(int i = 0; i < numberOfNodes; i++) {

			int currentIndex = 0;
			int j = 0;

			while(j < numberOfNodes) {

				if(adjacencyMatrix[i][j])
					adjacencyList[i][currentIndex++] = nodes[j];

				j++;
			}
		}
		
		//DEBUG
		for(Node node : nodes)
		{
			node.heat = -node.degree;
		}
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void printNodes() {

		for(int i = 0; i < numberOfNodes; i++) {

			System.out.print(i + " : { ");
			for(int j = 0; j < adjacencyList[i].length; j++) {
				if(j != adjacencyList[i].length - 1)
					System.out.print(adjacencyList[i][j] + ", ");
				else
					System.out.print(adjacencyList[i][j] + " }\n");
			}
		}

	}

	public void printMatrix() {
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			System.out.print("{ ");
			for(int j = 0; j < adjacencyMatrix[i].length; j++) {
				if(i == j)
					System.out.print("-----  ");
				else if(adjacencyMatrix[i][j])
					System.out.print("True  ");
				else
					System.out.print("False ");
			}
			System.out.print("}\n");
		}
	}
}
