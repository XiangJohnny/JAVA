package voiture;

import circuit.Circuit;
import circuit.Vecteur;


public class VoitureFactory {
	
	public static VoitureImpl voitureMake(Circuit circuit){
		return new VoitureImpl(new Vecteur(circuit.getPointDepart()), new Vecteur(circuit.getDirectionDepart()));
	}
	
	public static VoitureImpl voitureMake(){
		return new VoitureImpl(new Vecteur(0, 0), new Vecteur(0, 0));
	}

}
