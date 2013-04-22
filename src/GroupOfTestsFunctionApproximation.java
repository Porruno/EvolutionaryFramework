import functionApproximation.X;
import functionApproximation.FunctionAproximationGEP_4;
import functionApproximation.FunctionAproximationGEP_1;
import geneExpressionProgramming.GEPGenome;
import geneticProgramming.GPGenome;
import test.GroupOfTests;


public class GroupOfTestsFunctionApproximation {
	public static void main(String[] args){
		Class<?>[] testClasses = {FunctionAproximationGEP_1.class, FunctionAproximationGEP_4.class};
		GroupOfTests groupOfTests = new GroupOfTests("Function Aproximation", testClasses, 10);
                groupOfTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().graph("Prueba", new X(3));
                groupOfTests.graph();
	}
}
