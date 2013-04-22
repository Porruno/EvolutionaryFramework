package CSP.GA;

import CSP.CSP.CSPRepository;
import CSP.CSP.CSPSolver;
import CSP.Framework.EvolutionaryFramework;
import CSP.Framework.Framework;
import CSP.HyperHeuristic.BlockHyperHeuristic;
import CSP.HyperHeuristic.HyperHeuristic;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions for runing the steady state genetic algorithm.
 * ----------------------------------------------------------------------------
*/

public class GeneticAlgorithm {
    
    private Population population;
    private CSPRepository repository;
    private static double crossOverProbability = 1.0;
    private static double mutationProbability = 0.1;    
    private int hyperHeuristicType, initialRun = 1, type;    
    private String populationName;    
    
    public static final int STEADY_STATE = 0, GENERATIONAL = 1, ELITIST_GENERATIONAL = 2;
    
    /** Creates a new instance of GeneticAlgorithm. 
     * @param size The number of individuals in the population of the genetic algorithm.
     * @param repository The instance of the repository where the instances will
     * be loaded from.
    */
    public GeneticAlgorithm(int size, CSPRepository repository, int hyperHeuristicType, int type) {
        initialRun = 1;
        this.repository = repository;
        this.hyperHeuristicType = hyperHeuristicType;
        this.type = type;
        population = new Population(size, hyperHeuristicType);               
    }               
    
    /** Creates a new instance of GeneticAlgorithm from an existing population. 
     * @param repository A reference to the repository where the CSP instances will
     * be loaded from.
     * @param populationNumber The number of the population where the individuals will
     * be loaded from.
     * @param hyperHeuristicType The hyperHeuristicType of the hyper-heuristics within the individuals.
    */    
    public GeneticAlgorithm(CSPRepository repository, int populationNumber, int hyperHeuristicType, int type) {
        this.repository = repository;
        this.hyperHeuristicType = hyperHeuristicType;
        this.type = type;
        initialRun = populationNumber + 1;
        populationName = "Population " + CSP.Utils.Misc.toSpecialFormat(populationNumber);
        population = Population.loadFromFile(populationName, hyperHeuristicType);
        EvolutionaryFramework.setPopulationSize(population.size());        
    }

    /*
     * Returns the population of the genetic algorithm. 
     * @return The population used by the genetic algorithm.
    */
    public Population getPopulation() {
        return population;
    }
    
    /** Sets the crossover probability that will be used by the genetic algorithm.
     * @param probability The crossover probability.
    */
    public static void setCrossoverRate(double probability) {
        if (probability > 1) {
            crossOverProbability = 1;
        } else {
            crossOverProbability = probability;
        }
    }

    /** Sets the mutation probability that will be used by the genetic algorithm.
     * @param probability The mutation probability.
    */
    public static void setMutationRate(double probability) {
        if (probability > 1) {
            mutationProbability = 1;
        } else {
            mutationProbability = probability;
        }
    }    

    /** Runs the GeneticAlgorithm a given number of cycles.
     * @param cycles The number of cycles that the GeneticAlgorithm will be run.
     * @return The best individual obtained in the last generation.
    */
    public HyperHeuristic run(int cycles, String runName) {        
        int i, j, k, populationSize;
        double averagePopulationFitness, time;
        long startTime, endTime;      
        GregorianCalendar day = new GregorianCalendar();
        String text = "";
        Individual a, b, first, second, newA, newB, sons[], temp[], individuals[];
        Random generator = new Random();        
        startTime = System.currentTimeMillis();
        System.out.println("% CYCLE, AGE OF BEST, FITNESS OF BEST, AVG POPULATION FITNESS");
        text = "% This file was created on " + day.getTime().toString() + "\r\n";
        text += "% CYCLE, AGE OF BEST, FITNESS OF BEST, AVG POPULATION FITNESS\r\n";        
        for (i = initialRun; i < cycles + initialRun; i++) {                   
            for(j = 0; j < population.size(); j++) {
                evaluate(population.get(j));                
                population.get(j).setAge(population.get(j).getAge() + 1);
            }
            switch(type) {
                case STEADY_STATE:                            
                    temp = select(population);
                    a = temp[0];
                    b = temp[1];
                    //System.out.println("\t" + a.getHyperHeuristic() + " {" + a.getAptitude() + "}: ");
                    //System.out.println("\t" + b.getHyperHeuristic() + " {" + b.getAptitude() + "}: ");
                    if (generator.nextDouble() < GeneticAlgorithm.crossOverProbability) {
                        sons = crossover(a, b);
                        newA = sons[0];
                        newB = sons[1];
                    } else {
                        newA = a;
                        newB = b;
                    }
                    if (generator.nextDouble() < GeneticAlgorithm.mutationProbability) {
                        newA = mutate(newA);                        
                    }
                    if (generator.nextDouble() < GeneticAlgorithm.mutationProbability) {
                        newB = mutate(newB);                        
                    }                    
                    //System.out.println("\t\t*" + newA.getHyperHeuristic() + " {" + newA.getAptitude() + "}");
                    //System.out.println("\t\t*" + newB.getHyperHeuristic() + " {" + newB.getAptitude() + "}");
                    
                    evaluate(newA);
                    newA.setAge(1);
                    evaluate(newB);
                    newB.setAge(1);
                    
                    //System.out.println("\t\t" + newA.getHyperHeuristic() + " {" + newA.getAptitude() + "}");
                    //System.out.println("\t\t" + newB.getHyperHeuristic() + " {" + newB.getAptitude() + "}");
                    
                    population.sort();                    
                    if (newA.getAptitude() >= newB.getAptitude()) {
                        first = newA;
                        second = newB;
                    } else {
                        first = newB;
                        second = newA;
                    }
                    if (first.getAptitude() > (population.get(population.size() - 2)).getAptitude()) {
                        population.set(population.size() - 2, first);
                        if (second.getAptitude() > (population.get(population.size() - 1)).getAptitude()) {
                            population.set(population.size() - 1, second);
                        }
                    } else if (first.getAptitude() > (population.get(population.size() - 1)).getAptitude()) {
                        population.set(population.size() - 1, first);
                    }
                    break;
                case GENERATIONAL:
                    individuals = new Individual[population.size()];
                    for (j = 0; j < individuals.length; j += 2) {                        
                        temp = select(population);
                        individuals[j] = temp[0];
                        individuals[j + 1] = temp[1];                        
                        if (generator.nextDouble() < GeneticAlgorithm.crossOverProbability) {
                            sons = crossover(individuals[j], individuals[j + 1]);
                            individuals[j] = sons[0];
                            individuals[j + 1] = sons[1];
                        }                                            
                        individuals[j].setAge(1);
                        individuals[j + 1].setAge(1);
                    }                    
                    for (j = 0; j < individuals.length; j++) {
                        if (generator.nextDouble() < GeneticAlgorithm.mutationProbability) {
                            individuals[j] = mutate(individuals[j]);
                        }
                    }
                    population = new Population(individuals, hyperHeuristicType);                    
                    for (j = 0; j < population.size(); j++) {
                        evaluate(population.get(j));
                        population.get(j).setAge(1);
                    }            
                    break;
                case ELITIST_GENERATIONAL:
                    populationSize = population.size();
                    individuals = new Individual[populationSize * 2];
                    for (j = 0; j < populationSize; j += 2) {                        
                        temp = select(population);
                        individuals[j] = temp[0];
                        individuals[j + 1] = temp[1];                        
                        if (generator.nextDouble() < crossOverProbability) {
                            sons = crossover(individuals[j], individuals[j + 1]);
                            individuals[j] = sons[0];
                            individuals[j + 1] = sons[1];
                        }                        
                        individuals[j].setAge(0);                        
                        individuals[j + 1].setAge(0);                        
                    }                    
                    for (j = 0; j < populationSize; j++) {
                        if (generator.nextDouble() < mutationProbability) {
                            individuals[j] = mutate(individuals[j]);
                        }
                    }                    
                    k = populationSize;
                    for (j = 0; j < population.size(); j++) {
                        individuals[k] = population.get(j);                        
                        k++;
                    }
                    population = new Population(individuals, hyperHeuristicType);                    
                    for (j = 0; j < population.size(); j++) {
                        evaluate(population.get(j));
                        population.get(j).setAge(population.get(j).getAge() + 1);
                    }
                    population.sort();
                    individuals = new Individual[populationSize];
                    for (j = 0; j < populationSize; j++) {
                        individuals[j] = population.get(j);
                    }
                    population = new Population(individuals, hyperHeuristicType);
                    break;
            }            
            population.sort();            
            averagePopulationFitness = 0;
            for(j = 0; j < population.size(); j++) {                
                averagePopulationFitness += population.get(j).getAptitude();
            }
            averagePopulationFitness = averagePopulationFitness / population.size();                        
            population.saveToFile("Population " + CSP.Utils.Misc.toSpecialFormat(i));                
            text += i + ", " + population.get(0).getAge() + ", " + population.get(0).getAptitude() + ", " + averagePopulationFitness + "\r\n";            
            System.out.println(i + ", " + population.get(0).getAge() + ", " + population.get(0).getAptitude() + ", " + averagePopulationFitness);
        }
        endTime = System.currentTimeMillis();
        time = (double) (endTime - startTime) / 1000;
        System.out.println("% Time required to produce a hyper-heuristic: " + time + " seconds.");
        text += "% Time required to produce a hyper-heuristic: " + time + " seconds.";
        CSP.Utils.Files.saveToFile(text, runName + ".txt", false);        
        return population.get(0).getHyperHeuristic();
    }   
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */  
    
    /** Selects two individuals from the population using tournament selection.
     * @param population Population where the individuals will be extracted from.
     * @return An array of two individuals.
    */
    private static Individual[] select(Population population) {              
        int i, tournamentSize = 5;
        double bestApt = 0;
        Random generator = new Random();
        Individual parents[] = new Individual[2];
        Individual[] individuals = new Individual[tournamentSize];
        individuals[0] = population.get(generator.nextInt(population.size()));
        parents[0] = new Individual(individuals[0]);
        bestApt = individuals[0].getAptitude();
        for (i = 1; i < tournamentSize; i++) {
            individuals[i] = population.get(generator.nextInt(population.size()));
            if (individuals[i].getAptitude() > bestApt) {
                bestApt = individuals[i].getAptitude();
                parents[0] = new Individual(individuals[i]);
            }
        }
        individuals[0] = population.get(generator.nextInt(population.size()));
        parents[1] = new Individual(individuals[0]);
        bestApt = individuals[0].getAptitude();        
        for (i = 1; i < tournamentSize; i++) {
            individuals[i] = population.get(generator.nextInt(population.size()));
            if (individuals[i].getAptitude() > bestApt) {
                bestApt = individuals[i].getAptitude();
                parents[1] = new Individual(individuals[i]);
            }
        }
        return parents;
    }        
    
    /** Applies one of the crossover operators over two given individuals. The 
     * result are two new individuals.
     * @param individualA The first individual to be crossed.
     * @param individualB The second individual to be crossed.
     * @return An array of two individuals.
    */
    public static Individual[] crossover(Individual individualA, Individual individualB) {
        int i, crossoverPoint, indexA, indexB, delta;
        int parts[];
        double valueA, valueB;
        double valuesA[], valuesB[];
        ArrayList tempA, tempB;
        Random generator = new Random();
        Individual individuals[] = new Individual[2];
        crossoverPoint = generator.nextInt(10);                
        valuesA = ((BlockHyperHeuristic) individualA.getHyperHeuristic()).getAsDouble();
        valuesB = ((BlockHyperHeuristic) individualB.getHyperHeuristic()).getAsDouble();        
        indexA = (Framework.getFeatures().length + 1) * generator.nextInt(((BlockHyperHeuristic) individualA.getHyperHeuristic()).size());        
        indexB = (Framework.getFeatures().length + 1) * generator.nextInt(((BlockHyperHeuristic) individualB.getHyperHeuristic()).size());   
        delta = generator.nextInt(Framework.getFeatures().length);
        indexA += delta;
        indexB += delta;          
        crossoverPoint = generator.nextInt(13);                        
        valueA = valuesA[indexA] * 10000;
        valueB = valuesB[indexB] * 10000;        
        parts = new int[4];
        //System.out.println("Value A = " + valueA);
        //System.out.println("Value B = " + valueB);
        parts[0] = ((int) valueA >> crossoverPoint + 1) << crossoverPoint + 1;
        parts[2] = ((int) valueB >> crossoverPoint + 1) << crossoverPoint + 1;
        parts[1] = (int) (valueA % Math.pow(2, crossoverPoint + 1));
        parts[3] = (int) (valueB % Math.pow(2, crossoverPoint + 1));
        /*
        System.out.println("HP A = " + parts[0]);
        System.out.println("LP A = " + parts[1]);
        System.out.println("HP B = " + parts[2]);
        System.out.println("LP B = " + parts[3]);
        */
        valuesA[indexA] = (double) (parts[0] + parts[3]) / 10000;
        valuesB[indexB] = (double) (parts[2] + parts[1]) / 10000;
        tempA = new ArrayList();
        tempB = new ArrayList();
        for (i = 0; i <= indexA; i++) {
            tempA.add(valuesA[i]);
        }
        for (i = indexB + 1; i < valuesB.length; i++) {
            tempA.add(valuesB[i]);
        }        
        for (i = 0; i <= indexB; i++) {
            tempB.add(valuesB[i]);
        }
        for (i = indexA + 1; i < valuesA.length; i++) {
            tempB.add(valuesA[i]);
        }
        valuesA = new double[tempA.size()];
        for(i = 0; i < tempA.size(); i++) {
            valuesA[i] = (Double) tempA.get(i); 
        }
        valuesB = new double[tempB.size()];
        for(i = 0; i < tempB.size(); i++) {
            valuesB[i] = (Double) tempB.get(i); 
        }
        individuals[0] = new Individual();
        individuals[0].setHyperHeuristic(new BlockHyperHeuristic(valuesA));
        individuals[1] = new Individual();
        individuals[1].setHyperHeuristic(new BlockHyperHeuristic(valuesB));        
        return individuals;
    }
    
    /** Applies one of the mutation operators over one individual. The result 
     * is a new individual.
     * @param individual The individual to be mutated.
     * @return A mutated individual.
    */
    private static Individual mutate(Individual individual) {
        return individual;
    }
    
    /** Evaluates a given individual.
     * @param individual The index of the hyperheuristic that will be used to solve
     * the problems associated to the individual.
     * @param instances The number of problems used in the evaluation.
     * @return The evaluation of the individual.
    */
    private double evaluate(Individual individual) {
        int i, timesBetter = 0;                
        double checks, sum = 0;
        CSPSolver solver;
        if (!individual.isEvaluated()) {
            if (individual.getHyperHeuristic().getType() == HyperHeuristic.BLOCK) {
                ((BlockHyperHeuristic) individual.getHyperHeuristic()).resetBlockUse();
            }
            for (i = 0; i < repository.size(); i++) {
                solver = new CSPSolver(repository.get(i));                
                solver.solve(individual.getHyperHeuristic());                
                checks = solver.getConstraintChecks();
                if (checks > 0) {
                    sum += repository.getBestResult(i) / checks;
                    if (checks <= repository.getBestResult(i)) {
                        timesBetter++;
                    }
                }
            }
            ((BlockHyperHeuristic) individual.getHyperHeuristic()).deleteUnusedBlocks();
            
            individual.setAptitude(sum / repository.size() * timesBetter / repository.size());
            individual.setEvaluated(true);
        }
        return individual.getAptitude();
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
}
