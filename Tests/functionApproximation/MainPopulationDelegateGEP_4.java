package functionApproximation;

import geneExpressionProgramming.GEPPopulationDelegate;
import geneExpressionProgramming.LinkerFunction;
import java.util.ArrayList;

import geneticAlgorithm.selection.RouletteSelection;
import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;
import grammar.adf.ADF;

public class MainPopulationDelegateGEP_4 extends GEPPopulationDelegate {
	ArrayList<ADF> adfs = new ArrayList<ADF>();

	@Override
	public int getPopulationSize() {
		return 500;
	}

	@Override
	public Selection getSelection() {
//		return new RouletteSelection(206, 1);
		return new TournamentSelection(499, 2);
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
    public int getNumberOfORFs() {
        return 2;
    }

    @Override
    public int getHeadLength() {
        return 5;
    }

    @Override
    public LinkerFunction getLinkerFunction() {
        return new FunctionAproximationLinker(this.getNumberOfORFs());
    }
}
