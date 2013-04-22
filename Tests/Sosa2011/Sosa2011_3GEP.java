package Sosa2011;

import test.Test;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
public class Sosa2011_3GEP extends Test{

	@Override
	public GADelegate configure() {

		GEPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate2_3_4GEP();
		GPDelegate gpDelegate = new Sosa1994GPDelegate2_3_4();
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Sosa 2011 (No ADF, GEP)";
	}

}
