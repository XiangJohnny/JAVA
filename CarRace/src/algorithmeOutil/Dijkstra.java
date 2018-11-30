package algorithmeOutil;

import java.util.concurrent.PriorityBlockingQueue;

import controleur.UpdateCircuitListener;
import circuit.Circuit;
import circuit.Terrain;
import circuit.TerrainTools;
import circuit.Vecteur;

public class Dijkstra implements UpdateCircuitListener{

	private Double[][] distance;	//la matrice qui contient la distance par apport a l arrivee
	private Circuit circuit;
	private PriorityBlockingQueue<Vecteur> tas;	//tas contenant les points a calcules
	private ComparatorDijk comparator;

	public Dijkstra(Circuit circuit){
		this.circuit = circuit;	
		this.distance = new Double[circuit.getHeight()][circuit.getWidth()];
		comparator = new ComparatorDijk(distance);
		tas = new PriorityBlockingQueue<Vecteur>(1000, comparator);
		this.calDistance();
	}


	public Double[][] calDistance(){
		initDistance();	//initialisation pour commancer le calcul
		while(tas.isEmpty() == false){	//tantque au il exist de point a parcourir
			int x = (int) tas.peek().getX();	//x et y du point a parcourir qui est le plus proche de l arrivee
			int y = (int) tas.peek().getY();
			tas.remove();	//suppression ce cette point

			calVoisin(x, y);	//calcul de distance des voisins et mettre dans le tas les nouvelles points a parcourir

		}
		return distance;
	}

	private void initDistance(){	//initialisation du tas et la distance de la ligne d arrivee
		Vecteur StartDirection = circuit.getDirectionDepart();
		
		for(int x = 0; x < circuit.getHeight(); x++){	//initialisation de la matice distance
			for(int y = 0; y < circuit.getWidth(); y++){
				distance[x][y] = Double.POSITIVE_INFINITY;
			}
		}

		for(int x = 0; x < distance.length; x++){	//pour tous les points de circuit
			for(int y = 0; y < distance[0].length; y++){
				if(circuit.getTerrain(x, y) == Terrain.EndLine){	//la distance de la ligne d arrivee par rapport a l arrive est 0
					distance[x][y] = 0.0;

					for(int x1 = -1; x1 <= 1; x1++){	//pour les voisins du point
						for(int y1 = -1; y1 <= 1; y1++){
							if(x1 == 0 && y1 == 0){	//le point meme continue
								continue; 
							}
							if(TerrainTools.isRunnable(circuit.getTerrain(x + x1, y + y1)) == false){	//si ce nest pas la piste continue
								continue;
							}
							Vecteur direction = new Vecteur(x1, y1);

							if(StartDirection.produitScalaire(direction) < 0){	//si le point est le cote de l arrivee
								Double d = Math.sqrt( x1*x1 + y1*y1 )*10;

								if(distance[x+x1][y+y1] > (distance[x][y] + d)){	//si la nouvelle distance est plus petie
									distance[x+x1][y+y1] = distance[x][y] +d; 		//alors mettre a jour la nouvelle distance
								}
								if(!tas.contains(new Vecteur(x+x1, y+y1))){	//si le tas ne contient pas le voisin
									tas.add(new Vecteur(x+x1, y+y1));		//alors mettre dans le tas ce voisin qui est a passe
								}
							}
						}
					}
				}
			}
		}
	}

	private void calVoisin(int x, int y){	
		Double d;
		Vecteur point;
		for(int x1 = -1; x1 <=1; x1++){	//pour les voisins du point x y
			for(int y1 = -1; y1 <=1; y1++){
				point = new Vecteur(x+x1, y+y1);
				if(x1 == 0 && y1 == 0){	//si cest le point lui meme continue
					continue; 
				}
				if(TerrainTools.isRunnable(circuit.getTerrain(point)) == false){	//si le voisin nest pas le piste continue
					continue;
				}
				d = Math.sqrt( x1*x1 + y1*y1 )*10;
				if(distance[x+x1][y+y1] > (distance[x][y] + d)){	//si la nouvelle distance est plus petie
					distance[x+x1][y+y1] = distance[x][y] + d; 		//alors mettre a jour la nouvelle distance
					if(tas.contains(point)){	//si le tas contient deja le voisin
						tas.remove(point);		//on le sort
						tas.add(point);			//et on le remet pour qu il soit a la bonne place
					}
					else {
						tas.add(point);	//sinon on le met dans le tas
					}
				}
			}
		}


	}

	public Double[][] getDistance(){
		return distance;
	}


	@Override
	public void circuitUpdate(Circuit circuit) {
		this.circuit = circuit;
		this.distance = new Double[circuit.getHeight()][circuit.getWidth()];
		comparator.setDist(this.distance);
		this.calDistance();
	}

}
