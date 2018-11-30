import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import graphique.Fenetre;


public class VonKochMono {

	private final static double LG_MIN = 8.0; Fenetre f;

	public VonKochMono (Fenetre f, Point a, Point b, Point c) { 
		this.f = f;
		ExecutorService exec = Executors.newFixedThreadPool(4);
		exec.execute(new Cote(f, b, a, exec));
		exec.execute(new Cote(f, a, c, exec));
		exec.execute(new Cote(f, c, b, exec));
	}
}
