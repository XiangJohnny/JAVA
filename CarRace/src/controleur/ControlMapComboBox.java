package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

import algorithmeOutil.Simulation;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;

public class ControlMapComboBox implements ActionListener {

	private Simulation sim;
	private JButton start;
	private JButton reset;
	private JButton resume;
	private JButton pause;
	private JButton save;
	private JButton quit;

	
	public ControlMapComboBox(){
		
	}
	
	public ControlMapComboBox(Simulation sim, JButton start, JButton reset, JButton resume, JButton pause, JButton save, JButton quit) {
		this.sim = sim;
		this.start = start;
		this.reset = reset;
		this.resume = resume;
		this.pause = pause;
		this.save = save;
		this.quit = quit;
	}
	
	public void setModel(Simulation sim){
		this.sim = sim;
	}
	
	
	public void setButton(JButton start, JButton reset, JButton resume, JButton pause, JButton save, JButton quit){
		this.start = start;
		this.reset = reset;
		this.resume = resume;
		this.pause = pause;
		this.save = save;
		this.quit = quit;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JComboBox cb = (JComboBox)arg0.getSource();
		String s = (String)cb.getSelectedItem();
		s = s+".trk";
		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile(s);

		sim.setCircuit(circuit);;
		start.setEnabled(true);
		reset.setEnabled(false);
		resume.setEnabled(false);
		pause.setEnabled(false);
		save.setEnabled(true);
		quit.setEnabled(true);
		sim.reset();
	}

}
