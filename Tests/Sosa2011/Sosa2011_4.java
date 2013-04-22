package Sosa2011;

import test.Test;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
public class Sosa2011_4 extends Test{

	@Override
	public GADelegate configure() {
		GPPopulationDelegate adf1PopulationDelegate = new ADF2_3_4PopulationDelegate();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateSimple();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);
		
		
		GPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate2_3_4();
		mainPopulationDelegate.addADF(adf1);
		GPDelegate gpDelegate = new Sosa1994GPDelegate2_3_4();
		ADF[] adfs = {adf1};
		gpDelegate.setADFs(adfs);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Sosa 2011 (Simple)";
	}

}
