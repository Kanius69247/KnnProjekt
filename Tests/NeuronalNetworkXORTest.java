package Tests;

import NeuronalNetwork.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronalNetworkXORTest {
    private static NeuronalNetwork nn;
    private static int[] structureXOR;
    private static double [][][] w;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureXOR = new int[]{2, 3, 2, 1};
        nn.create(structureXOR);
        w = nn.getWeights();

        w[0][0][0] = 1.0;
        w[0][0][1] = -0.5;
        w[0][0][2] = 0.0; //deactivate BIAS NeuronalNetwork.Neuron
        w[0][1][0] = 0.0;
        w[0][1][1] = -0.5;
        w[0][1][2] = 1.0;
        w[0][2][0] = 0.0; //Bias (deactivated)
        w[0][2][1] = 0.0; //Bias (deactivated)
        w[0][2][2] = 0.0; //Bias (deactivated)

        w[1][0][0] = -0.5;
        w[1][0][1] = 0.0;
        w[1][1][0] = -0.5;
        w[1][1][1] = -0.5;
        w[1][2][0] = 0.0;
        w[1][2][1] = -0.5;
        w[1][3][0] = 0.0; // Bias (deactivated)
        w[1][3][1] = 0.0; // Bias (deactivated)

        w[2][0][0] = -0.5;
        w[2][1][0] = -0.5;
        w[2][2][0] = 0.0; //Bias (deactivated)

        nn.setWeights(w);
        nn.setUnitType(1, 1, UnitType.stepfun, -0.7);
        nn.setUnitType(2, 0, UnitType.stepfun, -0.7);
        nn.setUnitType(2, 1, UnitType.stepfun, -0.7);
        nn.setUnitType(3, 0, UnitType.stepfun, -0.7);
    }

    @Test
    public void xor_test_1_1() {
        double[] x = { 1.0, 1.0 } ;
        double[] out = nn.compute( x );
        assertEquals(0, out[0]);
    }

    @Test
    public void xor_test_1_0() {
        double[] x = { 1.0, 0.0 } ;
        double[] out = nn.compute( x );
        assertEquals(1, out[0]);
    }

    @Test
    public void xor_test_0_1() {
        double[] x = { 0.0, 1.0 } ;
        double[] out = nn.compute( x );
        assertEquals(1, out[0]);
    }

    @Test
    public void xor_test_0_0() {
        double[] x = { 0.0, 0.0 } ;
        double[] out = nn.compute( x );
        assertEquals(0, out[0]);
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureOR = null;
        w = null;
    }
}
