package algorithmeGenetique;

public class GenomeGeneratorImplDouble5Param implements GenomeGenerator<Double> {

	//private Random r = new Random();
	//private Double[] defaultSigma  = {1.0, 1.0, 1.0, 1.0, 1.0};
	private Double[] defaultVal = {46.0, -0.82, 10.0, -1.0, 0.61};
	private Double[] paramMin = {1.0, -1.0, 1.0, -1.0, 0.01};
	private Double[] paramMax = {200.0, 1.0, 50.0, 1.0, 0.9};
	private Double[] paramInput = null;

	public GenomeGeneratorImplDouble5Param(Double[] paramInput){
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
		for(int i=0; i<defaultVal.length; i++){ 
			// parcours d un tableau de valeurs par defaut 

			// generation aleatoire gaussienne
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
