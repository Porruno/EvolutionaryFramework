/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal.adf;

import grammar.adf.ADFInput;
import nombreIdeal.CompoundName;

/**
 *
 * @author Lay
 */
public class ADF_1_Input extends ADFInput{
    
    public ADF_1_Input(Integer numberOfParameters){
        super(numberOfParameters);
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return new Class<?>[]{CompoundName.class};
    }
    
}
