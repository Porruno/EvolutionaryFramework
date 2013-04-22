package grammar;

/**
 *
 * Implementor of the SpecialFunction. Its first parameter is always a boolean
 * that indicates if it should return the evaluation of the second or the third
 * parameter.
 * 
 * @author Jesús Irais González Romero
 */
public class IfThenElseFunction extends SpecialFunction{
    
    /**
     * Constructs an If-Then-Else function for the given return type.
     * @param returnType The return type of this function. It will also be used
     * to indicate the type of the second and the third parameter.
     * @param probability The probability of this function to be chosen once
     * it's been randomly selected.
     */
    public IfThenElseFunction(Class<?> returnType, Double probability){
        this.parameterTypes = new Class<?>[] {Boolean.class, returnType, returnType};
        this.returnType = returnType;
        this.probability = probability;
        this.name = "If-" + returnType.getSimpleName();
    }
    
    /**
     * Evaluates this function. First evaluates the first parameter, which its
     * type is the Boolean type; if it's true, then it evaluates the second
     * parameter and returns the result of that evaluation; if it's false, 
     * occurs similarly for the third parameter.
     * @param parameters The parameters to evaluate. The first has to be of type
     * boolean. The second and the third has to be of the kind specified by the
     * return type of this function.
     * @return The evaluation of the second parameter if the evaluation of the
     * first parameter is true; otherwise, the evaluation of the third
     * parameter.
     */
    @Override
    public Object evaluate(Object... parameters) {
        Boolean condition = (Boolean) parameters[0];
        return condition? parameters[1] : parameters[2];
    }
}
