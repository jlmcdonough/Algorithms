/*
Joseph McDonough
CMPT 435
Professor Labouseur
30 October 2020
 */

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class Assignment3 extends Queue
{
    public static void main(String args[]) throws FileNotFoundException
    {
            ArrayList<String> mySortedMagicItems = getAndSortList();

            ArrayList<String> myMagicRandom = new ArrayList<>();      //mutated by random items
            myMagicRandom = (ArrayList)mySortedMagicItems.clone();
            String[] randomItems = generateRandomItems(myMagicRandom);

            linearSearchComplete(mySortedMagicItems, randomItems);

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            binarySearchComplete(mySortedMagicItems, randomItems);

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            hashTableComplete(mySortedMagicItems, randomItems);
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

    //does the work of the previous assignment of reading and sorting file
    public static ArrayList<String> getAndSortList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("magicitems.txt"));
        ArrayList<String> myMagicItems = new ArrayList<String>();  //will be the sorted list

        while (reader.hasNextLine())
            myMagicItems.add(reader.nextLine());

        mergeSort(myMagicItems, myMagicItems.size());

        return myMagicItems;
    }

    //randomly selected the 42 items for the searches to work on
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

    //~~~~~~~~~~~~~~~~~~LINEAR SEARCH~~~~~~~~~~~~~~~~~~~
    public static ArrayList<Assignment3> linearSearch(List<String> myList, String item, ArrayList<Assignment3> linearResultsGroup)
    {
        int i = 0;
        boolean found = false;
        int linearSearchCount = 0;

        while(i < myList.size() && !found)
        {
            linearSearchCount++;
            if(myList.get(i).equalsIgnoreCase(item))
                found = true;

            i++;
        }

        Assignment3 searchedPair = new Assignment3(item, linearSearchCount);
        linearResultsGroup.add(searchedPair);
        return linearResultsGroup;
    }

    public static void linearSearchComplete(List<String> magicItems, String[] randomItems)
    {
        ArrayList<Assignment3> linearResultsGroup = new ArrayList<Assignment3>();
        for(int j = 0; j < randomItems.length; j++)
        {
            linearResultsGroup = linearSearch(magicItems, randomItems[j], linearResultsGroup);
        }

        System.out.println("LINEAR SEARCH\n");
        printSearchResults(linearResultsGroup);
    }

    //~~~~~~~~~~~~~~~~~~BINARY SEARCH~~~~~~~~~~~~~~~~~~~
    static int binarySearchCount = 0;
    public static ArrayList<Assignment3> binarySearch(List<String> magicItems, String item, int startIndex, int stopIndex, ArrayList<Assignment3> binaryResultsGroup)
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
            binarySearch(magicItems, item, startIndex, middleIndex - 1, binaryResultsGroup);
        else
            binarySearch(magicItems, item, middleIndex + 1, stopIndex, binaryResultsGroup);

        return binaryResultsGroup;
    }

    public static void binarySearchComplete(List<String> magicItems, String[] randomItems)
    {
        ArrayList<Assignment3> binaryResultsGroup = new ArrayList<Assignment3>();
        for(int j = 0; j < randomItems.length; j++)
        {
            binaryResultsGroup = binarySearch(magicItems, randomItems[j], 0, magicItems.size(), binaryResultsGroup);
        }

        System.out.println("BINARY SEARCH\n");
        printSearchResults(binaryResultsGroup);
    }


    //~~~~~~~~~~~~~~~~~~HASHING~~~~~~~~~~~~~~~~~~~
    private static ArrayList<Queue> myHashTable = new ArrayList<>();
    private static int HASH_TABLE_SIZE = 250;

    public static void createHashTable()
    {
        for(int i = 0; i < 250; i++)
        {
            Queue tempQueue = new Queue();
            myHashTable.add(tempQueue);
        }
    }

    //function taken from assignment and copied directly
    public static int makeHashCode(String str)
    {
        str = str.toUpperCase();
        int length = str.length();
        int letterTotal = 0;

        // Iterate over all letters in the string, totalling their ASCII values.
        for (int i = 0; i < length; i++)
        {
            char thisLetter = str.charAt(i);
            int thisValue = (int)thisLetter;
            letterTotal = letterTotal + thisValue;
        }

        // Scale letterTotal to fit in HASH_TABLE_SIZE.
        int hashCode = (letterTotal * 1) % HASH_TABLE_SIZE;
        return hashCode;
    }

    //take each item in the file, hash it, and put at the proper index in hash table in form of queue at each index
    public static void loadHashTable(List<String> fileItems)
    {
        for(String item : fileItems)
        {
            int toGoIndex = makeHashCode(item);

            if(myHashTable.get(toGoIndex) == null)
            {
                Queue tempQueue = new Queue();
                tempQueue.enqueue(item);
            }
            else
                myHashTable.get(toGoIndex).enqueue(item);
        }
    }

    //hash the item again to see what index to look at, then go through that queue looking for value
    public static int findElement(String item)
    {
        int count = 0;   //going to the queue in the array is deemed "free"
        boolean found = false;
        int location = makeHashCode(item);
        Queue hashQueue = myHashTable.get(location);

        if(hashQueue.isEmpty())
            count = -1;
        else
        {
            Node nodeEval = hashQueue.queueFirst;
            while(!found)
            {
                if(nodeEval.data.equalsIgnoreCase(item))
                    found = true;
                else
                    nodeEval = nodeEval.reference;
                count++;
            }
        }
        if(!found)
            count = -1;  //want to let user know item was not found (cannot be found in negative searches normally

        return count;
    }

    public static void hashTableComplete(List<String> magicItems, String[] randomItems)
    {
        createHashTable();
        loadHashTable(magicItems);
        ArrayList<Assignment3> hashResultsGroup = new ArrayList<Assignment3>();
        for(int k = 0; k < randomItems.length; k++)
        {
            int searchCount = findElement(randomItems[k]);
            hashResultsGroup.add(new Assignment3(randomItems[k], searchCount));
        }

        System.out.println("HASH TABLE SEARCH\n");
        printSearchResults(hashResultsGroup);
    }


    //~~~~~~~~~~~~~~~~~~PRINT RESULTS~~~~~~~~~~~~~~~~~~~
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

    //~~~~~~~~~~~~~~~~~~MERGE SORT~~~~~~~~~~~~~~~~~~~

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
