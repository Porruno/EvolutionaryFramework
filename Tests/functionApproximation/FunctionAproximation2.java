package functionApproximation;

import test.Test;
import geneticAlgorithm.*;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;

public class FunctionAproximation2 extends Test {
	
	@Override
	public GADelegate configure() {
		GPPopulationDelegate adf1PopulationDelegate = new ADF1PopulationDelegate();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateSemicoevolutive();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);

		GPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate1_2_4();
		GPDelegate gpDelegate = new FunctionAproximationGPDelegate1_2();
		mainPopulationDelegate.addADF(adf1);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		ADF[]adfs = {adf1};
		gpDelegate.setADFs(adfs);

		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Function aproximation 2 (Semicoevolutive)";
	}

}
