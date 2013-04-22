/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koza1994;

import geneExpressionProgramming.LinkerFunction;

/**
 *
 * @author Ale
 */
class Koza1994LinkerFunction extends LinkerFunction {

    public Koza1994LinkerFunction(int numberOfORFs){
        super(numberOfORFs);
    }
    
   @Override
	public Class<?> getReturnType() {
		return Number.class;
	}

	@Override
	public Object evaluate(Object[] parameters) {
		double smallest = (Double)parameters[0];
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
