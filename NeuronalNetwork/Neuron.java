package NeuronalNetwork;

public class Neuron
{
    private UnitType unitType;
    private double threshold;
    private double derivative;
    private double result;

    public Neuron()
    {
        this.unitType = UnitType.id; //default value should be identity function
        this.threshold = 0.0;
        this.result = 0.0;
        this.derivative = 1.0;
    }

    /**
     * computes the output of the cell
     * @param input input value
     * @return output value (input * weight)
     */
    public double compute(double input)
    {
        switch(this.unitType)
        {
            case id:
                this.result = UnitTypeFunctions.id(input);
                break;
            case logistic:
                this.result = UnitTypeFunctions.logistic(input);
                break;
            case tanh:
                this.result = UnitTypeFunctions.tanh(input);
                break;
            case heaviside:
                this.result = UnitTypeFunctions.heaviside(input, threshold);
                break;
            case stepfun:
                this.result = UnitTypeFunctions.perceptronStepfun(input, threshold);
                break;
        }

        return this.result;
    }

    /**
     * Computes the derivative of the Output
     * @return derivative of the output
     */
    public double getDerivative() {
        switch(this.unitType)
        {
            case id:
                this.derivative = UnitTypeFunctions.derivationId(this.result);
                break;
            case logistic:
                this.derivative = UnitTypeFunctions.derivationLogistic(this.result);
                break;
            case tanh:
                this.derivative = UnitTypeFunctions.derivationTanh(this.result);
                break;
            case heaviside:
                this.derivative = UnitTypeFunctions.derivationHeaviside(this.result);
                break;
            case stepfun:
                this.derivative = UnitTypeFunctions.derivationPerceptronStepfun(this.result);
                break;
        }
        return this.derivative;
    }

    /**
     * Sets the unit type used in compute
     * @param unitType unitType
     */
    public void setUnitType(UnitType unitType)
    {
        this.unitType = unitType;
    }

    /**
     * Sets unit type and threshold used in compute
     * @param unitType unitType
     * @param threshold threshold
     */
    public void setUnitType(UnitType unitType, double threshold)
    {
        setUnitType(unitType);
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        String result = "";

        result += "unit: "+ unitType + ", ";
        if(unitType.toString().equals("stepfun"))
            result += this.threshold + ", ";

        return result;

    }

}
