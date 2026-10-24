package excepciones;

public class ElementoNoEncontradoException extends RuntimeException {
    public ElementoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
