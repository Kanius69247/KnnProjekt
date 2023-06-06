package Tests;

import NeuronalNetwork.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CSVReaderTest {

    @Test
    public void testReadData()
    {
        double[][] expected = {//= new double[16][7];
        {1,0,0,1,0,0,0},
        {0.8,0,0.1,1,0,0,0},
        {0.99,0.1,0,1,0,0,0},
        {1.1,0,0.01,1,0,0,0},
        {1,1,0,0,1,0,0},
        {0.99,1.1,0,0,1,0,0},
        {1.1,0.9,0,0,1,0,0},
        {1,1,0.1,0,1,0,0},
        {0,0,1,0,0,1,0},
        {0.1,0.1,1,0,0,1,0},
        {0,0.1,1.1,0,0,1,0},
        {0.1,0,1,0,0,1,0},
        {0,1,0,0,0,0,1},
        {0.1,1.1,0,0,0,0,1},
        {0.01,1.1,0.1,0,0,0,1},
        {0,0.99,-0.01,0,0,0,1},
        };
        double[][] result = CSVReader.readDataCsv("Tests/csv/TrafficLightResults.csv");

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testReadStructure()
    {
        int[] expected = new int[]{3,3,4};

        int[] result = CSVReader.readStructureFromCSV("Tests/csv/TrafficLightStructure.csv");

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testReadWeights()
    {
        double[][][] expected = {
                {{-0.081, 0.08, -0.04},
                {0.06, 0.02, -0.003},
                {-0.01, 0.003, -0.09},
                {0.08, -0.09, -0.05}},

                {{-0.008, 0.01, 0.01, 2.9E-4},
                {0.06, -0.06, -0.027, -0.01},
                {0.04, 0.06, 0.08, 0.08},
                {-0.08, 0.06, 0.09, -0.001}}};

        double [][][] result = CSVReader.readWeightsFromCSV("Tests/csv/TrafficLightStructure.csv");

        Assertions.assertArrayEquals(expected, result);
    }
}
