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
            Scanner reader = new Scanner(getFile());
            ArrayList<String> myMagicItems = new ArrayList<String>();   //holds list of magic items as per text file
            ArrayList<String> myPalindromes = new ArrayList<String>();  //holds list of palidromes as they appear

            //adds each magic items into the magic item list
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                myMagicItems.add(normalizeItems(item));
            }

            //one item at a time, creates a stack and queue and adds each character of the item onto it
            for(int i = 0; i < myMagicItems.size(); i++)
            {
                //each item is in its own stack/queue
                Stack itemStack = new Stack();
                Queue itemQueue = new Queue();

                //puts each letter at the top/end of stack/queue
                for(int j = 0; j < myMagicItems.get(i).length(); j++)
                {
                    String letter = Character.toString(myMagicItems.get(i).charAt(j));
                    itemStack.push(letter);
                    itemQueue.enqueue(letter);
                }

                //checks to see if the word is a palindrome --> if it is, if = true and item get adds to palidrome list
                if(evalPalidrome(itemStack, itemQueue, myMagicItems.get(i)))
                    myPalindromes.add(myMagicItems.get(i));
            }

            //prints out each palidrome
            for(String palindromes : myPalindromes )
                System.out.println(palindromes);
        }

        //in the event file is not found, user is made aware and program cannot run
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }

    }

    //Gets path of magicitems.txt and returns the file
    public static File getFile()
    {
        URL path = Assignment1.class.getResource("magicitems.txt");
        File magicFile = new File(path.getFile());
        return magicFile;
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
    public static boolean evalPalidrome(Stack s, Queue q, String item)
    {
        boolean isPalindrome = true;
        int k = 0;
        while(k < item.length())
        {
            String stackPop = s.pop().data;
            String queueDequeue = q.dequeue().data;
            if(isPalindrome && (stackPop.equals(queueDequeue)))
                k++;
            else if(isPalindrome && !((stackPop.equals(queueDequeue))))
            {
                isPalindrome = false;
                return isPalindrome;
            }
        }
        return isPalindrome;
    }

}

