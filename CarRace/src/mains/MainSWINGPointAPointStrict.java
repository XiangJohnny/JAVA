package mains;

import java.awt.Dimension;

import javax.swing.JLabel;

import radar.Radar;
import radar.RadarPointaPointStrict;
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
import algorithmeOutil.Simulation;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import controleur.ClickedPointAPointListener;
import controleur.ControlButton;
import controleur.ControlClickedPointAPoint;
import controleur.ControlFiltreComboBox;
import controleur.ControlMapComboBox;
import controleur.UpdateCircuitListener;
import controleur.UpdateEventListener;
import controleur.UpdateResetCoursListener;

public class MainSWINGPointAPointStrict {
	//la voiture se dirige vers le point et la atteint dans l ordre des clicks des points

	public static void main(String[] args) {
		
		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile("1_safe.trk");
		Voiture voiture = VoitureFactory.voitureMake(circuit);
		
		IHMSwing ihmSWING = new IHMSwing();


		Radar radar = new RadarPointaPointStrict(voiture, circuit, ihmSWING);
		Strategy strategy = new StrategyRadar(radar, voiture);
		
		Simulation simul = new Simulation(circuit, voiture, strategy);
		
		Views views = new Views("Course");
		
		ObserveurSWING circuitObserveurSWING = new CircuitObserveurSWING(circuit);//affichage couche par couche couche dans l ordre
		ObserveurSWING voitureObserveurSWING = new VoitureObserveurSWING(voiture);//assurer que une couche recouvre tous les autres
		ObserveurSWING nbCoupObserveurSWING = new NbcoupObserveurSWING(simul);
		ObserveurSWING pointObserveurSWING = new PointapointObserveurSWING();
		ihmSWING.add(circuitObserveurSWING);
		ihmSWING.add(voitureObserveurSWING); 
		ihmSWING.add(nbCoupObserveurSWING);
		ihmSWING.add(pointObserveurSWING);
		ihmSWING.setPreferredSize(new Dimension(circuit.getWidth(), circuit.getHeight()));
		
		ControlButton controlButton = new ControlButton();
		controlButton.setModel(circuit, simul, ihmSWING);
		ControlMapComboBox controlMapComboBox = new ControlMapComboBox();
		controlMapComboBox.setModel(simul);
		ControlClickedPointAPoint controlClickedPointAPoint = new ControlClickedPointAPoint(ihmSWING);
		ihmSWING.addMouseListener(controlClickedPointAPoint);
		controlClickedPointAPoint.add((ClickedPointAPointListener) ihmSWING);
		controlClickedPointAPoint.add((ClickedPointAPointListener) radar);
		controlClickedPointAPoint.add((ClickedPointAPointListener) pointObserveurSWING);
		ControlFiltreComboBox controlFiltreComboBox = new ControlFiltreComboBox(circuit);

		views.buld(ihmSWING, controlButton, controlMapComboBox, controlFiltreComboBox, (JLabel)nbCoupObserveurSWING);
		
		simul.add((UpdateEventListener)ihmSWING);
		
		simul.add((UpdateResetCoursListener)voiture);
		simul.add((UpdateResetCoursListener)radar);
		
		simul.add((UpdateCircuitListener)voiture);
		simul.add((UpdateCircuitListener)pointObserveurSWING);
		simul.add((UpdateCircuitListener)ihmSWING); 
		simul.add((UpdateCircuitListener)radar);
		simul.add((UpdateCircuitListener)circuitObserveurSWING);	
	}
}