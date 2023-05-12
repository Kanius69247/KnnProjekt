package Tests;

import NeuronalNetwork.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class NeuronalNetworkTrainXORTest {
    private static NeuronalNetwork nn;
    private static int[] structureXOR;
    private static double [][][] w;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureXOR = new int[]{2, 3, 2, 1};
        nn.create(structureXOR);

        //Create network with random weights
        //set Input and Expected Output
        //Let network train and change it`s weights on its own till expected output is calculated by nn

        //Set the correct UnitTypes with the correct Threshold
        nn.setUnitType(1, 1, UnitType.stepfun, -0.7);
        nn.setUnitType(2, 0, UnitType.stepfun, -0.7);
        nn.setUnitType(2, 1, UnitType.stepfun, -0.7);
        nn.setUnitType(3, 0, UnitType.stepfun, -0.7);
        System.out.println(nn);
    }

    @Test
    public void xor_test_1_1() {
        double[] in = { 1.0, 1.0 } ; //Input
        double[] expected = {0.0};
        double[] out;// = new double[]{0}; //Expected output for 0-0 is 0
        //do training

        //do training
        nn.train(in, expected);

        out = nn.compute(in);

        //out should be 0
        assertEquals(0, out[0]);//Check calculated value matches expected value
    }

    @Test
    public void xor_test_0_0() {
        double[] in = { 0.0, 0.0 } ; //Input
        double[] expected = {0.0};

        double[] out;// = new double[]{0}; //Expected output for 0-0 is 0

        //do training
        nn.train(in, expected);

        out = nn.compute(in);

        //out should be 0
        assertEquals(0, out[0]);//Check calculated value matches expected value
    }

    @Test
    public void xor_test_1_0() {
        double[] in = { 1.0, 0.0 } ; //Input
        double[] expected = {1.0};

        //do training
        nn.train(in, expected);

        double[] out = nn.compute(in);

        //out should be 1
        assertEquals(1, out[0]);//Check calculated value matches expected value
    }

    @Test
    public void xor_test_0_1() {
        double[] in = { 0.0, 1.0 } ; //Input
        double[] expected = new double[]{1.0}; //Expected output for 0-1 is 0

        //do training
        nn.train(in, expected);

        double[] out = nn.compute(in);

        //out should be 1
        assertEquals(1, out[0]);//Check calculated value matches expected value
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureOR = null;
        w = null;
    }
}
