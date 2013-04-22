package geneExpressionProgramming;

import geneticAlgorithm.PopulationDelegate;
import geneticAlgorithm.selection.Selection;
import grammar.adf.ADF;
import java.util.HashMap;

public class GEPPopulationDelegate extends PopulationDelegate {

    protected int numberOfORFs;
    protected int headLength;
    protected LinkerFunction linkerFunction;
    protected double chanceOfGeneMutation;
    protected double chanceOfISTransposition;
    protected double chanceOfRISTransposition;
    protected double chanceOfGeneTransposition;
    protected double chanceOfOnePointRecombination;
    protected double chanceOfTwoPointRecombination;
    protected double chanceOfGeneRecombination;

    public GEPPopulationDelegate(ADF[] adfs, int populationSize, boolean maximization,
            Selection selection,
            Class<?>[] classesForComponents, Class<?> returnClassType,
            Class<?> inputClass, HashMap ifFunctionProbabilitiesForType,
            int headLength, LinkerFunction linkerFunction,
            double chanceOfGeneMutation, double chanceOfISTransposition,
            double chanceOfRISTransposition, double chanceOfGeneTransposition,
            double chanceOfOnePointRecombination, double chanceOfTwoPointRecombination,
            double chanceOfGeneRecombination) {

        super(adfs, populationSize, maximization, selection, 1.0,
                1.0, GEPPopulation.class, classesForComponents,
                returnClassType, inputClass, ifFunctionProbabilitiesForType);

        this.numberOfORFs = linkerFunction.getNumberOfORFs();
        this.headLength = headLength;
        this.linkerFunction = linkerFunction;
        this.chanceOfGeneMutation = chanceOfGeneMutation;
        this.chanceOfISTransposition = chanceOfISTransposition;
        this.chanceOfRISTransposition = chanceOfRISTransposition;
        this.chanceOfGeneTransposition = chanceOfGeneTransposition;
        this.chanceOfOnePointRecombination = chanceOfOnePointRecombination;
        this.chanceOfTwoPointRecombination = chanceOfTwoPointRecombination;
        this.chanceOfGeneRecombination = chanceOfGeneRecombination;

    }

    public int getNumberOfORFs(){
        return this.numberOfORFs;
    }

    public int getHeadLength(){
        return this.headLength;
    }

    public LinkerFunction getLinkerFunction(){
        return this.linkerFunction;
    }

    public double getChanceOfGeneMutation(){
        return this.chanceOfGeneMutation;
    }

    public double getChanceOfISTransposition(){
        return this.chanceOfISTransposition;
    }

    public double getChanceOfRISTransposition(){
        return this.chanceOfRISTransposition;
    }

    public double getChanceOfGeneTransposition(){
        return this.chanceOfGeneTransposition;
    }

    public double getChanceOfOnePointRecombination(){
        return this.chanceOfOnePointRecombination;
    }

    public double getChanceOfTwoPointRecombination(){
        return this.chanceOfTwoPointRecombination;
    }

    public double getChanceOfGeneRecombination(){
        return this.chanceOfGeneRecombination;
    }
}