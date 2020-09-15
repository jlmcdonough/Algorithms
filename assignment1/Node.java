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

class Stack {
    Node stackFirst;  //keeps track of first element that would leave stack

    public Stack()
    {
        stackFirst = null; //stack is empty by default
    }

    //Checks to see if stack is empty
    public boolean isEmpty()
    {
        if (stackFirst == null)
            return true;
        else
            return false;
    }

    //Takes in data for node, creates node and pushes to top of stack
    public void push(String s)
    {
        Node n = new Node(s);
        Node oldTop = stackFirst;
        n.reference = oldTop;
        stackFirst = n;
    }

    //Pops top node off the stack
    public Node pop()
    {
        Node returnValue = null;
        if (this.isEmpty()) {
            System.out.print("Nothing to pop, stack is empty");
        } else {
            returnValue = stackFirst;
            stackFirst = stackFirst.reference;
        }
        return returnValue;
    }
}

class Queue {
    Node queueFirst;  //keeps track of first element in queue
    Node queueLast;  //keeps track of last element in queue

    public Queue()
    {
        queueFirst = null;
        queueLast = null;
    }

    //Checks to see if queue is empty
    public boolean isEmpty()
    {
        if (queueFirst == null)
            return true;
        else
            return false;
    }

    //Adds node to end of queue
    public void enqueue(String s)
    {
        Node last = new Node(s);
        last.reference = null;
        Node secondToLast = queueLast;
        if(isEmpty())
            queueFirst = last;
        else
            secondToLast.reference = last;
        queueLast = last;
    }

    //Takes first node off queue
    public Node dequeue()
    {
        Node returnValue = null;
        if (this.isEmpty())
            System.out.print("Nothing to dequeue, queue is empty");
        else
        {
            returnValue = queueFirst;
            queueFirst = queueFirst.reference;
        }
        return returnValue;
    }
}