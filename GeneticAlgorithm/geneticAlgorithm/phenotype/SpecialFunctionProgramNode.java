/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgorithm.phenotype;

import geneticAlgorithm.Genome;
import grammar.Function;
import grammar.IfThenElseFunction;
import grammar.Input;
import grammar.OrderByFunction;

/**
 *
 * @author Lay
 */
public class SpecialFunctionProgramNode extends ProgramNode {

    public SpecialFunctionProgramNode() {
        super();
    }

    protected SpecialFunctionProgramNode(Genome genome, Function function, int maxLevel) {
        super(genome, function, maxLevel);
    }

    @Override
    public Object evaluate(Input input) {
        Object[] parameters = new Object[this.children.size() + function.getNumberOfParametersThatAreInputType()];

        if (IfThenElseFunction.class.isAssignableFrom(this.function.getClass())) {
            parameters[0] = children.get(0).evaluate(input);
            Boolean condition = (Boolean) parameters[0];
            if (condition) {
                parameters[1] = children.get(1).evaluate(input);
            } else {
                parameters[2] = children.get(2).evaluate(input);
            }
        }
        
        if(OrderByFunction.class.isAssignableFrom(this.function.getClass())){
            parameters[0] = children.get(0).function;
            parameters[1] = children.get(1).evaluate(input);
        }
        
        return function.evaluate(parameters);
    }

    @Override
    protected Object evaluateUsingOnlyBestADFs(Input input) {
        Object[] parameters = new Object[this.children.size() + function.getNumberOfParametersThatAreInputType()];

        if (IfThenElseFunction.class.isAssignableFrom(this.function.getClass())) {
            parameters[0] = children.get(0).evaluateUsingOnlyBestADFs(input);
            Boolean condition = (Boolean) parameters[0];
            if (condition) {
                parameters[1] = children.get(1).evaluateProgramNodeUsingOnlyBestADFs(input);
            } else {
                parameters[2] = children.get(2).evaluateProgramNodeUsingOnlyBestADFs(input);
            }
        }
        return function.evaluate(parameters);
    }
}
