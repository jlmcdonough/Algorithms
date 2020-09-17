/*
Joseph McDonough
CMPT 435
Professor Labouseur
18 September 2020
 */

import java.io.*;
import java.net.URL;
import java.util.*;

public class Assignment1
{
    public static void main(String[] args)
    {
        try
        {
            Scanner reader = new Scanner(new File("magicitems.txt"));
            ArrayList<String> myMagicItemsNorm = new ArrayList<String>();   //holds list of magic items as per text file, after normalized for palindrome detection
            ArrayList<String> myMagicItemsUnchanged = new ArrayList<String>();   //holds list of magic items as they appear in text file
            ArrayList<String> myPalindromes = new ArrayList<String>();  //holds list of palindromes as they appear

            //adds each magic items into the magic item list
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                myMagicItemsNorm.add(normalizeItems(item));
                myMagicItemsUnchanged.add(item);
            }

            //one item at a time, creates a stack and queue and adds each character of the item onto it
            for(int i = 0; i < myMagicItemsNorm.size(); i++)
            {
                //each item is in its own stack/queue
                Stack itemStack = new Stack();
                Queue itemQueue = new Queue();

                //puts each letter at the top/end of stack/queue
                for(int j = 0; j < myMagicItemsNorm.get(i).length(); j++)
                {
                    String letter = Character.toString(myMagicItemsNorm.get(i).charAt(j));
                    itemStack.push(letter);
                    itemQueue.enqueue(letter);
                }

                //checks to see if the word is a palindrome --> if it is, if = true and item get adds to palindrome list
                if(evalPalindrome(itemStack, itemQueue, myMagicItemsNorm.get(i)))
                    myPalindromes.add(myMagicItemsUnchanged.get(i));    //Both lists contain same elements, except one has items untouched <-- thats the one being printed
            }

            //prints out each palindrome
            for(String palindromes : myPalindromes )
                System.out.println(palindromes);
        }

        //in the event file is not found, user is made aware and program cannot run
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }

    }


    //turns all characters lowercase and removes whitespace and punctuation
    public static String normalizeItems(String item)
    {
        item = item.toLowerCase().trim().replaceAll(" ","");
        item = item.replaceAll("\\p{Punct}", "");
        return item;
    }

    //takes each character off the stack and queue one at a time and checks to see if equal
    //if stack pop = queue dequeue, then it still has a chance to be a palindrome, other cannot be
    public static boolean evalPalindrome(Stack s, Queue q, String item)
    {
        int k = 0;
        while(k < item.length())
        {
            String stackPop = s.pop().data;
            String queueDequeue = q.dequeue().data;
            if((stackPop.equals(queueDequeue)))
                k++;
            else if(!((stackPop.equals(queueDequeue))))
                return false;
        }
        return true;
    }

}

