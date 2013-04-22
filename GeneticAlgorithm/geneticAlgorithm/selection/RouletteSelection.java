package geneticAlgorithm.selection;

import geneticAlgorithm.Genome;

public class RouletteSelection extends Selection{

	public RouletteSelection(int numberOfParentsToSelect) {
		super(numberOfParentsToSelect);
	}

	public Genome[] selectParents(Genome[] genomes){
		boolean maximization = genomes[0].getMaximization();
		Genome[] selectedParents = new Genome[this.numberOfParentsToSelect];
		double totalAptitude = this.getTotalAptitude(genomes);
		for(int i = 0; i < this.numberOfParentsToSelect; i++){
			Genome parent = genomes[genomes.length - 1];
			double acum = 0;
			double random = Math.random();
			for(Genome genome : genomes){
				acum += maximization ? genome.getAptitude() : 1 / genome.getAptitude();
				if(acum / totalAptitude  >= random){
					parent = genome;
					break;
				}
			}
			selectedParents[i] = parent;
		}

		return selectedParents;
	}

	private double getTotalAptitude(Genome[] genomes){
		boolean maximization = genomes[0].getMaximization();
		double totalAptitude = 0;

		if(maximization){
			for(Genome genome : genomes){
				totalAptitude += genome.getAptitude();
			}
		}else{
			for(Genome genome : genomes){
				totalAptitude += 1 / genome.getAptitude();
			}
		}

		return totalAptitude;
	}

}
