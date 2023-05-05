import NeuronalNetwork.CSVReader;
import NeuronalNetwork.NeuronalNetwork;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        double[][] data = CSVReader.read("Tests/csv/TrafficLightStructure.csv");
        NeuronalNetwork nn = new NeuronalNetwork();
        int[] structure = new int[data[0].length];
        int csv_weights_length = 0;

        for(int i = 0; i < structure.length; i++) {
            structure[i] = (int)data[0][i];
            if(i < structure.length - 1) {
                csv_weights_length += (int)data[0][i];
            }
        }
        nn.create(structure);

        nn.setWeights(CSVReader.formatWeightMatrix(data));
    }
}
