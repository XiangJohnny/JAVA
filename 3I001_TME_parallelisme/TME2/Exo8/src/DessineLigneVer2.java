import graphique.Fenetre;

import java.awt.Point;

public class DessineLigneVer2 implements Runnable {
	private Fenetre fenetre;
	private String couleur;
	private int x1;
	private int y1;
	private int x2;
	private int y2;

	public DessineLigneVer2(int x1, int y1, int x2, int y2, Fenetre fenetre,
			String couleur) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.couleur = couleur;
		this.fenetre = fenetre;
	}

	@Override
	public void run() {
		fenetre.tracerLignePointAPoint(new Point(x1, y1), new Point(x2, y2),
				couleur);
	}
}
