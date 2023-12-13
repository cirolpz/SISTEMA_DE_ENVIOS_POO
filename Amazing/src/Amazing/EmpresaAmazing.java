package Amazing;

import java.util.*;
import java.util.stream.Collectors;

public class EmpresaAmazing implements IEmpresa {
    public String numeroCuit;
    private HashMap<String, Transporte> transportes;
    private HashMap<Integer,Pedido> pedidos;
    private HashMap<Integer,Pedido>pedidoDelPaquete;//cod de paquete obtiene pedido
    private int codPaquete;
    private double totalFacturadoPedidosCerrados;
    public EmpresaAmazing(String numeroCuit) {
        this.numeroCuit = numeroCuit;
        this.transportes = new HashMap<>();
        this.pedidos = new HashMap<>();
        this.pedidoDelPaquete = new HashMap<>();
        this.totalFacturadoPedidosCerrados = 0;
    }
    private void lanzarExcepcion(String mensaje) {
        throw new RuntimeException(mensaje);
    }
    @Override
    public void registrarAutomovil(String patente, int volMax, int valorViaje, int maxPaq) {
        if (!contienePatente(patente)) {
            Transporte transporte = new Automovil(patente, volMax, valorViaje, maxPaq);
            agregarTransporte(patente, transporte);
        } else {
            lanzarExcepcion("Error: La patente '" + patente + "' ya está registrada.");
        }
    }

    private boolean contienePatente(String patente){
        return  transportes.containsKey(patente);
}
private void agregarTransporte(String patente,Transporte transporte){
        transportes.put(patente,transporte);
}

    @Override
    public void registrarUtilitario(String patente, int volMax, int valorViaje, int valorExtra) {
        if (!contienePatente(patente)) {
            Transporte transporte = new Utilitario(patente, volMax, valorViaje, valorExtra);
            agregarTransporte(patente, transporte);
        } else {
            lanzarExcepcion("La patente ya está registrada.");
        }
    }

    @Override
    public void registrarCamion(String patente, int volMax, int valorViaje, int adicXPaq) {
        if (!contienePatente(patente)) {
            Transporte transporte = new Camion(patente, volMax, valorViaje, adicXPaq);
            agregarTransporte(patente, transporte);
        } else {
            lanzarExcepcion("La patente ya está registrada.");
        }
    }

    @Override
    public int registrarPedido(String cliente, String direccion, int dni) {
        int codigoPedido = longitudDePedidos() + 10;
        Pedido pedido = new Pedido(codigoPedido, dni,cliente, direccion);
        agergarPedidoALista(pedido);
        return codigoPedido;

    }
    private int longitudDePedidos(){
        return pedidos.size();
    }

    private void agergarPedidoALista(Pedido pedido){
        pedidos.put(pedido.obtenerCodPedido(),pedido);
    }

    @Override
    public int agregarPaquete(int codPedido, int volumen, int precio, int costoEnvio) {
        Pedido pedido = buscarPedidoPorCodigo(codPedido);
        codPaquete++;
        validarPedidoExistente(pedido);
        validarPedidoAbierto(pedido);
        Paquete paquete = crearPaqueteOrdinario(volumen, precio, costoEnvio);
        agregarPaqueteAlPedido(pedido, paquete);
        return codPaquete;
    }
    private void validarPedidoExistente(Pedido pedido) {//Otra alternativa para lanzar excepciones
        if (pedido == null) {
            throw new RuntimeException("El pedido no está registrado.");
        }
    }

    private void validarPedidoAbierto(Pedido pedido) {
        if (pedido.fueCerrado()) {
            throw new RuntimeException("El pedido ya está finalizado.");
        }
    }

    private Paquete crearPaqueteOrdinario(int volumen, int precio, int costoEnvio) {
        return new PaqueteOrdinario(codPaquete, volumen, precio, costoEnvio, false);
    }

    private void agregarPaqueteAlPedido(Pedido pedido, Paquete paquete) {
        pedido.agregarPaquete(paquete);
        pedido.organizarPaquetes();
        agregarPedidoAlMapa(codPaquete, pedido);
    }
    private Paquete crearPaqueteEspecial(int volumen, int precio, int porcentaje, int adicional) {
        return new PaqueteEspecial(codPaquete, volumen, precio, porcentaje, adicional, false);
    }
    @Override
    public int agregarPaquete(int codPedido, int volumen, int precio, int porcentaje, int adicional) {
        Pedido pedido = buscarPedidoPorCodigo(codPedido);
        codPaquete++;
        validarPedidoExistente(pedido);
        validarPedidoAbierto(pedido);
        Paquete paqueteEspecial = crearPaqueteEspecial(volumen, precio, porcentaje, adicional);
        agregarPaqueteAlPedido(pedido, paqueteEspecial);
        return codPaquete;
    }
    private Pedido buscarPedidoPorCodigo(int codPedido) {
        for (Pedido pedido : pedidos.values()) {
            if (pedido.tieneMismoCodPedido(codPedido)) {
                return pedido;
            }
        }
        return null;
    }
    @Override
    public boolean quitarPaquete(int codPaquete) {
        Pedido pedidoConPaquete = buscarPedidoPorPaquete(codPaquete);

        if (pedidoConPaquete != null) {
            return quitarPaqueteDelPedido(pedidoConPaquete, codPaquete);
        } else {
            throw new RuntimeException("El código de paquete no se encuentra registrado.");
        }
    }
    private Pedido buscarPedidoPorPaquete(int codPaquete) {
        for (Pedido pedido : pedidos.values()) {
            if (pedido.tienePaquete(codPaquete)) {
                return pedido;
            }
        }
        return null;
    }
    private boolean quitarPaqueteDelPedido(Pedido pedido, int codPaquete) {
        return pedido.quitarPaquete(codPaquete);
    }
    @Override
    public double cerrarPedido(int codPedido) {
        Pedido pedido = buscarPedidoPorCodigo(codPedido);

        if (pedido != null) {
            validarPedidoAbierto(pedido);
            cerrarYFacturarPedido(pedido);
            return pedido.calcularCostoPedido();
        } else {
            throw new RuntimeException("El pedido no está registrado.");
        }
    }
    private void cerrarYFacturarPedido(Pedido pedido) {
        pedido.cerrarPedido();
        totalFacturadoPedidosCerrados += pedido.calcularCostoPedido();
    }
    @Override
    public String cargarTransporte(String patente) {
        validarExistenciaTransporte(patente);
        StringBuilder listado = new StringBuilder();

        for (Pedido pedido : obtenerPedidosCerrados()) {
            cargarPaquetesNoEntregados(patente, pedido, listado);
        }

        reemplazarUnderscoresPorMas(listado);

        return listado.toString();
    }

    // Función para validar la existencia del transporte
    private void validarExistenciaTransporte(String patente) {
        if (!transportes.containsKey(patente)) {
            throw new RuntimeException("La patente no está en el sistema.");
        }
    }

    // Función para obtener los pedidos cerrados
    private Collection<Pedido> obtenerPedidosCerrados() {
        return pedidos.values().stream()
                .filter(Pedido::fueCerrado)
                .collect(Collectors.toList());
    }

    // Función para cargar paquetes no entregados al transporte
    private void cargarPaquetesNoEntregados(String patente, Pedido pedido, StringBuilder listado) {
        HashMap<Integer, Paquete> paquetes = pedido.obtenerPaquetes();
        for (Paquete paq : paquetes.values()) {
            if (!paq.isEntregado()) {
                transportes.get(patente).cargarPaquetes(paq);
                agregarInfoPaqueteAlListado(paq, listado);
            }
        }
    }

    // Función para agregar información del paquete al listado
    private void agregarInfoPaqueteAlListado(Paquete paq, StringBuilder listado) {
        int codPaquete = paq.obtenerId();
        if (pedidoDelPaquete.containsKey(codPaquete)) {
            Pedido pedidoAsociado = pedidoDelPaquete.get(codPaquete);
            int nroPedido = pedidoAsociado.codPedido;
            String direccion = pedidoAsociado.obtenerDireccionCliente();
            listado.append(" _ [ ").append(nroPedido).append(" - ").append(codPaquete).append(" ] ").append(direccion).append("\n");
        }
    }

    // Función para reemplazar "_" por "+"
    private void reemplazarUnderscoresPorMas(StringBuilder listado) {
        int index = -1;
        while ((index = listado.indexOf("_", index + 1)) != -1) {
            listado.replace(index, index + 1, "+");
        }
    }
    public void agregarPedidoAlMapa(int codPaquete, Pedido pedido) {
        pedidoDelPaquete.put(codPaquete, pedido);
    }

    @Override
    public double costoEntrega(String patente) {
        validarExistenciaPatente(patente);
        Transporte transporte = obtenerTransporte(patente);
        return transporte.calcularCostoEntrega();
    }

    // Función para validar la existencia de la patente
    private void validarExistenciaPatente(String patente) {
        if (!transportes.containsKey(patente)) {
            throw new RuntimeException("La patente no está en el sistema.");
        }
    }

    // Función para obtener el transporte asociado a la patente
    private Transporte obtenerTransporte(String patente) {
        return transportes.get(patente);
    }
    @Override
    public Map<Integer, String> pedidosNoEntregados() {
        Map<Integer, String> pedidosNoEntregados = new HashMap<>();
        for (Pedido pedido : pedidos.values()) {
            if (pedidoFueCerradoYtienePaquetesSinEntregar(pedido)) {
                agregarPedidoNoEntregado(pedidosNoEntregados, pedido);
            }
        }
        return pedidosNoEntregados;
    }
    private boolean pedidoFueCerradoYtienePaquetesSinEntregar(Pedido pedido) {
        return pedido.siFueCerradoYtienePaquetesSinEntregar();
    }
    private void agregarPedidoNoEntregado(Map<Integer, String> pedidosNoEntregados, Pedido pedido) {
        pedidosNoEntregados.put(pedido.obtenerCodPedido(), pedido.obtenerNombreCliente());
    }

    @Override
    public double facturacionTotalPedidosCerrados() {
    	return totalFacturadoPedidosCerrados;
    }

    @Override
    public boolean hayTransportesIdenticos() {
        List<Transporte> listaTransportes = obtenerListaTransportes();

        for (int i = 0; i < listaTransportes.size(); i++) {
            Transporte transporte1 = listaTransportes.get(i);
            if (existeTransporteIdentico(transporte1, listaTransportes, i)) {
                return true;
            }
        }

        return false;
    }
    private List<Transporte> obtenerListaTransportes() {
        return new ArrayList<>(transportes.values());
    }
    private boolean existeTransporteIdentico(Transporte transporte, List<Transporte> listaTransportes, int indice) {
        for (int j = indice + 1; j < listaTransportes.size(); j++) {
            Transporte transporte2 = listaTransportes.get(j);
            if (transporte.equals(transporte2)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public String toString() {
        return "\n\nEmpresa Amazing: numero de cuit: '" + numeroCuit +"'\n\nLos transportes dispónibles son: \n" + transportes + "\n\nLos pedidos:\n" + pedidos;
    }

}
