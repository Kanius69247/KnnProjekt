package Tests;

import NeuronalNetwork.NeuronalNetwork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeuronalNetworkErrorTest {
    private static NeuronalNetwork nn;
    private static int[] structureError;
    private static double [][][] w;

    @BeforeEach
    public void setUp() {
        nn = new NeuronalNetwork();
        structureError = new int[]{2, 2};
        nn.create(structureError);

        // in: x1, x2    out: y1=(1 * x1)+(1 * x2), y2=(1 * x1)+(1 * x2)
        w = new double[][][]{{{1,1}, {1,1}, {0,0}}};
        nn.setWeights(w);

        double[][] samples = new double[][]{{0,0},{1,1},{2,2}};
        nn.computeAll(samples);

        double[][] actuals = new double[][]{{0,0},{1,2},{3,4}};
        nn.setActuals(actuals);
    }

    @Test
    void computeErrorAll_test() {

        double[] errors = nn.computeErrorAll();

        assertEquals(0, errors[0]); // in:(0, 0), out:(0, 0) -> actual:(0, 0)
                                             // (0^2 + 0^2) / 2 = 0
        assertEquals(0.5, errors[1]); // in:(1, 1), out:(2, 2) -> actual:(1, 2)
                                             // ((2-1)^2 + (2-2)^2) / 2 = (1+0)/2 = 0.5
        assertEquals(0.5, errors[2]); // in:(2, 2), out:(4, 4) -> actual:(3, 4)
                                             // ((4-3)^2 + (4-4)^2) / 2 = (1+0)/2 = 0.5
    }

    @Test
    void computeErrorAll_test2() {

        NeuronalNetwork nn_2 = new NeuronalNetwork();
        int[] structure_2 = {2,3,4,2};
        nn_2.create(structure_2);

        nn_2.readActual("Tests/csv/actuall.csv");
        nn_2.readSamples("Tests/csv/sample.csv");
        nn_2.computeAll();

        double[] errors = nn_2.computeErrorAll();

//		System.out.println(Arrays.deepToString(nn_2.samples));
//		System.out.println(Arrays.deepToString(nn_2.actual));
//		System.out.println(Arrays.deepToString(nn_2.resultsAll));
        System.out.println(Arrays.toString(errors));

        assertTrue(errors.length == 30);

    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureError = null;
        w = null;
    }
}