package com.corporatetalenthub.modelo;

public interface Promocionable {
    double calcularBonoAscenso();

    /** Java 8 permitió evolucionar interfaces sin romper implementaciones existentes. */
    default void registrarLogPromocion() {
        System.out.println("Log: operación de promoción registrada.");
    }
}
