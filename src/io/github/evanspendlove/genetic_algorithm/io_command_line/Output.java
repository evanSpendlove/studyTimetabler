package io.github.evanspendlove.genetic_algorithm.io_command_line;

import io.github.evanspendlove.genetic_algorithm.Data;
import io.github.evanspendlove.genetic_algorithm.Driver;
import io.github.evanspendlove.genetic_algorithm.events.Event;
import io.github.evanspendlove.genetic_algorithm.events.EventTime;
import io.github.evanspendlove.genetic_algorithm.Population;
import io.github.evanspendlove.genetic_algorithm.Timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Output
{
    // Class instance variables
    private int generationNumber = -1;
    private Timetable timetable;
    private Population population;
    Driver driver;
    Data data;

    // Getters & Setters
    private void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    private void setData(Data data) {this.data = data;}

    public Population getPopulation() {
        return population;
    }

    private void setPopulation(Population population) {
        this.population = population;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    private void setGenerationNumber(int generationNumber)
    {
        if(generationNumber >= 0)
        {
            this.generationNumber = generationNumber;
        }
    }

    public Timetable getTimetable() {
        return timetable;
    }

    private void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    // Overloaded Constructor
    public Output(int generationNumber, Population population)
    {
        setGenerationNumber(generationNumber);
        setPopulation(population);
    }

    public Output(int generationNumber, Timetable timetable)
    {
        setGenerationNumber(generationNumber);
        setTimetable(timetable);
    }

    public Output(Driver driver, Data data)
    {
        setDriver(driver);
        setData(data);
    }

    // Output print methods

    public void printTimetableAsTable(Timetable timetable, int generation)
    {
        ArrayList<Event> events = timetable.getEvents();
        System.out.print("\n                ");
        System.out.println("Event # |  Type | Module Code - Module Name (Study Hours) |  Event Time [ID - Time]" ); // Change
        System.out.print("               ");
        System.out.print("-----------------------------------------------------------------------------------------");
        System.out.println("-----------------------------------");

        // Need to sort and print by day.
        timetable = sortEventsByTime(timetable);

        events.forEach(x ->
        {
            System.out.print("                    ");
            System.out.print(String.format("%1$02d", driver.getEventNumber()) + "  |  ");
            System.out.println(x);

            driver.setEventNumber(driver.getEventNumber() + 1); // Increment the eventNumber
        });

        driver.setEventNumber(1);

        if(timetable.getFitness() == 1)
        {
            System.out.println("> Solution found in " + (generation + 1) + " generations");
        }

        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");

        System.out.println("");

        printTimetableInCalendarFormat(timetable, generation);
    }

    public void printAvailableData()
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

    public void printTimetableInCalendarFormat(Timetable timetable, int generation)
    {
        // Variable declaration
        timetable = sortEventsByTime(timetable); // Sort the timetable by day/time
        ArrayList<ArrayList<Event>> splitEvents = splitEventsByDay(timetable); // Split events into days

        ArrayList<EventTime> eventTimes = generateAllEventTimes();
        ArrayList<String> calendar = new ArrayList<String>(); // ArrayList of Strings

        ArrayList<String> daysOfTheWeek = new ArrayList<String>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        int dayLength = (eventTimes.size() / Driver.getDaysPerWeek());

        for(int i = 0; i < dayLength; i++)
        {
            calendar.add(new String(""));
        }

        // 5 * 9 time slots, 9 per day.

        for(int timeSlot = 0; timeSlot < dayLength; timeSlot++) // For each time slot in eventTimes
        {
            calendar.set(timeSlot, eventTimes.get(timeSlot).timeToString() + "\t"); // Add the current timeslot to the relevant String

            for(int day = 0; day < Driver.getDaysPerWeek(); day++) // For each day of the week
            {
                String result = new String("");
                int eventIndex = containsEventAtTime(eventTimes.get(timeSlot + (day * Driver.getHoursPerDay())), splitEvents.get(day)); // Get the index of the event at this time on this day

                if(eventIndex != -1) // Add the event
                {
                    result += splitEvents.get(day).get(eventIndex);
                }
                else // Or add a blank event
                {
                    result += "                               ";
                }

                calendar.set(timeSlot, calendar.get(timeSlot) + result); // Add the result to the String at timeSlot index.
            }
        }

        System.out.print("     \t"); // Print a tab for readability

        for(int day = 0; day < Driver.getDaysPerWeek(); day++)
        {
            System.out.print(String.format("%16s", daysOfTheWeek.get(day)) + String.format("%16s", ""));
        }

        System.out.println();
        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");

        // TODO: Learn & Fix String Formatting for printing -> {%, $, etc.}

        for(int timeSlot = 0; timeSlot < dayLength; timeSlot++) // For each time slot in eventTimes
        {
            System.out.println(String.format("%34s", calendar.get(timeSlot)));
        }

        System.out.println(""); // Readability
    }

    public void printPopulation(Population population, int generationNumber)
    {
        System.out.println("> Generation #" + generationNumber);
        System.out.print("  Timetable # |                                              ");
        System.out.print("Events [Type: {ModuleCode} [time (eventTimeID)]       ");
        System.out.print(String.format("%324s", ""));
        System.out.println("                                          | Fitness   | Conflicts\t");
        System.out.print("-------------------------------------------------------------------------------------------");
        System.out.print("----------------------------------------------------------------------------------------------" +
                "----------------------------------------------------------------------------------------------" +
                "----------------------------------------------------------------------------------------------" +
                "--------------------------------------------");
        System.out.println("----------------------------------------------------------------------------------------------");

        population.getTimetables().forEach(schedule -> System.out.println(String.format("%500s", ("          " + driver.incrementTimetableNumber()
                + "  | " + schedule + "  |  " + String.format("%.5f", schedule.getFitness())
                + "  |  " + schedule.getNumbOfConflicts()))) );

        driver.setTimetableNumber(1);

        System.out.println();

        printTimetableAsTable(population.getTimetables().get(0), generationNumber); // Print fittest schedule
    }

    // Functions for formatting timetable before printing

    private Timetable sortEventsByTime(Timetable timetable)
    {
        Timetable sortedTimetable = timetable;
        ArrayList<Event> sortedEvents = sortedTimetable.getEvents();
        sortedEvents.sort(Comparator.comparing(Event::getTime));

        return sortedTimetable;
    }

    private ArrayList<ArrayList<Event>> splitEventsByDay(Timetable timetable)
    {
        ArrayList<ArrayList<Event>> splitEvents = new ArrayList<>();

        for(int i = 0; i < Driver.getDaysPerWeek(); i++) // Initialise the ArrayList<>
        {
            splitEvents.add(new ArrayList<Event>());
        }

        ArrayList<Event> events = timetable.getEvents();

        for(int i = 0; i < events.size(); i++)
        {
            int curDay = events.get(i).getEventDayAsInt();
            splitEvents.get(curDay).add(events.get(i));
        }

        return splitEvents;
    }

    private int containsEventAtTime(EventTime time, ArrayList<Event> day)
    {
        for(int i = 0; i < day.size(); i++) // For every event in the day
        {
            if(day.get(i).getTime().compareTo(time) == 0) // Check if the event time matches the desired time
            {
                return i; // If it does, return the index of the event
            }
        }

        return -1; // Otherwise return -1
    }

    private ArrayList<EventTime> generateAllEventTimes()
    {
        ArrayList<EventTime> eventTimes = new ArrayList<EventTime>();

        // Create new EventTimes
        for(int day = 0; day < Driver.getDaysPerWeek(); day++) // For each day of the week
        {
            for(int time = 0; time < Driver.getHoursPerDay(); time++) // For each of the possible time slots
            {
                int currentTime = Driver.getDayStartTime() + time; // Gets the current time

                EventTime newEventTime = Data.constructEventTime(day, currentTime);

                eventTimes.add(newEventTime);
            }
        }

        return eventTimes;
    }
}
