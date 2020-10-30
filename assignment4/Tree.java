/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

public class Tree extends Node
{
    Node root;
    Node left;
    Node right;
    Node parent;

    public Tree()
    {
        this.root = null;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public void treeInsert(Tree t, Node n)
    {
        Node trailingParent = null;
        Node currNode = t.root;

        while(currNode != null)
        {
            trailingParent = currNode;
            if(n.data.compareToIgnoreCase(currNode.data) < 0)
                currNode = currNode.left;
            else
                currNode = currNode.right;
        }

        n.parent = trailingParent;

        if(trailingParent == null)
            t.root = n;
        else if(n.data.compareToIgnoreCase(trailingParent.data) < 0)
            trailingParent.left = n;
        else
            trailingParent.right = n;
    }

}
