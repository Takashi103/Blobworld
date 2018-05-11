import java.util.Scanner;
import java.util.PriorityQueue;

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

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

		int n = input.nextInt();

		graph = new Graph(n);

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

	public static void findSolution() {

		Node[] nodeArray = graph.getNodes();


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