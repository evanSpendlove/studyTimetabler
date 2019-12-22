package GeneticAlgorithm;

import GeneticAlgorithm.Events.Event;
import GeneticAlgorithm.Events.EventTime;

import java.util.ArrayList;

public class Data
{
    // Instance variables
    private ArrayList<Module> modules;
    private ArrayList<Event> events;
    private ArrayList<EventTime> eventTimes;
    private int numberOfEvents = 0;

    // Getters
    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<EventTime> getEventTimes() {
        return eventTimes;
    }

    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    // Initialise method
    public Data initialise()
    {


        return this;
    }

    // Constructor
    public Data() { initialise();}

}
