package gestor;

import dao.VehiculoDAO;
import enums.EstadoVehiculo;
import enums.TipoCombustible;
import excepciones.AccesoDatosException;
import excepciones.ElementoNoEncontradoException;
import excepciones.VehiculoNoDisponibleException;
import interfaces.DAO;
import modelo.Vehiculo;
import java.util.ArrayList;
import java.util.List;

// TODO (Ej. 4.b): esta clase hereda de un gestor general que todavía no existe.
public class GestorVehiculos extends Gestor<Vehiculo> {

    // TODO (Ej. 4.a): el gestor accede a la base a través del contrato general de persistencia, que todavía no
    //       existe.
    private final DAO<Vehiculo> dao = new VehiculoDAO();

    public List<Vehiculo> listarDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : elementos) {
            if (v.getEstado() == EstadoVehiculo.DISPONIBLE) {
                disponibles.add(v);
            }
        }
        return disponibles;
    }

    public List<Vehiculo> listarPorCombustible(TipoCombustible combustible) {
        // TODO (Ej. 2.f): quedarse solo con los vehículos del combustible pedido y devolverlos.
        List<Vehiculo> resultado = new ArrayList<>();
        for (Vehiculo v: elementos){
            if (v.getCombustible() == combustible){
                resultado.add(v);
            }
        }
        return resultado;
    }

    public void alquilarVehiculo(String patente) throws ElementoNoEncontradoException, VehiculoNoDisponibleException {
        Vehiculo v = buscar(patente);
        v.alquilar();
    }

    public void devolverVehiculo(String patente) throws ElementoNoEncontradoException {
        Vehiculo v = buscar(patente);
        v.devolver();
    }

    public double calcularRecaudacionEstimada(int dias) {
        double total = 0;
        for (int i = 0; i < elementos.size(); i++) {
            total += elementos.get(i).calcularCostoAlquiler(dias);
        }
        return total;
    }

    public void guardarEnBD(Vehiculo v) throws AccesoDatosException {
        dao.insertar(v);
    }

    public List<Vehiculo> listarDesdeBD() throws AccesoDatosException {
        return dao.listarTodos();
    }

    public Vehiculo buscarEnBD(String patente) throws AccesoDatosException, ElementoNoEncontradoException {
        return dao.buscarPorId(patente);
    }

    public void actualizarEnBD(Vehiculo v) throws AccesoDatosException, ElementoNoEncontradoException {
        dao.actualizar(v);
    }

    public void eliminarDeBD(String patente) throws AccesoDatosException, ElementoNoEncontradoException {
        dao.eliminar(patente);
    }
}
