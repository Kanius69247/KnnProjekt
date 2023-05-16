package Tests;

import NeuronalNetwork.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronalNetworkTrafficTest {
    private static NeuronalNetwork nn;
    private static int[] structureTraffic;
    private static double [][][] w;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureTraffic = new int[]{3,3,4};
        nn.create(structureTraffic);
        nn.setBias(1);

        //Input weights
        double [][][] w = CSVReader.readWeightsFromCSV("Tests/csv/TrafficLightStructure.csv");
        nn.setWeights(w);

        //Changing Neuron Types
        nn.setUnitType(1,0, UnitType.logistic);
        nn.setUnitType(1,1, UnitType.logistic);
        nn.setUnitType(1,2, UnitType.logistic);

    }

    @Test
    public void traffic_test_1_1() {
        double[] x = {1.0, 0.0, 0.0};
        double[] out = nn.compute( x );
        double[] expected = {-0.0350473917418512, 0.0637984092627128,
                            0.11976621345437,   0.0336754888083852};

        assertEquals(expected[0], out[0], 0.01);
        assertEquals(expected[1], out[1], 0.01);
        assertEquals(expected[2], out[2], 0.01);
        assertEquals(expected[3], out[3], 0.01);
    }

    @Test
    public void trafficTestAll()
    {
        int[] structure = CSVReader.readStructureFromCSV("Tests/csv/TrafficLightStructure.csv");
        double [][][] weights = CSVReader.readWeightsFromCSV("Tests/csv/TrafficLightStructure.csv");
        double[][] input = CSVReader.readInputValuesFromDataArray(CSVReader.readDataCsv("Tests/csv/TrafficLightResults.csv"), structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(CSVReader.readDataCsv("Tests/csv/TrafficLightResults.csv"), structure);
        nn.create(structure);
        nn.setWeights(weights);
        nn.setUnitType(1,0, UnitType.tanh);
        nn.setUnitType(1,1, UnitType.tanh);
        nn.setUnitType(1,2, UnitType.tanh);

        double[][] results = new double[input.length][expectedOutput.length];

        for(int i = 0; i < input.length; i++)
            results[i] = nn.compute(input[i]);

//        Assertions.assertArrayEquals(expectedOutput, results);
    }

    @Test
    public void TestTrafficLightsTrainDataBinary()
    {
        int[] structure = {3,3,4};
        double [][][] weights = CSVReader.readWeightsFromCSV("Tests/csv/TrafficLightStructure.csv");
        double[][] input = CSVReader.readInputValuesFromDataArray(CSVReader.readDataCsv("Tests/csv/testdata_trafficlights_binary.csv"), structure);
        double[][] expectedOutput = CSVReader.readOutputValuesFromDataArray(CSVReader.readDataCsv("Tests/csv/testdata_trafficlights_binary.csv"), structure);
        nn.create(structure);
        nn.setUnitType(1,0, UnitType.logistic);
        nn.setUnitType(1,1, UnitType.logistic);
        nn.setUnitType(1,2, UnitType.logistic);

        double[][] results = new double[input.length][expectedOutput.length];

        for(int i = 0; i < input.length; i++)
            results[i] = nn.compute(input[i]);

        //Assertions.assertArrayEquals(expectedOutput, results);

        for(int i = 0; i < results.length; i++)
            for(int j = 0; j < results[i].length; j++) {
                //Assertions.assertEquals(expectedOutput[i][j], Math.round(results[i][j]));//, 0.1);
            }
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureTraffic = null;
        w = null;
    }
}
