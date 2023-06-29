import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.UnitType;
import NeuronalNetwork.TrainingResult;

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

        System.out.println(nn);

        train();

        //test();

        //validate();

        System.out.println(nn);

        writeStructureToCSV();

        System.out.println("exit!");
    }

    private static void setUp()
    {
        structure = CSVReader.readStructureFromCSV("Tests/csv/asteroidsStructure.csv");
        double[][][] weights = CSVReader.readWeightsFromCSV("Tests/csv/asteroidsStructure.csv");

        nn = new NeuronalNetwork();
        nn.create(structure);
        nn.setWeights(weights);

        trainingData = CSVReader.readDataCsv("Tests/csv/asteroidsTrainingData.csv");
        testData = CSVReader.readDataCsv("Tests/csv/asteroidsTestData.csv");
        validationData = CSVReader.readDataCsv("Tests/csv/asteroidsValidationData.csv");

        for(int i = 0; i < structure.length-1; i++)
            for(int j = 0; j < structure[i]; j++)
                nn.setUnitType(i,j, UnitType.relu);

        nn.setUnitType(2,0, UnitType.sigmoid);
    }

    private static void train()
    {
        System.out.println("start training...");
        double[][] input = CSVReader.readInputValuesFromDataArray(trainingData, structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(trainingData, structure);
        int trainingEpoche = 20000000;

        for(int i = 0; i < 1; i++)
        {
            System.out.println("iteration:" +i);
            List<TrainingResult> result = nn.train(input[i],expectedOutput[i], trainingEpoche);
            System.out.println("result: av_error: " + result.stream().mapToDouble(x -> x.error).sum() / result.size() + " epochCount: "+result.stream().mapToInt(x -> x.epcho).max());

            //Write training result to csv for plotting
            /*double[][] dResult = new double[result.size()][2];

            for(int r = 0; r < result.size(); r++)
            {
                dResult[r][0] = result.get(r).epcho;
                dResult[r][1] = result.get(r).error;
            }

            CSVReader.write("AsteroidTrainingResult.csv", dResult);*/
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

            //Write result to csv for plotting
            /*double[][] dResult = new double[result.length][2];

            for(int j = 0; j < result.length; j++)
            {
                dResult[j][0] = j;
                dResult[j][1] = result[j];
            }

            CSVReader.write("AsteroidTestResult.csv", dResult);*/
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


            //Write result to csv for plotting
            /*double[][] dResult = new double[result.length][2];

            for(int j = 0; j < result.length; j++)
            {
                dResult[j][0] = j;
                dResult[j][1] = result[j];
            }

            CSVReader.write("AsteroidTestResult.csv", dResult);*/
        }


        System.out.println("validation finished");
    }

    private static void writeStructureToCSV()
    {
        //Write new weights to csv
        CSVReader.writeStructure("Tests/csv/asteroidsStructure.csv", structure, nn.getWeights());
        System.out.println("new weights written!");
    }

    /**
     * Sets the trainingData (30%) testData (30%) and validationData (10%)
     */
    private static void splitAndWriteDataBatches()
    {
        double[][] data = CSVReader.readDataCsv("Tests/csv/asteroidsData.csv");

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

        CSVReader.write("Tests/csv/asteroidsTrainingData.csv", trainingData);

        //testData (30%)
        for(int i = 0; i < third; i++) {
            int rndDataSet = random.nextInt(dataAsList.size());
            var o = dataAsList.get(rndDataSet);
            testData[i] = o;
            dataAsList.remove(0);
        }

        CSVReader.write("Tests/csv/asteroidsTestData.csv", testData);

        //validationData (10%)
        for(int i = 0; i < tenth; i++) {
            int rndDataSet = random.nextInt(dataAsList.size());
            var o = dataAsList.get(rndDataSet);
            validationData[i] = o;
            dataAsList.remove(0);
        }

        CSVReader.write("Tests/csv/asteroidsValidationData.csv", validationData);
    }
}
