package virtualGene;

public class VirtualGene {

	
	public enum CrossoverPointsDistribution{
		TRADITIONAL, GENERALIZED, CONTINUOUS;
	}
	
	double rmin;
	double rmax;
	double deltar;
	int base;
	double N;
	double delta; // >= base
	CrossoverPointsDistribution crossoverPointsDistribution;

	public VirtualGene(double rmin, double rmax, int base, double N, double deltasPercentage, CrossoverPointsDistribution crossoverPointsDistribution) throws Exception{
		this.rmin = rmin;
		this.rmax = rmax;
		this.base = base;
		this.N = N;
		this.delta = (rmax - rmin) * deltasPercentage;
		if(this.delta < this.base){
			System.out.println("Delta  can't be smaller than the base");
			this.delta = this.base;
		}
		this.deltar = (this.rmax - this.rmin) / Math.pow(this.base, this.N);
		this.crossoverPointsDistribution = crossoverPointsDistribution;
	}
	
	public double[] crossover(double r1, double r2){
		double[] result = new double[2];
		double X = X(r1, r2);
		result[0] = r2 + X;
		result[1] = r1 - X;
		return result;
	}

	public double X(double r1, double r2){
		double k = k(r1, r2, this.crossoverPointsDistribution);
		double a = modulus(r1-rmin,  k-rmin);
		double b = modulus(r2-rmin, k-rmin);
		return a  - b;
	}

	public double L(double r, double k){
		return modulus(r-rmin, k-rmin) + rmin;
	}

	public double H(double r, double k){
		return r - modulus(r-rmin, k-rmin);
	}
	
	public double randomSegment(double l, double h, double k){
		double deltaToUse;
		if(l + h + (k * this.delta) > this.rmax){
			deltaToUse = (this.rmax - l - h) / k;
		}else{
			deltaToUse = this.delta;
		}
		
		return (k - rmin) * Math.floor(deltaToUse * Math.random()) - rmin;
	}

	public double mutation(double r){
		double k = k(0, 0, this.crossoverPointsDistribution);
		double l = L(r, k);
		double hK = delta*(k - rmin) + rmin;
		double h = H(r, hK);
		double segment = randomSegment(l, h, k);
		double result = l + h + segment;
		
		if(result > this.rmax){
			System.out.println("l: " + l);
			System.out.println("h: " + h);
			System.out.println("hK: " + hK);
			System.out.println("segment: " + segment);
		}
		return result;
	}
	
	public double fromPToR(double p){
		return p * deltar + this.rmin;
	}
	
	public double fromRToP(double r){
		return (r - rmin) / ((this.rmax - this.rmin) / Math.pow(base, N)); 
	}

	public double k(double r1, double r2, CrossoverPointsDistribution crossoverPointsDistribution){
		double k = 0;
		switch(crossoverPointsDistribution){
		case TRADITIONAL:
			k = Math.pow(base, Math.floor(N * Math.random()));
			break;
		case GENERALIZED:
			k = Math.floor(Math.pow(base, N * Math.random()));
			break;
		case CONTINUOUS:
			if(r1 + r2 - rmin >= rmax){
				return k(r1, r2, CrossoverPointsDistribution.TRADITIONAL);
			}
			k = Math.pow(this.base, this.N * Math.random());
			break;
		}
		k *= deltar;
		k += rmin;

		return k;
	}
	
	public static double modulus(double x, double y){
		int intPart = (int) (x / y);
		return x - (intPart * y);
	}
}
