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

            String mergeArray[] = myMagicItemsMerge.toArray(new String[myMagicItemsMerge.size()]);
            System.out.println("\nMerge Count: " + mergeSort(mergeArray,666)); //prints comparison count
            for(String s: mergeArray)
            {
                if(s.equals("Aerewens armor")) //want all arrays to look the same in the end
                    System.out.print("[" + s + ", ");
                else if(s.equals("Zales Might"))
                    System.out.println(s + "]");
                else
                    System.out.print(s + ", ");
            }

            System.out.println("\nQuick Count: " + quickSort(myMagicItemsQuick)); //prints comparison count
            System.out.println(myMagicItemsQuick);

            System.out.println(myMagicItemsInsertion.equals(myMagicItemsSelection));
            System.out.println(myMagicItemsInsertion.equals(myMagicItemsQuick));

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
    public static int mergeSort(String[] items, int itemsSize)
    {
        if (itemsSize <= 1)
            return -9999;   //should do nothing

        int midpoint = itemsSize / 2;
        String[] leftItems = new String[midpoint];
        String[] rightItems = new String[itemsSize - midpoint];

        for (int i = 0; i < midpoint; i++)
        {
            leftItems[i] = items[i];
        }

        for (int j = midpoint; j < itemsSize; j++)
        {
            rightItems[j - midpoint] = items[j];

        }
        mergeSort(leftItems, midpoint);
        mergeSort(rightItems, itemsSize - midpoint);


        merge(items, leftItems, rightItems, midpoint, itemsSize - midpoint);

        return mergeComparisonCount;
    }

    public static void merge(String[] temp, String[] leftItems, String[] rightItems, int left, int right) {

        int leftInc = 0;  //keeps track of distance of middle (0 being farthest away)
        int rightInc = 0; //keeps track of distance from end of array
        int tempInc = 0; //keeps track of current variable in temp(sorted) array

        while (leftInc < left && rightInc < right)
        {
            mergeComparisonCount++;

            if (leftItems[leftInc].compareToIgnoreCase(rightItems[rightInc]) < 0)
            {
                temp[tempInc++] = leftItems[leftInc++];
            }
            else
            {
                temp[tempInc++] = rightItems[rightInc++];
            }
        }

        while (leftInc < left)
        {
            mergeComparisonCount++;
            temp[tempInc++] = leftItems[leftInc++];
        }

        while (rightInc < right)
        {
            mergeComparisonCount++;
            temp[tempInc++] = rightItems[rightInc++];
        }
    }

    static int quickComparisonCount = 0;
    public static int quickSort(List<String> items)
    {
        if (items.size() <= 1)
            return -999999;   //should do nothing

        Random rand = new Random();
        int pivotNumber = rand.nextInt(items.size());
        String pivotItem = items.get(pivotNumber);

        swap(items, 0, pivotNumber);
        int k = 0;

        for(int i = 1; i < items.size(); i++)
        {
            quickComparisonCount++;
            if(items.get(0).compareToIgnoreCase(items.get(i)) > 0)
            {
                swap(items, k, i);
                swap(items, 0, k);
            }
        }
        quickSort(items.subList(0,k));
        quickSort(items.subList(k+1,items.size()));

        return quickComparisonCount;
    }

    public static void swap(List<String> itemList, int lowestPos, int swaperPos)
    {
        String temp = itemList.get(lowestPos);
        itemList.set(lowestPos, itemList.get(swaperPos));  //take what is in the higher spot, and put it at the lowest index
        itemList.set(swaperPos, temp);   //take what used to be in lowestPos and put it in the higher spot
    }

}
