/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal;

import nombreIdeal.adf.ADF_1_Input;

/**
 *
 * @author Lay
 */
public class Components {

    public static CompoundName compoundName(ChoosingNameInput input) {
        return input.compoundName;
    }

    public static CompoundName getFirstParameterFromADFInput(ADF_1_Input input) {
        return (CompoundName) input.getObjectAtIndex(0);
    }

    public static Boolean and(Boolean arg1, Boolean arg2) {
        return arg1 && arg2;
    }

    public static Boolean or(Boolean arg1, Boolean arg2) {
        return arg1 || arg2;
    }

    public static Boolean not(Boolean arg1) {
        return !arg1.booleanValue();
    }

    public static Boolean greaterThan(Integer arg1, Integer arg2) {
        return arg1.intValue() > arg2.intValue();
    }

    public static Boolean yes() {
        return true;
    }

    public static Boolean no() {
        return false;
    }

    public static Boolean hasMixedGenre(CompoundName compoundName) {
        return compoundName.hasMixedGenre();
    }

    public static Boolean hasMixedNationality(CompoundName compoundName) {
        return compoundName.hasMixedNationality();
    }

    public static Boolean isMasculine(CompoundName compoundName) {
        return compoundName.isMasculine();
    }

    public static Integer length(CompoundName compoundName) {
        return compoundName.length();
    }

    public static Integer three() {
        return 3;
    }

    public static Integer six() {
        return 6;
    }

    public static Integer addition(Integer arg1, Integer arg2) {
        return arg1.intValue() + arg2.intValue();
    }
}
