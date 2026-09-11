package com.riwi.talent.model;

import java.sql.SQLException;
import java.util.List;

public interface EmpleadoDAO {
    void crearTabla() throws SQLException;
    void insertar(EmpleadoRecord empleado) throws SQLException;
    List<EmpleadoRecord> listar() throws SQLException;
    boolean actualizar(EmpleadoRecord empleado) throws SQLException;
    boolean eliminar(int id) throws SQLException;
}
