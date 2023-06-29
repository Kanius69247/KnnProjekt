package NeuronalNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Math;

/**
 * Represents a Neuronal Network
 */
public class NeuronalNetwork {
    private Neuron[][] cells = new Neuron[0][];
    //[Layer] [Neuronen im Layer] [Neuronen im darauffolgenden Layer]
    private double [][][]initialWeight = new double[0][][];
    private double[][][] weights = new double[0][][];
    private double[][] results;
    private double[][] resultsAll;
    private double bias;
    private double biasWeight;
    private ArrayList<Double> errors = new ArrayList<Double>();

    /**
     * Initializes a new instance of NeuronalNetwork
     */
    public NeuronalNetwork() {
        this.bias = 0;
        this.biasWeight = 1;
    }

    /**
     * create the Neuronal network with the given structure
     * @param structure network structure (e.g. {2,3,4,5})
     */
    public void create(int[] structure) {
        createNetworkStructure(structure);
        createRandomWeights(structure);
    }

    /**
     * Creating new neuron network by given number of layers and neurons
     * @param structure number of neurons in each layers in array (e.g. {2,3,4,5})
     */
    private void createNetworkStructure(int[] structure) {
        cells = new Neuron[structure.length][];
        for (int i = 0; i < structure.length; i++) {

            int neuronCount = (i < structure.length - 1) ? structure[i] + 1 : structure[i];
            cells[i] = new Neuron[neuronCount];

            for (int j = 0; j < neuronCount; j++) {
                cells[i][j] = new Neuron();
            }
        }
    }

    /**
     * Setting weight of the network with random number (-1 ~ 1)
     * @param structure number of neurons in each layers (e.g. {2,3,4,5})
     */
    private void createRandomWeights(int[] structure) {
        Random rnd = new Random();
        int layers = structure.length;
        this.initialWeight = new double[layers - 1][][];

        for (int i = 0; i < layers-1; i++) {

            int neurons = this.cells[i].length;
            this.initialWeight[i] = new double[neurons][];

            for (int j = 0; j < neurons; j++) {

                //No weights to bias neuron (cells[i+1].length -1;), when next layer is output there is no bias neuron (cells[i+1].length)
                int neuronsNextLayer = (i == layers-2) ? this.cells[i+1].length : this.cells[i+1].length -1;
                this.initialWeight[i][j] = new double[neuronsNextLayer];

                for (int k = 0; k < neuronsNextLayer; k++) {
                    this.initialWeight[i][j][k] = -1 + 2 * rnd.nextDouble();
                }
            }
        }
        this.weights = this.initialWeight;
    }

    /**
     * network computes the given data until n-th line
     * @param data data to compute
     * @param n compute till n-th result
     * @return compute results
     */
    public double[] compute(double[] data, int n) throws IllegalArgumentException {
        if(data.length > cells[0].length || n > cells.length)
            throw new IllegalArgumentException("Data to compute does not match network structure!");

        double[][] sums = new double[n+1][];
        results = new double[n+1][];

        //making results of each layers
        for (int i = 0; i < n+1; i++) {
            sums[i] = new double[cells[i].length];
            results[i] = new double[cells[i].length];
        }

        //fill in input data/bias
        for (int i = 0; i <= data.length; i++) {
            sums[0][i] = (i == data.length) ? bias : data[i];
            results[0][i] = (i == data.length) ? bias : cells[0][i].compute(data[i]);
        }

        for (int i = 0; i < n; i++) {//iterate layers
            //System.out.println("i: "+i);
            for (int j = 0; j < results[i+1].length; j++) {//iterate neurons of the layer
                double sum = 0;//bias;

                if (i < n -1 && j == (results[i+1].length -1)){
                    sum = bias;
                } else {
                    for (int k = 0; k < results[i].length; k++) {//iterate the weights
                        sum += results[i][k] * weights[i][k][j];
                    }
                }

                sums[i+1][j] = sum;
                results[i+1][j] += cells[i+1][j].compute(sum);
            }
        }
        return results[results.length - 1];
    }

    /**
     * network computes the given data
     * @param data data to compute
     * @return compute results
     */
    public double[] compute(double[] data) {
        return compute(data, weights.length);
    }

    /**
     * computes quadratic error value of calculated and expected output
     * @param expected expected Output
     * @param actual the by the network calculated output
     * @return quadratic error between expected and actual
     */
    public double computeError(double[] expected, double[] actual){
        double sum = 0;
        double n = expected.length;

        for(int i = 0; i < expected.length; i++){
            sum += (1/n) * Math.pow((expected[i] - actual[i]), 2);//expected[i]
        }

        //return sum/expected.length;//nicht eher *0.5 statt /n
        return sum;
    }

    /**
     * Computes the quadratic error value according to computeError for multiple datasets
     * @param expected the expected output
     * @return errors of given data
     */
    public double[] computeErrorAll(double [][] expected) {
        int n = resultsAll.length;
        double[] errors = new double[n];

        for (int i = 0; i < n; i++) {
            errors[i] = computeError(resultsAll[i], expected[i]);
        }

        return errors;
    }

    /**
     * network computes the given data
     * @param samples multiple dataset to compute
     * @return compute results
     */
    public double[][] computeAll(double[][] samples) {
        double[][] resultsAll = new double[samples.length][];

        for (int i = 0; i < samples.length; i++) {
            resultsAll[i] = compute(samples[i]);
        }

        this.resultsAll = resultsAll;
        return this.resultsAll;
    }

    /**
     * Trains the neuronal Network via backpropagation
     * @param input input data
     * @param expected expected output data
     */
    public List<TrainingResult> train(double[] input, double[] expected, int maxEpoche) {
        List<TrainingResult> trainResults = new ArrayList<>();
        errors = new ArrayList<>();  //refresh errors list
        double initLearningRate = 0.000001;
        double learningRate = initLearningRate;
        double targetError = 0.00001;
        double error = 1.0;
        double lastError = 1.0;
        int epoche = 0;

        double[] InitResults = compute(input); //Calculate input
        error = computeError(expected, InitResults); //calculate error value (sum of all errors from output)
        lastError = computeError(expected, InitResults); //calculate error value (sum of all errors from output)

        while(error > targetError && epoche < maxEpoche){

            double[] results = compute(input); //Calculate input
            error = computeError(expected, results); //calculate error value (sum of all errors from output)
            errors.add(error);

            double diff = lastError - error;

            if(epoche > 25){
                if(0<diff && diff < learningRate) { //if reduced error is too low
                    learningRate = learningRate * 1.1;
                } else {
                    learningRate = initLearningRate;
                }
            }

            if(error < lastError)
                learningRate *= 1.1;//Increase Learning rate when error gets lower
            else if(error > lastError) {
                weights = initialWeight; //Reset weights to previous if error is getting bigger
                learningRate *= 0.5;//Decrease Learning when error gets bigger
            }

            lastError = error;
            backPropagation(expected, learningRate);
            epoche++;
            trainResults.add(new TrainingResult(epoche,error));
        }

        return trainResults;
    }

    /**
     * Changes the weights of the network according to the calculated error value
     * @param expectedOutput expected results of each neuron in each layer
     * @param learningRate rate of changing the weights
     */
    public void backPropagation(double[] expectedOutput, double learningRate) {
        double [][] neuronErrors = calcNeuronErrors(expectedOutput);
        initialWeight = weights;
        //Iterate backwards through neurons and adapt weights according to neuron errors
        for(int i = weights.length-1; i >= 0; i--) { //iterate through all layers but input (backwards)

            for(int j = 0; j < weights[i].length; j++) { //iterate through neurons (forward)

                for(int k = 0; k < weights[i][j].length; k++) {//iterate trough weights of current neuron

                    // System.out.println("Changing Weight at i-1:"+(i-1)+" j:"+j+" k:"+k);
                    //calculate the new Weight value
                    double deltaWeight = learningRate * this.results[i][j] * neuronErrors[i+1][k];
                    weights[i][j][k] -= deltaWeight;
                }
            }
        }
    }


    /**
     * Calculates the Error Value of each Neuron by using the Derivative on each neuron's output
     * @param expectedOutput expected Output of the neurons from output layer
     * @return Array matching network structure with error value to each neuron
     */
    private double[][] calcNeuronErrors(double[] expectedOutput) {
        double[][] neuronErrors = new double[cells.length][];

        //First backwards iteration to calculate all Error values of all neurons
        for (int i = cells.length - 1; i >= 0; i--) {//iterate through all layers but input (backward)

            neuronErrors[i] = new double[cells[i].length];//Initialize Array of neuron Errors
            for (int j = 0; j < cells[i].length; j++) {//iterate through neurons (forward)

                double neuronError = 0.0;
                if (i == cells.length - 1) {//If layer of Output neuron f'(net) * error

                    neuronError = cells[i][j].getDerivative() * (this.results[i][j] - expectedOutput[j]);
                    //System.out.println("cells["+i+"]["+j+"].getDerivative() "+ cells[i][j].getDerivative() +" * (this.results["+i+"]["+j+"] "+this.results[i][j]+" - expectedOutput["+j+"] "+expectedOutput[j]+") = "+neuronError);
               }
                else {//if not output layer f'(net) * sum (Sk Wjk)
                    double sum = 0.0; //Summe (der neuronen fehler * Ausgangs gewichte des momentanen neuron)
                    //iterate through i+1. layer
                    //neuron next layer neuronCount when next Layer is outputLayer else neuronCount -1 because bias has no weights
                    int neuronsNextLayer = i == cells.length - 2 ? cells[i + 1].length : cells[i + 1].length-1;

                    for (int k = 0; k < neuronsNextLayer; k++) {//for (int k = 0; k < cells[i + 1].length-1; k++) {
                        //sum up NeuronError * weight to neuron
                        sum += neuronErrors[i + 1][k] * weights[i][j][k];
                    }

                    neuronError = cells[i][j].getDerivative() * sum;
                   // System.out.println("cells["+i+"]["+j+"].getDerivative() "+cells[i][j].getDerivative()+" * sum "+sum + " = "+neuronError);
                }
               // System.out.println("Calc neuron error for neuron["+i+"]["+j+"]");
                neuronErrors[i][j] = neuronError;
            }
        }
        return neuronErrors;
    }

    /**
     * Initializing weights to first weights when it was created
     */
    public void initializeRandomWeights() {
        weights = initialWeight;
    }

    /**
     * Sets the unit type
     * @param layer
     * @param neuron
     * @param function
     */
    public void setUnitType(int layer, int neuron, UnitType function) {
        if(layer > cells.length || neuron > cells[layer].length)
            throw new IllegalArgumentException("Layer/Neuron index does not match Network structure!");

        cells[layer][neuron].setUnitType(function);
    }

    /**
     * Returns the error-values from all training steps in the last training process
     * @return errors
     */
    public ArrayList<Double> getErrors() {
        return errors;
    }

    /**
     * Sets the unit type
     * @param layer
     * @param neuron
     * @param function
     * @param threshold offset of function
     */
    public void setUnitType(int layer, int neuron, UnitType function, double threshold) {
        if(layer > cells.length || neuron > cells[layer].length)
            throw new IllegalArgumentException("Layer/Neuron index does not match Network structure!");

        cells[layer][neuron].setUnitType(function, threshold);
    }

    /**
     * Setting bias
     */
    public void setBias(double bias) {
        this.bias = bias;
    }

    /**
     * Set the weights
     * @param weights [ Layer ] [ Neuronen im Layer ] [ Neuronen im darauffolgenden Layer ]
     */
    public void setWeights(double[][][] weights) {
        if(weights.length > this.weights.length) //Still possible that arrays in 2. oder 3. Dimension have a wrong length
            throw new IllegalArgumentException("size of weights does not match!");

        this.weights = weights;
    }

    /**
     * Retrieves the weights used in the neuronal network
     * @return weights
     */
    public double[][][] getWeights() {
        return weights;
    }

    /**
     * toString
     * @return nn output string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n##Neuron types\n(Input)\n");

        for (int i = 0; i < cells.length; i++) {
            if(i == cells.length - 1) {
                result.append("(Output)\n");
            }
            for (int j = 0; j < cells[i].length; j++) {
                result.append("Layer:"+i+" Neuron:"+j+" "+cells[i][j].toString());

                if(i < cells.length-1)
                    result.append(" Weights: " +Arrays.toString(weights[i][j])+"\n");
                else
                    result.append("\n");
            }

            result.append("\n");
        }

        return result.toString();
    }
}