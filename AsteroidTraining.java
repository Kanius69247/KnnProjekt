import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsteroidTraining {
    private static NeuronalNetwork nn;
    private static int[] structure;
    private static double[][] trainingData;
    private static double[][] testData;
    private static double[][] validationData;

    public static void main(String[] args)
    {
        setUp();

        train();

        //test();

        //validate();

        //Write new weights to csv
    }

    private static void setUp()
    {
        structure = CSVReader.readStructureFromCSV("Tests/csv/asteroidsStructure.csv");
        double[][][] weights = CSVReader.readWeightsFromCSV("Tests/csv/asteroidsStructure.csv");
        double[][] data = CSVReader.readDataCsv("Tests/csv/asteroidsData.csv");

        nn = new NeuronalNetwork();
        nn.create(structure);
        //nn.setWeights(weights);
        initializeTestData(data);
    }

    private static void train()
    {
        System.out.println("start training...");
        double[][] input = CSVReader.readInputValuesFromDataArray(trainingData, structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(trainingData, structure);

        for(int i = 0; i < trainingData.length; i++)
        {
            nn.train(input[i],expectedOutput[i]);
        }

        System.out.println("training finished");
    }

    private static void test()
    {
        System.out.println("start testing...");
        double[][] input = CSVReader.readInputValuesFromDataArray(testData, structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(testData, structure);

        for(int i = 0; i < testData.length; i++) {
            double[] result = nn.compute(input[i]);
            for(int j = 0; j < result.length; j++)
                System.out.println("-> result: " + result[j] + " expectedOutput: " + expectedOutput[i][j] + " difference: "+ (expectedOutput[i][j] - result[j]));
        }

        System.out.println("testing finished");
    }

    private static void validate()
    {
        System.out.println("start validating...");
        double[][] input = CSVReader.readInputValuesFromDataArray(validationData, structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(validationData, structure);

        for(int i = 0; i < validationData.length; i++) {
            double[] result = nn.compute(input[i]);
            for(int j = 0; j < result.length; j++)
                System.out.println("-> result: " + result[j] + " expectedOutput: " + expectedOutput[i][j] + " difference: "+ (expectedOutput[i][j] - result[j]));
        }

        System.out.println("validation finished");
    }

    /**
     * Sets the trainingData (30%) testData (30%) and validationData (10%)
     */
    private static void initializeTestData(double[][] data)
    {
        Random random = new Random();
        int third = (data.length/100)*30;
        int tenth = (data.length/100)*10;
        trainingData = new double[third][7];
        testData = new double[third][7];
        validationData = new double[tenth][7];
        List<double[]> dataAsList = new ArrayList<>(Arrays.asList(data));

        //trainingData (30%)
        for(int i = 0; i < third; i++) {
            int rndDataSet = random.nextInt(dataAsList.size());
            var o = dataAsList.get(rndDataSet);
            trainingData[i] = o;
            dataAsList.remove(o);
        }

        //testData (30%)
        for(int i = 0; i < third; i++) {
            int rndDataSet = random.nextInt(dataAsList.size());
            var o = dataAsList.get(rndDataSet);
            testData[i] = o;
            dataAsList.remove(0);
        }

        //validationData (10%)
        for(int i = 0; i < tenth; i++) {
            int rndDataSet = random.nextInt(dataAsList.size());
            var o = dataAsList.get(rndDataSet);
            validationData[i] = o;
            dataAsList.remove(0);
        }
    }
}
