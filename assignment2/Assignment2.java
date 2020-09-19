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
            ArrayList<String> myMagicItems = new ArrayList<String>();   //holds list of magic items as per text file

            //adds each magic items into the magic item list
            while (reader.hasNextLine())
            {
                myMagicItems.add(reader.nextLine());
            }
        }

        //in the event file is not found, user is made aware and program cannot run
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }



    public static int selectionSort(ArrayList<String> items)
    {
        int comparisonCount = 0;

        return comparisonCount;
    }

    public static int insertionSort(ArrayList<String> items)
    {
        int comparisonCount = 0;

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
}
