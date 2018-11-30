package mains;

import algorithmeGenetique.CrossingOperator;
import algorithmeGenetique.CrossingOperatorImpl;
import algorithmeGenetique.FitnessOperator;
import algorithmeGenetique.FitnessOperatorImplDouble5Param;
import algorithmeGenetique.GeneticAlgorithm;
import algorithmeGenetique.Genome;
import algorithmeGenetique.GenomeGenerator;
import algorithmeGenetique.GenomeGeneratorImplDouble5Param;
import algorithmeGenetique.MutationOperator;
import algorithmeGenetique.MutationOperatorImplDouble;
import algorithmeOutil.FileTools;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;



public class MainAlgorithmeGenetique5Param {
	
	static String nomCircuit = "1_safe";
	static String savePlace = "fichierDeParametre/";
	static String saveMode = "Parametre5";

	public static void main(String[] args) {
		Double[] sigma = {1.0, 1.0, 1.0, 1.0, 1.0};
		Circuit c = CircuitFactoryFromFile.CircuitFormFile(nomCircuit+".trk");

		int populationSize = 200;
		double rate  = 0.9; // pour la mutation
		MutationOperator<Double> muteOp = new MutationOperatorImplDouble(rate, sigma);
		CrossingOperator<Double> crossOp = new CrossingOperatorImpl<Double>();
		FitnessOperator<Double> fitnessOp = new FitnessOperatorImplDouble5Param(c);
		GenomeGenerator<Double> genomeGn = new GenomeGeneratorImplDouble5Param(FileTools.loadParam(savePlace+nomCircuit+saveMode)); 
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