
public class Neuron
{
    private String unitType; //Still not sure if this decides which function is used
    private double threshold;

    public Neuron()
    {
        unitType = "indentfun"; //default value should be identity function
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
            case "stepfun":
                //compute something
                result = perceptionStepfun(input);
                break;

            case "indentfun":
                result = id(input); //compute something
                break;

            case "tanh":
                result = tanh(input);
                break;

            case "logistic":
                result = logistic(input);
                break;
        }

        if(result > threshold) //is Threshold only for perception stepfun?
            return 1.0;
        else
            return 0.0;
    }

    /**
     * Sets the unit type used in compute
     * @param unitType unitType
     */
    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    /**
     * Sets unit type and threshold used in compute
     * @param unitType unitType
     * @param threshold threshold
     */
    public void setUnitType(String unitType, double threshold)
    {
        this.unitType = unitType;
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return "unit: "+ unitType + ", Threshold: " + threshold;
    }

    private double id(double id)
    {
        return 0.0;
    }

    private double logistic(double input)
    {
        return 0.0; 
    }

    private double tanh(double input)
    {
        return Math.tanh(input);
    }

    private double perceptionStepfun(double data)
    {
        return 0.0;
    }

}
