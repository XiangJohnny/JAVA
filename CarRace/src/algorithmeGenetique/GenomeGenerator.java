package algorithmeGenetique;

public interface GenomeGenerator<Gene> {
	public Genome<Gene> build();
	public Double[] getParamMin();
	public Double[] getParamMax();
}
