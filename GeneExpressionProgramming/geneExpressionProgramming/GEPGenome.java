package geneExpressionProgramming;

import geneticAlgorithm.Genome;
import geneticAlgorithm.Population;
import geneticAlgorithm.phenotype.ProgramNodeFactory;
import grammar.Grammar;
import grammar.Type;
import java.util.ArrayList;

public class GEPGenome extends Genome {
    
    protected int[] bonds;
    
    public GEPGenome(Population population, Integer indexInPopulation) {
        super(population, indexInPopulation);
    }
    
    public GEPGenome(Population population, int indexInPopulation, int numberOfORFs, int headLength, int tailLength, int genotypeLength, int largestTypeNumberOfFunctions) {
        super(population, indexInPopulation);
        this.bonds = new int[genotypeLength];
        for (int i = 0; i < bonds.length; i++) {
            this.bonds[i] = (int) (Math.random() * largestTypeNumberOfFunctions);
        }
        this.generatePhenotype();
    }

    public void generatePhenotype() {
        GEPPopulation castedPopulation = (GEPPopulation) this.population;
        int numberOfORFs = castedPopulation.getNumberOfORFs();
        int orfLength = castedPopulation.getORFLength();
        int tailLength = orfLength - castedPopulation.headLength;
        Type orfReturnType = castedPopulation.getORFReturnType();
        Grammar grammar = this.population.getGrammar();
        this.phenotype = ProgramNodeFactory.createProgramNode(this, castedPopulation.getLinkerFunction(), 0);

        for (int i = 0; i < numberOfORFs; i++) {
            ArrayList<Integer> orfGenes = new ArrayList<Integer>(orfLength);
            for (int j = i * orfLength; j < i * orfLength + orfLength; j++) {
                orfGenes.add(bonds[j]);
            }
            this.phenotype.addChild(ORFTree.createORFTree(this, grammar, orfReturnType, orfGenes, tailLength));
        }
        this.consolidatePhenotype();
    }

    @Override
    public Genome clone() {
        GEPGenome newGenome = (GEPGenome) super.clone();
        newGenome.bonds = new int[this.bonds.length];
        System.arraycopy(this.bonds, 0, newGenome.bonds, 0, bonds.length);
        return newGenome;
    }

    @Override
    public void prepareForNextGeneration() {
        this.generatePhenotype();
        super.prepareForNextGeneration();
    }
    
    public String genotypeToString(){
        String acum = "";
        
        for(Integer bond : this.bonds){
            acum += bond + " ";
        }
        return acum;
    }

    @Override
    public void prepareToBeGraphed() {
        this.generatePhenotype();
    }
    
}
