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
        System.out.println(showResults());
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
        else if(!powerOfTwo(tempGroupSize))
        {
            System.out.println("Testing is done using binary trees - please keep group size as a power of 2 (e.g. 2, 4, 8, 16");
            getGroupSize(population);
        }
        else
            groupSize = tempGroupSize;
    }

    public static boolean powerOfTwo(int value)
    {
        double result = (Math.log(value) / Math.log(2));
        double resultAsInt = (double)(int)result;  //will turn all decimals into .0 <- if this equals original, original was whole number
        if(result == resultAsInt)
            return true;
        else
            return false;
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
        System.out.println("\nPopulation Size: " + popSize +
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
            System.out.print("\nAre these values correct (Y/N): ");
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
    static int caseOne = 0;   //no parital tests (groups of 8)
    static int caseTwo = 0;   //one partial tests (groups of 4)
    static int caseThree = 0;  //individual tests (groups of 1)

    public static void performTests(boolean[] pop)
    {
        boolean infectionFound = false;
        int whileIndex = 0;  //will increase by group size after a completed iteration

        int divideLevel = getDivideLevel();
        int currLevel = 0;

        boolean[] evalPop = Arrays.copyOfRange(pop, 0, groupSize);


        while(whileIndex < popSize)   //does work only if no infections found in subgroup
        {

            testCount++;

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
            caseOne++;
        }
    }

    public static void performSubTests(boolean[] subgroup, int currLevel, int divideLevel, int currGroupSize)
    {
        if(currGroupSize == 4)
        {
            performSingleTests(subgroup, currLevel, divideLevel, 4);
        }
        else
        {
            currGroupSize /= 2;
            boolean[] subGroup1 = Arrays.copyOfRange(subgroup, 0, currGroupSize );
            boolean[] subGroup2 = Arrays.copyOfRange(subgroup, currGroupSize, currGroupSize * 2 );
            boolean subGroup1ToSingle = false;
            boolean subGroup2ToSingle = false;

            testCount++;

            for(int i = 0; i < subGroup1.length; i++)
            {

                if(subGroup1[i] && currGroupSize == 1)
                    infectionCount++;

                if(subGroup1[i] && currGroupSize > 4)
                    performSubTests(subGroup1, currLevel++, divideLevel, currGroupSize);

                if(subGroup1[i] && currGroupSize == 4)
                {
                    performSingleTests(subGroup1, currLevel++, divideLevel, currGroupSize);
                    subGroup1ToSingle = true;
                }
            }

            testCount++;

            for(int i = 0; i < subGroup2.length; i++)
            {
                if(subGroup2[i] && currGroupSize == 1)
                    infectionCount++;

                if(subGroup2[i] && currGroupSize > 4)
                    performSubTests(subGroup2, currLevel++, divideLevel, currGroupSize/2);

                if(subGroup2[i] && currGroupSize == 4)
                {
                    performSingleTests(subGroup2, currLevel++, divideLevel, currGroupSize);
                    subGroup2ToSingle = true;
                }
            }

            if(subGroup1ToSingle && subGroup2ToSingle) //if both are true, then it went to 11 tests, otherwise if only 1 is, went to 7 tests
                caseThree++;
            else
                caseTwo++;
        }
    }

    public static void performSingleTests(boolean[] subgroup, int currLevel, int divideLevel, int currGroupSize)
    {
        boolean individual1 = subgroup[0];
        boolean individual2 = subgroup[1];
        boolean individual3 = subgroup[2];
        boolean individual4 = subgroup[3];

        if(individual1)
            infectionCount++;
        if(individual2)
            infectionCount++;
        if(individual3)
            infectionCount++;
        if(individual4)
            infectionCount++;

        testCount += 4;
    }

    public static int getDivideLevel()
    {
        int divideLevel = 0;
        int tempGroupSize = groupSize;

        while(tempGroupSize > 4)
        {
            tempGroupSize /= 2;
            divideLevel++;
        }

        return divideLevel;
    }

    public static String showResults()
    {

        String case1Results = "Case (1): " + caseOne + " - instances requiring no partial tests\n";

        String case2Results = "Case (2): " + caseTwo + " - instances requiring six additional tests\n";

        String case3Results = "Case (3): " + caseThree + " - instance requiring 11 tests\n";

        String divider = "———————————————————————————————————————\n";

        String infectionTotal = "Out of a population of " + popSize + " people, " + infectionCount + " tested positive for the infection.\n";

        String testTotal = testCount + " tests to screen a population of " + popSize + " people for a disease with " + infectionRate + "% infection rate.";

        return case1Results + case2Results + case3Results + divider + infectionTotal + testTotal;
    }
}
