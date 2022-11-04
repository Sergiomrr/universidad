package comandos.personajes;

import comandos.estructura.Direccion;
import comandos.estructura.TipoComando;

public class ComandoAlfa extends Comando{
	private int alturaEdificio;
	
	
	public ComandoAlfa(int numBombas) {
		super( numBombas);
		this.alturaEdificio=0;	
		this.tipo=TipoComando.ALFA;
	}
	
	@Override
	protected boolean decidirTirarBomba(Contexto con) {
		return (super.decidirTirarBomba(con) && getPosicionActual().getY()<= alturaEdificio/2);
	}
	
	
	
	public void setAlturaEdificio(int alturaEdificio) {
		this.alturaEdificio = alturaEdificio;
	}

	@Override
	protected Direccion decidirMovimiento(Contexto con) {
		if(con.getListDir().contains(Direccion.ABAJO)) {
			return Direccion.ABAJO;
		}
		return super.decidirMovimiento(con);
	}
	

}
