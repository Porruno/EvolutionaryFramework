package functionApproximation;

import grammar.Input;

public class X extends Input {
	public Number value;
	
	public X(Number value){
		this.value = value;
	}
	
	public boolean equals(Input otherInput){
		X otherX = (X) otherInput;
		return this.value.doubleValue() == otherX.value.doubleValue();
	}
}
