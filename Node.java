/**
 * Node Object
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Node implements Comparable<Node>
{
    public int nodeNumber, degree;
    public double originalHeat;
    public double heat;

    public Node(int name)
    {
        this(name, 0);
    }    

    public Node(int name, int tempDegree)
    {
        degree = tempDegree;
        nodeNumber = name;
    }

    public int compareTo(Node o)
    {
    	return this.degree - o.degree;
    }
}
