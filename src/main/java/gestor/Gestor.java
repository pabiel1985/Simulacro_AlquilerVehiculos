package gestor;

import interfaces.Identificable;

import java.util.ArrayList;
import java.util.List;

public class Gestor<T extends Identificable> {

    protected List<T> elementos;

    public Gestor() {
        this.elementos = new ArrayList<>();
    }
}
