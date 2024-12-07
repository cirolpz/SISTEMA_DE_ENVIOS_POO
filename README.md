# EmpresaAmazing

EmpresaAmazing es una aplicación Java que gestiona pedidos y transporte para una empresa. Permite registrar vehículos, crear pedidos, agregar paquetes a los pedidos y gestionar el proceso de entrega.

## Características

- Registrar diferentes tipos de vehículos (automóviles, utilitarios, camiones).
- Crear y gestionar pedidos.
- Agregar paquetes a los pedidos.
- Cerrar pedidos y calcular la facturación total.
- Cargar vehículos de transporte con paquetes.
- Calcular costos de entrega.
- Listar pedidos no entregados.
- Verificar si hay vehículos de transporte idénticos.

## Comenzando

### Requisitos Previos

- Java Development Kit (JDK) 8 o superior
- IntelliJ IDEA u otro IDE para Java

### Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/cirolpz/EmpresaAmazing.git
    ```
2. Abre el proyecto en IntelliJ IDEA.

### Ejecutar la Aplicación

1. Navega al archivo `src/Amazing/Main.java`.
2. Ejecuta la clase `Main`.

### Ejecutar Pruebas

1. Navega al archivo `test/Amazing/EmpresaAmazingTest.java`.
2. Haz clic derecho en la clase de prueba o en los métodos de prueba individuales y selecciona "Run".

## Conceptos de Programación Orientada a Objetos (POO) Utilizados

### Herencia

La herencia es un mecanismo que permite crear una nueva clase a partir de una clase existente. En este proyecto, se utiliza la herencia para definir diferentes tipos de vehículos. Por ejemplo, `Automovil`, `Utilitario` y `Camion` heredan de una clase base `Vehiculo`.

### Sobreescritura

La sobreescritura es una característica que permite a una subclase proporcionar una implementación específica de un método que ya está definido en su superclase. En este proyecto, se utiliza la sobreescritura para definir comportamientos específicos de los métodos en las clases derivadas de `Vehiculo`.

### Encapsulamiento

El encapsulamiento es el principio de ocultar los detalles internos de un objeto y exponer solo lo necesario a través de métodos públicos. En este proyecto, se utiliza el encapsulamiento para proteger los datos de la clase `EmpresaAmazing` y sus métodos.

### Polimorfismo

El polimorfismo permite tratar objetos de diferentes clases de una manera uniforme. En este proyecto, se utiliza el polimorfismo para manejar diferentes tipos de vehículos a través de una referencia de la clase base `Vehiculo`.

Estos conceptos de POO ayudan a estructurar el código de manera modular y reutilizable, facilitando el mantenimiento y la extensión del proyecto.
### Sobrecarga

La sobrecarga es una característica que permite definir múltiples métodos con el mismo nombre pero con diferentes parámetros en una misma clase. En este proyecto, se utiliza la sobrecarga en la clase `EmpresaAmazing` para el método `agregarPaquete`, permitiendo agregar paquetes con diferentes conjuntos de parámetros.

## Uso

La clase `Main` demuestra cómo usar la clase `EmpresaAmazing` para registrar vehículos, crear pedidos, agregar paquetes y gestionar entregas. Puedes modificar la clase `Main` para probar diferentes escenarios.

### Ejemplo

```java
public class Main {
    public static void main(String[] args) {
        EmpresaAmazing empresa = new EmpresaAmazing("30-456789-2");

        // Registrar vehículos
        empresa.registrarAutomovil("AB444ZZ", 10000, 3500, 5);
        empresa.registrarUtilitario("AA222FF", 18000, 10000, 10000);
        // ... más código ...

        // Crear pedidos
        int p1 = empresa.registrarPedido("Angel Gutierrez", "San Martin 321", 28324132);
        // ... más código ...

        // Agregar paquetes a los pedidos
        int paq1 = empresa.agregarPaquete(p1, 1235, 2890, 1000);
        // ... más código ...

        // Cerrar pedidos
        empresa.cerrarPedido(p1);
        // ... más código ...

        // Cargar vehículos de transporte
        System.out.println(empresa.cargarTransporte("AA222FF"));
        // ... más código ...

        // Calcular costos de entrega
        System.out.println("Costo del transporte: " + empresa.costoEntrega("AE555YY"));

        // Imprimir facturación total
        System.out.println("Facturación total de pedidos: " + empresa.facturacionTotalPedidosCerrados());

        // Listar pedidos no entregados
        System.out.println("Listados con paquetes sin entregar: " + empresa.pedidosNoEntregados());

        // Verificar si hay vehículos de transporte idénticos
        System.out.println("Hay transportes iguales: " + empresa.hayTransportesIdenticos());

        // Imprimir detalles de la empresa
        System.out.println(empresa.toString());
    }
}

