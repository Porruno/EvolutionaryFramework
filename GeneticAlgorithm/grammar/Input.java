package grammar;

/**
 * The abstraction of all the input types. Implementors: Custom classes created
 * by the user for all the populations, ADFInput.
 * @author Jesús Irais González Romero
 */
public abstract class Input {
	
    /**
     * Returns true if this is equal to the given parameter. It may be usefull
     * if in the future, the frameworks is optimized so that a function returns
     * its last result in the case that the input of the previous call is the
     * same as the input of the current call.
     * @param otherInput The other input to be compared.
     * @return True if they are equal.
     */
	public abstract boolean equals(Input otherInput);
}
