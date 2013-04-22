package CSP.CSP;

import CSP.Utils.Combinations;
import CSP.Utils.Misc;
import CSP.Utils.StatisticalTools;
import grammar.Input;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author José Carlos Ortiz Bayliss
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class represents a Constraint Satisfaction Problem. Contains a series of
 * variables and a collection of constraints that prohibits some assignations 
 * among some variables.
 * ----------------------------------------------------------------------------
*/

public class CSP extends Input{
    
    public static final int DOMAIN_AVERAGE = 0, DOMAIN_STD_DEVIATION = 1, CONSTRAINT_DENSITY = 2, CONSTRAINT_TIGHTNESS = 3, KAPPA = 4, SOLUTION_DENSITY_STANDARD_DEVIATION = 5;
    public static final int MODEL_B = 0, MODEL_F = 1, MODEL_RB = 2, MODEL_J = 3, MODEL_RB3 = 4;        
    private Variable variables[];
    private ArrayList<Constraint> constraints;              
    
    /**
     * Creates a new instance of CSP from an array of Variable.
     * @param variables An array of Variable with the variables that will be contained in the instance.
    */
    public CSP(Variable variables[]) {
        this.variables = variables;
        constraints = new ArrayList<Constraint>(25);
    }
    
    /**
     * Creates a new instance of CSP from a square matrix of Variable.
     * @param variables An array of Variable with the variables that will be contained in the instance.
    */
    public CSP(Variable variables[][]) {
        int i, j, k = 0;
        this.variables = new Variable[variables.length * variables.length];
        for (i = 0; i < variables.length; i++) {
            for (j = 0; j < variables.length; j++) {
                this.variables[k++] = variables[i][j];
            } 
        }
        constraints = new ArrayList<Constraint>(25);          
    }
    
    /** 
     * Creates a new  instance of CSP.
     * @param fileName The name of the XML file that contains the instance.
    */
    public static CSP loadFromFile(String fileName) {              
        CSP csp;
        XMLLoader loader = new XMLLoader();         
        csp = loader.createCSP(fileName);
        csp.secureEmptyConstraints();
        return csp;
    }        
    
    /**
     * Saves a CSP instance to a file.
     * @param fileName The file name where the instance will be saved.
    */
    public void saveToFile(String fileName) {
        int i, j, k, arity, maxArity, relationCount;
        int domain[], tuple[];
        String line;
        Constraint constraint;
        Variable scope[];
        ArrayList tuples;
        String format = "XCSP 2.1";        
        maxArity = 2;        
        try {
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
            fw.write("\r\n<instance>");
            fw.write("\r\n<presentation maxConstraintArity=\"" + maxArity +"\" format=\"" + format + "\" type=\"CSP\"/>");            
            // Domains information
            fw.write("\r\n\r\n<domains nbDomains=\"" + variables.length + "\">");
            for (i = 0; i < variables.length; i++) {
                domain = variables[i].getDomain();
                fw.write("\r\n<domain name=\"D" + i + "\" nbValues=\"" + domain.length + "\">");                
                for (j = 0; j < domain.length - 1; j++) {
                    fw.write(domain[j] + " ");
                }
                fw.write(domain[j] + "</domain>");
            }
            fw.write("\r\n</domains>");            
            // Variables information
            fw.write("\r\n\r\n<variables nbVariables=\"" + variables.length + "\">");
            for (i = 0; i < variables.length; i++) {
                domain = variables[i].getDomain();
                fw.write("\r\n<variable name=\"" + variables[i].getId() + "\" domain=\"D" + i + "\"/>");
            }
            fw.write("\r\n</variables>");            
            // Relations information
            relationCount = 0;
            for (i = 0; i < constraints.size(); i++) {
                constraint = (Constraint) constraints.get(i);
                if (constraint.getType() == Constraint.IN_EXTENSION) {
                    relationCount++;
                }
            }
            if (relationCount > 0) {
                k = 0;
                fw.write("\r\n\r\n<relations nbRelations=\"" + relationCount + "\">");
                relationCount = 0;
                for (i = 0; i < constraints.size(); i++) {
                    constraint = constraints.get(i);
                    tuples = constraint.getTuples();
                    if (constraint.getType() == Constraint.IN_EXTENSION) {
                        arity = 0;
                        line = "";
                        for (j = 0; j < tuples.size();  j++) {
                            tuple = (int[]) tuples.get(j);                            
                            for (k = 0; k < tuple.length - 1; k++) {
                                line = line + tuple[k] + " ";
                            }
                            line = line + tuple[k] + "|";
                            if (tuple.length > arity) {
                                arity = tuple.length;
                            }                            
                        }
                        if (line.length() > 1) {
                            line = line.substring(0, line.length() - 1);
                        }
                        if (!constraint.isConflict()) {
                            fw.write("\r\n<relation name=\"R" + relationCount + "\" arity=\"" + arity + "\" nbTuples = \"" + tuples.size() + "\" semantics=\"supports\">" + line + "</relation>");
                        } else {
                            fw.write("\r\n<relation name=\"R" + relationCount + "\" arity=\"" + arity + "\" nbTuples = \"" + tuples.size() + "\" semantics=\"conflicts\">" + line + "</relation>");
                        }
                        relationCount++;
                    }
                }                                
                fw.write("\r\n</relations>");                                
            }            
            // Constraints information
            if (constraints.size() > 0) {
                relationCount = 0;
                fw.write("\r\n\r\n<constraints nbConstraints=\"" + constraints.size() + "\">");
                for (i = 0; i < constraints.size(); i++) {
                    constraint = constraints.get(i);
                    if (constraint.getType() == Constraint.IN_EXTENSION) {
                        scope = constraint.getScope();
                        fw.write("\r\n<constraint name=\"" + constraint.getId() + "\" arity=\"" + scope.length + "\" scope = \"");
                        for (j = 0; j < scope.length - 1; j++) {
                            fw.write(scope[j].getId() + " ");
                        }
                        fw.write(scope[j].getId());
                        fw.write("\" reference=\"R" + relationCount + "\"/>");
                        relationCount++;
                    }
                }
                fw.write("\r\n</constraints>");
            }
            fw.write("\r\n</instance>");
            fw.close();
        } catch (IOException e) {
            System.out.println("There was an error when writing into file: " + fileName + ".");
            System.out.println("The system will halt.");
            System.exit(1);
        }        
    }          
    
    public CSP(int n, int m) {
        int i, j, constraintCounter = 0;        
        int domain[], values[];
        ArrayList tuples;
        Random generator = new Random();
        constraints = new ArrayList<Constraint>(n);
        variables = new Variable[n];
        values = new int[n];
        domain = new int[m];
        for (i = 0; i < m; i++) {
            domain[i] = i;
        }
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);
            values[i] = generator.nextInt(m);
        }
        /*
        for (i = 0; i < n - 1; i++) {
            tuples = new ArrayList(1);                
            tuples.add(new int[]{values[i], values[i + 1]});
            this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[i + 1].getId()}, tuples, Constraint.SUPPORTS);
            constraintCounter++;
        }        
        */
        
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                tuples = new ArrayList<int[]>(1);            
                tuples.add(new int[]{values[i], values[j]});
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.SUPPORTS);
                constraintCounter++;
            }
        }        
        this.transformCSP();
    }
    
    /**
     * Creates a new random instance of CSP using model B.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p1 The constraint density of the instance.
     * @param p2 The constraint tightness of the instance.
     * @param randomModel The random model that will be used to generate the 
     * instance.
    */
    public CSP(int n, int m, double p1, double p2, int randomModel) {
        double alpha, r;
        switch(randomModel) {
            case MODEL_B:
                randomModelB(n, m, p1, p2);
                break;
            case MODEL_F:
                randomModelF(n, m, p1, p2);
                break;
            case MODEL_RB:
                r = p1 * (n - 1) / (2 * Math.log(n));
                alpha = Math.log10(m) / Math.log10(n);
                randomModelRB(n, alpha, r, p2);
                break;
            case MODEL_J:
                randomModelJ(n, m, p1, p2);
                break;
            default:
                System.out.println("Undefined random generation model.");
                System.out.println("The system will halt.");
                System.exit(1);
        }
    }
    
    /**
     * Creates a new random instance of CSP using model RB.
     * @param n The number of variables in the instance.
     * @param alpha Determines the domain sizes of the variables.
     * @param r Determines the maximum number of constraints in the instance.
     * @param p2 The constraint tightness of the instance.
     * instance.
    */
    public CSP(int n, double alpha, double r, double p2) {
        randomModelRB(n, alpha, r, p2);                
    }
         
    /**
     * Creates a new random instance of CSP using model E.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p The proportion of conflicts within the instance.     
    */
    public CSP(int n, int m, double p) {
        randomModelE(n, m, p);
    }        
        
    /** 
     * Creates an instance of CSP from another CSP instance. It is a copy constructor.
     * @param csp The CSP instance that will be copy into the new object.
    */
    public CSP(CSP csp) {
        int i, j;
        String variableIds[];
        Constraint constraint;
        Variable variableReferences[];
        ArrayList<int[]> tuples;
        variables = new Variable[csp.getVariables().length];
        constraints = new ArrayList<Constraint>(csp.constraints.size());
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(csp.getVariables()[i]);
        }
        for (i = 0; i < csp.constraints.size(); i++) {
            constraint = csp.constraints.get(i);
            variableReferences = constraint.getScope();
            variableIds = new String[variableReferences.length];
            for (j = 0; j < variableReferences.length; j++) {
                variableIds[j] = variableReferences[j].getId();
            }
            if (constraint.getType() == Constraint.IN_EXTENSION) {            
                tuples = new ArrayList<int[]>(constraint.getTuples());
                (this.addConstraint(constraint.getId(), variableIds, tuples, !constraint.isConflict())).setSecure(constraint.isSecure());
            } else if (constraint.getType() == Constraint.IN_INTENSION) {
                (this.addConstraint(constraint.getId(), variableIds, constraint.getFunction())).setSecure(constraint.isSecure());
            } else if (constraint.getType() == Constraint.GLOBAL) {
                (this.addConstraint(constraint.getId(), variableIds, constraint.getFunction())).setSecure(constraint.isSecure());
            }
        }        
    }        
       
    /**
     * Returns the variable which id matches the string variableId.
     * @param variableId A text string with the id of the variable.
     * @return An instance of Variable that matches the given id.
    */
    public Variable getVariable(String variableId) {
        int i;
        for (i = 0; i < variables.length; i++) {
            if (variables[i].getId().equalsIgnoreCase(variableId)) {
                return variables[i];
            }
        }
        System.out.println("The variable '" + variableId + "' has not been defined.");
        System.out.println("The getVariable method cannot return a valid reference.");
        System.exit(1);
        return null;
    }
    
    /**
     * Returns the variables in the CSP instance.
     * @return An array of Variable with the variables in the CSP instance.
    */
    public Variable[] getVariables() {
        return variables;
    }
    
    /**
     * Returns the uninstantiated variables in the CSP instance.
     * @return An array of Variable with the uninstantiated variables in the CSP instance. 
    */
    public Variable[] getUninstantiatedVariables() {
        int i, k, size = 0;
        Variable uninstantiated[];
        for (i = 0; i < variables.length; i++) {
            if (!variables[i].isInstantiated()) {
                size++;
            } 
        }
        uninstantiated = new Variable[size];
        k = 0;
        for (i = 0; i < variables.length; i++) {
            if (!variables[i].isInstantiated()) {
                uninstantiated[k++] = variables[i];
            } 
        }
        return uninstantiated;
    }
    
    public Variable[] getInstantiatedVariables(){
        Variable[] instantiated = new Variable[this.getVariables().length];
        int j = 0;
        for(int i = 0; i < this.variables.length; i++){
            if(this.variables[i].isInstantiated()){
                instantiated[j] = this.variables[i];
                j++;
            }
        }
        Variable[] result = new Variable[j];
        System.arraycopy(instantiated, 0, result, 0, j + 1);
        return result;
    }
    
    /**
     * Returns the features that can represent the CSP.
    */    
    public double getFeature(int property) {
        switch (property) {
            case DOMAIN_AVERAGE:
                return getDomainAverage();
            case DOMAIN_STD_DEVIATION:
                return getDomainStandardDeviation();
            case CONSTRAINT_DENSITY:
                return getConstraintDensity();
            case CONSTRAINT_TIGHTNESS:
                return getConstraintTightness();
            case KAPPA:
                return getKappa();
            case SOLUTION_DENSITY_STANDARD_DEVIATION:
                return getRhoStandardDeviation();
            default:
                System.out.println("Error. Invalid CSP Property.");
                System.exit(1);
        }
        return 0;
    }
    
    /**
     * Returns the normalized features that can represent the CSP.
     * @param property The property to return.
    */    
    public double getNormalizedFeature(int property) {
        switch (property) {            
            case DOMAIN_AVERAGE:
                return getNormalizedDomainAverage();
            case DOMAIN_STD_DEVIATION:
                return getNormalizedDomainStandardDeviation();
            case CONSTRAINT_DENSITY:
                return getConstraintDensity();
            case CONSTRAINT_TIGHTNESS:
                return getConstraintTightness();
            case KAPPA:
                return getNormalizedKappa();
            case SOLUTION_DENSITY_STANDARD_DEVIATION:
                return getRhoStandardDeviation();
            default:
                System.out.println("Error. Invalid CSP Property.");
                System.exit(1);
        }
        return 0;
    }
    
    /**
     * Returns the features that can represent the CSP.
    */
    public static String featureToString(int property) {
        switch (property) {
            case DOMAIN_AVERAGE:
                return "DOMAIN AVG";
            case DOMAIN_STD_DEVIATION:
                return "DOMAIN STD DEV";
            case CONSTRAINT_DENSITY:
                return "P1";
            case CONSTRAINT_TIGHTNESS:
                return "P2";
            case KAPPA:
                return "KAPPA";
            case SOLUTION_DENSITY_STANDARD_DEVIATION:
                return "RHO STD DV";
            default:
                System.out.println("Error. Invalid CSP Property.");
                System.exit(1);
        }        
        return "";
    }
    
    /**
     * Adds a constraint to the CSP instance.
     * @param constraintId A text string that will be used as unique identifier for the constraint.
     * @param variableIds An array of text strings with the ids of the variables that are involved in the constraint.
     * @param function A text string for the function coded in the constraint. This function can represent a global constraint
     * or a constraint defined in intension. 
     * @return A reference to the constraint created.
    */
    public final Constraint addConstraint(String constraintId, String variableIds[], String function) {
        int i, j, k = 0;
        boolean found;
        Constraint constraint;
        Variable scope[] = new Variable[variableIds.length];
        for (i = 0; i < variableIds.length; i++) {
            found = false;
            for (j = 0; j < variables.length; j++) {
                if (variableIds[i].equalsIgnoreCase(variables[j].getId())) {                    
                    scope[k] = variables[j];
                    k++;
                    found = true;
                    break;                    
                }
            }
            if (!found) {
                System.out.println("The variable '" + variableIds[i] + "' has not been defined." );
                System.out.println("The constraint cannot be added.");
                System.exit(1);
            }
        }        
        constraint = new Constraint(constraintId, scope, function);        
        addConstraint(constraint);        
        return constraint;
    }    
    
    /**
     * Adds a constraint to the CSP instance.
     * @param constraintId A text string that will be used as unique identifier for the constraint.
     * @param variableIds An array of text strings with the ids of the variables that are involved in the constraint.
     * @param tuples An instance of ArrayList with the tuples to be used for the constraint.
     * @param supports A boolean value that indicates if the tuples are supporting values or conflicts.
     * Use true when you want to indicate that the tuples contain supporting values, false otherwise.
     * @return A reference to the constraint created.
    */    
    public final Constraint addConstraint(String constraintId, String variableIds[], ArrayList tuples,  boolean supports) {
        int i, j, k = 0;
        boolean found;
        Constraint constraint;
        Variable scope[] = new Variable[variableIds.length];
        for (i = 0; i < variableIds.length; i++) {
            found = false;
            for (j = 0; j < variables.length; j++) {
                if (variableIds[i].equalsIgnoreCase(variables[j].getId())) {
                    scope[k] = variables[j];
                    k++;
                    found = true;
                    continue;
                }
            }
            if (!found) {             
                System.out.println("The variable '" + variableIds[i] + "' has not been defined.");
                System.out.println("The constraint cannot be added.");
                System.exit(1);
            }
        }        
        constraint = new Constraint(constraintId, scope, tuples, supports);    
        addConstraint(constraint);
        return constraint;
    }           
    
    @Override
    /**
     * Returns the string representation of the CSP instance.
     * @return The string representation of the CSP instance.
    */
    public String toString() {
        int i;
        String stringRepresentation = "";
        if (variables != null) {
            for (i = 0; i < variables.length; i++) {
                stringRepresentation = stringRepresentation + variables[i].toDetailedString() + "\r\n";
            }
            for (i = 0; i < constraints.size(); i++) {
                if (!(constraints.get(i)).isSecure()) {
                    stringRepresentation = stringRepresentation + constraints.get(i).toString() + "\r\n";
                }
            }            
        }
        return stringRepresentation;
    }
    
    /**
     * Returns the constraints in the CSP instance.
     * @return An instance of ArrayList with the constraints in the CSP instance.
    */
    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }        
        
    /**
     * Returns the domain average of the uninstantiated variables in the instance.
     * @return The domain average of the uninstantiated variables in the instance.     
    */
    public double getDomainAverage() {
        int i;
        double average = 0;
        Variable remainingVariables[] = this.getUninstantiatedVariables();
        for (i = 0; i < remainingVariables.length; i++) {
            average += remainingVariables[i].getDomain().length;
        }
        return average / remainingVariables.length;
    }
    
    /**
     * Returns the standard deviation of the uninstantiated variables in the instance.
     * @return The standard deviation of the uninstantiated variables in the instance.
    */
    public double getDomainStandardDeviation() {
        int i;
        double average = getDomainAverage();
        double sum = 0;
        Variable remainingVariables[] = this.getUninstantiatedVariables();
        for (i = 0; i < remainingVariables.length; i++) {
             sum += Math.pow(remainingVariables[i].getDomain().length - average, 2);
        }
        return Math.sqrt(sum / remainingVariables.length);
    }        
    
    /**
     * Returns the constraint density of the CSP instance.
     * @return The constraint density of the CSP instance.
    */
    public double getConstraintDensity() {
        int i, constraintCount = 0;
        double maxConstraints;
        Constraint constraint;
        maxConstraints = variables.length * (variables.length - 1) / 2;
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if (constraint.getType() != Constraint.IN_EXTENSION) {
                return -1;                
            } else {
                if (!constraint.isSecure()) {
                    constraintCount++;
                }
            }
        }
        if(maxConstraints > 0) {
            return  constraintCount / maxConstraints;
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the constraint tightness of the CSP instance.
     * @return The constraint tightness of the CSP instance.
    */
    public double getConstraintTightness() {
        int i, j, d, constraintCount = 0;
        double conflicts = 0, maxConflicts = 0;
        Variable scope[];
        Constraint constraint;
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if (constraint.getType() != Constraint.IN_EXTENSION) {
                return -1;
            } else {
                if (!constraint.isSecure()) {
                    scope = constraint.getScope();
                    d = 1;
                    for (j = 0; j < scope.length; j++) {
                        d *= scope[j].getDomain().length;
                    }
                    if (((double) constraint.getTuples().size() / (double) d) > 1) {
                        System.out.println("A fatal error ocurred when calculating the constraint tightness.");
                        System.out.println("The system will halt.");
                        System.exit(1);
                    }
                    if (constraint.isConflict()) {
                        conflicts += (double) constraint.getTuples().size();
                    } else {
                        conflicts += d - (double) constraint.getTuples().size();
                    }
                    maxConflicts += d;
                    constraintCount++;
                }
            }
        }
        if (constraintCount == 0) {
            return 0;
        } else {
            return conflicts / maxConflicts;
        }
    }
    
    /**
     * Returns the solution density of the CSP instance.     
     * @return The solution density of the CSP instance.
    */
    public double getSolutionDensity() {
        int i, j, d;
        double density = 1;
        Constraint constraint;
        Variable scope[];
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            scope = constraint.getScope();            
            if (constraint.getType() != Constraint.IN_EXTENSION) {
                return -1;
            } else {
                if (!constraint.isSecure()) {
                    d = 1;
                    for (j = 0; j < scope.length; j++) {
                        d *= scope[j].getDomain().length;
                    }
                    if (constraint.isConflict()) {
                        density *= 1 - (constraint.getTuples().size() / (double) d);                        
                    } else {
                        density *= constraint.getTuples().size() / (double) d;
                    }
                }
            }
        }        
        return density;
    }
    
    /**
     * Returns an estimation of the kappa factor of the CSP instance.
     * @return An estimation of the kappa factor of the CSP instance.
    */
    public double getKappa() {
        int i, j, d;
        double upperPart = 0, lowerPart = 0, pc;
        Variable scope[];
        Constraint constraint;
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            scope = constraint.getScope();
            if (constraint.getType() != Constraint.IN_EXTENSION) {
                return -1;
            } else {
                if (!constraint.isSecure()) {
                    d = 1;
                    for (j = 0; j < scope.length; j++) {
                        d *= scope[j].getDomain().length;
                    }
                    if (constraint.isConflict()) {
                        pc = (constraint.getTuples().size() / (double) d);
                    } else {
                        pc = 1 - (constraint.getTuples().size() / (double) d);
                    }
                    if (1 - pc > 0) {
                        upperPart += Math.log10(1 - pc) / Math.log10(2);
                    } else {
                        upperPart = Double.POSITIVE_INFINITY;
                    }
                }
            }
        }
        for (i = 0; i < variables.length; i++) {
            if (variables[i].getDomain().length > 0) {
                lowerPart += Math.log10(variables[i].getDomain().length) / Math.log10(2);
            } else {
                lowerPart = 0;
            }
        }
        if (lowerPart > 0) {
            return Math.abs(upperPart / lowerPart);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }
    
    /**
     * Returns the expected number solutions in the CSP instance.
     * @return The expected number solutions in the CSP instance.
    */
    public double getExpectedNumberOfSolutions() {
        int i;
        double expectedSolutions = 1;
        Variable remainingVariables[] = this.getUninstantiatedVariables();
        for (i = 0; i < remainingVariables.length; i++) {
            expectedSolutions *= remainingVariables[i].getDomain().length;
        }
        return expectedSolutions * getSolutionDensity();
    }    
    
    /**
     * Returns the uniformity coefficient.
     * @return The uniformity coefficient.
    */
    public double getRhoStandardDeviation() {
        int i;
        double mean, deviation;
        double solutionDensities[];
        Variable remainingVariables[] = getUninstantiatedVariables();
        solutionDensities = new double[remainingVariables.length];
        for (i = 0; i < solutionDensities.length; i++) {
            solutionDensities[i] = solutionDensity(remainingVariables[i]);            
        }
        mean = StatisticalTools.mean(solutionDensities);
        deviation = StatisticalTools.stdev(solutionDensities);        
        if (mean > 0) {
            return deviation / mean;
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the constraint density standard deviation of the instance.
     * @return The constraint density standard deviation of the instance.
    */
    public double getConstraintDensityStandardDeviation() {
        int i;
        double values[];
        Variable remainingVariables[] = getUninstantiatedVariables();
        values = new double[remainingVariables.length];
        for (i = 0; i < remainingVariables.length; i++) {
            values[i] = (double) remainingVariables[i].getConstraints().size() / (remainingVariables.length - 1);            
        }
        return StatisticalTools.stdev(values);
    }
    
    /**
     * Returns the constraint tightness standard deviation of the CSP instance.
     * @return The constraint tightness standard deviation of the CSP instance.
    */
    public double getConstraintTightnessStandardDeviation() {
        int i, j, d, constraintCount = 0;
        double values[];
        Variable scope[];
        ArrayList<Double> conflicts = new ArrayList<Double>(100);
        Constraint constraint;
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if (constraint.getType() != Constraint.IN_EXTENSION) {
                return -1;
            } else {
                if (!constraint.isSecure()) {
                    scope = constraint.getScope();
                    d = 1;
                    for (j = 0; j < scope.length; j++) {
                        d *= scope[j].getDomain().length;
                    }
                    if (((double) constraint.getTuples().size() / (double) d) > 1) {
                        System.out.println("A fatal error ocurred when calculating the constraint tightness.");
                        System.out.println("The system will halt.");
                        System.exit(1);
                    }
                    if (constraint.isConflict()) {
                        conflicts.add((double) constraint.getTuples().size() / d);
                    } else {
                        conflicts.add(d - (double) constraint.getTuples().size() / d);
                    }                    
                    constraintCount++;
                }
            }
        }
        if (constraintCount == 0) {
            return 0;
        } else {
            values = new double[conflicts.size()];
            for (i = 0; i < conflicts.size(); i++) {
                values[i] = conflicts.get(i);
            }
            return StatisticalTools.stdev(values);            
        }
    }
            
    /**
     * Returns the solution density of a given variable in the CSP instance.
     * @param variable The instance of variable which solution density we want to know.
     * @return The solution density of a given variable in the CSP instance.
    */
    public double solutionDensity(Variable variable) {
        int i, j, d;
        double density = 1;
        Constraint constraint;
        Variable scope[];
        ArrayList<Constraint> localConstraints = variable.getConstraints();
        for (i = 0; i < localConstraints.size(); i++) {
            constraint = localConstraints.get(i);            
            if (!constraint.isSecure() && constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict()) {
                scope = constraint.getScope();
                d = 1;
                for (j = 0; j < scope.length; j++) {
                    d *= scope[j].getDomain().length;
                }
                density *= 1 - (constraint.getTuples().size() / (double) d);
            }
        }        
        return density;
    }
    
    /**
     * Returns the solution density of a given variable in the CSP instance.
     * @param variable The instance of variable which solution density we want to know.
     * @return The solution density of a given variable in the CSP instance.
    */
    public double kappa(Variable variable) {
        int i, j, d;
        double kappa = 0;
        Constraint constraint;
        Variable scope[];
        ArrayList<Constraint> localConstraints = variable.getConstraints();
        for (i = 0; i < localConstraints.size(); i++) {
            constraint = localConstraints.get(i);            
            if (!constraint.isSecure() && constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict()) {
                scope = constraint.getScope();
                d = 1;
                for (j = 0; j < scope.length; j++) {
                    d *= scope[j].getDomain().length;
                }
                kappa += Misc.log2(1 - (constraint.getTuples().size() / (double) d));                
            }
        }        
        return -1 * kappa / Misc.log2(variable.getDomain().length);
    }   
    
    /**
     * Returns the saturation degree of a given variable in the CSP instance.
     * @param variable The instance of variable which saturation degree we want to know.
     * @return The saturation degree of a given variable in the CSP instance.
    */
    public int degree(Variable variable, boolean forward) {
        int i, j, degree = 0;
        Constraint constraint;
        Variable scope[];
        ArrayList<Constraint> localConstraints = variable.getConstraints();
        for (i = 0; i < localConstraints.size(); i++) {
            constraint = localConstraints.get(i);
            scope = constraint.getScope();
            for (j = 0; j < scope.length; j++) {
                if (forward) {
                    if (scope[j] != variable && scope[j].isInstantiated()) {
                        degree++;
                    }
                } else {
                    if (scope[j] != variable && !scope[j].isInstantiated()) {
                        degree++;
                    }
                }
            }
        }
        return degree;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Adds a constraint to the CSP instance.
     * @param The intance of Constraint to be added to the CSP instance.
     * @return A reference to the constraint created.
    */    
    private Constraint addConstraint(Constraint constraint) {
        int i;
        Variable scope[] = constraint.getScope();
        for (i = 0; i < scope.length; i++) {
            scope[i].addConstraint(constraint);
        }
        constraints.add(constraint);        
        return constraint;
    }        
    
    /** 
     * Returns true if a tuple <x, y> as already been defined, false otherwise.
     * @param x The integer value for x.
     * @param y The integer value for y.
     * @return The function returns true if a tuple <x, y> as already been defined, false otherwise.
    */
    private boolean isPairDefined(ArrayList tuples, int x, int y) {
        int i;
        int pair[];
        for (i = 0; i < tuples.size(); i++) {
            pair = (int []) tuples.get(i);
            if (pair[0] == x && pair[1] == y) {
                return true;
            }
        }
        return false;
    }        
    
    /**
     * Removes the constraints that were not selected during the subset creation.
    */
    private void cleanConstraints() {
        int i, j, k;
        boolean found;
        Constraint constraint;        
        for (i = 0; i < variables.length; i++) {            
            for (j = 0; j < variables[i].getConstraints().size(); j++) {
                constraint = variables[i].getConstraints().get(j);
                found = false;
                for (k = 0; k < constraints.size(); k++) {
                    if (constraints.get(k).getId().equalsIgnoreCase(constraint.getId())) {
                        found = true;
                    }
                }
                if (!found) {
                    variables[i].getConstraints().remove(j);
                    j--;
                }
            }
        }
    }
    
    /**
     * Secures all the cosntraints that have no conflicts over them.
    */
    private void secureEmptyConstraints() {
        int i;
        Constraint constraint;        
        for (i = 0; i < constraints.size(); i++) {            
            constraint = (Constraint) constraints.get(i);
            if (constraint.getType() == Constraint.IN_EXTENSION) {
                if (constraint.getTuples().isEmpty()) {
                    constraint.setSecure(true);
                }
            }
        }
    }
    
    /**
     * Returns the normalized domain average of the uninstantiated variables.
     * @return The normalized domain average of the uninstantiated variables.
    */
    private double getNormalizedDomainAverage() {        
        int i, max = 0;
        Variable remainingVariables[] = this.getUninstantiatedVariables();
        for (i = 0; i < remainingVariables.length; i++) {
            if (remainingVariables[i].getDomain().length > max) {
                max = remainingVariables[i].getDomain().length;
            }
        }
        if (max > 0) {
            return getDomainAverage() / max;
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the normalized domain average of the uninstantiated variables.
     * @return The normalized domain average of the uninstantiated variables.
    */
    private double getNormalizedDomainStandardDeviation() {
        double average = getDomainAverage();
        if (average > 0) {
            return getDomainStandardDeviation() / average;
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the normalized kappa value for the instance.
     * @return The nrmalizaed kappa value for the instance.
    */
    private double getNormalizedKappa() {
        if (getKappa() / 3 > 1) {
            return 1;
        } else {
            return getKappa() / 3;
        }                   
    }     
    
    /**
     * Returns true if a constraint between the variables with ids varAId and avarBId has already been defined, 
     * 'false' otherwise.
     * @param varAId A text string with the id of the first varible.
     * @param varAId A text string with the id of the second varible.
     * @return The function returns true if a constraint between the variables with ids varAId and avarBId has already
     * been defined, 'false' otherwise.
    */
    private boolean constraintNotDefined(String varAId, String varBId) {
        int i;
        Variable scope[];
        for (i = 0; i < constraints.size(); i++) {
            scope = constraints.get(i).getScope();
            if (scope[0].getId().equalsIgnoreCase(varAId) && scope[1].getId().equalsIgnoreCase(varBId)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Creates a new random instance of CSP using model B. This implementation
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p1 The constraint density of the instance.
     * @param p2 The constraint tightness of the instance.
    */
    private void randomModelB(int n, int m, double p1, double p2) {
        int i, j, x, y, constraintsLeft, conflictsLeft, constraintCounter;
        int domain[];
        ArrayList<int[]> tuples = new ArrayList<int[]>(25);              
        constraints = new ArrayList<Constraint>(n * n);
        domain = new int[m];
        for (i = 0; i < domain.length; i++) {
            domain[i] = i;
        }        
        if (p1 > 1.0) {
            p1 = 1.0;
        }
        if (p2 > 1.0) {
            p2 = 1.0;
        }        
        variables = new Variable[n];
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);
        }                
        constraintCounter = 0;
        constraintsLeft = (int) Math.ceil(p1 * n * (n - 1) / 2); 
        conflictsLeft = (int) Math.ceil(p2 * Math.pow(m, 2));        
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                tuples = new ArrayList(25);                
                for (x = 0; x < m; x++) {
                    for (y = 0; y < m; y++) {
                        tuples.add(new int[]{x, y});
                    }                    
                }
                tuples = Misc.randomSample(tuples, conflictsLeft);
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
                constraintCounter++;
            }
        }
        constraints = Misc.randomSample(constraints, constraintsLeft);
        cleanConstraints();
        secureEmptyConstraints();
    }
    
    /**
     * Creates a new random instance of CSP using model B. This implementation
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p1 The constraint density of the instance.
     * @param p2 The constraint tightness of the instance.
    */
    public CSP(int n, int m, double p1, double p2, boolean supports) {
        int i, j, x, y, constraintsLeft, conflictsLeft, constraintCounter;
        int domain[];
        ArrayList<int[]> tuples = new ArrayList<int[]>(25);              
        constraints = new ArrayList<Constraint>(n * n);
        domain = new int[m];
        for (i = 0; i < domain.length; i++) {
            domain[i] = i;
        }        
        if (p1 > 1.0) {
            p1 = 1.0;
        }      
        if (p2 > 1.0) {
            p2 = 1.0;
        }        
        variables = new Variable[n];
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);
        }                
        constraintCounter = 0;
        constraintsLeft = (int) Math.ceil(p1 * n * (n - 1) / 2); 
        conflictsLeft = (int) Math.ceil(p2 * Math.pow(m, 2));        
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                tuples = new ArrayList<int[]>(25);       
                for (x = 0; x < m; x++) {
                    for (y = 0; y < m; y++) {
                        tuples.add(new int[]{x, y});
                    }                    
                }
                tuples = Misc.randomSample(tuples, conflictsLeft);
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, supports);
                constraintCounter++;
            }
        }
        constraints = Misc.randomSample(constraints, constraintsLeft);
        cleanConstraints();
        secureEmptyConstraints();
    }      
    
    /**
     * Creates a new random instance of CSP using model E. This implementation 
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p The proportion of conflicts within the instance.     
    */
    private void randomModelE(int n, int m, double p) {
        int i, j, x, y, constraintCounter = 0, index;
        int domain[];
        Constraint constraint;
        double conflictsLeft;
        ArrayList<int[]> tuples = new ArrayList<int[]>(0);
        Random generator = new Random();
        constraints = new ArrayList<Constraint>(n * n);
        conflictsLeft = Math.ceil(p * (Combinations.factorial(n) / (2 * Combinations.factorial(n - 2))) * Math.pow(m, 2));
        variables = new Variable[n];
        domain = new int[m];
        for (i = 0; i < domain.length; i++) {
            domain[i] = i;
        }
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);
        }
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                tuples = new ArrayList();
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
                constraintCounter++;
            }
        }
        for (i = 0; i < conflictsLeft; i++) {
            index = generator.nextInt(constraints.size());            
            x = generator.nextInt(m);
            y = generator.nextInt(m);            
            constraint = (Constraint) constraints.get(index);
            tuples = constraint.getTuples();
            if (!isPairDefined(tuples, x, y)) {                
                tuples.add(new int[]{x, y});
            } 
            /*else {
                i--;
            }*/
        }
        cleanConstraints();
        secureEmptyConstraints();
    }        
    
    /**
     * Creates a new random instance of CSP using model F. This implementation 
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size of the variables.
     * @param p1 The constraint density of the instance.
     * @param p2 The constraint tightness of the instance.
    */
    private void randomModelF(int n, int m, double p1, double p2) {
        int constraintsLeft;        
        randomModelE(n, m, p2);
        constraintsLeft = (int) Math.ceil(p1 * n * (n - 1) / 2); 
        cleanConstraints();
        constraints = Misc.randomSample(constraints, constraintsLeft);
        cleanConstraints();
        secureEmptyConstraints();
    }
    
    /**
     * Creates a new random instance of CSP using model RB.This implementation 
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param alpha The factor of grow in the domain size.
     * @param r Determines the number of constraints in the instance.
     * @param p2 The proportion of conflicts per constraint.     
    */
    private void randomModelRB(int n, double alpha, double r, double p2) {
        int i, j, x, y, m, constraintsLeft, conflictsLeft, constraintCounter = 0;
        int domain[];        
        ArrayList<int[]> tuples;        
        Constraint constraint;
        m = (int) Math.pow(n, alpha);
        variables = new Variable[n];
        domain = new int[m];
        for (i = 0; i < m; i++) {
            domain[i] = i;
        }
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);            
        }
        constraints = new ArrayList<Constraint>((int) Math.round(r));
        conflictsLeft = (int) Math.round(p2 * Math.pow(m, 2));
        for (i = 0; i < n; i++) {
            for (j = i + 1; j < n; j++) {
                tuples = new ArrayList(m * m);                
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
                constraintCounter++;
            }
        }
        constraintsLeft = (int) Math.round(r * n * Math.log(n));
        if (constraintsLeft > constraints.size()) {
            constraintsLeft = constraints.size();
        }
        constraints = Misc.randomSample(constraints, constraintsLeft);        
        //constraints = Misc.randomSampleWithRepetitions(constraints, constraintsLeft);
        cleanConstraints();
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            tuples = constraint.getTuples();
            for (x = 0; x < m; x++) {
                for (y = 0; y < m; y++) {
                    tuples.add(new int[]{x, y});
                }
            }
            tuples = Misc.randomSample(tuples, conflictsLeft);
            constraint.setTuples(tuples);
        }
        secureEmptyConstraints();
    }        
    
    /**
     * Creates a new random instance of CSP using model J. This implementation 
     * creates constraints with arity = 2.
     * @param n The number of variables in the instance.
     * @param m The uniform domain size.
     * @param p1 The proportion of instances per constraint.
     * @param p2 The proportion of conflicts per constraint.     
    */
    private void randomModelJ(int n, int m, double p1, double p2) {
        int n1, n2, i, j, k, x, y, constraintsLeft, conflictsLeft, constraintCounter;
        int domain[];
        Random generator = new Random();
        ArrayList<int[]> tuples = new ArrayList<int[]>(25);              
        constraints = new ArrayList<Constraint>(n * n);
        domain = new int[m];
        for (i = 0; i < domain.length; i++) {
            domain[i] = i;
        }        
        if (p1 > 1.0) {
            p1 = 1.0;
        }
        if (p2 > 1.0) {
            p2 = 1.0;
        }    
        variables = new Variable[n];
        for (i = 0; i < variables.length; i++) {
            variables[i] = new Variable(("V" + i), domain);
        }
        if (n % 2 == 0) {
            n1 = n / 2;
            n2 = n / 2;
        } else {
            n1 = (int) Math.ceil((double) n / 2);
            n2 = (int) Math.floor((double) n / 2);
        }        
        constraintCounter = 0;
        constraintsLeft = (int) Math.ceil(p1 * ((n1 * (n1 - 1) / 2) + (n2 * (n2 - 1) / 2))); 
        conflictsLeft = (int) Math.ceil(p2 * Math.pow(m, 2));
        // Part I                
        for (i = 0; i < n1; i++) {
            for (j = i + 1; j < n1; j++) {
                tuples = new ArrayList(25);
                for (x = 0; x < m; x++) {
                    for (y = 0; y < m; y++) {
                        tuples.add(new int[]{x, y});
                    }                    
                }
                tuples = Misc.randomSample(tuples, conflictsLeft);
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
                constraintCounter++;
            }
        }
        // Part II
        for (i = n1; i < n1 + n2; i++) {
            for (j = i + 1; j < n1 + n2; j++) {
                tuples = new ArrayList(25);
                for (x = 0; x < m; x++) {
                    for (y = 0; y < m; y++) {
                        tuples.add(new int[]{x, y});
                    }                    
                }
                tuples = Misc.randomSample(tuples, conflictsLeft);
                this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
                constraintCounter++;
            }
        }
        constraints = Misc.randomSample(constraints, constraintsLeft);
        cleanConstraints();
        constraintsLeft = (int) Math.ceil(p1 * n * (n - 1) / 2) - constraintsLeft;
        // Connecting the two parts of the graph        
        for (k = 0; k < constraintsLeft; k++) {
            do {
                i = generator.nextInt(n1);
                j = n1 + generator.nextInt(n2);                
            } while (i != j && !constraintNotDefined(variables[i].getId(),variables[j].getId() ));
            tuples = new ArrayList(25);
            for (x = 0; x < m; x++) {
                for (y = 0; y < m; y++) {
                    tuples.add(new int[]{x, y});
                }
            }
            tuples = Misc.randomSample(tuples, conflictsLeft);
            this.addConstraint(("C" + constraintCounter), new String[]{variables[i].getId(), variables[j].getId()}, tuples, Constraint.CONFLICTS);
            constraintCounter++;
        }        
        secureEmptyConstraints();        
    }
    
    
    public void prepareForEvaluation(){
        
        for(Constraint constraint : this.constraints){
            constraint.setPxi();
        }
        
        for(Variable variable : this.variables){
            variable.setNumberOfConflicts();
            variable.setNumberOfConstraintsInvolved();
            variable.setFDeg();
            variable.setBDeg();
            variable.setBBZ();
            variable.setFBZ();
        }
    }
    
    /**
     * Transforms the in intension constraints within an instance into in extension
     * ones.
    */
    private final void transformCSP() {
        int i;
        Constraint constraint;
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if (constraint.getType() == Constraint.IN_INTENSION) {
                transformConstraint(constraint, i);                
            }
        }
    }
    
    /**
     * If the constraint is defined in intension, this function can transform it 
     * into an in extension constraint. It only works for binary constraints.
    */
    private Constraint transformConstraint(Constraint constraint, int index) {
        int i, j;
        int a[], b[];
        ArrayList<int[]> tuples = new ArrayList<int[]>(20);
        a = Misc.copy(constraint.getScope()[0].getDomain());
        b = Misc.copy(constraint.getScope()[1].getDomain());
        if (constraint.getType() == Constraint.IN_INTENSION && constraint.getScope().length == 2) {
            a = Misc.copy(constraint.getScope()[0].getDomain());
            b = Misc.copy(constraint.getScope()[1].getDomain());
            for (i = 0; i < a.length; i++) {
                constraint.getScope()[0].setValue(a[i]);
                for (j = 0; j < b.length; j++) {
                    constraint.getScope()[1].setValue(b[j]);
                    InIntensionConstraintChecker checker = new InIntensionConstraintChecker(this, constraint);                 
                    if (checker.check() == 0) {                        
                        tuples.add(new int[]{a[i], b[j]});
                    }
                }
            }
        }
        constraint.getScope()[0].setDomain(a);
        constraint.getScope()[1].setDomain(b);
        constraint = new Constraint(constraint.getId(), constraint.getScope(), tuples, false);
        constraints.set(index, constraint);
        return constraint;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */

    @Override
    public boolean equals(Input otherInput) {
        return false;
    }
    
}
