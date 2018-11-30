package mains;

import java.awt.Dimension;

import javax.swing.JLabel;

import radar.Radar;
import radar.RadarDijkstra;
import radar.RadarPointaPointStrict;
import strategy.Strategy;
import strategy.StrategyComposite;
import strategy.StrategyParametriquePrevoyance;
import strategy.StrategyRadar;
import view.CircuitObserveurSWING;
import view.IHMSwing;
import view.NbcoupObserveurSWING;
import view.ObserveurSWING;
import view.PointapointObserveurSWING;
import view.TrajetObserveurSWING;
import view.Views;
import view.VoitureObserveurSWING;
import voiture.Voiture;
import voiture.VoitureFactory;
import algorithmeOutil.Dijkstra;
import algorithmeOutil.FileTools;
import algorithmeOutil.Prevoyance;
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
import controleur.UpdateParamListener;
import controleur.UpdateResetCoursListener;

public class MainSWINGStrategyComposite {
	
	static String nomCircuit = "1_safe";
	static String loadPlace = "fichierDeParametre/";
	static String loadMode = "ParametrePrevoyance";

	public static void main(String[] args) {
		double[] thetas = {Math.PI/2, Math.PI/3,Math.PI/4, Math.PI/6, Math.PI/12, Math.PI/17, Math.PI/24, 0, -Math.PI/24, -Math.PI/17, -Math.PI/12, -Math.PI/6,-Math.PI/4, -Math.PI/3, -Math.PI/2};

		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile(nomCircuit+".trk");
		Voiture voiture = VoitureFactory.voitureMake(circuit);

		IHMSwing ihmSWING = new IHMSwing();

		Dijkstra dijkstra = new Dijkstra(circuit);
		Radar radarPoint = new RadarPointaPointStrict(voiture, circuit, ihmSWING);
		Radar radarDij = new RadarDijkstra(thetas, voiture, circuit, dijkstra);
		Strategy strategyPoint = new StrategyRadar(radarPoint, voiture);		
		Prevoyance prevoyance = new Prevoyance(voiture, dijkstra);
		Strategy strategyPrev = new StrategyParametriquePrevoyance(radarDij, voiture, circuit, prevoyance);
		((StrategyParametriquePrevoyance)strategyPrev).setParam(FileTools.loadParam(loadPlace+nomCircuit+loadMode));

		StrategyComposite strategy = new StrategyComposite();
		strategy.add(strategyPoint);
		strategy.add(strategyPrev);

		Simulation simul = new Simulation(circuit, voiture, strategy);
		simul.setSaveParamOption(loadPlace, loadMode);
		
		Views views = new Views("Course");

		ObserveurSWING circuitObserveurSWING = new CircuitObserveurSWING(circuit);//affichage couche par couche dans l ordre
		ObserveurSWING voitureObserveurSWING = new VoitureObserveurSWING(voiture);//assurer que une couche recouvre tous les autres
		ObserveurSWING nbCoupObserveurSWING = new NbcoupObserveurSWING(simul);
		ObserveurSWING pointObserveurSWING = new PointapointObserveurSWING();
		ObserveurSWING trajetObserveuerSWING = new TrajetObserveurSWING(voiture);

		ihmSWING.add(circuitObserveurSWING);
		ihmSWING.add(trajetObserveuerSWING);
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
		controlClickedPointAPoint.add((ClickedPointAPointListener) radarPoint);
		controlClickedPointAPoint.add((ClickedPointAPointListener) pointObserveurSWING);
		ControlFiltreComboBox controlFiltreComboBox = new ControlFiltreComboBox(circuit);

		views.buld(ihmSWING, controlButton, controlMapComboBox, controlFiltreComboBox, (JLabel)nbCoupObserveurSWING);
		
		simul.add((UpdateEventListener)ihmSWING);
		
		simul.add((UpdateResetCoursListener)radarPoint);
		simul.add((UpdateResetCoursListener)voiture);
		simul.add((UpdateResetCoursListener)trajetObserveuerSWING);
		
		simul.add((UpdateParamListener)strategyPrev);

		
		simul.add((UpdateCircuitListener)voiture);
		simul.add((UpdateCircuitListener)pointObserveurSWING);
		simul.add((UpdateCircuitListener)ihmSWING); 
		simul.add((UpdateCircuitListener)dijkstra);// le dijkstra doit etre avant le radar
		simul.add((UpdateCircuitListener)radarPoint);
		simul.add((UpdateCircuitListener)radarDij);
		simul.add((UpdateCircuitListener)circuitObserveurSWING);
	}
}
