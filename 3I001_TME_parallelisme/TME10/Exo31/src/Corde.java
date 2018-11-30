
public interface Corde {
	
	public abstract void acceder(Position p, int poids) throws InterruptedException, OutOfCapacityOfCorde;
	
	public abstract void lacher(Position p, int poids) throws InterruptedException;
}
