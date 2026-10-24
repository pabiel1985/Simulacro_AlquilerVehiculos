package modelo;

import enums.EstadoVehiculo;
import enums.TipoCombustible;
import excepciones.DatoInvalidoException;
import excepciones.VehiculoNoDisponibleException;
import interfaces.Alquilable;
import interfaces.Identificable;

import java.util.Objects;

// TODO (Ej. 2.a): los vehículos dicen ser Identificable, pero ninguno cumple ese contrato todavía.
public abstract class Vehiculo implements Alquilable, Identificable {

    private String patente;
    private String marca;
    private String modelo;
    private int anio;
    private double precioBaseDiario;
    private TipoCombustible combustible;
    private EstadoVehiculo estado;

    public Vehiculo(String patente, String marca, String modelo, int anio, double precioBaseDiario, TipoCombustible combustible) {
        setPatente(patente);
        setMarca(marca);
        setModelo(modelo);
        setAnio(anio);
        setPrecioBaseDiario(precioBaseDiario);
        setCombustible(combustible);
        this.estado = EstadoVehiculo.DISPONIBLE;
    }

    public abstract String getTipo();

    protected abstract double calcularAdicional(int dias);

    @Override
    public double calcularCostoAlquiler(int dias) {
        // TODO (Ej. 3.b): no tiene sentido calcular un alquiler por cero días o menos.
        if (dias <= 0){
            throw new DatoInvalidoException("ERROR: La cantidad de días debe ser mayor a cero (recibido:" + dias + ")");
        }
        double costoBase = precioBaseDiario * dias * combustible.getFactor();
        if (dias > 7) {
            costoBase = costoBase * 0.90;
        }
        return costoBase + calcularAdicional(dias);
    }

    public void alquilar() {
        // TODO (Ej. 3.b): solo se puede alquilar un vehículo que esté disponible.
        if (estado != EstadoVehiculo.DISPONIBLE) {
            throw new VehiculoNoDisponibleException("ERROR: El vehículo " + getPatente() +
                    " no está disponible (estado actual:" + estado + ")");
        }
        estado = EstadoVehiculo.ALQUILADO;
    }

    public void devolver() {
        if (estado == EstadoVehiculo.ALQUILADO) {
            estado = EstadoVehiculo.DISPONIBLE;
        }
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        // TODO (Ej. 3.b): revisá las reglas de "Datos que no aceptamos".
        if (patente == null || patente.isBlank()) {
            throw new DatoInvalidoException("ERROR: La patente no puede estar vacía");
        }
        this.patente = patente.toUpperCase();
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecioBaseDiario() {
        return precioBaseDiario;
    }

    public void setPrecioBaseDiario(double precioBaseDiario) {
        // TODO (Ej. 3.b): revisá las reglas de "Datos que no aceptamos".
        if (precioBaseDiario <= 0) {
            throw new DatoInvalidoException
                    ("ERROR: El precio base diario debe ser mayor a cero (recibido:" + precioBaseDiario +")");
        }
        this.precioBaseDiario = precioBaseDiario;
    }

    public TipoCombustible getCombustible() {
        return combustible;
    }

    public void setCombustible(TipoCombustible combustible) {
        this.combustible = combustible;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s %s (%d) - $%.2f/día - %s - %s",
                getTipo(), patente, marca, modelo, anio, precioBaseDiario, combustible, estado);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vehiculo vehiculo)) return false;
        return Objects.equals(patente, vehiculo.patente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patente);
    }

    @Override
    public String getId() {
        return getPatente() ;
    }
}
