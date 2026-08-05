package com.gestionorganizacional.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class EstilosUI {
    public static final Color PRIMARIO = new Color(37, 99, 235);
    public static final Color PRIMARIO_OSCURO = new Color(30, 64, 175);
    public static final Color PRIMARIO_SUAVE = new Color(239, 246, 255);
    public static final Color SECUNDARIO = new Color(14, 165, 164);
    public static final Color EXITO = new Color(22, 163, 74);
    public static final Color ADVERTENCIA = new Color(217, 119, 6);
    public static final Color ERROR = new Color(220, 38, 38);
    public static final Color TEXTO = new Color(15, 23, 42);
    public static final Color TEXTO_SUAVE = new Color(100, 116, 139);
    public static final Color BORDE = new Color(226, 232, 240);
    public static final Color FONDO = new Color(241, 245, 249);
    public static final Color TARJETA = Color.WHITE;
    public static final Color SIDEBAR = new Color(15, 23, 42);
    public static final Color SIDEBAR_HOVER = new Color(30, 41, 59);
    public static final Color FILA_ALTERNA = new Color(248, 250, 252);
    public static final Font FUENTE = new Font("Segoe UI", Font.PLAIN, 13);

    public enum VarianteBoton { PRIMARIO, SECUNDARIO, PELIGRO, FANTASMA, NAVEGACION }

    private EstilosUI() {}

    public static void configurarLookAndFeel() {
        UIManager.put("defaultFont", FUENTE);
        UIManager.put("Label.font", FUENTE);
        UIManager.put("Button.font", FUENTE.deriveFont(Font.BOLD));
        UIManager.put("TextField.font", FUENTE);
        UIManager.put("ComboBox.font", FUENTE);
        UIManager.put("Table.font", FUENTE);
        UIManager.put("TableHeader.font", FUENTE.deriveFont(Font.BOLD));
        UIManager.put("OptionPane.messageFont", FUENTE);
        UIManager.put("OptionPane.buttonFont", FUENTE.deriveFont(Font.BOLD));
        UIManager.put("control", TARJETA);
        UIManager.put("nimbusBase", PRIMARIO);
        UIManager.put("nimbusFocus", PRIMARIO);
        UIManager.put("nimbusSelectionBackground", PRIMARIO);
        UIManager.put("Table.selectionBackground", new Color(219, 234, 254));
        UIManager.put("Table.selectionForeground", TEXTO);
        UIManager.put("InternalFrame.activeTitleBackground", PRIMARIO);
    }

    public static void configurarPanelGradiente(JPanel panel) {
        panel.setOpaque(false);
        panel.setUI(new BasicPanelUI() {
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setPaint(new LinearGradientPaint(0, 0, c.getWidth(), c.getHeight(),
                        new float[]{0f, 1f}, new Color[]{PRIMARIO_OSCURO, SECUNDARIO}));
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    public static void configurarCampo(JTextField campo) {
        Border normal = BorderFactory.createCompoundBorder(new BordeRedondeado(BORDE, 10, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10));
        Border foco = BorderFactory.createCompoundBorder(new BordeRedondeado(PRIMARIO, 10, 2),
                BorderFactory.createEmptyBorder(6, 9, 6, 9));
        campo.setBorder(normal);
        campo.setFont(FUENTE.deriveFont(14f));
        campo.setForeground(TEXTO);
        campo.setBackground(Color.WHITE);
        campo.setCaretColor(PRIMARIO);
        campo.setPreferredSize(new Dimension(Math.max(120, campo.getPreferredSize().width), 38));
        if (!Boolean.TRUE.equals(campo.getClientProperty("sgo.focus.configurado"))) {
            campo.putClientProperty("sgo.focus.configurado", Boolean.TRUE);
            campo.addFocusListener(new FocusAdapter() {
                @Override public void focusGained(FocusEvent e) { campo.setBorder(foco); }
                @Override public void focusLost(FocusEvent e) { campo.setBorder(normal); }
            });
        }
    }

    public static void configurarCombo(JComboBox<?> combo) {
        combo.setFont(FUENTE);
        combo.setBackground(Color.WHITE);
        combo.setForeground(TEXTO);
        combo.setBorder(new BordeRedondeado(BORDE, 10, 1));
        combo.setPreferredSize(new Dimension(Math.max(130, combo.getPreferredSize().width), 38));
    }

    public static void configurarSpinner(JSpinner spinner) {
        spinner.setFont(FUENTE);
        spinner.setBorder(new BordeRedondeado(BORDE, 10, 1));
        spinner.setPreferredSize(new Dimension(Math.max(100, spinner.getPreferredSize().width), 38));
    }

    public static void configurarBoton(JButton boton, IconosUI.Tipo icono, VarianteBoton variante) {
        Color fondo;
        Color frente;
        switch (variante) {
            case PRIMARIO -> { fondo = PRIMARIO; frente = Color.WHITE; }
            case SECUNDARIO -> { fondo = new Color(226, 232, 240); frente = TEXTO; }
            case PELIGRO -> { fondo = new Color(254, 226, 226); frente = ERROR; }
            case FANTASMA -> { fondo = Color.WHITE; frente = TEXTO; }
            case NAVEGACION -> { fondo = SIDEBAR; frente = new Color(226, 232, 240); }
            default -> { fondo = PRIMARIO; frente = Color.WHITE; }
        }
        boton.setFont(FUENTE.deriveFont(Font.BOLD, variante == VarianteBoton.NAVEGACION ? 13f : 12f));
        boton.setBackground(fondo);
        boton.setForeground(frente);
        boton.setIcon(IconosUI.crear(icono, variante == VarianteBoton.NAVEGACION ? 19 : 16, frente));
        boton.setIconTextGap(9);
        boton.setHorizontalAlignment(variante == VarianteBoton.NAVEGACION ? SwingConstants.LEFT : SwingConstants.CENTER);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setBorder(new BordeRedondeado(variante == VarianteBoton.FANTASMA ? BORDE : fondo, 10, 1));
        boton.setMargin(new Insets(9, 14, 9, 14));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(Math.max(100, boton.getPreferredSize().width),
                variante == VarianteBoton.NAVEGACION ? 43 : 38));
    }

    public static void configurarBotonNavegacion(JButton boton, IconosUI.Tipo icono) {
        configurarBoton(boton, icono, VarianteBoton.NAVEGACION);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void configurarTabla(JTable tabla, JScrollPane scroll) {
        tabla.setRowHeight(36);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(BORDE);
        tabla.setIntercellSpacing(new Dimension(0, 1));
        tabla.setSelectionBackground(new Color(219, 234, 254));
        tabla.setSelectionForeground(TEXTO);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoCreateRowSorter(true);
        tabla.setDefaultRenderer(Object.class, new RenderFila());
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(TEXTO);
        header.setFont(FUENTE.deriveFont(Font.BOLD, 12f));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE));
        scroll.setBorder(new BordeRedondeado(BORDE, 12, 1));
        JViewport viewport = scroll.getViewport();
        viewport.setBackground(Color.WHITE);
    }

    public static void aplicarFormularioMantenimiento(JInternalFrame frame, JPanel formulario,
            JPanel acciones, JTable tabla, JScrollPane scroll) {
        frame.getContentPane().setBackground(FONDO);
        frame.setFrameIcon(IconosUI.crear(IconosUI.Tipo.INFORMACION, 16, PRIMARIO));
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(BorderFactory.createCompoundBorder(
                new BordeRedondeado(BORDE, 14, 1), BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        acciones.setBackground(Color.WHITE);
        acciones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDE),
                BorderFactory.createEmptyBorder(9, 10, 9, 10)));
        aplicarControles(formulario);
        aplicarControles(acciones);
        configurarTabla(tabla, scroll);
        estilizarBotonesPorTexto(acciones);
    }

    public static void aplicarControles(Container contenedor) {
        for (Component componente : contenedor.getComponents()) {
            if (componente instanceof JPasswordField campo) configurarCampo(campo);
            else if (componente instanceof JTextField campo) configurarCampo(campo);
            else if (componente instanceof JComboBox<?> combo) configurarCombo(combo);
            else if (componente instanceof JSpinner spinner) configurarSpinner(spinner);
            else if (componente instanceof JLabel etiqueta) {
                etiqueta.setFont(FUENTE);
                etiqueta.setForeground(TEXTO);
            } else if (componente instanceof JCheckBox check) {
                check.setFont(FUENTE);
                check.setForeground(TEXTO);
                check.setOpaque(false);
            }
            if (componente instanceof Container hijo) aplicarControles(hijo);
        }
    }

    public static void estilizarBotonesPorTexto(Container contenedor) {
        for (Component componente : contenedor.getComponents()) {
            if (componente instanceof JButton boton) {
                String texto = boton.getText() == null ? "" : boton.getText().toLowerCase();
                if (texto.contains("guardar")) configurarBoton(boton, IconosUI.Tipo.GUARDAR, VarianteBoton.PRIMARIO);
                else if (texto.contains("modificar") || texto.contains("editar")) configurarBoton(boton, IconosUI.Tipo.EDITAR, VarianteBoton.SECUNDARIO);
                else if (texto.contains("eliminar")) configurarBoton(boton, IconosUI.Tipo.ELIMINAR, VarianteBoton.PELIGRO);
                else if (texto.contains("nuevo")) configurarBoton(boton, IconosUI.Tipo.NUEVO, VarianteBoton.SECUNDARIO);
                else if (texto.contains("cancelar")) configurarBoton(boton, IconosUI.Tipo.CANCELAR, VarianteBoton.FANTASMA);
                else if (texto.contains("buscar") || texto.contains("filtrar")) configurarBoton(boton, IconosUI.Tipo.BUSCAR, VarianteBoton.FANTASMA);
                else if (texto.contains("generar") || texto.contains("actualizar")) configurarBoton(boton, IconosUI.Tipo.ACTUALIZAR, VarianteBoton.PRIMARIO);
            }
            if (componente instanceof Container hijo) estilizarBotonesPorTexto(hijo);
        }
    }

    public static JPanel crearTarjeta() {
        JPanel panel = new JPanel();
        panel.setBackground(TARJETA);
        panel.setBorder(BorderFactory.createCompoundBorder(new BordeRedondeado(BORDE, 16, 1),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        return panel;
    }

    public static void margenInferior(JComponent componente, int margen) {
        componente.setBorder(BorderFactory.createEmptyBorder(0, 0, margen, 0));
    }

    public static void configurarBotonPrimario(JButton boton) {
        configurarBoton(boton, IconosUI.Tipo.USUARIO, VarianteBoton.PRIMARIO);
    }

    private static final class RenderFila extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) c.setBackground(row % 2 == 0 ? Color.WHITE : FILA_ALTERNA);
            c.setForeground(TEXTO);
            setBorder(BorderFactory.createEmptyBorder(0, 9, 0, 9));
            return c;
        }
    }

    private static final class BordeRedondeado extends AbstractBorder {
        private final Color color;
        private final int radio;
        private final int grosor;

        private BordeRedondeado(Color color, int radio, int grosor) {
            this.color = color; this.radio = radio; this.grosor = grosor;
        }

        @Override public Insets getBorderInsets(Component c) { return new Insets(grosor, grosor, grosor, grosor); }
        @Override public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(grosor, grosor, grosor, grosor); return insets;
        }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new java.awt.BasicStroke(grosor));
            g2.drawRoundRect(x, y, width - 1, height - 1, radio, radio);
            g2.dispose();
        }
    }
}
