package com.gestionorganizacional.modelo;

public class Pais implements Entidad {
    private int id;
    private String nombre;
    private String codigoIso;
    private boolean activo;

    public Pais() {}

    public Pais(int id, String nombre, String codigoIso, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.codigoIso = codigoIso;
        this.activo = activo;
    }

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigoIso() { return codigoIso; }
    public void setCodigoIso(String codigoIso) { this.codigoIso = codigoIso; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override public String toString() { return nombre; }
}

