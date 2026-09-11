package com.riwi.talent.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    @Override public void crearTabla() throws SQLException {
        var sql = "CREATE TABLE IF NOT EXISTS empleados (id INT PRIMARY KEY, nombre VARCHAR(120) NOT NULL, salario DECIMAL(15,2) NOT NULL, perfil VARCHAR(80) NOT NULL)";
        try (var connection = ConexionDB.obtenerConexion(); var statement = connection.prepareStatement(sql)) { statement.executeUpdate(); }
    }

    @Override public void insertar(EmpleadoRecord empleado) throws SQLException {
        var sql = "INSERT INTO empleados (id, nombre, salario, perfil) VALUES (?, ?, ?, ?)";
        try (var connection = ConexionDB.obtenerConexion(); var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, empleado.id()); statement.setString(2, empleado.nombre()); statement.setDouble(3, empleado.salario()); statement.setString(4, empleado.perfil()); statement.executeUpdate();
        }
    }

    @Override public List<EmpleadoRecord> listar() throws SQLException {
        var empleados = new ArrayList<EmpleadoRecord>();
        var sql = "SELECT id, nombre, salario, perfil FROM empleados ORDER BY id";
        try (var connection = ConexionDB.obtenerConexion(); var statement = connection.prepareStatement(sql); var result = statement.executeQuery()) {
            while (result.next()) empleados.add(new EmpleadoRecord(result.getInt("id"), result.getString("nombre"), result.getDouble("salario"), result.getString("perfil")));
        }
        return empleados;
    }

    @Override public boolean actualizar(EmpleadoRecord empleado) throws SQLException {
        var sql = "UPDATE empleados SET nombre = ?, salario = ?, perfil = ? WHERE id = ?";
        try (var connection = ConexionDB.obtenerConexion(); var statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.nombre()); statement.setDouble(2, empleado.salario()); statement.setString(3, empleado.perfil()); statement.setInt(4, empleado.id()); return statement.executeUpdate() > 0;
        }
    }

    @Override public boolean eliminar(int id) throws SQLException {
        var sql = "DELETE FROM empleados WHERE id = ?";
        try (var connection = ConexionDB.obtenerConexion(); var statement = connection.prepareStatement(sql)) { statement.setInt(1, id); return statement.executeUpdate() > 0; }
    }
}
