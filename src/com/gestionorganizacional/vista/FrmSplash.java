package com.gestionorganizacional.vista;

import com.gestionorganizacional.conexion.ConexionDB;
import com.gestionorganizacional.util.EstilosUI;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class FrmSplash extends javax.swing.JDialog {

    public FrmSplash() {
        super((java.awt.Frame) null, false);
        initComponents();
        setLocationRelativeTo(null);
    }

    public void iniciarCarga() {
        setVisible(true);
        new SwingWorker<Void, String>() {
            private Exception error;

            @Override
            protected Void doInBackground() {
                try {
                    actualizar(15, "Preparando la aplicación...");
                    pausaBreve();
                    actualizar(40, "Verificando la base de datos...");
                    ConexionDB.inicializar();
                    pausaBreve();
                    actualizar(72, "Cargando configuración...");
                    pausaBreve();
                    actualizar(100, "Todo listo");
                    pausaBreve();
                } catch (Exception ex) {
                    error = ex;
                }
                return null;
            }

            private void actualizar(int progreso, String mensaje) {
                setProgress(progreso);
                publish(mensaje);
            }

            private void pausaBreve() throws InterruptedException {
                Thread.sleep(320);
            }

            @Override
            protected void process(List<String> mensajes) {
                if (!mensajes.isEmpty()) {
                    lblEstado.setText(mensajes.get(mensajes.size() - 1));
                }
                prgCarga.setValue(getProgress());
            }

            @Override
            protected void done() {
                dispose();
                if (error != null) {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo conectar o preparar MySQL.\n"
                            + "Verifique que el servidor esté iniciado y revise settings.properties.\n\n"
                            + "Detalle: " + error.getMessage(),
                            "Error de inicio", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                new FrmLogin().setVisible(true);
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlFondo = new com.gestionorganizacional.util.PanelDegradado();
        lblLogo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        prgCarga = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Iniciando sistema");
        setAlwaysOnTop(true);
        setModal(false);
        setUndecorated(true);
        setResizable(false);

        pnlFondo.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(EstilosUI.PRIMARIO_OSCURO, 2),
                javax.swing.BorderFactory.createEmptyBorder(42, 64, 42, 64)));
        pnlFondo.setLayout(new java.awt.GridLayout(0, 1, 0, 8));

        lblLogo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 52));
        lblLogo.setForeground(java.awt.Color.WHITE);
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("SGO");
        pnlFondo.add(lblLogo);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 25));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Sistema de Gestión Organizacional");
        pnlFondo.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblSubtitulo.setForeground(new java.awt.Color(219, 234, 254));
        lblSubtitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubtitulo.setText("Personas · Proyectos · Organización");
        pnlFondo.add(lblSubtitulo);

        lblEstado.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblEstado.setForeground(java.awt.Color.WHITE);
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Preparando la aplicación...");
        pnlFondo.add(lblEstado);

        prgCarga.setBackground(new java.awt.Color(191, 219, 254));
        prgCarga.setForeground(java.awt.Color.WHITE);
        prgCarga.setBorderPainted(false);
        prgCarga.setStringPainted(true);
        pnlFondo.add(prgCarga);

        getContentPane().add(pnlFondo, java.awt.BorderLayout.CENTER);
        setSize(new java.awt.Dimension(600, 380));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private com.gestionorganizacional.util.PanelDegradado pnlFondo;
    private javax.swing.JProgressBar prgCarga;
    // End of variables declaration//GEN-END:variables
}
