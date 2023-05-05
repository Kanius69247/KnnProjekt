import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        //just change filepath, neural net creation and weight setting is automated
        double[][] data = CSVReader.read("Tests/csv/TrafficLightStructure.csv");
        double[][] results = CSVReader.read("Tests/csv/TrafficLightResults.csv");
        NeuronalNetwork nn = new NeuronalNetwork();
        int[] structure = new int[data[0].length];

        for(int i = 0; i < structure.length; i++) {
            structure[i] = (int)data[0][i];
        }

        nn.create(structure);

        nn.setWeights(CSVReader.formatWeightMatrix(data));

        System.out.println(nn.toString2() + "\n");

        double[][][] formatted_results = CSVReader.formatResultMatrix(structure, results);

        for(int i = 0; i < formatted_results.length; i++) {
            for(int j = 0; j < formatted_results[i].length; j++) {
                if(j == 0) {
                    System.out.println("Input " + (i + 1) + ":\n" + Arrays.toString(formatted_results[i][j]));
                    continue;
                }
                else if(j == 1) {
                    System.out.println("Output " + (i + 1) + ":\n" + Arrays.toString(formatted_results[i][j]) + "\n");
                }
            }
        }

        //nn.train(formatted_results[0][0], formatted_results[0][1]);

        //System.out.println(nn.toString2());

        double[][] nn_results = new double[formatted_results.length][];
        for(int i = 0; i < nn_results.length; i++) {
            nn_results[i] = nn.compute(formatted_results[i][0]);
            System.out.println(Arrays.toString(nn_results[i]));
        }

        //CSVReader.write("Tests/csv/testresults.csv", nn_results);
    }
}
