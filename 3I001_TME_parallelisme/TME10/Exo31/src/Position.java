
public enum Position {
	NORD(0),
	SUD(1);
	
	private int index;
	
	Position(int index){
		this.index = index;
	}
	
	public int index(){
		return index;
	}
	
	public void setIndex(int i){
		index = i;
	}
	
}
