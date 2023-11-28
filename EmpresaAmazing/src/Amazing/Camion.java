package Amazing;

public class Camion extends Transporte{
	private double adicXPaq;
	
	public Camion(String patente, int volMax, int valorViaje, int adicXPaq) {
		super(patente, volMax, valorViaje);
		if (adicXPaq<0)
			throw new RuntimeException("Adicional del tranporte invalido.");
		this.adicXPaq = adicXPaq;
	}
	
	@Override
	public double calcularCosto() {
		return consultarValorXViaje() + consultarCantPaq()*adicXPaq;
	}

	@Override
	public boolean tienenMismoTipo(Transporte t) {
		return t instanceof Camion;
	}
	
	@Override 
	public void agregarPaquete(int idPaquete, Paquete paq, int codPedido, String direccion) {
		if (   paq instanceof Especial 
			&& paq.tieneVolumenMayor(2000)
			&& !paq.tieneVolumenMayor(volMax) 
			&& !paq.cargado())
				cargar(idPaquete, paq, codPedido, direccion);
	}
}
