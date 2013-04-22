package Sosa2011;

import geneticAlgorithm.Genome;
import geneticAlgorithm.EvaluationInterface;

public class Evaluator implements EvaluationInterface {

	@Override
	public double evaluateGenome(Genome genome){
		double correctValue;
		double x;
		double errorAcum = 0;
		double genomeValue;
		for(int i = 1; i < 10; i++){
			x = i * .2;
			double monomio1 = Math.pow(x, 5) * Math.sin(x);
			double monomio2 = x * Math.pow(Math.sin(x), 4);
			double monomio3 = 2*Math.pow(x, 3) * Math.pow(Math.sin(x), 3);
			double monomio4 = Math.pow(Math.sin(x), 2);
			double monomio5 = Math.pow(x, 2) * Math.sin(x);
			double monomio6 = Math.sin(x);
			
			correctValue = monomio1 + monomio2 - monomio3 + monomio4 - monomio5 - monomio6;

			Object result = genome.evaluate(new X(x));
			genomeValue = ((Number) result).doubleValue();
			if(Double.isNaN(genomeValue) || Double.isInfinite(genomeValue)){
				genomeValue = 0;
			}
			errorAcum += Math.abs(correctValue - genomeValue);

		}
		return errorAcum;
	}

}
