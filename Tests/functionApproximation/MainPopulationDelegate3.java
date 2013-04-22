package functionApproximation;

import java.util.ArrayList;

import geneticAlgorithm.selection.RouletteSelection;
import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;
import grammar.adf.ADF;

public class MainPopulationDelegate3 extends GPPopulationDelegate {
	ArrayList<ADF> adfs = new ArrayList<ADF>();

	@Override
	public int getPopulationSize() {
		return 200;
	}

	@Override
	public Selection getSelection() {
//		return new RouletteSelection(206);
		return new TournamentSelection(150, 2);
	}
	
	public int getThreadsToUse(){
		return 1;
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
	public int getMaxLevel() {
		return 5;
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
}
