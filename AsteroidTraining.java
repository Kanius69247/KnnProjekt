import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.UnitType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsteroidTraining {
    private static NeuronalNetwork nn;
    private static int[] structure;

    private static int[] dataStructure;
    private static double[][] trainingData;
    private static double[][] testData;
    private static double[][] validationData;

    public static void main(String[] args)
    {

        System.out.println("Test summery");
        System.out.println("");

        setUp();
        train();
        test();
        validate();

        //Write new weights to csv
        //CSVReader.writeStructure("Tests/csv/asteroidsData.csv", structure, nn.getWeights());
    }

    private static void setUp()
    {
        //structure = CSVReader.readStructureFromCSV("Tests/csv/asteroidsStructure.csv");
        //double[][][] weights = CSVReader.readWeightsFromCSV("Tests/csv/asteroidsStructure.csv");

        structure = new int[]{5, 3, 2, 1};
        dataStructure = new int[]{7, 1};
        double[][] data = CSVReader.readDataCsv("Tests/csv/asteroidsData.csv");

        nn = new NeuronalNetwork();
        nn.create(structure);
        //nn.setWeights(weights);
        initializeDataBatches(data);

        //nn.setUnitType(0,0, UnitType.relu);
        //nn.setUnitType(0,1, UnitType.relu);
        //nn.setUnitType(0,2, UnitType.relu);
        //nn.setUnitType(0,3, UnitType.relu);
        //nn.setUnitType(0,4, UnitType.relu);

        //nn.setUnitType(1,0, UnitType.relu);
        //nn.setUnitType(1,1, UnitType.relu);
        //nn.setUnitType(1,2, UnitType.relu);

        //nn.setUnitType(2,0, UnitType.relu);
        //nn.setUnitType(2,1, UnitType.relu);

        nn.setUnitType(3,0, UnitType.relu);

        System.out.println("[NeuronalNetwork initial status]");
        System.out.println(nn);
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("");


    }

    private static void train()
    {
        System.out.println("[Training]");

        double[][] input = CSVReader.readInputValuesFromDataArray(trainingData, dataStructure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(trainingData, dataStructure);

        int trainCount = 1; //For case of repetation of train

        for (int train = 0; train < trainCount; train++) {
            System.out.println("****** train Nr." + train);
            for (int i = 0; i < trainingData.length; i++) {
                double[] in = {input[i][1], input[i][2], input[i][3], input[i][4], input[i][6]};
                nn.train(in, expectedOutput[i]);

                if (i % 10000 == 0 || i == trainingData.length - 1) {
                    System.out.print(i + "th Traning with input:" + Arrays.toString(in));
                    System.out.println(" with Expacted Result: " + Arrays.toString(expectedOutput[i]));

                }
            }
            System.out.println("");
        }


        System.out.println(">> Notification: " + trainCount + " times traning with "+ trainingData.length +" datas is done!");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("");
    }

    private static void test()
    {
        System.out.println("[Test Result]");
        double[][] input = CSVReader.readInputValuesFromDataArray(testData, dataStructure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(testData, dataStructure);

        int count = 0;
        int corr = 0;
        double targetErrorRate = 0.01;

        for(int i = 0; i < testData.length; i++) {

            double[] in = {input[i][1], input[i][2], input[i][3], input[i][4], input[i][6]};
            double[] result = nn.compute(in);

            for (int j = 0; j < result.length; j++) {
                count++;
                if (Math.abs(expectedOutput[i][j] - result[j]) < targetErrorRate) {
                    corr++;

                    if(i%10000 == 0)
                        System.out.print(i + "th test: Pass");
                    if(i%10000 == 0)
                        System.out.println("-> expectedOutput: " + expectedOutput[i][j] + " result: " + result[j] +  " difference: "+ (expectedOutput[i][j] - result[j]));
                }
                else {
                    if(i%10000 == 0)
                        System.out.print(i + "th test: Fail");
                    if(i%10000 == 0)
                        System.out.println("-> expectedOutput: " + expectedOutput[i][j] + " result: " + result[j] +  " difference: "+ (expectedOutput[i][j] - result[j]));
                }
            }
        }

        double rate = (double)corr/(double)count;

        System.out.println("");
        System.out.println(">> Notification: Testing with "+ testData.length +" datas is done!");
        System.out.println(">> Accuracy: " + rate*100 +"% (" + corr + " out of " + count+")");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("");
    }

    private static void validate()
    {
        System.out.println("[Validation]");
        double[][] input = CSVReader.readInputValuesFromDataArray(validationData, dataStructure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(validationData, dataStructure);

        int count = 0;
        int corr = 0;
        double targetErrorRate = 0.01;

        for(int i = 0; i < validationData.length; i++) {
            double[] in = {input[i][1], input[i][2], input[i][3], input[i][4], input[i][6]};
            double[] result = nn.compute(in);
            for (int j = 0; j < result.length; j++) {
                count++;
                if (Math.abs(expectedOutput[i][j] - result[j]) < targetErrorRate) {
                    corr++;

                    if(i%3000 == 0)
                        System.out.print(i + "th Validation: Pass");
                    if(i%3000 == 0)
                        System.out.println("-> expectedOutput: " + expectedOutput[i][j] + " result: " + result[j] +  " difference: "+ (expectedOutput[i][j] - result[j]));

                } else {
                    if(i%3000 == 0)
                        System.out.print(i + "th Validation: Fail");
                    if(i%3000 == 0)
                        System.out.println("-> expectedOutput: " + expectedOutput[i][j] + " result: " + result[j] +  " difference: "+ (expectedOutput[i][j] - result[j]));
                }
            }
        }


        double rate = (double)corr/(double)count;

        System.out.println("");

        System.out.println(">> Notification: Validation with "+ validationData.length +" datas is done!");
        System.out.println(">> Accuracy: " + rate*100 +"% (" + corr + " out of " + count+")");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("");

        System.out.println("[NeuronalNetwork final status]");
        System.out.println(nn);
        System.out.println("---------------------------------------------------------------------------------------------");
        //System.out.println("");

    }

    /**
     * Sets the trainingData (30%) testData (30%) and validationData (10%)
     */
    private static void initializeDataBatches(double[][] data)
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
