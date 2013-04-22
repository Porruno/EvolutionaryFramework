
import nombreIdeal.ChoosingNameInput;
import nombreIdeal.Evaluator;
import nombreIdeal.tests.ChoosingName_GP_Coevolutive;
import test.GroupOfTests;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lay
 */
public class GroupOfTestsChoosingName {
    
    public static void main(String[] args){
        GroupOfTests groupOfTests = new GroupOfTests("Choosing name", new Class[]{ChoosingName_GP_Coevolutive.class}, 2);
        groupOfTests.graph();
        groupOfTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().graph("ChoosingName", new ChoosingNameInput(Evaluator.cases[1]));
    }
    
}
