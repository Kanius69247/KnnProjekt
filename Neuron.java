
public class Neuron
{
    private String unitType; //Still not sure if this decides which function is used
    private double threshold;

    public Neuron()
    {
        unitType = "id"; //default value should be identity function
        threshold = 0.0;
    }

    /**
     * computes the output of the cell
     * @param input input value
     * @return output value (input * weight)
     */
    public double compute(double input)
    {
        double result = 0.0;

        switch(unitType)
        {
            case "id":
                result = id(input);
                break;
            case "logistic":
                result = logistic(input);
                break;
            case "tanh":
                result = tanh(input);
                break;
            case "heaviside":
                result = heaviside(input);
                break;
            case "perceptronStepfun":
                result = perceptronStepfun(input);
                break;
        }

        return result;
    }

    /**
     * Sets the unit type used in compute
     * @param unitType unitType
     */
    public void setUnitType(String unitType)
    {
        String[] activateFun = {"id", "logistic", "tanh", "heaviside", "perceptronStepfun"};
        boolean found = false;
        for (String s : activateFun) {
            if (s.contains(unitType)) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Invalid type of activate function!\n(Vaild ption: id, logistic, tanh, heaviside, perceptronStepfun");
        }
        this.unitType = unitType;
    }

    /**
     * Sets unit type and threshold used in compute
     * @param unitType unitType
     * @param threshold threshold
     */
    public void setUnitType(String unitType, double threshold)
    {
        setUnitType(unitType);
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return "unit: "+ unitType + ", ";
    }

    private double id(double x)
    {
        return x;
    }

    private double logistic(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double tanh(double x) {
        return Math.tanh(x);
    }

    public double heaviside(double x) {
        if (x < threshold) {
            return 0.0;
        } else if (x == threshold) {
            return 0.5;
        } else {
            return 1.0;
        }
    }

    private double perceptronStepfun(double x)
    {
        if (x<=threshold){
            return 0;
        } else {
            return 1;
        }
    }

}
