/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgorithm.phenotype;

import geneticAlgorithm.Genome;
import grammar.Function;
import grammar.Input;

/**
 *
 * @author Lay
 */
public class SimpleProgramNode extends ProgramNode {

    public SimpleProgramNode() {
        super();
    }

    protected SimpleProgramNode(Genome genome, Function function, int maxLevel) {
        super(genome, function, maxLevel);
    }

    @Override
    public Object evaluate(Input input) {
        Object[] parameters = new Object[this.children.size() + function.getNumberOfParametersThatAreInputType()];
        int i = 0;
        int childsIterator = 0;
        for (Class<?> parameter : function.getParameterTypes()) {
            if (Input.class.isAssignableFrom(parameter)) {
                parameters[i] = input;
            } else {
                parameters[i] = children.get(childsIterator).evaluate(input);
                childsIterator++;
            }
            i++;
        }
        return function.evaluate(parameters);
    }

    @Override
    protected Object evaluateUsingOnlyBestADFs(Input input) {
        Object[] parameters = new Object[function.getParameterTypes().length];
        int i = 0;
        int childsIterator = 0;
        for (Class<?> parameter : function.getParameterTypes()) {
            if (Input.class.isAssignableFrom(parameter)) {
                parameters[i] = input;
            } else {
                parameters[i] = children.get(childsIterator).evaluateUsingOnlyBestADFs(input);
                childsIterator++;
            }
            i++;
        }
        this.result = function.evaluate(parameters);

        return this.result;
    }
}