package GeneticAlgorithm;

import GeneticAlgorithm.Events.Event;

import java.util.ArrayList;

public class Timetable
{
    // Instance variables
    private ArrayList<Event> events;
    private Data data;
    private int eventNumb = 0;
    private int numbOfConflicts = 0;
    private boolean isFitnessChanged = true;
    private double fitness = -1;

    // Getters
    public Data getData() {
        return data;
    }

    public int getNumbOfConflicts() {
        return numbOfConflicts;
    }

    public double getFitness()
    {
        if(isFitnessChanged)
        {
            fitness = calculateFitness();
            isFitnessChanged = false;
        }
        return fitness;
    }

    public ArrayList<Event> getEvents()
    {
        isFitnessChanged = true;
        return events;
    }

    // Constructor
    public Timetable(Data data)
    {
        this.data = data;
        this.events =  new ArrayList<>(data.getNumberOfEvents());
    }

    // Utility methods

    public Timetable initialize()
    {
        for(int module = 0; module < data.getModules().size(); module++) // For each module
        {
            for(int studySession = 0; studySession < data.getModules().get(module).getHourPerWeek(); studySession++) // For each hour of study needed per week
            {
                // Create a new study session of 1 hour duration
                Event newStudySession = new Event(eventNumb++, Event.type.STUDY, data.getModules().get(module));
                newStudySession.setTime(data.getEventTimes().get((int) (data.getEventTimes().size() * Math.random()))); // Set a random study time
                events.add(newStudySession);
            }
        }

        return this;
    }

    private double calculateFitness()
    {
        numbOfConflicts = 0;

        for(int i = 0; i < events.size(); i++)
        {
            for(int j = 0; j < events.size(); j++)
            {
                if(events.get(i).getTime() == events.get(j).getTime() && events.get(i).getID() != events.get(j).getID()) // If two unique events occur at the same time
                {
                    numbOfConflicts++;
                }
            }
        }

        return (1 / (double) (numbOfConflicts + 1));
    }

    @Override
    public String toString()
    {
        String returnValue = new String();

        for(int i = 0; i < events.size() - 1; i++)
        {
            returnValue += events.get(i) + ",";
        }
        returnValue += events.get(events.size() - 1);

        return returnValue;
    }

}
