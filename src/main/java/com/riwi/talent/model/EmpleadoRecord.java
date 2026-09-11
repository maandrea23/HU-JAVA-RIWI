package com.riwi.talent.model;

/** Record inmutable para transportar resultados de consultas JDBC complejas. */
public record EmpleadoRecord(int id, String nombre, double salario, String perfil) { }
