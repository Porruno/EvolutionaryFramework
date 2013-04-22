package test;

public class GenerationsToEvaluations {

	public static void generationsToEvaluations(double[] knownPoints, double[] knownValues, double[] unknownPoints, double[] unknownValues){
		double y2;
		double y1;
		double x2;
		double x1;
		int unknownPointCursor = 0;
		for(int i = 0; i < unknownPoints.length; i++){
			for(int j = unknownPointCursor; j < knownPoints.length; j++){
				if(knownPoints[j] >= unknownPoints[i]){
					x2 = knownPoints[j];
					y2 = knownValues[j];
					if(j == 0){
						j = 1;
					}
					x1 = knownPoints[j-1];
					y1 = knownValues[j - 1];
					unknownPointCursor = j - 1;
					double slope = (y2 - y1) / (x2 -x1);
					if(Double.isNaN(slope) || Double.isInfinite(slope)){
						slope = 0;
					}
					unknownValues[i] = y1 + (unknownPoints[i] - x1) * slope;
					break;
				}
			}
		}
		
	}
}
