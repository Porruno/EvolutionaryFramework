package iteratedLocalSearch;
import CSP.CSP.CSP;
import CSP.CSP.ConstraintOrderingHeuristics;
import CSP.CSP.GACSPSolver;
import CSP.CSP.ValueOrderingHeuristics;
import CSP.CSP.Variable;
import static CSP.tests.test1.CSPTestILSSimple.cspForEvaluation;
import geneticAlgorithm.EvaluationInterface;
import geneticAlgorithm.Genome;
import geneticAlgorithm.Population;
import geneticAlgorithm.PopulationDelegate;
import geneticAlgorithm.phenotype.ProgramNode;
import geneticAlgorithm.phenotype.ProgramNodeFactory;
import geneticAlgorithm.phenotype.ProgramNodeFactory.FunctionRestriction;
import grammar.Type;
import java.util.ArrayList;
import java.util.Vector;


public class GPPopulation extends Population {

    protected int maxLevel;
    protected EvaluationInterface evaluator;
    
    public GPPopulation(PopulationDelegate gpPopulationDelegate) {
        super(gpPopulationDelegate);
        GPPopulationDelegate castedGPPopulationDelegate = (GPPopulationDelegate) gpPopulationDelegate;
        this.maxLevel = castedGPPopulationDelegate.getMaxLevel();
    }

    @Override
    protected Genome generateAGenome(int indexInPopulation) {
        return new GPGenome(this, indexInPopulation, grammar, this.rootType, this.maxLevel);
    }

    @Override
    public Genome[] replicateGenomes(Genome[] genomesToReplicate) {
        Genome[] copies = new Genome[genomesToReplicate.length];
        for (int i = 0; i < genomesToReplicate.length; i++) {
            copies[i] = genomesToReplicate[i].clone();
        }
        return copies;
    }

    /**
     * Crosses 2 given parents. Is algorithm dependant.
     *
     * @param copy1
     * @param copy2
     * @return An array with the generated descendants.
     */
    @Override
    public void recombinateGenomes(Genome copy1, Genome copy2) {
        this.crossGenomes((GPGenome) copy1, (GPGenome) copy2);
    }

    public void crossGenomes(GPGenome descendant1, GPGenome descendant2) {
        int geneToCrossIndex = (int) (Math.random() * descendant1.numberOfGenes());

        ProgramNode geneToCrossDescendant1 = descendant1.getGene(geneToCrossIndex);

        Class<?> typeClass = geneToCrossDescendant1.getReturnType();
        ArrayList<ProgramNode> descendant2CandidateGenes = descendant2.getCandidateGenesForCrossing(typeClass, geneToCrossDescendant1.getLevel(), geneToCrossDescendant1.getLevelOfDeepestNode());

        if (descendant2CandidateGenes.isEmpty()) {
            return;
        }
        int geneToCrossIndexParent2 = (int) (Math.random() * descendant2CandidateGenes.size());
        ProgramNode geneToCrossDescendant2 = descendant2CandidateGenes.get(geneToCrossIndexParent2);

        geneToCrossDescendant1.crossWithOtherProgramNode(geneToCrossDescendant2);
    }

    @Override
    public void mutateGenome(Genome genome) {
        double evalClon, evalGenome;
        GPGenome[] nuevo = new GPGenome[1];
        GPGenome clon = (GPGenome)genome.clone();
        GPGenome newGenome = (GPGenome) genome.clone();
        evalClon = evaluateGenome(clon);
        newGenome = permutation(newGenome);
        for(int i = 0; i<GroupOfTestsCSPILS.depth;i++){
            newGenome = localSearch(newGenome);
            evalGenome = evaluateGenome(newGenome);
            if(evalGenome < evalClon){
                genome.setPhenotype(newGenome.getPhenotype());
                genome.consolidatePhenotype();
            }
        }
        
        //clon.evaluate(evaluator);
        
        
        //clon.evaluate(this.evaluator);
        //genome.evaluate(this.evaluator);
        //System.out.println(evalClon);
        //System.out.println(evalGenome);
        //System.out.println(clon.getIndexInPopulation());
        //System.out.println(newGenome.getIndexInPopulation());
        
    }
    
    
        public GPGenome permutation(GPGenome genome){
            
            GPGenome clon = (GPGenome) genome.clone();
            int geneToMutateIndex = (int) (clon.numberOfGenes() * Math.random());
            ProgramNode geneToMutate = clon.getGene(geneToMutateIndex);
            Type geneToMutateType = grammar.getTypeForClass(geneToMutate.getReturnType());
            ProgramNode newGene = ProgramNodeFactory.createProgramNode(geneToMutate.getGenome(), grammar, geneToMutateType, geneToMutate.getLevel(), this.maxLevel, FunctionRestriction.No_Restrictions);

            if (geneToMutate.isRoot()) {
                clon.setPhenotype(newGene);
            } else {
                ProgramNode parentNode = geneToMutate.getParentNode();
                int indexInParentNode = geneToMutate.removeFromParentNode();
                parentNode.addChild(newGene, indexInParentNode);
            }
            clon.consolidatePhenotype();
            return clon;
        }
        
        public GPGenome localSearch(GPGenome genome){
            GPGenome clon = (GPGenome) genome.clone();
            int terminals[] = terminalIndex(clon);
            int geneToMutateIndex = terminals[(int)(terminals.length * Math.random())];
            
            ProgramNode geneToMutate = clon.getGene(geneToMutateIndex);
            Type geneToMutateType = grammar.getTypeForClass(geneToMutate.getReturnType());
            ProgramNode newGene = ProgramNodeFactory.createProgramNode(geneToMutate.getGenome(), grammar, geneToMutateType, geneToMutate.getLevel(), this.maxLevel, FunctionRestriction.No_Restrictions);

            if (geneToMutate.isRoot()) {
                clon.setPhenotype(newGene);
            } else {
                ProgramNode parentNode = geneToMutate.getParentNode();
                int indexInParentNode = geneToMutate.removeFromParentNode();
                parentNode.addChild(newGene, indexInParentNode);
            }
            clon.consolidatePhenotype();
            return clon;
        }
        
        public int[] terminalIndex(GPGenome genome){
            Vector list = new Vector();
            int index[];
            for(int i=0;i<genome.numberOfGenes();i++){
                if(genome.getGene(i).isTerminal()){
                    list.add(i);
                }
            }
            index = new int[list.size()];
            for(int i=0;i<index.length;i++){
                index[i]=(int)list.get(i);
            }
            return index;
        }
        
        
    public double evaluateGenome(Genome genome) {
        CSP csp = new CSP(cspForEvaluation);
        GACSPSolver solver = new GACSPSolver(csp, genome);
        Variable[] variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        /*
         *
         * double violationsAcum = 0; for(Variable variable : variables){
         * variable.setNumberOfConflicts(); violationsAcum +=
         * variable.getNumberOfConflicts(); }
         */

        long checks = solver.getConstraintChecks();
        return checks;

    }
        
        
}