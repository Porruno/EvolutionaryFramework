package geneticProgramming;

import geneticAlgorithm.Genome;
import geneticAlgorithm.Population;
import geneticAlgorithm.PopulationDelegate;
import geneticAlgorithm.phenotype.ProgramNode;
import geneticAlgorithm.phenotype.ProgramNodeFactory;
import geneticAlgorithm.phenotype.ProgramNodeFactory.FunctionRestriction;
import grammar.Type;
import java.util.ArrayList;

public class GPPopulation extends Population {

    protected int maxLevel;

    public GPPopulation(PopulationDelegate gpPopulationDelegate) {
        super(gpPopulationDelegate);
        GPPopulationDelegate castedGPPopulationDelegate = (GPPopulationDelegate) gpPopulationDelegate;
        this.maxLevel = castedGPPopulationDelegate.getMaxLevel();
    }

    @Override
    protected Genome generateAGenome(int indexInPopulation) {
        return new GPGenome(this, indexInPopulation, grammar, this.rootType, this.maxLevel);
    }

    @Override
    public Genome[] replicateGenomes(Genome[] genomesToReplicate) {
        Genome[] copies = new Genome[genomesToReplicate.length];
        for (int i = 0; i < genomesToReplicate.length; i++) {
            copies[i] = genomesToReplicate[i].clone();
        }
        return copies;
    }

    /**
     * Crosses 2 given parents. Is algorithm dependant.
     *
     * @param copy1
     * @param copy2
     * @return An array with the generated descendants.
     */
    @Override
    public void recombinateGenomes(Genome copy1, Genome copy2) {
        this.crossGenomes((GPGenome) copy1, (GPGenome) copy2);
    }

    public void crossGenomes(GPGenome descendant1, GPGenome descendant2) {
        int geneToCrossIndex = (int) (Math.random() * descendant1.numberOfGenes());

        ProgramNode geneToCrossDescendant1 = descendant1.getGene(geneToCrossIndex);

        Class<?> typeClass = geneToCrossDescendant1.getReturnType();
        ArrayList<ProgramNode> descendant2CandidateGenes = descendant2.getCandidateGenesForCrossing(typeClass, geneToCrossDescendant1.getLevel(), geneToCrossDescendant1.getLevelOfDeepestNode());

        if (descendant2CandidateGenes.isEmpty()) {
            return;
        }
        int geneToCrossIndexParent2 = (int) (Math.random() * descendant2CandidateGenes.size());
        ProgramNode geneToCrossDescendant2 = descendant2CandidateGenes.get(geneToCrossIndexParent2);

        geneToCrossDescendant1.crossWithOtherProgramNode(geneToCrossDescendant2);
    }

    @Override
    public void mutateGenome(Genome genome) {
        GPGenome castedGenome = (GPGenome) genome;

        int geneToMutateIndex = (int) (castedGenome.numberOfGenes() * Math.random());
        ProgramNode geneToMutate = castedGenome.getGene(geneToMutateIndex);
        Type geneToMutateType = grammar.getTypeForClass(geneToMutate.getReturnType());
        ProgramNode newGene = ProgramNodeFactory.createProgramNode(geneToMutate.getGenome(), grammar, geneToMutateType, geneToMutate.getLevel(), this.maxLevel, FunctionRestriction.No_Restrictions);
        
        if (geneToMutate.isRoot()) {
            genome.setPhenotype(newGene);
        } else {
            ProgramNode parentNode = geneToMutate.getParentNode();
            int indexInParentNode = geneToMutate.removeFromParentNode();
            parentNode.addChild(newGene, indexInParentNode);
        }
        genome.consolidatePhenotype();
        
    }
}