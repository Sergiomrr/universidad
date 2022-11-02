package comandos.juego;

import comandos.estructura.Escenario;
import comandos.estructura.Estado;
import comandos.estructura.Direccion;
import comandos.estructura.Posicion;
import comandos.vista.Alarma;
import comandos.vista.Pantalla;

public class PruebaPantalla {
	public static void main(String[] args) {
		final int alto = 9;
		final int ancho = 5;
		Pantalla p = new Pantalla(ancho, alto, 75, java.awt.Color.BLUE);
		Posicion objetivo = new Posicion(0, 0);
		Escenario escenario = new Escenario("escenario", ancho, alto);
		for (int i = 0; i < escenario.getAncho(); i++) {
			escenario.introducirComando(1);
		}
		escenario.iniciarPartida();
		boolean continuar = true;
		String ultTecla = "";
		while (escenario.getEstado()==Estado.EN_JUEGO && continuar == true) {
			p.resetear();
			if (p.hayTecla()) {
				String tecla = p.leerTecla();
				ultTecla = tecla;
				switch (tecla) {
				case "i":
					escenario.desplazarArma(Direccion.ARRIBA);
					break;
				case "j":
					escenario.desplazarArma(Direccion.IZQUIERDA);
					break;
				case "l":
					escenario.desplazarArma(Direccion.DERECHA);
					break;
				case "k":
					escenario.desplazarArma(Direccion.ABAJO);
					break;
				case "d":
					escenario.disparar();
					break;
				case "x":
					continuar = false;
					break;
				}
			}

			p.setBarraEstado("Ultima tecla = " + ultTecla + " Tiempo transcurrido = " + escenario.tiempoTrascurrido());
			for (Imagen imagen : escenario.devuelveImagenes()) {
				p.addImagen(imagen.getCoordenadaX(), imagen.getCoordenadaY(), imagen.getRuta());
			}
			escenario.actualiza();
			p.dibujar();
			Alarma.dormir(150);
		}

		p.setBarraEstado("Fin de partida: "+ escenario.getEstado());

	}

}
