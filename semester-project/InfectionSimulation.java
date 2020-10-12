/*
Joseph McDonough
CMPT 435
Professor Labouseur
11 December 2020
 */

import java.util.*;

public class InfectionSimulation
{
    public static void main(String args[])
    {
        inputLoop();
        verifyInput();

    }

    public static void getUserInput()
    {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Enter the population size: ");
        int popSize = myScanner.nextInt();
        System.out.print("Enter infection rate (as percentage - e.g. 90% is 90): ");
        double infectionRate = myScanner.nextDouble();
        System.out.print("Enter testing group size: ");
        int groupSize = myScanner.nextInt();
        System.out.print("Enter test accuracy rate (as percentage - e.g. 90% is 90): ");
        double testAccuracy = myScanner.nextInt();

        System.out.println("Population Size: " + popSize +
                "\nInfection Rate: " + infectionRate + "%" +
                "\nGroup Size: " + groupSize +
                "\nTest Accuracy: " + testAccuracy + "%");

        //myScanner.close();
    }

    public static void inputLoop()
    {
        boolean haveData = false;
        while(!haveData)
        {
            try
            {
                getUserInput();
                haveData = true;
            }
            catch (Exception InputMismatchException)
            {
                System.out.println("Please only enter numbers");
            }
        }
    }

    public static void verifyInput()
    {
        Scanner myScanner = new Scanner(System.in);
        boolean verifiedInput = false;
        while (!verifiedInput)
        {
            System.out.print("Are these values correct (Y/N): ");
            String yesOrNo = myScanner.nextLine();
            if (yesOrNo.equalsIgnoreCase("N"))
                inputLoop();
            else if (yesOrNo.equalsIgnoreCase("Y"))
            {
                System.out.println("Values Confirmed");
                verifiedInput = true;
            } else
              {
                System.out.println("Please only enter 'Y' or 'N' ");
                verifyInput();
              }
        }
    }

/*
    public static void createPopulation(TAKE USER INPUT FOR GROUP STUFF)
    {
        //infectPopulation()
    }

    public static void infectPopulation(TAKE INFO FROM ABOVE)
    {

    }

    public static void divideIntoGroups(TAKE FROM createPopulation)
    {
        //if population is not divisible by group size, round up or down so that it is
    }

    public static String showResults()
    {
        /*
        Case (1): 125 × 0.8500 = 106.25 instances requiring 107 tests (there are no partial tests)
        Case (2): 125 × 0.1496 = 18.70 instances requiring 131 tests
        Case (3): 125 × 0.0004 = 0.05 round up to 1 instance requiring 11 tests
        ———————————————————————————————————————
        249 tests to screen a population of 1000 people for a disease with 2% infection rate.

    } */
}
