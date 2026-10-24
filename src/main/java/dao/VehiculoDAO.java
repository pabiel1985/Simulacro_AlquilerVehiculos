package dao;

import config.ConexionDB;
import enums.EstadoVehiculo;
import enums.TipoCombustible;
import excepciones.AccesoDatosException;
import excepciones.ElementoNoEncontradoException;
import interfaces.DAO;
import modelo.Auto;
import modelo.Camioneta;
import modelo.Moto;
import modelo.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

// TODO (Ej. 4.a): esta clase dice cumplir un contrato general de persistencia que todavía no existe.
public class  VehiculoDAO implements DAO<Vehiculo> {

    private static final String SQL_INSERTAR = "INSERT INTO vehiculos (patente, tipo, marca, modelo, anio, precio_base_diario, "
            + "combustible, estado, cantidad_puertas, cilindrada, traccion_4x4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM vehiculos ORDER BY patente";
    private static final String SQL_BUSCAR = "SELECT * FROM vehiculos WHERE patente = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE vehiculos SET precio_base_diario = ?, estado = ? WHERE patente = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM vehiculos WHERE patente = ?";

    @Override
    public void insertar(Vehiculo v) throws AccesoDatosException {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR)) {
            ps.setString(1, v.getPatente());
            ps.setString(2, v.getTipo());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setInt(5, v.getAnio());
            ps.setDouble(6, v.getPrecioBaseDiario());
            ps.setString(7, v.getCombustible().name());
            ps.setString(8, v.getEstado().name());
            ps.setNull(9, Types.INTEGER);
            ps.setNull(10, Types.INTEGER);
            ps.setNull(11, Types.BOOLEAN);
            if (v instanceof Auto) {
                ps.setInt(9, ((Auto) v).getCantidadPuertas());
            } else if (v instanceof Moto) {
                ps.setInt(10, ((Moto) v).getCilindrada());
            } else if (v instanceof Camioneta) {
                ps.setBoolean(11, ((Camioneta) v).isTraccion4x4());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al insertar el vehículo " + v.getPatente(), e);
        }
    }

    @Override
    public List<Vehiculo> listarTodos() throws AccesoDatosException {
        List<Vehiculo> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearVehiculo(rs));
            }
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al listar los vehículos", e);
        }
        return lista;
    }

    // TODO (Ej. 5.a): falta poder traer un vehículo puntual de la base a partir de su patente (su id).

    // TODO (Ej. 5.b): falta poder guardar en la base los cambios de precio y de estado de un vehículo.

    @Override
    public void eliminar(String patente) throws AccesoDatosException, ElementoNoEncontradoException {
        // TODO (Ej. 5.c): hoy no elimina nada, y tampoco avisa si el vehículo no existe.
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setString(1, patente);
        } catch (SQLException e) {
            throw new AccesoDatosException("Error al eliminar el vehículo " + patente, e);
        }
    }

    @Override
    public Vehiculo buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException {
        return null;
    }

    @Override
    public void actualizar(Vehiculo entidad) throws AccesoDatosException, ElementoNoEncontradoException {

    }


    private Vehiculo crearVehiculo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String patente = rs.getString("patente");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        int anio = rs.getInt("anio");
        double precio = rs.getDouble("precio_base_diario");
        TipoCombustible combustible = TipoCombustible.valueOf(rs.getString("combustible"));

        Vehiculo v;
        switch (tipo) {
            case "AUTO":
                v = new Auto(patente, marca, modelo, anio, precio, combustible, rs.getInt("cantidad_puertas"));
                break;
            case "MOTO":
                v = new Moto(patente, marca, modelo, anio, precio, combustible, rs.getInt("cilindrada"));
                break;
            case "CAMIONETA":
                v = new Camioneta(patente, marca, modelo, anio, precio, combustible, rs.getBoolean("traccion_4x4"));
                break;
            default:
                throw new SQLException("Tipo de vehículo desconocido: " + tipo);
        }
        v.setEstado(EstadoVehiculo.valueOf(rs.getString("estado")));
        return v;
    }
}
