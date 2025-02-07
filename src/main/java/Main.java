import Modelo.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        System.out.println("Tareas");

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        try (Connection connection = DButils.getConnection()) {
            Scanner scanner = new Scanner(System.in);
            DBDDL conexion = new DBDDL();
            Tarea dbTarea = new Tarea();

            if (connection != null && !connection.isClosed()) {
                while (true) {
                    System.out.println("\nGestor de Tareas");
                    System.out.println("1. Crear tarea");
                    System.out.println("2. Listar tareas");
                    System.out.println("3. Borrar tarea");
                    System.out.println("4. Editar tarea");
                    System.out.println("5. Salir");
                    System.out.print("Seleccione una opción: ");

                    int opcion = scanner.nextInt();
                    scanner.nextLine();  // Consumir el salto de línea

                    switch (opcion) {
                        case 1:
                            crearTarea(scanner, dbTarea, conexion);
                            break;
                        case 2:
                            listarTareas(conexion);
                            break;
                        case 3:
                            borrarTarea(scanner, conexion);
                            break;
                        case 4:
                            editarTarea(scanner, conexion);
                            break;
                        case 5:
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

    private static void listarTareas(DBDDL conexion) {
        if (conexion.tareas.isEmpty()) {
            System.out.println("⚠️ No hay tareas registradas.");
        } else {
            for (Tarea tarea : conexion.tareas) {
                System.out.println(tarea);
            }
        }
    }

    private static void crearTarea(Scanner scanner, Tarea dbTarea, DBDDL conexion) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        dbTarea = new Tarea(0, titulo, descripcion, LocalDate.now(), Estado.Pendiente, Prioridad.Baja, LocalDate.now());
        conexion.tareas.add(dbTarea);
        conexion.insertarTarea(dbTarea);
        System.out.println("✅ Tarea creada con éxito.");
    }

    private static void borrarTarea(Scanner scanner, DBDDL conexion) {
        if (conexion.tareas.isEmpty()) {
            System.out.println("⚠️ No hay tareas para borrar.");
            return;
        }

        listarTareas(conexion);
        System.out.print("\nIngrese el ID de la tarea a borrar: ");
        int idBorrar = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (conexion.existeTarea(idBorrar)) {
            conexion.borrarTarea(idBorrar);
            System.out.println("✅ Tarea eliminada correctamente.");
        } else {
            System.out.println("⚠️ No se encontró ninguna tarea con ese ID.");
        }
    }

    private static void editarTarea(Scanner scanner, DBDDL conexion) {
        if (conexion.tareas.isEmpty()) {
            System.out.println("⚠️ No hay tareas para editar.");
            return;
        }

        listarTareas(conexion);
        System.out.print("\nIngrese el ID de la tarea a editar: ");
        int idEditar = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (!conexion.existeTarea(idEditar)) {
            System.out.println("⚠️ No se encontró ninguna tarea con ese ID.");
            return;
        }

        System.out.print("Nuevo título: ");
        String titulo = scanner.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = scanner.nextLine();

        Tarea tareaEditada = new Tarea(idEditar, titulo, descripcion, LocalDate.now(), Estado.Pendiente, Prioridad.Baja, LocalDate.now());
        conexion.editarTarea(tareaEditada);
        System.out.println("✅ Tarea editada con éxito.");
    }
}
