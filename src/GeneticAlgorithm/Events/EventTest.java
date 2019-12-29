package GeneticAlgorithm.Events;

import static org.junit.jupiter.api.Assertions.*;

// TODO: Use reflection api to test classes (setters) without relying on previous methods

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
        assertEquals(newTime, testEvent.getTime());
    }

    @org.junit.jupiter.api.Test
    void setTime()
    {
        // Test that the setTime() method properly returns the time, assuming getTime() works
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        EventTime newTime = new EventTime("Mon5", "Monday: 05:00");
        testEvent.setTime(newTime);

        assertEquals(newTime, testEvent.getTime());
    }

    @org.junit.jupiter.api.Test
    void getModule()
    {
        // Test getModule() method, assuming constructor correctly sets module
        Module testModule = new Module("T1", "Test Module", 0);
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        assertEquals(testModule, testEvent.getModule());
    }

    @org.junit.jupiter.api.Test
    void getID()
    {
        // Assumes the constructor for the Event class works correctly
        Module testModule = new Module("T1", "Test Module", 0);
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        assertEquals(0, testEvent.getID());

        try
        {
            Event testEvent2 = new Event(-1, Event.type.STUDY, testModule); // Testing that IDs cannot be negative
        }
        catch(Exception ex)
        {
            assertEquals("Event IDs cannot be negative.", ex.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void getEventType()
    {
        Module testModule = new Module("T1", "Test Module", 0);
        Event testStudy = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        assertEquals(Event.type.STUDY, testStudy.getEventType());

        // Test lecture event type
        Event testLecture = new Event(1, Event.type.LECTURE, testModule);
        assertEquals(Event.type.LECTURE, testLecture.getEventType());

        // Test Exam Prep event type
        Event testExamPrep = new Event(2, Event.type.EXAM_PREP, testModule);
        assertEquals(Event.type.EXAM_PREP, testExamPrep.getEventType());

        // Test Lecture Review event type
        Event testLectReview = new Event(3, Event.type.LECTURE_REVIEW, testModule);
        assertEquals(Event.type.LECTURE_REVIEW, testLectReview.getEventType());

        // Test Other event type
        Event testOther = new Event(4, Event.type.OTHER, testModule);
        assertEquals(Event.type.OTHER, testOther.getEventType());
    }

    @org.junit.jupiter.api.Test
    void testToString()
    {
        // Test for null EventTime
        Module testModule = new Module("T1", "Test Module", 0);
        Event testEvent = new Event(0, Event.type.STUDY, new Module("T1", "Test Module", 0));

        assertEquals("[Study: {T1 - Test Module (0)}null(0)]", testEvent.toString());

        // Testing with EventTime set
        testEvent.setTime(new EventTime("Mon5", "Monday: 05:00")); // Set a time
        assertEquals("[Study: {T1 - Test Module (0)}[Mon5 - Monday: 05:00](0)]", testEvent.toString());
    }
}