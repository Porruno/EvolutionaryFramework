package test;

import geneticAlgorithm.GADelegate;
import geneticAlgorithm.GeneticAlgorithm;
import geneticAlgorithm.Genome;

public abstract class Test {

    public GeneticAlgorithm geneticAlgorithm;
    protected Genome bestGenome;
    protected double[] collectedData;
    private int generations;
    private int evaluationsPerGeneration;
    private boolean maximization;
    private int totalEvaluations;

    public Test() {
        GADelegate gaDelegate = this.configure();
        this.geneticAlgorithm = new GeneticAlgorithm(gaDelegate);

        this.generations = gaDelegate.getNumberOfGenerations();
        this.evaluationsPerGeneration = this.geneticAlgorithm.getEvaluationsPerGeneration();
        this.maximization = this.geneticAlgorithm.getMainPopulation().getMaximization();
        this.totalEvaluations = this.getGenerations() * this.getEvaluationsPerGeneration();

    }

    public void runTest(int runs) {
        this.collectedData = new double[this.getGenerations()];
        for (int i = 0; i < runs; i++) {
            if (i > 0) {
                this.geneticAlgorithm = new GeneticAlgorithm(this.configure());
            }
            geneticAlgorithm.run();
            double[] runCollectedData = geneticAlgorithm.getBestAptitudes();
            for (int j = 0; j < collectedData.length; j++) {
                collectedData[j] += runCollectedData[j];
            }
            if (i == 0) {
                bestGenome = geneticAlgorithm.getBestGenomeEver();
            } else {
                Genome runBestGenome = geneticAlgorithm.getBestGenomeEver();
                if (runBestGenome.compareTo(runBestGenome) == -1) {
                    bestGenome = runBestGenome;
                }
            }
        }

        for (int i = 0; i < collectedData.length; i++) {
            collectedData[i] /= runs;
        }

    }

    public double[] getCollectedData() {
        return this.collectedData;
    }

    public Genome getBestGenome() {
        return this.bestGenome;
    }

    public int getEvaluationsPerGeneration() {
        return this.evaluationsPerGeneration;
    }

    public boolean getMaximization() {
        return this.maximization;
    }

    public int getGenerations() {
        return this.generations;
    }

    public int getTotalEvaluations() {
        return totalEvaluations;
    }

    public abstract String getName();

    public abstract GADelegate configure();
}