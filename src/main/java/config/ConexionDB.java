package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/alquiler_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "1234";

    private ConexionDB() {
    }

    public static Connection getConexion() {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
