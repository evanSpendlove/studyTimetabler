package GeneticAlgorithm;

import GeneticAlgorithm.CommandLineIO.UserInput;
import GeneticAlgorithm.Events.Event;

import java.util.ArrayList;
import java.util.Comparator;

/*
    Features to add:

    // Additional constraints:
    TODO: Grouping of study sessions -> boolean input
    TODO: Max number of hours in a row -> int input
    DONE: EX-To-do: Preferences for morning, day, afternoon study -> need to add check if possible, and if not, reduce required termination fitness
    TODO: Different study sub types -> grouping lecture review with lectures (minimise distance from lecture)
        --> Would need to link the event with the module, likely using a unique identifier, i.e. Module Code
    DONE EX-To-do: Even spread of work across days -> average hours per day +/- acceptable variance
    TODO: Add option for user to input the times they are and aren't available
    // Additional major feature -> Assignments -> would need a complete revamp, could be a spin off.

    // Input/Output
    TODO: Add .ics export formatting, where you select the start date of the calendar and the duration of the recurrence
    TODO: Add proper input functions to validate input and also allow user to set key variables, e.g. number of days per week
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

    // User defined Variables
    public enum timesOfDay {MORNING, DAY, EVENING};
    private static int DAY_START_TIME = 9;
    private static int HOURS_PER_DAY = 9;
    private static int DAYS_PER_WEEK = 5;
    private static timesOfDay TIME_PREFERENCE;
    private static int AVAILABLE_PREF_TIMES = 0; // Set elsewhere

    // Instance variables
    private Data data;
    private int timetableNumber = 0;
    private int eventNumber = 1;

    public static void main(String[] args)
    {
        Driver driver = new Driver();
        UserInput inputDriver = new UserInput();

        driver.data = new Data(inputDriver.userInputRequired(), inputDriver);

        System.out.println("Number of days per week: " + Driver.getDaysPerWeek());
        System.out.println("Number of hours per day: " + Driver.getHoursPerDay());
        System.out.println("Day Start time: " + Driver.getDayStartTime());

        int generationNumber = 0;
        driver.printAvailableData();

        boolean solutionFound = false;


        System.out.println("> Generation #" + generationNumber);
        System.out.print("  Timetable # |                                              ");
        System.out.print("Classes [dept, class, room, instructor, meeting-time]       ");
        System.out.println("                                          | Fitness   | Conflicts");
        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.data);
        Population population = new Population(Driver.POPULATION_SIZE, driver.data).sortByFitness();

        population.getTimetables().forEach(timetable -> System.out.println("          " + driver.timetableNumber++
                + "  | " + timetable + "  |  " + String.format("%.5f", timetable.getFitness())
                + "  |  " + timetable.getNumbOfConflicts()));

        driver.printTimetableAsTable(population.getTimetables().get(0), generationNumber); // Print fittest timetable
        driver.eventNumber = 1;

        System.out.println("Preferences available: " + Driver.AVAILABLE_PREF_TIMES + ", TimeSlots Available: " + driver.data.getEventTimes().size());

        while(!solutionFound && generationNumber < 150000) // While we don't have a valid Timetable
        {
            population = geneticAlgorithm.evolve(population).sortByFitness();
            driver.timetableNumber = 0;

            generationNumber++;

            if(population.getTimetables().get(0).getFitness() > 3)
            {
                solutionFound = true;
            }
        }

        System.out.println("> Generation #" + generationNumber);
        System.out.print("  Timetable # |                                              ");
        System.out.print("Classes [dept, class, room, instructor, meeting-time]       ");
        System.out.println("                                          | Fitness   | Conflicts");
        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");

        population.getTimetables().forEach(schedule -> System.out.println("          " + driver.timetableNumber++
                + "  | " + schedule + "  |  " + String.format("%.5f", schedule.getFitness())
                + "  |  " + schedule.getNumbOfConflicts()));

        driver.printTimetableAsTable(population.getTimetables().get(0), generationNumber); // Print fittest schedule
        // driver.eventNumber = 1;

    }

    // Getters & Setters

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

    public static void setAvailablePrefTimes(int availablePrefTimes) {
        AVAILABLE_PREF_TIMES = availablePrefTimes;
    }

    // Utility Functions

    private void printTimetableAsTable(Timetable timetable, int generation)
    {
        ArrayList<Event> events = timetable.getEvents();
        System.out.print("\n                ");
        System.out.println("Event # |  Type | Module Code - Module Name (Study Hours) |  Event Time [ID - Time]" ); // Change
        System.out.print("               ");
        System.out.print("-----------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------");

        // Need to sort and print by day.
        sortEventsByTime(timetable, new int[5]);
        
        events.forEach(x ->
        {
            System.out.print("                    ");
            System.out.print(String.format("%1$02d", eventNumber) + "  |  ");
            System.out.println(x);

            eventNumber++;
        });

        if(timetable.getFitness() == 1) System.out.println("> Solution found in " + (generation + 1) + " generations");
        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    private void printAvailableData()
    {
        System.out.println("Available Modules ==>");
        data.getModules().forEach(x -> System.out.println(x));

        System.out.println("Available Event Times ==>");
        data.getEventTimes().forEach(x -> System.out.println(x));

        System.out.print("-------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.print("-------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");
    }
    
    private void sortEventsByTime(Timetable timetable, int[] dayEndIndices)
    {
        ArrayList<Event> sortedEvents = timetable.getEvents();
        sortedEvents.sort(Comparator.comparing(Event::getTime));

    }
}
