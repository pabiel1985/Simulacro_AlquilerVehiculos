package interfaces;

import excepciones.AccesoDatosException;
import excepciones.ElementoDuplicadoException;
import excepciones.ElementoNoEncontradoException;
import modelo.Vehiculo;

import java.sql.ResultSet;
import java.util.List;

public interface DAO <T>{
    void  insertar(T elemento) throws AccesoDatosException;
    List<T> listarTodos() throws AccesoDatosException;
    void  eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException;
    T buscarPorId(String id) throws AccesoDatosException, ElementoNoEncontradoException;
    void actualizar(T entidad) throws AccesoDatosException, ElementoNoEncontradoException;

}
