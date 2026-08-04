package com.gestionorganizacional.util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

public final class EstilosUI {
    public static final Color PRIMARIO = new Color(37, 99, 235);
    public static final Color PRIMARIO_OSCURO = new Color(30, 64, 175);
    public static final Color TEXTO = new Color(15, 23, 42);
    public static final Color TEXTO_SUAVE = new Color(100, 116, 139);
    public static final Color BORDE = new Color(203, 213, 225);
    public static final Color ERROR = new Color(185, 28, 28);
    public static final Color FONDO = new Color(248, 250, 252);

    private EstilosUI() {}

    public static void configurarLookAndFeel() {
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));
    }

    public static void configurarCampo(JTextField campo) {
        Border normal = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(8, 10, 8, 10));
        Border foco = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARIO, 2),
                BorderFactory.createEmptyBorder(7, 9, 7, 9));
        campo.setBorder(normal);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setForeground(TEXTO);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        campo.setPreferredSize(new Dimension(320, 40));
        campo.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) { campo.setBorder(foco); }
            @Override public void focusLost(FocusEvent e) { campo.setBorder(normal); }
        });
    }

    public static void configurarBotonPrimario(JButton boton) {
        boton.setBackground(PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(11, 20, 11, 20));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        boton.setPreferredSize(new Dimension(320, 44));
    }

    public static void margenInferior(JComponent componente, int margen) {
        componente.setBorder(BorderFactory.createEmptyBorder(0, 0, margen, 0));
    }
}

