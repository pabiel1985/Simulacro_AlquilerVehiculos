package modelo;

import enums.TipoCombustible;

public class Auto extends Vehiculo {

    private int cantidadPuertas;

    public Auto(String patente, String marca, String modelo, int anio, double precioBaseDiario, TipoCombustible combustible, int cantidadPuertas) {
        super(patente, marca, modelo, anio, precioBaseDiario, combustible);
        this.cantidadPuertas = cantidadPuertas;
    }

    @Override
    public String getTipo() {
        return "AUTO";
    }

    @Override
    protected double calcularAdicional(int dias) {
        return getPrecioBaseDiario() * 0.05 * dias;
        /// se comoca get faltante para comunicar con padre
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public void setCantidadPuertas(int cantidadPuertas) {
        this.cantidadPuertas = cantidadPuertas;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + cantidadPuertas + " puertas";
    }
}
