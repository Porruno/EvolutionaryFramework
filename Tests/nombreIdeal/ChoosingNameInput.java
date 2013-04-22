/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal;

import grammar.Input;

/**
 *
 * @author Lay
 */
public class ChoosingNameInput extends Input {
    CompoundName compoundName;

    public ChoosingNameInput(CompoundName compoundName) {
        this.compoundName = compoundName;
    }

    @Override
    public boolean equals(Input otherInput) {
        ChoosingNameInput castedOtherInput = (ChoosingNameInput) otherInput;
        if (!this.compoundName.firstName.contentEquals(castedOtherInput.compoundName.firstName)) {
            return false;
        }
        if (!this.compoundName.secondName.contentEquals(castedOtherInput.compoundName.secondName)) {
            return false;
        }

        return true;
    }
}
