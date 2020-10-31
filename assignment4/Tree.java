/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

class Tree extends Node
{
    Node root;

    public Tree()
    {
        root = null;
    }

    public void treeInsert(Tree t, Node n )
    {
        Node trailingParent = null;
        Node currNode = t.root;
        while(currNode != null)
        {
            trailingParent = currNode;
            if(n.data.compareToIgnoreCase(currNode.data) < 0)
                currNode = currNode.left;
            else if(n.data.compareToIgnoreCase(currNode.data) >= 0)
                currNode = currNode.right;
            else
                System.out.println("ERROR ADDING");
        }
        n.parent = trailingParent;
        if(trailingParent == null)
            t.root = n;
        else if(n.data.compareToIgnoreCase(trailingParent.data) < 0)
            trailingParent.left = n;
        else
            trailingParent.right = n;
    }

    public static int count = 0;
    public Node treeSearch(Node root, String key)
    {
        count++;

        if (root==null || key.compareToIgnoreCase(root.data) == 0)
            return root;
        else if (key.compareToIgnoreCase(root.data) < 0)
            return treeSearch(root.left, key);

        else
            return treeSearch(root.right, key);

    }
}
