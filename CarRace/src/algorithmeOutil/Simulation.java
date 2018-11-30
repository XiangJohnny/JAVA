package algorithmeOutil;

import java.util.ArrayList;

import controleur.UpdateCircuitListener;
import controleur.UpdateCircuitSender;
import controleur.UpdateEventListener;
import controleur.UpdateEventSender;
import controleur.UpdateParamListener;
import controleur.UpdateParamSender;
import controleur.UpdateResetCoursListener;
import controleur.UpdateResetCoursSender;
import strategy.Commande;
import strategy.Strategy;
import voiture.Voiture;
import voiture.VoitureException;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;

public class Simulation implements UpdateEventSender, UpdateCircuitSender, UpdateResetCoursSender, UpdateParamSender{
	Thread t = null;
	private int nbcoup = 0;
	private boolean crash;
	private boolean finish;
	Circuit circuit;
	Voiture voiture;
	Strategy strategy;
	
	private String loadPlace = null;
	private String loadMode = null;
	ArrayList<Commande> listeCommande = new ArrayList<Commande>();
	private ArrayList<UpdateEventListener> eventListeners = new ArrayList<UpdateEventListener>();
	private ArrayList<UpdateCircuitListener> circuitListeners = new ArrayList<UpdateCircuitListener>();
	private ArrayList<UpdateResetCoursListener> resetCoursListeners = new ArrayList<UpdateResetCoursListener>();
	private ArrayList<UpdateParamListener> paramListeners = new ArrayList<UpdateParamListener>();


	public Simulation(Circuit circuit, Voiture voiture, Strategy strategy){
		super();
		this.circuit = circuit;
		this.voiture = voiture;
		this.strategy = strategy;
		crash = false;
		finish = false;
	}


	public void play() throws VoitureException{
		Commande c;
		updateEvent();

		while(!crash  && !finish){
			c = strategy.getCommande();

			listeCommande.add(c);
			nbcoup++;
			voiture.drive(c);
			updateEvent();

			if(TerrainTools.isRunnable(circuit.getTerrain(voiture.getPosition())) == false){
				crash = true;
			}
			if(circuit.getTerrain(voiture.getPosition()) == Terrain.EndLine){
				finish = true;
			}
		}
		if(crash){
			System.out.println("La voiture se crash!!!!!!!!!!!!!!!!.");
			//throw new VoitureException("La voiture se crash");
		}
		if(finish){
			if(voiture.getDirection().produitScalaire(circuit.getDirectionArrivee()) < 0){
				System.out.println("Passage sur la ligne d arrive avec mauvaise direction.");
			}
			else {
				System.out.println("La course est reusie en "+nbcoup+" coups.");
			}
		}
	}

	public ArrayList<Commande> getListeCommande(){
		return listeCommande;
	}

	public int getCoup(){
		return nbcoup;
	}

	public void commencer(){
		t = new Thread(){
			public void run() {
				try {
					play();
				} catch (VoitureException e) {
					System.out.println(e);
				}
			}
		};
		t.start();
	}

	public void resume(){
		t = new Thread(){
			public void run() {
				try {
					play();
				} catch (VoitureException e) {
					System.out.println(e);
				}
			}
		};
		t.start();
	}

	@SuppressWarnings("deprecation")
	public void pause(){
		if(t != null && t.isAlive()){
			t.stop();
		}
	}

	@SuppressWarnings("deprecation")
	public void reset(){
		if( t != null && t.isAlive()){
			t.stop();
		}
		nbcoup = 0;
		crash = false;
		finish = false;
		listeCommande.clear();
		updateEvent();
		updateResetCours();
	}

	public void setCircuit(Circuit circuit){
		this.circuit.setCircuit(circuit);
		voiture.resetVoiture(this.circuit);
		updateEvent();
		updateCircuit();
		updateParam();
	}
	
	@Override
	public void add(UpdateEventListener listener) {
		eventListeners.add(listener);
	}

	@Override
	public void updateEvent() {
		for(UpdateEventListener listener: eventListeners){
			listener.manageUpdate();
		}
	}

	@Override
	public void add(UpdateCircuitListener listener) {
		circuitListeners.add(listener);
	}


	@Override
	public void updateCircuit() {
		for(UpdateCircuitListener listener : circuitListeners){
			listener.circuitUpdate(circuit);
		}
	}


	@Override
	public void add(UpdateResetCoursListener listener) {
		resetCoursListeners.add(listener);
	}


	@Override
	public void updateResetCours() {
		for(UpdateResetCoursListener listener: resetCoursListeners){
			listener.coursResetUpdate(circuit);
		}
	}


	@Override
	public void add(UpdateParamListener listener) {
		paramListeners.add(listener);
	}


	@Override
	public void updateParam() {
		System.out.println(loadPlace+circuit.getName()+loadMode);
		for(UpdateParamListener listener: paramListeners){
			listener.paramUpdate(loadPlace+circuit.getName()+loadMode);
		}
		
	}
	
	public void setSaveParamOption(String loadPlace, String loadMode){
		this.loadPlace = loadPlace;
		this.loadMode = loadMode;
	}


}