package com.corporatetalenthub.modelo;

/**
 * Reporte inmutable. Un POJO Java 8/11 requeriría constructor, getters y
 * toString escritos manualmente; el record genera ese contrato automáticamente.
 */
public record DesempenoReport(int idEmpleado, double promedio, String feedback) { }
