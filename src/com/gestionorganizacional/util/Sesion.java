package com.gestionorganizacional.util;

import com.gestionorganizacional.modelo.Usuario;

public final class Sesion {
    private static Usuario usuarioActual;

    private Sesion() {}

    public static synchronized void iniciar(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static synchronized Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static synchronized boolean estaIniciada() {
        return usuarioActual != null;
    }

    public static synchronized void cerrar() {
        usuarioActual = null;
    }
}

