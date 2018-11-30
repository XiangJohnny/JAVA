package algorithmeGenetique;

public interface FitnessOperator<Gene> {
	double fit(Genome<Gene>  g);
}
