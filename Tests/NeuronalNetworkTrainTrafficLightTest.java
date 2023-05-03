package Tests;

import NeuronalNetwork.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronalNetworkTrainTrafficLightTest {
    private static NeuronalNetwork nn;
    private static int[] structure;
    private static double [][][] w;
    private double[][] trainingData;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structure = new int[]{3, 3, 4};
        nn.create(structure);

        //Create network with random weights
        //set Input and Expected Output
        //Let network train and change it`s weights on itś on till expected output is calculated by nn
        nn.setUnitType(1,0, UnitType.logistic);
        nn.setUnitType(1,1, UnitType.logistic);
        nn.setUnitType(1,2, UnitType.logistic);

        trainingData = CSVReader.read("Tests/csv/TrafficLightResults.csv");
    }

    @Test
    public void trafficLightTraining0() {
        double[] in = { trainingData[0][0], trainingData[0][1], trainingData[0][2] } ; //Input
        double[] exOut = {trainingData[0][3], trainingData[0][4], trainingData[0][5], trainingData[0][6]}; //Set the expected Output

        //do training

        nn.train(in, exOut);

        double[] out = nn.compute(in);

        //out should be 0
        assertEquals(out, exOut);//Check calculated value matches expected value
    }

    @Test
    public void trafficLightTraining1() {
        double[] in = { trainingData[1][0], trainingData[1][1], trainingData[1][2] } ; //Input
        double[] exOut = {trainingData[1][3], trainingData[1][4], trainingData[1][5], trainingData[1][6]}; //Set the expected Output

        //do training

        nn.train(in, exOut);

        double[] out = nn.compute(in);

        //out should be 0
        assertEquals(out, exOut);//Check calculated value matches expected value
    }



    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureOR = null;
        w = null;
    }
}
