package functionApproximation;

import geneExpressionProgramming.GEPPopulationDelegate;
import test.Test;
import geneticAlgorithm.*;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;

public class FunctionAproximationGEP_4 extends Test {
	
	@Override
	public GADelegate configure() {
		GEPPopulationDelegate adf4PopulationDelegate = new ADF4PopulationDelegate();
		adf4PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateSimple();
		adf1Delegate.setPopulationDelegate(adf4PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);

		GEPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegateGEP_4();
		GPDelegate gpDelegate = new FunctionAproximationGEPDelegate4();
		mainPopulationDelegate.addADF(adf1);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		ADF[]adfs = {adf1};
		gpDelegate.setADFs(adfs);

		return gpDelegate;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Function aproximation 1 (ADF Simple)";
	}

}
