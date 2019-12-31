package io.github.evanspendlove.genetic_algorithm;

import java.util.ArrayList;

public class Population
{
    private ArrayList<Timetable> timetables;

    // Getters
    public ArrayList<Timetable> getTimetables() {
        return timetables;
    }

    // Constructor

    public Population(int size, Data data)
    {
        timetables = new ArrayList<Timetable>(size);

        for(int i = 0; i < size; i++)
        {
            timetables.add(new Timetable(data).initialize()); // Generate our new schedules for this population
        }
    }

    public Population sortByFitness()
    {
        timetables.sort((timetable1, timetable2) ->
        {
            int returnValue = 0;
            if(timetable1.getFitness() > timetable2.getFitness()) returnValue = -1;
            else if(timetable1.getFitness() < timetable2.getFitness()) returnValue = 1;

            return returnValue;
        });

        return this;
    }
}
