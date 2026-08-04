package com.gestionorganizacional.modelo;

public class Cargo implements Entidad {
    private int id;
    private String nombre;
    private String descripcion;
    private double salarioMinimo;
    private double salarioMaximo;
    private boolean activo;

    public Cargo() {}

    public Cargo(int id, String nombre, String descripcion, double salarioMinimo,
                 double salarioMaximo, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.salarioMinimo = salarioMinimo;
        this.salarioMaximo = salarioMaximo;
        this.activo = activo;
    }

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getSalarioMinimo() { return salarioMinimo; }
    public void setSalarioMinimo(double salarioMinimo) { this.salarioMinimo = salarioMinimo; }
    public double getSalarioMaximo() { return salarioMaximo; }
    public void setSalarioMaximo(double salarioMaximo) { this.salarioMaximo = salarioMaximo; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override public String toString() { return nombre; }
}

