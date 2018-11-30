package voiture;

import controleur.UpdateCircuitListener;
import controleur.UpdateResetCoursListener;
import circuit.Circuit;
import circuit.Vecteur;
import strategy.Commande;

public interface Voiture extends UpdateCircuitListener, UpdateResetCoursListener{
	
	 // pour le pilotage
    public void drive(Commande c) throws VoitureException;
    public double getMaxTurn();

    // pour l'observation
    public double getVitesse();
    public Vecteur getPosition();
    public Vecteur getDirection();
    public double getBraquage();
    public void resetVoiture(Circuit circuit);
    
}
