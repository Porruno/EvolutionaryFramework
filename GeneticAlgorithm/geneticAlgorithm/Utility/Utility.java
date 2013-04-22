package geneticAlgorithm.Utility;

import geneticAlgorithm.Genome;
import java.util.ArrayList;


/**
 * This class contains only utility functions.
 * @author Jesús Irais González Romero
 */
public class Utility {

    /**
     * Sorts the genomes in descendant order with boolean search. Descendant order means
     * from best to worst.
     * @param population An array of genomes to be sorted.
     */
    public static void sortGenomesDescendantOrder(Genome[] population) {
        ArrayList<Genome> sortedGenomes = new ArrayList<Genome>(population.length);

        int first;
        int last;
        int middle = 0;
        Genome leftGenome;
        Genome rightGenome;
        Genome genomeToAdd;
        Genome pivot;
        sortedGenomes.add(population[0]);
        for (int i = 1; i < population.length; i++) {
            first = 0;
            last = sortedGenomes.size() - 1;
            genomeToAdd = population[i];
            while (true) {
                middle = (last - first) / 2 + first;
                pivot = sortedGenomes.get(middle);
                leftGenome = (middle - 1) < first ? null : sortedGenomes.get(middle - 1);
                rightGenome = (middle + 1) > sortedGenomes.size() - 1 ? null : sortedGenomes.get(middle + 1);
                if ((leftGenome == null || leftGenome.getAptitude() >= genomeToAdd.getAptitude()) && (rightGenome == null || rightGenome.getAptitude() <= genomeToAdd.getAptitude())) {
                    middle = pivot.getAptitude() < genomeToAdd.getAptitude() ? middle - 1 : middle;
                    break;
                } else if (pivot.getAptitude() < genomeToAdd.getAptitude()) {
                    last = middle - 1;
                } else {
                    first = middle + 1;
                }
            }
            sortedGenomes.add(middle + 1, genomeToAdd);
        }
        population = sortedGenomes.toArray(population);
    }

    /**
     * Used by OrderByFunction class to insert an ObjectNumber object in an
     * arraylist mantaining the ascendant order.
     * @param objectNumbers An arraylist of ObjectNumbers already in order.
     * @param objectToAdd Object to be added to the first parameter.
     */
    public static void insertObjectNumberInOrder(ArrayList<ObjectNumber> objectNumbers,
            ObjectNumber objectToAdd) {
        int first;
        int last;
        int middle = 0;
        ObjectNumber leftObject;
        ObjectNumber rightObject;
        ObjectNumber pivot;

        first = 0;
        last = objectNumbers.size() - 1;

        while (true) {
            middle = (last - first) / 2 + first;
            pivot = objectNumbers.get(middle);
            leftObject = (middle - 1) < first ? null : objectNumbers.get(middle - 1);
            rightObject = (middle + 1) > objectNumbers.size() - 1 ? null : objectNumbers.get(middle + 1);
            if ((leftObject == null || leftObject.number >= objectToAdd.number) && (rightObject == null || rightObject.number <= objectToAdd.number)) {
                middle = pivot.number < objectToAdd.number ? middle - 1 : middle;
                break;
            } else if (pivot.number < objectToAdd.number) {
                last = middle - 1;
            } else {
                first = middle + 1;
            }
        }
        objectNumbers.add(middle + 1, objectToAdd);
    }
}