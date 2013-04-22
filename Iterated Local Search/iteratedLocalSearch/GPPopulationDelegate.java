package iteratedLocalSearch;

import geneticAlgorithm.PopulationDelegate;
import geneticAlgorithm.selection.Selection;
import grammar.adf.ADF;
import java.util.HashMap;

public class GPPopulationDelegate extends PopulationDelegate {
    
    protected int maxLevel;
    
    public GPPopulationDelegate(ADF[] adfs, int populationSize, boolean maximization,
            Selection selection, double chanceOfMutation, double chanceOfCrossing,
            Class<?>[] classesForComponents, Class<?> returnClassType,
            Class<?> inputClass, HashMap ifFunctionProbabilitiesForType,
            int maxLevel){
        
        super(adfs, populationSize, maximization, selection, chanceOfMutation,
                chanceOfCrossing, GPPopulation.class, classesForComponents,
                returnClassType, inputClass, ifFunctionProbabilitiesForType);
        
        this.maxLevel = maxLevel;
    }

	public int getMaxLevel(){
            return this.maxLevel;
        }
}
