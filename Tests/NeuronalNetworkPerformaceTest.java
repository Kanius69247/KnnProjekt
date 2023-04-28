package Tests;

import NeuronalNetwork.NeuronalNetwork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NeuronalNetworkPerformaceTest {
    private static NeuronalNetwork nn;
    private static int[] structure;
    private static double [][][] weights;


    public void setUpOr() {
        nn = new NeuronalNetwork();
        structure = new int[]{2, 3, 2, 1};
        nn.create(structure);
        nn = new NeuronalNetwork();
        structure = new int[]{2, 1};
        nn.create( structure );
        weights = nn.getWeights();

        weights[0][0][0] = 1.0;
        weights[0][1][0] = 1.0;
        weights[0][2][0] = 0.0; //deactivate BIAS NeuronalNetwork.Neuron

        nn.setWeights(weights);
        nn.setUnitType(1, 0, "stepfun", 0.5);
    }

    public void setUpNand() {
        nn = new NeuronalNetwork();
        structure = new int[]{2, 1};
        nn.create(structure);
        weights = nn.getWeights();

        weights[0][0][0] = -0.5;
        weights[0][1][0] = -0.5;
        weights[0][2][0] = 0.0; //deactivate BIAS NeuronalNetwork.Neuron

        nn.setWeights(weights);
        nn.setUnitType(1, 0, "stepfun", -0.7);
    }

    @BeforeEach
    public void setUpXor() {
        nn = new NeuronalNetwork();
        structure = new int[]{2, 3, 2, 1};
        nn.create(structure);
        weights = nn.getWeights();

        weights[0][0][0] = 1.0;
        weights[0][0][1] = -0.5;
        weights[0][0][2] = 0.0; //deactivate BIAS NeuronalNetwork.Neuron
        weights[0][1][0] = 0.0;
        weights[0][1][1] = -0.5;
        weights[0][1][2] = 1.0;
        weights[0][2][0] = 0.0; //Bias (deactivated)
        weights[0][2][1] = 0.0; //Bias (deactivated)
        weights[0][2][2] = 0.0; //Bias (deactivated)

        weights[1][0][0] = -0.5;
        weights[1][0][1] = 0.0;
        weights[1][1][0] = -0.5;
        weights[1][1][1] = -0.5;
        weights[1][2][0] = 0.0;
        weights[1][2][1] = -0.5;
        weights[1][3][0] = 0.0; // Bias (deactivated)
        weights[1][3][1] = 0.0; // Bias (deactivated)

        weights[2][0][0] = -0.5;
        weights[2][1][0] = -0.5;
        weights[2][2][0] = 0.0; //Bias (deactivated)

        nn.setWeights(weights);
        nn.setUnitType(1, 1, "stepfun", -0.7);
        nn.setUnitType(2, 0, "stepfun", -0.7);
        nn.setUnitType(2, 1, "stepfun", -0.7);
        nn.setUnitType(3, 0, "stepfun", -0.7);
    }

    @Test
    public void orPerformaceTest0_0() {
        setUpOr();
        double[] input = {0.0,0.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("or-Performance (0.0,0.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("or-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void orPerfomanceTest0_1() {
        setUpOr();
        double[] input = {0.0,1.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("or-Performance (0.0,1.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("or-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void orPerfomanceTest1_1() {
        setUpOr();
        double[] input = {1.0,1.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("or-Performance (1.0,1.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("or-Performance (x1000) Average Elapsed Time: " + times/1000);
    }


    @Test
    public void nandPerfomanceTest0_0() {
        setUpNand();
        double[] input = {0.0,0.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("nand-Performance (0.0,0.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("nand-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void nandPerfomanceTest0_1() {
        setUpNand();
        double[] input = {0.0,1.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("nand-Performance (0.0,1.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("nand-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void nandPerfomanceTest1_1() {
        setUpNand();
        double[] input = {1.0,1.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("nand-Performance (1.0,1.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("nand-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void xorPerfomanceTest1_1() {
        setUpXor();
        double[] input = {1.0,1.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("xor-Performance (1.0,1.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("xor-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void xorPerfomanceTest0_0() {
        setUpXor();
        double[] input = {0.0,0.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("xor-Performance (0.0,0.0) Elapsed Time: " + timeElapsed);
        }

        System.out.println("xor-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @Test
    public void xorPerfomanceTest1_0() {
        setUpXor();
        double[] input = {1.0,0.0};

        long times = 0;

        for(int i = 0; i < 1000; i++) {
            long start = System.currentTimeMillis();
            //System.out.println("start: "+start);

            double[] out = nn.compute(input);

            long finish = System.currentTimeMillis();
            //System.out.println("finish: " + finish);

            long timeElapsed = finish - start;
            times += timeElapsed;
            //System.out.println("xor-Performance (1.0,0.0) Elapsed Time: " + timeElapsed);
        }
        System.out.println("xor-Performance (x1000) Average Elapsed Time: " + times/1000);
    }

    @AfterEach
    public void tearDown() {
        nn = null;
        int[] structureOR = null;
        weights = null;
    }
}
