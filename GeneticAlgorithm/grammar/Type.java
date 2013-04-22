package grammar;

import java.util.ArrayList;

/**
 * Represents the class of an object and contains the functions whose
 * return type is represented by this.
 * 
 * @author Jesús Irais González Romero
 */
public class Type {

    public Class typeClass;
    private ArrayList<Function> functions;
    private ArrayList<Function> terminals;
    private ArrayList<Function> notTerminals;
    private ArrayList<Function> functionsForApplyFunctionFirstParameter;
    private int minCalls = Integer.MAX_VALUE - 1;

    /**
     * Constructs a type given a class.
     * @param type the class to be represented by the constructed type.
     */
    public Type(Class type) {
        this.typeClass = type;
        this.functions = new ArrayList<Function>();
        this.terminals = new ArrayList<Function>();
        this.notTerminals = new ArrayList<Function>();
        this.functionsForApplyFunctionFirstParameter = new ArrayList<Function>();
    }

    /**
     * @return An arraylist of functions whose return type is represented by
     * this.
     */
    public ArrayList<Function> getFunctions() {
        return functions;
    }

    /**
     * Adds a function to the arraylist of functions represented by this class.
     * It also adds the function to the arraylist of notTerminalFunctions if
     * the function is not terminal or to the arraylist of terminalFunctions 
     * otherwise.
     * @param function The function to be added.
     */
    protected void addFunction(Function function) {
        this.functions.add(function);
        if (function.isTerminal) {
            this.terminals.add(function);
        } else {
            this.notTerminals.add(function);

            if (function.getArity() == 2) {
                Class<?>[] parameters = function.getParameterTypes();
                if (parameters[0] == parameters[1]) {
                    this.functionsForApplyFunctionFirstParameter.add(function);
                }
            }
        }
    }

    /**
     * Sort functions from smallest to biggest minCalls.
     * Uses a boolean search algorithm to find the position in which each 
     * function should be placed.
     */
    protected void sortFunctions() {
        ArrayList<Function> sortedFunctions = new ArrayList<Function>();
        int first;
        int last;
        int middle = 0;
        Function leftFunction;
        Function rightFunction;
        Function functionToAdd;
        Function pivot;
        sortedFunctions.add(this.functions.get(0));
        for (int i = 1; i < this.functions.size(); i++) {
            first = 0;
            last = sortedFunctions.size() - 1;
            functionToAdd = this.functions.get(i);
            while (true) {
                middle = (last - first) / 2 + first;
                pivot = sortedFunctions.get(middle);
                leftFunction = (middle - 1) < first ? null : sortedFunctions.get(middle - 1);
                rightFunction = (middle + 1) > sortedFunctions.size() - 1 ? null : sortedFunctions.get(middle + 1);
                if ((leftFunction == null || leftFunction.getMinCalls() <= functionToAdd.getMinCalls()) && (rightFunction == null || rightFunction.getMinCalls() >= functionToAdd.getMinCalls())) {
                    middle = pivot.getMinCalls() > functionToAdd.getMinCalls() ? middle - 1 : middle;
                    break;
                } else if (pivot.getMinCalls() > functionToAdd.getMinCalls()) {
                    last = middle - 1;
                } else {
                    first = middle + 1;
                }
            }
            sortedFunctions.add(middle + 1, functionToAdd);
        }
        this.functions = sortedFunctions;
    }

    /**
     * Checks if this type is compatible with the given type. This type is 
     * compatible with when this type represents the same class
     * or an upper class in the herarchy to the class represented by the given
     * type.
     * @param type The given type.
     * @return True if this type is compatible with the given type, false
     * otherwise.
     */
    public boolean isCompatibleWithType(Type type) {
        return this.typeClass.isAssignableFrom(type.typeClass);
    }
    
    /**
     * Checks if the class represented by this type is compatible with the given class.
     * The class represented by this type is 
     * compatible with the given class when this type represents the same class
     * or an upper class in the herarchy to the given class.
     * @param aClass The given class.
     * @return True if this type is compatible with type that represents the
     * class, false otherwise.
     */
    public boolean isCompatibleWithClass(Class aClass) {
        return this.typeClass.isAssignableFrom(aClass);
    }

    /**
     * Checks if this type corresponds to the given type. A type corresponds
     * to another type when both represent the same class.
     * @param type The given type.
     * @return True if this corresponds the given type, false otherwise.
     */
    public boolean correspondondsToType(Type type) {
        return this.typeClass == type.typeClass;
    }

    /**
     * Checks if this type corresponds to the given class. A type corresponds
     * to a class when the class represented by the type is the same as the 
     * given class.
     * @param aClass The given class.
     * @return True if this corresponds to the given class, false otherwise.
     */
    public boolean correspondondsToClass(Class aClass) {
        return this.typeClass == aClass;
    }

    /**
     * Returns a function given an index. If the onlyTerminals flag is on, the
     * function will be taken from the terminals arraylist.
     * @param index The index of the fuction to be returned.
     * @param onlyTerminals A flag that indicates if the function should be
     * taken from the onlyTerminals arraylist.
     * @return The function that corresponds to the given index in the array
     * determined by the onlyTerminals flag.
     */
    public Function getFunctionAtIndex(int index, boolean onlyTerminals) {
        if(onlyTerminals){
            return this.getFunctionWithMaxDepthAndMinDepth(this.getMinCalls() - 1, 0);
        }
        
        
        return this.functions.get(index);
    }

    /**
     * Returns a function given an index, if the index overflows the arraylist,
     * a function is choosen by applying modulus to the index. If the
     * onlyTerminals flag is on, the function will be taken from the terminals
     * arraylist.
     * @param index The index of the fuction to be returned.
     * @param onlyTerminals A flag that indicates if the function should be
     * taken from the onlyTerminals arraylist.
     * @return The function that corresponds to the given index in the array
     * determined by the onlyTerminals flag.
     */
    public Function getFunctionAtIndexWithModulus(int index, boolean onlyTerminals) {
        int modulus = index % this.functions.size();
        return getFunctionAtIndex(modulus, onlyTerminals);
    }

    /**
     * Randomly selects a function that could be used as a first parameter for
     * an apply function.
     * @param maxDepth The max depth that the function to return can have.
     * @return A function that could be used as a first parameter for an apply
     * function.
     */
    public Function getFunctionForApplyFirstParameter(int maxDepth) {
        ArrayList<Function> possibleFunctions = new ArrayList<Function>(this.functionsForApplyFunctionFirstParameter.size());
        for (Function function : this.functionsForApplyFunctionFirstParameter) {
            if (function.minCalls <= maxDepth && function.getArity() == 2) {
                possibleFunctions.add(function);
            }
        }
        return possibleFunctions.get((int) (Math.random() * possibleFunctions.size()));
    }

    
    
    
    public Function getFunctionWithMaxDepthAndMinDepth(int maxDepth, int minDepth) {
        int firstSelectableFunction = 0;
        int lastSelectableFunction = 0;
        int maxCalls = maxDepth + 1;
        for (Function function : functions) {
            if (function.getMinCalls() < minDepth) {
                firstSelectableFunction++;
            }
            if (function.getMinCalls() <= maxCalls) {
                lastSelectableFunction++;
            }
        }

        int rangeOfSelectableFunctions = lastSelectableFunction - firstSelectableFunction;
        if(rangeOfSelectableFunctions == 0){
            System.out.println("error of selectable functions in Type.java");
        }

        int functionIndex = (int) (Math.random() * rangeOfSelectableFunctions) + firstSelectableFunction;
        Function functionToReturn = this.functions.get(functionIndex);
        if (IfThenElseFunction.class.isAssignableFrom(functionToReturn.getClass())) {
            double random = Math.random();
            if (random > ((IfThenElseFunction) functionToReturn).getProbability()) {
                functionToReturn = this.getFunctionWithMaxDepthAndMinDepth(maxDepth, minDepth);
            }
        }
        
        if(functionToReturn == null){
            System.out.println("error, not function to return in Type.java");
        }
        return functionToReturn;
    }

    
    /**
     * @return The minimum number of calls needed to generate an object of this
     * type.
     */
    public int getMinCalls() {
        return this.minCalls;
    }

    /**
     * Sets the minimum number of calls needed to generate an object of this type.
     * @param minCalls  Minimum number of calls needed to generate an object of
     * this type.
     */
    protected void setMinCalls(int minCalls) {
        this.minCalls = minCalls;
    }

    /**
     * @return A description of the type as a string. It includes a description
     * of the functions that it contains.
     */
    @Override
    public String toString() {
        String string = "";
        string += this.typeClass.getSimpleName() + " " + this.minCalls +  "\t    ->";
        for (Function function : functions) {
            string += "\n\t\t" + function.getName();
            for (Class type : function.getParameterTypes()) {
                string += " " + type.getSimpleName();
            }
        }
        return string;
    }
}