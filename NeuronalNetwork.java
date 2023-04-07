import java.util.Random;

/**
 * Represents a Neuronal Network
 */
public class NeuronalNetwork
{
    Neuron[][] cells = new Neuron[0][];

    //[Layer] [Neuronen im Layer] [Neuronen im darauffolgenden Layer]
    double[][][] weights = new double[0][][];


    /**
     * create the Neuronal network with the given structure
     * @param structure network structure (e.g. {2,3,4,5}) (max length 5?? weil setWeights[][][]???)
     */
    public void create(int[] structure)
    {
        cells = new Neuron[structure.length][];

        for(int i = 0; i < structure.length; i++)
        {
            cells[i] = new Neuron[structure[i]];

            for(int j = 0; j < structure[i]; j++)
                cells[i][j] = new Neuron();
        }

        createRandomWeights(structure, true);
    }

    private void createRandomWeights(int[] structure, boolean useBias)
    {
        Random rnd = new Random();

        int layers = structure.length;

        weights = new double[layers -1][][];

         for(int i = 0; i < layers -1; i++)
         {
             int neurons = structure[i] +1; //+1 für BIAS neuron
             weights[i] = new double[neurons][];

             for(int j = 0; j < neurons; j++)
             {
                 int neuronsNextLayer = structure[i+1];
                 weights[i][j] = new double[neuronsNextLayer];

                 for(int k = 0; k < neuronsNextLayer; k++)
                 {
                     if(!useBias && k == neuronsNextLayer-1)
                     {
                         weights[i][j][k] = 0.0;
                     }
                     else
                     {
                         double randomWeight = -1 + (1 - (-1)) * rnd.nextDouble();
                         weights[i][j][k] = randomWeight;
                     }
                 }
             }
         }
    }

    /**
     * Set the weights
     * @param weights [ Layer ] [ Neuronen im Layer ] [ Neuronen im darauffolgenden Layer ]
     */
    public void setWeights(double[][][] weights)
    {
        this.weights = weights;
    }

    /**
     * Retrieves the weights used in the neuronal network
     * @return weights
     */
    public double[][][] getWeights()
    {
        return weights;
    }

    /**
     * Sets the unit type
     * @param layer
     * @param neuron
     * @param function
     * @param threshold
     */
    public void setUnitType(int layer, int neuron, String function, double threshold)
    {
        cells[layer][neuron].setUnitType(function, threshold);
    }

    /**
     * network computes the given data
     * @param data data to compute
     * @return compute results
     */
    public double[] compute(double[] data)
    {
        double[][] results = new double[cells.length][];

        //fill in input data
        for(int i = 0; i < cells[0].length; i++)
        {
            results[i] = new double[cells[i].length];
            results[0][i] = cells[0][i].compute(data[i]);
        }

        //Compute Layer 1 (second) to end
        for(int i = 0; i < cells.length-1; i++)//iterate trough layers
        {
            for(int j = 0; j < cells[i+1].length; j++)//iterate trough neurons of the layer
            {
                double sum = 0;
                for (int k = 0; k < cells[i].length; k++)//iterate trough the weights
                {
                    sum += results[i][k] * weights[i][k][j];
                }

                results[i+1][j] = cells[i+1][j].compute(sum);
            }
        }

        return results[results.length];
    }

    /**
     * network computes the given data
     * @param data data to compute
     * @return compute results
     */
    public double[][] computeAll(double[][] data)
    {
        double[][] results = new double[data.length][];

        for(int i = 0; i < data.length; i++)
        {
            results[i] = compute(data[i]);
        }

        return results;
    }

    /**
     * toString
     * @return nn output string
     */
    @Override
    public String toString()
    {
        String result = "";

        for(int i = 0; i < cells.length; i++)
        {
            for(int j = 0; j < cells[i].length; j++)
            {
                result += cells[i][j].toString();
            }

            result += "\n";
        }

        return result;
    }
}
