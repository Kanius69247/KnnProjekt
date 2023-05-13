import NeuronalNetwork.NeuronalNetwork;
import NeuronalNetwork.CSVReader;
import java.util.ArrayList;

public class ErrorValueCSVCreation {
    public static void main(String args[]) {
        NeuronalNetwork nn = new NeuronalNetwork();
        nn.create(new int[]{3,3,4});
        nn.train(new double[]{1.0, 0.0, 0.0}, new double[]{1.0, 0.0, 0.0, 0.0});
        ArrayList<Double> errors = nn.getErrors();
        System.out.println(errors);

        double[][] e = new double[errors.size()][];
        for(int i = 0; i < e.length; i++) {
            e[i] = new double[]{errors.get(i).doubleValue()};
        }
        CSVReader.write("Tests/csv/errors.csv", e);
    }
}
