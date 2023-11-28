package Amazing;

public class Utilitario extends Transporte {
	private double valorExtra;
	
	
	public Utilitario(String patente, int volMax, double valorViaje, double valorExtra) {
		super(patente, volMax, valorViaje);
		if (valorExtra<0)
			throw new RuntimeException("ERROR: datos invalidos");
		this.valorExtra = valorExtra;
	}

	@Override
	public double calcularCosto() {
		if (consultarCantPaq() == 0)
			throw new RuntimeException("ERROR: no hay paquetes cargados");
		if (consultarCantPaq()>3)
			return consultarValorXViaje() + valorExtra;
		
		return consultarValorXViaje();
	}
	
	@Override
	public boolean tienenMismoTipo(Transporte t) {
		return t instanceof Utilitario;
	}
	
	@Override
	public void agregarPaquete(int codPaquete, Paquete paq, int codPedido, String direccion) {
		if (   !paq.tieneVolumenMayor(super.volMax) 
			&& !paq.cargado())
				cargar(codPaquete, paq, codPedido, direccion);
	}
}
