package com.gestionorganizacional.modelo;

import java.time.LocalDate;

public class Empleado implements Entidad {
    private int id;
    private String identidad;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private LocalDate fechaContratacion;
    private double salario;
    private int departamentoId;
    private String departamentoNombre;
    private int cargoId;
    private String cargoNombre;
    private boolean activo;

    public Empleado() {}

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public String getIdentidad() { return identidad; }
    public void setIdentidad(String identidad) { this.identidad = identidad; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(LocalDate fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }
    public String getDepartamentoNombre() { return departamentoNombre; }
    public void setDepartamentoNombre(String departamentoNombre) { this.departamentoNombre = departamentoNombre; }
    public int getCargoId() { return cargoId; }
    public void setCargoId(int cargoId) { this.cargoId = cargoId; }
    public String getCargoNombre() { return cargoNombre; }
    public void setCargoNombre(String cargoNombre) { this.cargoNombre = cargoNombre; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombreCompleto() { return nombres + " " + apellidos; }
    @Override public String toString() { return getNombreCompleto(); }
}

