package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import view.IHMSwing;

public class ControlClickedPointAPoint implements MouseListener, ClickedPointAPointSender {
	
	private IHMSwing ihmSWING;
	private ArrayList<ClickedPointAPointListener> listenersPointAPoint = new ArrayList<ClickedPointAPointListener>();
	
	public ControlClickedPointAPoint(IHMSwing ihmSWING) {
		this.ihmSWING = ihmSWING;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(ihmSWING.contains(arg0.getPoint())){
			updateClicked(arg0.getY(), arg0.getX());
		}
	}
	
	@Override
	public void add(ClickedPointAPointListener listener) {
		listenersPointAPoint.add(listener);
	}

	@Override
	public void updateClicked(int x, int y) {
		for(ClickedPointAPointListener listener : listenersPointAPoint){
			listener.clickedUpdate(x, y);
		}
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
