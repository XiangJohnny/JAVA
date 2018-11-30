import java.awt.Point;

import graphique.Fenetre;

public class TestmainVer3 {

	public static void main(String[] args) {
		final Fenetre fenetre = new Fenetre(1000, 1000, "fenetre");
		Thread gauche = new Thread(new Runnable() {
			public void run() {
				fenetre.tracerLignePointAPoint(new Point(100, 100), new Point(
						400, 400), "red");

			}
		});

		Thread droit = new Thread(new Runnable() {
			public void run() {
				fenetre.tracerLignePointAPoint(new Point(400, 400), new Point(
						100, 700), "black");

			}
		});
		Thread base = new Thread(new Runnable() {
			public void run() {
				fenetre.tracerLignePointAPoint(new Point(100, 100), new Point(
						100, 700), "bleu");

			}
		});

		gauche.start();
		droit.start();
		base.start();
	}

}
