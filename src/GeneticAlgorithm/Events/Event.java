package GeneticAlgorithm.Events;

public class Event
{
    public enum type { LECTURE, LECTURE_REVIEW, STUDY ,EXAM_PREP, OTHER } // Types of events
    private int id = -1; // Unique event ID
    private EventTime time = null; // Time of the event
    private Module module = null; // Which module does it relate to
    private type eventType = null; // What type of event is it?

    // Getters and Setters
    public EventTime getTime() {
        return time;
    }

    public void setTime(EventTime time)
    {
        // TODO: Add regex or parsing check to ensure matches certain format: DAY: TIME {XX:00}

        this.time = time;
    }

    public Module getModule() {
        return module;
    }

    public int getID() { return id; }

    public type getEventType() {
        return eventType;
    }

    private void setValidID(int id)
    {
        if(id >= 0)
        {
            this.id = id;
        }
        else
        {
            throw new IllegalArgumentException("Event IDs cannot be negative.");
        }
    }


    // Constructor
    public Event(int id, type eventType, Module module)
    {
        // TODO: Add validation IF(User can now create events through input, e.g. lunch)

        setValidID(id);
        this.eventType = eventType;
        this.module = module;
    }

    // Utility methods

    public int getEventDayAsInt()
    {
        return this.getTime().getDayFromID(this.getTime().getTime());
    }

    public int getEventTimeAsInt()
    {
        return this.getTime().getTimeFromString(this.getTime().getTime());
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

        returnValue += time.idToString() + "(" + this.getID() + ")" + "]";

        return returnValue;
    }
}
