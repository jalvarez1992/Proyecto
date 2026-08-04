package com.gestionorganizacional.util;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class VistaUtil {
    private VistaUtil() {}

    public static DefaultTableModel modeloTabla(String... columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static double decimal(String texto, String campo) throws ValidacionException {
        try {
            return Double.parseDouble(Validaciones.requerido(texto, campo).replace(",", "."));
        } catch (NumberFormatException ex) {
            throw new ValidacionException("El campo " + campo + " debe ser numérico.");
        }
    }

    public static LocalDate fecha(String texto, String campo) throws ValidacionException {
        try {
            return LocalDate.parse(Validaciones.requerido(texto, campo));
        } catch (DateTimeParseException ex) {
            throw new ValidacionException("El campo " + campo + " debe usar el formato AAAA-MM-DD.");
        }
    }

    public static LocalDate fechaOpcional(String texto, String campo) throws ValidacionException {
        return texto == null || texto.isBlank() ? null : fecha(texto, campo);
    }

    public static void exito(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component padre, Exception ex) {
        JOptionPane.showMessageDialog(padre, ex.getMessage(), "Atención", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirmarEliminar(Component padre) {
        return JOptionPane.showConfirmDialog(padre,
                "¿Desea eliminar el registro seleccionado?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }
}

