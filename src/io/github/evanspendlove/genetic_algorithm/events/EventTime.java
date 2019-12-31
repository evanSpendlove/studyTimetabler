package io.github.evanspendlove.genetic_algorithm.events;

public class EventTime implements Comparable
{
    private String id;
    private String time;

    public String getID() {
        return id;
    }

    public String getTime() {
        return time;
    }

    // Constructor
    public EventTime(String id, String time)
    {
        this.id = id;
        this.time = time;
    }

    @Override
    public String toString()
    {
        return "[" + id + " - " + time + "]";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof EventTime)) return false;
        EventTime o = (EventTime) obj;

        if(o.getID().compareTo(this.getID()) == 0) // If the IDs of the eventTimes are the same
        {
            if(o.getTime().compareTo(this.getTime()) == 0) // If their times are the same
            {
                return true;
            }
        }

        return false;
    }

    public int getDayFromID(String id)
    {
        if(id.contains("Mon"))
        {
            return 0;
        }
        else if(id.contains("Tue"))
        {
            return 1;
        }
        else if(id.contains("Wed"))
        {
            return 2;
        }
        else if(id.contains("Thur"))
        {
            return 3;
        }
        else if(id.contains("Fri"))
        {
            return 4;
        }
        else if(id.contains("Sat"))
        {
            return 5;
        }
        else if(id.contains("Sun"))
        {
            return 6;
        }
        else
        {
            throw new IllegalArgumentException("ID must contain a valid day of the week.");
        }
    }

    public int getTimeFromString(String dayAndTime)
    {
        int firstColon = dayAndTime.indexOf(':');
        String onlyTime = dayAndTime.substring(firstColon+1);
        int secondColon = onlyTime.indexOf(':');

        return Integer.parseInt(onlyTime.substring(secondColon - 2, secondColon));
    }

    @Override
    public int compareTo(Object obj)
    {
        EventTime secondObj = (EventTime) obj;
        int day1 = getDayFromID(this.getID());
        int day2 = getDayFromID(secondObj.getID());


        if(day1 > day2) // Compare based on day
        {
            return 1;
        }
        else if(day1 < day2)
        {
            return -1;
        }
        else // Two events must be on the same day.
        {
            int time1 = getTimeFromString(this.getTime());
            int time2 = getTimeFromString(secondObj.getTime());

            if(time1 > time2) // Compare based on time, given day is the same
            {
                return 1;
            }
            else if(time1 < time2)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }

}
