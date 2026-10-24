package modelo;

import enums.TipoCombustible;

public class Moto extends Vehiculo {

    private int cilindrada;

    public Moto(String patente, String marca, String modelo, int anio, double precioBaseDiario, TipoCombustible combustible, int cilindrada) {
        super(patente, marca, modelo, anio, precioBaseDiario, combustible);
        this.cilindrada = cilindrada;
    }

    @Override
    public String getTipo() {
        return "MOTO"; ///  el modulo era int y tenia que ser String
    }

    @Override
    protected double calcularAdicional(int dias) {
        // TODO (Ej. 2.d): aplicar el adicional de las motos según su cilindrada.
        if (cilindrada > 500) {
            return dias * 1000;
        }
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
