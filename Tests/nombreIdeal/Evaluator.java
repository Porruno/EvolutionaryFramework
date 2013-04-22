/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal;

import geneticAlgorithm.EvaluationInterface;
import geneticAlgorithm.Genome;

/**
 *
 * @author Lay
 */
public class Evaluator implements EvaluationInterface{
    
    public static void main(String args[]){
        for(CompoundName compoundName : cases){
            System.out.println(compoundName);
            System.out.println(isGoodCompoundName(compoundName)? "Good" : "Bad");
        }
    }
    
    public static CompoundName[] cases = { new CompoundName("Jesus", "Juan"),
                                    new CompoundName("Jesus", "Anacleto"),
                                    new CompoundName("Jesus", "Clayton"),
                                    new CompoundName("Maria", "Carolina"),
                                    new CompoundName("Maria", "Caroline"),
                                    new CompoundName("Mary", "Rose"),
                                    new CompoundName("Mary", "Anacleto")};

    @Override
    public double evaluateGenome(Genome genome) {
        int errorsAcum = 0;
        for(CompoundName compoundName : cases){
            if(isGoodCompoundName(compoundName) != genome.evaluate(new ChoosingNameInput(compoundName))){
                errorsAcum ++;
            }
        }
        return errorsAcum;
    }
    
    
    public static boolean isGoodCompoundName(CompoundName compoundName){
        if(compoundName.hasMixedGenre() || compoundName.hasMixedNationality()) return false;
        
        int length = compoundName.length();
        boolean isMasculine = compoundName.isMasculine();
        if(length < 10 && isMasculine ||
                length > 10 && !isMasculine) return false;
        
        return true;
    }
    
}
