package geneExpressionProgramming;

import geneticAlgorithm.phenotype.ProgramNode;
import geneticAlgorithm.phenotype.ProgramNodeFactory;
import grammar.Function;
import grammar.Grammar;
import grammar.Type;
import java.util.ArrayList;

public class ORFTree {

    public static ProgramNode createORFTree(GEPGenome genome, Grammar grammar, Type type, ArrayList<Integer> genes, int tailLength) {
        boolean onlyTerminals = genes.size() <= tailLength;
        Function rootFunction = type.getFunctionAtIndexWithModulus(genes.remove(0), onlyTerminals);
        ProgramNode rootNode = ProgramNodeFactory.createProgramNode(genome, rootFunction, 0);
        ArrayList<ProgramNode> emptySockets = new ArrayList<ProgramNode>();
        rootNode.getCurrentEmptySockets(emptySockets);

        while (!emptySockets.isEmpty()) {
            while (!emptySockets.isEmpty()) {
                ProgramNode emptySocket = emptySockets.remove(0);
                Type typeOfNewNode = grammar.getTypeForClass(emptySocket.getNextEmptySocketClass());
                onlyTerminals = genes.size() <= tailLength;
                Function socketFunction = typeOfNewNode.getFunctionAtIndexWithModulus(genes.remove(0), onlyTerminals);
                ProgramNode newProgramNode = ProgramNodeFactory.createProgramNode(genome, socketFunction, 0);
                emptySocket.addChild(newProgramNode);
            }
            rootNode.getCurrentEmptySockets(emptySockets);
        }
        return rootNode;
    }
}
