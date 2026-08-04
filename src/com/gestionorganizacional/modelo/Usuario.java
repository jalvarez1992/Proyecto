package com.gestionorganizacional.modelo;

import java.time.LocalDateTime;

public class Usuario implements Entidad {
    private int id;
    private String nombreUsuario;
    private String nombreCompleto;
    private String claveHash;
    private String claveSalt;
    private int iteraciones;
    private String rol;
    private boolean activo;
    private LocalDateTime ultimoAcceso;

    public Usuario() {}

    @Override public int getId() { return id; }
    @Override public void setId(int id) { this.id = id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }
    public String getClaveSalt() { return claveSalt; }
    public void setClaveSalt(String claveSalt) { this.claveSalt = claveSalt; }
    public int getIteraciones() { return iteraciones; }
    public void setIteraciones(int iteraciones) { this.iteraciones = iteraciones; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getUltimoAcceso() { return ultimoAcceso; }
    public void setUltimoAcceso(LocalDateTime ultimoAcceso) { this.ultimoAcceso = ultimoAcceso; }

    public void limpiarCredenciales() {
        claveHash = null;
        claveSalt = null;
    }
}

