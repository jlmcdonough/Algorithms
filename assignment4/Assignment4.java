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

        System.out.println(" ");

        graphComplete();

    }


    //~~~~~~~~~~~~~~~~~ASSIGNMENT VARIABLES AND RESULT CONSTRUCTOR~~~~~~~~~
    private int searchCount;
    private String searchItem;
    public Assignment4(String item, int count)
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

    public static ArrayList<Assignment4> searchTree(Tree myBinaryTree, String[] randomItems)
    {
        ArrayList<Assignment4> binaryTreeResults = new ArrayList<Assignment4>();
        for(String s : randomItems)
        {
            myBinaryTree.treeSearch(myBinaryTree.root, s);
            binaryTreeResults.add(new Assignment4(s, count));
            count = 0;
        }

        return binaryTreeResults;
    }

    public static void binaryTreeComplete(List<String> magicItems, String[] randomItems)
    {
        Tree myBinaryTree = createAndFillTree(magicItems);
        ArrayList<Assignment4> results = searchTree(myBinaryTree, randomItems);
        printSearchResults(results);
    }

    //~~~~~~~~~~~~~~~~~~PRINT RESULTS~~~~~~~~~~~~~~~~~~~
    public static void printSearchResults(List<Assignment4> sortList)
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
    static class Graph
    {
        private ArrayList<ArrayList<Integer>> vertex;
        private ArrayList<Integer> edge;
        private String name;

        public Graph(String n, ArrayList<ArrayList<Integer>> v)
        {
            this.name = n;
            this.vertex = v;
        }

    }

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

            if(graphFile.get(i).substring(0,2).equalsIgnoreCase("--"))
            {
                inGraph = true;
                ArrayList<ArrayList<Integer>> vertex = new ArrayList<ArrayList<Integer>>();
                if(!graphFile.get(i).contains("Zork"))
                    vertex.add(null);   //vertices all start at value 1, so going to make index 0 null such that they can start at one, except in Zork graph
                ArrayList<Integer> edge = new ArrayList<Integer>();
                String graphName = graphFile.get(i).substring(3);

                i++;

                while(inGraph && i <= graphFile.size())
                {
                    if(i == graphFile.size() || graphFile.get(i).equalsIgnoreCase(""))
                    {
                        inGraph = false;
                        myGraphs.add(new Graph(graphName ,vertex));
                    }
                    else if(graphFile.get(i).equalsIgnoreCase("new graph"))
                    {
                    }
                    else if(graphFile.get(i).substring(0,10).equalsIgnoreCase("add vertex"))
                    {
                        vertex.add(new ArrayList<Integer>());
                    }
                    else if(graphFile.get(i).substring(0,8).equalsIgnoreCase("add edge"))
                    {
                        String edgeCombo = graphFile.get(i).substring(9);
                        String[] edges = edgeCombo.split(" - ");
                        int edge1 = Integer.parseInt(edges[0]);
                        int edge2 = Integer.parseInt(edges[1]);

                        ArrayList<Integer> temp1 = vertex.get(edge1);
                        ArrayList<Integer> temp2 = vertex.get(edge2);

                        temp1.add(edge2);
                        temp2.add(edge1);
                    }

                    i++;

                }
            }
        }

        return myGraphs;
    }

    public static void printMatrix(Graph myGraph)
    {
        int startingIndex;
        if(myGraph.name.contains("Zork"))   //Zork has starting vertex of 0, rest at 1
            startingIndex = 0;
        else
            startingIndex = 1;

        System.out.println("Matrix for " + myGraph.name);
        for(int j = startingIndex - 1; j < myGraph.vertex.size(); j++)
        {
            if(j == -1)  //to keep everything in line
                System.out.print("\t");
            else if(j == 0 && !myGraph.name.contains("Zork"))  //same as above, but for non-Zork graphs
                System.out.print("\t");
            else
                System.out.print("\t" + j + "|");
            for(int k = startingIndex; k <  myGraph.vertex.size(); k++)
            {
                ArrayList<Integer> theseEdges = myGraph.vertex.get(k);
                if(j == -1)  //for header
                    System.out.print("\t" + k);
                else if(j == 0 && !myGraph.name.contains("Zork"))
                    System.out.print("\t" + k);
                else if(j == k)
                    System.out.print("\t-");
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

    public static void printAdjacencyList(Graph myGraph)
    {
        int startingIndex;
        if(myGraph.name.contains("Zork"))
            startingIndex = 0;
        else
            startingIndex = 1;

        System.out.println("Adjacency List for " + myGraph.name);
        for(int i = startingIndex; i < myGraph.vertex.size(); i++)
        {
            String results = "[" + i + "] ";
            ArrayList<Integer> theseEdges = myGraph.vertex.get(i);
            for(int j = 0; j < theseEdges.size(); j++)
                results += ("\t" + theseEdges.get(j));
            System.out.println(results);
        }
    }


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
