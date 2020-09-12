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
            URL path = Assignment1.class.getResource("magicitems.txt");
            File magicFile = new File(path.getFile());
            System.out.println(magicFile);
            Scanner reader = new Scanner(magicFile);
            ArrayList<String> myMagicItems = new ArrayList<String>();
            ArrayList<String> myPalindromes = new ArrayList<String>();
            while (reader.hasNextLine())
            {
                String item = reader.nextLine();
                item = item.toLowerCase().trim().replaceAll(" ","");
                item = item.replaceAll("\\p{Punct}", "");
                myMagicItems.add(item);
            }

            for(int i = 0; i < myMagicItems.size(); i++)
            {
                Stack s = new Stack();
                Queue q1 = new Queue();
                for(int j = 0; j < myMagicItems.get(i).length(); j++)
                {
                    String letter = Character.toString(myMagicItems.get(i).charAt(j));
                    s.push(letter);
                    q1.enqueue(letter);
                }

                boolean isPalindrome = true;
                int k = 0;
                while(k < myMagicItems.get(i).length())
                {
                    String stackPop = s.pop().data;
                    String queueDequeue = q1.dequeue().data;
                    if(isPalindrome && (stackPop.equals(queueDequeue)))
                        k++;
                    else if(isPalindrome && !((stackPop.equals(queueDequeue))))
                    {
                        isPalindrome = false;
                        break;
                    }
                }

                if(s.isEmpty() && isPalindrome)
                    myPalindromes.add(myMagicItems.get(i));

            }

            System.out.println(myPalindromes.size());
            for(String palindromes : myPalindromes )
                System.out.println(palindromes);
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }

    }
}

