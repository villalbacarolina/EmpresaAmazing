package Amazing;

public class Especial extends Paquete {
	private double porcentaje;
	private double adicional;
	
	public Especial(int id, int volumen, int precio, int porcentaje, int adicional) {
		super(id, volumen, precio);
		if ( !(porcentaje>0) || !(adicional>0) )
			throw new RuntimeException("Porcentaje o adicional invalidos.");
		
		this.porcentaje = porcentaje;
		this.adicional = adicional;
	}
	
	public double consultarPorcentaje() {
		return porcentaje;
	}
	
	public double consultarAdicional() {
		return adicional;
	}
	
	public boolean tienenMismoPorcentaje(double porcentaje) {
		return this.porcentaje == porcentaje;
	}
	
	public boolean tienenMismoAdicional(double adicional) {
		return this.adicional== adicional;
	}
	
	@Override
	public double calcularImporte() {
		
		super.precio += (porcentaje / 100) * super.precio;

	    if (tieneVolumenMayor(3000)) 
	    	super.precio += adicional;
	    
	    if (tieneVolumenMayor(5000)) 
	    	super.precio += adicional;

	    return super.precio;
	}
	
	@Override
	public boolean tienenMismoTipo(Paquete p) {
		return p instanceof Especial;
	}

	@Override
	public boolean tienenMismosAtributos(Paquete p) {
		return ((Especial) p).tienenMismoPorcentaje(this.porcentaje) && ((Especial) p).tienenMismoAdicional(this.adicional);
	}
}
