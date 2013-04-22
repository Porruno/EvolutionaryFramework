package koza1994;

import grammar.adf.ADFInput;

public class ADF1Input extends ADFInput{

	public ADF1Input(Integer numberOfParameters) {
		super(numberOfParameters);
	}

	@Override
	public Class<?>[] getParameterTypes() {
		Class<?>[] parameterTypes = {Number.class};
		return parameterTypes;
	}

}
