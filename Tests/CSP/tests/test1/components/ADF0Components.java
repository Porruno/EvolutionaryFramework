/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.tests.test1.components;

import CSP.CSP.Constraint;
import CSP.CSP.Variable;
import java.util.ArrayList;

/**
 *
 * @author Lay
 */
public class ADF0Components {

    public static VariablesValues1 conflicts(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] conflicts = new double[variables.length];
        for (int i = 0; i < conflicts.length; i++) {
            conflicts[i] = variables[i].getNumberOfConflicts();
        }
        return new VariablesValues1(conflicts);
    }
    
    public static VariablesValues1 constraintsInvolved(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] constraintsInvolved = new double[variables.length];
        for (int i = 0; i < constraintsInvolved.length; i++) {
            constraintsInvolved[i] = variables[i].getNumberOfConstraintsInvolved();
        }
        return new VariablesValues1(constraintsInvolved);
    }
    
    public static VariablesValues1 fdeg(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] fdegs = new double[variables.length];
        for (int i = 0; i < fdegs.length; i++) {
            fdegs[i] = variables[i].getFDeg();
        }
        return new VariablesValues1(fdegs);
    }
    
    public static VariablesValues1 bdeg(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] bdegs = new double[variables.length];
        for (int i = 0; i < bdegs.length; i++) {
            bdegs[i] = variables[i].getBDeg();
        }
        return new VariablesValues1(bdegs);
    }
    
    public static VariablesValues1 bbz(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] bbzs = new double[variables.length];
        for (int i = 0; i < bbzs.length; i++) {
            bbzs[i] = variables[i].getBBZ();
        }
        return new VariablesValues1(bbzs);
    }
    
    public static VariablesValues1 fbz(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] fbzs = new double[variables.length];
        for (int i = 0; i < fbzs.length; i++) {
            fbzs[i] = variables[i].getFBZ();
        }
        return new VariablesValues1(fbzs);
    }
    
    public static VariablesValues1 mxi(ADF0Input adf) {
        Variable variables[] = adf.getUninstantiatedVariables();

        double[] mxis = new double[variables.length];
        for (int i = 0; i < mxis.length; i++) {
            mxis[i] = variables[i].getDomain().length;
        }
        return new VariablesValues1(mxis);
    }

    public static VariablesValues2 pxi(ADF0Input adf) {
        Variable[] variables = adf.getUninstantiatedVariables();
        double[][] values = new double[variables.length][];
        for (int i = 0; i < variables.length; i++) {
            ArrayList<Constraint> constraints = variables[i].getConstraints();
            values[i] = new double[constraints.size()];
            int j = 0;
            for (Constraint constraint : constraints) {
                values[i][j] = constraint.getPxi();
            }
        }

        return new VariablesValues2(values);
    }
    
    public static VariableValue smallest(VariablesValues1 arg0, ADF0Input adf) {
        double largestValue = arg0.values[0];
        int largestValuePosition = 0;
        for (int i = 1; i < arg0.values.length; i++) {
            if (arg0.values[i] < largestValue) {
                largestValue = arg0.values[i];
                largestValuePosition = i;
            }
        }
        Variable largestVariable = adf.getUninstantiatedVariables()[largestValuePosition];
        return new VariableValue(largestVariable, largestValue);
    }

    public static VariableValue largest(VariablesValues1 arg0, ADF0Input adf) {
        double largestValue = arg0.values[0];
        int largestValuePosition = 0;
        for (int i = 1; i < arg0.values.length; i++) {
            if (arg0.values[i] > largestValue) {
                largestValue = arg0.values[i];
                largestValuePosition = i;
            }
        }
        Variable largestVariable = adf.getUninstantiatedVariables()[largestValuePosition];
        return new VariableValue(largestVariable, largestValue);
    }

    public static Boolean greaterOrEqualThan(VariableValue arg0, VariableValue arg1) {
        return arg0.value >= arg1.value;
    }

    public static Boolean smallerOrEqualThan(VariableValue arg0, VariableValue arg1) {
        return arg0.value <= arg1.value;
    }

    public static Variable variableFromVariableValue(VariableValue arg0) {
        return arg0.variable;
    }

    public static Double one() {
        return 1.0;
    }

    public static VariablesValues1 log2(VariablesValues1 arg0) {
        for (int i = 0; i < arg0.values.length; i++) {
            arg0.values[i] = Math.log(arg0.values[i]) / Math.log(2);
        }
        return arg0;
    }
}