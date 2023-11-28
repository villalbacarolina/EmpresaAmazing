package Amazing;

public abstract class Paquete {
	private int codPaquete;
	protected int volumen;
	protected double precio;
	private boolean cargado;
	
	public Paquete (int codPaquete, int volumen, double precio) {
		if(codPaquete<0 || volumen<0 || precio<0)
			throw new RuntimeException("ERROR: datos invalidos");
		
		this.codPaquete = codPaquete;
		this.volumen = volumen;
		this.precio = precio;
	}

	public boolean tieneVolumenMayor(int volumen) {
		return this.volumen>=volumen;
	}
	
	public boolean tienenMismoVolumen(int volumen) {
		return this.volumen==volumen;
	}
	
	
	public boolean cargado() {
		return cargado;
	}
	
	public int codPaquete() {
		return codPaquete;
	}
	
	public int cargarPaquete() {
		this.cargado = true;
		return this.volumen;
	}
	
	public boolean tienenMismasCaracteristicas(Paquete p) {
		return     p.tienenMismoVolumen(volumen) 
				&& tienenMismoTipo(p) 
				&& p.tienenMismoPrecio(precio) 
				&& tienenMismosAtributos(p);
	}
	
	public boolean tienenMismoPrecio(double precio) {
		return this.precio==precio;
	}
	
	public abstract boolean tienenMismoTipo(Paquete p);

	public abstract double calcularImporte();
	
	public abstract boolean tienenMismosAtributos(Paquete p);

}
