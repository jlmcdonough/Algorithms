/*
Joseph McDonough
CMPT 435
Professor Labouseur
2 October 2020
 */

import java.io.File;
import java.io.FileNotFoundException;
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
            //adds each magic items into the magic item list
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                myMagicItemsSelection.add(item);
                myMagicItemsInsertion.add(item);
            }

            System.out.println(selectionSort(myMagicItemsSelection)); //prints comparison count
            System.out.println(myMagicItemsSelection);
            System.out.println(insertionSort(myMagicItemsInsertion)); //prints comparison count
            System.out.println(myMagicItemsInsertion);
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

    public static int mergeSort(ArrayList<String> items)
    {
        int comparisonCount = 0;

        return comparisonCount;
    }

    public static int quickSort(ArrayList<String> items)
    {
        int comparisonCount = 0;

        return comparisonCount;
    }

    public static void swap(ArrayList<String> itemList, int lowestPos, int swaperPos)
    {
        String temp = itemList.get(lowestPos);
        itemList.set(lowestPos, itemList.get(swaperPos));  //take what is in the higher spot, and put it at the lowest index
        itemList.set(swaperPos, temp);   //take what used to be in lowestPos and put it in the higher spot
    }

}
