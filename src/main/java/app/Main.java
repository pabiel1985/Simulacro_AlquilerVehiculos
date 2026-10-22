package app;

import enums.EstadoVehiculo;
import enums.TipoCombustible;
import excepciones.AccesoDatosException;
import gestor.GestorVehiculos;
import modelo.Auto;
import modelo.Camioneta;
import modelo.Moto;
import modelo.Vehiculo;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final GestorVehiculos gestor = new GestorVehiculos();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    altaVehiculos();
                    break;
                case 2:
                    if (hayVehiculos()) {
                        listadoGeneral();
                    }
                    break;
                case 3:
                    if (hayVehiculos()) {
                        alquileres();
                    }
                    break;
                case 4:
                    if (hayVehiculos()) {
                        costos();
                    }
                    break;
                case 5:
                    if (hayVehiculos()) {
                        guardarEnBD();
                    }
                    break;
                case 6:
                    operacionesBD();
                    break;
                case 7:
                    altaVehiculos();
                    listadoGeneral();
                    alquileres();
                    costos();
                    guardarEnBD();
                    operacionesBD();
                    System.out.println("\n========== FIN DEL PROGRAMA ==========");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n================ MENÚ DE PRUEBAS ================");
        System.out.println("1. Alta de vehículos");
        System.out.println("2. Listado general");
        System.out.println("3. Alquileres");
        System.out.println("4. Costos de alquiler");
        System.out.println("5. Guardar en BD y listar (JDBC)");
        System.out.println("6. Buscar / actualizar / eliminar en BD (JDBC)");
        System.out.println("7. Ejecutar todo");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private static boolean hayVehiculos() {
        if (gestor.cantidad() == 0) {
            System.out.println("No hay vehículos cargados. Ejecutá primero la opción 1.");
            return false;
        }
        return true;
    }

    private static void altaVehiculos() {
        // TODO (Ej. 3.c): dar de alta puede fallar por una patente repetida o por datos inválidos. Informá cada
        //       problema y que el resto de la flota se cargue igual.
        System.out.println("\n========== 1. ALTA DE VEHÍCULOS ==========");
        gestor.agregar(new Auto("AB123CD", "Toyota", "Corolla", 2022, 20000, TipoCombustible.NAFTA, 4));
        gestor.agregar(new Moto("A123BCD", "Honda", "CB500", 2023, 9000, TipoCombustible.NAFTA, 471));
        gestor.agregar(new Moto("A456EFG", "Kawasaki", "Z900", 2024, 14000, TipoCombustible.NAFTA, 948));
        gestor.agregar(new Camioneta("AD789GH", "Ford", "Ranger", 2021, 30000, TipoCombustible.DIESEL, true));
        gestor.agregar(new Camioneta("AE012IJ", "Renault", "Kangoo", 2020, 18000, TipoCombustible.ELECTRICO, false));
        System.out.println("Vehículos cargados: " + gestor.cantidad());

        gestor.agregar(new Auto("AB123CD", "Fiat", "Cronos", 2023, 17000, TipoCombustible.NAFTA, 4));

        Moto invalida = new Moto("", "Honda", "Wave", 2020, -500, TipoCombustible.NAFTA, 110);
        gestor.agregar(invalida);
    }

    private static void listadoGeneral() {
        System.out.println("\n========== 2. LISTADO GENERAL ==========");
        mostrarLista("Todos los vehículos", gestor.listar());
    }

    private static void alquileres() {
        // TODO (Ej. 3.c): alquilar o devolver puede fallar si el vehículo no existe o no está disponible. Cada
        //       intento fallido se informa y el menú sigue.
        System.out.println("\n========== 3. ALQUILERES ==========");
        gestor.alquilarVehiculo("AB123CD");
        System.out.println("Vehículo AB123CD alquilado");
        gestor.alquilarVehiculo("A456EFG");
        System.out.println("Vehículo A456EFG alquilado");
        gestor.alquilarVehiculo("AB123CD");
        System.out.println("Vehículo AB123CD alquilado");

        gestor.alquilarVehiculo("XX000XX");

        gestor.devolverVehiculo("A456EFG");
        System.out.println("Vehículo A456EFG devuelto");

        mostrarLista("Vehículos disponibles", gestor.listarDisponibles());
        mostrarLista("Vehículos DIESEL", gestor.listarPorCombustible(TipoCombustible.DIESEL));
    }

    private static void costos() {
        // TODO (Ej. 3.c): un cálculo con una cantidad de días inválida no debe cortar el programa.
        System.out.println("\n========== 4. COSTOS DE ALQUILER ==========");
        for (Vehiculo v : gestor.listar()) {
            System.out.printf("%-8s %-10s 3 días: $%.2f | 10 días: $%.2f%n",
                    v.getPatente(), v.getTipo(), v.calcularCostoAlquiler(3), v.calcularCostoAlquiler(10));
        }
        System.out.printf("Recaudación estimada de la flota (5 días): $%.2f%n", gestor.calcularRecaudacionEstimada(5));

        Vehiculo primero = gestor.listar().get(0);
        System.out.println("Costo por 0 días: " + primero.calcularCostoAlquiler(0));
    }

    private static void guardarEnBD() {
        // TODO (Ej. 3.c): cualquier operación con la base puede fallar. Si falla un vehículo, que se intente con
        //       los demás. Usá mostrarErrorBD para informar.
        System.out.println("\n========== 5. PERSISTENCIA (JDBC) ==========");
        for (Vehiculo v : gestor.listar()) {
            gestor.guardarEnBD(v);
            System.out.println("Insertado en BD: " + v.getPatente());
        }

        mostrarLista("Vehículos en la base de datos", gestor.listarDesdeBD());
    }

    private static void operacionesBD() {
        // TODO (Ej. 3.c): además de los errores de la base, buscar, actualizar o eliminar algo que no existe
        //       también es un problema a informar. Fijate qué pasos dependen de otros.
        System.out.println("\n========== 6. CONSULTAS Y MODIFICACIONES (JDBC) ==========");
        Vehiculo buscado = gestor.buscarEnBD("AD789GH");
        System.out.println("Encontrado: " + buscado);

        buscado.setPrecioBaseDiario(32000);
        buscado.setEstado(EstadoVehiculo.EN_MANTENIMIENTO);
        gestor.actualizarEnBD(buscado);
        System.out.println("Actualizado: " + gestor.buscarEnBD("AD789GH"));

        gestor.eliminarDeBD("A123BCD");
        System.out.println("Eliminado de BD: A123BCD");
        mostrarLista("Vehículos en la BD luego de eliminar", gestor.listarDesdeBD());

        gestor.buscarEnBD("A123BCD");

        gestor.eliminarDeBD("ZZ999ZZ");
    }

    private static void mostrarLista(String titulo, List<Vehiculo> lista) {
        System.out.println("--- " + titulo + " (" + lista.size() + ") ---");
        if (lista.isEmpty()) {
            System.out.println("(sin elementos)");
            return;
        }
        for (Vehiculo v : lista) {
            System.out.println(v);
        }
    }

    private static void mostrarErrorBD(AccesoDatosException e) {
        System.out.println("ERROR BD: " + e.getMessage());
        if (e.getCause() != null) {
            System.out.println("   Causa: " + e.getCause().getMessage());
        }
    }
}
