package comandos.estructura;

import java.util.Random;
import java.time.LocalDateTime;
import comandos.personajes.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import comandos.juego.Imagen;

public class Escenario {
	private static final int INTERVALO_ACTUALIZACION = 1;
	private final String nombre;
	private final int alto;
	private final int ancho;
	private Posicion arma;
	private LocalDateTime inicio;
	private Estado estado;
	private LocalDateTime ultimaActualizacion;

	// matriz de valores booleanos que indican si estan habilitadas o no
	// Las zonas no deben ser conocidas fuera de la clase por lo que se hace el
	// atributo privado
	// y no se crea un método get para dicho atributo
	private final Boolean zonas[][];
	private HashSet<Posicion> ventanas;
	private HashSet<Comando> comandos;
	private HashSet<Posicion> bombas;

	public Escenario(String nombre, int ancho, int alto, HashSet<Posicion> ventanas) {
		this.nombre = nombre;
		this.alto = alto;
		this.ancho = ancho;
		this.ventanas = ventanas;
		this.arma = new Posicion(0, 0);
		this.inicio = null;
		this.zonas = new Boolean[this.ancho][this.alto];
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				zonas[i][j] = true;
			}
		}
		this.bombas = new HashSet<Posicion>();
		this.comandos = new HashSet<Comando>();
		this.estado = null;
	}

	public Escenario(String nombre, int ancho, int alto) {
		this.nombre = nombre;
		this.alto = alto;
		this.ancho = ancho;
		this.arma = new Posicion(0, 0);
		this.zonas = new Boolean[this.ancho][this.alto];
		this.ventanas = new HashSet<Posicion>();
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				// Planta alta todas abiertas
				if (j == this.alto - 1) {
					zonas[i][j] = true;
					// Planta par distinta de cero cerrada
				} else if (j % 2 == 0 && i > 0) {
					zonas[i][j] = false;
					// Planta par y zona cero o planta impar abierta
				} else if ((j % 2 == 0 && i == 0) || (j % 2 != 0)) {
					zonas[i][j] = true;
				}
				// Ventanas en planta impar y zona par
				if (j % 2 != 0 && i % 2 == 0) {
					Posicion p = new Posicion(i, j);
					this.ventanas.add(p);
				}
			}
		}
		this.bombas = new HashSet<Posicion>();
		this.comandos = new HashSet<Comando>();
		this.estado = null;
	}

	public String getNombre() {
		return nombre;
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	public boolean isCerrada(int x, int y) {
		return !this.zonas[x][y];

	}

	public boolean isCerrada(Posicion p) {
		return isCerrada(p.getX(), p.getY());

	}

	public boolean hasVentana(int x, int y) {
		Posicion p = new Posicion(x, y);
		return this.ventanas.contains(p);
	}

	public boolean hasVentana(Posicion p) {
		return hasVentana(p.getX(), p.getY());
	}

	private boolean isPosicionValida(Posicion p) {
		return p.getX() >= 0 && p.getX() < this.ancho && p.getY() >= 0 && p.getY() < this.alto;
	}

	public List<Posicion> getAdyacentesValidos(int x, int y) {
		List<Posicion> posiciones = new ArrayList<Posicion>();
		Posicion posActual = new Posicion(x, y);
		for (Direccion dir : Direccion.values()) {
			Posicion posAdyacente = posActual.adyacente(dir);
			if (this.isPosicionValida(posAdyacente) && !this.isCerrada(posAdyacente)) {
				posiciones.add(posAdyacente);
			}
		}
		return posiciones;
	}

	public boolean desplazarArma(Direccion direccion) {
		if (this.isPosicionValida(this.arma.adyacente(direccion))) {
			switch (direccion) {
			case ARRIBA:
				this.arma = new Posicion(this.arma.getX(), this.arma.getY() + 1);
				break;
			case ABAJO:
				this.arma = new Posicion(this.arma.getX(), this.arma.getY() - 1);
				break;
			case DERECHA:
				this.arma = new Posicion(this.arma.getX() + 1, this.arma.getY());
				break;
			case IZQUIERDA:
				this.arma = new Posicion(this.arma.getX() - 1, this.arma.getY());
				break;
			}
			return true;
		}
		return false;

	}

	public Posicion getArma() {
		return arma;
	}

	public Estado iniciarPartida() {
		this.inicio = LocalDateTime.now();
		return estado = Estado.EN_JUEGO;
	}

	public Estado getEstado() {
		return estado;
	}



	public long tiempoTrascurrido() {
		return ChronoUnit.SECONDS.between(inicio, LocalDateTime.now());

	}

	public List<Imagen> devuelveImagenes() {
		List<Imagen> imagenes = new ArrayList<Imagen>();
		for (int i = 0; i < this.getAlto(); i++) {
			for (int j = 0; j < this.getAncho(); j++) {
				Posicion pos = new Posicion(j, i);
				if (this.isCerrada(j, i)) {
					imagenes.add(new Imagen("imagenes/zona-cerrada.png", j, i));
				}
				if (this.devolverComando(pos) != null) {
					imagenes.add(new Imagen("imagenes/comando.png", j, i));
				}
				if (this.hasVentana(j, i)) {
					imagenes.add(new Imagen("imagenes/ventana.png", j, i));
				} else {
					imagenes.add(new Imagen("imagenes/ladrillos.png", j, i));
				}
				if (this.devolverBomba(pos) != null) {
					imagenes.add(new Imagen("imagenes/bomba.png", j, i));
				}
			}
		}
		imagenes.add(new Imagen("imagenes/objetivo.png", arma.getX(), arma.getY()));
		return imagenes;
	}

	public Posicion devolverBomba(Posicion p) {
		for (Posicion bomb : bombas) {
			if (bomb.equals(p)) {
				return bomb;
			}
		}
		return null;
	}

	public HashSet<Posicion> devuelvePosicionComandos() {
		HashSet<Posicion> posComandos = new HashSet<Posicion>();

		for (Comando com : comandos) {
			posComandos.add(com.getPosicionActual());
		}
		return posComandos;
	}

	public Comando devolverComando(Posicion p) {
		for (Comando com : comandos) {
			if (com.getPosicionActual().equals(p)) {
				return com;
			}
		}
		return null;
	}

	// Opción A: Inserto sí o sí, siempre que haya al menos una posición libre
	public boolean introducirComando(int numBombas) {
		List<Posicion> posicionesAbiertas = new ArrayList<Posicion>();
		// Buscamos las posiciones abiertas
		for (int i = 0; i < getAncho(); i++) {
			Posicion pos = new Posicion(i, getAlto() - 1);
			if (devolverComando(pos) == null && !isCerrada(pos)) {
				posicionesAbiertas.add(pos);
			}

		}
		// Si no hay ninguna posición abierta termino
		if (posicionesAbiertas.isEmpty()) {
			return false;
		}
		// Genero un número aleatorio entre 0 y el tamaño de la lista que contiene solo
		// posiciones disponibles
		// y obtengo la componente x de la posición contenida en esa posición de la
		// lista
		Random gen = new Random();
		int posComando = gen.nextInt(posicionesAbiertas.size());
		Posicion posi = new Posicion(posicionesAbiertas.get(posComando).getX(), getAlto() - 1);
		Comando com = new Comando(numBombas);
		com.addHistoricoPosiciones(posi);
		this.comandos.add(com);
		return true;
	}

	// Opción B sólo inserto un comando si la generación del número aleatorio me
	// produce una posición libre
	public boolean introducirComandoOpcionB(int numBombas) {
		boolean hayPosicionLibre = false;
		// Buscamos las posiciones abiertas
		for (int i = 0; i < getAncho(); i++) {
			Posicion pos = new Posicion(i, getAlto() - 1);
			if (devolverComando(pos) == null && !isCerrada(pos)) {
				hayPosicionLibre = true;
			}

		}
		// Si no hay ninguna posición abierta termino
		if (!hayPosicionLibre) {
			return false;
		}
		// Genero un número aleatorio entre 0 y el tamaño de la lista que contiene solo
		// posiciones disponibles
		// y obtengo la componente x de la posición contenida en esa posición de la
		// lista
		Random gen = new Random();
		int posComando = gen.nextInt(getAncho());
		Posicion posi = new Posicion(posComando, getAlto() - 1);
		if (devolverComando(posi) == null && !isCerrada(posi)) {
			Comando com = new Comando(numBombas);
			com.addHistoricoPosiciones(posi);
			this.comandos.add(com);
			return true;
		}
		return false;

	}

	public void disparar() {
		if (this.hasVentana(this.arma)) {
			comandos.remove(this.devolverComando(this.arma));
		}
		bombas.remove(this.devolverBomba(this.arma));
	}

	private List<Direccion> getDireccionesPosibles(Comando com) {
		List<Posicion> adyacentesValidos = getAdyacentesValidos(com.getPosicionActual().getX(),
				com.getPosicionActual().getY());
		HashSet<Posicion> posComandos = devuelvePosicionComandos();
		List<Posicion> posicionesValidas = new ArrayList<Posicion>();
		for (Posicion adyacente : adyacentesValidos) {
			if (!posComandos.contains(adyacente)) {
				posicionesValidas.add(adyacente);
			}
		}
		List<Direccion> direccionesPosibles = new ArrayList<Direccion>();
		for (Posicion posicion : posicionesValidas) {
			if (posicion.getX() > com.getPosicionActual().getX()) {
				direccionesPosibles.add(Direccion.DERECHA);
			}
			if (posicion.getX() < com.getPosicionActual().getX()) {
				direccionesPosibles.add(Direccion.IZQUIERDA);
			}
			if (posicion.getY() > com.getPosicionActual().getY()) {
				direccionesPosibles.add(Direccion.ARRIBA);
			}
			if (posicion.getY() < com.getPosicionActual().getY()) {
				direccionesPosibles.add(Direccion.ABAJO);
			}
		}

		return direccionesPosibles;
	}

	public void actualiza() {
		if (ultimaActualizacion == null) {
			ultimaActualizacion = LocalDateTime.now();
		}
		LocalDateTime tiempoIntervalo = LocalDateTime.now().minusSeconds(INTERVALO_ACTUALIZACION);
		if (tiempoIntervalo.isAfter(ultimaActualizacion) || tiempoIntervalo.isEqual(ultimaActualizacion)) {

			for (Comando com : comandos) {
				// Crear direcciones validas
				Contexto contexto = new Contexto(hasVentana(com.getPosicionActual()), getDireccionesPosibles(com));
				Decision decision = com.tomarDecisiones(contexto);
				verificaDecisiones(com, contexto, decision);

			}
			descenderBombas();
			verificaFinJuego();
			ultimaActualizacion = LocalDateTime.now();
		}
	}

	private void verificaFinJuego() {
		for (Posicion bomba : bombas) {
			if (bomba.getY() == 0) {
				this.estado = Estado.FIN_POR_BOMBA;
				break;
			}
		}
		for (Comando comando : comandos) {
			if (comando.getPosicionActual().getY() == 0) {
				this.estado = Estado.FIN_POR_CONTROL;
				break;
			}
		}
	}

	private void descenderBombas() {
		HashSet<Posicion> resultado = new HashSet<Posicion>();
		for (Posicion bomba : bombas) {
			Posicion p = new Posicion(bomba.getX(), bomba.getY() - 1);
			resultado.add(p);
		}
		bombas = resultado;
	}

	private void verificaDecisiones(Comando com, Contexto contexto, Decision decision) {
		if (decisionTirarBombaCorrecta(decision, com, contexto)) {
			bombas.add(com.getPosicionActual());
			com.bombaLiberada();
		}

		Posicion pos = decisionMovimientoCorrecta(decision, com);
		if (pos != null) {
			com.addHistoricoPosiciones(pos);
		}
	}

	private Posicion decisionMovimientoCorrecta(Decision decision, Comando com) {
		if (decision != null && decision.getDirMovimiento() != null) {
			Posicion pos = com.getPosicionActual().adyacente(decision.getDirMovimiento());
			HashSet<Posicion> posComandos = devuelvePosicionComandos();
			if (isPosicionValida(pos) && !isCerrada(pos) && !posComandos.contains(pos)) {
				return pos;
			}
		}
		return null;
	}

	private boolean decisionTirarBombaCorrecta(Decision decision, Comando com, Contexto con) {
		if (decision != null) {
			return (decision.isTirarBomba() && com.getNumBombasDis() >= 1 && con.isEstaEnVentana());
		}
		return false;
	}
}
