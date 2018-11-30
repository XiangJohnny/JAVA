package algorithmeGenetique;

public interface MutationOperator<Gene> {
	void mute(Genome<Gene> g);
	void setParamMax(Double[] paramMax);
	void setParamMin(Double[] paramMin);
}
