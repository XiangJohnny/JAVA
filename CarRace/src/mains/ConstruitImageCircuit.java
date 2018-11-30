package mains;

import java.awt.image.BufferedImage;

import algorithmeOutil.FileTools;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainTools;

public class ConstruitImageCircuit {

	public static void main(String[] args) {
		String[] infilename = {"1_safe.trk","2_safe.trk","3_safe.trk","4_safe.trk","5_safe.trk","6_safe.trk",
				"7_safe.trk","8_safe.trk","aufeu.trk", "bond_safe.trk", "Een2.trk","labymod.trk","labyperso.trk",
				"perso.trk","t260_safe.trk","t2009.trk"};
		String[] outfilename = {"1_safe.png","2_safe.png","3_safe.png","4_safe.png","5_safe.png","6_safe.png",
				"7_safe.png","8_safe.png","aufeu.png", "bond_safe.png", "Een2.png","labymod.png","labyperso.png",
				"perso.png","t260_safe.png","t2009.png"};
		Circuit cir = null;
		for(int x = 0; x < infilename.length; x++){
			cir = CircuitFactoryFromFile.CircuitFormFile(infilename[x]);
			BufferedImage im = TerrainTools.imageFromCircuit(cir.getMatrice());
			FileTools.writeImage("imageDesCircuits/"+outfilename[x], im);
		}

	}

}
