package algorithmeGenetique;

import algorithmeOutil.Dijkstra;
import algorithmeOutil.Simulation;
import radar.RadarDijkstra;
import strategy.StrategyParametrique8Param;
import voiture.Voiture;
import voiture.VoitureException;
import voiture.VoitureFactory;
import circuit.Circuit;

public class FitnessOperatorImplDouble8Param implements FitnessOperator<Double> {
	
	private double[] thetas = {Math.PI/2, Math.PI/3,Math.PI/4, Math.PI/6, Math.PI/12, Math.PI/17, Math.PI/24, 0, -Math.PI/24, -Math.PI/17, -Math.PI/12, -Math.PI/6,-Math.PI/4, -Math.PI/3, -Math.PI/2};
	private Circuit circuit;
	private Dijkstra dij;

	public FitnessOperatorImplDouble8Param(Circuit circuit){
		this.circuit = circuit;
		dij = new Dijkstra(circuit);
	}

	@Override
	public double fit(Genome<Double> g) {
		
		Voiture voiture = VoitureFactory.voitureMake(circuit);
		StrategyParametrique8Param strategy = new StrategyParametrique8Param(new RadarDijkstra(thetas, voiture, circuit, dij), voiture, circuit);
		//StrategyParametrique8Param strategy = new StrategyParametrique8Param(new RadarClassique(thetas, voiture, circuit), voiture, circuit);
		strategy.setParam(conversion(g));
		voiture.resetVoiture(circuit);

		Simulation simul = new Simulation(circuit, voiture, strategy);

		try {
			simul.play();
		} catch (VoitureException e) {
			e.printStackTrace();
		}

		if(dij.getDistance()[(int) voiture.getPosition().getX()][(int) voiture.getPosition().getY()] == 0){
			return -simul.getCoup();
		}
		else{
			return -dij.getDistance()[(int) voiture.getPosition().getX()][(int) voiture.getPosition().getY()] - 100000;
		}
	}

	private Double[] conversion(Genome<Double> g){
		Double[] parametre = new Double[g.size()];
		for(int i = 0; i < g.size(); i++){
			parametre[i] = g.get(i);
		}
		return parametre;
	}

}
