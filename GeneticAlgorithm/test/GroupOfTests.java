package test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupOfTests {

    protected String name;
    protected TestResult[] testResults;
    protected int evaluationsPerGenerationToUse;

    public GroupOfTests(String name, Class<?>[] testClasses, int runs) {
        this.name = name;
        this.testResults = new TestResult[testClasses.length];

        for (int i = 0; i < testClasses.length; i++) {
            try {
                Test aTest = (Test) testClasses[i].newInstance();
                testResults[i] = new TestResult(aTest, runs);
            } catch (InstantiationException ex) {
                Logger.getLogger(GroupOfTests.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GroupOfTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.evaluationsPerGenerationToUse = testResults[0].evaluationsPerGeneration;
        for (int i = 1; i < testResults.length; i++) {
            if (testResults[i].evaluationsPerGeneration < evaluationsPerGenerationToUse) {
                this.evaluationsPerGenerationToUse = testResults[i].evaluationsPerGeneration;
            }
        }

        for (int i = 0; i < testResults.length; i++) {
            TestResult tr = testResults[i];
            int totalEvaluations = tr.totalEvaluations;
            int numberOfPointsToCalculate = (int) ((double) totalEvaluations / evaluationsPerGenerationToUse);

            double remainder = (double) totalEvaluations % evaluationsPerGenerationToUse;
            if (remainder != 0) {
                numberOfPointsToCalculate += 1;
            }

            double[] knownNodes = new double[tr.getGenerations()];
            double[] unknownNodes = new double[numberOfPointsToCalculate];
            for (int j = 0; j < knownNodes.length; j++) {
                knownNodes[j] = (j + 1) * tr.evaluationsPerGeneration;
            }
            for (int j = 0; j < numberOfPointsToCalculate - 1; j++) {
                unknownNodes[j] = (j + 1) * evaluationsPerGenerationToUse;
            }

            if (remainder != 0) {
                unknownNodes[numberOfPointsToCalculate - 1] = (numberOfPointsToCalculate - 1) * evaluationsPerGenerationToUse + remainder;
            } else {
                unknownNodes[numberOfPointsToCalculate - 1] = (numberOfPointsToCalculate) * evaluationsPerGenerationToUse;
            }

            //Average best
            double[] knownBest = tr.averageBest;
            double[] unknownBest = new double[numberOfPointsToCalculate];
            GenerationsToEvaluations.generationsToEvaluations(knownNodes, knownBest, unknownNodes, unknownBest);
            tr.averageBest = unknownBest;
        }
    }

    public void graph() {
        new GroupOfTestsChart(this.name, this.testResults, this.evaluationsPerGenerationToUse);
    }

    public TestResult getTestResultAtIndex(int index) {
        return this.testResults[index];
    }
}