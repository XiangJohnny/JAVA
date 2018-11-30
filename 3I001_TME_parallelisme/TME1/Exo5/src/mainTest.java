
public class mainTest {

	//Question 2: on doit inclure chaque instruction de creation dans le bloc try catch car la creation peut provoquer une Exception

	public static void main(String[] args) {

		try{
			Compte decouvert = new Compte(100, true, 100);
			Compte nonDecouvert = new Compte(100);

			System.out.println(decouvert.toString());
			System.out.println(nonDecouvert.toString());

			decouvert.crediter(100);
			nonDecouvert.crediter(100);
			System.out.println(decouvert.toString());
			System.out.println(nonDecouvert.toString());

			decouvert.debiter(100);
			nonDecouvert.debiter(1000);

		}
		catch(SoldeNegatifException e){
			e.printStackTrace();
		}
		catch(SoldeInsufisantException e){
			e.printStackTrace();
		}
	}

}
