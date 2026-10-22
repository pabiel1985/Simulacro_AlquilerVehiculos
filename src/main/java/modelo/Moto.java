package modelo;

import enums.TipoCombustible;

public class Moto extends Vehiculo {

    private int cilindrada;

    public Moto(String patente, String marca, String modelo, int anio, double precioBaseDiario, TipoCombustible combustible, int cilindrada) {
        super(patente, marca, modelo, anio, precioBaseDiario, combustible);
        this.cilindrada = cilindrada;
    }

    @Override
    public int getTipo() {
        return "MOTO";
    }

    @Override
    protected double calcularAdicional(int dias) {
        // TODO (Ej. 2.d): aplicar el adicional de las motos según su cilindrada.
        return 0;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + cilindrada + "cc";
    }
}
