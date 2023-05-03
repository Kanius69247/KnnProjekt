package Tests;

import NeuronalNetwork.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronalNetworkNANDTest {
    private static NeuronalNetwork nn;
    private static int[] structureNAND;
    private static double [][][] w;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureNAND = new int[]{2, 1};
        nn.create(structureNAND);
        w = nn.getWeights();

        w[0][0][0] = -0.5;
        w[0][1][0] = -0.5;
        w[0][2][0] = 0.0; //deactivate BIAS NeuronalNetwork.Neuron

        nn.setWeights(w);
        nn.setUnitType(1, 0, UnitType.stepfun, -0.7);
    }

    @Test
    public void nand_test_1_1() {
        double[] x = { 1.0, 1.0 } ;
        double[] out = nn.compute( x );
        assertEquals(0, out[0]);
    }

    @Test
    public void nand_test_1_0() {
        double[] x = { 1.0, 0.0 } ;
        double[] out = nn.compute( x );
        assertEquals(1, out[0]);
    }

    @Test
    public void nand_test_0_1() {
        double[] x = { 0.0, 1.0 } ;
        double[] out = nn.compute( x );
        assertEquals(1, out[0]);
    }

    @Test
    public void nand_test_0_0() {
        double[] x = { 0.0, 0.0 } ;
        double[] out = nn.compute( x );
        assertEquals(1, out[0]);
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureOR = null;
        w = null;
    }
}
