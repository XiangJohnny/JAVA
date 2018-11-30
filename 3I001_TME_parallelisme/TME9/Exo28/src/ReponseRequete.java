
public class ReponseRequete {
	
	private int mess;
	private Client refe;
	private int numRe;
	public ReponseRequete(Client refClient, int numReq, int message){
		mess = message;
		refe = refClient;
		numRe = numReq;
	}
	
	public String toString(){
		return "Reponse de Requete du client "+refe.getId()+" de numero de requete "+numRe+":"+mess;
	}
	
	public void signalClient(){
		refe.requeteServie(this);
	}

}
