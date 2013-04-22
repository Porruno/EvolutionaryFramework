package Sosa2011;

import test.Test;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
public class Sosa2011_3 extends Test{

	@Override
	public GADelegate configure() {

		GPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate2_3_4();
		GPDelegate gpDelegate = new Sosa1994GPDelegate2_3_4();
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Sosa 2011 (No ADF)";
	}

}
