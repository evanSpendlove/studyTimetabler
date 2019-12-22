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
}
