/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grammar;

import geneticAlgorithm.Utility.ObjectNumber;
import geneticAlgorithm.Utility.Utility;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lay
 */
public class ApplyFunction extends SpecialFunction{
    protected Class<?>[] parameters;
    
    public ApplyFunction(Class<?> arrayType, Double probability){
        if(!arrayType.isArray()){
            Logger.getLogger(ApplyFunction.class.getName()).log(Level.SEVERE, null, new Exception("arrayType needs to be an Array Class."));
        }
        
        this.parameters = new Class<?>[] {returnType, arrayType};
        this.returnType = arrayType.getComponentType();
        this.probability = probability;
        this.name = "Apply-" + arrayType.getComponentType().getSimpleName() + "[]" + " - " + returnType.getSimpleName();
    }

    @Override
    public Object evaluate(Object... parameters) {
        Function function = (Function) parameters[0];
        Object[] objects = (Object[]) parameters[1];
        
        if(objects.length == 1) return objects[0];
        
        int functionArity = function.getArity();
        
        if(functionArity != 2){
            Logger.getLogger(ApplyFunction.class.getName()).log(Level.SEVERE, null, new Exception("Function arity should be 2!"));
        }
        
        Object[] functionParameters = new Object[functionArity];
        
        System.arraycopy(objects, 0, functionParameters, 0, 2);
        Object result = function.evaluate(functionParameters);
        
        for(int i = 1; i < objects.length; i++){
            functionParameters[0] = result;
            functionParameters[1] = objects[i];
            result = function.evaluate(functionParameters);
        }
        return result;
    }
}
