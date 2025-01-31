package Modelo;

public class DBDDL {
    String sentencia= """    
    CREATE TABLE tareas (
            id INT AUTO_INCREMENT PRIMARY KEY,
            titulo VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150),
    fechaInc DATE,
    fechaFin DATE,
    estado ENUM('Pendiente', 'Empezada', 'Acabada') DEFAULT 'Pendiente',
    prioridad ENUM('Alta', 'Media', 'Baja') DEFAULT 'Media');
    """;
}
