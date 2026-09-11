/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corporatetalenthub.modelo;

/**
 *
 * @author cohorte5
 */


/**
 * Modelo tradicional compatible con la sintaxis de Java 8.
 *
 * Esta clase es más verbosa que un Record porque declara campos, constructor,
 * getters, setter y métodos explícitamente. Esa verbosidad es útil cuando el
 * objeto necesita estado mutable, como bonoMensual o nombre.
 */
public non-sealed class Empleado extends Persona implements Promocionable {

    // Los 8 tipos primitivos requeridos:
    private byte nivelAcceso;
    private short anioIngreso;
    private long numeroDocumento;
    private float puntajeTest;
    private double salarioBase;
    private char tipoContrato;
    private boolean esActivo;

    // String no es primitivo: es una clase y esta variable guarda una referencia.

    // Datos adicionales necesarios para las reglas de negocio.
    private int edad;
    private int idSede;
    private double bonoMensual;

    public Empleado(
            byte nivelAcceso,
            short anioIngreso,
            int idEmpleado,
            long numeroDocumento,
            float puntajeTest,
            double salarioBase,
            char tipoContrato,
            boolean esActivo,
            String nombre,
            int edad,
            int idSede,
            double bonoMensual) {
        super(idEmpleado, nombre);
        this.nivelAcceso = nivelAcceso;
        this.anioIngreso = anioIngreso;
        this.numeroDocumento = numeroDocumento;
        this.puntajeTest = puntajeTest;
        this.salarioBase = salarioBase;
        this.tipoContrato = tipoContrato;
        this.esActivo = esActivo;
        this.edad = edad;
        this.idSede = idSede;
        this.bonoMensual = bonoMensual;
    }

    public double calcularSalarioFinal() {
        // Orden: paréntesis internos -> multiplicaciones -> suma -> resta.
        // 1. bonoMensual * 1.10
        // 2. salarioBase + resultado anterior
        // 3. salarioBase * 0.05
        // 4. resta de ambos resultados
        return (salarioBase + (bonoMensual * 1.10))
                - (salarioBase * 0.05);
    }

    public boolean tieneBonoExtra() {
        // % se evalúa antes que ==. Un residuo cero indica que el ID es par.
        return getIdEmpleado() % 2 == 0;
    }

    public boolean validarElegibilidad() {
        // Precedencia: primero !, después && y finalmente ||.
        // Los paréntesis hacen explícitos los dos grupos de condiciones.
        return (puntajeTest > 85 && edad < 30)
                || (idSede == 1 && !esActivo);
    }

    public void actualizarBonoMensual(double incremento) {
        // Equivale a: bonoMensual = bonoMensual + incremento;
        bonoMensual += incremento;
    }

    public float getPuntajeTest() {
        return puntajeTest;
    }

    public double getBonoMensual() {
        return bonoMensual;
    }

    @Override
    public double calcularBonoAscenso() {
        return bonoMensual * 1.10;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nivelAcceso=" + nivelAcceso +
                ", anioIngreso=" + anioIngreso +
                ", idEmpleado=" + getIdEmpleado() +
                ", numeroDocumento=" + numeroDocumento +
                ", puntajeTest=" + puntajeTest +
                ", salarioBase=" + salarioBase +
                ", tipoContrato=" + tipoContrato +
                ", esActivo=" + esActivo +
                ", nombre='" + getNombre() + '\'' +
                ", edad=" + edad +
                ", idSede=" + idSede +
                ", bonoMensual=" + bonoMensual +
                '}';
    }
}
