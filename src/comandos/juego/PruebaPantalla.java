package comandos.juego;

import comandos.estructura.Direccion;
import comandos.estructura.Escenario;
import comandos.estructura.Estado;
import comandos.estructura.Posicion;
import comandos.personajes.Comando;
import comandos.personajes.ComandoAlacran;
import comandos.personajes.ComandoAlfa;
import comandos.personajes.ComandoHalcon;
import comandos.vista.Alarma;
import comandos.vista.Pantalla;

public class PruebaPantalla {
	public static void main(String[] args) throws ClassNotFoundException, CloneNotSupportedException {
		final int alto = 12;
		final int ancho = 10;
		Pantalla p = new Pantalla(ancho, alto, 75, java.awt.Color.BLUE);
		Posicion objetivo = new Posicion(0, 0);
		Escenario escenario = new Escenario("escenario", ancho, alto);
		Comando com = new Comando(1);
		Comando comAlfa = new ComandoAlfa(1);
		Comando comAlacran = new ComandoAlacran(1);
		Comando comHalcon = new ComandoHalcon(1);
		escenario.introducirComando(comAlfa);
		escenario.introducirComando(com);
		escenario.introducirComando(comAlacran);
		escenario.introducirComando(comHalcon);

//		escenario.introducirComando(2, TipoComando.BASICO);
//		escenario.introducirComando(2, TipoComando.HALCON);
//		escenario.introducirComando(2, TipoComando.ALFA);
//		escenario.introducirComando(2, TipoComando.ALACRAN);

		escenario.iniciarPartida();
		boolean continuar = true;
		String ultTecla = "";
		while (escenario.getEstado() == Estado.EN_JUEGO && continuar == true) {
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

		p.setBarraEstado("Fin de partida: " + escenario.getEstado());

	}

}
