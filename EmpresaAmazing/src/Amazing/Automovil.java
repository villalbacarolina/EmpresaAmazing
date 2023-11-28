package Amazing;

public class Automovil extends Transporte {
	private int maxPaq;

	public Automovil(String patente, int volMax, int valorViaje, int maxPaq) {
		super(patente, volMax, valorViaje);
		if (maxPaq<=0)
			throw new RuntimeException("Cantidad maxima de paquetes del tranporte invalida.");
		this.maxPaq = maxPaq;
	}

	
	@Override
	public double calcularCosto() {
		return super.valorXViaje;
	}

	@Override
	public boolean tienenMismoTipo(Transporte t) {
		return t instanceof Automovil;
	}

	@Override
	public void agregarPaquete(int codPaquete, Paquete paq, int codPedido, String direccion) {
		if (   paq instanceof Ordinario
			&& !paq.tieneVolumenMayor(2000)
			&& !paq.tieneVolumenMayor(super.volMax) 
			&& !paq.cargado()
			&& consultarCantPaq() <= this.maxPaq)
				cargar(codPaquete, paq, codPedido, direccion);
	}
}
