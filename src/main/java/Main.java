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
                    System.out.println("3. Borrar tareas");
                    System.out.println("4. Salir");
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
                            int idBorrar;

                            while (true) {

                                if (conexion.tareas.isEmpty()) {
                                    System.out.println("⚠️ No hay tareas para borrar.");
                                    break;
                                }
                                listarTareas(conexion);
                                System.out.print("\nIngrese el ID de la tarea a borrar: ");
                                idBorrar = scanner.nextInt();
                                int finalIdBorrar = idBorrar;
                                conexion.tareas.removeIf(tarea -> tarea.getId() == finalIdBorrar);
                                conexion.borrarTarea(idBorrar);
                                System.out.println("\n📋 Lista actualizada de tareas:");
                                listarTareas(conexion);
                                System.out.print("\n¿Desea borrar otra tarea? (s/n): ");
                                String respuesta = scanner.next().toLowerCase();
                                if (!respuesta.equals("s")) {
                                    break;
                                }
                            }
                        case 4:
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
    private static void listarTareas(DBDDL conexion){
        for(Tarea i: conexion.tareas){
            System.out.println(i);
        }
    }
    private static void crearTarea(Scanner scanner, Tarea dbTarea, DBDDL conexion) {

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        /*
        System.out.print("Estado (Pendiente, Empezada, Acabada): ");
        Estado estado = Estado.valueOf(scanner.nextLine());

        System.out.print("Prioridad (Alta, Media, Baja): ");
        Prioridad prioridad = Prioridad.valueOf(scanner.nextLine());
        */
        dbTarea = new Tarea(0, titulo, descripcion, LocalDate.now(), Estado.Pendiente, Prioridad.Baja, LocalDate.now());
        conexion.tareas.add(dbTarea);
        conexion.insertarTarea(dbTarea);
        System.out.println("✅ Tarea creada con éxito.");
    }
}