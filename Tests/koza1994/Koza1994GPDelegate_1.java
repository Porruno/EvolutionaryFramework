/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package koza1994;
import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Koza1994GPDelegate_1 extends
		GPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 50;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
