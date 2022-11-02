package comandos.personajes;

import java.util.List;

import comandos.estructura.Direccion;

public class Contexto {
	private final boolean estaEnVentana;
	private final List<Direccion> listDir;
	
	


	public Contexto(boolean estaEnVentana, List<Direccion> listDir) {
		this.estaEnVentana=estaEnVentana;
		this.listDir=listDir;
		
	}
	
	public boolean isEstaEnVentana() {
		return estaEnVentana;
	}

	public List<Direccion> getListDir() {
		return listDir;
	}
	
	
	

}
