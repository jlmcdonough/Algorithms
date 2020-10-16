/*
Joseph McDonough
CMPT 435
Professor Labouseur
30 October 2020
 */

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Assignment3
{
    public static void main(String args[])
    {
        try
        {
            Scanner reader = new Scanner(new File("magicitems.txt"));
            ArrayList<String> myMagicItems = new ArrayList<String>();  //will be the sorted list
            ArrayList<String> myMagicLinear = new ArrayList<>();      //mutated by random items

            while (reader.hasNextLine())
                myMagicItems.add(reader.nextLine());

            mergeSort(myMagicItems, myMagicItems.size());
            myMagicLinear = (ArrayList)myMagicItems.clone();  //create a cl

            String[] randomItems = new String[42];
            Random rand = new Random();
            for(int i = 0; i < randomItems.length; i++)  //assignment wants 42 random items
            {
                int randIndex = rand.nextInt(myMagicLinear.size());  //choosing myMagicItems so can remove items without messing up search
                randomItems[i] = myMagicLinear.get(randIndex);
                myMagicLinear.remove(randIndex);  //dont want to get duplicates so removing once its gotten
            }

            ArrayList<Assignment3> linearSortedGroup = new ArrayList<Assignment3>();
            for(int j = 0; j < randomItems.length; j++)
            {
                linearSortedGroup = linearSort(myMagicItems, randomItems[j], linearSortedGroup);
            }

            //longest item is 46 characters long, so ensured 1 padding on each side
            //largest number is 3 characters long, so ensured 1 padding on each side
            System.out.println("LINEAR SEARCH\n");
            System.out.format("%-48s%-5s%n", "ITEM", "COUNT");
            int linearTotalCount = 0;
            for(int k = 0; k < linearSortedGroup.size(); k++)
            {
                linearTotalCount += linearSortedGroup.get(k).getCount();
                System.out.format("%-48s%-5d%n", linearSortedGroup.get(k).getItem(), linearSortedGroup.get(k).getCount());
            }
            System.out.println("AVERAGE SEARCH COUNT: " + (linearTotalCount/42));
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    //object that holds search item and counts taken to find it as a pair
    private int searchCount = 0;
    private String searchItem = "";

    public Assignment3(String item, int count)
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


    public static ArrayList<Assignment3> linearSort(List<String> myList, String item, ArrayList<Assignment3> linearCountArray)
    {
        int i = 0;
        boolean found = false;
        int linearSortCount = 0;

        while(i < myList.size() && !found)
        {
            linearSortCount++;
            if(myList.get(i).equalsIgnoreCase(item))
                found = true;

            i++;
        }

        Assignment3 sortedPair = new Assignment3(item, linearSortCount);
        linearCountArray.add(sortedPair);
        return linearCountArray;
    }






    //since merge sort performed the least amount of comparisons in assignment2, will use it here to sort
    public static List<String> mergeSort(List<String> items, int itemsSize)
    {
        if (itemsSize > 1)
        {
            int midpoint = itemsSize / 2;
            List<String> leftItems = Arrays.asList(new String[midpoint]); //333 on first pass
            List<String> rightItems = Arrays.asList(new String[itemsSize - midpoint]);  //666-333 = 333 on first pass

            for (int i = 0; i < midpoint; i++)      //[0,333) on first pass
                leftItems.set(i, items.get(i));

            for (int j = midpoint; j < itemsSize; j++)          //[333-666) on first pass
                rightItems.set(j - midpoint, items.get(j));

            mergeSort(leftItems, midpoint);
            mergeSort(rightItems, itemsSize - midpoint);

            merge(items, leftItems, rightItems, midpoint, itemsSize - midpoint);
        }

        return items;
    }

    public static void merge(List<String> temp, List<String> leftItems, List<String> rightItems, int left, int right) {

        int leftInc = 0;  //keeps track of distance of middle (0 being farthest away)
        int rightInc = 0; //keeps track of distance from end of array
        int tempInc = 0; //keeps track of current variable in temp(sorted) array

        while (leftInc < left && rightInc < right)
        {
            if (leftItems.get(leftInc).compareToIgnoreCase(rightItems.get(rightInc)) < 0)
                temp.set(tempInc++, leftItems.get(leftInc++));
            else
                temp.set(tempInc++, rightItems.get(rightInc++));
        }

        while (leftInc < left)
            temp.set(tempInc++, leftItems.get(leftInc++));


        while (rightInc < right)
            temp.set(tempInc++, rightItems.get(rightInc++));
    }
}
