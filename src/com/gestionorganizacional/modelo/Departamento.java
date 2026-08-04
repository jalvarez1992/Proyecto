package com.gestionorganizacional.modelo;

public class Departamento implements Entidad {
    private int id;
    private String nombre;
    private int paisId;
    private String paisNombre;
    private boolean activo;

    public Departamento() {}

    public Departamento(int id, String nombre, int paisId, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.paisId = paisId;
        this.activo = activo;
    }

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getPaisId() { return paisId; }
    public void setPaisId(int paisId) { this.paisId = paisId; }
    public String getPaisNombre() { return paisNombre; }
    public void setPaisNombre(String paisNombre) { this.paisNombre = paisNombre; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override public String toString() { return nombre; }
}

