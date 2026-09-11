package com.corporatetalenthub.modelo;

/**
 * Java 8/11 permitiría que cualquier clase extendiera una Persona abstracta.
 * Al ser sealed, Java 17/21 verifica y limita la jerarquía a los tipos del
 * dominio declarados aquí, evitando extensiones inesperadas en la API.
 */
public abstract sealed class Persona permits Empleado, ConsultorExterno {
    private final int idEmpleado;
    private String nombre;

    protected Persona(int idEmpleado, String nombre) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
