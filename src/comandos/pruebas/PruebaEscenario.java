package comandos.pruebas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import comandos.estructura.Direccion;
import comandos.estructura.Escenario;
import comandos.estructura.Posicion;

public class PruebaEscenario {
	
	public static void main(String[] args) {

		List<Posicion> ventanas= new ArrayList<Posicion>();
		ventanas.add(new Posicion(4,4));
		ventanas.add(new Posicion(2,4));
		ventanas.add(new Posicion(1,3));
		ventanas.add(new Posicion(3,3));
		Escenario escenario1 = new Escenario("edificio1", 5, 5, (HashSet<Posicion>) ventanas);
		Escenario escenario2 = new Escenario("edificio2", 5, 5);
		
		
		List<Escenario> escenarios = new ArrayList<Escenario>();
		escenarios.add(escenario1);
		escenarios.add(escenario2);
		
		
		for(Escenario escenario: escenarios) {
			System.out.println("Escenario: " + escenario.getNombre());
			for(int i=0; i < escenario.getAncho(); i++) {
				for(int j=0; j< escenario.getAlto(); j++) {
					System.out.println("La zona (" + i+ ", " + j +")"+ "está "
				//operador ternario== if else
				+ (escenario.isCerrada(i,j) ? "cerrada":"abierta")
				+ " y " 
				+ (escenario.hasVentana(i,j) ? "no":"si")
				+ " tiene ventana");
					
					
				}
			}
		}
		
		
		List<Posicion> posicionesEsquina = escenario2.getAdyacentesValidos(0, 0);
		posicionesEsquina.get(0).getX();
		List<Posicion> posicionesPlantaImpar = escenario2.getAdyacentesValidos(1, 1);
		System.out.println("Posiciones válidas desde la esquina (0, 0):");
		for(Posicion posicionEsquina : posicionesEsquina) {
			System.out.println(posicionEsquina);
		}
		System.out.println("Posiciones válidas desde la posicion(1, 1)(planta impar):");
		for(Posicion posicionPlantaImpar : posicionesPlantaImpar) {
			System.out.println(posicionPlantaImpar);
		}
		
		
	
	}
}
