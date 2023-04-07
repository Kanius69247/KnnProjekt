
public class Neuron
{
    private String unitType;
    private double threshold;

    public Neuron()
    {

    }

    //region public methods

    /**
     * computes the output of the cell
     * @param input input value
     * @return output value (input * weight)
     */
    public double compute(double input)
    {
        if(input > threshold) //keine ahnung ob das stimmt
            return 1.0;
        else
            return 0.0;
    }

    public void setUnitType(String unitType)
    {
        this.unitType = unitType;
    }

    public void setUnitType(String unitType, double threshold)
    {
        this.unitType = unitType;
        this.threshold = threshold;
    }


    @Override
    public String toString()
    {
        return "unit: "+ unitType + ", ";
    }

    //endregion

    //region private methods

    private double id(double id)
    {
        return 0.0;
    }

    private double logistic(double a)
    {
        return 0.0;
    }

    private double perceptionStepfun(double data)
    {
        return 0.0;
    }

    //endregion

}
