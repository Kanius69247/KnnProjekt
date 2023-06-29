import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;

import java.util.Arrays;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        //read in the data

        System.out.print("results CSV Filename (.csv): ");
        String line = reader.next();
        double[][] results = CSVReader.read("Tests/csv/" + line);

        //create new network instance
        NeuronalNetwork nn = new NeuronalNetwork();

        //get the structure
        //int[] structure = new int[data[0].length];
        //for(int i = 0; i < structure.length; i++) {
        //    structure[i] = (int)data[0][i];
        //}

        //create network with specified structure
        nn.create(new int[]{3,3,4});

        //set the weights
        //nn.setWeights(CSVReader.formatWeightMatrix(data));

        System.out.println("\nNeural Net created:" + nn + "\nResult Matrix:");

        //create result array
        double[][][] formatted_results = CSVReader.formatResultMatrix(new int[]{3,3,4}, results);
        for(int i = 0; i < formatted_results.length; i++) {
            for(int j = 0; j < formatted_results[i].length; j++) {
                if(j == 0) {
                    System.out.println("Input " + (i) + ":\n" + Arrays.toString(formatted_results[i][j]));
                    continue;
                }
                else if(j == 1) {
                    System.out.println("Output " + (i) + ":\n" + Arrays.toString(formatted_results[i][j]) + "\n");
                }
            }
        }

        //System.out.print("Choose dataset used for training (number behind Input/Output in Result Matrix): ");
        //int index = reader.nextInt();

        //train the net with one data sample of the result array
        for(int i = 0; i < formatted_results.length - 1; i++) {
            nn.train(formatted_results[i][0], formatted_results[i][1], 200000);

        }
        //trained network
        System.out.println("Trained Network:" + nn + "\n");

        //make a prediction
        //double[] prediction = nn.compute(formatted_results[index][0]);
        System.out.print("Testdata CSV Filename (.csv): ");
        String line2 = reader.next();
        double[][] data = CSVReader.read("Tests/csv/" + line2);
        double[][][] testdata = CSVReader.formatResultMatrix(new int[]{3,3,4}, data);
        for(int i = 0; i < testdata.length - 1; i++) {
            System.out.println("Prediction:\n" + "Input: " + Arrays.toString(testdata[i][0]) + " Output nn: " + Arrays.toString(nn.compute(testdata[i][0])) + " Expected: " + Arrays.toString(testdata[i][1]));
        }
    }
}
