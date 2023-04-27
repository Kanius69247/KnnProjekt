package Tests;

import NeuronalNetwork.NeuronalNetwork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeuronalNetworkHalfAdderTest {

    private static NeuronalNetwork nn;
    private static int[] structureHalfAdder;
    private static double [][][] w;
    
    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureHalfAdder = new int[]{2,4,4,3,2};
        nn.create(structureHalfAdder);

        double [][][] weights = nn.getWeights();

        weights[0][0][0] = 1.0;
        weights[0][0][1] = 0;
        weights[0][0][2] = 1.0;
        weights[0][0][3] = 0;
        weights[0][0][4] = 0;

        weights[0][1][0] = 0;
        weights[0][1][1] = 1.0;
        weights[0][1][2] = 0;
        weights[0][1][3] = 1.0;
        weights[0][1][4] = 0;

        weights[0][2][0] = 0; //Bias deactivated
        weights[0][2][1] = 0;
        weights[0][2][2] = 0;
        weights[0][2][3] = 0;
        weights[0][2][4] = 0; //<-

        weights[1][0][0] = 1.0;
        weights[1][0][1] = 0;
        weights[1][0][2] = 0;
        weights[1][0][3] = 0;
        weights[1][0][4] = 0;

        weights[1][1][0] = 1.0;
        weights[1][1][1] = 0;
        weights[1][1][2] = 0;
        weights[1][1][3] = 0;
        weights[1][1][4] = 0;

        weights[1][2][0] = 0;
        weights[1][2][1] = 1.0;
        weights[1][2][2] = -0.5;
        weights[1][2][3] = 0;
        weights[1][2][4] = 0;

        weights[1][3][0] = 0;
        weights[1][3][1] = 0;
        weights[1][3][2] = -0.5;
        weights[1][3][3] = 1;
        weights[1][3][4] = 0;

        weights[1][4][0] = 0; //Bias deactivated ->
        weights[1][4][1] = 0;
        weights[1][4][2] = 0;
        weights[1][4][3] = 0;
        weights[1][4][4] = 0;//<-

        weights[2][0][0] = 1.0;
        weights[2][0][1] = 0;
        weights[2][0][2] = 0;
        weights[2][0][3] = 0;

        weights[2][1][0] = 0;
        weights[2][1][1] = -0.5;
        weights[2][1][2] = 0;
        weights[2][1][3] = 0;

        weights[2][2][0] = 0;
        weights[2][2][1] = -0.5;
        weights[2][2][2] = -0.5;
        weights[2][2][3] = 0;

        weights[2][3][0] = 0;
        weights[2][3][1] = 0;
        weights[2][3][2] = -0.5;
        weights[2][3][3] = 0;

        weights[2][4][0] = 0;//Bias deactivated ->
        weights[2][4][1] = 0;
        weights[2][4][2] = 0;
        weights[2][4][3] = 0;//<-

        weights[3][0][0] = 1.0;
        weights[3][0][1] = 0;
        weights[3][0][0] = 0;

        weights[3][1][0] = 0;
        weights[3][1][1] = -0.5;

        weights[3][2][0] = 0;
        weights[3][2][1] = -0.5;

        weights[3][3][0] = 0;//Bias deactivated ->
        weights[3][3][1] = 0;

        nn.setWeights(weights);

        //Changing Neuron Types
        nn.setUnitType(2,0,"stepfun",1.5);
        nn.setUnitType(2,2,"stepfun", -0.7);
        nn.setUnitType(3,1,"stepfun", -0.7);
        nn.setUnitType(3,2,"stepfun", -0.7);
        nn.setUnitType(4,1,"stepfun", -0.7);
    }

    @Test
    public void halfAdder_add00() {
        double[] x = { 0.0, 0.0};
        double[] out = nn.compute( x );
        //out[0] = out
        //out[1] = carryOver
        assertEquals(0, out[0]);
        assertEquals(0, out[1]);
    }

    @Test
    public void halfAdder_add01() {
        double[] x = { 0.0, 0.1};
        double[] out = nn.compute( x );
        //out[0] = out
        //out[1] = carryOver
        assertEquals(1, out[0]);
        assertEquals(0, out[1]);
    }

    @Test
    public void halfAdder_add10() {
        double[] x = { 1.0, 0.0 } ;
        double[] out = nn.compute( x );
        //out[0] = out
        //out[1] = carryOver
        assertEquals(1, out[0]);
        assertEquals(0, out[1]);
    }

    @Test
    public void halfAdder_add11() {
        double[] x = { 1.0, 1.0 } ;
        double[] out = nn.compute( x );
        //out[0] = out
        //out[1] = carryOver
        assertEquals(0, out[0]);
        assertEquals(1, out[1]);
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureTraffic = null;
        w = null;
    }
}
