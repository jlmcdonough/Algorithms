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
            ArrayList<String> myMagicItems = new ArrayList<String>();
            ArrayList<String> myPalindromes = new ArrayList<String>();
            
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                myMagicItems.add(normalizeItems(item));
            }

            for(int i = 0; i < myMagicItems.size(); i++)
            {
                Stack s = new Stack();
                Queue q = new Queue();
                for(int j = 0; j < myMagicItems.get(i).length(); j++)
                {
                    String letter = Character.toString(myMagicItems.get(i).charAt(j));
                    s.push(letter);
                    q.enqueue(letter);
                }

                if(evalPalidrome(s, q, myMagicItems.get(i)))
                    myPalindromes.add(myMagicItems.get(i));
            }

            for(String palindromes : myPalindromes )
                System.out.println(palindromes);
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }

    }

    public static File getFile()
    {
        URL path = Assignment1.class.getResource("magicitems.txt");
        File magicFile = new File(path.getFile());
        return magicFile;
    }

    public static String normalizeItems(String item)
    {
        item = item.toLowerCase().trim().replaceAll(" ","");
        item = item.replaceAll("\\p{Punct}", "");
        return item;
    }

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

