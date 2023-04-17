package NeuronalNetwork;

import java.util.Random;

/**
 * Represents a Neuronal Network
 */
public class NeuronalNetwork {
    Neuron[][] cells = new Neuron[0][];
    //[Layer] [Neuronen im Layer] [Neuronen im darauffolgenden Layer]
    double[][][] weights = new double[0][][];
    double[][] sums;
    double[][] results;
    int[] structure;
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
        this.structure = structure;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }

    private void createNetworkStructure(int[] structure) {
        cells = new Neuron[structure.length][];
        for (int i = 0; i < structure.length; i++) {

            int neuronCount = (i < structure.length - 1) ? structure[i] + 1 : structure[i];
            cells[i] = new Neuron[neuronCount];

            for (int j = 0; j < neuronCount; j++) {
                cells[i][j] = new Neuron();
                if ((i < structure.length - 1) && (j >= neuronCount - 1)) {
                    cells[i][j].setUnitType("id");
                }
            }
        }
        createRandomWeights(structure);
    }

    public void initializeRandomWeights() {
        createNetworkStructure(structure);
    }


    private void createRandomWeights(int[] structure) {
        Random rnd = new Random();
        int layers = structure.length;
        weights = new double[layers - 1][][];

        for (int i = 0; i < layers - 1; i++) {

            int neurons = cells[i].length;
            weights[i] = new double[neurons][];

            for (int j = 0; j < neurons; j++) {

                int neuronsNextLayer = cells[i+1].length;
                weights[i][j] = new double[neuronsNextLayer];

                for (int k = 0; k < neuronsNextLayer; k++) {
                    if (j == (neurons - 1)) {
                        weights[i][j][k] = (k < (neuronsNextLayer - 1)) ? 0.0 : biasWeight;
                    } else {
                        weights[i][j][k] = -1 + 2 * rnd.nextDouble();
                    }
                }
            }
        }
    }


    /**
     * Set the cells
     * @param cells [ Layer ] [ Neuronen im Layer ]
     */

    public void setCells(Neuron[][] cells) {
        this.cells = cells;
    }

    /**
     * Retrieves the cells used in the neuronal network
     * @return cells
     */
    public Neuron[][] getCells() {
        return cells;
    }

    /**
     * Set the weights
     * @param weights [ Layer ] [ Neuronen im Layer ] [ Neuronen im darauffolgenden Layer ]
     */
    public void setWeights(double[][][] weights) {
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
     * Sets the unit type
     * @param layer
     * @param neuron
     * @param function
     */
    public void setUnitType(int layer, int neuron, String
            function) {
        cells[layer][neuron].setUnitType(function);
    }
    public void setUnitType(int layer, int neuron, String
            function, double threshold) {
        cells[layer][neuron].setUnitType(function, threshold);
    }

    /**
     * network computes the given data
     * @param data data to compute
     * @return compute results
     */
    public double[] compute(double[] data) {
        sums = new double[cells.length][];
        results = new double[cells.length][];

        //making results of each layers
        for (int i = 0; i < cells.length; i++) {
            sums[i] = new double[cells[i].length];
            results[i] = new double[cells[i].length];
        }

        //fill in input data/bias
        for (int i = 0; i <= data.length; i++) {
            sums[0][i] = (i == data.length) ? bias : data[i];
            results[0][i] = (i == data.length) ? bias : data[i];
        }


        for (int i = 0; i < results.length - 1; i++) //iterate layers
        {
            for (int j = 0; j < results[i+1].length; j++) //iterate neurons of the layer
            {
                double sum = 0;
                for (int k = 0; k < results[i].length; k++) //iterate the weights
                {
                    sum += results[i][k] * weights[i][k][j];
                }
                sums[i + 1][j] = sum;
                results[i + 1][j] = cells[i + 1][j].compute(sum);
            }
        }
        return results[results.length - 1];
    }

    /**
     * network computes the given data
     * @param data data to compute
     * @return compute results
     */
    public double[][] computeAll(double[][] data) {
        double[][] resultsAll = new double[data.length][];

        for (int i = 0; i < data.length; i++) {
            resultsAll[i] = compute(data[i]);
        }

        return resultsAll;
    }

    /**
     * toString
     * @return nn output string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                result.append(cells[i][j].toString());
            }

            result.append("\n");
        }

        return result.toString();
    }

}