package Sosa2011;

import test.Test;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;

public class Sosa2011_4GEP extends Test{

	@Override
	public GADelegate configure() {
		GEPPopulationDelegate adf1PopulationDelegate = new ADF4PopulationDelegateGEP();
		adf1PopulationDelegate.initiate();
		ADFDelegate adf1Delegate = new ADF1DelegateSimple();
		adf1Delegate.setPopulationDelegate(adf1PopulationDelegate);

		ADF adf1 = new ADF(0, adf1Delegate);

		GEPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate1_2_4GEP();
		GPDelegate gpDelegate = new Sosa1994GPDelegate1_2_4();
		mainPopulationDelegate.addADF(adf1);
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		ADF[]adfs = {adf1};
		gpDelegate.setADFs(adfs);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Sosa 2011 (with_ADF, GEP)";
	}

}