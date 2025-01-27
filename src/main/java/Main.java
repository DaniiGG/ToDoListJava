import Modelo.DButils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        System.out.println("Tareas");

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        try (Connection connection = DButils.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión establecida con éxito.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.err.println("Error al intentar conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }


        



    }
}