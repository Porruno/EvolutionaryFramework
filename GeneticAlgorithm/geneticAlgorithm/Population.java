package geneticAlgorithm;

import geneticAlgorithm.selection.Selection;
import grammar.Grammar;
import grammar.Type;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate.ADFType;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Population {

    protected int populationSize;
    protected int replacementSize;
    protected double chanceOfMutation;
    protected double chanceOfCrossing;
    protected Grammar grammar;
    protected Genome[] completePopulation;
    protected Genome[] newGeneration;
    protected int currentGeneration = -1;
    Selection selection;
    protected boolean maximization;
    protected Type rootType;
    protected Class<?> returnType;

    public Population(PopulationDelegate populationDelegate) {
        try {
            this.selection = populationDelegate.getSelection();
            this.maximization = populationDelegate.getMaximization();
            this.chanceOfCrossing = populationDelegate.getChanceOfCrossing();
            this.chanceOfMutation = populationDelegate.getChanceOfMutation();
            this.populationSize = populationDelegate.getPopulationSize();
            this.replacementSize = populationDelegate.getSelection().getGenomesGeneratedPerGeneration();
            this.returnType = populationDelegate.getReturnClassType();
            this.completePopulation = new Genome[this.populationSize];
            this.newGeneration = new Genome[this.replacementSize];
            this.grammar = new Grammar(populationDelegate.getADFs(),
                    populationDelegate.getInputClass(),
                    populationDelegate.getIfFunctionProabilitiesForType(),
                    populationDelegate.getClassesForComponents());
            this.rootType = this.grammar.getTypeForClass(populationDelegate.getReturnClassType());
        } catch (Exception ex) {
            Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Generates the initial population.
     */
    public void generateInitialGeneration() {
        for (int i = 0; i < this.populationSize; i++) {
            Genome newGenome = this.generateAGenome(i);
            newGenome.prepareForNextGeneration();
            completePopulation[i] = newGenome;
        }
        newGeneration = completePopulation;
        this.currentGeneration = 0;
    }

    /**
     * Generates a random genome. Is algorithm dependant.
     *
     * @return A random new genome.
     */
    protected abstract Genome generateAGenome(int indexInPopulation);

    public int getCurrentGeneration() {
        return this.currentGeneration;
    }

    public abstract Genome[] replicateGenomes(Genome[] genomesToReplicate);

    public void mutation(Genome[] genomesToMutate) {
        double random = Math.random();
        for (Genome genome : genomesToMutate) {
            if (random > this.chanceOfMutation) {
                continue;
            }
            this.mutateGenome(genome);
        }
    }

    //Parents are already cloned
    public void recombinateGenomes(Genome[] parents) {
        ADF[] adfs = this.grammar.getADFs();
        for (int i = 0; i < parents.length / 2; i++) {
            double random = Math.random();
            if (random > this.chanceOfCrossing) {
                continue;
            }
            int descendant1Index = i * 2;
            int descendant2Index = descendant1Index + 1;
            Genome copy1 = parents[descendant1Index];
            Genome copy2 = parents[descendant2Index];

            this.recombinateGenomes(copy1, copy2);
            for (ADF adf : adfs) {
                if (adf.getADFType() != ADFType.SIMPLE) {
                    continue;
                }
                Genome adf1 = adf.getPopulation().getGenomeAtIndex(copy1.indexInPopulation, false);
                Genome adf2 = adf.getPopulation().getGenomeAtIndex(copy2.indexInPopulation, false);
                adf.evolveAPair(adf1, adf2);
            }
        }
    }

    public Class<?> getReturnType() {
        return this.returnType;
    }

    public Genome getGenomeAtIndex(int index, boolean onlyNewGeneration) {
        if (onlyNewGeneration) {
            return this.newGeneration[index];
        } else {
            return this.completePopulation[index];
        }
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public Genome[] getCompletePopulation() {
        return this.completePopulation;
    }

    public Genome[] getSortedFromBestToWorst() {
        Genome[] sortedPopulation = this.completePopulation.clone();
        Arrays.sort(sortedPopulation);
        return sortedPopulation;
    }

    /**
     * Return the best genome in the current generation.
     *
     * @return The best genome in the current generation.
     */
    public Genome getBestGenome() {
        return this.getSortedFromBestToWorst()[0];
    }

    public void replaceOldGenomes(Genome[] descendants) {
        //Replace old genomes with recently generated genomes.
        //All threads share the same array, so is enough to ask for it to thread 0
        Genome[] sortedGenomes = this.getSortedFromBestToWorst();
        this.newGeneration = descendants;
        for (int i = 0; i < descendants.length; i++) {
            Genome genomeToReplace = sortedGenomes[this.populationSize - 1 - i];
            descendants[i].indexInPopulation = genomeToReplace.indexInPopulation;
            this.completePopulation[genomeToReplace.indexInPopulation] = descendants[i];
        }
    }

    /**
     * Selects the parents to be crossed. Then, wakes up and waits for the
     * crossing threads to cross the selected parents.
     */
    public Genome[] generateNextGeneration() {
        for (Genome genome : this.completePopulation) {
            genome.isFromNewGeneration = false;
        }
        Genome[] parents = this.selection.selectParents(this.completePopulation);

        Genome[] descendants = this.replicateGenomes(parents);

        this.recombinateGenomes(descendants);
        this.mutation(descendants);
        for (Genome genome : descendants) {
            genome.prepareForNextGeneration();
        }
        return descendants;
    }

    /**
     * Prints in the console a description of the current generation, genome by
     * genome.
     */
    public void printCurrentGeneration() {
        for (int i = 0; i < this.completePopulation.length; i++) {
            Genome genomeToPrint = this.completePopulation[i];
            System.out.printf("Genome: %d\nAptitude: %f\nRepresentation: %s", i, genomeToPrint.aptitude, genomeToPrint.toString());
        }
    }

    public void increaseGeneration() {
        this.currentGeneration++;
    }

    public void resetAptitudesOfNewGeneration() {
        for (Genome genome : this.newGeneration) {
            genome.resetAptitude();
        }
    }

    public boolean getMaximization() {
        return this.maximization;
    }

    public int getReplacementSize() {
        return this.replacementSize;
    }

    public Grammar getGrammar() {
        return this.grammar;
    }

    public void prepareInfoAboutADFs() {
        for (Genome genome : this.completePopulation) {
            if (genome.isFromNewGeneration) {
                genome.lookForADFs();
            }
            genome.computeIterationsToDo();
        }
    }

    public abstract void mutateGenome(Genome genome);

    public abstract void recombinateGenomes(Genome descendant1, Genome descendant2);
}