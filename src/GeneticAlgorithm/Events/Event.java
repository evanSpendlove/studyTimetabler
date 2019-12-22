package GeneticAlgorithm.Events;

public class Event
{
    public enum type { LECTURE, STUDY, OTHER } // Types of events
    private int id;
    private EventTime time; // Time of the event
    private Module module = null; // Which module does it relate to
    private type eventType; // What type is it?

    // Getters and Setters
    public EventTime getTime() {
        return time;
    }

    public void setTime(EventTime time) {
        this.time = time;
    }

    public Module getModule() {
        return module;
    }

    public int getID() { return id; }

    public type getEventType() {
        return eventType;
    }

    // Constructor
    public Event(int id, type eventType, Module module)
    {
        this.id = id;
        this.eventType = eventType;
        this.module = module;
    }

    @Override
    public String toString()
    {
        String returnValue = new String();
        returnValue += "[";

        switch(this.eventType) // Switch based on eventType
        {
            case LECTURE:
                returnValue += "Lecture: ";
                break;
            case STUDY:
                returnValue += "Study: ";
                break;
            case OTHER:
                returnValue += "Other: ";
                break;
            default:
                throw new IllegalArgumentException("Event must have a valid type."); // Throw appropriate exception
        }

        if(this.module != null) //
        {
            returnValue += module;
        }

        returnValue += time + "]";

        return returnValue;
    }
}
