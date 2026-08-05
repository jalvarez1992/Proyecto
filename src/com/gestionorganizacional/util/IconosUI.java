package com.gestionorganizacional.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Icon;

/** Iconos vectoriales livianos, sin archivos externos ni dependencias. */
public final class IconosUI {
    public enum Tipo {
        INICIO, PAIS, DEPARTAMENTO, CARGO, EMPLEADO, PROYECTO, ASIGNACION,
        REPORTE, NUEVO, GUARDAR, EDITAR, ELIMINAR, CANCELAR, BUSCAR,
        PDF, CSV, SALIR, CERRAR_SESION, INFORMACION, ACTUALIZAR, USUARIO
    }

    private IconosUI() {}

    public static Icon crear(Tipo tipo, int tamano, Color color) {
        return new IconoVector(tipo, tamano, color);
    }

    private static final class IconoVector implements Icon {
        private final Tipo tipo;
        private final int tamano;
        private final Color color;

        private IconoVector(Tipo tipo, int tamano, Color color) {
            this.tipo = tipo;
            this.tamano = tamano;
            this.color = color;
        }

        @Override public int getIconWidth() { return tamano; }
        @Override public int getIconHeight() { return tamano; }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(Math.max(1.6f, tamano / 12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            double s = tamano;
            switch (tipo) {
                case INICIO -> inicio(g2, s);
                case PAIS -> pais(g2, s);
                case DEPARTAMENTO -> departamento(g2, s);
                case CARGO -> cargo(g2, s);
                case EMPLEADO, USUARIO -> usuario(g2, s);
                case PROYECTO -> proyecto(g2, s);
                case ASIGNACION -> asignacion(g2, s);
                case REPORTE -> reporte(g2, s);
                case NUEVO -> mas(g2, s);
                case GUARDAR -> guardar(g2, s);
                case EDITAR -> editar(g2, s);
                case ELIMINAR -> eliminar(g2, s);
                case CANCELAR -> cancelar(g2, s);
                case BUSCAR -> buscar(g2, s);
                case PDF -> documento(g2, s, true);
                case CSV -> documento(g2, s, false);
                case SALIR -> salir(g2, s);
                case CERRAR_SESION -> cerrarSesion(g2, s);
                case INFORMACION -> informacion(g2, s);
                case ACTUALIZAR -> actualizar(g2, s);
            }
            g2.dispose();
        }

        private static void inicio(Graphics2D g, double s) {
            Path2D p = new Path2D.Double();
            p.moveTo(s * .13, s * .46); p.lineTo(s * .5, s * .14); p.lineTo(s * .87, s * .46);
            g.draw(p); g.draw(new Rectangle2D.Double(s * .23, s * .43, s * .54, s * .43));
            g.draw(new Rectangle2D.Double(s * .43, s * .62, s * .15, s * .24));
        }

        private static void pais(Graphics2D g, double s) {
            g.draw(new Ellipse2D.Double(s * .12, s * .12, s * .76, s * .76));
            g.draw(new Arc2D.Double(s * .28, s * .12, s * .44, s * .76, 90, 180, Arc2D.OPEN));
            g.draw(new Arc2D.Double(s * .28, s * .12, s * .44, s * .76, -90, 180, Arc2D.OPEN));
            g.draw(new Line2D.Double(s * .14, s * .39, s * .86, s * .39));
            g.draw(new Line2D.Double(s * .14, s * .61, s * .86, s * .61));
        }

        private static void departamento(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .15, s * .32, s * .7, s * .55));
            g.draw(new Rectangle2D.Double(s * .31, s * .13, s * .38, s * .19));
            for (int r = 0; r < 2; r++) for (int c = 0; c < 3; c++) {
                g.draw(new Rectangle2D.Double(s * (.25 + c * .19), s * (.43 + r * .18), s * .1, s * .09));
            }
        }

        private static void cargo(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .12, s * .3, s * .76, s * .52));
            g.draw(new Arc2D.Double(s * .34, s * .12, s * .32, s * .34, 0, 180, Arc2D.OPEN));
            g.draw(new Line2D.Double(s * .12, s * .5, s * .88, s * .5));
            g.draw(new Rectangle2D.Double(s * .43, s * .45, s * .14, s * .1));
        }

        private static void usuario(Graphics2D g, double s) {
            g.draw(new Ellipse2D.Double(s * .34, s * .12, s * .32, s * .32));
            g.draw(new Arc2D.Double(s * .16, s * .42, s * .68, s * .5, 0, 180, Arc2D.OPEN));
        }

        private static void proyecto(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .14, s * .2, s * .72, s * .65));
            g.draw(new Line2D.Double(s * .28, s * .38, s * .72, s * .38));
            g.draw(new Line2D.Double(s * .28, s * .55, s * .72, s * .55));
            g.draw(new Line2D.Double(s * .28, s * .72, s * .58, s * .72));
            g.draw(new Line2D.Double(s * .22, s * .12, s * .22, s * .28));
            g.draw(new Line2D.Double(s * .78, s * .12, s * .78, s * .28));
        }

        private static void asignacion(Graphics2D g, double s) {
            usuario(g, s * .7);
            g.draw(new Line2D.Double(s * .58, s * .58, s * .86, s * .58));
            g.draw(new Line2D.Double(s * .72, s * .44, s * .72, s * .72));
        }

        private static void reporte(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .17, s * .12, s * .66, s * .76));
            g.draw(new Line2D.Double(s * .3, s * .3, s * .7, s * .3));
            g.draw(new Rectangle2D.Double(s * .28, s * .62, s * .09, s * .14));
            g.draw(new Rectangle2D.Double(s * .45, s * .49, s * .09, s * .27));
            g.draw(new Rectangle2D.Double(s * .62, s * .38, s * .09, s * .38));
        }

        private static void mas(Graphics2D g, double s) {
            g.draw(new Line2D.Double(s * .5, s * .18, s * .5, s * .82));
            g.draw(new Line2D.Double(s * .18, s * .5, s * .82, s * .5));
        }

        private static void guardar(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .14, s * .12, s * .72, s * .76));
            g.draw(new Rectangle2D.Double(s * .28, s * .15, s * .4, s * .24));
            g.draw(new Rectangle2D.Double(s * .28, s * .58, s * .44, s * .3));
        }

        private static void editar(Graphics2D g, double s) {
            Path2D p = new Path2D.Double();
            p.moveTo(s * .18, s * .72); p.lineTo(s * .28, s * .46); p.lineTo(s * .68, s * .16);
            p.lineTo(s * .84, s * .32); p.lineTo(s * .45, s * .63); p.closePath();
            g.draw(p); g.draw(new Line2D.Double(s * .18, s * .82, s * .52, s * .82));
        }

        private static void eliminar(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .25, s * .28, s * .5, s * .58));
            g.draw(new Line2D.Double(s * .18, s * .26, s * .82, s * .26));
            g.draw(new Line2D.Double(s * .38, s * .14, s * .62, s * .14));
            g.draw(new Line2D.Double(s * .41, s * .4, s * .41, s * .72));
            g.draw(new Line2D.Double(s * .59, s * .4, s * .59, s * .72));
        }

        private static void cancelar(Graphics2D g, double s) {
            g.draw(new Ellipse2D.Double(s * .14, s * .14, s * .72, s * .72));
            g.draw(new Line2D.Double(s * .31, s * .31, s * .69, s * .69));
            g.draw(new Line2D.Double(s * .69, s * .31, s * .31, s * .69));
        }

        private static void buscar(Graphics2D g, double s) {
            g.draw(new Ellipse2D.Double(s * .13, s * .13, s * .52, s * .52));
            g.draw(new Line2D.Double(s * .58, s * .58, s * .87, s * .87));
        }

        private static void documento(Graphics2D g, double s, boolean pdf) {
            Path2D p = new Path2D.Double();
            p.moveTo(s * .2, s * .1); p.lineTo(s * .62, s * .1); p.lineTo(s * .82, s * .3);
            p.lineTo(s * .82, s * .9); p.lineTo(s * .2, s * .9); p.closePath();
            g.draw(p); g.draw(new Line2D.Double(s * .62, s * .1, s * .62, s * .3));
            g.draw(new Line2D.Double(s * .62, s * .3, s * .82, s * .3));
            if (pdf) {
                g.draw(new Line2D.Double(s * .31, s * .55, s * .31, s * .75));
                g.draw(new Arc2D.Double(s * .31, s * .55, s * .17, s * .11, -90, 180, Arc2D.OPEN));
                g.draw(new Line2D.Double(s * .55, s * .55, s * .55, s * .75));
                g.draw(new Arc2D.Double(s * .47, s * .55, s * .16, s * .2, -90, 180, Arc2D.OPEN));
            } else {
                g.draw(new Rectangle2D.Double(s * .29, s * .52, s * .42, s * .26));
                g.draw(new Line2D.Double(s * .29, s * .61, s * .71, s * .61));
                g.draw(new Line2D.Double(s * .29, s * .69, s * .71, s * .69));
                g.draw(new Line2D.Double(s * .43, s * .52, s * .43, s * .78));
                g.draw(new Line2D.Double(s * .57, s * .52, s * .57, s * .78));
            }
        }

        private static void salir(Graphics2D g, double s) {
            g.draw(new Rectangle2D.Double(s * .14, s * .13, s * .44, s * .74));
            g.draw(new Line2D.Double(s * .45, s * .5, s * .88, s * .5));
            g.draw(new Line2D.Double(s * .7, s * .32, s * .88, s * .5));
            g.draw(new Line2D.Double(s * .7, s * .68, s * .88, s * .5));
        }

        private static void cerrarSesion(Graphics2D g, double s) {
            g.draw(new Arc2D.Double(s * .16, s * .16, s * .68, s * .68, 40, 280, Arc2D.OPEN));
            g.draw(new Line2D.Double(s * .5, s * .08, s * .5, s * .46));
        }

        private static void informacion(Graphics2D g, double s) {
            g.draw(new Ellipse2D.Double(s * .14, s * .14, s * .72, s * .72));
            g.draw(new Line2D.Double(s * .5, s * .43, s * .5, s * .72));
            g.fill(new Ellipse2D.Double(s * .46, s * .27, s * .08, s * .08));
        }

        private static void actualizar(Graphics2D g, double s) {
            g.draw(new Arc2D.Double(s * .16, s * .16, s * .68, s * .68, 35, 285, Arc2D.OPEN));
            Path2D p = new Path2D.Double();
            p.moveTo(s * .7, s * .13); p.lineTo(s * .86, s * .16); p.lineTo(s * .8, s * .32); p.closePath();
            g.fill(p);
        }
    }
}
