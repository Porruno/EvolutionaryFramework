package CSP.CSP;

import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss Monterrey, Nuevo León, México August 2006 -
 * March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle variables with finite
 * domains.
 * ----------------------------------------------------------------------------
 */
public class Variable {

    private int domain[];
    private boolean instantiated = false;
    private String variableId;
    private ArrayList<Constraint> constraints;
    
    private double numberOfConflicts;
    private double numberOfConstraintsInvolved;
    private double fdeg;
    private double bdeg;
    private double bbz;
    private double fbz;

    /**
     * Creates a new instance of Variable.
     *
     * @param variableId A text string that represents the unique identifier of
     * the variable.
     * @param domain An array of int that correspond to the domain of the
     * variable.
     */
    public Variable(String variableId, int domain[]) {
        setDomain(domain);
        this.variableId = variableId;
        this.constraints = new ArrayList<Constraint>(25);
    }

    /**
     * Creates a new instance of Variable from another variable. It is a copy
     * constructor.
     *
     * @param variable The instance of Variable that will be copied into the new
     * instance.
     */
    public Variable(Variable variable) {
        variableId = variable.getId();
        setDomain(variable.getDomain());
        this.instantiated = variable.instantiated;
        this.constraints = new ArrayList(25);
    }

    /**
     * Sets the domain of the variable.
     *
     * @param domain An array of int that contains the values for the domain.
     */
    public void setDomain(int domain[]) {
        int i;
        this.domain = new int[domain.length];
        for (i = 0; i < this.domain.length; i++) {
            this.domain[i] = domain[i];
        }
        if (domain.length == 1) {
            this.instantiated = true;
        } else {
            instantiated = false;
        }
    }

    /**
     * Instantiates a variable with a given value.
     *
     * @param value An integer value used to instantiate the variable. If the
     * value does not correspond to one of the values in the current domain, the
     * instantiation fails.
     */
    public void setValue(int value) {
        int i;
        boolean validValue = false;
        instantiated = true;
        if (domain.length > 1) {
            for (i = 0; i < domain.length; i++) {
                if (value == domain[i]) {
                    validValue = true;
                }
            }
        } else {
            validValue = true;
        }
        if (validValue) {
            domain = new int[]{value};
        } else {
            System.out.println("The value '" + value + "' is not in the domain of variable '" + variableId + "'.");
            System.out.println("The variable cannot be instantiated.");
            System.exit(1);
        }
    }

    /**
     * Instantiates a variable with a given value.
     *
     * @param value An integer value used to instantiate the variable. If the
     * value does not correspond to one of the values in the current domain, the
     * instantiation fails.
     */
    public void removeValue(int value) {
        int i, k;
        int newDomain[];
        boolean validValue = false;
        if (domain.length > 0) {
            for (i = 0; i < domain.length; i++) {
                if (value == domain[i]) {
                    validValue = true;
                }
            }
        } else {
            validValue = false;
        }
        if (validValue) {
            k = 0;
            newDomain = new int[domain.length - 1];
            for (i = 0; i < domain.length; i++) {
                if (domain[i] != value) {
                    newDomain[k++] = domain[i];
                }
            }
            domain = newDomain;
        } else {
            System.out.println("The value '" + value + "' is not in the domain of variable '" + variableId + "'.");
            System.out.println("The value cannot be removed.");
            System.exit(1);
        }
    }

    /**
     * Returns the unique identifier of the variable.
     *
     * @return A text String with identifier of the variable.
     */
    public String getId() {
        return variableId;
    }

    /**
     * Returns the domain of the variable.
     *
     * @return An array of int that represent the current domain of the
     * variable. If the variable has been instantiated, the domain contains only
     * one value.
     */
    public int[] getDomain() {
        return domain;
    }

    /**
     * Returns the constraints in which the variable is involved.
     *
     * @return An instance of ArrayList that contains all the constraints in
     * which the variable is involved.
     */
    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * Returns the unsatisfied constraints in which the variable is involved.
     *
     * @return An instance of ArrayList that contains all the unsatisfied
     * constraints in which the variable is involved.
     */
    public ArrayList getUnsatisfiedConstraints() {
        int i;
        Constraint constraint;
        ArrayList<Constraint> unsatisfiedConstraints = new ArrayList<Constraint>(constraints.size());
        for (i = 0; i < constraints.size(); i++) {
            constraint = constraints.get(i);
            if (!constraint.isSecure()) {
                unsatisfiedConstraints.add(constraint);
            }
        }
        return unsatisfiedConstraints;
    }

    /**
     * Adds a constraint to the variable.
     *
     * @param constraint An instance of Constraint in which the variable is
     * involved.
     */
    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public void setInstantiated(boolean state) {
        instantiated = state;
    }

    /**
     * Returns the state of the variable. This function returns true is the
     * variable has been instantiated, false otherwise.
     *
     * @return A boolean value depending of the state of the variable. This
     * function returns true is the variable has been instantiated, false
     * otherwise.
     */
    public boolean isInstantiated() {
        return instantiated;
    }

    /**
     * Returns the detailed string representation of the variable.
     *
     * @return A text String with the string representation of the variable.
     */
    public String toDetailedString() {
        int i;
        Constraint constraint;
        String stringRepresentation = variableId;
        stringRepresentation = stringRepresentation + " = [ ";
        for (i = 0; i < domain.length; i++) {
            stringRepresentation = stringRepresentation + domain[i] + " ";
        }
        stringRepresentation = stringRepresentation + "]";
        stringRepresentation = stringRepresentation + " [";
        for (i = 0; i < constraints.size(); i++) {
            constraint = (Constraint) constraints.get(i);
            if (!constraint.isSecure()) {
                stringRepresentation = stringRepresentation + " " + constraint.getId();
            }
        }
        stringRepresentation = stringRepresentation + " ]";
        return stringRepresentation;
    }

    @Override
    /**
     * Returns the string representation of the variable.
     *
     * @return A text String with the string representation of the variable.
     */
    public String toString() {
        int i;
        String stringRepresentation = variableId;
        stringRepresentation = stringRepresentation + " = [ ";
        for (i = 0; i < domain.length; i++) {
            stringRepresentation = stringRepresentation + domain[i] + " ";
        }
        stringRepresentation = stringRepresentation + "]";
        return stringRepresentation;
    }

    public void setNumberOfConflicts() {
        this.numberOfConflicts = this.getUnsatisfiedConstraints().size();
    }

    public double getNumberOfConflicts() {
        return this.numberOfConflicts;
    }

    public void setNumberOfConstraintsInvolved() {
        this.numberOfConstraintsInvolved = this.constraints.size();
    }

    public double getNumberOfConstraintsInvolved() {
        return this.numberOfConstraintsInvolved;
    }
               
    public void setFDeg() {
        this.fdeg = 0;

        for (Constraint constraint : this.constraints) {
            double counter = 0;
            for (Variable aVariable : constraint.getScope()) {
                if (aVariable != this && !aVariable.isInstantiated()) {
                    counter++;
                }
            }
            this.fdeg += counter;
        }
    }

    public double getFDeg() {
        return this.fdeg;
    }

    public void setBDeg() {
        double neighborsAcum = 0;
        for (Constraint constraint : this.constraints) {
            neighborsAcum += constraint.getScope().length - 1;
        }
        this.bdeg = neighborsAcum - this.fdeg;
    }

    public double getBDeg() {
        return this.bdeg;
    }

    public void setBBZ() {
        if (this.bdeg == 0) {
            this.bbz = this.domain.length;
        } else {
            this.bbz = this.domain.length / this.bdeg;
        }
    }

    public double getBBZ() {
        return this.bbz;
    }

    public void setFBZ() {
        if (this.fdeg == 0) {
            this.fbz = this.domain.length;
        } else {
            this.fbz = this.domain.length / this.fdeg;
        }
    }

    public double getFBZ() {
        return this.fbz;
    }
}