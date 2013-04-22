package geneticAlgorithm.selection;

import geneticAlgorithm.Genome;

public abstract class Selection {
	protected int numberOfParentsToSelect;
	
	protected Integer pendingNotificationsFromThreads = 0;
	protected Object lockForCrossingThreads = new Object();
	
	public Selection(int genomesGeneratedPerGeneration){
		this.numberOfParentsToSelect = genomesGeneratedPerGeneration;
	}
	
	public int getGenomesGeneratedPerGeneration(){
		return this.numberOfParentsToSelect;
	}
	public abstract Genome[] selectParents(Genome[] genomes);
	
}
