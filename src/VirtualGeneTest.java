import virtualGene.VirtualGene;
import virtualGene.VirtualGene.CrossoverPointsDistribution;


public class VirtualGeneTest {

	public static void main(String[] args){
		
		try {
			VirtualGene vg = new VirtualGene(0, 20, 10, 4, .5, CrossoverPointsDistribution.CONTINUOUS);
			double r = 11;
			
			for(int i = 0; i < 1000; i++){
				double newR = vg.mutation(r);
				System.out.println(newR);
			}
			
			for(int i = 0; i < 100; i++){
				double[] crossedGenes = vg.crossover(15, 0);
				System.out.println(crossedGenes[0] + ", " + crossedGenes[1]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
