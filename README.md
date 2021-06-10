[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.4475473.svg)](https://doi.org/10.5281/zenodo.4475473)

# Metaheuristics N Stages ACO

Ant Colony Optimization (ACO) is a metaheuristic inspired by the shortest path seeking behavior in ant colonies. This algorithm was developed to solve combinatorial problems such as the Trade Traveler problem, the Quadratic Assignment problem, the Sequential Ordering problem, Production Scheduling, Schedules, Telecommunications Routing, Investment Planning, among others.

In [1] presented a form of exploration for the ACO algorithms known as Colony of Ants in two Stages (Two-Stage ACO). The idea was to divide the exploration carried out by the ants into two phases. This project provides the Ant Colony System (ACS) metaheuristic in an N Stages strategy, to obtain optimal solutions to discrete problems. This project focuses on solving Travel Salesman Problem problems (TSP)

## Features
- Three TSP file decoders were implemented.
- The implementation does not involve external libraries, not typical of Java.
- The project allows the execution from 2 to N stages.
- Soon there will be more TSP decoders.

## Installation
- Java\
Run\
Execution using .jar - Download release
```
parameters: ant_colony_system_NS.jar file_tsp alpha beta p0 cons_evap num_ants num_iter q0 step percentAnts percentCicle percentNode percentNStepSolution
java -jar ant_colony_system_NS.jar file_tsp 1 2 0.1 0.1 45 10000 0.1 3 33-33-33 25-25-50 25-25-50 5
```

## Get the source
Clone the Git repository from GitHub
```github
git clone https://github.com/dhernandezl/Metaheuristics-N-Stages-ACO
```

## References
Please include the following related citations:
1. A. Puris, R. Bello, y F. Herrera, «Analysis of the efficacy of a Two-Stage methodology for ant colony optimization: Case of study with TSP and QAP», Expert Syst. Appl., vol. 37, n.7, pp. 5443-5453, jul.2010, doi: 10.1016/j.eswa.2010.02.069.
