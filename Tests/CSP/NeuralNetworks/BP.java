
package CSP.NeuralNetworks;

import java.util.Random;

/** 
 * @author José Carlos Ortiz Bayliss  
 * March, 2012
 * ----------------------------------------------------------------------------
 * This class provides the functionality to create and handle back propagation
 * neural networks.
 * ----------------------------------------------------------------------------
*/

public class BP {

    NeuralLayer inputLayer;
    NeuralLayer hiddenLayers[];
    NeuralLayer outputLayer;
    
    private double learningRate = 0.5;
    private double momentum = 0.1;           
    
    /** Creates a back propagation neural network.
     * @param inputLayerSize The number of neurons in the inputLayerSize layer.
     * @param hiddenLayerSizes An array of integers with the number of neurons 
     * in each hidden layer.     
     * @param outputLayerSize The number of neurons in the outputLayerSize layer.
     * @param learningRate The learning rate of the network.
     * @param momentum The momentum to be used by the network.
    */
    public BP(int inputLayerSize, int hiddenLayerSizes[], int outputLayerSize, double learningRate, double momentum) {
        int i;
        // We create the input layer
        inputLayer = new NeuralLayer(inputLayerSize, NeuralLayer.INPUT, learningRate, momentum);
        // We create the hidden layers
        hiddenLayers = new NeuralLayer[hiddenLayerSizes.length];        
        for (i = 0; i < hiddenLayerSizes.length; i++) {
            hiddenLayers[i] = new NeuralLayer(hiddenLayerSizes[i], NeuralLayer.HIDDEN, learningRate, momentum);
        }
        // We create the output layer
        outputLayer = new NeuralLayer(outputLayerSize, NeuralLayer.OUTPUT, learningRate, momentum);        
        this.learningRate = learningRate;
        this.momentum = momentum;        
        // We make the proper connections
        hiddenLayers[0].connect(inputLayer);        
        for (i = 1; i < hiddenLayers.length; i++) {
            hiddenLayers[i].connect(hiddenLayers[i - 1]);
        }        
        outputLayer.connect(hiddenLayers[i - 1]);        
    }                
    
    /** 
     * Sets the momentum for the neural network training.
     * @param momentum The value for the momentum.
    */
    public void setMomentum(double momentum) {
        this.momentum = momentum; 
    }
    
    /**
     * Sets the learning rate for the neural network training.
     * @param learningRate The value for the learning rate.
    */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
    
    /**
     * Returns the learning rate used by the neural network for training.
     * @return The learning rate used by the neural network for training.
    */
    public double getLearningRate() {
        return learningRate;
    }
    
    /**
     * Returns the momuntum used by the neural network for training.
     * @return The momuntum used by the neural network for training.
    */
    public double getMomentum() {
        return momentum;
    }
    
    /**
     * Computes the output of the neural network given an input.
     * @param input An array of doubles with the input values.
     * @return An array of doubles with the values of the output of the neural network.
    */
    public double[] getOutput(double input[]) {
        int i;
        double netInput[], netHidden[][], netOutput[];
        netHidden = new double[hiddenLayers.length][];                
        netInput = inputLayer.computeOutput(input);
        netHidden[0] = hiddenLayers[0].computeOutput(netInput);        
        netOutput = null;
        for (i = 1; i < hiddenLayers.length; i++) {
            netHidden[i] = hiddenLayers[i].computeOutput(netHidden[i - 1]); 
        }
        netOutput = outputLayer.computeOutput(netHidden[i - 1]);    
        return netOutput;
    }
        
    /**
     * Trains the neural network with patterns from a given file.
     * @param fileName The name of the file that contains the training patterns.
    */
    /*
    public void train(String fileName) {
        int i, j, heuristic, index = -1;
        double input[], output[];
        String rows[] = Utils.Misc.toStringArray(Utils.Files.loadFromFile(fileName));
        StringTokenizer tokens;
        input = new double[Framework.getFeatures().length];
        output = new double[Framework.getHeuristics().length];
        for (i = 0; i < rows.length; i++) {
            tokens = new StringTokenizer(rows[i], " ,");
            for (j = 0; j < input.length; j++) {
                input[j] = Double.valueOf(tokens.nextToken());
            }
            heuristic = Integer.valueOf(tokens.nextToken());            
            for (j = 0; j < Framework.getHeuristics().length; j++) {
                if (heuristic == Framework.getHeuristics()[j]) {
                    index = j;
                    break;
                }
            }
            for (j = 0; j < output.length; j++) {                
                    output[j] = 0;                
            }
            output[index] = 1;     
            train(input, output);            
        }
    }
    */
    
    /**
     * Trains the neural network by using an input and an expected output.
     * @param input An array of double with the input values.
     * @param expectedOutput An array od double with the expected output for the input.
    */
    public void train(double input[], double expectedOutput[]) {
        int i;
        this.getOutput(input);
        outputLayer.computeDelta(expectedOutput);
        for (i = hiddenLayers.length - 1; i > 0; i--) {
            hiddenLayers[i].computeDelta(expectedOutput);
        }
        // We update the weight matrix
        outputLayer.updateWeights();
        for (i = hiddenLayers.length - 1; i > 0; i--) {
            hiddenLayers[i].updateWeights();
        }                            
    }
    
    /** Saves the instance of the Neural Network into a text file.
     * @param fileName Name of the file where the instance will be saved.
    */
    /*
    public void saveToFile(String fileName) {
        int i, j, k;
        File f;
        NeuralLayer inputLayer, hiddenLayer[], outputLayer;
        double weights[][];
        double threshold[];

        inputLayer = this.getInputLayer();
        hiddenLayer = this.getHiddenLayers();
        outputLayer = this.getOutputLayer();

        try {
            f = new File(fileName);
            FileWriter fw = new FileWriter(f);
            fw.write((2 + hiddenLayer.length) + " ; Number of layers\r\n");

            fw.write(inputLayer.getSize() + " ");
            for (k = 0; k < hiddenLayer.length; k++) {
                fw.write(hiddenLayer[k].getSize() + " ");
            }
            fw.write(outputLayer.getSize() + " ");
            fw.write(this.getLearningRate() + " ");
            fw.write(this.getMomentum() + " ;\r\n");

            // We save the hiddenLayerSize layers
            for (k = 0; k < hiddenLayer.length; k++) {
                weights = hiddenLayer[k].getWeights();
                threshold = hiddenLayer[k].getThreshold();
                for (i = 0; i < hiddenLayer[k].getSize(); i++) {
                    fw.write(threshold[i] + " ");
                    for (j = 0; j < weights[i].length; j++) {
                        fw.write(weights[i][j] + " ");
                    }
                    fw.write(";\r\n");
                }
            }

            // We save the outut layer
            weights = outputLayer.getWeights();
            threshold = outputLayer.getThreshold();
            for (i = 0; i < outputLayer.getSize(); i++) {
                fw.write(threshold[i] + " ");
                for (j = 0; j < weights[i].length; j++) {
                    fw.write(weights[i][j] + " ");
                }
                fw.write(";\r\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file \'" + fileName + "\'.");
            System.out.println("Exception text: " + e.toString());
            return;
        }
    }

    /** Creates an instance of Neural Network from a given file.
     * @param fileName Name of the file where the data will be read from.
     * @return A new instance of Neural Network or null if an error occurs.
    */
    /*
    public static BackPropagation loadFromFile(String fileName) {
        File file = new File(fileName);
        boolean readingThreshold;
        int size = (int) file.length(), i, j, chars_read = 0, layers = 0, k;
        double params[] = new double[7];
        double weights[][];
        double threshold[];

        FileReader in;
        String rows[];
        StringBuffer temp = new StringBuffer();
        BackPropagation network;
        NeuralLayer hiddenLayer[], outputLayer;                
        
        try {
            in = new FileReader(file);
            char[] data = new char[size];
            while (in.ready()) {
                chars_read += in.read(data, chars_read, size - chars_read);
            }
            in.close();
            
            //We convert the contents of the file into rows of strings
            rows = Utils.Misc.toStringArray(new String(data, 0, chars_read));            
            //----------------- Variables -----------------

            // The number of layers is read
            for (j = 0; (rows[0].charAt(j) != ' '); j++) {
                temp.append(rows[0].charAt(j));
            }            
            layers = Integer.valueOf(temp.toString());
            temp.delete(0, temp.length());
            //System.out.println("Number of layers: " + layers);
            // The parameters of the neural network are read
            for (k = 0, j = 0; rows[1].charAt(j) != ';'; j++) {
                if (rows[1].charAt(j) == ' ') {
                    params[k++] = Double.valueOf(temp.toString());
                    temp.delete(0, temp.length());
                } else {
                    temp.append(rows[1].charAt(j));
                }
            }            
            if (layers == 3) { 
                System.out.println("Neurons in inputLayerSize layer: " + (int) params[0]);
                System.out.println("Neurons in hiddenLayerSize layer: " + (int) params[1]);
                System.out.println("Neurons in outputLayerSize layer: " + (int) params[2]);
                System.out.println("Learing rate: " + params[3]);
                System.out.println("Momentum: " + params[4]);
                network = new BackPropagation((int) params[0], (int) params[1], (int) params[2], params[3], params[4]);
                network.setLearningRate(params[3]);
                network.setMomentum(params[4]);

                hiddenLayer = network.getHiddenLayers();
                weights = new double[(int) params[1]][(int) params[0]];
                threshold = new double[(int) params[1]];

                // Each threshold and weight are read
                temp.setLength(0);
                for (i = 0; i < hiddenLayer[0].getSize(); i++) {
                    k = 0;
                    readingThreshold = true;
                    for (j = 0; rows[i + 2].charAt(j) != ';'; j++) {
                        if (rows[i + 2].charAt(j) != ' ') {
                            temp.append(rows[i + 2].charAt(j));
                        } else {
                            if (readingThreshold) {
                                readingThreshold = false;
                                threshold[i] = Double.valueOf(temp.toString());
                            } else {
                                weights[i][k++] = Double.valueOf(temp.toString());
                            }
                            temp.delete(0, temp.length());
                        }
                    }
                }
                hiddenLayer[0].setThreshold(threshold);
                hiddenLayer[0].setWeights(weights);

                outputLayer = network.getOutputLayer();
                weights = new double[(int) params[2]][(int) params[1]];
                threshold = new double[(int) params[2]];

                // Each threshold and weight are read
                temp.setLength(0);
                for (i = 0; i < outputLayer.getSize(); i++) {
                    k = 0;
                    readingThreshold = true;
                    for (j = 0; rows[i + hiddenLayer[0].getSize() + 2].charAt(j) != ';'; j++) {
                        if (rows[i + hiddenLayer[0].getSize() + 2].charAt(j) != ' ') {
                            temp.append(rows[i + hiddenLayer[0].getSize() + 2].charAt(j));
                        } else {
                            if (readingThreshold) {
                                readingThreshold = false;
                                threshold[i] = Double.valueOf(temp.toString());
                            } else {
                                weights[i][k++] = Double.valueOf(temp.toString());
                            }
                            temp.delete(0, temp.length());
                        }
                    }
                }
                outputLayer.setThreshold(threshold);
                outputLayer.setWeights(weights);

            } else {
                System.out.println("Neurons in inputLayerSize layer: " + (int) params[0]);
                System.out.println("Neurons in first hiddenLayerSize layer: " + (int) params[1]);
                System.out.println("Neurons in second hiddenLayerSize layer: " + (int) params[2]);
                System.out.println("Neurons in outputLayerSize layer: " + (int) params[3]);
                System.out.println("Learing rate: " + params[4]);
                System.out.println("Momentum: " + params[5]);
                network = new BackPropagation((int) params[0], (int) params[1], (int) params[2], (int) params[3], params[4], params[5]);
                network.setLearningRate(params[4]);
                network.setMomentum(params[5]);

                hiddenLayer = network.getHiddenLayers();
                weights = new double[(int) params[1]][(int) params[0]];
                threshold = new double[(int) params[1]];

                // Each threshold and weight are read
                temp.setLength(0);
                for (i = 0; i < hiddenLayer[0].getSize(); i++) {
                    k = 0;
                    readingThreshold = true;
                    for (j = 0; rows[i + 2].charAt(j) != ';'; j++) {
                        if (rows[i + 2].charAt(j) != ' ') {
                            temp.append(rows[i + 2].charAt(j));
                        } else {
                            if (readingThreshold) {
                                readingThreshold = false;
                                threshold[i] = Double.valueOf(temp.toString());
                            } else {
                                weights[i][k++] = Double.valueOf(temp.toString());
                            }
                            temp.delete(0, temp.length());
                        }
                    }
                }
                hiddenLayer[0].setThreshold(threshold);
                hiddenLayer[0].setWeights(weights);

                weights = new double[(int) params[2]][(int) params[1]];
                threshold = new double[(int) params[2]];

                // Each threshold and weight are read
                temp.setLength(0);
                for (i = 0; i < hiddenLayer[1].getSize(); i++) {
                    k = 0;
                    readingThreshold = true;
                    for (j = 0; rows[i + hiddenLayer[0].getSize() + 2].charAt(j) != ';'; j++) {
                        if (rows[i + hiddenLayer[0].getSize() + 2].charAt(j) != ' ') {
                            temp.append(rows[i + hiddenLayer[0].getSize() + 2].charAt(j));
                        } else {
                            if (readingThreshold) {
                                readingThreshold = false;
                                threshold[i] = Double.valueOf(temp.toString());
                            } else {
                                weights[i][k++] = Double.valueOf(temp.toString());
                            }
                            temp.delete(0, temp.length());
                        }
                    }
                }
                hiddenLayer[1].setThreshold(threshold);
                hiddenLayer[1].setWeights(weights);
                
                outputLayer = network.getOutputLayer();
                weights = new double[(int) params[3]][(int) params[2]];
                threshold = new double[(int) params[3]];

                // Each threshold and weight are read
                temp.setLength(0);
                for (i = 0; i < outputLayer.getSize(); i++) {
                    k = 0;
                    readingThreshold = true;
                    for (j = 0; rows[i + hiddenLayer[0].getSize() + hiddenLayer[1].getSize() + 2].charAt(j) != ';'; j++) {
                        if (rows[i + hiddenLayer[0].getSize() + hiddenLayer[1].getSize() + 2].charAt(j) != ' ') {
                            temp.append(rows[i + hiddenLayer[0].getSize() + hiddenLayer[1].getSize() + 2].charAt(j));
                        } else {
                            if (readingThreshold) {
                                readingThreshold = false;
                                threshold[i] = Double.valueOf(temp.toString());
                            } else {
                                weights[i][k++] = Double.valueOf(temp.toString());                                
                            }
                            temp.delete(0, temp.length());
                        }
                    }
                }
                outputLayer.setThreshold(threshold);
                outputLayer.setWeights(weights);
            }            
            return network;
        } catch (Exception e) {
            System.out.println("The neural network coundn't be created from file \'" + fileName + "\'.");
            System.out.println("Exception text: " + e.toString());
            System.exit(1);
            return null;
        }
    }    
    */        
    
    private class NeuralLayer {

        private double weights[][];
        private double pastWeights[][];
        private int size;
        private double learningRate = 0.5;
        private double momentum = 0.1;
        private double net[];
        private double output[];
        private double threshold[];
        private double pastThreshold[];
        private double delta[];
        private boolean connected = false;
        private int layerType;
        private NeuralLayer nextLayer, previousLayer;
        public static final int INPUT = 0;
        public static final int HIDDEN = 1;
        public static final int OUTPUT = 2;

        public NeuralLayer(int neurons, int layerType, double learningRate, double momentum) {
            this.layerType = layerType;
            this.learningRate = learningRate;
            this.momentum = momentum;
            nextLayer = null;
            previousLayer = null;
            size = neurons;
            switch (layerType) {
                case INPUT:
                    connected = true;
                    break;
                case HIDDEN:
                    connected = false;
                    break;
                case OUTPUT:
                    connected = false;
                    break;
            }
        }

        /**
         * 
        */
        private void setNextLayer(NeuralLayer layer) {
            nextLayer = layer;
        }

        /** 
         * Returns the number of neurons in the layer.
         * @return The number of neurons in the layer.
        */
        private int getSize() {
            return size;
        }

        /**
         * Sets the weigths of the connections of the layer with the previous one.
         * weights An array of doubles with the weights of the connections.
        */
        private void setWeights(double weights[][]) {
            int i, j;
            // We do not set the weights if it is an input layer (there is no previous layer)
            if (this.layerType == NeuralLayer.INPUT) {
                return;
            }
            for (i = 0; i < size; i++) {
                for (j = 0; j < previousLayer.getSize(); j++) {
                    this.weights[i][j] = weights[i][j];
                }
            }
        }

        /** 
         * Returns the weight matrix of the layer.
         * @return The weight matrix of the layer.
        */
        private double[][] getWeights() {
            return weights;
        }

        /**
         * Sets the threshold values for the layer.
         * @param threshold An array of doubles with the values of the threshold.
        */
        private void setThreshold(double threshold[]) {
            this.threshold = threshold;
        }

        /** Returns the threshold of the layer.
         * @return The threshold of the layer.
         */
        private double[] getThreshold() {
            return threshold;
        }

        /** 
         * Connects the current layer with a layer specified in the argument.
         * @param layer The layer that the current layer will be connected to.
        */
        private void connect(NeuralLayer layer) {
            int i, j;
            Random generator = new Random();
            connected = true;
            layer.setNextLayer(this);
            previousLayer = layer;
            threshold = new double[size];
            pastThreshold = new double[size];
            weights = new double[size][layer.getSize()];
            pastWeights = new double[size][layer.getSize()];
            for (i = 0; i < size; i++) {
                //threshold[i] = generator.nextDouble();
                threshold[i] = generator.nextGaussian();
                pastThreshold[i] = 0;
                for (j = 0; j < layer.getSize(); j++) {
                    //weights[i][j] = generator.nextDouble();
                    weights[i][j] = generator.nextGaussian();
                    pastWeights[i][j] = 0;
                }
            }
        }

        /**
         * Returns the error of a given neuron.
         * @return The error of a given neuron.
        */
        private double getDelta(int index) {
            return delta[index];
        }

        /** 
         * Computes the output of the current layer for the given net.
         * @param input An array of doubles which represents the input of the layer.
         * @return An array of doubles with the output of the layer.
        */
        public double[] computeOutput(double input[]) {
            int i;
            net = computeNet(input);
            if (!connected) {
                return null;
            }
            output = new double[size];
            switch (layerType) {
                case INPUT:
                    for (i = 0; i < size; i++) {
                        output[i] = input[i];
                    }
                    break;
                case HIDDEN:
                case OUTPUT:
                    for (i = 0; i < size; i++) {
                        output[i] = transferenceFunction(net[i]);
                    }
                    break;
            }
            return output;
        }

        /** 
         * Computes the error of the layer with respect to the expected output.
         * @param expectedOutput The array of doubles that specifies the expected 
         * output of the layer.
         * @return The error of the layer with respect to the expected output.
        */
        public double[] computeDelta(double expectedOutput[]) {
            int i, j;
            //System.out.println("Ya voy a obtener los valores de delta...");        
            double w[][];
            double sum;
            delta = new double[size];
            switch (layerType) {
                case HIDDEN:
                    w = nextLayer.getWeights();
                    for (i = 0; i < size; i++) {
                        sum = 0;
                        for (j = 0; j < nextLayer.getSize(); j++) {
                            sum = nextLayer.getDelta(j) * (w[j][i]);
                        }
                        delta[i] = transferenceFunctionDerivate(net[i]) * sum;
                    }
                    break;
                case OUTPUT:
                    for (i = 0; i < delta.length; i++) {
                        delta[i] = (expectedOutput[i] - output[i]) * transferenceFunctionDerivate(net[i]);
                    }
                    break;
            }
            return delta;
        }

        /**
         * Updates the weights of the layer.
        */
        private void updateWeights() {
            int i, j;
            for (i = 0; i < size; i++) {
                pastThreshold[i] = pastThreshold[i] * momentum;
                threshold[i] = threshold[i] + delta[i] * learningRate + pastThreshold[i] * momentum;
                for (j = 0; j < weights[i].length; j++) {
                    pastWeights[i][j] = pastWeights[i][j] * momentum;
                    weights[i][j] = weights[i][j] + learningRate * previousLayer.getOutput(j) * delta[i] + pastWeights[i][j] * momentum;
                }
            }
        }
        
        private double getOutput(int index) {
            return output[index];
        }

        /** 
         * Computes the output of the current layer for the given input before being
         * passed through the transference function.
         * @param input An array of doubles which represents the input of the layer.
         * @return An array of doubles with the output of the layer.
        */
        private double[] computeNet(double input[]) {
            int i, j;
            if (!connected) {
                return null;
            }
            double output[] = new double[size];
            net = new double[size];
            switch (layerType) {
                case INPUT:
                    for (i = 0; i < size; i++) {
                        output[i] = input[i];
                    }
                    break;
                case HIDDEN:
                case OUTPUT:                    
                    for (i = 0; i < size; i++) {
                        for (j = 0; j < weights[i].length; j++) {                            
                            output[i] = weights[i][j] * input[j] + output[i];
                        }
                        output[i] = threshold[i] + output[i];
                        net[i] = output[i];
                    }
                    break;
            }
            return output;
        }

        /** 
         * Implements the sigmoidal function.
         * @param x The value of x to evaluate.
         * @return The value for the sigmoidal function.
        */
        private double transferenceFunction(double x) {
            return 1 / (1 + Math.exp(-1 * x));
        }

        /** 
         * Implements the derivate of the sigmoidal function.
         * @param x The value to evaluate.
         * @return the value of the derivate of the sigmoidal function.
        */
        private double transferenceFunctionDerivate(double x) {
            return transferenceFunction(x) * (1 - transferenceFunction(x));
        }
        
    }
    
}

