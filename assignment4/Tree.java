/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

public class Tree extends Node
{
    Node root;
    Tree left;
    Tree right;
    Node parent;

    public Tree()
    {
        this.root = null;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public static void treeInsert(Tree t, Node n)
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
        {
            trailingParent.left = n;
            System.out.println("LEFT" + n.data);
        }

        else
        {
            trailingParent.right = n;
            System.out.println("RIGHT" + n.data);
        }
    }

    public Tree treeSearch(Tree t, String item)
    {
        if(t == null || t.data.equalsIgnoreCase(item))
            return t;
        else if(item.compareToIgnoreCase(t.data) < 0)
            return treeSearch(t.left, item);
        else
            return treeSearch(t.right, item);
    }

}
