package grammar;

/**
 * 
 * This class is an abstraction of a function. Is an abstract class whose
 * implementors are: SimpleFunction, ADF, and SpecialFunction.
 * 
 * @author Jesús Irais González Romero
 */
public abstract class Function {

    protected Class<?> returnType;
    protected String name;
    protected Class<?>[] parameterTypes;
    protected int minCalls = Integer.MAX_VALUE - 1;
    protected boolean isTerminal;

    /**
     * Returns the result that corresponds to the given parameters.
     * @param parameters Parameters to be evaluated.
     * @return The result that corresponds to the given parameters.
     */
    public abstract Object evaluate(Object... parameters);

    /**
     * @return The return type of this function.
     */
    public Class<?> getReturnType() {
        return this.returnType;
    }

    /**
     * @return The name of this function.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Deduces if this function is terminal and sets its isTerminal property.
     */
    public void checkIfIsTerminal() {
        this.isTerminal = true;
        for (int i = 0; i < this.parameterTypes.length; i++) {
            if (Input.class.isAssignableFrom(this.parameterTypes[i])) {
                continue;
            }
            this.isTerminal = false;
            break;
        }
    }

    /**
     * @return The parameters' class of this function.
     */
    public abstract Class<?>[] getParameterTypes();

    /**
     * @return The number of parameters of this function.
     */
    public int getArity() {
        return this.getParameterTypes().length;
    }

    /**
     * @return The number of parameters whose type is a kind of Input.
     */
    public int getNumberOfParametersThatAreInputType() {
        int acum = 0;
        for (Class<?> aClass : this.getParameterTypes()) {
            if (Input.class.isAssignableFrom(aClass)) {
                acum++;
            }
        }
        return acum;
    }

    /**
     * @return A boolean that indicates if this function is terminal.
     */
    public boolean isTerminal() {
        return this.isTerminal;
    }

    /**
     * @return The minimum number of function calls needed to return the result
     * of evaluating this function. It includes the call to this function.
     */
    public int getMinCalls() {
        return this.minCalls;
    }

    /**
     * Sets the minimum number of calls needed to return the result of
     * evaluating this function. The number should count the call to this
     * function.
     * @param minCalls The number of minimum calls.
     */
    protected void setMinCalls(int minCalls) {
        this.minCalls = minCalls;
    }

    /**
     * @return The name of this function.
     */
    @Override
    public String toString() {
        return this.getName();
    }
}