package com.riwi.talent.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionDB {
    private static final String URL = System.getenv().getOrDefault("TALENT_DB_URL", "jdbc:h2:./data/talent;AUTO_SERVER=TRUE");
    private static final String USER = System.getenv().getOrDefault("TALENT_DB_USER", "sa");
    private static final String PASSWORD = System.getenv().getOrDefault("TALENT_DB_PASSWORD", "");

    private ConexionDB() { }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /*
     * Legacy Java 8 cerraba la conexión manualmente en finally, repitiendo
     * null-checks y corriendo el riesgo de ocultar una excepción previa.
     * JDBC moderno usa try-with-resources: Connection, Statement y ResultSet
     * se cierran automáticamente, evitando fugas de conexiones y memoria.
     */
}
