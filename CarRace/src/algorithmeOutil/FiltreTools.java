package algorithmeOutil;

import circuit.Terrain;
import circuit.TerrainTools;

public class FiltreTools {

	private static boolean[][] filtreDiago =
		{{true, false},
		{false, true}};
	private static boolean[][] filtreDiago_do =
		{{true, false},
		{false, true}};
	private static boolean[][] filtreFace = 
		{{true, true, false},
		{true, false, false},
		{true,true, false}};
	private static boolean[][] filtreFace_do =
		{{false, true, false},
		{false, false, false},
		{false,true, false}};

	public static void filtre(Terrain[][] mat, boolean[][] window, boolean[][] solution){
		boolean[][] winRot;
		boolean[][] soluRot;
		filtre_do(mat, window, solution);

		winRot = rotation90(window);
		soluRot = rotation90(solution);
		filtre_do(mat, winRot, soluRot);

		winRot = rotation90(winRot);
		soluRot = rotation90(soluRot);
		filtre_do(mat, winRot, soluRot);

		winRot = rotation90(winRot);
		soluRot = rotation90(soluRot);
		filtre_do(mat, winRot, soluRot);

	}

	private static void filtre_do(Terrain[][] mat, boolean[][] window, boolean[][] solution){
		for(int i=0; i<mat.length; i++){
			for(int j=0; j<mat[0].length; j++){    

				if(matching(mat, i, j, window)){ // test
					for(int m =- window.length/2 ; m<(window.length-window.length/2); m++){
						for(int n = -window[0].length/2; n<(window[0].length-window[0].length/2); n++){
							if(solution[m+window.length/2][n+window[0].length/2] == true){
								if(0 <= i+m && i+m < mat.length &&
										0 <= j+n && j+n < mat[0].length){
									mat[i+m][j+n] = Terrain.Herbe; //remplacement
								}
							}
						}
					}
				}        
			}
		}
	}

	private static boolean matching(Terrain[][] mat, int i, int j, boolean[][] window){
		for(int m = -window.length/2; m<(window.length-window.length/2); m++){
			for(int n = -window[0].length/2; n<(window[0].length-window[0].length/2); n++){
				if(0 <= i+m && i+m < mat.length &&
						0 <= j+n && j+n < mat[0].length){
					if(TerrainTools.isRunnable(mat[i+m][j+n]) != window[m+window.length/2][n+window[0].length/2]){
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean[][] rotation90(boolean [][] mat){
		boolean[][] out = new boolean[mat[0].length][mat.length];

		for(int i = 0; i< out.length; i++){
			for(int j = 0; j < out[0].length; j++){
				out[i][j] = mat[mat.length-j-1][i];
			}
		}

		return out;
	}
	
	public static boolean[][] getFiltreDiago() {
		return filtreDiago;
	}

	public static boolean[][] getFiltreDiago_do() {
		return filtreDiago_do;
	}

	public static boolean[][] getFiltreFace() {
		return filtreFace;
	}

	public static boolean[][] getFiltreFace_do() {
		return filtreFace_do;
	}

}
