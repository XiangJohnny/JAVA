package mains;

import algorithmeGenetique.CrossingOperator;
import algorithmeGenetique.CrossingOperatorImpl;
import algorithmeGenetique.FitnessOperator;
import algorithmeGenetique.FitnessOperatorImplPrevoyance;
import algorithmeGenetique.GeneticAlgorithm;
import algorithmeGenetique.Genome;
import algorithmeGenetique.GenomeGenerator;
import algorithmeGenetique.GenomeGeneratorImplPrevoyance;
import algorithmeGenetique.MutationOperator;
import algorithmeGenetique.MutationOperatorImplDouble;
import algorithmeOutil.FileTools;
import algorithmeOutil.FiltreTools;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;


public class MainAlgorithmeGenetiqueprevoyance {
	
	static String nomCircuit = "2_safe";
	static String savePlace = "fichierDeParametre/";
	static String saveMode = "ParametrePrevoyance";

	public static void main(String[] args) {
		Double[] sigma  = {1.0, 0.25, 1.0, 0.25, 1.0, 1.0, 0.25, 0.25, 1.0};
		String filename = nomCircuit+".trk";
		Circuit c = CircuitFactoryFromFile.CircuitFormFile(filename);

		FiltreTools.filtre(c.getMatrice(), FiltreTools.getFiltreDiago(), FiltreTools.getFiltreDiago_do());
		FiltreTools.filtre(c.getMatrice(), FiltreTools.getFiltreFace(), FiltreTools.getFiltreFace_do());
		
		int populationSize = 200;
		double rate  = 0.1; // pour la mutation
		CrossingOperator<Double> crossOp = new CrossingOperatorImpl<Double>();
		FitnessOperator<Double> fitnessOp = new FitnessOperatorImplPrevoyance(c);
		
		GenomeGenerator<Double> genomeGn = new GenomeGeneratorImplPrevoyance(FileTools.loadParam(savePlace+nomCircuit+saveMode)); 
		
		MutationOperator<Double> muteOp = new MutationOperatorImplDouble(rate, sigma);
		muteOp.setParamMin(genomeGn.getParamMin());
		muteOp.setParamMax(genomeGn.getParamMax());
		
				
		GeneticAlgorithm<Double> ga = new GeneticAlgorithm<Double>(populationSize, genomeGn, muteOp, crossOp, fitnessOp);
		Genome<Double> genomeOptimal = ga.optimize(15);

		System.out.println("Best : "+genomeOptimal.getFitness());
		for(int i=0; i<genomeOptimal.size(); i++) System.out.print(genomeOptimal.get(i)+", ");
		System.out.println();
		
		FileTools.saveParam(genomeOptimal, savePlace+nomCircuit+saveMode);
		
	}
}