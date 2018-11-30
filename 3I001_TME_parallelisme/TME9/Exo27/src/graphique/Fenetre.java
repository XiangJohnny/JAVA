package graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	private int largeur, hauteur;

	/*-----------------------------  LES CONSTRUCTEURS  ------------------------------*/

	/**
	 * Cree une fenetre de dimensions width x height, avec un titre title et une
	 * couleur de fond couleur
	 */
	public Fenetre(int width, int height, String title, String couleur) {
		// Create and set up the window.
		super(title);
		largeur = width;
		hauteur = height;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Displx2 the window.
		setMinimumSize(new Dimension(width, height));
		try {
			getContentPane().setBackground(Couleur.parseCouleur(couleur));
		} catch (ColorNotFoundException e) {
			System.out
					.println("Couleur de fond incorrecte. Blanc utilisé à la place");
			getContentPane().setBackground(Color.white);
		}
		setVisible(true);
	}

	/**
	 * Cree une fenetre de dimensions width x height, avec un titre title et un
	 * fond blanc
	 */
	public Fenetre(int width, int height, String title) {
		this(width, height, title, "white");
	}

	/*--------------------------  LES METHODES DE DESSIN  --------------------------*/

	/**
	 * Remplit le fond de la fenetre avec la couleur couleur
	 */
	public void remplir(String couleur) {
		try {
			getContentPane().setBackground(Couleur.parseCouleur(couleur));
		} catch (ColorNotFoundException e) {
			System.out
					.println("Couleur de fond incorrecte. Blanc utilisé à la place");
			getContentPane().setBackground(Color.white);
		}
		setVisible(true);
	}

	/**
	 * Affiche en couleur le point de coordonnees (x, y)
	 */
	public void tracerPoint(Point pt, String couleur) {

		try {
			if (pt.x < 0 || pt.x >= largeur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en largeur");
			}
			if (pt.y < 0 || pt.y >= hauteur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en hauteur");
			}
			Graphics g = getContentPane().getGraphics();
			try {
				g.setColor(Couleur.parseCouleur(couleur));
			} catch (ColorNotFoundException e) {
				System.out
						.println("Couleur du crayon incorrecte. Noir utilisé à la place");
				g.setColor(Color.black);
			}
			g.drawLine(pt.x, pt.y, pt.x, pt.y);
			// setVisible(true);
			attendre(10);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Affiche en noir le point de coordonnees (x, y)
	 */
	public void tracerPoint(Point pt) {
		tracerPoint(pt, "black");
	}

	// ------------------------------------------------------------------------------------
	/**
	 * Trace une ligne en couleur entre les points pt1 et pt2
	 */
	public void tracerLigne(Point pt1, Point pt2, String couleur) {
		try {
			if (pt1.x < 0 || pt1.x >= largeur || pt2.y < 0 || pt2.y >= largeur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en largeur");
			}
			if (pt1.y < 0 || pt1.y >= largeur || pt2.y < 0 || pt2.y >= largeur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en hauteur");
			}
			Graphics g = getContentPane().getGraphics();
			try {
				g.setColor(Couleur.parseCouleur(couleur));
			} catch (ColorNotFoundException e) {
				System.out
						.println("Couleur du crayon incorrecte. Noir utilisé à la place");
				g.setColor(Color.black);
			}
			g.drawLine(pt1.x, pt1.y, pt2.x, pt2.y);
			attendre(10);
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
	}

	// ------------------------------------------------------------------------------------
	/**
	 * Trace une ligne noire entre les points pt1 et pt2
	 */
	public void tracerLigne(Point pt1, Point pt2) {
		tracerLigne(pt1, pt2, "black");
	}

	// ------------------------------------------------------------------------------------
	/**
	 * Trace point par point une ligne en couleur du point pt1 au point pt2
	 */
	public void tracerLignePointAPoint(Point pt1, Point pt2, String couleur) {
		int x, y, Xincr, Yincr;

		try {
			if (pt1.x < 0 || pt1.x >= largeur || pt2.y < 0 || pt2.y >= largeur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en largeur");
			}
			if (pt1.y < 0 || pt1.y >= largeur || pt2.y < 0 || pt2.y >= largeur) {
				throw new IndexOutOfBoundsException(
						"Erreur coordonnées : pixel sort en hauteur");
			}

			Graphics g = getContentPane().getGraphics();
			try {
				g.setColor(Couleur.parseCouleur(couleur));
			} catch (ColorNotFoundException e) {
				System.out
						.println("Couleur du crayon incorrecte. Noir utilisé à la place");
				g.setColor(Color.black);
			}

			// Bresenham algorithm. Implementation entirely poached from Kenny
			// Hoff (95).

			int dX = pt2.x > pt1.x ? pt2.x - pt1.x : pt1.x - pt2.x;
			int dY = pt2.y > pt1.y ? pt2.y - pt1.y : pt1.y - pt2.y;

			if (pt1.x > pt2.x) {
				Xincr = -1;
			} else {
				Xincr = 1;
			}
			if (pt1.y > pt2.y) {
				Yincr = -1;
			} else {
				Yincr = 1;
			}

			x = pt1.x;
			y = pt1.y;

			if (dX >= dY) {
				int dPr = dY << 1;
				int dPru = dPr - (dX << 1);
				int P = dPr - dX;

				while (dX >= 0) {
					/*
					 * on n'utilise pas tracerPoint pour ne pas repeter la
					 * definition de g et la verification de la couleur
					 */
					g.drawLine(x, y, x, y);
					attendre(10);
					if (P > 0) {
						x += Xincr;
						y += Yincr;
						P += dPru;
					} else {
						x += Xincr;
						P += dPr;
					}
					dX--;
				}
			} else {
				int dPr = dX << 1;
				int dPru = dPr - (dY << 1);
				int P = dPr - dY;

				while (dY >= 0) {
					g.drawLine(x, y, x, y);
					attendre(10);
					if (P > 0) {
						x += Xincr;
						y += Yincr;
						P += dPru;
					} else {
						y += Yincr;
						P += dPr;
					}
					dY--;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}

	}

	// ------------------------------------------------------------------------------------
	/**
	 * Trace point par point une ligne noire du point pt1 au point pt2
	 */
	public void tracerLignePointAPoint(Point pt1, Point pt2) {
		tracerLignePointAPoint(pt1, pt2, "black");
	}

	// -------------------------- L'ATTENTE
	// ---------------------------------------------
	/* qui permet de rafraichir la fenetre ??!! */

	private static void attendre(long duree) {
		try {
			Thread.sleep(duree);
		} catch (InterruptedException e) {
		}
	}

}