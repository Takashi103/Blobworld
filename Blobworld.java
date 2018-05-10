import java.util.Scanner;

/**
 * This class handles the problem solving of the Blobworld problem.
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Blobworld {

	private static Graph graph;

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);

		int n = input.nextInt();

		graph = new Graph(n);

		while(input.hasNextInt()) {

			int startNode = input.nextInt();
			int endNode = input.nextInt();

			graph.addEdge(startNode, endNode);

		}

		findSolution();
	}

	public static void findSolution() {

		Node[] nodes = graph.getNodes();
		sortAscending(nodes);


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