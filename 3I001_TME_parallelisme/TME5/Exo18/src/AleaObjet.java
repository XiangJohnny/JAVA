import java.util.Random;


public class AleaObjet {
	
	private static int count = 0;
	private int id;
	private int poid;
	private Random gen = new Random();
	
	public AleaObjet(int min, int max){
		this.poid = min + gen.nextInt(max-min+1);
		++count;
		id = count;
	}
	
	public int getId(){
		return id;
	}
	
	public int getPoids(){
		return poid;
	}

}
