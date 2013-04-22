package geneticAlgorithm;

import grammar.adf.ADF;

public class GADelegate {
	
	protected PopulationDelegate mainPopulationDelegate;
        protected int numberOfGenerations;
        protected ADF[] adfs;
        protected EvaluationInterface evaluationInterface;
        
        
	public GADelegate(PopulationDelegate mainPopulationDelegate,
                int numberOfGenerations, ADF[] adfs,
                EvaluationInterface evaluationInterface){
            this.mainPopulationDelegate = mainPopulationDelegate;
            this.numberOfGenerations = numberOfGenerations;
            this.adfs = adfs;
            this.evaluationInterface = evaluationInterface;
	}

	public int getNumberOfGenerations(){
            return numberOfGenerations;
        }
	
	public PopulationDelegate getMainPopulationDelegate(){
		return this.mainPopulationDelegate;
	}
	
	public ADF[] getADFs(){
		return this.adfs;
	}
	
	public EvaluationInterface getEvaluationInterface(){
            return this.evaluationInterface;
        }	
	
}
