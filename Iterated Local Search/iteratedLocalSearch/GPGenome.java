package iteratedLocalSearch;

import geneticAlgorithm.Genome;
import geneticAlgorithm.Population;
import geneticAlgorithm.phenotype.ProgramNode;
import geneticAlgorithm.phenotype.ProgramNodeFactory;
import geneticAlgorithm.phenotype.ProgramNodeFactory.FunctionRestriction;
import grammar.Grammar;
import grammar.Type;
import java.util.ArrayList;

public final class GPGenome extends Genome {

    protected int maxLevel;

    public GPGenome(Population population, Integer indexInPopulation) {
        super(population, indexInPopulation);
    }

    public GPGenome(Population population, Integer indexInPopulation, Grammar grammar, Type root, int maxLevel) {
        super(population, indexInPopulation);
        this.maxLevel = maxLevel;
        this.createPhenotype(grammar, root);
    }
    
    protected void createPhenotype(Grammar grammar, Type root){
        this.phenotype = ProgramNodeFactory.createProgramNode(this, grammar, root, 0, this.maxLevel, FunctionRestriction.No_Restrictions);
        this.consolidatePhenotype();
    }

    public int numberOfGenes() {
        return 1 + this.phenotype.getNumberOfSubgenes();
    }

    public ProgramNode getGene(int numberOfGene) {
        return this.phenotype.getNode(numberOfGene);
    }

    public ArrayList<ProgramNode> getCandidateGenesForCrossing(Class typeClass, int otherGeneLevel, int otherGeneLevelOfDeepestGene) {
        ArrayList<ProgramNode> candidates = new ArrayList<ProgramNode>();
        this.phenotype.addCandidateGenesForCrossing(candidates, typeClass, otherGeneLevel, otherGeneLevelOfDeepestGene);
        return candidates;
    }

    @Override
    public Genome clone() {
        GPGenome newGenome = (GPGenome) super.clone();
        newGenome.phenotype = this.phenotype.clone();
        newGenome.maxLevel = this.maxLevel;
        newGenome.consolidatePhenotype();
        return newGenome;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public void prepareForNextGeneration() {
        super.prepareForNextGeneration();
    }

    @Override
    public void prepareToBeGraphed() {
        return; //GP is already prepared to be graphed since its genotype is the same as its phenotype.
    }

}