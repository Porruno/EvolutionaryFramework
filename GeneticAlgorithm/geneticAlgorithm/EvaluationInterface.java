package geneticAlgorithm;

import geneticAlgorithm.Genome;

public abstract interface EvaluationInterface {
	public double evaluateGenome(Genome genome);
}
