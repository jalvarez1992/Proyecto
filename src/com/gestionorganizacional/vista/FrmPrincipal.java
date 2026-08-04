package com.gestionorganizacional.vista;

import com.gestionorganizacional.modelo.Usuario;
import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.util.Sesion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class FrmPrincipal extends javax.swing.JFrame {
    private final DateTimeFormatter formatoFecha =
            DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
    private javax.swing.Timer reloj;

    public FrmPrincipal() {
        initComponents();
        aplicarSesion();
        iniciarReloj();
        setLocationRelativeTo(null);
    }

    private void aplicarSesion() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblUsuarioActual.setText("  " + usuario.getNombreCompleto()
                    + "  ·  " + usuario.getRol());
            setTitle("Sistema de Gestión Organizacional · " + usuario.getNombreCompleto());
        } else {
            lblUsuarioActual.setText("  Sesión no iniciada");
        }
    }

    private void iniciarReloj() {
        lblFechaHora.setText(LocalDateTime.now().format(formatoFecha) + "  ");
        reloj = new javax.swing.Timer(1000,
                evt -> lblFechaHora.setText(LocalDateTime.now().format(formatoFecha) + "  "));
        reloj.start();
    }

    @Override
    public void dispose() {
        if (reloj != null) reloj.stop();
        super.dispose();
    }

    private void abrir(JInternalFrame nuevo) {
        for (JInternalFrame abierto : desktopPane.getAllFrames()) {
            if (abierto.getClass().equals(nuevo.getClass())) {
                try {
                    abierto.setIcon(false);
                    abierto.setSelected(true);
                    abierto.toFront();
                } catch (java.beans.PropertyVetoException ignored) {
                    abierto.toFront();
                }
                nuevo.dispose();
                return;
            }
        }
        desktopPane.add(nuevo);
        nuevo.setVisible(true);
        nuevo.toFront();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        mnuCatalogos = new javax.swing.JMenu();
        mniPaises = new javax.swing.JMenuItem();
        mniDepartamentos = new javax.swing.JMenuItem();
        mniCargos = new javax.swing.JMenuItem();
        mnuGestion = new javax.swing.JMenu();
        mniEmpleados = new javax.swing.JMenuItem();
        mniProyectos = new javax.swing.JMenuItem();
        mniAsignaciones = new javax.swing.JMenuItem();
        mnuSistema = new javax.swing.JMenu();
        mniAcercaDe = new javax.swing.JMenuItem();
        mniCerrarSesion = new javax.swing.JMenuItem();
        mniSalir = new javax.swing.JMenuItem();
        pnlEstado = new javax.swing.JPanel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblFechaHora = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión Organizacional");
        setMinimumSize(new java.awt.Dimension(1000, 700));
        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        pnlEstado.setBackground(EstilosUI.TEXTO);
        pnlEstado.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 8, 7, 8));
        pnlEstado.setLayout(new java.awt.BorderLayout());
        lblUsuarioActual.setForeground(java.awt.Color.WHITE);
        lblUsuarioActual.setText("  Usuario");
        pnlEstado.add(lblUsuarioActual, java.awt.BorderLayout.WEST);
        lblFechaHora.setForeground(new java.awt.Color(203, 213, 225));
        lblFechaHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaHora.setText("Fecha");
        pnlEstado.add(lblFechaHora, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlEstado, java.awt.BorderLayout.SOUTH);

        mnuCatalogos.setText("Catálogos");
        mniPaises.setText("Países");
        mniPaises.addActionListener(evt -> mniPaisesActionPerformed(evt));
        mnuCatalogos.add(mniPaises);
        mniDepartamentos.setText("Departamentos");
        mniDepartamentos.addActionListener(evt -> mniDepartamentosActionPerformed(evt));
        mnuCatalogos.add(mniDepartamentos);
        mniCargos.setText("Cargos");
        mniCargos.addActionListener(evt -> mniCargosActionPerformed(evt));
        mnuCatalogos.add(mniCargos);
        menuBar.add(mnuCatalogos);

        mnuGestion.setText("Gestión");
        mniEmpleados.setText("Empleados");
        mniEmpleados.addActionListener(evt -> mniEmpleadosActionPerformed(evt));
        mnuGestion.add(mniEmpleados);
        mniProyectos.setText("Proyectos");
        mniProyectos.addActionListener(evt -> mniProyectosActionPerformed(evt));
        mnuGestion.add(mniProyectos);
        mniAsignaciones.setText("Asignaciones");
        mniAsignaciones.addActionListener(evt -> mniAsignacionesActionPerformed(evt));
        mnuGestion.add(mniAsignaciones);
        menuBar.add(mnuGestion);

        mnuSistema.setText("Sistema");
        mniAcercaDe.setText("Acerca de");
        mniAcercaDe.addActionListener(evt -> mniAcercaDeActionPerformed(evt));
        mnuSistema.add(mniAcercaDe);
        mniCerrarSesion.setText("Cerrar sesión");
        mniCerrarSesion.addActionListener(evt -> mniCerrarSesionActionPerformed(evt));
        mnuSistema.add(mniCerrarSesion);
        mniSalir.setText("Salir");
        mniSalir.addActionListener(evt -> mniSalirActionPerformed(evt));
        mnuSistema.add(mniSalir);
        menuBar.add(mnuSistema);
        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mniPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniPaisesActionPerformed
        abrir(new FrmPais());
    }//GEN-LAST:event_mniPaisesActionPerformed
    private void mniDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDepartamentosActionPerformed
        abrir(new FrmDepartamento());
    }//GEN-LAST:event_mniDepartamentosActionPerformed
    private void mniCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCargosActionPerformed
        abrir(new FrmCargo());
    }//GEN-LAST:event_mniCargosActionPerformed
    private void mniEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniEmpleadosActionPerformed
        abrir(new FrmEmpleado());
    }//GEN-LAST:event_mniEmpleadosActionPerformed
    private void mniProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniProyectosActionPerformed
        abrir(new FrmProyecto());
    }//GEN-LAST:event_mniProyectosActionPerformed
    private void mniAsignacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAsignacionesActionPerformed
        abrir(new FrmAsignacion());
    }//GEN-LAST:event_mniAsignacionesActionPerformed
    private void mniAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAcercaDeActionPerformed
        JOptionPane.showMessageDialog(this,
                "Sistema de Gestión Organizacional\nJava 17 · Swing · MySQL · JDBC",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_mniAcercaDeActionPerformed
    private void mniCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCerrarSesionActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Desea cerrar la sesión actual?", "Cerrar sesión",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            Sesion.cerrar();
            dispose();
            new FrmLogin().setVisible(true);
        }
    }//GEN-LAST:event_mniCerrarSesionActionPerformed
    private void mniSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniSalirActionPerformed
        Sesion.cerrar();
        dispose();
    }//GEN-LAST:event_mniSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mniAcercaDe;
    private javax.swing.JMenuItem mniAsignaciones;
    private javax.swing.JMenuItem mniCargos;
    private javax.swing.JMenuItem mniCerrarSesion;
    private javax.swing.JMenuItem mniDepartamentos;
    private javax.swing.JMenuItem mniEmpleados;
    private javax.swing.JMenuItem mniPaises;
    private javax.swing.JMenuItem mniProyectos;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JMenu mnuCatalogos;
    private javax.swing.JMenu mnuGestion;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JLabel lblFechaHora;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JPanel pnlEstado;
    // End of variables declaration//GEN-END:variables
}
