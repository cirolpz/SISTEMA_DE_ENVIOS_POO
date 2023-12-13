package Amazing;

public class PaqueteOrdinario extends Paquete {
	double costoEnvio;
	
    public PaqueteOrdinario(int id, int volumen, double precio, double costoEnvio,boolean entregado) {
        super(id, volumen, precio,entregado);
        this.costoEnvio = costoEnvio;
        this.precio = calcularCostoTotalDelPaquete();
    }

    public double calcularCostoTotalDelPaquete() {
    	double costoTotal = obtenerPrecio() + costoEnvio;
    	return costoTotal; 
    }

    @Override
    public String toString() {
        return " id de  paquete ordinario, ";
    }
}