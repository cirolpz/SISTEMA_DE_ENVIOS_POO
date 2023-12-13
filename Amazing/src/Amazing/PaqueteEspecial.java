package Amazing;

public class PaqueteEspecial extends Paquete {
    private double valorAdicional;
    private double porcentajeAdicional;

    public PaqueteEspecial(int id, int volumen, double precio, double porcentaje, double valorAdicional ,boolean entregado) {
        super(id, volumen, precio,entregado);
        this.valorAdicional = valorAdicional;
        this.porcentajeAdicional = porcentaje;
        this.precio = calcularCostoTotalDelPaquete();
    }

    @Override
    public double calcularCostoTotalDelPaquete() {
        double costoPorcentajeAdicional = calcularCostoPorcentajeAdicional();
        double costoAdicionalPorVolumen = calcularCostoAdicionalPorVolumen();
        double costoTotal = obtenerPrecio() + costoPorcentajeAdicional + costoAdicionalPorVolumen;
        return costoTotal;
    }

    private double calcularCostoPorcentajeAdicional() {
        return obtenerPrecio() * (porcentajeAdicional / 100);
    }

    private double calcularCostoAdicionalPorVolumen() {
        double costoAdicionalPorVolumen = 0;
        if (obtenerVolumen() >= 3000) {
            costoAdicionalPorVolumen = valorAdicional;
            if (obtenerVolumen() >= 5000) {
                costoAdicionalPorVolumen *= 2;
            }
        }
        return costoAdicionalPorVolumen;
    }
    @Override
    public String toString() {
        return " id de paquete especial, ";
    }
}
