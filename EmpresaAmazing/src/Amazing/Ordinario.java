package Amazing;

public class Ordinario extends Paquete {
	
	private double costoEnvio;
	
	public Ordinario(int id, int volumen, double precio, double costoEnvio) {
		super(id, volumen, precio);
		if (!(costoEnvio>0))
			throw new RuntimeException("Costo de envío invalido.");
		this.costoEnvio = costoEnvio;
	}
	@Override
	public double calcularImporte() {  
		return super.precio + costoEnvio;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o) 
//			return true;
//        if (o == null || getClass() != o.getClass()) 
//        	return false;
//        Ordinario paqOrd = (Ordinario) o;
//        return paqOrd.mismosDatos(super.volumen, super.precio, this.costoEnvio);
//    }
//	
//	private boolean mismosDatos(int volumen, double precio, double costoEnvio2) {
//		return this.volumen==volumen && this.precio==precio 
//			&& this.costoEnvio==costoEnvio2;
//	}
//
//	@Override
//    public int hashCode() {
//        return Objects.hash(volumen, precio, costoEnvio);
//    }
	
	
	public boolean tienenMismoCostoEnvio(double costo) {
		return this.costoEnvio == costo;
	}
	
	@Override
	public boolean tienenMismoTipo(Paquete p) {
		return p instanceof Ordinario;
	}
	
	@Override
	public boolean tienenMismosAtributos(Paquete p) {
		return ((Ordinario) p).tienenMismoCostoEnvio(this.costoEnvio); 
	}

}
