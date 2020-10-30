/*
Joseph McDonough
CMPT 435
Professor Labouseur
18 September 2020
 */

public class Node
{
    String data;     //contents of node are strings
    Node reference;  //tail of node points to next node in linked list
    //~~~~~~~for use of trees, left, right, and previous references
    Node left;
    Node right;
    Node parent;

    //Creates node that holds no data
    public Node()
    {
        data = "";
        reference = null;
    }

    //Creates node with data
    public Node(String s)
    {
        data = s;
        reference = null;
    }

    //Sets pointer of node to the next node in the linked list
    public void nextNode(Node n)
    {
        reference = n;
    }
}

