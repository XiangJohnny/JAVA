package algorithmeGenetique;


public class GenomeGeneratorImplPrevoyance implements GenomeGenerator<Double> {

	private Double[] defaultVal = {60.0, -1.0, 9.0, 0.3, 9.2, -1.0, 0.1, Math.PI/4, 25.0};
	private Double[] paramInput = null;

	private Double[] paramMin = {0.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.01, 0.0, 15.0};
	private Double[] paramMax = {200.0, 1.0, 50.0, 1.0, 50.0, 1.0, 0.9, Math.PI, 100.0};

	public GenomeGeneratorImplPrevoyance(){};

	public GenomeGeneratorImplPrevoyance(Double[] paramInput){
		this.paramInput = paramInput;
	}

	public Genome<Double> build(){
		Double[] param;
		if(paramInput != null){
			param = paramInput;
		}
		else {
			param = defaultVal;
		}
		Genome<Double> genome = new Genome<Double>(); // génome vide
		for(int i=0; i<param.length; i++){ 
			// parcours d'un tableau de valeurs par défaut 

			// génération aléatoire gaussienne
			double d = param[i];  //on veut garder les parametres issu de la lecture 
			d = Math.min(d, paramMax[i]); // borne
			d = Math.max(d, paramMin[i]);
			genome.add(d);
		}
		return genome;
	}

	public Double[] getParamMin(){
		return paramMin;
	}

	public Double[] getParamMax(){
		return paramMax;
	}
}