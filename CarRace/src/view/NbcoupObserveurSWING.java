package view;

import java.awt.Graphics;

import javax.swing.JLabel;

import algorithmeOutil.Simulation;

@SuppressWarnings("serial")
public class NbcoupObserveurSWING extends JLabel implements ObserveurSWING {

	private Simulation sim;
	
	public NbcoupObserveurSWING(Simulation sim){
		this.setText("Nb Coup");
		this.sim = sim;
	}

	@Override
	public void print(Graphics g){
		this.setText("Nb Coup:"+sim.getCoup());
	}
}
