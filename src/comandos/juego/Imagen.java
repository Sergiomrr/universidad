package comandos.juego;

public class Imagen {
		private final String ruta;
		private final int coordenadaX;
		private final int coordenadaY;
		
		public Imagen(String ruta, int coordenadaX, int coordenadaY) {
			this.ruta=ruta;
			this.coordenadaX= coordenadaX;
			this.coordenadaY= coordenadaY;
			
		}

		public String getRuta() {
			return ruta;
		}

		public int getCoordenadaX() {
			return coordenadaX;
		}

		public int getCoordenadaY() {
			return coordenadaY;
		}
		
		
		
		
		
		
		
		
}
