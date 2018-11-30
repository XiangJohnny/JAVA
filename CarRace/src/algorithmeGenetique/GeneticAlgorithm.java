package algorithmeGenetique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class GeneticAlgorithm<Gene> {

	public static final double MIN_VALUE = -1E10;
	// Attributs
	private int populationSize;
	private GenomeGenerator<Gene> genomeGen;
	private MutationOperator<Gene> muteOp;
	private CrossingOperator<Gene> crossOp;
	private FitnessOperator<Gene> fitnessOp;

	//=======================================================
	// Internal attributs and methods
	private ArrayList<Genome<Gene>> population;
	private int sizeNewPopulation;
	private Random r = new Random();

	// Attributs settings
	public GeneticAlgorithm(int populationSize, GenomeGenerator<Gene> genomeGen,
			MutationOperator<Gene> muteOp, CrossingOperator<Gene> crossOp,
			FitnessOperator<Gene> fitnessOp) {
		super();
		this.populationSize = populationSize;
		this.genomeGen = genomeGen;
		this.muteOp = muteOp;
		this.crossOp = crossOp;
		this.fitnessOp = fitnessOp;

		sizeNewPopulation = populationSize/2;

		population = new ArrayList<Genome<Gene>>();
		init();
	}

	// general cycle

	private  void init(){
		System.out.println("GA: INITIALIZATION");


		//	    ArrayList<Genome<Gene>> toEval = new ArrayList<Genome<Gene>>();
		for(int i=0; i<populationSize; i++)
			population.add(genomeGen.build());

		//evalParaListGenome(population); // evaluation en calcul parallele
		initEvalParaListGenome(population); //tout lq population est le meme qui est celui de la lecture

	}
	private void initEvalParaListGenome(ArrayList<Genome<Gene>> toEval){
		double fitness;

		toEval.get(0).eval(fitnessOp);
		fitness = toEval.get(0).getFitness();
		for (Genome<Gene> input : toEval) {
			input.setFitness(fitness);
		}

	}

	private void evalParaListGenome(ArrayList<Genome<Gene>> toEval){

		int threads = Runtime.getRuntime().availableProcessors(); //thread = 1 pour dire qu il y a un seul processeur
		System.out.println(threads + " time parallelized");
		ExecutorService service = Executors.newFixedThreadPool(threads);

		List<Future<Genome<Gene>>> futures = new ArrayList<Future<Genome<Gene>>>();
		for (final Genome<Gene> input : toEval) {
			Callable<Genome<Gene>> callable = new Callable<Genome<Gene>>() {
				public Genome<Gene> call() throws Exception {
					input.eval(fitnessOp);

					return input;
				}
			};
			futures.add(service.submit(callable));
		}
		service.shutdown();

		try{
			for (Future<Genome<Gene>> future : futures) {
				future.get();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public Genome<Gene> optimize(int nCycle){
		System.out.println("GA: OPTIMIZATION");

		ArrayList<Genome<Gene>> childs = new ArrayList<Genome<Gene>>(sizeNewPopulation);
		for(int i=0; i<nCycle; i++){
			rankPopulation(); // tri de la population pour avoir les meilleurs au début

			System.out.println("iteration : "+i+"\nbest:"+population.get(0).getFitness());

			System.out.println("=====================");

			childs.clear();
			for(int childIter=0; childIter<sizeNewPopulation; childIter++){
				Pair<Genome<Gene>, Genome<Gene>> parents = select();

				Genome<Gene> child = crossOp.cross(parents.getX(), parents.getY());
				child.mute(muteOp);

				childs.add(child);

				//                child.eval(fitnessOp);
				//
				//                insert(child);
			}
			evalParaListGenome(childs);
			for(Genome<Gene> child:childs)
				insert(child);


		}

		rankPopulation(); 

		return population.get(0);
	}


	private void rankPopulation(){
		Collections.sort(population, Genome.getComparator());
	}

	private Pair<Genome<Gene>, Genome<Gene>> select(){ 
		// selectionner aleatoirement en favorisant le debut de la liste...
		int i = Math.min( (int) (Math.abs(r.nextGaussian()) * populationSize/3.), populationSize-1);
		int j = Math.min( (int) (Math.abs(r.nextGaussian()) * populationSize/3.), populationSize-1);

		return new Pair<Genome<Gene>, Genome<Gene>>(population.get(i), population.get(j));
	}

	private void insert(Genome<Gene> g){
		// inserer un genome aleatoirement en favorisant la fin de la liste et en 
		// protegeant le premier élément du tableau

		int i = Math.min( (int) (Math.abs(r.nextGaussian()) * populationSize/3.), populationSize-1);
		i = populationSize-1-i;
		if(i == 0)
			i++;

		population.set(i, g);
	}

}
