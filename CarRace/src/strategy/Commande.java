package strategy;

public class Commande {
	private double accl;
	private double turn;
	
	public Commande(double accl, double turn){
		this.accl = accl;
		this.turn = turn;
	}
	
	public double getAcc(){
		return accl;
	}
	
	public double getTurn(){
		return turn;
	}

}
