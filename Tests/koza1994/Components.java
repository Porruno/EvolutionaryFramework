package koza1994;

public class Components {

	
	public static Number multiplication(Number arg1, Number arg2){
		return new Double (arg1.doubleValue() * arg2.doubleValue());
	}
	
	public static Number divition(Number arg1, Number arg2){
		return new Double (arg1.doubleValue() / arg2.doubleValue());
	}
	
	public static Number addition(Number arg1, Number arg2){
		return new Double (arg1.doubleValue() + arg2.doubleValue());
	}
	
	public static Number substraction(Number arg1, Number arg2){
		return new Double (arg1.doubleValue() - arg2.doubleValue());
	}
	
	public static Number x(X x){
		return x.value;
	}
	
	public static Number one(){
		return 1;
	}
	
	public static Number getFirstParameterFromADF1(ADF1Input adfInput){
		return (Number) adfInput.getObjectAtIndex(0);
	}
}
