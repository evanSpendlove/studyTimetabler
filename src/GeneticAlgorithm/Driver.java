package GeneticAlgorithm;

import GeneticAlgorithm.Events.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Driver
{
    // Constants
    public static final int POPULATION_SIZE = 9;
    public static final double MUTATION_RATE = 0.1;
    public static final double CROSSOVER_RATE = 0.9;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int NUMBER_OF_ELITE_TIMETABLES = 1;
    public static final String EVENT_START_TIME = "09";
    public static final int NUMBER_OF_EVENT_TIMES = 9;
    public static final int NUMBER_OF_DAYS_PER_WEEK = 5;

    // Instance variables
    private Data data;
    private int timetableNumber = 0;
    private int eventNumber = 1;

    public static void main(String[] args)
    {
        Driver driver = new Driver();
        driver.data = new Data();
        int generationNumber = 0;
        driver.printAvailableData();


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

        while(population.getTimetables().get(0).getFitness() != 1.0) // While we don't have a valid Timetable
        {
            System.out.println("> Generation #" + ++generationNumber);
            System.out.print("  Schedule # |                                              ");
            System.out.print("Classes [dept, class, room, instructor, meeting-time]       ");
            System.out.println("                                          | Fitness   | Conflicts");
            System.out.print("-------------------------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------------------------");

            population = geneticAlgorithm.evolve(population).sortByFitness();
            driver.timetableNumber = 0;
            population.getTimetables().forEach(schedule -> System.out.println("          " + driver.timetableNumber++
                    + "  | " + schedule + "  |  " + String.format("%.5f", schedule.getFitness())
                    + "  |  " + schedule.getNumbOfConflicts()));

            driver.printTimetableAsTable(population.getTimetables().get(0), generationNumber); // Print fittest schedule
            driver.eventNumber = 1;
        }

    }

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
