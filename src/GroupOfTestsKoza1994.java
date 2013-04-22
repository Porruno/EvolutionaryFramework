import koza1994.Koza1994_1;
import test.GroupOfTests;


public class GroupOfTestsKoza1994 {
	public static void main(String[] args){
		Class<?>[] testClasses = {Koza1994_1.class};
		GroupOfTests groupOfTests = new GroupOfTests("Koza 1994", testClasses, 10);
		groupOfTests.graphAverageAverage();
		groupOfTests.graphAverageBest();
		for(int i = 0; i < testClasses.length ; i++){
			System.out.printf("Aptitude of test %d: %f\n", i, groupOfTests.getTestResultAtIndex(i).getBestGenomeOfAllRuns().getAptitude());
		}
	}
}