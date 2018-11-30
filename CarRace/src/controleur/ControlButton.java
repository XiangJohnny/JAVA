package controleur;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import view.IHMSwing;
import algorithmeOutil.FileTools;
import algorithmeOutil.Simulation;
import circuit.Circuit;

public class ControlButton implements ActionListener {

	private Simulation sim;
	private IHMSwing ihmSWING;
	private Circuit circuit;
	private JButton start;
	private JButton reset;
	private JButton resume;
	private JButton pause;
	private JButton filtrer;
	private JButton saveCommande;
	private JButton saveImage;
	private JButton quit;
	
	private ControlFiltreComboBox controlFiltreComboBox;
	
	public ControlButton(){
		
	}

	public ControlButton(Simulation sim, IHMSwing ihmSWING, JButton start, JButton reset, JButton resume, JButton pause, JButton filtrer, JButton saveCommande, JButton quit){
		this.start = start;
		this.reset = reset;
		this.resume = resume;
		this.pause = pause;
		this.filtrer = filtrer;
		this.saveCommande = saveCommande;
		this.quit = quit;
		this.sim = sim;
		this.ihmSWING = ihmSWING;
	}
	
	public void setModel(Circuit circuit, Simulation sim, IHMSwing ihmSWING){
		this.circuit = circuit;
		this.sim = sim;
		this.ihmSWING = ihmSWING;
	}
	
	public void setButton(JButton start, JButton reset, JButton resume, JButton pause, JButton filtrer, JButton saveCommande, JButton saveImage, JButton quit){
		this.start = start;
		this.reset = reset;
		this.resume = resume;
		this.pause = pause;
		this.filtrer = filtrer;
		this.saveCommande = saveCommande;
		this.saveImage = saveImage;
		this.quit = quit;
	}
	
	public void setControlComboBoxFiltre(ControlFiltreComboBox controlFiltreComboBox){
		this.controlFiltreComboBox = controlFiltreComboBox;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals(start.getText())){
			start.setEnabled(false);
			reset.setEnabled(true);
			resume.setEnabled(false);
			pause.setEnabled(true);
			sim.commencer();
		}
		else if(arg0.getActionCommand().equals(reset.getText())){
			start.setEnabled(true);
			reset.setEnabled(false);
			resume.setEnabled(false);
			pause.setEnabled(false);
			sim.reset();
		}
		else if(arg0.getActionCommand().equals(resume.getText())){
			//repend la course
			resume.setEnabled(false);
			pause.setEnabled(true);
			sim.resume();
		}
		else if(arg0.getActionCommand().equals(pause.getText())){
			//stop la course
			resume.setEnabled(true);
			pause.setEnabled(false);
			sim.pause();
		}
		else if(arg0.getActionCommand().equals(filtrer.getText())){
			controlFiltreComboBox.filtrer();
			sim.updateCircuit();
			sim.reset();
		}
		else if(arg0.getActionCommand().equals(saveCommande.getText())){
			saveCommande.setEnabled(true);
			FileTools.saveListeCommande(sim.getListeCommande(), circuit.getName()+"ListComande");
		}
		else if(arg0.getActionCommand().equals(saveImage.getText())){
			BufferedImage image = new BufferedImage(ihmSWING.getWidth(), ihmSWING.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			ihmSWING.paint(g);
			FileTools.writeImage(circuit.getName()+"Course.png", image);			
		}
		else if(arg0.getActionCommand().equals(quit.getText())){
			//sortir du programme
			quit.setEnabled(false);
			System.exit(0);
		}
	}

}
