import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        //just change filepath, neural net creation and weight setting is automated
        double[][] data = CSVReader.read("Tests/csv/TrafficLightStructure.csv");
        NeuronalNetwork nn = new NeuronalNetwork();
        int[] structure = new int[data[0].length];

        for(int i = 0; i < structure.length; i++) {
            structure[i] = (int)data[0][i];
        }

        nn.create(structure);

        double[][][]weights = CSVReader.formatWeightMatrix(data);
        nn.setWeights(weights);

        for(int i = 0; i < weights.length; i++) {
            for(int j = 0; j < weights[i].length; j++) {
                System.out.println(Arrays.toString(weights[i][j]));
            }
        }
        System.out.println("\n" + nn.toString2());
    }
}
