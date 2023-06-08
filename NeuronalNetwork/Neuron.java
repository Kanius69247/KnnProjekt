package NeuronalNetwork;

public class Neuron
{
    private UnitType unitType;
    private double threshold;
    private double derivative;
    private double result;

    /**
     * Initializes a new instance of Neuron
     */
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
            case relu:
                this.result = UnitTypeFunctions.rectifiedLinearUnit(input);
                break;
            case sigmoid:
                this.result = UnitTypeFunctions.sigmoid(input);
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
            case relu:
                this.derivative = UnitTypeFunctions.derivationRectifiedLinearUnit(this.result);
                break;
            case sigmoid:
                this.derivative = UnitTypeFunctions.derivativeSigmoid(this.result);
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

    /**
     * returns a string representation of the neuron
     * @return neuron data as string
     */
    @Override
    public String toString()
    {
        String result = "";

        result += "Unit: "+ unitType;
        if(unitType == unitType.stepfun || unitType == unitType.heaviside){
        result += " Threshold: " + this.threshold;
    }
        result = result + " Result: "+ this.result;
        return result;

    }

}
