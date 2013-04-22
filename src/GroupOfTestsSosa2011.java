import Sosa2011.X;
import Sosa2011.Sosa2011_4GEP;
import test.GroupOfTests;


public class GroupOfTestsSosa2011 {
	public static void main(String[] args){
		Class<?>[] testClasses = {Sosa2011_4GEP.class};
		GroupOfTests groupOfTests = new GroupOfTests("Sosa 2011", testClasses, 1);
                groupOfTests.graph();

		for(int i = 0; i < testClasses.length ; i++){
			System.out.printf("Aptitude of test %d: %f\n", i, groupOfTests.getTestResultAtIndex(i).getBestGenomeOfAllRuns().getAptitude());
		}
                groupOfTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().graph("Prueba", new X(3));
	}
}