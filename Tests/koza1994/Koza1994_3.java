package koza1994;

import test.Test;
import geneticAlgorithm.GADelegate;
import geneticProgramming.*;
public class Koza1994_3 extends Test{

	@Override
	public GADelegate configure() {

		GPPopulationDelegate mainPopulationDelegate = new MainPopulationDelegate3();
		GPDelegate gpDelegate = new Koza1994GPDelegate3();
		mainPopulationDelegate.initiate();
		gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
		return gpDelegate;
	}

	@Override
	public String getName() {
		return "Koza 1994 (No ADF)";
	}

}
