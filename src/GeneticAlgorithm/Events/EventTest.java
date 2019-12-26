package GeneticAlgorithm.Events;

import static org.junit.jupiter.api.Assertions.*;

class EventTest
{

    @org.junit.jupiter.api.Test
    void getTime()
    {
        // Test that an unset EventTime properly points to null
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));
        EventTime testTime = testEvent.getTime();
        assertNull(testTime);

        // Test that the getTime() method properly returns the time, assuming setTime() works
        EventTime newTime = new EventTime("Mon5", "Monday: 05:00");
        testEvent.setTime(newTime);
        assertEquals(testEvent.getTime(), newTime);
    }

    @org.junit.jupiter.api.Test
    void setTime()
    {
        // Test that the setTime() method properly returns the time, assuming getTime() works
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        EventTime newTime = new EventTime("Mon5", "Monday: 05:00");
        testEvent.setTime(newTime);

        assertEquals(testEvent.getTime(), newTime);
    }

    @org.junit.jupiter.api.Test
    void getModule()
    {
        // Test getModule() method, assuming constructor correctly sets module
        Module testModule = new Module("T1", "Test Module", 0);
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        assertEquals(testEvent.getModule(), testModule);
    }

    @org.junit.jupiter.api.Test
    void getID()
    {

    }

    @org.junit.jupiter.api.Test
    void getEventType() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }
}