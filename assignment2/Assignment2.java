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
            ArrayList<String> myMagicItemsSelection = new ArrayList<String>();   //holds list of magic items as per text file
            ArrayList<String> myMagicItemsInsertion = new ArrayList<String>();   //holds list of magic items as per text file
            ArrayList<String> myMagicItemsMerge = new ArrayList<String>();   //holds list of magic items as per text file
            ArrayList<String> myMagicItemsQuick = new ArrayList<String>();   //holds list of magic items as per text file

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
            System.out.println(sortedQuick);
        }

        //in the event file is not found, user is made aware and program cannot run
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }


    public static int selectionSort(ArrayList<String> itemList)
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

    public static int insertionSort(ArrayList<String> itemList)
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
    static ArrayList<String> sortedQuick = new ArrayList<>();
    static ArrayList<String> dupeItems = new ArrayList<>();
    public static int quickSort(ArrayList<String> items)
    {
        if (items.size() < 1)
            return 999;   //should do nothing

        Random rand = new Random();
        int pivotNumber = rand.nextInt(items.size());
        String pivotItem = items.get(pivotNumber);

        ArrayList<String> lesserItems = new ArrayList<>();
        ArrayList<String> greaterItems = new ArrayList<>();


        for(int i = 0; i < items.size(); i++)
        {
            String s = items.get(i);
            quickComparisonCount++;
            if(pivotItem.compareToIgnoreCase(s) > 0)
                lesserItems.add(s);
            if(pivotItem.compareToIgnoreCase(s) < 0)
                greaterItems.add(s);
            if(pivotItem.compareToIgnoreCase(s) == 0 && i != pivotNumber)
                dupeItems.add(s);
        }

        quickSort(lesserItems);
        sortedQuick.add(pivotItem);
        quickSort(greaterItems);
        System.out.println(sortedQuick);

        /*
            Conditions to be met:
            1) If there are dupes
            2) lesserItems is 0 --> meaning sort is down to 1 item elements
            3) greaterItems is 0 --> " "
            4) indexOf the first dupe occurrence is in sortedQuick array
                --> This means that all items are already in the sortedQuick array (and only occurrence once)
                    --> Basically ensures that each item (unique or has duplicates) is present in the array and dupes can be added
                    --> Dupes get added to the index of the pre-existing element
         */
        if(dupeItems.size() > 0 && lesserItems.size() == 0 && greaterItems.size() == 0 && sortedQuick.indexOf(dupeItems.get(0)) >= 0)
        {
            for(String s : dupeItems)
            {
                int index = sortedQuick.indexOf(s);
                sortedQuick.add(index, s);
            }
            dupeItems.removeAll(dupeItems);
        }

        return quickComparisonCount;
    }

    public static void swap(ArrayList<String> itemList, int lowestPos, int swaperPos)
    {
        String temp = itemList.get(lowestPos);
        itemList.set(lowestPos, itemList.get(swaperPos));  //take what is in the higher spot, and put it at the lowest index
        itemList.set(swaperPos, temp);   //take what used to be in lowestPos and put it in the higher spot
    }

}
