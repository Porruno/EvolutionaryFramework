package CSP.HyperHeuristic;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions for creating and using a Learning ArrayList 
 * Quantization neural network.
 * ----------------------------------------------------------------------------
*/

public class NeuralNetworkHyperHeuristic extends HyperHeuristic{

    private int labels[];    
    private double learningRate;
    private double w[][];        
           
    /**
     * Creates a new Kohonen neural network.
     * @param inputLayerSize The number of neurons in the input layer.
     * @param outputLayerSize The number of neurons in the output layer (also 
     * known as Kohonen layer).
    */
    public NeuralNetworkHyperHeuristic(int inputLayerSize, int labels[], double learningRate) {
        int i, j;
        Random generator = new Random();
        this.labels = labels;
        w = new double[this.labels.length][inputLayerSize];        
        this.learningRate = learningRate;
        for (i = 0; i < w.length; i++) {
            for (j = 0; j < w[i].length; j++) {
                w[i][j] = generator.nextDouble();
            }
        }
        type = HyperHeuristic.NEURAL;
    }
    
    /**
     * Creates a new instance of NeuralNetworkHyperHeuristic from an existing instance.
     * @param neuralNetwork The neural network that will be copied into the new instance.
    */
    public NeuralNetworkHyperHeuristic(NeuralNetworkHyperHeuristic neuralNetwork) {
        int i, j;
        double weights[][] = neuralNetwork.w;
        this.labels = neuralNetwork.labels;
        w = new double[weights.length][weights[0].length];        
        learningRate = 0.0;
        for (i = 0; i < w.length; i++) {
            for (j = 0; j < w[i].length; j++) {
                w[i][j] = weights[i][j];
            }
        }
        type = HyperHeuristic.NEURAL;
    }
    
    /**
     * Creates a new Kohonen neural network from a matrix of weights.
     * @param weigths The matrix of weights.
    */
    public NeuralNetworkHyperHeuristic(double weights[][], int labels[]) {
        int i, j;
        this.labels = labels;
        w = new double[weights.length][weights[0].length];        
        learningRate = 0.0;
        for (i = 0; i < w.length; i++) {
            for (j = 0; j < w[i].length; j++) {
                w[i][j] = weights[i][j];
            }
        }
        type = HyperHeuristic.NEURAL;
    }
    
    /**
     * Calculates the output of the network.
     * @param input The input of the network.
     * @return the index of the winning neuron.
    */
    public int getOutput(double input[]) {
        int index;
        index = getWinningNeuronIndex(input);        
        return labels[index];
    }
    
    /**
     * Trains the network with a given ArrayList and the expected output.
     * @param input The input ArrayList.
     * @param expectedOutput The expected output given the input ArrayList.
     * @return The label of the winning neuron.
    */
    public void train(double input[], int expectedOutput) {
        int index;
        index = getWinningNeuronIndex(input, expectedOutput);
        adjustWeigths(index, input);        
    }
    
    /**
     * Trains the neural network with patterns from a given file.
     * @param fileName The name of the file that contains the training patterns.
    */    
    public void train(String fileName, int cycles) {
        int i, j, k, heuristic;
        double input[];
        String rows[] = CSP.Utils.Misc.toStringArray(CSP.Utils.Files.loadFromFile(fileName));
        StringTokenizer tokens;
        input = new double[w[0].length];
        for (i = 0; i < cycles; i++) {
            for (j = 0; j < rows.length; j++) {
                tokens = new StringTokenizer(rows[j], " ,");
                for (k = 0; k < input.length; k++) {
                    input[k] = Double.valueOf(tokens.nextToken());
                }
                heuristic = Integer.valueOf(tokens.nextToken());
                train(input, heuristic);
            }
        }
    }
    
    /**
     * Returns the ArrayList of weigths of the specified Kohonen unit.
     * @param index The index of the Kohonen unit which want to get its ArrayList of weigths.
     * @return The ArrayList of weigths of the specified Kohonen unit.
    */
    public double[] getWeightArrayList(int index) {
        return w[index];
    }
    
        /**
     * Saves the SOM network to a text file.
     * @param fileName The name of the file where the neural network will be saved.
    */
    public void saveToFile(String fileName) {
        int i, j;
        String line = w.length + ", " + w[0].length + "\r\n";
        CSP.Utils.Files.saveToFile(line, fileName, false);
        for (i = 0; i < w.length; i++) {
            line = "";
            for (j = 0; j < w[i].length; j++) {
                line += w[i][j];
                if (j < w[i].length - 1) {
                    line += ", ";
                } else {
                    line += " ";
                }
            }
            line += "\r\n";
            CSP.Utils.Files.saveToFile(line, fileName, true);
        }
        line = "";
        for (i = 0; i < labels.length; i++) {
            line += labels[i]; 
            if (i < labels.length - 1) {
                    line += ", ";
                } else {
                    line += " ";
                }
        }
        CSP.Utils.Files.saveToFile(line, fileName, true);
    }
    
    /**
     * Loads a LVQ neural network from a text file.
     * @param fileName The name of the file where the network is saved.
    */
    public static NeuralNetworkHyperHeuristic loadFromFile(String fileName) {
        int i, j, inputs, outputs;
        int labels[];
        String text = CSP.Utils.Files.loadFromFile(fileName);
        String lines[] = CSP.Utils.Misc.toStringArray(text);
        StringTokenizer tokens = new StringTokenizer(lines[0], " ,");
        outputs = Integer.parseInt(tokens.nextToken());
        inputs = Integer.parseInt(tokens.nextToken());
        double w[][] = new double[outputs][inputs];
        for (i = 0; i < outputs; i++) {
            tokens = new StringTokenizer(lines[i + 1], " ,");
            for (j = 0; j < inputs; j++) {                
                w[i][j] = Double.parseDouble(tokens.nextToken().trim());                
            }            
        }
        labels = new int[outputs];
        tokens = new StringTokenizer(lines[i + 1], " ,");
        for (i = 0; i < outputs; i++) {
            labels[i] = Integer.parseInt(tokens.nextToken().trim());
        }
        return new NeuralNetworkHyperHeuristic(w, labels);
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Gets the index of the winning neuron.
     * @param input The input of the neural network.
     * @return The index of the winning neuron.
    */
    private int getWinningNeuronIndex(double input[]) {
        int i, index = -1;
        double distance, min = Double.MAX_VALUE;
        for (i = 0; i < labels.length; i++) {
            distance = distance(input, w[i]);
            if (distance < min) {
                min = distance;
                index = i;
            }
        }
        return index;
    }
    
    /**
     * Gets the index of the winning neuron that matches the expected label.
     * @param input The input of the neural network.
     * @param expectedOutput The expected output of the network.
     * @return The index of the winning neuron.
    */
    private int getWinningNeuronIndex(double input[], int expectedOutput) {
        int i, index = -1;
        double distance, min = Double.MAX_VALUE;
        for (i = 0; i < labels.length; i++) {
            distance = distance(input, w[i]);            
            if (labels[i] == expectedOutput && distance < min) {
                min = distance;
                index = i;
            }
        }
        if (index == -1) {            
            System.out.println("The class \'" + expectedOutput + "\' is not defined in the neural network.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        return index;
    }
    
    /**
     * Adjusts the weights of the neural network.
     * @param index The index of the winning neuron.
    */
    private void adjustWeigths(int index, double input[]) {
        int i;
        for (i = 0; i < w[index].length; i++) {
            w[index][i] = w[index][i] + learningRate * (input[i] - w[index][i]);            
        }
        if (learningRate > 0) {
            learningRate = learningRate - learningRate * 0.001;
        }
    }
    
    /**
     * Estimates the distance between two ArrayLists.
     * @param from The first ArrayList.
     * @param to The second ArrayList.
     * @return The distance between both ArrayLists.
    */
    private double distance(double from[], double to[]) {
        int i;
        double distance = 0;
        if (from.length != to.length) {
            System.out.println("The length of the two arrays does not match.");
            System.exit(1);
        }
        for (i = 0; i < from.length; i++) {
            distance += Math.pow(to[i] - from[i], 2);
        }
        distance = Math.sqrt(distance);
        return distance;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
