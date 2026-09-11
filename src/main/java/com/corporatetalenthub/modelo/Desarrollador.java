package com.corporatetalenthub.modelo;

public final class Desarrollador extends Empleado {
    private final String lenguajePrincipal;

    public Desarrollador(byte nivelAcceso, short anioIngreso, int idEmpleado, long numeroDocumento,
                         float puntajeTest, double salarioBase, char tipoContrato, boolean esActivo,
                         String nombre, int edad, int idSede, double bonoMensual, String lenguajePrincipal) {
        super(nivelAcceso, anioIngreso, idEmpleado, numeroDocumento, puntajeTest, salarioBase,
                tipoContrato, esActivo, nombre, edad, idSede, bonoMensual);
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguaje() { return lenguajePrincipal; }
    @Override public double calcularBonoAscenso() { return getBonoMensual() * 1.25; }
}
