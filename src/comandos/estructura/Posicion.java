package comandos.estructura;

import java.util.Objects;

public class Posicion {
	private final int x;
	private final int y;
	
	public Posicion(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Posicion adyacente(Direccion direccion) {
		switch(direccion) {
		case ARRIBA:
			return new Posicion(this.x, this.y + 1);
		case ABAJO:
			return new Posicion(this.x, this.y -1);
		case DERECHA:
			return new Posicion(this.x + 1, this.y);
		case IZQUIERDA:
			return new Posicion(this.x -1, this.y);
		default:
			return null;
		}
	}

	public String toString() {
		return "Posicion [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posicion other = (Posicion) obj;
		return x == other.x && y == other.y;
	}
	
	
	
	
	
}