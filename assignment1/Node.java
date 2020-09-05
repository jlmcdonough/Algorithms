/*
Joseph McDonough
CMPT 435
Professor Labouseur
18 September 2020
 */

public class Node {
    String data;     //contents of node are strings
    Node reference;  //tail of node points to next node in linked list

    //Creates node that holds no data
    public Node()
    {
        data = "";
        reference = null;
    }

    //Creates node with data
    public Node(String s) {
        data = s;
        reference = null;
    }

    //Sets pointer of node to the next node in the linked list
    public void nextNode(Node n)
    {
        this.reference = n;
    }
}

class Stack {
    Node first;  //keeps track of first element that would leave stack

    public Stack()
    {
        first = null; //stack is empty by default
    }

    //Checks to see if stack is empty
    public boolean isEmpty()
    {
        if (first == null)
            return true;
        else
            return false;
    }

    //Takes in data for node, creates node and pushes to top of stack
    public void push(String s)
    {
        Node n = new Node(s);
        Node oldTop = this.first;
        n.reference = oldTop;
        this.first = n;
    }

    //Pops top node off the stack
    public Node pop() {
        Node returnValue = null;
        if (this.isEmpty()) {
            System.out.print("Nothing to pop, stack is empty");
        } else {
            returnValue = first;
            first = first.reference;
        }
        return returnValue;
    }
}

class Queue {

}