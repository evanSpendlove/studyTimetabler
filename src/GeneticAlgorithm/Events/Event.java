package GeneticAlgorithm.Events;

public class Event
{
    public enum type { LECTURE, STUDY, OTHER } // Types of events
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

    public void setModule(Module module) {
        this.module = module;
    }

    public type getEventType() {
        return eventType;
    }

    public void setEventType(type eventType) {
        this.eventType = eventType;
    }

    // Overloaded Constructor
    public Event(type eventType, Module module, EventTime time)
    {
        this.eventType = eventType;
        this.module = module;
        this.time = time;
    }

    // For non lecture-related events, i.e. lunch
    public Event(type eventType, EventTime time)
    {
        this.eventType = eventType;
        this.time = time;
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
