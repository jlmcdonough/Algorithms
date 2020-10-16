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
    public static void main(String args[]) throws FileNotFoundException
    {
            ArrayList<String> mySortedMagicItems = getAndSortList();

            ArrayList<String> myMagicRandom = new ArrayList<>();      //mutated by random items
            myMagicRandom = (ArrayList)mySortedMagicItems.clone();
            String[] randomItems = generateRandomItems(myMagicRandom);

            linearSortComplete(mySortedMagicItems, randomItems);

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            binarySortComplete(mySortedMagicItems, randomItems);

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

    public static ArrayList<String> getAndSortList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("magicitems.txt"));
        ArrayList<String> myMagicItems = new ArrayList<String>();  //will be the sorted list

        while (reader.hasNextLine())
            myMagicItems.add(reader.nextLine());

        mergeSort(myMagicItems, myMagicItems.size());

        return myMagicItems;
    }

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

    //~~~~~~~~~~~~~~~~~LINEAR SORT~~~~~~~~~~~~~~~~~~~~~~~~~
    public static ArrayList<Assignment3> linearSort(List<String> myList, String item, ArrayList<Assignment3> linearResultsGroup)
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
        linearResultsGroup.add(sortedPair);
        return linearResultsGroup;
    }

    public static void linearSortComplete(List<String> magicItems, String[] randomItems)
    {
        ArrayList<Assignment3> linearResultsGroup = new ArrayList<Assignment3>();
        for(int j = 0; j < randomItems.length; j++)
        {
            linearResultsGroup = linearSort(magicItems, randomItems[j], linearResultsGroup);
        }

        System.out.println("LINEAR SEARCH\n");
        printSearchResults(linearResultsGroup);
    }

    //~~~~~~~~~~~~~~~~~~BINARY SORT~~~~~~~~~~~~~~~~~~~
    static int binarySearchCount = 0;
    public static ArrayList<Assignment3> binarySort(List<String> magicItems, String item, int startIndex, int stopIndex, ArrayList<Assignment3> binaryResultsGroup)
    {
        int middleIndex = (int)((startIndex + stopIndex) / 2);  //floor value of uneven splits
        binarySearchCount++;

        if(startIndex > stopIndex)
            System.out.print("ITEM NOT FOUND");
        else if(magicItems.get(middleIndex).equalsIgnoreCase(item))
        {
            binaryResultsGroup.add(new Assignment3(item, binarySearchCount));
            binarySearchCount = 0;
        }
        else if(magicItems.get(middleIndex).compareToIgnoreCase(item) > 0) //search item is closer to aaaa then mid-item
            binarySort(magicItems, item, startIndex, middleIndex - 1, binaryResultsGroup);
        else
            binarySort(magicItems, item, middleIndex + 1, stopIndex, binaryResultsGroup);

        return binaryResultsGroup;
    }

    public static void binarySortComplete(List<String> magicItems, String[] randomItems)
    {
        ArrayList<Assignment3> binaryResultsGroup = new ArrayList<Assignment3>();
        for(int j = 0; j < randomItems.length; j++)
        {
            binaryResultsGroup = binarySort(magicItems, randomItems[j], 0, magicItems.size(), binaryResultsGroup);
        }

        System.out.println("BINARY SEARCH\n");
        printSearchResults(binaryResultsGroup);
    }
    
    
    public static void printSearchResults(List<Assignment3> sortList)
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
