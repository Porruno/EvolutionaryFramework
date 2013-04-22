/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.tests.test1.components;

import CSP.CSP.CSP;
import CSP.CSP.Variable;
import grammar.adf.ADFInput;

public class ADF0Input extends ADFInput {
    static Class<?>[] parameterTypes = new Class<?>[]{CSP.class};
    Variable[] uninstantiatedVariables;

    @Override
    public void init() {
        this.uninstantiatedVariables = ((CSP)this.parameters[0]).getUninstantiatedVariables();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return ADF0Input.parameterTypes;
    }
    
    public Variable[] getUninstantiatedVariables(){
        return this.uninstantiatedVariables;
    }
    
}
