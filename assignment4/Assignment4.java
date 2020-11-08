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


        ArrayList<String> myMagicItems = getList();
        String[] myRandomItems = generateRandomItems(myMagicItems);

        binaryTreeComplete(myMagicItems, myRandomItems);

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        graphComplete();

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

    //used to hold a graph which contains an ArrayList of vertices where each index is an arraylist of edges
    static class Graph extends Queue
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

        ArrayList<String> dFSResults = new ArrayList<String>();
        public void depthFirstSearch(Vertex fromVertex)
        {
            if(!fromVertex.getProcessed())
            {
                dFSResults.add(String.valueOf(fromVertex.getId()));
                fromVertex.setProcessed(true);
            }

            for(int i = 0; i < fromVertex.getEdges().size(); i++)
            {
                int n = fromVertex.getEdges().get(i);
                Vertex v = this.getThisVertex(n);         //getting the vertex from the id that is provided by the for-loop

                if(!v.getProcessed())
                    depthFirstSearch(v);
            }
        }

        public void visitDisconnectedDFS()
        {
            for(Vertex checkProcess : this.getVertices())
            {
                if(!checkProcess.getProcessed())
                {
                    dFSResults.add("|");
                    depthFirstSearch(checkProcess);
                }
            }
        }

        public ArrayList<String> getDFSResults()
        {
            return dFSResults;
        }

        ArrayList<String> bFSResults = new ArrayList<String>();
        public void breadthFirstSearch(Vertex fromVertex)
        {
            Queue myQueue = new Queue();
            myQueue.enqueue(String.valueOf(fromVertex.getId()));   //passing the vertex id as a string into queue
            fromVertex.setProcessed(true);

            while(!myQueue.isEmpty())
            {
                Node currIndex = myQueue.dequeue();      //dequeue returns a Node whose data is the string of vertex id
                bFSResults.add(currIndex.data);
                Vertex currentVertex = this.getThisVertex(Integer.parseInt(currIndex.data));    //turn the String id into an Int and then get the vertex with that id as a name

                for(int i = 0; i < currentVertex.getEdges().size(); i++)
                {
                    int n = currentVertex.getEdges().get(i);
                    Vertex v = this.getThisVertex(n);              //getting the vertex from the id that is provided by the for-loop

                    if(!v.getProcessed())
                    {
                        myQueue.enqueue(String.valueOf(n));
                        v.setProcessed(true);
                    }
                }
            }
        }

        public void visitDisconnectedBFS()
        {
            for(Vertex checkProcess : this.getVertices())
            {
                if(!checkProcess.getProcessed())
                {
                    bFSResults.add("|");
                    breadthFirstSearch(checkProcess);
                }
            }
        }

        public ArrayList<String> getBFSResults()
        {
            return bFSResults;
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

        public void addEdge(int e)
        {
            this.edges.add(e);
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
                ArrayList<Vertex> tempVertex = new ArrayList<Vertex>();
                String graphName = graphFile.get(i).substring(3);  //name starts after the 3 character in the -- line

                i++;
                //want <= so that the last graph can be added when file reaches completion
                while(inGraph && i <= graphFile.size())
                {
                    //end of file, or empty line mean end of graph in graphs1.txt file
                    if(i == graphFile.size() || graphFile.get(i).equalsIgnoreCase(""))
                    {
                        inGraph = false;
                        myGraphs.add(new Graph(graphName , tempVertex));
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
                        tempVertex.add(new Vertex(new ArrayList<Integer>(), Integer.parseInt(graphFile.get(i).substring(11))));
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
                        Vertex temp1 = null;
                        Vertex temp2 = null;

                        for(int v1 = 0; v1 < tempVertex.size(); v1++)
                        {
                            if(tempVertex.get(v1).getId() == edge1)
                                temp1 = tempVertex.get(v1);
                        }

                        for(int v2 = 0; v2 < tempVertex.size(); v2++)
                        {
                            if(tempVertex.get(v2).getId() == edge2)
                                temp2 = tempVertex.get(v2);
                        }

                        temp1.addEdge(edge2);
                        temp2.addEdge(edge1);
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

        ArrayList<Vertex> theseVertices = myGraph.getVertices();

        //Prints upper row of matrix
        System.out.print("\t");
        for(int header = 0; header < theseVertices.size(); header++)
            System.out.print("\t" + theseVertices.get(header).getId());
        System.out.println();

        //outer loop prints the vertical numbers while inner loop goes horizontal
        //inner loop is what also does the comparisons
        //since each index is the actual vertex point, can compare indexes instead of getting data
        for(int j = 0 ; j < theseVertices.size(); j++)
        {
            System.out.print("\t" + theseVertices.get(j).getId() + "|");

            for(int k = 0; k <  theseVertices.size(); k++)
            {
                ArrayList<Integer> theseEdges = theseVertices.get(k).getEdges();
                //if they are the same vertex, denote with "-"
                if(j == k)
                    System.out.print("\t-");
                //if the value of the vertex in question (denoted by row therefore j) is present in the the vertex denoted by the columns (therefore k)
                else if(theseEdges.contains(theseVertices.get(j).getId()))
                    System.out.print("\t1");
                //if the value is not present
                else if(!theseEdges.contains(theseVertices.get(j).getId()))
                    System.out.print("\t0");
                //something else to denote if something goes awry, which has yet to
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

    public static void resetProcessed(Graph g)
    {
        for(Vertex v : g.getVertices())
            v.setProcessed(false);
    }


    public static void printTraversal(ArrayList<String> results)
    {
        for(int s = 0; s < results.size() - 1; s++)
        {
            if(results.get(s).equalsIgnoreCase("|"))
                System.out.println("");
            else if(results.get(s + 1).equalsIgnoreCase("|"))
                System.out.print(results.get(s));
            else
                System.out.print(results.get(s) + ", ");
        }
        System.out.print(results.get(results.size() - 1));
    }

    public static void graphComplete() throws FileNotFoundException
    {
        ArrayList<String> graphFile = readGraphFile();
        ArrayList<Graph> myGraphs = createGraphs(graphFile);

        for(Graph f : myGraphs)
        {
            printMatrix(f);
            System.out.println(" ");

            printAdjacencyList(f);
            System.out.println(" ");

            System.out.println("Depth First Search for " + f.name);
            f.depthFirstSearch(f.getVertices().get(0));
            f.visitDisconnectedDFS();
            printTraversal(f.getDFSResults());
            System.out.println(" ");

            resetProcessed(f);

            System.out.println("Breadth First Search for " + f.name);
            f.breadthFirstSearch(f.getVertices().get(0));
            f.visitDisconnectedBFS();
            printTraversal(f.getBFSResults());
            System.out.println(" ");

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }


}