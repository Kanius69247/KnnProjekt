package Tests;

import NeuronalNetwork.NeuronalNetwork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        double [][][] w = {
                {{-0.081,0.08,-0.04},
                        {0.06,0.02,-0.003},
                        {-0.01,0.003,-0.09},
                        {0.08,-0.09,-0.05}},

                {{-0.008,0.01,0.01,2.9E-4},
                        {0.06,-0.06,-0.027,-0.01},
                        {0.04,0.06,0.08,0.08},
                        {-0.08,0.06,0.09,-0.001}}
        };
        nn.setWeights(w);

        //Changing Neuron Types
        nn.setUnitType(1,0,"logistic");
        nn.setUnitType(1,1,"logistic");
        nn.setUnitType(1,2,"logistic");

    }

    @Test
    public void xor_test_1_1() {
        double[] x = {1.0, 0.0, 0.0};
        double[] out = nn.compute( x );
        double[] expected = {-0.0350473917418512, 0.0637984092627128,
                            0.11976621345437,   0.0336754888083852};

        assertEquals(expected[0], out[0], 0.0001);
        assertEquals(expected[0], out[0], 0.0001);
        assertEquals(expected[0], out[0], 0.0001);
        assertEquals(expected[0], out[0], 0.0001);
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureTraffic = null;
        w = null;
    }
}
