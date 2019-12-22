package GeneticAlgorithm.Events;

public class EventTime
{
    private String id;
    private String time;

    public String getId() {
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

        if(o.getId().compareTo(this.getId()) == 0)
        {
            if(o.getTime().compareTo(this.getTime()) == 0)
            {
                return true;
            }
        }

        return false;
    }

}
