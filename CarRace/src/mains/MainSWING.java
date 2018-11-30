package mains;


import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import radar.Radar;
import radar.RadarClassique;
import radar.RadarDijkstra;
import radar.RadarPointaPoint;
import strategy.Strategy;
import strategy.StrategyRadar;
import view.CircuitObserveurSWING;
import view.IHMSwing;
import view.NbcoupObserveurSWING;
import view.ObserveurSWING;
import view.PointapointObserveurSWING;
import view.Views;
import view.VoitureObserveurSWING;
import voiture.Voiture;
import voiture.VoitureFactory;
import algorithmeOutil.Dijkstra;
import algorithmeOutil.Simulation;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import controleur.ControlButton;
import controleur.ControlFiltreComboBox;
import controleur.ControlMapComboBox;
import controleur.UpdateCircuitListener;
import controleur.UpdateEventListener;
import controleur.UpdateResetCoursListener;

public class MainSWING {
	
	// on test ici le radar classique
	//pour le dijkastra decommente

	public static void main(String[] args) {
		double[] thetas = {Math.PI/2, Math.PI/3,Math.PI/4, Math.PI/6, Math.PI/12, Math.PI/17, Math.PI/24, 0, -Math.PI/24, -Math.PI/17, -Math.PI/12, -Math.PI/6,-Math.PI/4, -Math.PI/3, -Math.PI/2};

		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile("1_safe.trk");
		Voiture voiture = VoitureFactory.voitureMake(circuit);
		
		IHMSwing ihmSWING = new IHMSwing();


		//Dijkstra dijkstra = new Dijkstra(circuit);
		Radar radar = new RadarClassique(thetas, voiture, circuit);
		//Radar radar = new RadarDijkstra(thetas, voiture, circuit, dijkstra);
		Strategy strategy = new StrategyRadar(radar, voiture);

		
		Simulation simul = new Simulation(circuit, voiture, strategy);
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
		
		simul.add((UpdateCircuitListener)voiture);
		simul.add((UpdateCircuitListener)ihmSWING); 
		//simul.add((UpdateCircuitListener)dijkstra);// le dijkstra doit etre avant le radar
		simul.add((UpdateCircuitListener)radar);
		simul.add((UpdateCircuitListener)circuitObserveurSWING);
	}
	
}
