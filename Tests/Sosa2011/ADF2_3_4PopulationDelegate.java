package Sosa2011;

import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;

public class ADF2_3_4PopulationDelegate extends
		GPPopulationDelegate {

	@Override
	public int getMaxLevel() {
		return 6;
	}

	@Override
	public int getPopulationSize() {
		return 500;
	}

	@Override
	public boolean getMaximization() {
		return false;
	}

	@Override
	public Selection getSelection() {
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
	public Class<?>[] getClassesForComponents() {
		Class<?>[] components = {Components.class};
		return components;
	}

	@Override
	public Class<?> getReturnClassType() {
		return Number.class;
	}

	@Override
	public Class<?> getInputClass() {
		return ADF1Input.class;
	}

}
