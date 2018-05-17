import java.util.ArrayList;
import java.util.Scanner;

/**
 * Graph Object
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Graph  {

	public boolean[][] adjacencyMatrix;
	public int[][] adjacencyList;

	public int numberOfNodes;

	public Node[] nodes;

	public boolean useList = true;

	public Graph(int numberOfNodes) {

		this.numberOfNodes = numberOfNodes;

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

		Scanner input = new Scanner(System.in);

		//Read in each edge.
		while(input.hasNextInt()) {

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
		adjacencyList = new int[numberOfNodes][];
		for(int i = 0; i < numberOfNodes; i++)
			adjacencyList[i] = new int[nodes[i].degree];

		//Add the edges in the Adjacentcy Matrix to the list.
		for(int i = 0; i < numberOfNodes; i++) {

			int currentIndex = 0;
			int j = 0;

			while(j < numberOfNodes) {

				if(adjacencyMatrix[i][j])
					adjacencyList[i][currentIndex++] = j;

				j++;
			}
		}
	}

	public Node[] getNodes() {
		return nodes;
	}

	public void printNodes() {

		for(int i = 0; i < numberOfNodes; i++) {

			System.out.print(i + " { ")
			for(int j = 0; j < adjacencyList[i].length; j++) {
				if(j != adjacencyList.length[i] - 1)
					System.out.print(adjacencyList[i][j] + ", ");
				else
					System.out.print(adjacencyList[i][j] + " }\n");
			}
		}

	}
}