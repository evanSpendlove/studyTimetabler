package io.github.evanspendlove.genetic_algorithm;

import io.github.evanspendlove.genetic_algorithm.io_command_line.Output;
import io.github.evanspendlove.genetic_algorithm.io_command_line.UserInput;

/*
    Features to add:

    // Additional constraints:
    TODO: Grouping of study sessions -> boolean input
    TODO: Max number of hours in a row -> int input
    DONE: EX-To-do: Preferences for morning, day, afternoon study -> need to add check if possible, and if not, reduce required termination fitness
    TODO: Different study sub types -> grouping lecture review with lectures (minimise distance from lecture)
        --> Would need to link the event with the module, likely using a unique identifier, i.e. Module Code
    DONE EX-To-do: Even spread of work across days -> average hours per day +/- acceptable variance
    DONE EX-To-do: Add option for user to input the times they are and aren't available
    // Additional major feature -> Assignments -> would need a complete revamp, could be a spin off.

    // Input/Output
    TODO: Add .ics export formatting, where you select the start date of the calendar and the duration of the recurrence
    DONE EX-TO-DO: Add proper input functions to validate input and also allow user to set key variables, e.g. number of days per week
    TODO: Add GUI for input/output
 */

public class Driver
{
    // Constants
    public static final int POPULATION_SIZE = 9;
    public static final double MUTATION_RATE = 0.1;
    public static final double CROSSOVER_RATE = 0.9;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int NUMBER_OF_ELITE_TIMETABLES = 1;
    public static final int ACCEPTABLE_FITNESS = 3;
    public static final int MAX_GENERATIONS = 150000;

    // User defined Variables
    public enum timesOfDay {MORNING, DAY, EVENING};
    private static int DAY_START_TIME = 9;
    private static int HOURS_PER_DAY = 9;
    private static int DAYS_PER_WEEK = 5;
    private static timesOfDay TIME_PREFERENCE;
    private static int AVAILABLE_PREF_TIMES = 0; // Set elsewhere

    // Instance variables
    private Data data;
    private int timetableNumber = 1;
    private int eventNumber = 1;

    public static void main(String[] args)
    {
        Driver driver = new Driver();
        UserInput inputDriver = new UserInput();
        driver.data = new Data(inputDriver.userInputRequired(), inputDriver);
        Output output = new Output(driver, driver.data);

        int generationNumber = 0;
        boolean solutionFound = false;

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.getData()); // Create new Genetic Algorithm
        Population population = new Population(Driver.POPULATION_SIZE, driver.getData()).sortByFitness(); // Create new population

        System.out.println("Number of days per week: " + Driver.getDaysPerWeek());
        System.out.println("Number of hours per day: " + Driver.getHoursPerDay());
        System.out.println("Day Start time: " + Driver.getDayStartTime());
        System.out.println("Preferences available: " + Driver.getAvailablePrefTimes() + ", TimeSlots Available: " + driver.data.getEventTimes().size());

        output.printAvailableData(); // Print the available data for clarity

        output.printPopulation(population, generationNumber); // Output the initial population

        while(!solutionFound && generationNumber < Driver.MAX_GENERATIONS) // While we don't have a valid Timetable && the maxGenerations is not reached
        {
            population = geneticAlgorithm.evolve(population).sortByFitness(); // Evolve the current population
            generationNumber++; // Increment the generation number

            if(population.getTimetables().get(0).getFitness() > Driver.ACCEPTABLE_FITNESS) // If the fittest timetable has an acceptable fitness
            {
                solutionFound = true; // The solution has been found
            }
        }

        output.printPopulation(population, generationNumber); // Print the population with the fittest timetable
    }

    // Getters & Setters

    public int getEventNumber(){ return this.eventNumber;}

    public void setEventNumber(int num)
    {
        if(num > 0)
        {
            this.eventNumber = num;
        }
        else
        {
            throw new IllegalArgumentException("Event Number cannot be < 1.");
        }
    }

    public Data getData()
    {
        return this.data;
    }

    public int getTimetableNumber()
    {
        return this.timetableNumber;
    }

    public void setTimetableNumber(int number)
    {
        this.timetableNumber = number;
    }

    public int incrementTimetableNumber()
    {
        return this.timetableNumber++;
    }

    public static int getDayStartTime() {
        return DAY_START_TIME;
    }

    public static void setDayStartTime(int eventStartTime) {
        DAY_START_TIME = eventStartTime;
    }

    public static int getHoursPerDay() {
        return HOURS_PER_DAY;
    }

    public static void setHoursPerDay(int hoursPerDay) {
        HOURS_PER_DAY = hoursPerDay;
    }

    public static int getDaysPerWeek() {
        return DAYS_PER_WEEK;
    }

    public static void setDaysPerWeek(int daysPerWeek) {
        DAYS_PER_WEEK = daysPerWeek;
    }

    public static timesOfDay getTimePreference() {
        return TIME_PREFERENCE;
    }

    public static void setTimePreference(int timePreference)
    {
        switch(timePreference)
        {
            case 0:
                TIME_PREFERENCE = timesOfDay.MORNING;
                break;
            case 1:
                TIME_PREFERENCE = timesOfDay.DAY;
                break;
            case 2:
                TIME_PREFERENCE = timesOfDay.EVENING;
                break;
            default:
                throw new IllegalArgumentException("Time of Day Preference must be between range {0, 2} inclusive.");
        }
    }

    public static int getAvailablePrefTimes() {
        return AVAILABLE_PREF_TIMES;
    }

    public static void setAvailablePrefTimes(int availablePrefTimes)
    {
        if(availablePrefTimes > 0)
        {
            AVAILABLE_PREF_TIMES = availablePrefTimes;
        }
        else
        {
            throw new IllegalArgumentException("Number of available preferred times must be positive.");
        }
    }
}
