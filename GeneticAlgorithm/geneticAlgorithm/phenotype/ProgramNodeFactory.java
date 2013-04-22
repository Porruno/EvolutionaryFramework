/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticAlgorithm.phenotype;

import geneExpressionProgramming.LinkerFunction;
import geneticAlgorithm.Genome;
import grammar.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lay
 */
public class ProgramNodeFactory {
    
    public enum FunctionRestriction{
        For_Apply_First_Parameter,
        No_Restrictions
    }

    public static ProgramNode createProgramNodeFromClass(Class<?> programNodeClass) {
        try {
            return (ProgramNode) programNodeClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ProgramNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProgramNodeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ProgramNode createProgramNode(Genome genome, Function function, int maxLevel) {
        if (function.getClass() == SimpleFunction.class || LinkerFunction.class.isAssignableFrom(function.getClass())) {
            return new SimpleProgramNode(genome, function, maxLevel);
        } else if (SpecialFunction.class.isAssignableFrom(function.getClass())) {
            return new SpecialFunctionProgramNode(genome, function, maxLevel);
        } else {
            return new ADFProgramNode(genome, function, maxLevel);
        }
    }
    
    
    public static void addRestOfChildrenToSpecialNode(ProgramNode programNode, Function function, Grammar grammar, Genome genome, int level, int maxLevel){
        for (int i = 1; i < function.getParameterTypes().length; i++){
                Class<?> aClass = function.getParameterTypes()[i];
                if(Input.class.isAssignableFrom(aClass)) continue;
                Type parameterType = grammar.getTypeForClass(aClass);
                ProgramNode aChild = ProgramNodeFactory.createProgramNode(genome, grammar, parameterType, level + 1, maxLevel, FunctionRestriction.No_Restrictions);
                programNode.addChild(aChild);
            }
    }
    
    public static ProgramNode createProgramNode(Genome genome, Grammar grammar, Type type, int level, int maxLevel, FunctionRestriction functionRestriction) {
        int maxDepth = maxLevel - level;
        Function function;
        switch(functionRestriction){
            case For_Apply_First_Parameter:
                function = type.getFunctionForApplyFirstParameter(maxDepth);
            break;
            case No_Restrictions:
                function = type.getFunctionWithMaxDepthAndMinDepth(maxDepth, 0);
                break;
            default:
                function = null;
                break;
        }
        
        ProgramNode programNode = ProgramNodeFactory.createProgramNode(genome, function, maxLevel);
        programNode.level = level;
        if (function.isTerminal()) {
            return programNode;
        }
        
        if(ApplyFunction.class.isAssignableFrom(function.getClass())){
            Class<?> firstParameterClass = function.getParameterTypes()[0];
            Type firstParameterType = grammar.getTypeForClass(firstParameterClass);
            ProgramNode firstChild = ProgramNodeFactory.createProgramNode(genome, grammar, firstParameterType, level + 1, maxLevel, FunctionRestriction.For_Apply_First_Parameter);
            programNode.addChild(firstChild);
            
            addRestOfChildrenToSpecialNode(programNode, function, grammar, genome, level, maxLevel);
        }else {
            for (Class<?> aClass : function.getParameterTypes()) {
                if(Input.class.isAssignableFrom(aClass)) continue;
                Type parameterType = grammar.getTypeForClass(aClass);
                ProgramNode aChild = ProgramNodeFactory.createProgramNode(genome, grammar, parameterType, level + 1, maxLevel, FunctionRestriction.No_Restrictions);
                programNode.addChild(aChild);
            }
        }
        return programNode;
    }
}