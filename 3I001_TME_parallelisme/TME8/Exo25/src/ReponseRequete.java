
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
		return "Client "+refe.getId()+" Requete "+numRe+" du client:"+mess;
	}

}
