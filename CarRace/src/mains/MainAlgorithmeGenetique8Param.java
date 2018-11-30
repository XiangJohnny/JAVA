package mains;

import algorithmeGenetique.CrossingOperator;
import algorithmeGenetique.CrossingOperatorImpl;
import algorithmeGenetique.FitnessOperator;
import algorithmeGenetique.GeneticAlgorithm;
import algorithmeGenetique.Genome;
import algorithmeGenetique.GenomeGenerator;
import algorithmeGenetique.GenomeGeneratorImplDouble8Param;
import algorithmeGenetique.MutationOperator;
import algorithmeGenetique.MutationOperatorImplDouble;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import algorithmeGenetique.FitnessOperatorImplDouble8Param;
import algorithmeOutil.FileTools;
import algorithmeOutil.FiltreTools;


public class MainAlgorithmeGenetique8Param {
	
	static String nomCircuit = "2_safe";
	static String savePlace = "fichierDeParametre/";
	static String saveMode = "Parametre8";

	public static void main(String[] args) {
		Double[] sigma  = {1.0, 0.25, 1.0, 0.25, 1.0, 0.25, 0.25, 0.25};
		String filename = nomCircuit+".trk";
		Circuit c = CircuitFactoryFromFile.CircuitFormFile(filename);

		FiltreTools.filtre(c.getMatrice(), FiltreTools.getFiltreDiago(), FiltreTools.getFiltreDiago_do());
		FiltreTools.filtre(c.getMatrice(), FiltreTools.getFiltreFace(), FiltreTools.getFiltreFace_do());
		
		int populationSize = 200;
		double rate  = 0.1; // pour la mutation
		CrossingOperator<Double> crossOp = new CrossingOperatorImpl<Double>();
		FitnessOperator<Double> fitnessOp = new FitnessOperatorImplDouble8Param(c);
		GenomeGenerator<Double> genomeGn = new GenomeGeneratorImplDouble8Param(FileTools.loadParam(savePlace+nomCircuit+saveMode)); 
		MutationOperator<Double> muteOp = new MutationOperatorImplDouble(rate, sigma);
		muteOp.setParamMin(genomeGn.getParamMin());
		muteOp.setParamMax(genomeGn.getParamMax());
		
				
		GeneticAlgorithm<Double> ga = new GeneticAlgorithm<Double>(populationSize, genomeGn, muteOp, crossOp, fitnessOp);
		Genome<Double> res = ga.optimize(15);

		System.out.println("Best : "+res.getFitness());
		for(int i=0; i<res.size(); i++) System.out.print(res.get(i)+", ");
		System.out.println();
		
		FileTools.saveParam(res, savePlace+nomCircuit+saveMode);
	}
}