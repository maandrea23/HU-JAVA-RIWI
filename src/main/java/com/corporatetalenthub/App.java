package com.corporatetalenthub;

import com.corporatetalenthub.modelo.Empleado;
import com.corporatetalenthub.modelo.DesempenoReport;
import com.corporatetalenthub.modelo.Persona;
import com.corporatetalenthub.modelo.Desarrollador;
import com.corporatetalenthub.modelo.Gerente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/** Punto de entrada del sistema Corporate Talent Hub. */
public class App {
    public static void main(String[] args) {
        var empleados = new ArrayList<Empleado>();
        var empleadosPorId = new HashMap<String, Empleado>();
        // List.of y Map.of crean configuraciones inmutables: son más seguras que
        // un ArrayList/HashMap mutable, aunque no permiten operaciones como add().
        var tecnologias = List.of("Java", "Spring", "SQL");
        var sedes = Map.of("BOG", "Bogotá", "MED", "Medellín");
        var scanner = new Scanner(System.in);
        var continuar = true;
        System.out.println("""
                =====================================
                     CORPORATE TALENT HUB
                   Gestión del talento humano
                =====================================
                """);
        do {
            System.out.println("\n1. Registrar empleado\n2. Ver reporte\n3. Buscar por ID\n4. Eliminar por ID\n5. Filtrar puntaje\n6. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                var opcion = scanner.nextInt(); scanner.nextLine();
                // Java 8 requiere break; olvidarlo causa fall-through.
                // Java 17/21 usa ->, que evita ese riesgo y resulta más breve.
                switch (opcion) {
                    case 1:
                        var empleado = registrarEmpleado(scanner);
                        empleados.add(empleado);
                        empleadosPorId.put(String.valueOf(empleado.getIdEmpleado()), empleado);
                        System.out.println("Empleado registrado. Tecnologías: " + tecnologias + " | Sedes: " + sedes.values());
                        break;
                    case 2: mostrarReporte(empleados, scanner); break;
                    case 3: buscarEmpleado(empleadosPorId, scanner); break;
                    case 4: eliminarEmpleado(empleados, empleadosPorId, scanner); break;
                    case 5: filtrarPorPuntaje(empleados, empleadosPorId, scanner); break;
                    case 6: continuar = false; System.out.println("Sistema finalizado."); break;
                    default: System.out.println("Opción fuera del rango 1-6."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida: debe ingresar un número del menú."); scanner.nextLine();
            }
        } while (continuar);
        scanner.close();
    }

    private static void buscarEmpleado(Map<String, Empleado> empleadosPorId, Scanner s) {
        System.out.print("ID a buscar: "); var id = s.nextLine().trim();
        var empleado = empleadosPorId.get(id);
        System.out.println(empleado == null ? "Empleado no encontrado." : empleado);
    }

    private static void eliminarEmpleado(List<Empleado> empleados, Map<String, Empleado> mapa, Scanner s) {
        System.out.print("ID a eliminar: "); var id = s.nextLine().trim();
        var eliminado = mapa.remove(id);
        if (eliminado == null) { System.out.println("Empleado no encontrado."); return; }
        empleados.remove(eliminado);
        System.out.println("Empleado eliminado.");
    }

    private static void filtrarPorPuntaje(List<Empleado> empleados, Map<String, Empleado> mapa, Scanner s) {
        var minimo = (float) leerDouble(s, "Puntaje mínimo (0-100): ", 0, 100);
        var antes = empleados.size();
        empleados.removeIf(empleado -> empleado.getPuntajeTest() < minimo);
        mapa.entrySet().removeIf(entrada -> !empleados.contains(entrada.getValue()));
        System.out.println("Empleados eliminados por puntaje: " + (antes - empleados.size()));
    }

    private static Empleado registrarEmpleado(Scanner s) {
        System.out.println("\n--- Registro de empleado ---");
        var nivel = (byte) leerLong(s, "Nivel de acceso (0-10): ", 0, 10);
        var anio = (short) leerLong(s, "Año de ingreso (2000-2100): ", 2000, 2100);
        var id = (int) leerLong(s, "ID del empleado (positivo): ", 1, Integer.MAX_VALUE);
        var documento = leerLong(s, "Número de documento (positivo): ", 1, Long.MAX_VALUE);
        var puntaje = (float) leerDouble(s, "Puntaje de test (0-100): ", 0, 100);
        var salario = leerDouble(s, "Salario base (>= 0): ", 0, Double.MAX_VALUE);
        char contrato; do { System.out.print("Tipo de contrato (I/T): "); var v = s.nextLine().trim().toUpperCase(); contrato = v.length() == 1 ? v.charAt(0) : '\0'; } while (contrato != 'I' && contrato != 'T');
        boolean activo;
        while (true) {
            System.out.print("¿Está activo? (true/false): ");
            var v = s.nextLine().trim();
            if (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("false")) { activo = Boolean.parseBoolean(v); break; }
            System.out.println("Use true o false.");
        }
        System.out.print("Nombre: "); var nombre = s.nextLine().trim();
        var edad = (int) leerLong(s, "Edad (18-70): ", 18, 70);
        var sede = (int) leerLong(s, "ID de sede (1-999): ", 1, 999);
        var bono = leerDouble(s, "Bono mensual (>= 0): ", 0, Double.MAX_VALUE);
        return new Empleado(nivel, anio, id, documento, puntaje, salario, contrato, activo, nombre, edad, sede, bono);
    }

    private static void mostrarReporte(List<Empleado> empleados, Scanner s) {
        if (empleados.isEmpty()) { System.out.println("No hay empleados registrados."); return; }
        // Legacy Java 8/11: empleados.get(0) y empleados.get(empleados.size() - 1).
        // Java 21: getFirst/getLast previenen errores de índice y reversed mejora la lectura.
        System.out.println("Primero: " + empleados.getFirst().getNombre() + " | Último: " + empleados.getLast().getNombre());
        System.out.println("Orden inverso: " + empleados.reversed().stream().map(Empleado::getNombre).toList());
        var desempeno = new double[empleados.size()][3];
        for (var fila = 0; fila < empleados.size(); fila++) {
            for (var trimestre = 0; trimestre < 3; trimestre++) desempeno[fila][trimestre] = leerDouble(s, "Calificación T" + (trimestre + 1) + " (0-100): ", 0, 100);
            var promedio = (desempeno[fila][0] + desempeno[fila][1] + desempeno[fila][2]) / 3;
            var simplificado = (int) promedio; // Casting: descarta la parte decimal.
            var promocion = promedio >= 80 ? "Promoción aprobada" : "Promoción pendiente";
            System.out.printf("%s | Promedio %.2f | Puntaje %d | %s | Categoría %s%n", empleados.get(fila).getNombre(), promedio, simplificado, promocion, obtenerCategoriaSalarial(empleados.get(fila).calcularSalarioFinal()));
            var reporte = new DesempenoReport(empleados.get(fila).getIdEmpleado(), promedio, promocion);
            System.out.println("Reporte inmutable: " + reporte + " | Perfil: " + describirPerfil(empleados.get(fila)));
        }
        var promedioSalarios = empleados.stream().mapToDouble(Empleado::calcularSalarioFinal).average().orElse(0);
        System.out.printf("Total empleados: %d | Promedio de salarios: %.2f%n", empleados.size(), promedioSalarios);
    }

    /**
     * Legacy Java 8/11 requería instanceof y casting manual:
     * ((Desarrollador) persona).getLenguaje(). Java 17/21 enlaza el tipo y
     * elimina ese casting repetitivo mediante Pattern Matching for instanceof.
     */
    public static String describirPerfil(Persona persona) {
        if (persona instanceof Desarrollador des) {
            return "Desarrollador - " + des.getLenguaje();
        }
        if (persona instanceof Gerente gerente) {
            return "Gerente - presupuesto " + gerente.getPresupuestoMensual();
        }
        if (persona instanceof Empleado) {
            return "Empleado general";
        }
        return "Consultor externo";
    }

    /** Switch Expression Java 17/21 con sintaxis de flecha. */
    public static String obtenerCategoriaSalarial(double salario) {
        var nivel = salario >= 10_000_000 ? 2 : salario >= 5_000_000 ? 1 : 0;
        return switch (nivel) {
            case 2 -> "Ejecutiva";
            case 1 -> "Senior";
            default -> "Inicial";
        };
    }

    private static long leerLong(Scanner s, String p, long min, long max) { while (true) try { System.out.print(p); var v = s.nextLong(); s.nextLine(); if (v >= min && v <= max) return v; System.out.println("Valor fuera de rango."); } catch (InputMismatchException e) { System.out.println("Debe ingresar un número entero."); s.nextLine(); } }
    private static double leerDouble(Scanner s, String p, double min, double max) { while (true) try { System.out.print(p); var v = s.nextDouble(); s.nextLine(); if (v >= min && v <= max) return v; System.out.println("Valor fuera de rango."); } catch (InputMismatchException e) { System.out.println("Debe ingresar un número decimal."); s.nextLine(); } }
}
