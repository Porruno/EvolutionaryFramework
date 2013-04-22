package grammar;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A kind of function that represents the functions programmed in Java by the
 * user.
 * It uses Java's Reflection API to obtain the necessary information like: 
 * name of the function, return type and its parameter types.
 * @author Jesús Irais González Romero.
 */
public class SimpleFunction extends Function {

    public Method method;

    /**
     * Constructor method. Creates a simple function based on a java method.
     * @param method 
     */
    public SimpleFunction(Method method) {
        this.method = method;
        this.returnType = method.getReturnType();
        this.parameterTypes = method.getParameterTypes();
        this.name = method.getName();
        checkIfIsTerminal();
    }

    /**
     * @return The method's name.
     */
    @Override
    public String getName() {
        return method.getName();
    }

    /**
     * Evaluates its method with the specified parameters.
     * @param parameters Objets to be evaluated with its method.
     * @return The result of evaluating the parameters with its method.
     */
    @Override
    public Object evaluate(Object... parameters) {
        try {
            return this.method.invoke(null, parameters);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SimpleFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SimpleFunction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SimpleFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return Its method's parameters.
     */
    @Override
    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }
}
