package circuit;


public class TestVecteur {

	public static void main(String[] args) {
		Vecteur a = new Vecteur(1,0);
		Vecteur b = new Vecteur(0,1);
		System.out.println("Vecteur (1,0)+(0,1) = "+a.additionNewVecteur(b));

		Vecteur c = new Vecteur(1,0);		
		c.rotation(0);
		System.out.println("rotation (1,0) de 0 "+c);

		c.rotation(Math.PI/2);
		c.normalisation();
		System.out.println("rotation (1,0) de PI/2 "+c);
		System.out.println("priduit scalaire de "+a+b+a.produitScalaire(b));


		int[][] tab = new int[3][5];
		System.out.println(tab.length);
		System.out.println(tab[0].length);

		Vecteur d = new Vecteur(10, 8);
		System.out.println(d.norme());
		d.normalisation();
		System.out.println(d.norme());
	}

}
