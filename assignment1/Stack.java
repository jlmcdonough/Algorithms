public class Stack extends Node
{
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
