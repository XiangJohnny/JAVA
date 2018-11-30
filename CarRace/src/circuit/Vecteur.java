package circuit;

public class Vecteur {

	private double x, y;

	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vecteur(Vecteur a, Vecteur b){
		this.x = b.getX() - a.getX();
		this.y = b.getY() - a.getY();
	}
	
	public Vecteur(Vecteur v){
		this.x = v.getX();
		this.y = v.getY();
	}
	
	@Override
	public Vecteur clone(){
		return new Vecteur(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vecteur [x=" + x + ", y=" + y + "]";
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vecteur additionNewVecteur(Vecteur obj){
		if(obj == null){
			return null;
		}
		if(this.getClass() != obj.getClass()){
			return null;
		}
		return new Vecteur(this.x + obj.getX(), this.y + obj.getY());
	}

	public void additionModifVecteur(Vecteur obj){
		if(obj == null){
			return;
		}
		if(this.getClass() != obj.getClass()){
			return;
		}
		this.x += obj.getX();
		this.y += obj.getY();
	}

	public Vecteur soustractionNewVecteur(Vecteur obj){
		if(obj == null){
			return null;
		}
		if(this.getClass() != obj.getClass()){
			return null;
		}
		return new Vecteur(this.x - obj.getX(), this.y - obj.getY());
	}

	public void soustractionModifVecteur(Vecteur obj){
		if(obj == null){
			return;
		}
		if(this.getClass() != obj.getClass()){
			return;
		}
		this.x -= obj.getX();
		this.y -= obj.getY();
	}

	public double produitScalaire(Vecteur obj){
		if(obj == null){
			return 0;
		}
		if(this.getClass() != obj.getClass()){
			return 0;
		}
		return (this.x * obj.getX())+(this.y * obj.getY());
	}

	public double produitVectoriel(Vecteur obj){
		return (obj.getY() * this.x) - (obj.getX() * this.y);
	}
	
	public double angleEntreVecteur(Vecteur v){
		double angle = this.produitScalaire(v);
		double signe = this.produitVectoriel(v);
		double cosx = angle / (this.norme() * v.norme());
		if( signe < 0){
			if(cosx > 1){
				return -0;
			}
			if(cosx < -1){
				return -Math.PI;
			}
			return -Math.acos(angle / (this.norme() * v.norme()));
		}
		else{
			if(cosx > 1){
				return 0;
			}
			if(cosx < -1){
				return Math.PI;
			}
			return Math.acos(angle / (this.norme() * v.norme()));
		}
	}

	public void multiParScalaireModif(double multi){
		this.x *= multi;
		this.y *= multi;
	}
	
	public Vecteur multiParScalaireNew(double multi){
		return new Vecteur(this.x * multi, this.y * multi);
	}

	public void rotation(double angle){
		double tmp;
		tmp = this.x*Math.cos(angle) - this.y*Math.sin(angle);
		this.y = this.x*Math.sin(angle) + this.y*Math.cos(angle);
		this.x = tmp;
	}

	public double norme(){
		return Math.sqrt(this.x*this.x + this.y*this.y);
	}
	
	public void normalisation(){
		double norme = this.norme();
		this.x /= norme;
		this.y /= norme;
	}


}
