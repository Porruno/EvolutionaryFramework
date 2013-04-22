/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.tests.test1.components;

/**
 *
 * @author Lay
 */
public class ArithmeticComponents {
    //****************************************************
    //*********************S U M A************************
    //****************************************************

    public static VariablesValues1 plus(Double arg0, VariablesValues1 arg1) {
        double castedArg0 = arg0.doubleValue();
        for (int i = 0; i < arg1.values.length; i++) {
            arg1.values[i] = arg1.values[i] + castedArg0;
        }
        return arg1;
    }

    public static VariablesValues1 plus(VariablesValues1 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            arg0.values[i] = arg0.values[i] + arg1.values[i];
        }
        return arg0;
    }

    public static VariablesValues2 plus(VariablesValues2 arg0, Double arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] + arg1;
            }
        }
        return arg0;
    }

    public static VariablesValues2 plus(VariablesValues2 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] + arg1.values[i];
            }
        }
        return arg0;
    }

    public static Double plus(VariablesValues1 arg0) {
        double acum = 0;
        for (int i = 0; i < arg0.values.length; i++) {
            acum += arg0.values[i];
        }
        return acum;
    }

    public static VariablesValues1 plus(VariablesValues2 arg0) {
        double[] acums = new double[arg0.values.length];
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                acums[i] += arg0.values[i][j];
            }
        }

        VariablesValues1 vv1 = new VariablesValues1(acums);
        return vv1;
    }

    //****************************************************
    //***********M U L T I P L I C A C I O N**************
    //****************************************************
    
    public static VariablesValues1 multiplication(Double arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg1.values.length; i++) {
            arg1.values[i] = arg1.values[i] * arg0;
        }
        return arg1;
    }

    public static VariablesValues1 multiplication(VariablesValues1 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            arg0.values[i] = arg0.values[i] * arg1.values[i];
        }
        return arg0;
    }

    public static VariablesValues2 multiplication(VariablesValues2 arg0, Double arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] * arg1;
            }
        }
        return arg0;
    }

    public static VariablesValues2 multiplication(VariablesValues2 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] * arg1.values[i];
            }
        }
        return arg0;
    }

    public static Double multiplication(VariablesValues1 arg0) {
        double acum = 1;
        for (int i = 0; i < arg0.values.length; i++) {
            acum *= arg0.values[i];
        }
        return acum;
    }

    public static VariablesValues1 multiplication(VariablesValues2 arg0) {
        double[] acums = new double[arg0.values.length];
        for (int i = 0; i < acums.length; i++) {
            acums[i] = 1;
        }
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                acums[i] *= arg0.values[i][j];
            }
        }

        VariablesValues1 vv1 = new VariablesValues1(acums);
        return vv1;
    }

    //****************************************************
    //********************R E S T A***********************
    //****************************************************

    public static VariablesValues1 minus(Double arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg1.values.length; i++) {
            arg1.values[i] = arg1.values[i] - arg0;
        }
        return arg1;
    }

    public static VariablesValues1 minus(VariablesValues1 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            arg0.values[i] = arg0.values[i] - arg1.values[i];
        }
        return arg0;
    }

    public static VariablesValues2 minus(VariablesValues2 arg0, Double arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] - arg1;
            }
        }
        return arg0;
    }

    public static VariablesValues2 minus(VariablesValues2 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            for (int j = 0; j < arg0.values[i].length; j++) {
                arg0.values[i][j] = arg0.values[i][j] - arg1.values[i];
            }
        }
        return arg0;
    }

    //****************************************************
    //******************D I V I S I O N*******************
    //****************************************************
    public static VariablesValues1 division(Double arg0, VariablesValues1 arg1) {
        if (arg0 != 0) {
            for (int i = 0; i < arg1.values.length; i++) {
                arg1.values[i] = arg1.values[i] / arg0;
            }
        }
        return arg1;
    }

    public static VariablesValues1 division(VariablesValues1 arg0, VariablesValues1 arg1) {
        for (int i = 0; i < arg0.values.length; i++) {
            if(arg1.values[i] == 0) continue;
            arg0.values[i] = arg0.values[i] / arg1.values[i];
        }
        return arg0;
    }



    //****************************************************
    //******************N E G A T I O N*******************
    //****************************************************
    public static VariablesValues1 negative(VariablesValues1 arg0){
        for(int i = 0; i < arg0.values.length; i++){
            arg0.values[i] *= -1;
        }
        return arg0;
    }
    
    public static VariablesValues2 negative(VariablesValues2 arg0){
        for(int i = 0; i < arg0.values.length; i++){
            for(int j = 0; j < arg0.values[i].length; j++){
                arg0.values[i][j] *= -1;
            }
        }
        return arg0;
    }   
}