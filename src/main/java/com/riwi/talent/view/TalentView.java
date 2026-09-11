package com.riwi.talent.view;

import com.riwi.talent.controller.TalentController;
import com.riwi.talent.model.EmpleadoRecord;
import java.sql.SQLException;
import java.util.Scanner;

/** Vista MVC: es la única capa responsable de leer desde teclado. */
public class TalentView {
    private final TalentController controller;
    private final Scanner scanner;

    public TalentView(TalentController controller, Scanner scanner) { this.controller = controller; this.scanner = scanner; }

    public void iniciar() {
        var continuar = true;
        while (continuar) {
            System.out.println("\n=== PERSISTENCIA CORPORATE TALENT HUB ===\n1. Insertar\n2. Listar reporte\n3. Actualizar\n4. Eliminar\n5. Salir");
            try {
                var opcion = scanner.nextInt(); scanner.nextLine();
                switch (opcion) {
                    case 1 -> insertar();
                    case 2 -> listar();
                    case 3 -> actualizar();
                    case 4 -> eliminar();
                    case 5 -> continuar = false;
                    default -> System.out.println("Opción inválida.");
                }
            } catch (SQLException e) { System.out.println("Error de persistencia: " + e.getMessage()); }
        }
    }

    private void insertar() throws SQLException { controller.crear(leerEmpleado()); System.out.println("Empleado persistido."); }
    private void actualizar() throws SQLException { if (controller.actualizar(leerEmpleado())) System.out.println("Empleado actualizado."); else System.out.println("ID no encontrado."); }
    private void eliminar() throws SQLException { System.out.print("ID: "); System.out.println(controller.eliminar(scanner.nextInt()) ? "Empleado eliminado." : "ID no encontrado."); scanner.nextLine(); }
    private void listar() throws SQLException {
        var empleados = controller.listar();
        var reporte = new StringBuilder("""
                
                -------- REPORTE CONSOLIDADO --------
                """);
        for (var empleado : empleados) reporte.append("ID: ").append(empleado.id()).append(" | ").append(empleado.nombre()).append(" | $ ").append(empleado.salario()).append(" | ").append(empleado.perfil()).append('\n');
        reporte.append("Total: ").append(empleados.size()).append('\n');
        System.out.println(reporte);
    }
    private EmpleadoRecord leerEmpleado() {
        System.out.print("ID: "); var id = scanner.nextInt(); scanner.nextLine();
        System.out.print("Nombre: "); var nombre = scanner.nextLine();
        System.out.print("Salario: "); var salario = scanner.nextDouble(); scanner.nextLine();
        System.out.print("Perfil: "); var perfil = scanner.nextLine();
        return new EmpleadoRecord(id, nombre, salario, perfil);
    }
}
