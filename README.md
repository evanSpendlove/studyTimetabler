# Study Timetabler
> Generates a timetable for study sessions based on current modules using a genetic algorithm.

## Background   
The problem statement for this project was as follows:   
    ``` Design a program that will generate a valid timetable of study sessions given certain, variable constraints.```   
When I began researching ways of addressing the timetabling problem, I came across Genetic Algorithms as a viable solution. I began reading several sources, such as academic papers, on this topic while preparing to start this project. This became my first SLP (Self-Learning Project). The project centered around using Genetic Algorithms to solve constraint-based scheduling/timetabling problems.

## Project Overview

### Input
This program takes a set of modules, which each have required lectures and study hours, as input. 
It also takes the start time of a working day, and the number of (hour-long) periods which can be assigned events.
### Setup
First, the program removes time-slots that would clash with lectures.
Then, it generates a list of study-sessions for each module.
### Processing
The genetic algorithm creates a population of timetables ranked by fitness.
The fitness of the timetable is measured by the number of constraints broken and the number of times a constraint is broken. 
### Output
Currently, the program outputs the final solution and intermediary generations to the console.
I plan to alter the input and output to be more convenient for the end user.
I plan to output an .ics file for the study sessions, so you can add them directly to your calendar app of choice.

## Installation

Windows, OS X & Linux:

```
    Install Java (1.8 or later).
    This project was designed in IntelliJ.
    Alternatively, you can copy the source code files into another IDE and compile them.
```

## Planned Features
- Additional constraints such as preferences for time-of-day (morning/day/evening)
- .ics export file format for study sessions
- Grouping of study sessions
- Adding preference for study sessions to be placed as close as possible to the lecture
- Multiple types of study sessions: lecture review, exam preparation, etc.

## Release History

* 0.0.1
    * Working first version - console based output, hard-coded inputs.

## Meta

Evan Spendlove

Distributed under the MIT license. See ``LICENSE`` for more information.

[https://github.com/evanSpendlove/studyTimetabler](https://github.com/evanSpendlove/)

## Contributing

1. Fork it (<https://github.com/evanSpendlove/studyTimetabler/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

