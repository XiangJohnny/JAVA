package circuit;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class TerrainTools {

	public static Terrain terrainFormChar(char c)throws TerrainException {
		int index = 0;
		while(index < Terrain.conversion.length && Terrain.conversion[index] != c){
			index++;
		}
		if(index == Terrain.conversion.length){
			throw new TerrainException("invalide TerrainCharacter");
		}
		return Terrain.values()[index];
	}

	public static char charFromTerrain(Terrain t) throws TerrainException{
		if(t == null){
			throw new TerrainException("invalide Terrain in argument");
		}
		return Terrain.conversion[t.ordinal()];
	}

	public static Color terrainToRGB(Terrain t) throws TerrainException{
		if(t == null){
			throw new TerrainException("invalide Terrain in argument");
		}
		return Terrain.convColor[t.ordinal()];
	}

	public static boolean isRunnable(Terrain t){
		return t != Terrain.Herbe && t != Terrain.Obstacle && t != Terrain.Eau;
	}

	public static BufferedImage imageFromCircuit(Terrain[][] track){
		BufferedImage im = new BufferedImage(track[0].length, track.length, BufferedImage.TYPE_INT_ARGB);

		try{
			for(int x = 0; x < track.length; x++){
				for(int y = 0; y < track[0].length; y++){
					im.setRGB(y, x, TerrainTools.terrainToRGB(track[x][y]).getRGB());
				}
			} 
		} catch(TerrainException e){
			System.out.println(e);
			return null;
		}

		return im;
	}
	
}