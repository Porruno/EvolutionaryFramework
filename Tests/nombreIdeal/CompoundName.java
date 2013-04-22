/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal;

import grammar.Input;

/**
 *
 * @author Lay
 */
public class CompoundName {

    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    static String[] names = {
       "Jesus", "Juan", "Anacleto",
       "Steve", "Bob", "Clayton",
       "Maria", "Rosa", "Carolina",
       "Mary", "Rose", "Caroline"
    };


    String firstName;
    String secondName;

    public CompoundName(String first, String second) {
        this.firstName = first;
        this.secondName = second;
    }

    public Integer length() {
        return this.firstName.length() + this.secondName.length();
    }

    public Integer numberOfVowels() {
        return numberOfVowels(firstName) + numberOfVowels(secondName);
    }
    
    public Integer numberOfConsonants(){
        return this.length() - this.numberOfVowels();
    }
    
    public Boolean hasMixedGenre(){
        return isMaleName(firstName) && !isMaleName(secondName) ||
                !isMaleName(firstName) && isMaleName(secondName);
    }
    
    public Boolean hasMixedNationality(){
        return isAmericanName(firstName) && !isAmericanName(secondName) ||
                !isAmericanName(firstName) && isAmericanName(secondName);
    }
    
    public Boolean isMasculine() {
        return isMaleName(firstName) && isMaleName(secondName);
    }
    
    @Override
    public String toString(){
        return firstName + " " + secondName;
    }
    
    //-----Auxiliar functions

    private static Integer numberOfVowels(String string) {
        int acum = 0;
        for (char aChar : string.toCharArray()) {
            for (char aVowel : vowels) {
                if (aChar == aVowel) {
                    acum++;
                    break;
                }
            }
        }
        return acum;
    }
    
    private Boolean isMaleName(String name) {

        return (name.contentEquals("Jesus")
                || name.contentEquals("Juan")
                || name.contentEquals("Anacleto")
                || name.contentEquals("Steve")
                || name.contentEquals("Bob")
                || name.contentEquals("Clayton"));
    }

    private Boolean isAmericanName(String name) {

        return (name.contentEquals("Mary")
                || name.contentEquals("Rose")
                || name.contentEquals("Caroline")
                || name.contentEquals("Steve")
                || name.contentEquals("Bob")
                || name.contentEquals("Clayton"));
    }
}
