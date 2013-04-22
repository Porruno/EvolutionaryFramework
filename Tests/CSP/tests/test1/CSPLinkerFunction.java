/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.tests.test1;

import CSP.CSP.Variable;
import geneExpressionProgramming.LinkerFunction;

/**
 *
 * @author Lay
 */
public class CSPLinkerFunction extends LinkerFunction{
    
    @Override
    public Class<?> getORFReturnType() {
        return Variable.class;
    }

    @Override
    public Object evaluate(Object... parameters) {
        return parameters[0];
    }

    @Override
    public int getNumberOfORFs() {
        return 1;
    }
}
