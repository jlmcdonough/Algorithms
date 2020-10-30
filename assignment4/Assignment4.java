/*
Joseph McDonough
CMPT 435
Professor Labouseur
13 November 2020
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Assignment4 extends Tree
{
    public static void main(String args[]) throws FileNotFoundException
    {
        ArrayList<String> myMagicItems = getList();
        String[] myRandomItems = generateRandomItems(myMagicItems);
        Tree myBinaryTree = new Tree();

        for(int i = 0; i < myMagicItems.size(); i++)
        {
            Node temp = new Node(myMagicItems.get(i));
            treeInsert(myBinaryTree, temp);
        }

        System.out.println(myBinaryTree.root.data + " <- root ");                             //Hectorius's Twin Rings
        System.out.println(myBinaryTree.root.left.data + " <- left of root");                 //Armatha's long sword
        System.out.println(myBinaryTree.root.left.left.data + " <- left left of root");       //Amulet of mighty fists +4
        System.out.println(myBinaryTree.root.left.right.data + " <- left right of root");     //Gauntlets of Warrior Might
        System.out.println(myBinaryTree.root.right.data + " <- right of root");               //Teleport Ribbon
        System.out.println(myBinaryTree.root.right.left.data + " <- right left of root");     //Pearl of power, two spells
        System.out.println(myBinaryTree.root.right.right.data + " <- right right  of root");  //White sword
    }

    //does the work of the previous assignment of reading and sorting file
    public static ArrayList<String> getList() throws FileNotFoundException
    {
        Scanner reader = new Scanner(new File("magicitems.txt"));
        ArrayList<String> myMagicItems = new ArrayList<String>();  //will be the sorted list

        while (reader.hasNextLine())
            myMagicItems.add(reader.nextLine());

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

    //~~~~~~~~~~~~~~~~~~~~~~BINARY SEARCH TREE~~~~~~~~~~~~~~~~~~~~~~~~
    //public static void treeInsert()

}
