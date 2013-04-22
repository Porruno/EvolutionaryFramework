package grammar;

import grammar.adf.ADF;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * This class is in charge of creating the grammar that a population will use
 * for creating programs.
 *
 * A grammar loops when there is at least one function whose parameters can't be
 * produced by a terminal function.
 *
 * A terminal function is a terminal with no parameters or whose parameters are
 * of the class specified by the parameter inputClass in the constructor method.
 *
 * @author Jesús Irais González Romero
 */
public final class Grammar {

    protected ArrayList<Type> types;
    protected ArrayList<SimpleFunction> simpleFunctions;
    protected ArrayList<Function> allFunctions;
    protected ADF[] adfs;
    protected ArrayList<Function> nonTerminalFunctions;
    protected ArrayList<Function> terminalFunctions;
    protected Class<?> inputClass;
    protected int largestArityOfAllFunctions;
    protected int maximumMinimumCallsOfAllFunctions;

    /**
     * Constructs a grammar.
     *
     * @param adfs An array of ADFs that will be included as functions in this
     * grammar
     * @param inputClass The class of the input that all entities in the
     * population that uses this grammar will receive.
     * @param probabilitiesForIfFunctionType A HashMap where the keys are types
     * and the values are the probabilities of an If-Then-Else function to be
     * chosen once it's been randomly selected.
     * @param probabilitiesForApplyFunctionType A HashMap where the keys are
     * types and the values are the probabilities of an Apply function to be
     * chosen once it's been randomly selected.
     * @param classes An array of classes that contain the functions to be
     * included in the grammar.
     * @throws Exception Exception thrown when the grammar could loop.
     */
    public Grammar(ADF[] adfs, Class<?> inputClass, HashMap probabilitiesForIfFunctionType, Class... classes) throws Exception {
        this.types = new ArrayList<Type>();
        this.simpleFunctions = new ArrayList<SimpleFunction>();
        this.nonTerminalFunctions = new ArrayList<Function>();
        this.terminalFunctions = new ArrayList<Function>();
        this.allFunctions = new ArrayList<Function>();
        this.inputClass = inputClass;

        this.addClasses(classes);

        this.adfs = adfs;
        for (int i = 0; i < adfs.length; i++) {
            this.addADF(adfs[i]);
        }

        addSpecialFunctions(probabilitiesForIfFunctionType, null);


        checkForEmptyTypes();
        LoopChecker loopChecker = new LoopChecker(this);
        loopChecker.checkForLoops();
        this.findFunctionsMinSubcalls();
        this.findMaximumMinimumCallsOfAllFunctions();
        this.sortFunctions();

    }

    /**
     * Calls the proper methods for adding the If-Then-Else and Apply functions.
     *
     * @param probabilitiesForIfFunctionType A HashMap where the keys are types
     * and the values are the probabilities of an If-Then-Else function to be
     * chosen once it's been randomly selected.
     * @param probabilitiesForApplyFunctionType A HashMap where the keys are
     * types and the values are the probabilities of an Apply function to be
     * chosen once it's been randomly selected.
     */
    private void addSpecialFunctions(HashMap probabilitiesForIfFunctionType, HashMap probabilitiesForApplyFunctionType) {
        addIfFunctions(probabilitiesForIfFunctionType);
        addApplyFunctions(probabilitiesForApplyFunctionType);
    }

    /**
     * Creates and adds an If-Then-Else function for each type in the grammar.
     * If the probability specified for certain type is zero, then, no
     * If-Then-Else function is created for that type. If the HashMap is null,
     * no If-Then-Else function is added. If no function produces a result of
     * Boolean type, then, no If-Then-Else function is created.
     *
     * @param probabilitiesForIfFunctionType
     */
    private void addIfFunctions(HashMap probabilitiesForIfFunctionType) {
        if (probabilitiesForIfFunctionType == null) {
            return;
        }
        Type booleanType = this.getTypeForClass(Boolean.class);
        if (booleanType != null) {
            for (Type type : this.types) {
                Double probability = (Double) probabilitiesForIfFunctionType.get(type.typeClass);
                if (probability == null) {
                    probability = new Double(1);
                }
                if (probability.doubleValue() == 0) {
                    continue;
                }
                this.addFunction(new IfThenElseFunction(type.typeClass, probability));
            }
        }
    }

    /**
     * Creates and adds Apply functions for all types that contain at least one
     * function whose arity = 2 and whose parameters and its return type are of
     * the same type. If the probability specified for certain type is zero,
     * then, no Apply function is created for that type. If the HashMap is null,
     * no Apply function is added.
     *
     * @param probabilitiesForApplyFunctionType
     */
    private void addApplyFunctions(HashMap probabilitiesForApplyFunctionType) {
        if (probabilitiesForApplyFunctionType == null) {
            return;
        }
        for (Type type : this.types) {
            Double probability = (Double) probabilitiesForApplyFunctionType.get(type.typeClass);
            if (probability == null) {
                probability = new Double(1);
            }
            if (probability.doubleValue() == 0) {
                continue;
            }
            for (Function function : type.getFunctions()) {
                if (function.getArity() == 2) {
                    for (Class<?> parameterType : function.getParameterTypes()) {
                        if (parameterType != type.typeClass) {
                            continue;
                        }
                    }
                    this.addFunction(new ApplyFunction(type.typeClass, probability));
                }
                break;
            }
            this.addFunction(new IfThenElseFunction(type.typeClass, probability));
        }
    }

    /**
     * Checks if the grammar contains a type without functions.
     *
     * @throws Exception Exception thrown in case that an empty type is found.
     */
    private void checkForEmptyTypes() throws Exception {
        String emptyTypes = "";
        for (Type type : types) {
            if (type.getFunctions().size() < 1) {
                emptyTypes += type.typeClass.getName() + "\n";
            }
        }

        if (!"".equals(emptyTypes)) {
            Exception exception = new Exception("This type(s) are empty:\n" + emptyTypes);
            throw exception;
        }
    }

    /**
     * Adds the functions in the classes into the grammar.
     *
     * @param classes An array of classes to be added.
     */
    private void addClasses(Class<?>... classes) {
        for (Class aClass : classes) {
            this.addTypes(aClass);
        }
        this.largestArityOfAllFunctions = 0;
        for (Class aClass : classes) {
            this.addFunctionsFromClass(aClass);
        }
    }

    /**
     * Finds and sets the minimum number of subcalls for each function in the
     * grammar. The number of subcalls is the number of calls needed to execute
     * the parameter with the largest level.
     */
    private void findFunctionsMinSubcalls() {
        boolean changeOcurred = false;
        for (int i = 0; i < this.allFunctions.size(); i++) {
            Function function = this.allFunctions.get(i);
            if (function.getMinCalls() == 1) {
                continue;
            }
            int minSubcalls = 0;
            for (Class aClass : function.getParameterTypes()) {
                if (Input.class.isAssignableFrom(aClass)) {
                    continue;
                }
                Type type = this.getTypeForClass(aClass);
                if (type.getMinCalls() > minSubcalls) {
                    minSubcalls = type.getMinCalls();
                }
            }
            int newMinCalls = minSubcalls + 1;

            if (newMinCalls < function.getMinCalls()) {
                function.setMinCalls(newMinCalls);
                changeOcurred = true;
            }
        }
        if (changeOcurred) {
            findTypesMinSubcalls();
        }
    }

    /**
     * Sets the correct value to the variable maximumMinimumCallsOfAllFunctions.
     * The correct value is the minimumCalls of the function with the largest
     * minimumCalls from all this grammar.
     */
    private void findMaximumMinimumCallsOfAllFunctions() {
        this.maximumMinimumCallsOfAllFunctions = 0;
        for (Function function : this.allFunctions) {
            if (function.getMinCalls() > this.maximumMinimumCallsOfAllFunctions) {
                this.maximumMinimumCallsOfAllFunctions = function.getMinCalls();
            }
        }
    }

    /**
     * Finds and sets for each type the minimum subcalls needed for generating a
     * result of its kind. The minimum subcalls needed for a generating a result
     * of a type is the minimum subcalls of the function with the smallest
     * minimum subcalls of that type.
     */
    private void findTypesMinSubcalls() {
        boolean changeOcurred = false;
        for (int i = 0; i < types.size(); i++) {
            Type type = types.get(i);
            for (Function function : type.getFunctions()) {
                if (function.getMinCalls() < type.getMinCalls()) {
                    type.setMinCalls(function.getMinCalls());
                    changeOcurred = true;
                }
            }
        }
        if (changeOcurred) {
            findFunctionsMinSubcalls();
        }
    }

    /**
     * Sorts the functions of all the types contained by this grammar.
     */
    private void sortFunctions() {
        for (Type type : this.types) {
            type.sortFunctions();
        }
    }

    /**
     * returns the array containing this grammar's ADFs.
     *
     * @return
     */
    public ADF[] getADFs() {
        return this.adfs;
    }

    /**
     * @return An arraylist with all the types contained by this grammar.
     */
    public ArrayList<Type> getTypes() {
        return types;
    }

    /**
     * @return An arraylist with all the terminal functions;
     */
    public ArrayList<Function> getTerminalFunctions() {
        return this.terminalFunctions;
    }

    /**
     * @param aClass The class represented by the desired type.
     * @return The type that is represented by the class specified in the
     * parameter. Returns null if no type represents the given class.
     */
    public Type getTypeForClass(Class aClass) {
        for (Type type : types) {
            if (type.correspondondsToClass(aClass)) {
                return type;
            }
        }
        return null;
    }

    public int getLargestArityOfAllFunctions() {
        return this.largestArityOfAllFunctions;
    }

    public int getMaximumMinimumCallsOfAllFunctions() {
        return this.maximumMinimumCallsOfAllFunctions;
    }

    /**
     * Adds the types represented by the return and the parameters types of the
     * functions implemented in the specified class. If the type was already
     * added, it's not added twice.
     *
     * @param aClass The class whose function's return and parameter types are
     * going to be added.
     */
    private void addTypes(Class aClass) {
        for (Method method : aClass.getDeclaredMethods()) {
            this.addClass(method.getReturnType());
            for (Class parameter : method.getParameterTypes()) {
                if (!Input.class.isAssignableFrom(parameter)) {
                    this.addClass(parameter);
                }
            }
        }
    }

    /**
     * Adds a type that will represent the given class.
     *
     * @param aClass The class to be represented by the type that it's going to
     * be created and added.
     */
    private void addClass(Class aClass) {
        boolean alreadyAdded = false;
        for (Type type : types) {
            if (type.correspondondsToClass(aClass)) {
                alreadyAdded = true;
                break;
            }
        }
        if (!alreadyAdded) {
            Type newType = new Type(aClass);
            this.types.add(newType);
        }
    }

    /**
     * Adds all the functions from a given class. Before adding a function, it
     * checks if one of its parameters is of class Input, but not the input
     * class specified for this grammar; if thats the case, the function is not
     * added.
     *
     * @param aClass The class containing the functions to be added.
     */
    private void addFunctionsFromClass(Class aClass) {
        for (Method method : aClass.getDeclaredMethods()) {
            //should skip method because it uses other's input type?
            boolean shouldSkip = false;
            for (Class<?> parameterClass : method.getParameterTypes()) {
                if (Input.class.isAssignableFrom(parameterClass) && parameterClass != this.inputClass) {
                    shouldSkip = true;
                    break;
                }
            }
            if (shouldSkip) {
                continue;
            }
            SimpleFunction newFunction = new SimpleFunction(method);
            this.addFunction(newFunction);
            this.simpleFunctions.add(newFunction);
        }
    }

    /**
     * Adds a given function to the grammar. If is terminal, it is also added to
     * the terminalFunctions arraylist. It replaces the
     * largestArityOfAllFunctions with the given function's arity if the second
     * is largest.
     *
     * @param function The function to be added.
     */
    private void addFunction(Function function) {
        for (Type type : types) {
            if (type.isCompatibleWithClass(function.getReturnType())) {
                type.addFunction(function);
            }
        }
        if (function.getArity() > this.largestArityOfAllFunctions) {
            this.largestArityOfAllFunctions = function.getArity();
        }
        if (function.isTerminal()) {
            this.terminalFunctions.add(function);
        } else {
            this.nonTerminalFunctions.add(function);
        }
        this.allFunctions.add(function);
    }

    /**
     * Adds the given adf to the arraylist of ADFs.
     *
     * @param adf The ADF to be added.
     */
    private void addADF(ADF adf) {
        this.allFunctions.add(adf);
        for (Type type : types) {
            if (type.isCompatibleWithClass(adf.getReturnType())) {
                type.addFunction(adf);
            }
        }
    }

    /**
     * Prints a description for each type in this grammar.
     *
     * @return
     */
    @Override
    public String toString() {
        String string = "";
        for (Type type : types) {
            string += type.toString();
            string += "\n-------\n";
        }

        return string;
    }
}