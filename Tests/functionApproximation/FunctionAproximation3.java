package functionApproximation;

import test.Test;
import geneticAlgorithm.*;
import geneticProgramming.*;
public class FunctionAproximation3 extends Test {
	
	@Override
	public GADelegate configure() {

		GPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate3();
		GPDelegate gpDelegate = new FunctionAproximationGPDelegate3();
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);

		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Function aproximation 3 (No ADF)";
	}

}
