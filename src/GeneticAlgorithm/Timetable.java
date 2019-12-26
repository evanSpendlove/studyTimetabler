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
        int availablePreferredTimes = Driver.AVAILABLE_PREF_TIMES;
        int weights[] = new int[3];
        weights[0] = 1; // Average Hours preference
        weights[1] = 1; // Time of day preference
        weights[2] = 5; // Two events occurring simultaneously is a critical error

        int averageHoursPerDay = this.getEvents().size() / Driver.NUMBER_OF_DAYS_PER_WEEK;
        int[] hoursPerDay = new int[5];

        for(int i = 0; i < events.size(); i++)
        {
            if(availablePreferredTimes > 0)
            {
                int startHour = Driver.EVENT_START_TIME + ((Driver.NUMBER_OF_EVENT_TIMES * Driver.TIME_PREFERENCE)/3);
                int endHour = startHour + (Driver.NUMBER_OF_EVENT_TIMES/3);

                int time = events.get(i).getTime().getTimeFromString(events.get(i).getTime().getTime());

                if(time < startHour || time > endHour) // If the event lies outside the desired time range
                {
                    numbOfConflicts = numbOfConflicts + weights[1];
                }
                else
                {
                    availablePreferredTimes--;
                }
            }

            int curDay = events.get(i).getTime().getDayFromID(events.get(i).getTime().getTime());

            hoursPerDay[curDay]++;

            for(int j = 0; j < events.size(); j++)
            {
                if(events.get(i).getTime() == events.get(j).getTime() && events.get(i).getID() != events.get(j).getID()) // If two unique events occur at the same time
                {
                    numbOfConflicts = numbOfConflicts + weights[2];
                }
            }
        }

        for(int i = 0; i < hoursPerDay.length; i++)
        {
            if(hoursPerDay[i] > averageHoursPerDay)
            {
                numbOfConflicts = numbOfConflicts + weights[0];
            }
        }

        return (5 / (double) (numbOfConflicts + 1));
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
