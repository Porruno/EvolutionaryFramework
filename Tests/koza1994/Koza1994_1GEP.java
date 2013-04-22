package koza1994;

import test.Test;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import Sosa2011.MainPopulationDelegate1_2_4GEP;

public class Koza1994_1GEP extends Test{

	@Override
	public GADelegate configure() {
		GPPopulationDelegate adf1PopulationDelegate = new ADF1PopulationDelegate();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateCoevolutive();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);

		GEPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate1_2_4GEP();
		GPDelegate gpDelegate = new Koza1994GPDelegate_1();
		mainPopulationDelegate.addADF(adf1);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		ADF[]adfs = {adf1};
		gpDelegate.setADFs(adfs);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Koza 1994 (Coevolutive, GEP)";
	}

}
