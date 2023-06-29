package NeuronalNetwork;

public class TrainingResult {
    public TrainingResult(int epoche, double error)
    {
        this.epcho = epoche;
        this.error = error;
    }

    public int epcho = 0;
    public double error = 0;
}
