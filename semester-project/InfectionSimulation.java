/*
Joseph McDonough
CMPT 435
Professor Labouseur
11 December 2020
 */

import java.util.*;

public class InfectionSimulation
{
    static Scanner myScanner = new Scanner(System.in);
    static int popSize;
    static double infectionRate;
    static int groupSize;
    static double testAccuracy;

    public static void main(String args[])
    {
        getUserInput();
        verifyInput();

    }

    public static void getPopulationSize()
    {
        System.out.print("Enter the population size: ");

        while(!myScanner.hasNextInt())
        {
            System.out.println("Only whole numbers are accepted input");
            myScanner.next();
            System.out.print("Enter the population size: ");
        }

        int tempPopSize = myScanner.nextInt();

        if(tempPopSize <= 0)
        {
            System.out.println("Population size must be greater than 0");
            getPopulationSize();
        }
        else
            popSize = tempPopSize;

    }

    public static void getInfectionRate()
    {
        System.out.print("Enter infection rate (as percentage - e.g. 90% is 90): ");

        while(!myScanner.hasNextDouble())
        {
            System.out.println("Only numbers are accepted input");
            myScanner.next();
            System.out.print("Enter infection rate (as percentage - e.g. 90% is 90): ");
        }

        double tempInfectRate = myScanner.nextDouble();

        if(tempInfectRate < 0 || tempInfectRate > 100)
        {
            System.out.println("Infection rate must be between 0 and 100");
            getInfectionRate();
        }
        else
            infectionRate = tempInfectRate;

    }

    public static void getGroupSize(int population)
    {
        System.out.print("Enter testing group size: ");

        while(!myScanner.hasNextInt())
        {
            System.out.println("Only whole numbers are accepted input");
            myScanner.next();
            System.out.print("Enter testing group size: ");
        }

        int tempGroupSize = myScanner.nextInt();

        if(tempGroupSize <= 0)
        {
            System.out.println("Group size must be greater than 0");
            getGroupSize(population);
        }
        else if(tempGroupSize > population)
        {
            System.out.println("Group size must be less than or equal to population size of " + population);
            getGroupSize(population);
        }
        else
            groupSize = tempGroupSize;
    }

    public static void getTestAccuracy()
    {
        System.out.print("Enter test accuracy rate (as percentage - e.g. 90% is 90): ");

        while(!myScanner.hasNextDouble())
        {
            System.out.println("Only numbers are accepted input");
            myScanner.next();
            System.out.print("Enter test accuracy rate (as percentage - e.g. 90% is 90): ");
        }

        double tempTestAccuracy = myScanner.nextDouble();

        if(tempTestAccuracy < 0 || tempTestAccuracy > 100)
        {
            System.out.println("Test Accuracy rate must be between 0 and 100");
            getTestAccuracy();
        }
        else
            testAccuracy = tempTestAccuracy;
    }

    public static void getUserInput()
    {
        getPopulationSize();
        getInfectionRate();
        getGroupSize(popSize);
        getTestAccuracy();
        System.out.println("Population Size: " + popSize +
                "\nInfection Rate: " + infectionRate + "%" +
                "\nGroup Size: " + groupSize +
                "\nTest Accuracy: " + testAccuracy + "%");

        //myScanner.close();
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
                getUserInput();
            else if (yesOrNo.equalsIgnoreCase("Y"))
            {
                System.out.println("\nVALUES CONFIRMED");
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
