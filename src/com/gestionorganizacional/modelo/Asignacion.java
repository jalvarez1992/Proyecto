package com.gestionorganizacional.modelo;

import java.time.LocalDate;

public class Asignacion implements Entidad {
    private int id;
    private int empleadoId;
    private String empleadoNombre;
    private int proyectoId;
    private String proyectoNombre;
    private LocalDate fechaAsignacion;
    private int horasAsignadas;
    private String rol;

    public Asignacion() {}

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public int getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(int empleadoId) { this.empleadoId = empleadoId; }
    public String getEmpleadoNombre() { return empleadoNombre; }
    public void setEmpleadoNombre(String empleadoNombre) { this.empleadoNombre = empleadoNombre; }
    public int getProyectoId() { return proyectoId; }
    public void setProyectoId(int proyectoId) { this.proyectoId = proyectoId; }
    public String getProyectoNombre() { return proyectoNombre; }
    public void setProyectoNombre(String proyectoNombre) { this.proyectoNombre = proyectoNombre; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public int getHorasAsignadas() { return horasAsignadas; }
    public void setHorasAsignadas(int horasAsignadas) { this.horasAsignadas = horasAsignadas; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

