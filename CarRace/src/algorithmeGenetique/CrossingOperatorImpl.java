package algorithmeGenetique;

import java.util.Random;

public class CrossingOperatorImpl<Gene> implements CrossingOperator<Gene> {

	private Random r = new Random();

	public Genome<Gene> cross(Genome<Gene> gen1, Genome<Gene> gen2) {
		Genome<Gene> genome = new Genome<Gene>();

		for(int i=0; i<gen1.size(); i++){
			if(r.nextDouble()>0.5)
				genome.add(gen1.get(i));
			else
				genome.add(gen2.get(i));
		}

		return genome;
	}
}
