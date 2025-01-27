package Modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DButils {

    public static Connection getConnection() throws SQLException {
        // Leer las variables del archivo .env
        String dbUrl = loadEnvFile().get("DB_URL");
        String dbUser = loadEnvFile().get("DB_USER");
        String dbPassword = loadEnvFile().get("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de la base de datos: " + e.getMessage());
            throw new SQLException("Driver no encontrado", e);
        }
        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalArgumentException("Las variables de entorno no están configuradas correctamente.");
        }else {
        // Establecer conexión
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private static Map<String, String> loadEnvFile() {
        Map<String, String> env = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // Ignorar líneas vacías o comentarios
                }
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    env.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch(IOException e){
            System.out.println("No se pudo cargar el archivo .env: " + e.getMessage());
        }
        return env;
    }
}
