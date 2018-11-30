package algorithmeOutil;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import circuit.Terrain;
import circuit.TerrainException;
import circuit.TerrainTools;
import algorithmeGenetique.Genome;
import strategy.Commande;

public class FileTools {

	public static Terrain[][] getTerrainWhitFile(String filename){
		try {
			// if we have a problem whit the file throws exception
			InputStream file = new FileInputStream(filename);
			BufferedReader in = null;
			int nbLigne = 0, nbColonne = 0;
			Terrain[][] terrain;

			try {
				//if we can't open the file or a problem in the reading throws exception
				InputStreamReader fr = new InputStreamReader(file); 
				in = new BufferedReader(fr);

				String buff = in.readLine();
				nbColonne = Integer.parseInt(buff);
				buff = in.readLine();
				nbLigne = Integer.parseInt(buff);
				terrain = new Terrain[nbLigne][nbColonne];

				for(int x = 0; x < nbLigne; x++){
					buff = in.readLine();
					for(int y = 0; y < nbColonne; y++){
						char c = buff.charAt(y);
						terrain[x][y] = TerrainTools.terrainFormChar(c);
					}
				}

				in.close();
				return terrain;
			} catch (TerrainException e){
				in.close();
				System.err.println(e);
				return null;

			} catch (Exception e) {
				in.close();
				e.printStackTrace();
				System.err.println("Invalid Format : " + file
						+ "... Loading aborted");
				return null;
			}
		} catch (Exception e){
			System.err.println("Invalid File : " + filename);
			return null;
		}
	}

	public static void writeImage(String imageName, BufferedImage im) {
		try{
			File outputfile = new File(imageName);
			ImageIO.write(im, "png", outputfile);
		}
		catch (IOException e){
			System.err.println("Erreur lors de la sauvegarde de l image "+imageName);
		}
	}

	public static BufferedImage readImage(String filename) throws IOException{
		File file = new File(filename);
		return ImageIO.read(file);
	}

	public static void saveListeCommande(ArrayList<Commande> liste, String filename){
		DataOutputStream os;
		try {
			os = new DataOutputStream(new FileOutputStream(filename));
			for(Commande c:liste){
				os.writeDouble(c.getAcc());
				os.writeDouble(c.getTurn());
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Commande> loadListeCommande(String filename) throws IOException{
		ArrayList<Commande> liste = null;
		DataInputStream os = null;
		
		try {
			os = new DataInputStream(new FileInputStream(filename));

			liste = new ArrayList<Commande>();
			double a,t;
			while(true){ // on attend la fin de fichier
				a = os.readDouble();
				t = os.readDouble();
				liste.add(new Commande(a,t));
			}

		} catch (EOFException e){
			os.close();
			return liste;
		} 

	}

	public static void saveParam(Double[] param, String filename){
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(new FileOutputStream(filename));
			os.writeInt(param.length); //8 parametre
			for(Double d : param){
				os.writeDouble(d);
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void saveParam(Genome<Double> gen, String filename){
		DataOutputStream os = null;
		try {
			os = new DataOutputStream(new FileOutputStream(filename));
			os.writeInt(gen.size()); //8 parametre
			for(int i = 0; i < gen.size(); i++){
				os.writeDouble(gen.get(i));
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Double[] loadParam(String filename) {
		Double[] param = null;
		DataInputStream os;
		try {
			os = new DataInputStream(new FileInputStream(filename));

			param = new Double[os.readInt()];

			for(int i = 0; i < param.length; i++){
				param[i] = os.readDouble();
			}
			os.close();

		} catch (FileNotFoundException e) {}
		catch(IOException e){}
		
		return param;
	}

}