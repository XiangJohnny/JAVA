package algorithmeGenetique;

import java.util.Random;


public class MutationOperatorImplDouble implements MutationOperator<Double>{
	private Double rate;
	private static Random r = new Random();
	private Double[] sigma;
	private Double[] paramMin;
	private Double[] paramMax;

	public MutationOperatorImplDouble(double rate,  Double[] sigma) {
		super();
		this.rate = rate;
		this.sigma = sigma;
	}
	@Override
	public void mute(Genome<Double> g) {
		for(int i=0; i<g.size(); i++){
			if(r.nextDouble()<rate){
				g.set(i, g.get(i)+ (r.nextGaussian()*sigma[i])*g.get(i));
				g.set(i, Math.min(g.get(i), paramMax[i])); // borne
				g.set(i, Math.max(g.get(i), paramMin[i])); // borne
				//g.set(i, g.get(i)+r.nextGaussian()*sigma[i]);
			}
		}
	}
	@Override
	public void setParamMax(Double[] paramMax) {
		this.paramMax = paramMax;
	}
	@Override
	public void setParamMin(Double[] paramMin) {
		this.paramMin = paramMin;		
	}
}
