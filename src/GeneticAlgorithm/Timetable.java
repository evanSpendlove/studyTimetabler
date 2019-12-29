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
        int availablePreferredTimes = Driver.getAvailablePrefTimes();
        int weights[] = new int[3];
        weights[0] = 1; // Average Hours preference
        weights[1] = 1; // Time of day preference
        weights[2] = 5; // Two events occurring simultaneously is a critical error

        int averageHoursPerDay = this.getEvents().size() / Driver.getDaysPerWeek();
        int[] hoursPerDay = new int[Driver.getDaysPerWeek()];

        for(int i = 0; i < events.size(); i++)
        {
            if(availablePreferredTimes > 0)
            {
                int startHour;
                int endHour;
                int offset = Driver.getHoursPerDay() / 3;

                switch(Driver.getTimePreference())
                {
                    case MORNING:
                        startHour = Driver.getDayStartTime();
                        endHour = Driver.getDayStartTime() + 1 * offset;
                        break;
                    case DAY:
                        startHour = Driver.getDayStartTime() + 1 * offset;
                        endHour = startHour + 1 * offset;
                        break;
                    case EVENING:
                        startHour = Driver.getDayStartTime() + 2 * offset;
                        endHour = Driver.getDayStartTime() + Driver.getHoursPerDay(); // End of Day
                        break;
                    default:
                        throw new IllegalArgumentException("Driver: Time Preference is set to an invalid value.");
                }

                int time = events.get(i).getEventTimeAsInt();

                if(time < startHour || time > endHour) // If the event lies outside the desired time range
                {
                    numbOfConflicts = numbOfConflicts + weights[1];
                }
                else
                {
                    availablePreferredTimes--;
                }
            }

            int curDay = events.get(i).getEventDayAsInt();

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
