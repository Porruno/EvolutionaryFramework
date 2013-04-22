/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal;

import geneticAlgorithm.selection.Selection;
import geneticAlgorithm.selection.TournamentSelection;
import geneticProgramming.GPPopulationDelegate;
import java.util.HashMap;

/**
 *
 * @author Lay
 */
public class PopulationDelegate_Main_GP_Coevolutive extends GPPopulationDelegate{

    @Override
    public int getMaxLevel() {
        return 6;
    }

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
        return ChoosingNameInput.class;
    }

    @Override
    public HashMap getProabilitiesForType() {
        HashMap probabilitiesForType = new HashMap();
        probabilitiesForType.put(CompoundName.class, new Double(0));
        return probabilitiesForType;
    }
    
}
