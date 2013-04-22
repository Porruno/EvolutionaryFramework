import functionApproximation.FunctionAproximation1;
import functionApproximation.FunctionAproximation2;
import functionApproximation.FunctionAproximation3;
import functionApproximation.FunctionAproximation4;


public class NumEvaluacionesTest {
	public static void main(String[] args){

		FunctionAproximation1 fa1 = new FunctionAproximation1();
		FunctionAproximation2 fa2 = new FunctionAproximation2();
		FunctionAproximation3 fa3 = new FunctionAproximation3();
		FunctionAproximation4 fa4 = new FunctionAproximation4();
		
		fa1.configure();
		fa2.configure();
		fa3.configure();
		fa4.configure();
		
		fa1.runTest(1);
		fa2.runTest(1);
		fa3.runTest(1);
		fa4.runTest(1);
		
		int evaluations1 = fa1.geneticAlgorithm.getEvaluationsPerGeneration();
		int evaluations2 = fa2.geneticAlgorithm.getEvaluationsPerGeneration();
		int evaluations3 = fa3.geneticAlgorithm.getEvaluationsPerGeneration();
		int evaluations4 = fa4.geneticAlgorithm.getEvaluationsPerGeneration();
		
		System.out.printf("Evaluations coevolutive: %d\n" +
				"Evaluations semicoevolutive: %d\n" +
				"Evaluations no adf: %d\n" +
				"Evaluations simple adf: %d\n", evaluations1, evaluations2, evaluations3, evaluations4);
	}
}
