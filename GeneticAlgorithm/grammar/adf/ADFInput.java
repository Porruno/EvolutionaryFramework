package grammar.adf;

import grammar.Input;

/**
 * Each genome needs a specified class for its imput, ADFInput is an abstract
 * class specially designed for the genomes of the population nof an ADF. The 
 * difference between the input for the main population's genomes and this kind
 * of input is that this can be generated given some parameters, while the input
 * for the main population's genomes are normally created by the user in the 
 * EvaluationInterface.
 * 
 * @author Jesús Irais González Romero
 */
public abstract class ADFInput extends Input {

	protected Object[]parameters;
	protected int iterator = 0;
        
        /**
         * Constructor method for creating an ADFInput, but the user is not 
         * intended to create objects of this class.
         */
	public ADFInput(){
                this.parameters = new Object[this.getParameterTypes().length];
	}
        
        
        /**
         * This method is called when this has received all the parameters
         * needed for its initiation. Regularly, the implementation of this
         * method will do nothing, for instance, when the functions that use this
         * input are of the kind: "getFirstParameter, getSecondParameter..."
         */
        public abstract void init();
	
        /**
         * Adds an object to the list of parameters.
         * @param parameter object to be added to the list of parameters.
         */
	public void addParameter(Object parameter){
		this.parameters[iterator] = parameter;
		iterator++;
	}
	
        /**
         * @param index Position of the desired object in the array of parameters.
         * @return The object that occupies the positiond indicated by the index
         * in the array of parameters of this ADFInput.
         */
	public Object getObjectAtIndex(int index){
		return this.parameters[index];
	}
	
        /**
         * @return An array with all the parameters used for creating this input.
         */
	public Object[] getParameters(){
		return this.parameters;
	}
	
        /**
         * Compares this object with another ADFInput.
         * @param otherInput Other ADFInput.
         * @return True if the parameters of both compared objects are equal.
         */
	@Override
	public boolean equals(Input otherInput) {
            if(this.getClass() != otherInput.getClass()) return false;
		ADFInput otherADFInput = (ADFInput) otherInput;
		if(this.parameters.length != otherADFInput.parameters.length) return false;
		for(int i = 0; i < this.parameters.length; i++){
			if(!this.parameters[i].equals(otherADFInput.parameters[i])) return false;
		}
		return false;
	}
	
        /**
         * @return An array with the classes of the parameters of this ADFInput.
         */
	public abstract Class<?>[] getParameterTypes();

}
