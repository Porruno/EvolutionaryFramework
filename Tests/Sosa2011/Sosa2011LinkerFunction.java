package Sosa2011;

import geneExpressionProgramming.LinkerFunction;

public class Sosa2011LinkerFunction extends LinkerFunction {

    public Sosa2011LinkerFunction(int numberOfORFs){
        super(numberOfORFs);
    }
    
    @Override
    public Class<?> getReturnType() {
        return Number.class;
    }

    @Override
    public Object evaluate(Object[] parameters) {
        double smallest = (Double) parameters[0];
//		for(int i = 1; i < parameters.length; i++){
//			if((Double) parameters[i] < smallest){
//				smallest = (Double) parameters[i];
//			}
//		}
        return smallest;
    }

    @Override
    public Class<?> getORFReturnType() {
        return Number.class;
    }
}
