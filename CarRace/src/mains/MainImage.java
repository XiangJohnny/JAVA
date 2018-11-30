package mains;

import java.awt.image.BufferedImage;

import algorithmeOutil.Dijkstra;
import algorithmeOutil.FileTools;
import algorithmeOutil.Simulation;
import radar.Radar;
import radar.RadarClassique;
import radar.RadarDijkstra;
import strategy.Strategy;
import strategy.StrategyRadar;
import view.IHMImage;
import view.VoitureObserveurImage;
import voiture.Voiture;
import voiture.VoitureException;
import voiture.VoitureFactory;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;

public class MainImage {
	
	static String nomCircuit = "7_safe";

	public static void main(String[] args) {

/*"1_safe.trk","2_safe.trk","3_safe.trk","4_safe.trk","5_safe.trk","6_safe.trk",
  "7_safe.trk","8_safe.trk","aufeu.trk", "bond_safe.trk", "Een2.trk","labymod.trk",
  "labyperso.trk","perso.trk","t260_safe.trk","t2009.trk
*/

		double[] thetas = {Math.PI/2, Math.PI/3,Math.PI/4, Math.PI/6, Math.PI/12, Math.PI/17, Math.PI/24, 0, -Math.PI/24, -Math.PI/17, -Math.PI/12, -Math.PI/6,-Math.PI/4, -Math.PI/3, -Math.PI/2};

		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile(nomCircuit+".trk");

		Voiture v = VoitureFactory.voitureMake(circuit);

		Radar radar = new RadarClassique(thetas, v, circuit);
//		Dijkstra dijkstra = new Dijkstra(circuit);
//		Radar radar = new RadarDijkstra(thetas, v, circuit, dijkstra);
		
		Strategy strategy = new StrategyRadar(radar, v);
		
		Simulation simul = new Simulation(circuit, v, strategy);
		IHMImage ihmImage = new IHMImage(circuit);
		ihmImage.add(new VoitureObserveurImage(v));
		simul.add(ihmImage);
		
		try {
			simul.play();
		} catch (VoitureException e) {
			e.printStackTrace();
		}
		
		BufferedImage image = ihmImage.getImage();

		FileTools.writeImage("course.png", image);

		FileTools.saveListeCommande(simul.getListeCommande(), nomCircuit+"liste_de_commande");
		
	}

}
