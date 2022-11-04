package comandos.personajes;

import java.util.HashSet;
import java.util.Random;

import comandos.personajes.Contexto;
import comandos.personajes.Decision;
import comandos.estructura.Direccion;
import comandos.estructura.Posicion;
import comandos.estructura.TipoComando;

public class Comando implements Cloneable{

	private Posicion posicionActual;
	private HashSet<Posicion> historicoPosiciones;
	private static final String RUTA_IMAGEN_COMANDO = " imagenes/comando.png";
	private final int numBombas;
	private int numBombasDis;
	private HashSet<Posicion> ventanasConocidas;
	protected TipoComando tipo;
	
	public Comando(int numBombas) {
		posicionActual = null;
		this.historicoPosiciones = new HashSet<Posicion>();
		this.numBombas = numBombas;
		this.numBombasDis = numBombas;
		this.ventanasConocidas = new HashSet<Posicion>();
		this.tipo=TipoComando.BASICO;

	}

	public Posicion getPosicionActual() {
		return posicionActual;
	}
	

	public int getNumBombas() {
		return numBombas;
	}

	public boolean addHistoricoPosiciones(Posicion p) {
		this.posicionActual = p;
		return historicoPosiciones.add(p);

	}

	public static String getRutaImagenComando() {
		return RUTA_IMAGEN_COMANDO;
	}

	public Decision tomarDecisiones(Contexto con) {
		if (con.isEstaEnVentana()) {
			ventanasConocidas.add(posicionActual);
		}
		return new Decision(decidirTirarBomba(con), decidirMovimiento(con));
	}

	protected boolean decidirTirarBomba(Contexto con) {
		return (this.numBombasDis >= 1 && con.isEstaEnVentana());
	}

	protected Direccion decidirMovimiento(Contexto con) {
		if (con.getListDir().size() > 0) {
			Random gen = new Random();
			int posComando = gen.nextInt(con.getListDir().size());
			return con.getListDir().get(posComando);
		}
		return null;
	}

	public int getNumBombasDis() {
		return numBombasDis;
	}
	
	public TipoComando getTipo() {
		return tipo;
	}

	public void bombaLiberada() {
		this.numBombasDis -= 1;
	}
//el metodo introducir comando hay que pasarle un comando?
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		Comando com = (Comando) super.clone();
		com.numBombasDis=this.numBombas;
		return com;
	}

	
	
	
}
