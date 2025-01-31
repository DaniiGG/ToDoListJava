import Modelo.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        System.out.println("Tareas");

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        try (Connection connection = DButils.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                Scanner scanner = new Scanner(System.in);
                DBDDL conexion = new DBDDL();
                Tarea dbTarea = new Tarea();

                while (true) {
                    System.out.println("\nGestor de Tareas");
                    System.out.println("1. Crear tarea");
                    System.out.println("2. Listar tareas");
                    System.out.println("3. Salir");
                    System.out.print("Seleccione una opción: ");

                    int opcion = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea

                    switch (opcion) {
                        case 1:
                            crearTarea(scanner, dbTarea, conexion);
                            break;
                        case 2:
                            conexion.listarTareas();
                            break;
                        case 3:
                            System.out.println("Saliendo...");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                }
            }

        }
    }

    private static void crearTarea(Scanner scanner, Tarea dbTarea,  DBDDL conexion) {

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Fecha de inicio (YYYY-MM-DD): ");
        LocalDate fechaInc = LocalDate.parse(scanner.nextLine());

        System.out.print("Fecha de fin (YYYY-MM-DD): ");
        LocalDate fechaFin = LocalDate.parse(scanner.nextLine());

        System.out.print("Estado (Pendiente, Empezada, Acabada): ");
        Estado estado = Estado.valueOf(scanner.nextLine());

        System.out.print("Prioridad (Alta, Media, Baja): ");
        Prioridad prioridad = Prioridad.valueOf(scanner.nextLine());

        dbTarea = new Tarea(0, titulo, descripcion, fechaInc, estado, prioridad, fechaFin);
        conexion.insertarTarea(dbTarea);

        System.out.println("✅ Tarea creada con éxito.");
    }
}