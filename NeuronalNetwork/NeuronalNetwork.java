package NeuronalNetwork;

import java.util.Random;
import java.lang.Math;

/**
 * Represents a Neuronal Network
 */
public class NeuronalNetwork {
    Neuron[][] cells = new Neuron[0][];
    //[Layer] [Neuronen im Layer] [Neuronen im darauffolgenden Layer]
    double[][][] initialWeight = new double[0][][];
    double[][][] weights = new double[0][][];
    double[][] results;
    public double[][] resultsAll;
    double bias;
    double biasWeight;


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
        initialWeight = new double[layers - 1][][];

        for (int i = 0; i < layers - 1; i++) {

            int neurons = cells[i].length;
            initialWeight[i] = new double[neurons][];

            for (int j = 0; j < neurons; j++) {

                int neuronsNextLayer = cells[i+1].length;
                initialWeight[i][j] = new double[neuronsNextLayer];

                for (int k = 0; k < neuronsNextLayer; k++) {
                    if (j == (neurons - 1)) {
                        initialWeight[i][j][k] = (k < (neuronsNextLayer - 1)) ? 0.0 : biasWeight;
                    } else {
                        initialWeight[i][j][k] = -1 + 2 * rnd.nextDouble();
                    }
                }
            }
        }
        weights = initialWeight;
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
            results[0][i] = (i == data.length) ? bias : data[i];
        }

        for (int i = 0; i < n; i++) //iterate layers
        {
            //System.out.println("i: "+i);
            for (int j = 0; j < results[i+1].length; j++) //iterate neurons of the layer
            {
                double sum = 0;

                if (i < n -1 && j == (results[i+1].length -1)){
                    sum = 1;
                } else {
                    for (int k = 0; k < results[i].length; k++) //iterate the weights
                    {
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
     * network computes the given data
     * @param actuals actual datas
     * @return compute results
     */
    public double computeError(double[] expected, double[] actuals){
        double temp = 0;
        int n = expected.length;

        for(int i = 0; i < n; i++){
            temp += Math.pow((expected[i] - actuals[i]), 2);
        }

        return temp/n;//nicht eher *0.5 statt /n
    }

    public double[] computeErrorAll(double [][] actuals) {
        int n = resultsAll.length;
        double[] errors = new double[n];

        for (int i = 0; i < n; i++) {
            errors[i] = computeError(resultsAll[i], actuals[i]);
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
    public void setUnitType(int layer, int neuron, String function) {
        if(layer > cells.length || neuron > cells[layer].length)
            throw new IllegalArgumentException("Layer/Neuron index does not match Network structure!");

        cells[layer][neuron].setUnitType(function);
    }

    /**
     * Sets the unit type
     * @param layer
     * @param neuron
     * @param function
     * @param threshold offset of function
     */
    public void setUnitType(int layer, int neuron, String function, double threshold) {
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
        result.append("\n##Neuron types\n");

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                result.append(cells[i][j].toString());
            }

            result.append("\n");
        }

        return result.toString();
    }

}