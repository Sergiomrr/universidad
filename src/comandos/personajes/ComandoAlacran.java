package comandos.personajes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import comandos.estructura.Direccion;
import comandos.estructura.Posicion;
import comandos.estructura.TipoComando;

public class ComandoAlacran extends Comando {

	public ComandoAlacran(int numBombas) {
		super(numBombas);
		this.tipo=TipoComando.ALACRAN;

	}

	// Este tipo de comando es capaz de pasar por las zonas cerradas
	// por eso se crea una nueva lista de direcciones en lugar de utilizar la lista
	// de direcciones del contexto
	@Override
	protected Direccion decidirMovimiento(Contexto con) {
		Posicion posComando = getPosicionActual();
		if (posComando.getY() > 3) {
			List<Direccion> listDir = new ArrayList<Direccion>();
			listDir.add(Direccion.ABAJO);
			listDir.add(Direccion.ARRIBA);
			listDir.add(Direccion.DERECHA);
			listDir.add(Direccion.IZQUIERDA);
			Random gen = new Random();
			int dirComando = gen.nextInt(listDir.size());
			return listDir.get(dirComando);
		} else {
			if (con.getListDir().size() > 0) {
				Random gen = new Random();
				int dirComando = gen.nextInt(con.getListDir().size());
				return con.getListDir().get(dirComando);
			}
		}
		return null;
	}

}
