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
        ArrayList<Spice> mySpices = getAndSortList();
        ArrayList<Spice> mutableSpice = new ArrayList<Spice>(mySpices);


        for(Spice spices : mySpices)
            System.out.println(spices.getName() + " " + spices.getUnitCost());

        System.out.println(" ");

        for(Integer i : knaps)
            System.out.println(i);

        System.out.println(" ");

        ArrayList<Spice> results = new ArrayList<Spice>();
        ArrayList<ArrayList<Spice>> allResults = new ArrayList<ArrayList<Spice>>();
        for(int i = 0; i < knaps.size(); i++)
        {
            int capacity = knaps.get(i);
            System.out.println("I is " + i + " CAP IS " + capacity);

            while(capacity > 0)
            {
//                System.out.println(mutableSpice.get(0).getQuantity());
                if(mutableSpice.size() == 0)
                    allResults.add(results);
                else if(mutableSpice.get(0).getQuantity() == 0)
                {
                    mutableSpice.remove(0);
                    capacity++;   //done to negate this cycle in the for-loop
                }
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

            for(Spice spices : mutableSpice)
                System.out.println(spices.getName() + " " + spices.getQuantity());
            System.out.println(" ");
            mutableSpice = resetSpice(constantSpice);
            for(Spice spices : mutableSpice)
                System.out.println(spices.getName() + " " + spices.getQuantity());
            System.out.println("---------- ");
        }

        for(int i = 0; i < allResults.size(); i++)
        {
            int j = 0;
            while(allResults.get(i).size() > j)
            {
                System.out.println("SPICE: " + allResults.get(i).get(j).getName());
                j++;
            }
        }
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

    public static ArrayList<Spice> resetSpice(ArrayList<Spice> cSpice)
    {
        ArrayList<Spice> newSpice = new ArrayList<Spice>();
        for(Spice s : cSpice)
        {
            newSpice.add(new Spice(s.getName(), s.getTotalPrice(), s.getQuantity()));
        }
        return newSpice;
    }

    static ArrayList<Integer> knaps = new ArrayList<Integer>();
    static ArrayList<Spice> constantSpice = new ArrayList<Spice>();
    public static ArrayList<Spice> getAndSortList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("spice.txt"));
        ArrayList<Spice> mySpices = new ArrayList<Spice>();
        while (reader.hasNextLine())
        {
            String thisLine = reader.nextLine();
            if(thisLine.equalsIgnoreCase(""))
            {
            }
            else if(thisLine.substring(0,2).equalsIgnoreCase("--"))
            {

            }
            else if(thisLine.substring(0,5).equalsIgnoreCase("spice"))
            {
                String[] results = thisLine.split(";");
                String name = results[0].split(" = ")[1];
                double tPrice = Double.parseDouble(results[1].split(" = ")[1]);
                int qty = Integer.parseInt(results[2].split(" = ")[1]);
                Spice thisSpice = new Spice(name, tPrice, qty);
                mySpices.add(thisSpice);
                Spice constSpice = new Spice(name, tPrice, qty);
                constantSpice.add(constSpice);
            }
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

}