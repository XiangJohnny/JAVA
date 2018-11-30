
public class Compte {
	private static int nbCompte = 0;
	private int numero;

	private float solde;
	private boolean devouvert = false;
	private float quotaDecouvert = 0;

	public Compte(float s) throws SoldeNegatifException {
		if(s < 0){
			throw new SoldeNegatifException("Solde negatif");
		}
		else{
			solde = s;
		}

		numero = nbCompte++;
	}

	public Compte(float s, boolean decouvert, float quotaDecouvert) throws SoldeNegatifException{
		this(s);
		this.devouvert = decouvert;
		if(devouvert){
			this.quotaDecouvert = quotaDecouvert;
		}
	}

	public void crediter(float s){
		solde += s;
	}
	
	public void debiter(float s) throws SoldeInsufisantException{
		if(solde-s < -quotaDecouvert){
			throw new SoldeInsufisantException("solde insufisant");
		}
		else{
			solde = solde-s;
		}

	}
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}
	
	public String toString(){
		return "compte:"+numero+",solde:"+solde;
	}
}
