package comandos.personajes;

import comandos.estructura.Direccion;

public class Decision {
	private final boolean tirarBomba;
	private final Direccion dirMovimiento;
	

	public Decision(boolean tirarBomba, Direccion dirMovimiento) {
		this.tirarBomba=tirarBomba;
		this.dirMovimiento=dirMovimiento;
		
	}
	
	
	public boolean isTirarBomba() {
		return tirarBomba;
	}


	public Direccion getDirMovimiento() {
		return dirMovimiento;
	}

	
	
	
	
	
	
	
	
	
	
}
