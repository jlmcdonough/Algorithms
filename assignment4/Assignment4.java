/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Assignment4 extends Tree
{
    public static void main(String args[]) throws FileNotFoundException
    {

       /*
        ArrayList<String> myMagicItems = getList();
        String[] myRandomItems = generateRandomItems(myMagicItems);

        binaryTreeComplete(myMagicItems, myRandomItems);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"); */

        ArrayList<String> graphFile = readGraphFile();
        ArrayList<Graph> myGraphs = createGraphs(graphFile);
        Graph g = myGraphs.get(0);
        System.out.println(g.getVertices().get(0).getId());
        System.out.println(g.getVertices().get(0).edges.size());
        g.depthFirstSearch(g.vertices.get(0));

      //  graphComplete();


    }


    //~~~~~~~~~~~~~~~~~CLASSES USED FOR AID~~~~~~~~~
    //used to help store tree count and item pairs
    static class TreePair
    {
        private int searchCount;
        private String searchItem;

        public TreePair(String item, int count)
        {
            searchItem = item;
            searchCount = count;
        }

        public String getItem()
        {
            return this.searchItem;
        }

        public int getCount()
        {
            return this.searchCount;
        }
    }

    //used to hold a graph which contains an ArrayList of vertexs where each index is an arraylist of edges
    static class Graph
    {
        private ArrayList<Vertex> vertices;
        private String name;

        public Graph(String n, ArrayList<Vertex> v)
        {
            this.vertices = v;
            this.name = n;
        }

        public String getName()
        {
            return this.name;
        }

        public ArrayList<Vertex> getVertices()
        {
            return this.vertices;
        }

        public Vertex getThisVertex(int id)
        {
            for(Vertex v : this.getVertices())
            {
                if(v.getId() == id)
                    return v;
            }

            return null;
        }

        public void depthFirstSearch(Vertex fromVertex)
        {
            if(!fromVertex.getProcessed())
            {
                System.out.println(fromVertex.getId());
                fromVertex.setProcessed(true);
            }

            //System.out.println("SIZE: " + fromVertex.getEdges().size() + " FOR VERTEX: " + fromVertex.getId());
           // System.out.println(fromVertex.getEdges());
            for(int i = 0; i < fromVertex.getEdges().size(); i++)
            {
                int n = fromVertex.getEdges().get(i);
                //System.out.println("N: " + n);
                Vertex v = this.getThisVertex(n);
                //System.out.println("VERTEX V: " + v.getId());
                if(!v.getProcessed())
                    depthFirstSearch(v);
            }
        }

    }

    static class Vertex
    {
        private ArrayList<Integer> edges;
        private boolean processed;
        private int id;

        public Vertex(ArrayList<Integer> e, int i)
        {
            this.edges = e;
            this.processed = false;
            this.id = i;
        }

        public ArrayList<Integer> getEdges()

        {
            return this.edges;
        }

        public boolean getProcessed()
        {
            return this.processed;
        }

        public int getId()
        {
            return this.id;
        }

        public void setProcessed(boolean bool)
        {
            this.processed = bool;
        }

    }

    //~~~~~~~~~~~~~~~~~~READ FILE AND GET RANDOM ITEMS~~~~~~~~~~~~~~~~~~~~~~
    //does the work of the previous assignment of reading the file
    public static ArrayList<String> getList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("magicitems.txt"));
        ArrayList<String> myMagicItems = new ArrayList<String>();  //will be the sorted list

        while (reader.hasNextLine())
            myMagicItems.add(reader.nextLine());

        return myMagicItems;
    }

    //randomly selected the 42 items for the searches to work on
    public static String[] generateRandomItems(List<String> inputList)
    {
        String[] randomItems = new String[42];
        Random rand = new Random();
        for(int i = 0; i < randomItems.length; i++)  //assignment wants 42 random items
        {
            int randIndex = rand.nextInt(inputList.size());  //choosing myMagicItems so can remove items without messing up search
            randomItems[i] = inputList.get(randIndex);
            inputList.remove(randIndex);  //dont want to get duplicates so removing once its gotten
        }

        return randomItems;
    }

    //~~~~~~~~~~~~~~~~~~BINARY TREE~~~~~~~~~~~~~~~~~~~~~
    public static Tree createAndFillTree(List<String> magicItems)
    {
        Tree myBinaryTree = new Tree();

        for(int i = 0; i < magicItems.size(); i++)
        {
            myBinaryTree.treeInsert(myBinaryTree, new Node(magicItems.get(i)));
        }

        return myBinaryTree;
    }

    //search the tree for all items in the random items list, using findTreeElement from Tree class to find each individual
    public static ArrayList<TreePair> searchTree(Tree myBinaryTree, String[] randomItems)
    {
        ArrayList<TreePair> binaryTreeResults = new ArrayList<TreePair>();
        for(String s : randomItems)
        {
            myBinaryTree.findTreeElement(myBinaryTree.getRoot(), s);
            binaryTreeResults.add(new TreePair(s, count));
            count = 0;
        }

        return binaryTreeResults;
    }

    public static void binaryTreeComplete(List<String> magicItems, String[] randomItems)
    {
        Tree myBinaryTree = createAndFillTree(magicItems);
        ArrayList<TreePair> results = searchTree(myBinaryTree, randomItems);
        printSearchResults(results);
    }

    //~~~~~~~~~~~~~~~~~~PRINT RESULTS~~~~~~~~~~~~~~~~~~~
    public static void printSearchResults(List<TreePair> sortList)
    {
        //longest item is 46 characters long, so ensured 1 padding on each side
        //largest number is 3 characters long, so ensured 1 padding on each side
        System.out.format("%-48s%-5s%n", "ITEM", "COUNT");
        int totalCount = 0;
        for(int k = 0; k < sortList.size(); k++)
        {
            totalCount += sortList.get(k).getCount();
            System.out.format("%-48s%-5d%n", sortList.get(k).getItem(), sortList.get(k).getCount());
        }
        System.out.println("AVERAGE SEARCH COUNT: " + (totalCount/42));
    }


    //~~~~~~~~~~~~GRAPH STUFF~~~~~~~~~~~~~~~~~~
    public static ArrayList<String> readGraphFile() throws FileNotFoundException
    {
        Scanner in = new Scanner(new File("graphs1.txt"));
        ArrayList<String> graph1Data = new ArrayList<String>();
        while(in.hasNextLine())
            graph1Data.add(in.nextLine());

        return graph1Data;
    }

    public static ArrayList<Graph> createGraphs(ArrayList<String> graphFile)
    {
        ArrayList<Graph> myGraphs = new ArrayList<Graph>();
        int i = 0;
        boolean inGraph = false;
        while(i < graphFile.size())
        {
            //the "--" is used prior to the naming of the graph and its contents follow
            if(graphFile.get(i).substring(0,2).equalsIgnoreCase("--"))
            {
                inGraph = true;
                ArrayList<ArrayList<Integer>> tempVertex = new ArrayList<ArrayList<Integer>>();
                int vertexStart = 0;

                //vertices all start at value 1, so going to make index 0 null such that they can start at one, except in Zork graph
                if(!graphFile.get(i).contains("Zork"))
                {
                    tempVertex.add(null);
                    vertexStart = 1;
                }

                String graphName = graphFile.get(i).substring(3);  //name starts after the 3 character in the -- line

                i++;
                //want <= so that the last graph can be added when file reaches completion
                while(inGraph && i <= graphFile.size())
                {
                    //end of file, or empty line mean end of graph in graphs1.txt file
                    if(i == graphFile.size() || graphFile.get(i).equalsIgnoreCase(""))
                    {
                        inGraph = false;
                        ArrayList<Vertex> tempVertices = new ArrayList<>();
                        for(int k = vertexStart; k < tempVertex.size(); k++)
                        {
                            tempVertices.add(new Vertex(tempVertex.get(k), k));
                        }
                        myGraphs.add(new Graph(graphName , tempVertices));
                    }

                    //since graphs are created on the line beginning with the name, this means nothing
                    //if graphs could have the same name, then would insert a name checker and if not, create new graph
                    else if(graphFile.get(i).equalsIgnoreCase("new graph"))
                    {
                    }

                    //add vertex appears in the first 10 characters with the number coming after
                    //this function creates a spot for it in the vertex arraylist whose index matches the vertex number
                    else if(graphFile.get(i).substring(0,10).equalsIgnoreCase("add vertex"))
                    {
                        tempVertex.add(new ArrayList<Integer>());
                    }

                    //add edge appears in the first 8 characters with the number coming after
                    //edge is denoted by vertex1 - vertex2 with the hyphen always separating the two
                    //essentially adding each edge number to the other ArrayList
                        // e.g. if add edge 1 - 3 --> would add 3 to 1's edges and would add 3 to 1's edges
                    else if(graphFile.get(i).substring(0,8).equalsIgnoreCase("add edge"))
                    {
                        String edgeCombo = graphFile.get(i).substring(9);
                        String[] edges = edgeCombo.split(" - ");
                        int edge1 = Integer.parseInt(edges[0]);
                        int edge2 = Integer.parseInt(edges[1]);

                        ArrayList<Integer> temp1 = tempVertex.get(edge1);
                        ArrayList<Integer> temp2 = tempVertex.get(edge2);

                        temp1.add(edge2);
                        temp2.add(edge1);
                    }

                    i++;

                }
            }
        }

        return myGraphs;
    }

    //prints the matrix where 1 means there is an edge between the 2 vertices, 0 means none, and - means it's itself and cannot have edge with itself
    public static void printMatrix(Graph myGraph)
    {
        System.out.println("Matrix for " + myGraph.getName());

        int zorkModifier = 0;

        if(!myGraph.getName().contains("Zork"))
        {
            zorkModifier = 1;
        }

        //starts one behind starting index so can get a row on-top with the vertices
        //outer loop prints the vertical numbers while inner loop goes horizontal
            //inner loop is what also does the comparisons
            //since each index is the actual vertex point, can compare indexes instead of getting data
        for(int j = -1 ; j < myGraph.getVertices().size() + zorkModifier; j++)
        {
            if(j == -1)  //to keep everything in line
                System.out.print("\t");
            else if(j == 0 && zorkModifier == 1)
            {
            }
            else
                System.out.print("\t" + j + "|");

            for(int k = 0 + zorkModifier; k <  myGraph.getVertices().size() + zorkModifier; k++)
            {
                ArrayList<Integer> theseEdges;
                if(zorkModifier == 0)
                    theseEdges = myGraph.getVertices().get(k).getEdges();
                else
                    theseEdges = myGraph.getVertices().get(k - 1).getEdges();

                if(j == -1)  //for header
                    System.out.print("\t" + k);
                else if(j == 0 && zorkModifier == 1)
                {
                }
                //if they are the same vertex, denote with "-"
                else if(j == k)
                    System.out.print("\t-");
                //if the value of the vertex is
                else if(theseEdges.contains(j))
                    System.out.print("\t1");
                else if(!theseEdges.contains(j))
                    System.out.print("\t0");
                else
                    System.out.print("\tE");
            }
            System.out.println("");
        }
    }

    //print the adjacency list for all vertices, regardless if they have edges
    public static void printAdjacencyList(Graph myGraph)
    {

        System.out.println("Adjacency List for " + myGraph.name);
        //goes through each vertex and prints it prior to checking to see if any edges
        for(int i = 0; i < myGraph.getVertices().size(); i++)
        {
            int vertexName = myGraph.getVertices().get(i).getId();
            String results = "[" + vertexName + "] ";
            ArrayList<Integer> theseEdges = myGraph.getVertices().get(i).getEdges();

            //if edges do exist for this vertex, print them out here
            for(int j = 0; j < theseEdges.size(); j++)
                results += ("\t" + theseEdges.get(j));

            System.out.println(results);
        }
    }


    /*
    public static void breadthFirstSearch(Vertex fromVertex)
    {
        myQueue = new Queue();
        myQueue.enqueue(fromVertex);
        fromVertex.setProcessed(true);

        while(!myQueue.isEmpty())
        {
            currentVertex = myQueue.dequeue();
            System.out.println(currentVertex.getId());
            for(Vertex n : fromVertex.getEdges())
                if(!n.getProcessed)
                {
                    myQueue.enqueue(n);
                    n.setProcessed(true);
                }
       }
    }
     */


    public static void graphComplete() throws FileNotFoundException
    {
        ArrayList<String> graphFile = readGraphFile();
        ArrayList<Graph> myGraphs = createGraphs(graphFile);

        for(Graph f : myGraphs)
        {
            //    System.out.println("");
            //    System.out.println(f);
            //    System.out.println(f.name);
            //    System.out.println(f.vertex);
            printMatrix(f);
            System.out.println(" ");
            printAdjacencyList(f);
        }
    }


}