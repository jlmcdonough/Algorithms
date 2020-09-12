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


        }

        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }

    }
}

