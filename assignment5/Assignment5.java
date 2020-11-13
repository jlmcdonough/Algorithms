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

public class Assignment5
{
    public static void main(String args[]) throws FileNotFoundException
    {
        ArrayList<Spice> mySpices = getAndSortList();

        for(Spice spices : mySpices)
            System.out.println(spices.getName() + " " + spices.getUnitCost());

        for(Integer i : knaps)
            System.out.println(i);
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

        public double getUnitCost()
        {
            return this.unitCost;
        }
    }




    static ArrayList<Integer> knaps = new ArrayList<Integer>();

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
            }
            else if(thisLine.substring(0,8).equalsIgnoreCase("knapsack"))
            {
                String results = thisLine.split(" = ")[1];
                results = results.trim();
                int cap = Integer.parseInt(results.split(";")[0]);
                knaps.add(cap);
            }
        }

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