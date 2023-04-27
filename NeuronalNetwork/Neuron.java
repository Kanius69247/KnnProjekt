package NeuronalNetwork;

import jdk.jfr.Threshold;

public class Neuron
{
    private String unitType;
    private double threshold;
    private double derivative;

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
                result = UnitTypeFunctions.id(input);
                derivative = UnitTypeFunctions.derivationId(result);
                break;
            case "logistic":
                result = UnitTypeFunctions.logistic(input);
                derivative = UnitTypeFunctions.derivationLogistic(result);
                break;
            case "tanh":
                result = UnitTypeFunctions.tanh(input);
                derivative = UnitTypeFunctions.derivationTanh(result);
                break;
            case "heaviside":
                result = UnitTypeFunctions.heaviside(input, threshold);
                derivative = UnitTypeFunctions.derivationHeaviside(result);
                break;
            case "stepfun":
                result = UnitTypeFunctions.perceptronStepfun(input, threshold);
                derivative = UnitTypeFunctions.derivationPerceptronStepfun(result);
                break;
        }

        return result;
    }

    public double getDerivative() {
        return derivative;
    }

    /**
     * Sets the unit type used in compute
     * @param unitType unitType
     */
    public void setUnitType(String unitType)
    {
        String[] activateFun = {"id", "logistic", "tanh", "heaviside", "stepfun"};
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
        String result = "";

        result += "unit: "+ unitType + ", ";
        if(unitType.equals("stepfun"))
            result += this.threshold + ", ";

        return result;

    }

}
