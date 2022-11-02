package comandos.pruebas;

import comandos.estructura.Direccion;
import comandos.estructura.Posicion;

public class PruebaPosicion {

	public static void main(String[] args) {
		
		Posicion posicion = new Posicion(5, 5);
		System.out.println("Posición inicial: "+ posicion.getX() + ", " + posicion.getY());
		for (Direccion dir : Direccion.values()) {
			Posicion pAdyacente = posicion.adyacente(dir);
			System.out.println(dir.name()+" "+pAdyacente.toString());
			
			
		}

	}

}





