package algorithmeGenetique;

import java.util.ArrayList;
import java.util.Comparator;

@SuppressWarnings("serial")
public class Genome<Gene> extends ArrayList<Gene>{
	private double fitness;
	
	public Genome() {
		super();
		fitness = GeneticAlgorithm.MIN_VALUE; 
		
	}
	
	public void mute(MutationOperator<Gene> op){
		//for(Gene g:this)
	    op.mute(this);
	}
	
	public double getFitness(){
		return fitness;
	}
	
	public void setFitness(double fitness){
		this.fitness = fitness;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("[");
		for(Gene g:this)
			sb.append(g+" ");
		sb.append("]");
		
		
		return sb.toString();
	}
	
	public void eval(FitnessOperator<Gene> fit){
		fitness = fit.fit(this);
	}
	
	@SuppressWarnings("rawtypes")
	public static Comparator<Genome> getComparator(){
		return new Comparator<Genome>(){
			public int compare(Genome g1, Genome g2){
				if(g1.getFitness() < g2.getFitness()){
					return 1;
				}
				else if(g1.fitness > g2.fitness){
					return -1;
				}
				return 0;
			}
		};
	}
	
}
