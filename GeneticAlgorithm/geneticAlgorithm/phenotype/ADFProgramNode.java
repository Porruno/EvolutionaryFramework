/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgorithm.phenotype;

import geneticAlgorithm.Genome;
import grammar.Function;
import grammar.Input;
import grammar.adf.ADF;

/**
 *
 * @author Lay
 */
public class ADFProgramNode extends ProgramNode {

    public ADFProgramNode(){
        super();
    }
    
    protected ADFProgramNode(Genome genome, Function function, int maxLevel) {
        super(genome, function, maxLevel);
    }

    @Override
    public Object evaluate(Input input) {
        Object[] parameters = new Object[function.getParameterTypes().length + 2];
        parameters[0] = this.genome.getGenomeIndexForADF((ADF) this.function);
        parameters[1] = !this.genome.isFromNewGeneration();

        int i = 2;
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
        Object[] parameters = new Object[function.getParameterTypes().length + 2];
        parameters[0] = this.genome.getGenomeIndexForBestADF((ADF) this.function);
        parameters[1] = false;

        int i = 2;
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