package GeneticAlgorithm.Events;

public class Event
{
    public enum type { LECTURE, LECTURE_REVIEW, STUDY ,EXAM_PREP, OTHER } // Types of events
    private int id; // Unique event ID
    private EventTime time; // Time of the event
    private Module module = null; // Which module does it relate to
    private type eventType; // What type of event is it?

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
        // TODO: Add validation IF(User can now create events through input, e.g. lunch)

        this.id = id;
        this.eventType = eventType;
        this.module = module;
    }

    @Override
    public String toString()
    {
        String returnValue = new String();
        returnValue += "[";

        // TODO: Add event sub-types for different study sessions

        switch(this.eventType) // Switch based on eventType
        {
            case LECTURE:
                returnValue += "Lecture: ";
                break;
            case LECTURE_REVIEW:
                returnValue +="Lecture Review: ";
                break;
            case STUDY:
                returnValue += "Study: ";
                break;
            case EXAM_PREP:
                returnValue += "Exam Prep: ";
                break;
            case OTHER:
                returnValue += "Other: ";
                break;
            default:
                throw new IllegalArgumentException("Event must have a valid type."); // Throw appropriate exception
        }

        if(this.module != null) // if the module has been set, i.e. it is not a non-study event like lunch
        {
            returnValue += module; // Add the module to the string
        }

        returnValue += time + "(" + this.getID() + ")" + "]";

        return returnValue;
    }
}
