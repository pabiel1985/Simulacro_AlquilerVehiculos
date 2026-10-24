package interfaces;

import excepciones.AccesoDatosException;
import excepciones.ElementoDuplicadoException;
import excepciones.ElementoNoEncontradoException;
import modelo.Vehiculo;

import java.sql.ResultSet;
import java.util.List;

public interface DAO <t>{
    void  insertar(t elemento) throws AccesoDatosException;
    List<t> listarTodos() throws AccesoDatosException;
    void  eliminar(String id) throws AccesoDatosException, ElementoNoEncontradoException;



}
