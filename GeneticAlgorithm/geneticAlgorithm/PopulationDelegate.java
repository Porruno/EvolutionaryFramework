package geneticAlgorithm;

import geneticAlgorithm.selection.Selection;
import grammar.adf.ADF;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopulationDelegate {

    protected ADF[] adfs;
    protected int populationSize;
    protected boolean maximization;
    protected Selection selection;
    protected double chanceOfMutation;
    protected double chanceOfCrossing;
    protected Class<?> populationType;
    protected Class<?>[] classesForComponents;
    protected Class<?> returnClassType;
    protected Class<?> inputClass;
    protected HashMap ifFunctionProbabilitiesForType;
    protected HashMap applyFunctionProbabilitiesForType;
    
    
    Population population;

    public PopulationDelegate(ADF[] adfs, int populationSize, boolean maximization,
            Selection selection, double chanceOfMutation, double chanceOfCrossing,
            Class<?> populationType, Class<?>[] classesForComponents,
            Class<?> returnClassType, Class<?> inputClass,
            HashMap ifFunctionProbabilitiesForType) {
        
        this.adfs = adfs;
        this.populationSize = populationSize;
        this.maximization = maximization;
        this.selection = selection;
        this.chanceOfMutation = chanceOfMutation;
        this.chanceOfCrossing = chanceOfCrossing;
        this.populationType = populationType;
        this.classesForComponents = classesForComponents;
        this.returnClassType = returnClassType;
        this.inputClass = inputClass;
        this.ifFunctionProbabilitiesForType = ifFunctionProbabilitiesForType;
    
    }
 

    public void initiate() {
        try {
            this.population = (Population) this.getPopulationType().getConstructor(PopulationDelegate.class).newInstance(this);
            this.population.generateInitialGeneration();
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PopulationDelegate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPopulationSize(){
        return this.populationSize;
    }

    public boolean getMaximization(){
        return this.maximization;
    }

    public Selection getSelection(){
        return this.selection;
    }

    public double getChanceOfMutation(){
        return this.chanceOfMutation;
    }

    public double getChanceOfCrossing(){
        return this.chanceOfCrossing;
    }

    public Class<?> getPopulationType(){
        return this.populationType;
    }

    public ADF[] getADFs() {
        return this.adfs;
    }

    public Population getPopultion() {
        return this.population;
    }

    public Class<?>[] getClassesForComponents(){
        return classesForComponents;
    }

    public Class<?> getReturnClassType(){
        return returnClassType;
    }

    public Class<?> getInputClass(){
        return inputClass;
    }

    public HashMap getIfFunctionProabilitiesForType(){
        return ifFunctionProbabilitiesForType;
    }
    
    public HashMap getApplyFunctionProabilitiesForType(){
        return applyFunctionProbabilitiesForType;
    }
}