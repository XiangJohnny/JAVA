package mains;
import java.awt.Dimension;

import javax.swing.JLabel;

import radar.Radar;
import radar.RadarDijkstra;
import strategy.Strategy;
import strategy.StrategyParametrique8Param;
import view.CircuitObserveurSWING;
import view.IHMSwing;
import view.NbcoupObserveurSWING;
import view.ObserveurSWING;
import view.Views;
import view.VoitureObserveurSWING;
import voiture.Voiture;
import voiture.VoitureFactory;
import algorithmeOutil.Dijkstra;
import algorithmeOutil.FileTools;
import algorithmeOutil.Simulation;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import controleur.ControlButton;
import controleur.ControlFiltreComboBox;
import controleur.ControlMapComboBox;
import controleur.UpdateCircuitListener;
import controleur.UpdateEventListener;
import controleur.UpdateParamListener;
import controleur.UpdateResetCoursListener;

public class MainSWINGDijkastra8Param {
	
	static String nomCircuit = "1_safe";
	static String loadPlace = "fichierDeParametre/";
	static String loadMode = "Parametre8";


	public static void main(String[] args) {
		double[] thetas = {Math.PI/2, Math.PI/3,Math.PI/4, Math.PI/6, Math.PI/12, Math.PI/17, Math.PI/24, 0, -Math.PI/24, -Math.PI/17, -Math.PI/12, -Math.PI/6,-Math.PI/4, -Math.PI/3, -Math.PI/2};
				
		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile(nomCircuit+".trk");
		Voiture voiture = VoitureFactory.voitureMake(circuit);

		IHMSwing ihmSWING = new IHMSwing();


		Dijkstra dijkstra = new Dijkstra(circuit);
		Radar radar = new RadarDijkstra(thetas, voiture, circuit, dijkstra);
		Strategy strategy = new StrategyParametrique8Param(radar, voiture, circuit);
		((StrategyParametrique8Param)strategy).setParam(FileTools.loadParam(loadPlace+nomCircuit+loadMode));


		Simulation simul = new Simulation(circuit, voiture, strategy);
		simul.setSaveParamOption(loadPlace, loadMode);
		Views views = new Views("Course");

		ObserveurSWING circuitObserveurSWING = new CircuitObserveurSWING(circuit);//affichage couche par couche couche dans l ordre
		ObserveurSWING voitureObserveurSWING = new VoitureObserveurSWING(voiture);//assurer que une couche recouvre tous les autres
		ObserveurSWING nbCoupObserveurSWING = new NbcoupObserveurSWING(simul);

		ihmSWING.add(circuitObserveurSWING);
		ihmSWING.add(voitureObserveurSWING); 
		ihmSWING.add(nbCoupObserveurSWING);
		ihmSWING.setPreferredSize(new Dimension(circuit.getWidth(), circuit.getHeight()));

		ControlButton controlButton = new ControlButton();
		controlButton.setModel(circuit, simul, ihmSWING);
		ControlMapComboBox controlMapComboBox = new ControlMapComboBox();
		controlMapComboBox.setModel(simul);
		ControlFiltreComboBox controlFiltreComboBox = new ControlFiltreComboBox(circuit);

		views.buld(ihmSWING, controlButton, controlMapComboBox, controlFiltreComboBox, (JLabel)nbCoupObserveurSWING);
		
		simul.add((UpdateEventListener)ihmSWING);

		simul.add((UpdateResetCoursListener)voiture);
		
		simul.add((UpdateParamListener)strategy);

		simul.add((UpdateCircuitListener)voiture);
		simul.add((UpdateCircuitListener)ihmSWING); 
		simul.add((UpdateCircuitListener)dijkstra);// le dijkstra doit etre avant le radar
		simul.add((UpdateCircuitListener)radar);
		simul.add((UpdateCircuitListener)circuitObserveurSWING);
	}

}
