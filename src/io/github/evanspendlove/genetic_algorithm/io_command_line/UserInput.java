package io.github.evanspendlove.genetic_algorithm.io_command_line;

import io.github.evanspendlove.genetic_algorithm.Data;
import io.github.evanspendlove.genetic_algorithm.Driver;
import io.github.evanspendlove.genetic_algorithm.events.EventTime;
import io.github.evanspendlove.genetic_algorithm.events.Module;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInput
{
    // Class instance variables
    public static boolean isWindows = System.getProperty("os.name").startsWith("Windows");
    private ArrayList<Module> modules = new ArrayList<>();
    private ArrayList<EventTime> lectureTimes = new ArrayList<>();
    private ArrayList<EventTime> busyTimes = new ArrayList<>();

    // Getters
    public ArrayList<Module> getModules() {
        return modules;
    }

    public ArrayList<EventTime> getLectureTimes() {
        return lectureTimes;
    }

    public ArrayList<EventTime> getBusyTimes() {
        return busyTimes;
    }

    public boolean userInputRequired()
    {
        Scanner input = new Scanner(System.in);
        int required = validBoundedInteger(-1, 2, "Do you wish to enter your own modules/lecture or proceed with stored values?\n{0 : No, 1 : Yes} ", "Error: Input must lie within range: {0,1} inclusive.\n");

        if(required == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void initializeTimetable(boolean userDefined)
    {


        defineTimetable(); // Sets the user-defined variables for the timetable bounds
        if(userDefined)
        {
            setModules(); // Lets the user create a series of modules, along with their corresponding lectures
            setBusyTimes(); // Lets the user set the times they are busy
        }
    }

    /*
        defineTimetable() method:
            - Takes no inputs, void return type
            - Lets the user defined the constraints of the timetable on program start
     */
    private void defineTimetable()
    {
        // Set the user defined variables
        int numberOfDaysPerWeek = validBoundedInteger(0, 8, "Please enter the number of days in your desired week: ", "Error: Invalid input, must lie within range {1, 7} inclusive.\n");
        int dayStartTime = validBoundedInteger(0, 24, "Please enter the start time (hour) of your day: ", "Invalid start time, must be within range: {1, 23} inclusive.\n");
        int hoursPerDay = validBoundedInteger(0, (24-dayStartTime), "Please enter the number of working hours per day: ", "Error: too few/many hours per day.");
        int timePreference = validBoundedInteger(-1, 3, "Please enter your time preference: (integer)\n{0 : Morning, 1 : Daytime, 2 : Evening\n > ", "Error, must be within range: {0, 3} inclusive");

        // Set static Driver variables
        Driver.setDaysPerWeek(numberOfDaysPerWeek);
        Driver.setDayStartTime(dayStartTime);
        Driver.setHoursPerDay(hoursPerDay);
        Driver.setTimePreference(timePreference);
    }

    // Methods for handling module creation

    private Module createModule()
    {
        // Variables for reading and storing input
        Scanner readInput = new Scanner(System.in);
        String moduleCode = new String();
        String moduleName = new String();

        printInstruction("Please enter the module code for this module: "); // Prompt user
        readInput.next(moduleCode); // Store input

        printInstruction("Please enter the module name for this module: "); // Prompt user
        readInput.next(moduleName); // Store input

        int moduleHoursPerWeek = validBoundedInteger(-1, 13, "Please enter the number of hour of study per week required for this module: ", "Error: Hours of study required must lie within range: {0, 12} inclusive.\n");

        return new Module(moduleCode, moduleName, moduleHoursPerWeek);
    }

    private void setModules()
    {
        int numberOfModules = validBoundedInteger(-1, 13, "Please enter the number of modules you wish to add to the timetable: ", "Error: Number of modules must lie within range: {0, 12} inclusive.\n");

        for(int i = 0; i < numberOfModules; i++)
        {
            modules.add(createModule()); // Create a new module
            setLectures();
        }
    }

    // Methods for handling lecture creation

    private EventTime createEvent()
    {
        Scanner readInput = new Scanner(System.in);
        int dayOfTheWeek = validBoundedInteger(0, 8, "Please enter the day of the week (as a number from 1 - 7 inclusive) of this event: ", "Error: Input must lie within range: {1, 7} inclusive.\n");

        int timeOfDay = validBoundedInteger(-1, 24, "Please enter the starting hour of this event (24-hour format): ", "Error: input must lie within range: {0, 23} inclusive.\n");

        return Data.constructEventTime(dayOfTheWeek, timeOfDay);
    }

    private void setLectures()
    {
        int numberOfLectures = validBoundedInteger(-1, 7, "Please enter the number of lecture this module has: ", "Error: number of lectures must lie within range: {0, 6} inclusive.\n");

        for(int i = 0; i < numberOfLectures; i++)
        {
            lectureTimes.add(createEvent());
        }
    }

    // Methods for handling removing busy times from calendar (E.g. lunch, society events)

    public void setBusyTimes()
    {
        int numberOfBusyTimes = validBoundedInteger(-1, 25, "Please enter the number of times you wish to mark as busy: ", "Error: number of busy times must lie within inclusive range: {0, 24}/\n");

        for(int i = 0; i < numberOfBusyTimes; i++) // Create each busy time to be removed
        {
            busyTimes.add(createEvent()); // Create a new event to be removed
        }
    }


    // Verification functions

    public int validBoundedInteger(int lower, int upper, String prompt, String error)
    {
        Scanner readIn = new Scanner(System.in); // New Scanner object for reading from command line
        int input = -1; // Holds user input

        boolean validInput = false; // Break condition for loop

        while(!validInput) // While the user has not inputted a valid int
        {
            printInstruction(prompt); // Prompt the user

            if(readIn.hasNextInt())
            {
                input = readIn.nextInt(); // Store their input

                if(input > lower && input < upper) // Validate their input
                {
                    validInput = true; // Set true if valid
                }
                else // Otherwise, not valid
                {
                    printError(error); // Print error
                }
            }
            else
            {
                readIn.next();
            }

        }

        return input; // Return valid input
    }

    // Utility Print functions

    public static void printInstruction(String input)
    {
        if(!isWindows) // Because Windows does not support ANSI Escape Codes
        {
            System.out.print((char)27 + "[34m" + input);
            System.out.print((char) 27 + "[39m"); // Why doesn't this reset the value..?
        }
        else
        {
            System.out.println(input);
        }
    }

    public static void printError(String input)
    {
        if(!isWindows) // Because Windows does not support ANSI Escape Codes
        {
            System.out.print((char)27 + "[31m" + input);
            System.out.print((char) 27 + "[39m");
        }
        else
        {
            System.out.println(input);
        }
    }

}
