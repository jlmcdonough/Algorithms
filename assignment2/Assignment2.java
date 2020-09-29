/*
Joseph McDonough
CMPT 435
Professor Labouseur
2 October 2020
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Assignment2
{
    public static void main(String args[])
    {
        try
        {
            Scanner reader = new Scanner(new File("magicitems.txt"));
            List<String> myMagicItemsSelection = new ArrayList<String>();   //holds list of magic items as per text file
            List<String> myMagicItemsInsertion = new ArrayList<String>();   //holds list of magic items as per text file
            List<String> myMagicItemsMerge = new ArrayList<String>();   //holds list of magic items as per text file
            List<String> myMagicItemsQuick = new ArrayList<String>();   //holds list of magic items as per text file

            //adds each magic items into the magic item list
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                myMagicItemsSelection.add(item);
                myMagicItemsInsertion.add(item);
                myMagicItemsMerge.add(item);
                myMagicItemsQuick.add(item);
            }


            System.out.println("\nSelection Count: " +selectionSort(myMagicItemsSelection)); //prints comparison count
            System.out.println( myMagicItemsSelection);

            System.out.println("\nInsertion Count: " + insertionSort(myMagicItemsInsertion)); //prints comparison count
            System.out.println( myMagicItemsInsertion);

            System.out.println("\nMerge Count: " + mergeSort(myMagicItemsMerge, myMagicItemsMerge.size())); //prints comparison count
            System.out.println(myMagicItemsMerge);

            System.out.println("\nQuick Count: " + quickSort(myMagicItemsQuick)); //prints comparison count
            System.out.println(myMagicItemsQuick);

            //Test to make sure all lists are same (includes order)
            System.out.println(" ");
            System.out.println("Insertion and Selection: " + myMagicItemsInsertion.equals(myMagicItemsSelection));
            System.out.println("Insertion and Merge: " + myMagicItemsInsertion.equals(myMagicItemsMerge));
            System.out.println("Insertion and Quick: " + myMagicItemsInsertion.equals(myMagicItemsQuick));

        }

        //in the event file is not found, user is made aware and program cannot run
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }


    public static int selectionSort(List<String> itemList)
    {
        int comparisonCount = 0;
        for(int i = 0; i < (itemList.size() - 1); i++)
        {
            int smallestPos = i;
            for(int j = i + 1; j < itemList.size(); j++)
            {
                comparisonCount++;
                if(itemList.get(smallestPos).compareToIgnoreCase(itemList.get(j)) > 0)
                    smallestPos = j;
            }
            swap(itemList, i, smallestPos);
        }
        return comparisonCount;
    }

    public static int insertionSort(List<String> itemList)
    {
        int comparisonCount = 0;
        for(int i = 1; i < itemList.size(); i++)
        {
            String currItem = itemList.get(i);
            int j = i-1;
            while(j >= 0 && (itemList.get(j).compareToIgnoreCase(currItem))>0)
            {
                comparisonCount++;
                swap(itemList, j+1, j);
                j--;
            }
            itemList.set(j+1, currItem);
        }
        return comparisonCount;
    }

    //txtbook pg 55
    static int mergeComparisonCount = 0;
    public static int mergeSort(List<String> items, int itemsSize)
    {
        if (itemsSize > 1)
        {
            int midpoint = itemsSize / 2;
            List<String> leftItems = Arrays.asList(new String[midpoint]);
            List<String> rightItems = Arrays.asList(new String[itemsSize - midpoint]);

            for (int i = 0; i < midpoint; i++)
                leftItems.set(i, items.get(i));

            for (int j = midpoint; j < itemsSize; j++)
                rightItems.set(j - midpoint, items.get(j));

            mergeSort(leftItems, midpoint);
            mergeSort(rightItems, itemsSize - midpoint);

            merge(items, leftItems, rightItems, midpoint, itemsSize - midpoint);
        }

        return mergeComparisonCount;
    }

    public static void merge(List<String> temp, List<String> leftItems, List<String> rightItems, int left, int right) {

        int leftInc = 0;  //keeps track of distance of middle (0 being farthest away)
        int rightInc = 0; //keeps track of distance from end of array
        int tempInc = 0; //keeps track of current variable in temp(sorted) array

        while (leftInc < left && rightInc < right)
        {
            mergeComparisonCount++;

            if (leftItems.get(leftInc).compareToIgnoreCase(rightItems.get(rightInc)) < 0)
                temp.set(tempInc++, leftItems.get(leftInc++));
            else
                temp.set(tempInc++, rightItems.get(rightInc++));
        }

        while (leftInc < left)
        {
            mergeComparisonCount++;
            temp.set(tempInc++, leftItems.get(leftInc++));
        }

        while (rightInc < right)
        {
            mergeComparisonCount++;
            temp.set(tempInc++, rightItems.get(rightInc++));
        }
    }

    static int quickComparisonCount = 0;
    public static int quickSort(List<String> items)
    {
        if (items.size() > 1)
        {
            Random rand = new Random();
            int pivotNumber = rand.nextInt(items.size());
            String pivotItem = items.get(pivotNumber);

            swap(items, 0, pivotNumber);
            int k = 0;

            for (int i = 1; i < items.size(); i++)
            {
                quickComparisonCount++;
                if (items.get(k).compareToIgnoreCase(items.get(i)) > 0)
                {
                    swap(items, k, i);
                    k++;
                    swap(items, k, i);

                }
            }

            quickSort(items.subList(0, k));
            quickSort(items.subList(k + 1, items.size()));
        }

        return quickComparisonCount;
    }

    public static void swap(List<String> itemList, int lowestPos, int swaperPos)
    {
        String temp = itemList.get(lowestPos);
        itemList.set(lowestPos, itemList.get(swaperPos));  //take what is in the higher spot, and put it at the lowest index
        itemList.set(swaperPos, temp);   //take what used to be in lowestPos and put it in the higher spot
    }

}
