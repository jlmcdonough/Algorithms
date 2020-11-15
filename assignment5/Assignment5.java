/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Assignment5
{
    public static void main(String args[]) throws FileNotFoundException
    {
        knapsackComplete();

        /*
        ArrayList<String> graphFile = readGraphFile();
        ArrayList<Graph> myGraphs = createGraphs(graphFile);
        System.out.println(myGraphs.get(0).getThisVertex(1).getEdges().get(0).getWeight()); //should print graph 1 vertex 1 weight going to 2 (which is 6)
        System.out.println(myGraphs.get(0).getThisVertex(1).getThisEdge(2).getWeight()); //should print graph 1 vertex 1 weight going to 2 (which is 6)

        Graph g = myGraphs.get(0);
        for(Vertex v : g.getVertices())
        {
            for(Edge e : v.getEdges())
            {
                System.out.println("V: " + v.getId() + " Source V: " +  e.getSourceVertex() + " E DEST: " + e.getDestVertex() + " WEIGHT: " + e.getWeight());
            }
        } */
    }



    static class Spice
    {
        private String name;
        private double totalPrice;
        private int quantity;
        private double unitCost;

        public Spice(String n, double p, int q)
        {
            this.name = n;
            this.totalPrice = p;
            this.quantity = q;
            this.unitCost = p / q;
        }

        //used as result when going through knapsack
        public Spice(String n, double u)
        {
            this.name = n;
            this.unitCost = u;
        }

        public String getName()
        {
            return this.name;
        }

        public double getTotalPrice()
        {
            return this.totalPrice;
        }

        public int getQuantity()
        {
            return this.quantity;
        }

        public void removeQuantity(int qA)
        {
            this.quantity -= qA;
        }

        public double getUnitCost()
        {
            return this.unitCost;
        }
    }

    //holds the capacity of the knapsacks
    static ArrayList<Integer> knaps = new ArrayList<Integer>();

    //holds the spice list and this is the list that is never modified and used to repopulate the mutated list
    static ArrayList<Spice> constantSpice = new ArrayList<Spice>();

    //reads the text file and creates the spices and knapsack capacity 
    public static ArrayList<Spice> getAndSortList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("spice.txt"));
        ArrayList<Spice> mySpices = new ArrayList<Spice>();
        while (reader.hasNextLine())
        {
            String thisLine = reader.nextLine();
            //blank space lines that appear or commented lines
            if(thisLine.equalsIgnoreCase("") || thisLine.substring(0,2).equalsIgnoreCase("--"))
            {
            }
            //lines with spice and its contents being with spice and have name, total price, and quantity separated by ';'
            else if(thisLine.substring(0,5).equalsIgnoreCase("spice"))
            {
                String[] results = thisLine.split(";");
                String name = results[0].split(" = ")[1];
                double tPrice = Double.parseDouble(results[1].split(" = ")[1]);
                int qty = Integer.parseInt(results[2].split(" = ")[1]);
                Spice thisSpice = new Spice(name, tPrice, qty);
                mySpices.add(thisSpice);
                //create two lists such that one is immutable and can be used to repopulate the mutated one after removing elements to fill a knapsack
                Spice constSpice = new Spice(name, tPrice, qty);
                constantSpice.add(constSpice);
            }
            //lines with knapsack capacity begin with knapsack and capacity is separated by '=' and lines end with ';'
            else if(thisLine.substring(0,8).equalsIgnoreCase("knapsack"))
            {
                String results = thisLine.split(" = ")[1];
                results = results.trim();
                int cap = Integer.parseInt(results.split(";")[0]);
                knaps.add(cap);
            }
        }

        selectionSort(constantSpice);
        selectionSort(mySpices);
        return mySpices;
    }

    //fills each knapsack up
    public static ArrayList<ArrayList<Spice>> fillKnapsacks(ArrayList<Spice> mutableSpice)
    {
        ArrayList<ArrayList<Spice>> allResults = new ArrayList<ArrayList<Spice>>();
        for(int i = 0; i < knaps.size(); i++)
        {
            int capacity = knaps.get(i);

            //holds the contents of the knapsack
            ArrayList<Spice> results = new ArrayList<Spice>();

            while(capacity > 0 && mutableSpice.size() != 0)
            {
                //if the remaining quantity of spices in index 0 is 0, then remove that spice from the list
                if(mutableSpice.get(0).getQuantity() == 0)
                {
                    mutableSpice.remove(0);
                    capacity++;   //done to negate this cycle in the while-loop
                }
                //subtract a spice from the quantity of that at index 0 as well as adding this spice to the contents of the knapsack
                else
                {
                    Spice thisSpice = mutableSpice.get(0);
                    String name = thisSpice.getName();
                    double worth = thisSpice.getUnitCost();
                    thisSpice.removeQuantity(1);
                    results.add(new Spice(name, worth));
                }
                capacity--;
            }

            allResults.add(results);

            mutableSpice = resetSpice(constantSpice);
        }

        return allResults;
    }

    //resets the values of all the spices to their original quantity
    public static ArrayList<Spice> resetSpice(ArrayList<Spice> cSpice)
    {
        ArrayList<Spice> newSpice = new ArrayList<Spice>();
        for(Spice s : cSpice)
        {
            newSpice.add(new Spice(s.getName(), s.getTotalPrice(), s.getQuantity()));
        }
        return newSpice;
    }

    //get the header of each knapsack - includes capacity and worth
    public static String individualKnapResult(ArrayList<Spice> knapSpice, int knapCap)
    {
        double sumPrice = 0;
        ArrayList<String> colors = new ArrayList<String>();

        //keep track of the total value of the contents as well as each color that appears
        for(Spice s : knapSpice)
        {
            sumPrice += s.getUnitCost();
            colors.add(s.getName());
        }

        String colorSequence = colorStatement(colors);
        String result = "Knapsack of capacity " + knapCap + " is worth " + sumPrice + " quatloos and contains " + colorSequence + ".";
        return result;
    }

    //creates the tail for the knapsack output - includes numbers of each color
    public static String colorStatement(ArrayList<String> colorList)
    {
        String colorSequence = "";

        //color and colorCount are two separate arrays but the indices line up with each other such that element 0 is both the color and the amount of times the color appears
        ArrayList<String> color = new ArrayList<String>();
        ArrayList<Integer> colorCount = new ArrayList<Integer>();

        for(String s : colorList)
        {
            //if color has not been previously used, add an entry for it in the colorList with a counter of 1
            if(!color.contains(s))
            {
                color.add(s);
                int indexOfColor = color.indexOf(s);
                colorCount.add(indexOfColor, 1);
            }
            //color has already appear, so dont need a new instance, just increase the counter that goes with it
            else
            {
                int indexOfColor = color.indexOf(s);
                colorCount.set(indexOfColor, colorCount.get(indexOfColor)+1);
            }
        }

        for(int i = 0; i < color.size() - 1; i++)
            colorSequence += colorCount.get(i) + " of " + color.get(i) + ", ";

        //want last element to end in a period and not a comma, as well as putting the 'and' before it
        colorSequence += "and " + colorCount.get(colorCount.size() - 1) + " of " + color.get(color.size() - 1);

        return colorSequence;
    }

    //prints the contents of each knapsack
    public static void printKnapsack(ArrayList<ArrayList<Spice>> allResults)
    {
        for(int i = 0; i < allResults.size(); i++)
            System.out.println(individualKnapResult(allResults.get(i), knaps.get(i)));
    }

    //does all the necessary work for the knapsack part of assignment
    public static void knapsackComplete() throws FileNotFoundException
    {
        ArrayList<Spice> mySpices = getAndSortList();
        ArrayList<Spice> mutableSpice = new ArrayList<Spice>(mySpices);
        ArrayList<ArrayList<Spice>> filled = fillKnapsacks(mutableSpice);
        printKnapsack(filled);
    }

//~~~~~~~~~~~~~~~~~~~~SELECTION SORT FROM ASSIGNMENT 2~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static int selectionSort(ArrayList<Spice> itemList)
    {
        int comparisonCount = 0;
        for(int i = 0; i < itemList.size(); i++)
        {
            int smallestPos = i;
            for(int j = i + 1; j < itemList.size(); j++)
            {
                comparisonCount++;   //if enable other comparison count, and make this one +2 instead of +1, it would equal n^2 at the end
                if(itemList.get(smallestPos).getUnitCost() < (itemList.get(j)).getUnitCost())
                    smallestPos = j;
            }
            swap(itemList, i, smallestPos);
        }
        return comparisonCount;
    }

    public static void swap(ArrayList<Spice> itemList, int lowestPos, int swaperPos)
    {
        Spice temp = itemList.get(lowestPos);
        itemList.set(lowestPos, itemList.get(swaperPos));  //take what is in the higher spot, and put it at the lowest index
        itemList.set(swaperPos, temp);   //take what used to be in lowestPos and put it in the higher spot
    }

//~~~~~~~~~~~~~~~~~~~~GRAPH AND VERTEX CLASS FROM ASSIGNMENT 4~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
    }

    static class Vertex
    {
        private ArrayList<Edge> edges;
        private boolean processed;
        private int id;

        public Vertex(ArrayList<Edge> e, int i)
        {
            this.edges = e;
            this.processed = false;
            this.id = i;
        }

        public ArrayList<Edge> getEdges()
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

        public void addEdge(int s, int e, int w)
        {
            this.edges.add(new Edge(s, e, w));
        }

        public Edge getThisEdge(int id)
        {
            for(Edge e : this.getEdges())
            {
                if(e.getDestVertex() == id)
                    return e;
            }

            return null;
        }
    }

    static class Edge
    {
        private int sourceVertex;
        private int destVertex;
        private int weight;

        public Edge(int s, int d, int w)
        {
            this.sourceVertex = s;
            this.destVertex = d;
            this.weight = w;
        }

        public int getSourceVertex()
        {
            return this.sourceVertex;
        }

        public int getDestVertex()
        {
            return this.destVertex;
        }

        public int getWeight()
        {
            return this.weight;
        }
    }

    public static ArrayList<String> readGraphFile() throws FileNotFoundException
    {
        Scanner in = new Scanner(new File("graphs2.txt"));
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
            if(graphFile.get(i).substring(0,2).equalsIgnoreCase("--"))                      //the "--" is used prior to the naming of the graph and its contents follow
            {
                inGraph = true;
                ArrayList<Vertex> tempVertex = new ArrayList<Vertex>();
                String graphName = graphFile.get(i).substring(3);  //name starts after the 3 character in the -- line

                i++;
                while(inGraph && i <= graphFile.size())                                                //want <= so that the last graph can be added when file reaches completion
                {
                    if(i == graphFile.size() || graphFile.get(i).equalsIgnoreCase(""))     //end of file, or empty line mean end of graph in graphs1.txt file
                    {
                        inGraph = false;
                        myGraphs.add(new Graph(graphName , tempVertex));
                    }
                    else if(graphFile.get(i).equalsIgnoreCase("new graph"))
                    {
                    }
                    else if(graphFile.get(i).substring(0,10).equalsIgnoreCase("add vertex"))
                    {
                        tempVertex.add(new Vertex(new ArrayList<Edge>(), Integer.parseInt(graphFile.get(i).substring(11))));
                    }
                    else if(graphFile.get(i).substring(0,8).equalsIgnoreCase("add edge"))
                    {
                        String edgeCombo = graphFile.get(i).substring(9);
                        String[] edges = edgeCombo.split(" - ");
                        int sourceEdge = Integer.parseInt(edges[0]);
                        edges[1] = edges[1].replaceAll("  ", " ");  //graph 1 has two spaces before its weight, this makes all weights separated by one space from destination vertex
                        String[] destinationAndWeight = edges[1].split(" ");
                        int destination = Integer.parseInt(destinationAndWeight[0]);
                        int weight = Integer.parseInt(destinationAndWeight[1]);
                        Vertex temp1 = null;

                        for(int v1 = 0; v1 < tempVertex.size(); v1++)          //gets the vertex identified by the edge1 vertex, and adds the vertex that forms edge2 to its edgeList
                        {
                            if(tempVertex.get(v1).getId() == sourceEdge)
                                temp1 = tempVertex.get(v1);
                        }

                        temp1.addEdge(sourceEdge, destination, weight);
                    }

                    i++;
                }
            }
        }

        return myGraphs;
    }

}