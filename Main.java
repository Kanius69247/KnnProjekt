import NeuronalNetwork.NeuronalNetwork;

import java.util.Arrays;

public class Main
{
	public static void main(String[] args)
	{
		NeuronalNetwork nn = new NeuronalNetwork();

		int[] struktur = {2,3,4,5};
 		nn.create( struktur );
 		// falls nicht anders vorgegeben:  
 		// - sollen alle Einheiten die Identitätsfunktion als Ausgabefunktion verwenden
 		// - sollen die Gewichte zufällig mit Werten zwschen -1 und 1 initialisiert werden
 		
		System.out.println( nn );
 		// toString soll Netzkonfiguration (Ebenenstruktur und Gewichte) ausgeben
 		
		int[] strukturAND = {2,1}; // BIAS-NeuronalNetwork.Neuron intern verwaltet
 		nn.create( strukturAND );
 		double [][][] w = nn.getWeights();

		nn.setUnitType(1, 0, "logistic");
		nn.setUnitType(1, 0, "stepfun", 1.5);

		nn.initializeRandomWeights();

		System.out.println( nn );
		System.out.println( "Before weight setting: "+ Arrays.deepToString(w) );
 		w[0][0][0] = 1.0;
 		w[0][1][0] = 1.0;
		w[0][2][0] = 0.0; //Bias

 		nn.setWeights(w);
		System.out.println( "After weight setting: "+ Arrays.deepToString(nn.getWeights()) );

 		
 		double[] x = { 1.0, 1.0 } ;
 		double[] yout = nn.compute( x );

 		double [][] data = {
 				{ 0.0, 0.0 },
 				{ 0.0, 1.0 },
 				{ 1.0, 0.0 },
 				{ 1.0, 1.0 }				
 		};
 		
 		double [][] out = nn.computeAll( data );

	}
}
