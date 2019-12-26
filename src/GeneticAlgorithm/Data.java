package GeneticAlgorithm;

import GeneticAlgorithm.Events.Event;
import GeneticAlgorithm.Events.EventTime;
import GeneticAlgorithm.Events.Module;

import java.util.ArrayList;
import java.util.Arrays;

public class Data
{
    // Instance variables
    private ArrayList<Module> modules;
    private ArrayList<EventTime> eventTimes;
    private int numberOfEvents = 0;
    private int numberOfPreferredTimes = 0;

    // Getters
    public ArrayList<Module> getModules() {
        return modules;
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
        // Create new EventTimes
        initialiseEventTimes();

        initialiseModules();

        // Remove lectures from available times
        removeLecturesFromTimetable();

        // Remove lunch breaks from available times
        // TODO: Remove lunch breaks from available times

        checkPreferencePossible();

        System.out.println("Number of Events: " + numberOfEvents + ", Number of Preferred Times: " + numberOfPreferredTimes);

        Driver.AVAILABLE_PREF_TIMES = numberOfPreferredTimes;

        return this;
    }

    // Constructor
    public Data() { initialise();}

    // Utility Methods

    private void initialiseModules()
    {
        // Create new Modules
        Module comp20050 = new Module("COMP20050", "Software Engineering Project 2", 0);
        Module comp20180 = new Module("COMP20180", "Intro to Operating Systems", 2);
        Module comp20280 = new Module("COMP20280", "Data Structures", 3);
        Module comp20290 = new Module("COMP20290", "Algorithms", 3);
        Module mst20050 = new Module("MST20050", "Linear Algebra II", 2);
        Module phil10110 = new Module("PHIL10110", "Intro to Eastern Philosophy", 5); // So much reading...

        modules = new ArrayList<Module>(Arrays.asList(comp20050, comp20180, comp20280, comp20290, mst20050, phil10110));
        modules.forEach(x -> numberOfEvents += x.getHourPerWeek());
    }

    private void initialiseEventTimes()
    {
        eventTimes = new ArrayList<EventTime>();

        // Create new EventTimes
        for(int day = 0; day < Driver.NUMBER_OF_DAYS_PER_WEEK; day++) // For each day of the week
        {
            for(int time = 0; time < Driver.NUMBER_OF_EVENT_TIMES; time++) // For each of the possible time slots
            {
                int currentTime = Driver.EVENT_START_TIME + time; // Gets the current time

                EventTime newEventTime = constructEventTime(day, currentTime);

                eventTimes.add(newEventTime);
            }
        }
    }

    private void removeLecturesFromTimetable()
    {
        // TODO: Add additional modules when timetable is updated correctly

        EventTime comp20180_L1 = new EventTime("Mon12", "Monday: 12:00");
        EventTime comp20180_L2 = new EventTime("Wed11", "Wednesday: 11:00");
        EventTime comp20180_L3a = new EventTime("Fri14", "Friday: 14:00");
        EventTime comp20180_L3b = new EventTime("Fri15", "Friday: 15:00");

        EventTime comp20050_L1 = new EventTime("Tue13", "Tuesday: 13:00");
        EventTime comp20050_L2a = new EventTime("Fri09", "Friday: 09:00");
        EventTime comp20050_L2b = new EventTime("Fri10", "Friday: 10:00");
        EventTime comp20050_L3 = new EventTime("Fri13", "Friday: 13:00");

        EventTime phil10110_L1 = new EventTime("Tue09", "Tuesday: 09:00");
        EventTime phil10110_L2 = new EventTime("Thur09", "Thursday: 09:00");
        EventTime phil10110_L3 = new EventTime("Thur10", "Thursday: 10:00");

        EventTime mst20050_L1 = new EventTime("Mon14", "Monday: 14:00");
        EventTime mst20050_L2 = new EventTime("Tue12", "Tuesday: 12:00");
        EventTime mst20050_L3 = new EventTime("Wed16", "Wednesday: 16:00");

        ArrayList<EventTime> lectures = new ArrayList<EventTime>
                (Arrays.asList
                        (comp20180_L1, comp20180_L2, comp20180_L3a, comp20180_L3b, comp20050_L1, comp20050_L2a, comp20050_L2b,
                                comp20050_L3, phil10110_L1, phil10110_L2, phil10110_L3, mst20050_L1, mst20050_L2, mst20050_L3
                        ));

        eventTimes.removeAll(lectures); // Remove all lectures as these time slots are taken
    }

    private void checkPreferencePossible()
    {
        int startHour = Driver.EVENT_START_TIME + ((Driver.NUMBER_OF_EVENT_TIMES * Driver.TIME_PREFERENCE)/3);
        int endHour = startHour + (Driver.NUMBER_OF_EVENT_TIMES/3);

        System.out.println("Pref Start Time: " + startHour + ", Pref End Time: " + endHour);

        for(int i = 0; i < eventTimes.size(); i++)
        {
            int currentTime = eventTimes.get(i).getTimeFromString(eventTimes.get(i).getTime());

            if(currentTime >= startHour && currentTime <= endHour) // If the current time lies within the preferred range
            {
                this.numberOfPreferredTimes++; // Increment number of preferred times that are available
            }
        }
    }

    private void removeEventTime(int in_day, int in_time)
    {
        EventTime target = constructEventTime(in_day, in_time); // Generate target EventTime
        eventTimes.remove(target); // Remove target
    }

    private EventTime constructEventTime(int day, int time)
    {
        String newEventTime = new String();
        String newEventID = new String();

        // First add the day
        switch(day)
        {
            case 0:
                newEventTime += "Monday: ";
                newEventID += "Mon";
                break;
            case 1:
                newEventTime += "Tuesday: ";
                newEventID += "Tue";
                break;
            case 2:
                newEventTime += "Wednesday: ";
                newEventID += "Wed";
                break;
            case 3:
                newEventTime += "Thursday: ";
                newEventID += "Thur";
                break;
            case 4:
                newEventTime += "Friday: ";
                newEventID += "Fri";
                break;
            default:
                throw new IllegalArgumentException("Invalid day of the week");
        }

        // Then, add the time
        if(time == Driver.EVENT_START_TIME)
        {
            newEventTime += "0";
            newEventTime += Driver.EVENT_START_TIME;
            newEventID += "0";
            newEventID +=  + Driver.EVENT_START_TIME;
        }
        else
        {
            newEventTime += Integer.toString(time);
            newEventID += Integer.toString(time);
        }

        newEventTime += ":00";

        return new EventTime(newEventID, newEventTime);
    }

}
