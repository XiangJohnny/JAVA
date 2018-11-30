
public class Client implements Runnable{
	
	private int numero;
	private GroupeClients groupe;
	private Restaurant res;
	private int numeroDeReservation;
	
	public Client(int numero, GroupeClients groupe, Restaurant res){
		this.numero = numero;
		this.groupe = groupe;
		this.res = res;
		numeroDeReservation = -1;
	}

	@Override
	public void run() {
		System.out.println("Client "+numero+" de "+groupe+" demande la reservation.");
		int num;
		if((num = groupe.reserver()) == -1){// plus de place libre
			System.out.println("Echec de reservation pour "+groupe);
			groupe.getThreadGroup().interrupt();
			
		}
		else{
			numeroDeReservation = num;
			System.out.println("Succes, Client "+numero+" de "+groupe+" a pour reservation "+num);
		}
	}

}
