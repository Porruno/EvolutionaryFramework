
package CSP.LCS;

import CSP.Framework.Framework;
import CSP.HyperHeuristic.HyperHeuristic;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle classifiers to be used
 * by the classifier system.
 * ----------------------------------------------------------------------------
*/

public class ClassifierSystem extends HyperHeuristic {

    private boolean explorationMode = true;
    private int depth;
    private ArrayList<Classifier> classifiers, actionSet;
    private ArrayList<Integer> indexes;
    
    /**
     * Creates a new clasisifier system.
     * @param n The number of classifiers in the system.
    */
    public ClassifierSystem(int n) {
        int i;
        classifiers = new ArrayList<Classifier>(n);
        indexes = new ArrayList<Integer>(n);
        actionSet = new ArrayList<Classifier>(n);
        depth = 0;
        for (i = 0; i < n; i++) {
            classifiers.add(new Classifier(Framework.getFeatures().length));
        }
        this.type = HyperHeuristic.LCS;
        explorationMode = true;
    }
    
    /**
     * Creates a new classifier system.
     * @param classifiers The classifiers that will be contained in the system.
    */
    public ClassifierSystem(ArrayList<Classifier> classifiers) {
        indexes = new ArrayList<Integer>(classifiers.size());
        actionSet = new ArrayList<Classifier>(classifiers.size());
        depth = 0;
        this.classifiers = classifiers;
        this.type = HyperHeuristic.LCS;
        setExplorationMode(false);
    }
    
    /**
     * Sets the training mode of the classifier system.
     * @param explorationMode A boolean value with the training mode.
    */
    public final void setExplorationMode(boolean trainingMode) {
        this.explorationMode = trainingMode;
    }
    
    /**
     * Runs the classifier system for one cycle.
    */
    public int run(double state[]) {        
        Classifier classifier;
        Classifier selected[], offspring[];
        ArrayList<Classifier> matchSet;
        Random generator = new Random();
        matchSet = createMatchSet(state);
        classifier = this.selectToApply(matchSet);     
        createActionSet(classifier.getAction(), actionSet, matchSet);
        if (explorationMode && generator.nextDouble() < 0.001) {
            selected = select();
            offspring = crossover(selected[0], selected[1]);
            classifiers.add(offspring[0]);
            classifiers.add(offspring[1]);
        }
        return classifier.getAction();
    }        
    
    /**
     * Updates the fitness of the classifiers in the action set that are 
     * associated to decision made at the given depth.
     * @param deltaFitness The change in the fitness.
     * @param depth The depth of the decision.
    */
    public void updateFitness(double deltaFitness) {
        int i, index = indexes.get(actionSet.size() - 1);
        Classifier classifier;
        depth--;
        //System.out.println("Must delete depth: " + depth);
        if (actionSet.isEmpty()) {
            System.out.println("Attempting to remove unexistent index!");
            return;
        }
        for (i = actionSet.size() - 1; indexes.get(i) == index; i--) {            
            classifier = actionSet.get(i);
            classifier.setFitness(classifier.getFitness() + deltaFitness);            
            indexes.remove(i);
            actionSet.remove(i);
            if (actionSet.isEmpty()) {
                //System.out.println("The list is now empty...");
                break;
            }
        }       
    }        
    
    /**
     * Saves the classifiers within the system to a text file.
     * @param fileName The name of the file where the classifiers will be saved.     
    */
    public void saveToFile(String fileName) {
        int i;
        String text = "";
        for (i = 0; i < classifiers.size(); i++) {
            text += classifiers.get(i) + "\r\n";
        }
        CSP.Utils.Files.saveToFile(text, fileName, false);
    }
    
    /**
     * Loads a Classifier System from a text file.
     * @param fileName The name of the file where the data will be loaded from.
     * @return The classifier system.
    */
    public static ClassifierSystem loadFromFile(String fileName) {
        int i, j, size, action;
        double fitness, radius;
        double condition[];
        String lines[] = CSP.Utils.Misc.toStringArray(CSP.Utils.Files.loadFromFile(fileName));
        StringTokenizer tokens;
        ArrayList<Classifier> classifiers = new ArrayList<Classifier>(lines.length);        
        for (i = 0; i < lines.length; i++) {
            tokens = new StringTokenizer(lines[i], ", ");
            size = tokens.countTokens() - 3;
            condition = new double[size];
            for (j = 0; j < size; j++) {
                condition[j] = Double.parseDouble(tokens.nextToken());
            }
            action = Integer.parseInt(tokens.nextToken());
            radius = Double.parseDouble(tokens.nextToken());
            fitness = Double.parseDouble(tokens.nextToken());
            classifiers.add(new Classifier(condition, action, radius, fitness));
        }
        return (new ClassifierSystem(classifiers));
    }
    
    /**
     * Removes from the population all the classifiers with fitness less or equal
     * than zero.     
    */
    public void remove() {
        int i, k = 0, size;
        size = classifiers.size();
        for (i = 0; i < size; i++) {
            if (classifiers.get(k).getFitness() <= 0) {
                classifiers.remove(k);
            } else {
                k++;
            }
        }
    }
    
    public void print() {
        int i;
        for (i = 0; i < classifiers.size(); i++) {
            System.out.println(classifiers.get(i));         
        }
    }        
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Returns all the classifiers within the system that match the environment 
     * state.
     * @param state The environment state.
     * @return All the classifiers within the system that match the environment
     * state.
    */
    private ArrayList<Classifier> createMatchSet(double state[]) {        
        int i;        
        Classifier classifier;
        Random generator;
        ArrayList<Classifier> matchSet = new ArrayList<Classifier>(10);
        for (i = 0; i < classifiers.size(); i++) {
            classifier = classifiers.get(i);
            if (matches(classifier, state)) {
                matchSet.add(classifiers.get(i));
            }
        }
        if (!explorationMode && matchSet.isEmpty()) {
            matchSet.add(selectClosestClassifier(state));
            return matchSet;
        }
        if (matchSet.isEmpty()) {
            generator = new Random();
            for (i = 0; i < Framework.getHeuristics().length; i++) {
                classifier = new Classifier(state, Framework.getHeuristics()[i], 0.0, 0.0);
                matchSet.add(classifier);
                classifiers.add(classifier);
            }
        }
        return matchSet;
    }
    
    /**
     * Returns a new action set that includes the classifiers in the match set 
     * that specify the same action than the selected classifier.
     * @param action The action of the selected classifier.
     * @param matchSet The action set.
     * @param matchSet The match set.
     * @return All the classifiers in the match set that specify the same action
     * than the selected classifier.
    */
    private ArrayList<Classifier> createActionSet(int action, ArrayList<Classifier> actionSet, ArrayList<Classifier> matchSet) {
        int i;
        Classifier classifier;        
        for (i = 0; i < matchSet.size(); i++) {
            classifier = matchSet.get(i);
            if (classifier.getAction() == action) {
                indexes.add(depth);
                actionSet.add(matchSet.get(i));                                        
            }
        }
        depth++;        
        return actionSet;
    }
    
    /**
     * Evaluates if actionSet given classifier matches the state provided.
     * @param classifier The classifier to be evaluated.
     * @param state The environment state.
     * @return true if the classifier matches the state, false otherwise.
    */
    private boolean matches(Classifier classifier, double state[]) {        
        double distance;
        if (classifier.getCondition().length != state.length) {
            System.out.println("The lenghts of the classifier condition and the environment state must be equal.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        distance = distance(classifier.getCondition(), state);        
        if (distance <= 0.05) {
            return true;
        }        
        return false;
    }
    
    /**
     * Selects one classifier in M to be applied to the environment.
     * @param classifiers The list of classifiers that matched the state condition.
     * @return The classifier to be applied.
    */
    private Classifier selectToApply(ArrayList<Classifier> classifiers) {
        int i, index = -1;
        Random generator = new Random();
        if (explorationMode) {
            return classifiers.get(generator.nextInt(classifiers.size()));            
        }
        double maxPerformance = Double.NEGATIVE_INFINITY;        
        for (i = 0; i < classifiers.size(); i++) {            
            if (classifiers.get(i).getFitness() > maxPerformance) {
                maxPerformance = classifiers.get(i).getFitness();
                index = i;
            }
        }        
        if (index == -1) {
            System.out.println("Debería crear un nuevo classificador!");
            return null;
        }
        return classifiers.get(index);
    }    
    
    /**
     * Selects one classifier in M to be applied to the environment.
     * @param classifiers The list of classifiers that matched the state condition.
     * @return The classifier to be applied.
    */
    private Classifier selectClosestClassifier(double state[]) {
        int i, index = -1; 
        double distance, minDistance = Double.POSITIVE_INFINITY;        
        for (i = 0; i < classifiers.size(); i++) {
            distance = distance(classifiers.get(i).getCondition(), state);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
            }
        }        
        if (index == -1) {
            System.out.println("Something bad really happened.");
            System.out.println("The system will halt.");
            System.exit(1);            
        }
        return classifiers.get(index);
    } 
    
    /** Calculates the euclidean distance between the condition of actionSet classifier 
     * and actionSet state provided by the environment.
     * @param condition The condiution of the classifier.
     * @param state The state representation of the environment.
     * @return The euclidean distance between the condition and the state.
    */
    private static double distance(double condition[], double state[]) {
        int i;
        double sum = 0;
        for (i = 0; i < Framework.getFeatures().length; i++) {
            sum = sum + Math.pow(condition[i] - state[i], 2);
        }        
        return Math.sqrt(sum);
    }
    
    /**
     * Selects two classifiers from the population.
     * @return An array containing the selected classifiers.
    */
    public Classifier[] select() {
        int i, tournamentSize = 5;
        double fitness;
        Random generator = new Random();
        Classifier classifier;
        Classifier selected[] = new Classifier[2];
        fitness = Double.NEGATIVE_INFINITY;
        for (i = 0; i < tournamentSize; i++) {
            classifier = classifiers.get(generator.nextInt(classifiers.size()));
            if (classifier.getFitness() > fitness) {
                fitness = classifier.getFitness();
                selected[0] = classifier;
            }
        }
        fitness = Double.NEGATIVE_INFINITY;
        for (i = 0; i < tournamentSize; i++) {
            classifier = classifiers.get(generator.nextInt(classifiers.size()));
            if (classifier.getFitness() > fitness) {
                fitness = classifier.getFitness();
                selected[1] = classifier;
            }
        }
        return selected;
    }
    
    /**
     * Combines the information from two existing classifiers to create a new one.
     * @param classifierA The first classifer to be used.
     * @param classifierA The second classifer to be used.
     * @return A new classifier.
    */
    public Classifier[] crossover(Classifier classifierA, Classifier classifierB) {        
        int featureIndex, crossPoint, factor;
        double newValueA, newValueB;
        double conditionA[], conditionB[];
        Classifier classifiers[] = new Classifier[2];
        Random generator = new Random();
        conditionA = classifierA.getCondition();
        conditionB = classifierB.getCondition();
        featureIndex = generator.nextInt(conditionA.length);        
        // If 10 bits are used to represent the features...
        crossPoint = generator.nextInt(8) + 1;
        factor = (int) Math.pow(2, crossPoint) ;        
        newValueA = (((int) (conditionA[featureIndex] * 1000) >> crossPoint) << crossPoint) + ((int) (conditionB[featureIndex] * 1000) % factor);
        newValueB = (((int) (conditionB[featureIndex] * 1000) >> crossPoint) << crossPoint) + ((int) (conditionA[featureIndex] * 1000) % factor);
        newValueA /= 1000;
        newValueB /= 1000;
        conditionA[featureIndex] = newValueA;
        conditionB[featureIndex] = newValueB;
        //classifiers[0] = new Classifier(conditionA, Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)], generator.nextDouble() / 2, 0.0);
        //classifiers[1] = new Classifier(conditionB, Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)], generator.nextDouble() / 2, 0.0);
        classifiers[0] = new Classifier(conditionA, classifierA.getAction(), generator.nextDouble() / 2, 0.0);
        classifiers[1] = new Classifier(conditionB, classifierB.getAction(), generator.nextDouble() / 2, 0.0);
        /*
        System.out.println("\tSelected:");
        System.out.println("\t\t" + classifierA);
        System.out.println("\t\t" + classifierB);
        System.out.println("\tCrossed into:");
        System.out.println("\t\t" + classifiers[0]);
        System.out.println("\t\t" + classifiers[1]);        
        */
        return classifiers;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
