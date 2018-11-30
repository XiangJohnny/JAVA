package controleur;

import javax.swing.JComboBox;

import algorithmeOutil.FiltreTools;
import circuit.Circuit;

public class ControlFiltreComboBox implements UpdateCircuitListener {
	private JComboBox filtreComboBox;
	private Circuit circuit;
	
	public ControlFiltreComboBox(Circuit circuit){
		this.circuit = circuit;
	}
	
	public void setFiltreComboBox(JComboBox filtreComboBox){
		this.filtreComboBox = filtreComboBox;
	}
	public void filtrer(){
		String s = (String)filtreComboBox.getSelectedItem();
		if(s.equals("filtre diago")){
			FiltreTools.filtre(circuit.getMatrice(), FiltreTools.getFiltreDiago(), FiltreTools.getFiltreDiago_do());
			
		}
		else if(s.equals("filtre licer")){
			FiltreTools.filtre(circuit.getMatrice(), FiltreTools.getFiltreFace(), FiltreTools.getFiltreFace_do());
		}
	}

	@Override
	public void circuitUpdate(Circuit circuit) {
		this.circuit = circuit;
	}

}
