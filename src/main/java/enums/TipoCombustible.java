package enums;

// TODO (Ej. 2.c): el cálculo de costos necesita consultar el factor de cada combustible.
public enum TipoCombustible {
    NAFTA(1.00),
    DIESEL(0.95),
    ELECTRICO(1.25),
    HIBRIDO(1.10);
  /// se coloca punto y coma para finaliazar listqa de enum
    private final double factor;

    TipoCombustible(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
