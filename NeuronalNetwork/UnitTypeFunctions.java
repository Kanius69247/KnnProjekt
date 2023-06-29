package NeuronalNetwork;

public class UnitTypeFunctions {
    public static double id(double x)
    {
        return x;
    }

    public static double derivationId(double x) {
        return 1.0;
    }

    public static double logistic(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double derivationLogistic(double x) {
        return x * (1 - x);
    }

    public static double tanh(double x) {
        return Math.tanh(x);
    }

    public static double derivationTanh(double x) {
        return 2 * logistic(2 * x) - 1;
    }

    public static double heaviside(double x, double threshold) {
        if (x < threshold) {
            return 0.0;
        } else if (x == threshold) {
            return 0.5;
        } else {
            return 1.0;
        }
    }

    public static double derivationHeaviside(double x) {
        return 0;
    }

    public static double perceptronStepfun(double x, double threshold)
    {
        if (x<=threshold){
            return 0;
        } else {
            return 1;
        }
    }

    public static double derivationPerceptronStepfun(double x) {
        return 0;
    }

    public static double rectifiedLinearUnit(double x)
    {
        return x > 0 ? x : 0;
    }

    public static double derivationRectifiedLinearUnit(double x)
    {
        return x > 0 ? 1.0: 0;
    }

    public static double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-1.0 * x));
    }

    public static double derivativeSigmoid(double x) {
        return x * (1.0 - x);
    }
    /*public static double derivativeSigmoid(double x) {
        double s = Math.log(x);
        return s * (1 - s);
    }*/
}
