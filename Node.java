/**
 * Node Object
 * @author Seth Corbin
 * @author Max Potter
 * @author Wyatt Fegley
 */
public class Node
{
    public int nodeNumber, degree;

    public Node(int name) {
        this(name, 0);
    }    

    public Node(int name, int tempDegree) {
        degree = tempDegree;
        nodeNumber = name;
    }
}
