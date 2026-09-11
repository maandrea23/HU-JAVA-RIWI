package com.riwi.talent.controller;

import com.riwi.talent.model.EmpleadoDAO;
import com.riwi.talent.model.EmpleadoRecord;
import java.sql.SQLException;
import java.util.List;

/** Coordina la vista y el modelo; no conoce Scanner ni detalles de consola. */
public class TalentController {
    private final EmpleadoDAO dao;

    public TalentController(EmpleadoDAO dao) throws SQLException { this.dao = dao; dao.crearTabla(); }
    public void crear(EmpleadoRecord empleado) throws SQLException { dao.insertar(empleado); }
    public List<EmpleadoRecord> listar() throws SQLException { return dao.listar(); }
    public boolean actualizar(EmpleadoRecord empleado) throws SQLException { return dao.actualizar(empleado); }
    public boolean eliminar(int id) throws SQLException { return dao.eliminar(id); }
}
