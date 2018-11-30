package algorithmeGenetique;



/**
 * Builds a pair in memory
 * @author guillaumes
 *
 * @param <X> : the type of the first element of the pair
 * @param <Y> : the type of the last element of the pair
 */
public class Pair<X,Y>{
	private  X x;
	private  Y y;

	/**
	 * 
	 * @param x : the firste element of the pair
	 * @param y : the last element of the pair
	 */
	public Pair(X x,Y y){
		this.x=x;
		this.y=y;
	}


	public final X getX(){
		return(x);
	}


	public final Y getY(){
		return(y);	
	}

	public final void setX(X x){
		this.x = x;
	}

	public final void setY(Y y){
		this.y = y;
	}

	@SuppressWarnings("unchecked")
	public final boolean equals(Object o){
		if(o.getClass().equals(this.getClass())){
			Pair<X,Y> p = (Pair<X,Y>)o;
			return(p.getX().equals(getX()) && p.getY().equals(getY()));
		}			
		return false;

	}
}
