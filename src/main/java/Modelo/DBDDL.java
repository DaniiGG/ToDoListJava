package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBDDL {
    private Connection connection;

    public DBDDL() {
        try {
            this.connection = DButils.getConnection(); // Usar la conexión de DButils
            crearTabla();
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    private void crearTabla() {
        String sentencia = """    
        CREATE TABLE IF NOT EXISTS tareas (
                id SERIAL PRIMARY KEY,
                titulo VARCHAR(30) NOT NULL,
                descripcion VARCHAR(150),
                fechaInc DATE,
                fechaFin DATE,
                estado VARCHAR(10) CHECK (estado IN ('Pendiente', 'Empezada', 'Acabada')) DEFAULT 'Pendiente',
                prioridad VARCHAR(5) CHECK (prioridad IN ('Alta', 'Media', 'Baja')) DEFAULT 'Media'
        );
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sentencia)) {
            stmt.execute();
            System.out.println("✅ Tabla `tareas` creada o verificada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al crear la tabla `tareas`: " + e.getMessage());
        }
    }

    public void insertarTarea(Tarea tarea) {
        String sql = "INSERT INTO tareas (titulo, descripcion, fechaInc, fechaFin, estado, prioridad) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setDate(3, java.sql.Date.valueOf(tarea.getFechaInc()));
            stmt.setDate(4, java.sql.Date.valueOf(tarea.getFechaFin()));
            stmt.setString(5, tarea.getEstado().name());
            stmt.setString(6, tarea.getPrioridad().name());

            stmt.executeUpdate();
            System.out.println("✅ Tarea insertada correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar tarea: " + e.getMessage());
        }
    }

    public List<Tarea> listarTareas() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarea tarea = new Tarea(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descripcion"),
                        rs.getDate("fechaInc").toLocalDate(),
                        Estado.valueOf(rs.getString("estado")),
                        Prioridad.valueOf(rs.getString("prioridad")),
                        rs.getDate("fechaFin").toLocalDate()
                );
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar tareas: " + e.getMessage());
        }

        return tareas;
    }
}
