public class Queue extends Node
{
    Node queueFirst;  //keeps track of first element in queue
    Node queueLast;  //keeps track of last element in queue
    Node nodeEvaluate;

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
        {
            queueFirst = last;
            nodeEvaluate = queueFirst;
        }
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

    public String peek()
    {
        Node returnValue = nodeEvaluate;
        String result;
        if(returnValue == null)
            result = "-9988778899";
        else if (!this.isEmpty())
        {
            result = returnValue.data;
            nodeEvaluate = nodeEvaluate.reference;
        }
        else
            result = "-9988778899";

        return result;
    }

    public String tempPeek()
    {
        Node returnValue = nodeEvaluate;
        String result;

        if(returnValue == null)
            result = "-9988778899";
        else if (!this.isEmpty())
        {
            returnValue = nodeEvaluate;
            result = returnValue.data;
        }
        else
            result = "-9988778899";

        return result;
    }
}
