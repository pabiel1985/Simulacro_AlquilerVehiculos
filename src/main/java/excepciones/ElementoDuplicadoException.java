package excepciones;

public class ElementoDuplicadoException extends RuntimeException {
    public ElementoDuplicadoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
