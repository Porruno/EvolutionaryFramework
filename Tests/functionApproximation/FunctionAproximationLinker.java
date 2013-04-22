/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package functionApproximation;

import geneExpressionProgramming.LinkerFunction;
/**
 *
 * @author Ale
 */
public class FunctionAproximationLinker extends LinkerFunction{

    public FunctionAproximationLinker(int numberOfORFs){
        super(numberOfORFs);
    }
    
    @Override
    public Class<?> getReturnType() {
        return Number.class;
    }

    @Override
    public Object evaluate(Object[] parameters) {
        double acum = 0;
        
        for(Object param : parameters){
            acum += ((Number) param).doubleValue();
        }
        
        return new Double(acum);
    }

    @Override
    public Class<?> getORFReturnType() {
        return Number.class;
    }
    
}
