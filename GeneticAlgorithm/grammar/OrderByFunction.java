/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grammar;

import geneticAlgorithm.Utility.ObjectNumber;
import geneticAlgorithm.Utility.Utility;
import java.util.ArrayList;

/**
 * Implementor of a SpecialFunction. It takes the function of its first
 * parameter instead of evaluating it and uses it to order the elements of the
 * second parameter, which has to be an array. Special considerations had to be
 * taken: The arity of the function of the first parameter has to be 1 and the
 * type of that parameter has to be compatible with the type of the components of
 * the array of the second parameter of this function.
 * @author Jesús Irais González Romero
 */
public class OrderByFunction extends SpecialFunction{
    
    public OrderByFunction(Class<?> returnType, Double probability){
        if(!returnType.isArray()){
            System.out.println("Return type needs to be an Array Class.");
            System.exit(-1);
        }
        
        this.parameterTypes = new Class<?>[] {Number.class, returnType};
        this.returnType = returnType;
        this.probability = probability;
        this.name = "OrderBy-" + returnType.getComponentType().getSimpleName() + "[]";
    }

    /**
     * @param parameters An array of 2 objects. The first has to be a non
     * terminal function that will be used to order the objects contained by
     * the array, which is the second object in the array of parameters.
     * @return The objects of the second parameter ordered by applying them the
     * function of the first parameter.
     */
    @Override
    public Object evaluate(Object... parameters) {
        Function function = (Function) parameters[0];
        Object[] objects = (Object[]) parameters[1];
        
        Object[] functionParameters = new Object[function.getArity()];
        
        ArrayList<ObjectNumber> orderedObjectNumbers = new ArrayList<ObjectNumber>(objects.length);
        
        for(Object object : objects){
            for(int i = 0; i < functionParameters.length; i++){
                functionParameters[i] = object;
            }
            Number value = (Number) function.evaluate(functionParameters);
            Utility.insertObjectNumberInOrder(orderedObjectNumbers, new ObjectNumber(object, value.doubleValue()));
        }
        Object[] orderedObjects = new Object[orderedObjectNumbers.size()];
        
        for(int i = 0; i < orderedObjects.length; i++){
            orderedObjects[i] = orderedObjectNumbers.remove(0).object;
        }
        
        Boolean condition = (Boolean) parameters[0];
        return condition? parameters[1] : parameters[2];
    }
        
}
