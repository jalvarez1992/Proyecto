package com.gestionorganizacional.util;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelDegradado extends JPanel {
    private Color colorInicial = new Color(30, 64, 175);
    private Color colorFinal = new Color(37, 99, 235);

    public PanelDegradado() {
        setOpaque(false);
    }

    public Color getColorInicial() { return colorInicial; }
    public void setColorInicial(Color colorInicial) { this.colorInicial = colorInicial; repaint(); }
    public Color getColorFinal() { return colorFinal; }
    public void setColorFinal(Color colorFinal) { this.colorFinal = colorFinal; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setPaint(new GradientPaint(0, 0, colorInicial, getWidth(), getHeight(), colorFinal));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}

