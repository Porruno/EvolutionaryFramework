package koza1994;

import test.Test;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
public class Koza1994_4GEP extends Test{

	@Override
	public GADelegate configure() {
		GPPopulationDelegate adf1PopulationDelegate = new ADF1PopulationDelegate();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateSimple();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);
		
		
		MainPopulationDelegate_4GEP mainPopulationDelegate = new MainPopulationDelegate_4GEP();
		mainPopulationDelegate.addADF(adf1);
		GPDelegate gpDelegate = new Koza1994GPDelegate1_2_4();
		ADF[] adfs = {adf1};
		gpDelegate.setADFs(adfs);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Koza 1994 (Simple ADF), GEP";
	}

}
