package modelo;

import enums.TipoCombustible;

public class Camioneta extends Vehiculo {

    private boolean traccion4x4;

    public Camioneta(String patente, String marca, String modelo, int anio, double precioBaseDiario, TipoCombustible combustible, boolean traccion4x4) {
        super(patente, marca, modelo, precioBaseDiario, anio, combustible);
        this.traccion4x4 = traccion4x4;
    }

    @Override
    public String getTipo() {
        return "CAMIONETA";
    }

    @Override
    protected double calcularAdicional(int dias) {
        double adicional = 5000;
        if (traccion4x4) {
            adicional += 300 * dias;
        }
        return adicional;
    }

    public boolean isTraccion4x4() {
        return traccion4x4;
    }

    public void setTraccion4x4(boolean traccion4x4) {
        this.traccion4x4 = traccion4x4;
    }

    // TODO (Ej. 2.e): al listar la flota no se sabe si la camioneta es 4x4.
}
