import graphique.Fenetre;

public class Testmain {

	public static void main(String[] args) {
		Fenetre fenetre = new Fenetre(1000, 1000, "fenetre");
		Thread coteG = new DessineLigne(100, 100, 400, 400, fenetre, "red");
		Thread coteD = new DessineLigne(400, 400, 100, 700, fenetre, "black");
		Thread coteB = new DessineLigne(100, 100, 100, 700, fenetre, "bleu");

		coteG.start();
		coteD.start();
		coteB.start();

	}

}
