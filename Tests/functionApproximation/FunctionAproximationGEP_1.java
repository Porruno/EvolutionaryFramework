package functionApproximation;

import geneExpressionProgramming.GEPPopulationDelegate;
import test.Test;
import geneticAlgorithm.*;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;

public class FunctionAproximationGEP_1 extends Test {
	
	@Override
	public GADelegate configure() {
		GEPPopulationDelegate adf1PopulationDelegate = new ADF1PopulationDelegateGEP();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateCoevolutive();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);

		MainPopulationDelegateGEP_1 mainPopulationDelegate = new MainPopulationDelegateGEP_1();
		GPDelegate gpDelegate = new FunctionAproximationGEPDelegate1();
		mainPopulationDelegate.addADF(adf1);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		ADF[]adfs = {adf1};
		gpDelegate.setADFs(adfs);

		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Function aproximation 1 (Coevolutive)";
	}

}
