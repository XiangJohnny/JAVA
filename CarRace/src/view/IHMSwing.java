package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controleur.ClickedPointAPointListener;
import controleur.UpdateCircuitListener;
import controleur.UpdateEventListener;
import circuit.Circuit;

@SuppressWarnings("serial")
public class IHMSwing extends JPanel implements UpdateEventListener, UpdateCircuitListener, ClickedPointAPointListener{

	private ArrayList<ObserveurSWING> observeurs = new ArrayList<ObserveurSWING>();
	public IHMSwing(){}

	public void add(ObserveurSWING observeur) {
		observeurs.add(observeur);
	}

	@Override
	public void manageUpdate() {
		//repaint();
		try {      // temporisation (sinon, on ne voit rien)
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});
	}


	public void paint(Graphics g){
		super.paint(g);

		for(ObserveurSWING o: observeurs)
			o.print(g);
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		this.setPreferredSize(new Dimension(circuit.getWidth(), circuit.getHeight()));
		manageUpdate();
	}

	/*public void mouseClick(int x, int y) {
		Graphics stylo = this.getGraphics();
		stylo.setColor(Color.BLACK);
		stylo.fillOval(x-3, y-3, 6, 6);
	}*/

	@Override
	public void clickedUpdate(int x, int y) {
		Graphics stylo = this.getGraphics();
		stylo.setColor(Color.BLACK);
		stylo.fillOval(y-3, x-3, 6, 6);		
	}

}
