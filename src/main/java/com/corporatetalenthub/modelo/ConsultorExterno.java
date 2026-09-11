package com.corporatetalenthub.modelo;

public final class ConsultorExterno extends Persona {
    private final String especialidad;

    public ConsultorExterno(int idEmpleado, String nombre, String especialidad) {
        super(idEmpleado, nombre);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }
}
