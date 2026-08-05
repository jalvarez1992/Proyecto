package com.gestionorganizacional.vista;

import com.gestionorganizacional.modelo.Usuario;
import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.util.IconosUI;
import com.gestionorganizacional.util.Sesion;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class FrmPrincipal extends javax.swing.JFrame {
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss");
    private javax.swing.Timer reloj;

    public FrmPrincipal() {
        initComponents();
        aplicarEstilos();
        aplicarSesion();
        iniciarReloj();
        setLocationRelativeTo(null);
        desktopPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                ajustarDashboard();
            }
        });
        javax.swing.SwingUtilities.invokeLater(this::mostrarInicio);
    }

    private void aplicarEstilos() {
        getContentPane().setBackground(EstilosUI.FONDO);
        pnlSidebar.setBackground(EstilosUI.SIDEBAR);
        pnlSidebar.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 12, 14, 12));
        lblMarca.setForeground(java.awt.Color.WHITE);
        lblModulo.setForeground(new java.awt.Color(148, 163, 184));
        EstilosUI.configurarBotonNavegacion(btnInicio, IconosUI.Tipo.INICIO);
        EstilosUI.configurarBotonNavegacion(btnPaises, IconosUI.Tipo.PAIS);
        EstilosUI.configurarBotonNavegacion(btnDepartamentos, IconosUI.Tipo.DEPARTAMENTO);
        EstilosUI.configurarBotonNavegacion(btnCargos, IconosUI.Tipo.CARGO);
        EstilosUI.configurarBotonNavegacion(btnEmpleados, IconosUI.Tipo.EMPLEADO);
        EstilosUI.configurarBotonNavegacion(btnProyectos, IconosUI.Tipo.PROYECTO);
        EstilosUI.configurarBotonNavegacion(btnAsignaciones, IconosUI.Tipo.ASIGNACION);
        EstilosUI.configurarBotonNavegacion(btnReportes, IconosUI.Tipo.REPORTE);
        EstilosUI.configurarBotonNavegacion(btnAcercaDe, IconosUI.Tipo.INFORMACION);
        EstilosUI.configurarBotonNavegacion(btnCerrarSesion, IconosUI.Tipo.CERRAR_SESION);
        EstilosUI.configurarBotonNavegacion(btnSalir, IconosUI.Tipo.SALIR);
        pnlHeader.setBackground(java.awt.Color.WHITE);
        pnlHeader.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, EstilosUI.BORDE),
                javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18)));
        lblTituloSeccion.setForeground(EstilosUI.TEXTO);
        lblUsuarioActual.setForeground(EstilosUI.TEXTO_SUAVE);
        desktopPane.setBackground(EstilosUI.FONDO);
        pnlEstado.setBackground(java.awt.Color.WHITE);
        pnlEstado.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, EstilosUI.BORDE),
                javax.swing.BorderFactory.createEmptyBorder(7, 12, 7, 12)));
        lblEstadoSistema.setForeground(EstilosUI.EXITO);
        lblFechaHora.setForeground(EstilosUI.TEXTO_SUAVE);
    }

    private void aplicarSesion() {
        Usuario usuario = Sesion.getUsuarioActual();
        if (usuario != null) {
            lblUsuarioActual.setText(usuario.getNombreCompleto() + "  ·  " + usuario.getRol());
            setTitle("Sistema de Gestión Organizacional · " + usuario.getNombreCompleto());
        } else {
            lblUsuarioActual.setText("Sesión no iniciada");
        }
    }

    private void iniciarReloj() {
        lblFechaHora.setText(LocalDateTime.now().format(formatoFecha));
        reloj = new javax.swing.Timer(1000,
                evt -> lblFechaHora.setText(LocalDateTime.now().format(formatoFecha)));
        reloj.start();
    }

    @Override public void dispose() {
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
                } catch (java.beans.PropertyVetoException ignored) { abierto.toFront(); }
                nuevo.dispose();
                return;
            }
        }
        desktopPane.add(nuevo);
        nuevo.setVisible(true);
        if (nuevo instanceof FrmDashboard) {
            nuevo.setBounds(0, 0, Math.max(700, desktopPane.getWidth()), Math.max(500, desktopPane.getHeight()));
            nuevo.moveToBack();
        } else {
            Dimension escritorio = desktopPane.getSize();
            Dimension ventana = nuevo.getSize();
            int x = Math.max(8, (escritorio.width - ventana.width) / 2);
            int y = Math.max(8, (escritorio.height - ventana.height) / 2);
            nuevo.setLocation(x, y);
            nuevo.toFront();
        }
    }

    private void mostrarInicio() {
        lblTituloSeccion.setText("Inicio");
        cerrarDashboardAnterior();
        abrir(new FrmDashboard(frame -> abrirModulo(frame.getTitle(), frame)));
        ajustarDashboard();
    }

    private void cerrarDashboardAnterior() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof FrmDashboard) frame.dispose();
        }
    }

    private void ajustarDashboard() {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof FrmDashboard) {
                frame.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
                frame.moveToBack();
            }
        }
    }

    private void abrirModulo(String titulo, JInternalFrame frame) {
        lblTituloSeccion.setText(titulo);
        abrir(frame);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlSidebar = new javax.swing.JPanel();
        lblMarca = new javax.swing.JLabel();
        lblModulo = new javax.swing.JLabel();
        btnInicio = new javax.swing.JButton();
        btnPaises = new javax.swing.JButton();
        btnDepartamentos = new javax.swing.JButton();
        btnCargos = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnProyectos = new javax.swing.JButton();
        btnAsignaciones = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnAcercaDe = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        pnlHeader = new javax.swing.JPanel();
        lblTituloSeccion = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        desktopPane = new javax.swing.JDesktopPane();
        pnlEstado = new javax.swing.JPanel();
        lblEstadoSistema = new javax.swing.JLabel();
        lblFechaHora = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestión Organizacional");
        setMinimumSize(new java.awt.Dimension(1180, 720));

        pnlSidebar.setPreferredSize(new java.awt.Dimension(235, 720));
        pnlSidebar.setLayout(new java.awt.GridLayout(0, 1, 0, 4));
        lblMarca.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 25));
        lblMarca.setText("  SGO");
        pnlSidebar.add(lblMarca);
        lblModulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
        lblModulo.setText("  NAVEGACIÓN");
        pnlSidebar.add(lblModulo);
        btnInicio.setText("Inicio");
        btnInicio.addActionListener(evt -> btnInicioActionPerformed(evt));
        pnlSidebar.add(btnInicio);
        btnPaises.setText("Países");
        btnPaises.addActionListener(evt -> btnPaisesActionPerformed(evt));
        pnlSidebar.add(btnPaises);
        btnDepartamentos.setText("Departamentos");
        btnDepartamentos.addActionListener(evt -> btnDepartamentosActionPerformed(evt));
        pnlSidebar.add(btnDepartamentos);
        btnCargos.setText("Cargos");
        btnCargos.addActionListener(evt -> btnCargosActionPerformed(evt));
        pnlSidebar.add(btnCargos);
        btnEmpleados.setText("Empleados");
        btnEmpleados.addActionListener(evt -> btnEmpleadosActionPerformed(evt));
        pnlSidebar.add(btnEmpleados);
        btnProyectos.setText("Proyectos");
        btnProyectos.addActionListener(evt -> btnProyectosActionPerformed(evt));
        pnlSidebar.add(btnProyectos);
        btnAsignaciones.setText("Asignaciones");
        btnAsignaciones.addActionListener(evt -> btnAsignacionesActionPerformed(evt));
        pnlSidebar.add(btnAsignaciones);
        btnReportes.setText("Reportería");
        btnReportes.addActionListener(evt -> btnReportesActionPerformed(evt));
        pnlSidebar.add(btnReportes);
        btnAcercaDe.setText("Acerca de");
        btnAcercaDe.addActionListener(evt -> btnAcercaDeActionPerformed(evt));
        pnlSidebar.add(btnAcercaDe);
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(evt -> btnCerrarSesionActionPerformed(evt));
        pnlSidebar.add(btnCerrarSesion);
        btnSalir.setText("Salir");
        btnSalir.addActionListener(evt -> btnSalirActionPerformed(evt));
        pnlSidebar.add(btnSalir);
        getContentPane().add(pnlSidebar, java.awt.BorderLayout.WEST);

        pnlHeader.setLayout(new java.awt.BorderLayout());
        lblTituloSeccion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTituloSeccion.setText("Inicio");
        pnlHeader.add(lblTituloSeccion, java.awt.BorderLayout.WEST);
        lblUsuarioActual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUsuarioActual.setText("Usuario");
        pnlHeader.add(lblUsuarioActual, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);

        desktopPane.setLayout(null);
        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        pnlEstado.setLayout(new java.awt.BorderLayout());
        lblEstadoSistema.setText("● Sistema listo");
        pnlEstado.add(lblEstadoSistema, java.awt.BorderLayout.WEST);
        lblFechaHora.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFechaHora.setText("Fecha");
        pnlEstado.add(lblFechaHora, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlEstado, java.awt.BorderLayout.SOUTH);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        mostrarInicio();
    }//GEN-LAST:event_btnInicioActionPerformed
    private void btnPaisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaisesActionPerformed
        abrirModulo("Países", new FrmPais());
    }//GEN-LAST:event_btnPaisesActionPerformed
    private void btnDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartamentosActionPerformed
        abrirModulo("Departamentos", new FrmDepartamento());
    }//GEN-LAST:event_btnDepartamentosActionPerformed
    private void btnCargosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargosActionPerformed
        abrirModulo("Cargos", new FrmCargo());
    }//GEN-LAST:event_btnCargosActionPerformed
    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        abrirModulo("Empleados", new FrmEmpleado());
    }//GEN-LAST:event_btnEmpleadosActionPerformed
    private void btnProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProyectosActionPerformed
        abrirModulo("Proyectos", new FrmProyecto());
    }//GEN-LAST:event_btnProyectosActionPerformed
    private void btnAsignacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignacionesActionPerformed
        abrirModulo("Asignaciones", new FrmAsignacion());
    }//GEN-LAST:event_btnAsignacionesActionPerformed
    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        abrirModulo("Reportería", new FrmReportes());
    }//GEN-LAST:event_btnReportesActionPerformed
    private void btnAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaDeActionPerformed
        JOptionPane.showMessageDialog(this,
                "Sistema de Gestión Organizacional\nJava 17 · Swing · MySQL · PDF · CSV",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnAcercaDeActionPerformed
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la sesión actual?",
                "Cerrar sesión", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) {
            Sesion.cerrar(); dispose(); new FrmLogin().setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea salir del sistema?",
                "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == JOptionPane.YES_OPTION) { Sesion.cerrar(); dispose(); }
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcercaDe;
    private javax.swing.JButton btnAsignaciones;
    private javax.swing.JButton btnCargos;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnDepartamentos;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnPaises;
    private javax.swing.JButton btnProyectos;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel lblEstadoSistema;
    private javax.swing.JLabel lblFechaHora;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModulo;
    private javax.swing.JLabel lblTituloSeccion;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JPanel pnlEstado;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlSidebar;
    // End of variables declaration//GEN-END:variables
}
