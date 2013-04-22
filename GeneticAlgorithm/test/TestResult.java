package test;

import geneticAlgorithm.Genome;

public class TestResult {
	public int evaluationsPerGeneration;
	public int generations;
	public int totalEvaluations;

	public String testName;
        
	public Genome bestGenome;
	public double[] averageBest;

	public Test test;

	public int getGenerations(){
		return generations;
	}

	public TestResult(Test test, int runs){
		this.test = test;
                test.runTest(runs);
                this.averageBest = test.getCollectedData();
		bestGenome = test.getBestGenome();
                
		this.evaluationsPerGeneration = test.getEvaluationsPerGeneration();
		this.generations = test.getGenerations();
		this.totalEvaluations = test.getTotalEvaluations();
		this.testName = test.getName();
	}
        
        public double[] getAverageBest(){
            return this.averageBest;
        }

	public Genome getBestGenomeOfAllRuns(){
		return this.bestGenome;
	}

}
