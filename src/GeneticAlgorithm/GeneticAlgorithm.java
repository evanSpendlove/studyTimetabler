package GeneticAlgorithm;

import java.util.ArrayList;

public class GeneticAlgorithm
{
    private Data data;
    public GeneticAlgorithm(Data data) {this.data = data;}

    public Population evolve(Population population)
    {
        return mutatePopulation(crossoverPopulation(population));
    }

    // Crossover Method using Tournament Selection
    Population crossoverPopulation(Population population)
    {
        Population crossoverPopulation = new Population(population.getTimetables().size(), data);

        // Need to perform Elitism here first
        for(int i = 0; i < Driver.NUMBER_OF_ELITE_TIMETABLES; i++)
        {
            crossoverPopulation.getTimetables().set(i, population.getTimetables().get(i));
        }

        // For the remaining Timetables in the population
        for(int i = Driver.NUMBER_OF_ELITE_TIMETABLES; i < population.getTimetables().size(); i++)
        {
            if(Driver.CROSSOVER_RATE > Math.random())
            {
                Timetable timetable1 = selectTournamentPopulation(population).sortByFitness().getTimetables().get(0);
                Timetable timetable2 = selectTournamentPopulation(population).sortByFitness().getTimetables().get(0);
                crossoverPopulation.getTimetables().set(i, crossoverTimetable(timetable1, timetable2));
            }
            else
            {
                crossoverPopulation.getTimetables().set(i, population.getTimetables().get(i));
            }
        }

        return crossoverPopulation;
    }

    Timetable crossoverTimetable(Timetable schedule1, Timetable schedule2)
    {
        Timetable crossoverSchedule = new Timetable(data).initialize();

        for(int i = 0; i < crossoverSchedule.getEvents().size(); i++)
        {
            if(Math.random() > 0.5)
            {
                crossoverSchedule.getEvents().set(i, schedule1.getEvents().get(i));
            }
            else
            {
                crossoverSchedule.getEvents().set(i, schedule2.getEvents().get(i));
            }
        }

        return crossoverSchedule;
    }

    Population selectTournamentPopulation(Population population)
    {
        Population tournamentPopulation = new Population(Driver.TOURNAMENT_SELECTION_SIZE, data);

        for(int i = 0; i < Driver.TOURNAMENT_SELECTION_SIZE; i++) // Get TOURNAMENT_SELECTION_SIZE random timetables
        {
            tournamentPopulation.getTimetables().set(i, population.getTimetables().get((int) (Math.random() * population.getTimetables().size())));
        }

        return tournamentPopulation;
    }

    // Mutate method for Population
    Population mutatePopulation(Population population)
    {
        Population mutatePopulation = new Population(population.getTimetables().size(), data);

        ArrayList<Timetable> timetables = mutatePopulation.getTimetables();

        for(int i = 0; i < Driver.NUMBER_OF_ELITE_TIMETABLES; i++) // Move the elite timetables into the new mutated Population
        {
            timetables.set(i, population.getTimetables().get(i));
        }

        // For the remaining timetables, mutate each one
        for(int i = Driver.NUMBER_OF_ELITE_TIMETABLES; i < population.getTimetables().size(); i++)
        {
            timetables.set(i, mutateTimetable(population.getTimetables().get(i)));
        }

        return mutatePopulation;
    }

    Timetable mutateTimetable(Timetable mutatedTimetable)
    {
        Timetable timetable = new Timetable(data).initialize();

        for(int i = 0; i < mutatedTimetable.getEvents().size(); i++)
        {
            if(Driver.MUTATION_RATE > Math.random())
            {
                mutatedTimetable.getEvents().set(i, timetable.getEvents().get(i));
            }
        }

        return mutatedTimetable;
    }
}
