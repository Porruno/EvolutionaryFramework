package functionApproximation;

import geneExpressionProgramming.GEPPopulationDelegate;
import geneExpressionProgramming.LinkerFunction;
import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;

public class ADF1PopulationDelegate extends
		GPPopulationDelegate {

	
	@Override
	public int getPopulationSize() {
		return 100;
	}

	@Override
	public boolean getMaximization() {
		return false;
	}

	@Override
	public Selection getSelection() {
		return new TournamentSelection(99, 2);
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

    

    @Override
    public int getMaxLevel() {
        return 2;
    }

}
