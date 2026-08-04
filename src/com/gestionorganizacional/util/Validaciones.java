package com.gestionorganizacional.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public final class Validaciones {
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private Validaciones() {}

    public static String requerido(String valor, String campo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }

    public static String email(String valor) throws ValidacionException {
        String limpio = requerido(valor, "correo electrónico").toLowerCase();
        if (!EMAIL.matcher(limpio).matches()) {
            throw new ValidacionException("Ingrese un correo electrónico válido.");
        }
        return limpio;
    }

    public static void positivoOCero(double valor, String campo) throws ValidacionException {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new ValidacionException(campo + " debe ser un número mayor o igual a cero.");
        }
    }

    public static void idSeleccionado(int id, String campo) throws ValidacionException {
        if (id <= 0) {
            throw new ValidacionException("Seleccione " + campo + ".");
        }
    }

    public static void rangoFechas(LocalDate inicio, LocalDate fin) throws ValidacionException {
        if (inicio == null) {
            throw new ValidacionException("La fecha de inicio es obligatoria.");
        }
        if (fin != null && fin.isBefore(inicio)) {
            throw new ValidacionException("La fecha final no puede ser anterior a la fecha inicial.");
        }
    }
}

