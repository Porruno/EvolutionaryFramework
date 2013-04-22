package geneExpressionProgramming;

import geneticAlgorithm.Genome;
import geneticAlgorithm.Population;
import geneticAlgorithm.PopulationDelegate;
import grammar.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import virtualGene.VirtualGene;
import virtualGene.VirtualGene.CrossoverPointsDistribution;

public class GEPPopulation extends Population {

    LinkerFunction linkerFunction;
    int headLength;
    int tailLength;
    int orfLength;
    
    int numberOfORFs;
    int genotypeLength;
    
    int largestTypeNumberOfFunctions;
    Type orfReturnType;
    
    double chanceOfGeneMutation;
    double chanceOfISTransposition;
    double chanceOfRISTransposition;
    double chanceOfGeneTransposition;
    double chanceOfOnePointRecombination;
    double chanceOfTwoPointRecombination;
    double chanceOfGeneRecombination;
    
    VirtualGene vg;

    public GEPPopulation(PopulationDelegate populationDelegate) {
        super(populationDelegate);
        GEPPopulationDelegate castedPopulationDelegate = (GEPPopulationDelegate) populationDelegate;
        this.linkerFunction = castedPopulationDelegate.getLinkerFunction();
        this.headLength = castedPopulationDelegate.getHeadLength();
        this.tailLength = headLength * (grammar.getLargestArityOfAllFunctions() - 1) + 1;
        this.tailLength *= Math.pow(this.grammar.getLargestArityOfAllFunctions(), this.grammar.getMaximumMinimumCallsOfAllFunctions()) - 1;
        this.tailLength /= this.grammar.getLargestArityOfAllFunctions() - 1;
        this.orfLength = headLength + tailLength;
        this.numberOfORFs = castedPopulationDelegate.getNumberOfORFs();
        this.genotypeLength = this.orfLength * this.numberOfORFs;
        this.largestTypeNumberOfFunctions = 0;
        for (Type type : grammar.getTypes()) {
            if (type.getFunctions().size() > this.largestTypeNumberOfFunctions) {
                this.largestTypeNumberOfFunctions = type.getFunctions().size();
            }
        }
        this.orfReturnType = this.grammar.getTypeForClass(this.linkerFunction.getORFReturnType());

        this.chanceOfGeneMutation = castedPopulationDelegate.getChanceOfGeneMutation();
        this.chanceOfISTransposition = castedPopulationDelegate.getChanceOfISTransposition();
        this.chanceOfRISTransposition = castedPopulationDelegate.getChanceOfRISTransposition();
        this.chanceOfGeneTransposition = castedPopulationDelegate.getChanceOfGeneTransposition();
        this.chanceOfOnePointRecombination = castedPopulationDelegate.getChanceOfOnePointRecombination();
        this.chanceOfTwoPointRecombination = castedPopulationDelegate.getChanceOfTwoPointRecombination();
        this.chanceOfGeneRecombination = castedPopulationDelegate.getChanceOfGeneRecombination();
        
        try {
            this.vg = new VirtualGene(0, this.largestTypeNumberOfFunctions, 10, this.largestTypeNumberOfFunctions / 10, 1, CrossoverPointsDistribution.CONTINUOUS);
        } catch (Exception ex) {
            Logger.getLogger(GEPPopulation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Genome generateAGenome(int indexInPopulation) {
        return new GEPGenome(this, indexInPopulation, this.numberOfORFs,
                this.headLength, this.tailLength, this.genotypeLength, 
                this.largestTypeNumberOfFunctions);
    }

    @Override
    public Genome[] replicateGenomes(Genome[] genomesToReplicate) {
        Genome[] replicates = new Genome[genomesToReplicate.length];
        for (int i = 0; i < genomesToReplicate.length; i++) {
            replicates[i] = genomesToReplicate[i].clone();
        }
        return replicates;
    }

    public LinkerFunction getLinkerFunction() {
        return this.linkerFunction;
    }

    public int getNumberOfORFs() {
        return this.numberOfORFs;
    }

    public int getORFLength() {
        return this.orfLength;
    }

    public Type getORFReturnType() {
        return this.orfReturnType;
    }

    @Override
    public void mutateGenome(Genome genome) {
        GEPGenome castedGenome = (GEPGenome) genome;

        double random = Math.random();
        if (random <= this.chanceOfGeneMutation) {
            this.geneMutation(castedGenome);
        }

        random = Math.random();
        if (random <= this.chanceOfISTransposition) {
            this.isTransposition(castedGenome);
        }

        random = Math.random();
        if (random <= this.chanceOfRISTransposition) {
            this.risTransposition(castedGenome);
        }
        
        random = Math.random();
        if (random <= this.chanceOfGeneTransposition){
            this.geneTransposition(castedGenome);
        }
    }

    private void geneMutation(GEPGenome genome) {
        int geneToMutateIndex = (int) (genome.bonds.length * Math.random());
        genome.bonds[geneToMutateIndex] = (int) vg.mutation(genome.bonds[geneToMutateIndex]);
    }

    private void isTransposition(GEPGenome genome) {
        int startIndex = (int) (this.genotypeLength * Math.random());
        int lengthOfTransposon = (int) ((this.headLength - 1) * Math.random());

        if(lengthOfTransposon + startIndex > this.genotypeLength){
            lengthOfTransposon = this.genotypeLength - startIndex;
        }
        int[] shiftedBonds = new int[this.headLength - 1 - lengthOfTransposon];
        
        System.arraycopy(genome.bonds, 1, shiftedBonds, 0, shiftedBonds.length);
        System.arraycopy(genome.bonds, startIndex, genome.bonds, 1, lengthOfTransposon);
        System.arraycopy(shiftedBonds, 0, genome.bonds, 1 + lengthOfTransposon, shiftedBonds.length);
    }

    private void risTransposition(GEPGenome genome) {
        int startIndex = (int) (this.genotypeLength * Math.random());
        int lengthOfTransposon = (int) (this.headLength * Math.random());
        
        if(lengthOfTransposon + startIndex > this.genotypeLength){
            lengthOfTransposon = this.genotypeLength - startIndex;
        }
        
        int[] shiftedBonds = new int[this.headLength - lengthOfTransposon];
        
        System.arraycopy(genome.bonds, 0, shiftedBonds, 0, shiftedBonds.length);
        System.arraycopy(genome.bonds, startIndex, genome.bonds, 0, lengthOfTransposon);
        System.arraycopy(shiftedBonds, 0, genome.bonds, lengthOfTransposon, shiftedBonds.length);
        
    }
    
    private void geneTransposition(GEPGenome genome){
        if(this.numberOfORFs == 1) return;
        int geneToTranspose = (int) ((this.numberOfORFs - 1) * Math.random() + 1);
        int startIndex = this.orfLength * geneToTranspose;
        int[] backupGenes = new int[startIndex];
        System.arraycopy(genome.bonds, 0, backupGenes, 0, startIndex);
        System.arraycopy(genome.bonds, startIndex, genome.bonds, 0, startIndex);
        System.arraycopy(backupGenes, 0, genome.bonds, this.orfLength, startIndex);
    }

    @Override
    public void recombinateGenomes(Genome descendant1, Genome descendant2) {
        GEPGenome castedGenomeDescendant1 = (GEPGenome) descendant1;
        GEPGenome castedGenomeDescendant2 = (GEPGenome) descendant2;

        double random = Math.random();
        if (random <= this.chanceOfOnePointRecombination) {
            this.onePointRecombination(castedGenomeDescendant1, castedGenomeDescendant2);
        }

        random = Math.random();
        if (random <= this.chanceOfTwoPointRecombination) {
            this.twoPointRecombination(castedGenomeDescendant1, castedGenomeDescendant2);
        }

        random = Math.random();
        if (random <= this.chanceOfGeneRecombination) {
            this.geneRecombination(castedGenomeDescendant1, castedGenomeDescendant2);
        }
    }

    private void onePointRecombination(GEPGenome genome1, GEPGenome genome2) {
        int bondToRecombinateIndex = (int) (genome1.bonds.length * Math.random());
        double[] crossedGenes = vg.crossover(genome1.bonds[bondToRecombinateIndex], genome2.bonds[bondToRecombinateIndex]);
        genome1.bonds[bondToRecombinateIndex] = (int) crossedGenes[1];
        genome2.bonds[bondToRecombinateIndex] = (int) crossedGenes[0];
        for (int i = bondToRecombinateIndex + 1; i < genome1.bonds.length; i++) {
            int aux = genome1.bonds[i];
            genome1.bonds[i] = genome2.bonds[i];
            genome2.bonds[i] = aux;
        }
    }

    private void twoPointRecombination(GEPGenome genome1, GEPGenome genome2) {
        int startIndex = (int) (this.genotypeLength * Math.random());
        int length = (int) ((this.genotypeLength - startIndex) * Math.random());
       
        int[] backupGenome1 = new int[length];
        System.arraycopy(genome1.bonds, startIndex, backupGenome1, 0, length);
        System.arraycopy(genome2.bonds, startIndex, genome1.bonds, startIndex, length);
        System.arraycopy(backupGenome1, 0, genome2.bonds, startIndex, length);
    }

    private void geneRecombination(GEPGenome genome1, GEPGenome genome2) {
        int geneToRecombinate = (int) (this.numberOfORFs * Math.random());
        int startIndex = geneToRecombinate * this.orfLength;
        int length = this.orfLength;
        
        int[] backupGenome1 = new int[length];
        System.arraycopy(genome1.bonds, startIndex, backupGenome1, 0, length);
        System.arraycopy(genome2.bonds, startIndex, genome1.bonds, startIndex, length);
        System.arraycopy(backupGenome1, 0, genome2.bonds, startIndex, length);
    }
}