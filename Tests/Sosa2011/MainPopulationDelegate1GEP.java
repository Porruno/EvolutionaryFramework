package Sosa2011;

import java.util.ArrayList;

import geneExpressionProgramming.GEPPopulationDelegate;
import geneExpressionProgramming.LinkerFunction;
import geneticAlgorithm.selection.RouletteSelection;
import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import grammar.adf.ADF;

public class MainPopulationDelegate1GEP extends GEPPopulationDelegate {

    ArrayList<ADF> adfs = new ArrayList<ADF>();

    @Override
    public int getPopulationSize() {
        return 100;
    }

    @Override
    public Selection getSelection() {
//		return new RouletteSelection(75);
        return new TournamentSelection(99, 2);
    }

    @Override
    public Class<?> getReturnClassType() {
        return Number.class;
    }

    @Override
    public boolean getMaximization() {
        return false;
    }

    @Override
    public Class<?>[] getClassesForComponents() {
        Class<?>[] components = {Components.class};
        return components;
    }

    @Override
    public Class<?> getInputClass() {
        return X.class;
    }

    @Override
    public int getHeadLength() {
        return 10;
    }

    @Override
    public int getNumberOfORFs() {
        return 1;
    }

    @Override
    public LinkerFunction getLinkerFunction() {
        return new Sosa2011LinkerFunction(this.getNumberOfORFs());
    }

    @Override
    public double getChanceOfGeneMutation() {
        return .1;
    }

    @Override
    public double getChanceOfISTransposition() {
        return .1;
    }

    @Override
    public double getChanceOfRISTransposition() {
        return .1;
    }

    @Override
    public double getChanceOfGeneTransposition() {
        return .1;
    }

    @Override
    public double getChanceOfOnePointRecombination() {
        return .23333;
    }

    @Override
    public double getChanceOfTwoPointRecombination() {
        return .23333;
    }

    @Override
    public double getChanceOfGeneRecombination() {
        return .23333;
    }
}
