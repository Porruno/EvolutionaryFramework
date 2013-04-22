package geneticAlgorithm.selection;

import geneticAlgorithm.Genome;

import java.util.ArrayList;
import java.util.Arrays;


public class TournamentSelection extends Selection{
	protected int windowSize;

	public TournamentSelection(int replacementSize, int windowSize) {
		super(replacementSize);
		
		this.windowSize = windowSize;
	}

	
	public Genome[] selectParents(Genome[] genomes){
		boolean maximization = genomes[0].getMaximization();
		Genome[] selectedParents = new Genome[this.numberOfParentsToSelect];
		Genome[] unsortedGenomes = this.unsortGenomes(genomes);
		Genome best;
		for(int i = 0; i < this.numberOfParentsToSelect; i++){
			best = unsortedGenomes[i];
			for(int j = i + 1; j < i + this.windowSize; j++){
				int nextGenomeIndex = j % genomes.length;
				if(unsortedGenomes[nextGenomeIndex].getAptitude() > best.getAptitude() && maximization ||
						unsortedGenomes[nextGenomeIndex].getAptitude() < best.getAptitude() && !maximization){
					best = unsortedGenomes[nextGenomeIndex];
				}
			}
			selectedParents[i] = best;
		}
		
		return this.unsortGenomes(selectedParents);
	}
	
	/**
	 * Unsorts a given array of Genomes. The original array remains the same, while a new array is returned.
	 * @param genomesToUnsort Array of genomes to unsort.
	 * @return A new array of unsorted Genomes.
	 */
	private Genome[] unsortGenomes(Genome[] genomesToUnsort){
		ArrayList<Genome> orderedGenomes = new ArrayList<Genome>(Arrays.asList(genomesToUnsort));
		Genome[] unsortedGenomesArray = new Genome[genomesToUnsort.length];
		for(int i = 0; i < genomesToUnsort.length; i++){
			unsortedGenomesArray[i] = orderedGenomes.remove((int)(Math.random() * orderedGenomes.size()));
		}

		return unsortedGenomesArray;
	}
}
