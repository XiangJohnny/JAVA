package mains;

import java.awt.Color;
import java.awt.image.BufferedImage;

import algorithmeOutil.Dijkstra;
import algorithmeOutil.FileTools;
import circuit.Circuit;
import circuit.CircuitFactoryFromFile;
import circuit.TerrainTools;

public class mainTestDijkastra {

	public static void main(String[] args){
		Circuit circuit = CircuitFactoryFromFile.CircuitFormFile("aufeu.trk");

		Dijkstra dij = new Dijkstra(circuit);

		Double[][] distance = dij.getDistance();

		BufferedImage im = TerrainTools.imageFromCircuit(circuit.getMatrice());

		for(int x = 0; x < distance.length; x++){
			for(int y = 0; y < distance[0].length; y++){
				if(distance[x][y] == Double.POSITIVE_INFINITY){
					continue;
				}
				Color color = new Color( (int) (distance[x][y]%255), 0, 0);
				im.setRGB(y, x, color.getRGB() );

			}
		}

		FileTools.writeImage("imageTestDijkastra.png", im);
		System.out.println("end");

	}
}
