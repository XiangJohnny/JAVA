package radar;

import controleur.UpdateCircuitListener;
import circuit.Circuit;

public interface Radar extends UpdateCircuitListener{
	public double[] scores(); // score de chaque branche
	public double[] distancesInPixels(); // pour l'observer
	public int getBestIndex(); // meilleur indice
	public double[] thetas(); // angles de chaque faisceau
	
	public void circuitUpdate(Circuit circuit);
}
