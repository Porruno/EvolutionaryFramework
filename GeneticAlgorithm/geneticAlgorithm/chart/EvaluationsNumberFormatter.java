package geneticAlgorithm.chart;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;


/**
 * Overrides certain functions of the NumberFormat class. Is used by the charts
 * for creating the correct labels for the number of evaluations for each 
 * generation. For example, if the variable "evaluationsPerGeneration" is equal
 * to 1000, then this formatter will return 5000 when given the number 5 for formatting.
 * 
 * @author Jesús Irais González Romero
 */
public class EvaluationsNumberFormatter extends NumberFormat {
	
    
	private static final long serialVersionUID = 1L;
        /**
         * 
         */
	public double evaluationsPerGeneration;
	
	public EvaluationsNumberFormatter(double evaluationsPerGeneration){
		this.evaluationsPerGeneration = evaluationsPerGeneration;
	}

	@Override
	public StringBuffer format(double arg0, StringBuffer arg1,
			FieldPosition arg2) {
		StringBuffer sb = new StringBuffer();
		String result = (int)(arg0 * this.evaluationsPerGeneration )+ "";
		sb.append(result);
		
		return sb;
	}

	@Override
	public StringBuffer format(long number, StringBuffer toAppendTo,
			FieldPosition pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		// TODO Auto-generated method stub
		return null;
	}

}
