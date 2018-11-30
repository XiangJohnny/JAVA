package algorithmeGenetique;

public interface CrossingOperator<Gene> {
	Genome<Gene> cross(Genome<Gene> g1, Genome<Gene> g2);
}
