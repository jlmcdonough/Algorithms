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
        gatherData();
        boolean[] population  = gatherPopulation();
        performTests(population);
        System.out.println(infectionCount + " X " + testCount);
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
                System.out.println("\nVALUES CONFIRMED\n");
                verifiedInput = true;
            } else
              {
                System.out.println("Please only enter 'Y' or 'N' ");
                verifyInput();
              }
        }
    }

    public static void gatherData()
    {
        getUserInput();
        verifyInput();
    }

    public static boolean[] createPopulation()
    {
        Random rand = new Random();
        int[] cleanPop = new int[popSize];

        for(int i = 0; i < cleanPop.length; i++)
        {
            cleanPop[i] = rand.nextInt(100) + 1;
        }

        return infectPopulation(cleanPop);
    }

    public static boolean[] infectPopulation(int[] cleanPop)
    {
        boolean[] infectedPop = new boolean[popSize];

        for(int i = 0; i < infectedPop.length; i++)
        {
            if(cleanPop[i] <= infectionRate)
                infectedPop[i] = true;
            else
                infectedPop[i] = false;
        }

        return infectedPop;
    }

    public static void divideIntoGroups(boolean[] pop)
    {
        int difference = popSize % groupSize;
        if(difference != 0 )
        {
            int adjustment = groupSize - difference;
            popSize += adjustment;
            System.out.println("\n*NOTE*\n In order for the population to be divided into even groups, " +
                               "the population size has been increased by " + adjustment + " to a " +
                               "total of " + popSize);
        }
    }

    public static boolean[] gatherPopulation()
    {
        boolean[] pop = createPopulation();
        divideIntoGroups(pop);
        return pop;
    }




    static int infectionCount = 0;
    static int testCount = 0;

    public static void performTests(boolean[] pop)
    {
        boolean infectionFound = false;
        int whileIndex = 0;  //will increase by group size after a completed iteration

        int divideLevel = getDivideLevel();
        int currLevel = 0;

        boolean[] evalPop = Arrays.copyOfRange(pop, 0, groupSize);

        System.out.println("POP SIZE: " + popSize + "GROUP SIZE: " + groupSize);

        while(whileIndex < popSize)   //does work only if no infections found in subgroup
        {
            System.out.println("WHILE INDEX: " + whileIndex + "GROUP SIZE: " + groupSize);
            testCount++;

            for(int j = 0; j < evalPop.length; j++)
                System.out.println("OUTERMOST: " + j + " " + evalPop[j]);

            for(int i = 0; i < groupSize; i++)
            {
                if(evalPop[i])
                {
                    infectionFound = true;
                }
            }

            if(infectionFound)
                performSubTests(evalPop, 0, divideLevel, groupSize);

            whileIndex += groupSize;
            evalPop = Arrays.copyOfRange(pop, 0 + whileIndex, groupSize + whileIndex);
            infectionFound = false;
        }
    }

    public static void performSubTests(boolean[] subgroup, int currLevel, int divideLevel, int currGroupSize)
    {
        currGroupSize /= 2;
        boolean[] subGroup1 = Arrays.copyOfRange(subgroup, 0, currGroupSize );
        boolean[] subGroup2 = Arrays.copyOfRange(subgroup, currGroupSize, currGroupSize * 2 );

        for(int j = 0; j < subGroup1.length; j++)
            System.out.println("SUBGROUP 1: " + j + " " + subGroup1[j]);
        for(int j = 0; j < subGroup2.length; j++)
            System.out.println("SUBGROUP 2: " + j + " " + subGroup2[j]);

        testCount++;
        for(int i = 0; i < subGroup1.length; i++)
        {
            if(subGroup1[i] && currGroupSize == 1)
                infectionCount++;

            if(subGroup1[i] && currGroupSize > 1)
                performSubTests(subGroup1, currLevel++, divideLevel, currGroupSize/2);
        }

        testCount++;
        for(int i = 0; i < subGroup2.length; i++)
        {
            if(subGroup2[i] && currGroupSize == 1)
                infectionCount++;

            if(subGroup2[i] && currGroupSize > 1)
                performSubTests(subGroup2, currLevel++, divideLevel, currGroupSize/2);
        }
    }

    public static int getDivideLevel()
    {
        int divideLevel = 0;
        int tempGroupSize = groupSize;

        while(tempGroupSize > 1)
        {
            tempGroupSize /= 2;
            divideLevel++;
        }
        System.out.println("DIVIDE LEVEL COUNT: " + divideLevel);
        return divideLevel;
    }

    public static String showResults()
    {
        /*
        Case (1): 125 × 0.8500 = 106.25 instances requiring 107 tests (there are no partial tests)
        Case (2): 125 × 0.1496 = 18.70 instances requiring 131 tests
        Case (3): 125 × 0.0004 = 0.05 round up to 1 instance requiring 11 tests
        ———————————————————————————————————————
        249 tests to screen a population of 1000 people for a disease with 2% infection rate.
        */
        return "nothing";
    }
}
