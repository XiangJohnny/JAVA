package voiture;

import circuit.Circuit;
import circuit.Vecteur;
import strategy.Commande;

public class VoitureImpl implements Voiture {
	private double[] tabVitesse     = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.}; 
	private double[] tabTurn        = {1.0,  0.9, 0.75, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.05};


	private double braquage = 0.1;
	private double frotement_sol = 0.0002;
	private double frotement_vent = 0.0005;

	private Vecteur position;
	private Vecteur direction;
	private double vmax = 0.9;
	private double acceleration = 0.005;
	private double vitesse = 0;

	public VoitureImpl(int x_possition, int y_possition, int x_direction, int y_direction){
		this.position = new Vecteur(x_possition, y_possition);
		this.direction = new Vecteur(x_direction, y_direction);
	}

	public VoitureImpl(Vecteur possition, Vecteur direction){
		this((int)possition.getX(), (int)possition.getY(), (int)direction.getX(), (int)direction.getY());
	}

	@Override
	public void drive(Commande c) throws VoitureException {
		if( !(( -1 <= c.getAcc() && c.getAcc() <= 1) && 
				(-getMaxTurn() <= c.getTurn() && c.getTurn() <= getMaxTurn())) ){
			//throw new VoitureException("invalide commande");
			System.out.println("invalide commande");
			System.out.println("acc:"+c.getAcc()+" turn:"+c.getTurn()+"  Maxturn:"+getMaxTurn());
			System.exit(0);
		}

		vitesse -= frotement_sol;
		vitesse -= frotement_vent*vitesse;

		direction.rotation(c.getTurn() * braquage);
		direction.normalisation();
		if(vitesse + c.getAcc()*acceleration > vmax){
			vitesse = vmax;			
		}
		else{
			vitesse += c.getAcc()*acceleration;
			if(vitesse < 0){
				vitesse = 0;
			}
		}
		position.additionModifVecteur(direction.multiParScalaireNew(vitesse));

	}

	@Override
	public double getMaxTurn() {
		for(int i = 0; i < tabVitesse.length; i++){
			if(vitesse <= vmax*tabVitesse[i]){
				return tabTurn[i];
			}	
		}
		return 0;
	}

	@Override
	public double getVitesse() {
		return vitesse;
	}

	@Override
	public Vecteur getPosition() {
		return position;
	}

	@Override
	public Vecteur getDirection() {
		return direction;
	}

	@Override
	public double getBraquage() {
		return braquage;
	}

	@Override
	public void resetVoiture(Circuit circuit){
		position.setX(circuit.getPointDepart().getX());
		position.setY(circuit.getPointDepart().getY());
		direction.setX(circuit.getDirectionDepart().getX());
		direction.setY(circuit.getDirectionDepart().getY());
		vitesse = 0;
	}

	@Override
	public void coursResetUpdate(Circuit circuit) {
		this.resetVoiture(circuit);
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		this.resetVoiture(circuit);		
	}

}
