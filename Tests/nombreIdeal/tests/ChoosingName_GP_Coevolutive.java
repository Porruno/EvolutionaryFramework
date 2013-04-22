/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal.tests;

import nombreIdeal.adf.ADFDelegate_Coevolutive;
import geneticAlgorithm.GADelegate;
import geneticProgramming.GPDelegate;
import geneticProgramming.GPPopulationDelegate;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import nombreIdeal.GADelegates.ChoosingName_GADelegate;
import nombreIdeal.PopulationDelegate_Main_GP_Coevolutive;
import nombreIdeal.adf.PopulationDelegate_ADF_GP_Coevolutive;
import test.Test;
import Sosa2011.ADF1DelegateCoevolutive;
import Sosa2011.ADF1PopulationDelegate;
import Sosa2011.MainPopulationDelegate1;
import Sosa2011.Sosa1994GPDelegate1;

/**
 *
 * @author Lay
 */
public class ChoosingName_GP_Coevolutive extends Test {

    @Override
    public GADelegate configure() {

        PopulationDelegate_ADF_GP_Coevolutive adfPopulationDelegate = new PopulationDelegate_ADF_GP_Coevolutive();
        adfPopulationDelegate.initiate();
        ADFDelegate_Coevolutive adfDelegate = new ADFDelegate_Coevolutive();
        adfDelegate.setPopulationDelegate(adfPopulationDelegate);

        ADF adf = new ADF(0, adfDelegate);

        PopulationDelegate_Main_GP_Coevolutive mainPopulationDelegate = new PopulationDelegate_Main_GP_Coevolutive();
        mainPopulationDelegate.addADF(adf);
        mainPopulationDelegate.initiate();

        GPDelegate gpDelegate = new ChoosingName_GADelegate();
        gpDelegate.setMainPopulationDelegate(mainPopulationDelegate);
        ADF[] adfs = {adf};
        gpDelegate.setADFs(adfs);
        return gpDelegate;
    }

    @Override
    public String getName() {
        return "GP Coevolutive";
    }
}
