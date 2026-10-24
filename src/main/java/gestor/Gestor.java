package gestor;

import excepciones.ElementoNoEncontradoException;
import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Gestor<T extends Identificable> {

    protected List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }

    public T buscar(String id) throws ElementoNoEncontradoException{
        for (T elemento : elementos) {
            if (elemento.getId().equalsIgnoreCase(id)) {
                return elemento;
            }
        }
        throw new ElementoNoEncontradoException(id);
    }

    public int cantidad() {
        return elementos.size();
    }
}
