import graphique.Fenetre;

public class TestmainVer2 {

	public static void main(String[] args) {
		Fenetre fenetre = new Fenetre(1000, 1000, "fenetre");
		DessineLigneVer2 coteG = new DessineLigneVer2(100, 100, 400, 400,
				fenetre, "red");
		DessineLigneVer2 coteD = new DessineLigneVer2(400, 400, 100, 700,
				fenetre, "black");
		DessineLigneVer2 coteB = new DessineLigneVer2(100, 100, 100, 700,
				fenetre, "bleu");

		Thread gauche = new Thread(coteG);
		Thread droit = new Thread(coteD);
		Thread base = new Thread(coteB);

		gauche.start();
		droit.start();
		base.start();

	}

}
