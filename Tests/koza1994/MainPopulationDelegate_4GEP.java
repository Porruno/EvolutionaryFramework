package koza1994;

import java.util.ArrayList;

import geneExpressionProgramming.GEPPopulationDelegate;
import geneExpressionProgramming.LinkerFunction;
import geneticAlgorithm.selection.RouletteSelection;
import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import grammar.adf.ADF;

public class MainPopulationDelegate_4GEP extends GEPPopulationDelegate {
	ArrayList<ADF> adfs = new ArrayList<ADF>();

	@Override
	public int getPopulationSize() {
		return 500;
	}

	@Override
	public Selection getSelection() {
//		return new RouletteSelection(75);
		return new TournamentSelection(499, 2);
	}
	
	
	@Override
	public double getChanceOfMutation() {
		return .05;
	}

	@Override
	public double getChanceOfCrossing() {
		return .95;
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
		return 7;
	}
	
	@Override
	public int getNumberOfORFs(){
		return 1;
	}

	@Override
	public LinkerFunction getLinkerFunction() {
		return new Koza1994LinkerFunction(this.getNumberOfORFs());
	}
}
