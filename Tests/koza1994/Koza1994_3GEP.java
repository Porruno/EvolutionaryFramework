package koza1994;

import test.Test;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
import Sosa2011.MainPopulationDelegate3GEP;
public class Koza1994_3GEP extends Test{

	@Override
	public GADelegate configure() {

		GEPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate3GEP();
		GPDelegate gpDelegate = new Koza1994GPDelegate3();
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Koza 1994 (Without ADF, GEP)";
	}

}
