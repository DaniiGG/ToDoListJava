package Modelo;

import java.time.LocalDate;

public class Tarea {
    int id;
    String titulo;
    String descripcion;
    LocalDate fechaInc;
    LocalDate fechaFin;
    Estado estado;
    Prioridad prioridad;

    public Tarea(int id, String titulo, String descripcion, LocalDate fechaInc, Estado estado, Prioridad prioridad, LocalDate fechaFin) {
        this.id=id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInc = fechaInc;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaFin = fechaFin;
    }

    public Tarea() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDate getFechaInc() {
        return fechaInc;
    }

    public void setFechaInc(LocalDate fechaInc) {
        this.fechaInc = fechaInc;
    }

}
