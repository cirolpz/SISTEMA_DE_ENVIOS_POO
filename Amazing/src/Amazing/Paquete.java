package Amazing;

public abstract class Paquete {

    private int id;
    private int volumen;
    protected double precio;
    private boolean entregado;

    public Paquete(int id, int volumen, double precio, boolean entregado) {
        this.id = id;
        this.volumen = volumen;
        this.precio = precio;
        this.entregado = entregado;
    }

    public double calcularCostoTotalDelPaquete() {
    	return 0;
    };

    public int obtenerId() {
        return id;
    }
    
    public int obtenerVolumen() {
        return volumen;
    }
    public double obtenerPrecio() {
        return precio;
    }


    public boolean isEntregado() {
        return entregado;
    }

    public void marcarEntregado() {
        this.entregado = true;
    }



    @Override
    public String toString() {
        return "el paquete con id: " + id +" tiene de volumen " + volumen +" el precio es $" + precio + ", se entregaron:" + entregado  + ' ';
    }
}
