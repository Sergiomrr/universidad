package comandos.personajes;

import comandos.estructura.TipoComando;

public class ComandoHalcon extends Comando {
	private final int VIDAS=3;
	private int vidasDisponibles;
	private int anchoEdificio;
	
	
	public ComandoHalcon(int numBombas) {
		super(numBombas);
		this.vidasDisponibles=VIDAS;
		this.anchoEdificio=0;
		this.tipo=TipoComando.HALCON;
	}
	


	public void setAnchoEdificio(int anchoEdificio) {
		this.anchoEdificio = anchoEdificio;
	}



	public int getVidasDisponibles() {
		return vidasDisponibles;
	}


	public int restaVidasDisponibles(int vidasDisponibles) {
		return this.vidasDisponibles--;
	}
	
	@Override
	protected boolean decidirTirarBomba(Contexto con) {
		return (super.decidirTirarBomba(con) && getPosicionActual().getY()> anchoEdificio/2);
	}
	

	
	
	
	
	
	
	
	
}
