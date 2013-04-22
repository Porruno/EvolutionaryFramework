/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgorithm.phenotype;

import geneticAlgorithm.Genome;
import grammar.Function;
import grammar.Input;
import grammar.adf.ADF;
import java.util.ArrayList;

/**
 *
 * @author Lay
 */
public abstract class ProgramNode {

    protected Genome genome;
    protected Function function;
    protected int level;
    protected ArrayList<ProgramNode> children;
    protected int maxLevel;
    private ProgramNode parentNode;
    protected Object result;
    private int numberOfSubgenes;
    protected int levelOfDeepestNode;
    protected int idInTree;
    protected int numberOfChilds;

    public ProgramNode() {
        this.children = new ArrayList<ProgramNode>();
    }

    protected ProgramNode(Genome genome, Function function, int maxLevel) {
        this.numberOfChilds = function.getArity() - function.getNumberOfParametersThatAreInputType();
        this.genome = genome;
        this.parentNode = null;
        this.children = new ArrayList<ProgramNode>();
        this.function = function;
        this.maxLevel = maxLevel;
    }

    public int findIdInTree(int startingFrom) {
        this.idInTree = startingFrom;
        startingFrom++;
        for (ProgramNode child : children) {
            startingFrom = child.findIdInTree(startingFrom);
        }
        return startingFrom;
    }

    public int getIndexInParentNode() {
        if (this.isRoot()) {
            return -1;
        } else {
            return this.parentNode.children.indexOf(this);
        }
    }

    public void crossWithOtherProgramNode(ProgramNode otherProgramNode) {
        Genome thisOriginalGenome = this.genome;
        Genome otherProgramNodeOriginalGenome = otherProgramNode.genome;
        boolean thisWasRoot = this.isRoot();
        boolean otherProgramNodeWasRoot = otherProgramNode.isRoot();
        ProgramNode thisOriginalParentNode = null;
        ProgramNode otherProgramNodeOriginalParentNode = null;

        int thisOriginalIndexInParentGenome = -1;
        int otherProgramNodeOriginalIndexInParentGenome = -1;

        if (!thisWasRoot) {
            thisOriginalParentNode = this.parentNode;
            thisOriginalIndexInParentGenome = this.removeFromParentNode();
        }
        if (!otherProgramNodeWasRoot) {
            otherProgramNodeOriginalParentNode = otherProgramNode.parentNode;
            otherProgramNodeOriginalIndexInParentGenome = otherProgramNode.removeFromParentNode();
        }

        if (thisWasRoot) {
            thisOriginalGenome.setPhenotype(otherProgramNode);
        } else {
            thisOriginalParentNode.addChild(otherProgramNode, thisOriginalIndexInParentGenome);
        }

        if (otherProgramNodeWasRoot) {
            otherProgramNodeOriginalGenome.setPhenotype(this);
        } else {
            otherProgramNodeOriginalParentNode.addChild(this, otherProgramNodeOriginalIndexInParentGenome);
        }
        
        thisOriginalGenome.consolidatePhenotype();
        otherProgramNodeOriginalGenome.consolidatePhenotype();
    }

    public void addChild(ProgramNode child, int atIndex) {
        child.parentNode = this;
        child.level = this.level + 1;
        this.children.add(atIndex, child);
    }

    public void addChild(ProgramNode child) {
        this.addChild(child, this.children.size());
    }

    public void removeChild(ProgramNode child) {
        child.parentNode = null;
        this.children.remove(child);
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isRoot() {
        return this.parentNode == null;
    }

    public void computeNumberOfSubgenes() {
        for (ProgramNode child : children) {
            child.computeNumberOfSubgenes();
        }
        int newNumberOfSubgenes = 0;

        for (ProgramNode child : children) {
            newNumberOfSubgenes += child.numberOfSubgenes + 1;
        }
        this.numberOfSubgenes = newNumberOfSubgenes;
    }

    public void computeLevelOfDeepestNode() {
        for (ProgramNode child : children) {
            child.computeLevelOfDeepestNode();
        }
        int newLevelOfDeepestNode = this.level;
        for (ProgramNode child : children) {
            if (child.levelOfDeepestNode > newLevelOfDeepestNode) {
                newLevelOfDeepestNode = child.levelOfDeepestNode;
            }
        }
        this.levelOfDeepestNode = newLevelOfDeepestNode;
    }

    public void propagateNewGenome(Genome newGenome) {
        this.genome = newGenome;
        for (ProgramNode child : this.children) {
            child.propagateNewGenome(newGenome);
        }
    }

    public boolean isTerminal() {
        return this.children.isEmpty();
    }

    public Object evaluateProgramNode(Input input) {
        this.result = this.evaluate(input);
        return this.result;
    }

    public Object evaluateProgramNodeUsingOnlyBestADFs(Input input) {
        this.result = this.evaluateUsingOnlyBestADFs(input);
        return this.result;
    }

    public void getCurrentEmptySockets(ArrayList<ProgramNode> emptySockets) {
        int numberOfEmptySockets = this.numberOfChilds - children.size();
        for (int i = 0; i < numberOfEmptySockets; i++) {
            emptySockets.add(this);
        }
        for (ProgramNode child : children) {
            child.getCurrentEmptySockets(emptySockets);
        }
    }

    public Class<?> getNextEmptySocketClass() {
        Class<?>[]parameters = this.function.getParameterTypes();
        int parametersThatAreInput = 0;
        for(int i = 0, j = 0; i < children.size(); i++, j++){
            if(Input.class.isAssignableFrom(parameters[j])){
                i--;
                parametersThatAreInput++;
            }
        }
        return parameters[children.size() + parametersThatAreInput];
    }

    protected abstract Object evaluate(Input input);

    protected abstract Object evaluateUsingOnlyBestADFs(Input input);

    public void lookForADFs(ArrayList<ADF> adfsAcum) {
        if (ADF.class.isAssignableFrom(this.function.getClass())) {
            if (!adfsAcum.contains(
                    (ADF) this.function)) {
                adfsAcum.add((ADF) this.function);
            }
        }
        for (ProgramNode child : children) {
            child.lookForADFs(adfsAcum);
        }
    }

    @Override
    public String toString() {
        return this.function.getName();
    }
    
    public Class<?>[] getParameterTypesOfFunction(){
        return this.function.getParameterTypes();
    }

    public String toDescription() {
        String description = "";
        description += function.getName();
        description += "\n";

        String parameterString;
        int childIterator = 0;
        int parameterIterator = 0;


        for (Class parameter : function.getParameterTypes()) {
            if (Input.class.isAssignableFrom(parameter)) {
                parameterString = parameter.getSimpleName();
            } else {
                parameterString = children.get(childIterator).toDescription();
                childIterator++;
            }
            parameterIterator++;

            String[] lines = parameterString.split("\n");
            for (int i = 0; i < lines.length; i++) {
                lines[i] = "\t" + lines[i];
            }
            if (parameterIterator != function.getParameterTypes().length) {
                parameterString = "";
                for (int i = 0; i < lines.length; i++) {
                    String head = lines[i].substring(0, 2);
                    if (head.compareTo("\t\t") == 0) {
                        lines[i] = "\t|\t" + lines[i].substring(2);
                    }
                    parameterString += lines[i] + "\n";
                }
            } else {
                parameterString = "";
                for (int i = 0; i < lines.length; i++) {
                    parameterString += lines[i] + "\n";
                }
            }
            description += parameterString;
        }
        return description;
    }

    public ProgramNode getNode(int nodeIndex) {
        if (nodeIndex == 0) {
            return this;
        }
        int acum = 1;
        for (ProgramNode gene : children) {
            if (acum + gene.numberOfSubgenes >= nodeIndex) {
                return gene.getNode(nodeIndex - acum);
            }
            acum += gene.numberOfSubgenes + 1;
        }
        return null;
    }

    public ProgramNode getParentNode() {
        return this.parentNode;
    }

    public int removeFromParentNode() {
        int indexInParentNode = this.getIndexInParentNode();
        this.parentNode.removeChild(this);
        return indexInParentNode;
    }

    public Genome getGenome() {
        return this.genome;
    }

    public int getLevelOfDeepestNode() {
        return this.levelOfDeepestNode;
    }

    public ArrayList<ProgramNode> getGenesOfTypeClass(Class typeClass) {
        ArrayList<ProgramNode> selectedProgramNodes = new ArrayList<ProgramNode>();
        if (typeClass.isAssignableFrom(this.getReturnType())) {
            selectedProgramNodes.add(this);
        }
        for (ProgramNode child : children) {
            selectedProgramNodes.addAll(child.getGenesOfTypeClass(typeClass));
        }
        return selectedProgramNodes;
    }

    public int getMargin() {
        return this.maxLevel - (this.getLevelOfDeepestNode() + this.level);
    }

    public Class<?> getReturnType() {
        return this.function.getReturnType();
    }

    public void addCandidateGenesForCrossing(ArrayList<ProgramNode> candidates, Class typeClass, int otherGeneLevel, int otherGeneLevelOfDeepestGene) {
        if (typeClass.isAssignableFrom(this.getReturnType())
                && otherGeneLevel + (this.getLevelOfDeepestNode() - this.level) <= this.maxLevel && //Asegurarse que este gene cabe en el otro genoma
                (otherGeneLevelOfDeepestGene - otherGeneLevel) + this.level <= this.maxLevel //Asegurarse que el otro gene cabria en este genoma
                ) {
            candidates.add(this);
        }
        for (ProgramNode child : children) {
            child.addCandidateGenesForCrossing(candidates, typeClass, otherGeneLevel, otherGeneLevelOfDeepestGene);
        }
    }

    @Override
    public ProgramNode clone() {
        ProgramNode newGene;
        newGene = ProgramNodeFactory.createProgramNodeFromClass(this.getClass());

        newGene.function = this.function;
        newGene.result = this.result;
        newGene.level = this.level;
        newGene.maxLevel = this.maxLevel;
        newGene.genome = this.genome;

        for (ProgramNode child : this.children) {
            ProgramNode clonedSubgene = child.clone();
            newGene.addChild(clonedSubgene);
        }
        return newGene;
    }

    public int getNumberOfSubgenes() {
        return this.numberOfSubgenes;
    }

    public int getIdInTree() {
        return this.idInTree;
    }

    public String graphAsRoot(boolean withResults, int treeId) {
        this.findIdInTree(0);
        String graphString = "subgraph cluster" + treeId + "{\n";
        graphString += "\n" + "n" + treeId + "" + this.getIdInTree() + " ;\n";
        graphString += this.graph(withResults, treeId) + "\n}";
        return graphString;
    }

    private String graph(boolean withResults, int treeId) {
        String nodeGraph = "n" + treeId + "" + this.idInTree + " [label=\"" + this.function.getName() + (withResults ? " = " + this.result : "") + "\"] ;";
        for (ProgramNode child : children) {
            nodeGraph += "\n" + child.graph(withResults, treeId) + "\n";
            nodeGraph += "n" + treeId + "" + this.idInTree + " -- n" + treeId + "" + child.idInTree + " ;";
        }
        return nodeGraph;
    }
}