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
    static int divideCount;
    static double testAccuracy;
    static String divider = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    public static void main(String args[])
    {
        simulation();
    }

    //~~~~~~~~~~~~~~~USER ENTRY PART~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //takes int for population size as input
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

    //takes double for rate of infection as input
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

    //takes int for group size as input
    //must be smaller than group size and be a power of 2 - only size 8 works as intended
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

    //identifies how many divides are needed before single tests (i.e. group size 4)
    //right now is not really useful as code hardcode to 2, but want this here when expand up to group size 16, 32, 64, etc.
    public static void getDivideLevel()
    {
        System.out.print("Enter testing group division amounts (or '-999' for explanation'): ");

        while(!myScanner.hasNextInt())
        {
            System.out.println("Only whole numbers are accepted input");
            myScanner.next();
            System.out.print("Enter testing group division amounts: ");
        }

        int tempDivideCount = myScanner.nextInt();

        if(tempDivideCount == -999)
        {
            System.out.println("This variable determines how many times the groups are cut in half before individual tests are performed.\n" +
                                "Examples:\n" +
                                "\tGroup size 16 with division amount of 3: 16 --> 8 --> 4 --> 2 --> 1\n" +
                                "\tGroup size 16 with division amount of 2: 16 --> 8 --> 4 --> 1\n" +
                                "\tGroup size 16 with division amount of 1: 16 --> 8 --> 1\n" +
                                "\tGroup size 16 with division amount of 4: 16 --> 8 --> 4 --> 2 --> 1 --> ERROR because can't go any further than group size 1 \n" +
                                "A testing group size must be bigger enough such that the division amounts can be done evenly and end with a whole number greater than 1.\n");
            getDivideLevel();
        }
        else if(tempDivideCount == -111)
            getGroupSize(popSize);
        else if(doesItDivide(groupSize, tempDivideCount))
        {
            divideCount = tempDivideCount;
        }
        else
            getDivideLevel();
    }

    public static boolean doesItDivide(int group, int divide)
    {
        int i = divide;
        boolean doesItDivide = true;
        int tempGroup = group;    //in the while statement, the second if will divide group by 2, but don't want that value after the check, so this separate value takes care of it

        while(i > 0 && doesItDivide)
        {
            if((group % 2) != 0)
            {
                doesItDivide = false;
                System.out.println("Your desired divide amount does not go evenly into the selected group size. Please try again.");
                System.out.println("\nYou can enter '-111' to go back to adjust your group size first.");
            }
            else if((tempGroup /= 2) == 1)
            {
                doesItDivide = false;
                System.out.println("The divide amount cannot send the groups to group size 1, the individual tests will be performed after the amount of division you have requested are met.  Please choose a smaller division amount.");
                System.out.println("\nYou can enter '-111' to go back to adjust your group size first.");
            }
            else
                group /= 2;
            i--;
        }

        return doesItDivide;
    }

    //takes double for test accuracy as input
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

    //function to perform all of the user input commands above and prints it to user
    public static void getUserInput()
    {
        getPopulationSize();
        getInfectionRate();
        getGroupSize(popSize);
        getDivideLevel();
        getTestAccuracy();
        System.out.println("\n" + printInput());
        //myScanner.close();
    }

    //prints the users entered data
    public static String printInput()
    {
        return("\tPopulation Size: " + popSize +
                "\n\tInfection Rate: " + infectionRate + "%" +
                "\n\tGroup Size: " + groupSize +
                "\n\tTest Accuracy: " + testAccuracy + "%");
    }

    //Gives user a chance to make sure all of their inputs are correct, if not, getUserInput runs and program basically starts over
    public static void verifyInput()
    {
        Scanner myScanner = new Scanner(System.in);
        boolean verifiedInput = false;
        while (!verifiedInput)
        {
            System.out.print("\nAre these values correct? (Y/N): ");
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

    static boolean userWantsCaseResults;
    //asks user if they would like each group data to be printed or not
    public static void wantData()
    {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Would you like the results for each group to be printed in the report? (Y/N): ");
        String yayOrNay = myScanner.nextLine();
        if (yayOrNay.equalsIgnoreCase(("Y")))
        {
            userWantsCaseResults = true;
            System.out.println("\nou have opted to view the data for all groups. Proceeding with the tests...");
        }
        else if (yayOrNay.equalsIgnoreCase(("N")))
        {
            userWantsCaseResults = false;
            System.out.println("\nYou have opted to not to view the data for all groups. Proceeding with the tests...");
        }
        else
        {
            System.out.println("Please only enter 'Y' or 'N' ");
            wantData();
        }
    }

    //function to gather the input and verify (does all of the above functions in one call)
    public static void gatherData()
    {
        getUserInput();
        verifyInput();
        wantData();
    }

    //concludes user entry part of simulation

    //~~~~~~~~~~~~~~~POPULATION PART~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //Program allows user to enter a group size that should be valid, but is not evenly divisible by the population size
    // (i.e. group size 8 is acceptable, but not divisible by 15 or 17 - if 15, then 1 is added to population, if 17, then 7 added to population)
    //this ensures that each group is full and there are no leftovers
    public static void divideIntoGroups()
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

    //give the user specification, creates an int array list, each int is a random number [1, 100]
    //random number gets assigned to each individual - this population is pre-infect (deemed clean cause ignorance is bliss)
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

    //array of integers is taken as input and if the number is less than the infection rate, than that test is infected and marked true (for sick)
    //if the number is greater than the infection rate, that test passed clean and is marked false
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

        return identifyFalseTests(infectedPop);
    }

    //function accounts for testing inaccuracy
    //each test is compared to another random number [0.0, 99.999...] (because test accuracy 100 means impossible for it to fail)
    //if that tests random number is greater than the testing inaccuracy, then the test is said to have given the wrong result and the boolean value flipped
    //false positives and negatives are identified before any test is even run in this simulation

    static int falseNegative = 0;  //get negative when should've been positive  (true becomes false due to test inaccuracy)
    static int falsePositive = 0;  //get positive when should've been negative  (false becomes true due to test inaccuracy)

    public static boolean[] identifyFalseTests(boolean[] falsePop)
    {
        Random rand = new Random();

        for(int i = 0; i < popSize; i++)
        {
            int wholeRand = rand.nextInt(100);
            double decimalRand = rand.nextDouble();
            double falseNum = wholeRand + decimalRand;

            if(falsePop[i] && falseNum > testAccuracy)
            {
                falsePop[i] = false;
                falseNegative++;
            }
            else if(!falsePop[i] && falseNum > testAccuracy)
            {
                falsePop[i] = true;
                falsePositive++;
            }
        }

        return falsePop;
    }



    //function that adds the tests needed to make even groups and does the creating, infecting, and identified false test on population
    //returns the final boolean array with the tests
    public static boolean[] gatherPopulation()
    {
        divideIntoGroups();
        boolean[] pop = createPopulation();
        return pop;
    }

    //concludes population part of simulation

    //~~~~~~~~~~~~~~~TEST PART~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    static int infectionCount = 0;   //total infections found
    static int testCount = 0;        //total tests done
    static int caseOne = 0;   //no partial tests (groups of 8)
    static int caseTwo = 0;   //one partial tests (groups of 4)
    static int caseThree = 0;  //individual tests (groups of 1)

    //performs the initial (level 0 - case 1) tests.  if no one in the original sample is infected, moves onto next group
    //if there is an infection, delegates that group to performSubTest unless group size is 4, that is performSingleTests
    public static void performTests(boolean[] pop)
    {
        boolean infectionFound = false;
        int whileIndex = 0;                   //will increase by group size after a completed iteration
        int divideLevel = divideCount;   //at the moment, is only useful for indenting on the printed results
        boolean[] evalPop = Arrays.copyOfRange(pop, 0, groupSize);   //takes a subset of the population array that spans groupSize long
        int currLevel = 1;

        while(whileIndex < popSize)   //does work only if no infections found in subgroup
        {

            testCount++;

            if(userWantsCaseResults)
                System.out.println("\n" + divider + "\nConducting tests on patients " + (whileIndex + 1) + " through " + (whileIndex + groupSize) + "\n");

            for(int i = 0; i < groupSize; i++)
            {
                if(evalPop[i])
                {
                    infectionFound = true;
                }
            }

            testResults(currLevel, infectionFound);

            if(infectionFound && groupSize > 4)
                performSubTests(evalPop, 2, divideLevel, groupSize);
            else if(infectionFound && groupSize == 4)
                performSingleTests(evalPop, 2, divideLevel, 4);
            else
            {
                currLevel = 1;
                caseOne++;
            }

            whileIndex += groupSize;
            evalPop = Arrays.copyOfRange(pop, 0 + whileIndex, groupSize + whileIndex);    //creates new subset array that spans from where previous left off to groupSize more)
            infectionFound = false;   //resets infectionFound

        }
    }

    //does the work if infection found in first passing, but does not do the work if there are only 4 in the group since that is individual test (delegates to performSingleTests)
    //takes the subgroup, the current level, the divide level, and the current group size as parameters (this gets cut in half each time)
    //for initial group size 8, this is level 2 - case 2 but could progress to case 3 if subgroup1 and subgroup2 are infected
    public static void performSubTests(boolean[] subgroup, int currLevel, int divideLevel, int currGroupSize)
    {
            if(currLevel == divideLevel)
                performSingleTests(subgroup, (currLevel), divideLevel, currGroupSize);

            currGroupSize /= 2;
            boolean[] subGroup1 = Arrays.copyOfRange(subgroup, 0, currGroupSize );                       //are splitting groups in half so this is first half
            boolean[] subGroup2 = Arrays.copyOfRange(subgroup, currGroupSize, currGroupSize * 2 );         //second half
            boolean subGroup1Positive = false;          //need to keep track if this goes to case 3 or stays at case 2)
            boolean subGroup2Positive = false;         //if both get set to true, then both are performing single tests and case three is triggered and incremented

            testCount++;  //it is considered 1 test to go through 1 group. this is subgroup1's test

            int i = 0;
            while(i < subGroup1.length && !subGroup1Positive)
            {

                if(subGroup1[i] && currGroupSize > 4)
                {
                    testResults((currLevel), true);            //need to print out results before this function gets put on stack so performSub
                    performSubTests(subGroup1, (currLevel + 1), divideLevel, currGroupSize);   //splits group in half yet again and recursively calls this function
                    subGroup1Positive = true;
                    currLevel--;                                                               //gets called at the very end as functions are coming off the stack and needs to decrement to indicate going back towards clean stack
                }

                if(subGroup1[i] && currGroupSize == 4)
                {
                    testResults((currLevel), true);
                    performSingleTests(subGroup1, (currLevel + 1), divideLevel, currGroupSize);
                    subGroup1Positive = true;
                }
                i++;
            }

            if(!subGroup1Positive)                                                         //if no infection was found on subgroup1, then print results
                testResults((currLevel), false);


            testCount++;   //for subgroup2

            int j = 0;                                                      //exact same as subgroup1
            while(j < subGroup2.length && !subGroup2Positive)
            {

                if(subGroup2[j] && currGroupSize > 4)
                {
                    testResults((currLevel), true);
                    performSubTests(subGroup2, (currLevel + 1), divideLevel, currGroupSize);
                    subGroup2Positive = true;
                }

                if(subGroup2[j] && currGroupSize == 4)
                {
                    testResults((currLevel), true);
                    performSingleTests(subGroup2, (currLevel + 1), divideLevel, currGroupSize);
                    subGroup2Positive = true;
                    currLevel--;
                }

                j++;
            }

            if(!subGroup2Positive)
                testResults((currLevel), false);

            if(subGroup1Positive && subGroup2Positive) //if both are true, then it went to 11 tests, otherwise if only 1 is, went to 7 tests
                caseThree++;
            else                                       //if one did not go to single tests, than it is a case 2.  Will need to adjust to account for group size 16 - do so by re-adding to single variable (i.e. if group size becomes 4 after split)
                caseTwo++;

    }

    //performs 4 individual tests on a subgroup of size 4
    //takes same parameters as performSubTests
    public static void performSingleTests(boolean[] subgroup, int currLevel, int divideLevel, int currGroupSize)
    {
        int infectPrevious = infectionCount;
        int newInfect = 0;
        ArrayList<Boolean> singleTest = new ArrayList<Boolean>();

        for(boolean b : subgroup)
            singleTest.add(b);

        for(int i = 0; i < singleTest.size(); i++)
        {
            if(singleTest.get(i))
                infectionCount++;
        }

        newInfect = infectionCount - infectPrevious;                   //want to print out how many positive tests were found here
        singleTestResults(currLevel, newInfect);

        testCount += singleTest.size();                                                //4 individual tests done so much increment by 4
    }


    //concludes test part

    //~~~~~~~~~~~~~~~RESULTS PART~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //takes current level and whether an infection was found as parameters
    //if infection was found, message states moving onto next level to find it
    //if no infection, thats it and lets user know that pool was clean
    public static void testResults(int currLevel, boolean infectionFound)
    {
        if(userWantsCaseResults)
        {
            String testResults;
            String testIndent = "";

            for(int i = 1; i < currLevel; i++)
                testIndent+= "   ";

            if(!infectionFound)
                testResults = "Level " + (currLevel) + " - no infection found";
            else
                testResults = "Level " + (currLevel) + " - infection found, proceeding to next level of testing";

            System.out.println(testIndent + testResults);
        }
    }

    //takes currentLevel and the amount of new infections
    //since this is single tests, know that there has to be at least 1, take number so can let user know how many
    public static void singleTestResults(int currLevel, int newInfect)
    {
        if(userWantsCaseResults)
        {
            String testResults = "";
            String testIndent = "";

            for(int i = 1; i < currLevel; i++)
                testIndent+= "   ";

            if(newInfect == 1)
                testResults = "Level " + (currLevel) + " - " + newInfect + " infection was found";
            else
                testResults = "Level " + (currLevel) + " - " + newInfect + " infections were found";

            System.out.println(testIndent + testResults);
        }
    }

    //final results method
    //returns the string containing all the details
    public static String showResults()
    {
        //header reminding the user of what the entered
        String header1 = "INFECTION SIMULATION RESULTS\n";
        String header2 = "\tInputted Values:\n";
        String header3 = printInput();
        String header = header1 + header2 + header3 + "\n\n";

        //section indicating the amount of case 1, case 2, case 3 were found (i.e. how many 0 additional test, 5 additional tests, 10 additional tests
        String instance = "instance";

        if(caseOne == 1)
            instance = "instance";
        else
            instance = "instances";

        String case1Results = "Case (1): " + caseOne + " - " + instance + " requiring no partial tests\n";

        if(caseTwo == 1)
            instance = "instance";
        else
            instance = "instances";

        String case2Results = "Case (2): " + caseTwo + " - " + instance + " requiring five additional tests\n";

        String case3Results = "";
        if(caseThree == 1 && divideCount == 2)
            instance = "instance";
        else
            instance = "instances";
        case3Results = "Case (3): " + caseThree + " - " + instance + " requiring ten additional tests\n";

        String cases = case1Results + case2Results + case3Results;

        //section indicating the amount of false positives and negatives present
        String test = "test was";

        if(falsePositive == 1)
            test = "test was";
        else
            test = "tests were";

        String falsePositiveResults = falsePositive + " false positive " + test + " identified due to the " + testAccuracy + "% test accuracy rate\n";

        if(falseNegative == 1)
            test = "test was";
        else
            test = "tests were";

        String falseNegativeResults = falseNegative + " false negative " + test + " identified due to the " + testAccuracy + "% test accuracy rate\n";

        String falseResults = falsePositiveResults + falseNegativeResults;

        //conclusion section indicating how many infected were found and how many total tests were used
        String infectionTotal = "Out of a population of " + popSize + " people, " + infectionCount + " tested positive for the infection.\n";

        String testTotal = testCount + " tests to screen a population of " + popSize + " people for a disease with " + infectionRate + "% infection rate.";

        return "\n\n" + divider + "\n" + header + cases + divider + "\n" + falseResults + divider + "\n" + infectionTotal + testTotal;

    }

    public static void decision()
    {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("\nWould you like to run the simulation again? (Y/N/SAME - Same uses the same input as previous): ");
        String decision = myScanner.nextLine();
        if(decision.equalsIgnoreCase("Y"))
            simulation();
        else if(decision.equalsIgnoreCase("N"))
            System.out.println("Simulation Over");
        else if(decision.equalsIgnoreCase("SAME"))
            sameSimulation();
        else
        {
            System.out.println("\nPlease only enter 'Y' or 'N'");
            decision();
        }
    }

    //resets all static variables prior to running simulation again
    public static void resetStatic()
    {
        infectionCount = 0;
        testCount = 0;
        caseOne = 0;
        caseTwo = 0;
        caseThree = 0;
        falseNegative = 0;
        falsePositive = 0;
    }

    //will run if user decides to run the simulation again using the same input as the previous one
    public static void sameSimulation()
    {
        resetStatic();
        boolean[] population  = gatherPopulation();
        performTests(population);
        System.out.println(showResults());
        decision();
    }

    //function to run initial simulation and any simulation after in which the user wants new input
    public static void simulation()
    {
        resetStatic();
        gatherData();
        boolean[] population  = gatherPopulation();
        performTests(population);
        System.out.println(showResults());
        decision();
    }
}