package algorithmeGenetique;

public class GenomeGeneratorImplDouble8Param implements GenomeGenerator<Double> {

	//private Random r = new Random();
	private Double[] defaultVal = {60.0, -1.0, 9.0, 0.3, 9.2,-1.0, 0.1, 0.5};
	private Double[] paramInput = null;

	//private Double[] defaultSigma  = {1.0, 0.25, 1.0, 0.25, 1.0, 0.25, 0.125, 0.125};
	private Double[] paramMin = {0.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.01, 0.01};
	private Double[] paramMax = {200.0, 1.0, 50.0, 1.0, 50.0, 1.0, 0.9, 0.9};

	public GenomeGeneratorImplDouble8Param(){};

	public GenomeGeneratorImplDouble8Param(Double[] paramInput){
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
			//double d = r.nextGaussian() * defaultSigma[i] + param[i];
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