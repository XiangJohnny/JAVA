package controleur;

public interface ClickedPointAPointSender {
	
	public void add(ClickedPointAPointListener listener);
	public void updateClicked(int x, int y);

}
