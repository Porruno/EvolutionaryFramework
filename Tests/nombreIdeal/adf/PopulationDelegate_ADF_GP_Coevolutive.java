/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal.adf;

import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;
import java.util.HashMap;
import nombreIdeal.Components;
import nombreIdeal.CompoundName;

/**
 *
 * @author Lay
 */
public class PopulationDelegate_ADF_GP_Coevolutive extends GPPopulationDelegate {

    @Override
    public int getPopulationSize() {
        return 100;
    }

    @Override
    public boolean getMaximization() {
        return false;
    }

    @Override
    public Selection getSelection() {
        return new TournamentSelection(90, 2);
    }

    @Override
    public double getChanceOfMutation() {
        return .05;
    }

    @Override
    public double getChanceOfCrossing() {
        return .9;
    }

    @Override
    public Class<?>[] getClassesForComponents() {
        return new Class<?>[]{Components.class};
    }

    @Override
    public Class<?> getReturnClassType() {
        return Boolean.class;
    }

    @Override
    public Class<?> getInputClass() {
        return ADF_1_Input.class;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
    
    @Override
    public HashMap getProabilitiesForType() {
        HashMap probabilitiesForType = new HashMap();
        probabilitiesForType.put(CompoundName.class, new Double(0));
        return probabilitiesForType;
    }
}