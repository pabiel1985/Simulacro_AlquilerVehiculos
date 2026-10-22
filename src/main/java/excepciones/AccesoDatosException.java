package excepciones;

public class AccesoDatosException extends Exception {

    public AccesoDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
