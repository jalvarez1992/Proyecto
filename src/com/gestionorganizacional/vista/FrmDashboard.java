package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.ReporteController;
import com.gestionorganizacional.modelo.ResumenDashboard;
import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.util.IconosUI;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.JInternalFrame;
import javax.swing.SwingWorker;

public class FrmDashboard extends javax.swing.JInternalFrame {
    private final ReporteController reporteController = new ReporteController();
    private Consumer<JInternalFrame> navegador = frame -> {};
    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "HN"));

    public FrmDashboard() {
        initComponents();
        aplicarEstilos();
        cargarResumen();
    }

    public FrmDashboard(Consumer<JInternalFrame> navegador) {
        this();
        if (navegador != null) this.navegador = navegador;
    }

    private void aplicarEstilos() {
        getContentPane().setBackground(EstilosUI.FONDO);
        if (getUI() instanceof javax.swing.plaf.basic.BasicInternalFrameUI ui) {
            ui.setNorthPane(null);
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder());
        pnlEncabezado.setBackground(EstilosUI.FONDO);
        pnlTarjetas.setBackground(EstilosUI.FONDO);
        pnlAccesos.setBackground(java.awt.Color.WHITE);
        pnlAccesos.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(EstilosUI.BORDE),
                javax.swing.BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        lblTitulo.setForeground(EstilosUI.TEXTO);
        lblBienvenida.setForeground(EstilosUI.TEXTO_SUAVE);
        lblAccesos.setForeground(EstilosUI.TEXTO);
        estilizarTarjeta(pnlEmpleados, lblEmpleadosTitulo, lblEmpleadosValor, IconosUI.Tipo.EMPLEADO);
        estilizarTarjeta(pnlDepartamentos, lblDepartamentosTitulo, lblDepartamentosValor, IconosUI.Tipo.DEPARTAMENTO);
        estilizarTarjeta(pnlProyectos, lblProyectosTitulo, lblProyectosValor, IconosUI.Tipo.PROYECTO);
        estilizarTarjeta(pnlAsignaciones, lblAsignacionesTitulo, lblAsignacionesValor, IconosUI.Tipo.ASIGNACION);
        EstilosUI.configurarBoton(btnEmpleados, IconosUI.Tipo.EMPLEADO, EstilosUI.VarianteBoton.FANTASMA);
        EstilosUI.configurarBoton(btnProyectos, IconosUI.Tipo.PROYECTO, EstilosUI.VarianteBoton.FANTASMA);
        EstilosUI.configurarBoton(btnAsignaciones, IconosUI.Tipo.ASIGNACION, EstilosUI.VarianteBoton.FANTASMA);
        EstilosUI.configurarBoton(btnReportes, IconosUI.Tipo.REPORTE, EstilosUI.VarianteBoton.PRIMARIO);
        lblPresupuesto.setForeground(EstilosUI.TEXTO_SUAVE);
    }

    private void estilizarTarjeta(javax.swing.JPanel panel, javax.swing.JLabel titulo,
            javax.swing.JLabel valor, IconosUI.Tipo icono) {
        panel.setBackground(java.awt.Color.WHITE);
        panel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(EstilosUI.BORDE),
                javax.swing.BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        titulo.setForeground(EstilosUI.TEXTO_SUAVE);
        titulo.setIcon(IconosUI.crear(icono, 20, EstilosUI.PRIMARIO));
        titulo.setIconTextGap(10);
        valor.setForeground(EstilosUI.TEXTO);
    }

    private void cargarResumen() {
        lblPresupuesto.setText("Actualizando indicadores...");
        new SwingWorker<ResumenDashboard, Void>() {
            private Exception error;
            @Override protected ResumenDashboard doInBackground() {
                try { return reporteController.obtenerResumen(); }
                catch (Exception ex) { error = ex; return null; }
            }
            @Override protected void done() {
                if (error != null) {
                    lblPresupuesto.setText("No fue posible cargar los indicadores: " + error.getMessage());
                    return;
                }
                try {
                    ResumenDashboard resumen = get();
                    lblEmpleadosValor.setText(String.valueOf(resumen.getEmpleadosActivos()));
                    lblDepartamentosValor.setText(String.valueOf(resumen.getDepartamentos()));
                    lblProyectosValor.setText(String.valueOf(resumen.getProyectosActivos()));
                    lblAsignacionesValor.setText(String.valueOf(resumen.getAsignaciones()));
                    lblPresupuesto.setText("Presupuesto acumulado de proyectos: "
                            + MONEDA.format(resumen.getPresupuestoTotal()));
                } catch (Exception ex) {
                    lblPresupuesto.setText("No fue posible actualizar los indicadores.");
                }
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlEncabezado = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        pnlTarjetas = new javax.swing.JPanel();
        pnlEmpleados = new javax.swing.JPanel();
        lblEmpleadosTitulo = new javax.swing.JLabel();
        lblEmpleadosValor = new javax.swing.JLabel();
        pnlDepartamentos = new javax.swing.JPanel();
        lblDepartamentosTitulo = new javax.swing.JLabel();
        lblDepartamentosValor = new javax.swing.JLabel();
        pnlProyectos = new javax.swing.JPanel();
        lblProyectosTitulo = new javax.swing.JLabel();
        lblProyectosValor = new javax.swing.JLabel();
        pnlAsignaciones = new javax.swing.JPanel();
        lblAsignacionesTitulo = new javax.swing.JLabel();
        lblAsignacionesValor = new javax.swing.JLabel();
        pnlAccesos = new javax.swing.JPanel();
        lblAccesos = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        btnEmpleados = new javax.swing.JButton();
        btnProyectos = new javax.swing.JButton();
        btnAsignaciones = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        lblPresupuesto = new javax.swing.JLabel();

        setBorder(null);
        setTitle("Inicio");
        setFrameIcon(null);

        pnlEncabezado.setBorder(javax.swing.BorderFactory.createEmptyBorder(24, 24, 14, 24));
        pnlEncabezado.setLayout(new java.awt.GridLayout(0, 1, 0, 4));
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 28));
        lblTitulo.setText("Panel de control");
        pnlEncabezado.add(lblTitulo);
        lblBienvenida.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblBienvenida.setText("Resumen general y accesos rápidos del sistema");
        pnlEncabezado.add(lblBienvenida);
        getContentPane().add(pnlEncabezado, java.awt.BorderLayout.NORTH);

        pnlTarjetas.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 24, 18, 24));
        pnlTarjetas.setLayout(new java.awt.GridLayout(1, 4, 14, 0));
        pnlEmpleados.setLayout(new java.awt.GridLayout(0, 1, 0, 8));
        lblEmpleadosTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblEmpleadosTitulo.setText("Empleados activos");
        pnlEmpleados.add(lblEmpleadosTitulo);
        lblEmpleadosValor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        lblEmpleadosValor.setText("—");
        pnlEmpleados.add(lblEmpleadosValor);
        pnlTarjetas.add(pnlEmpleados);

        pnlDepartamentos.setLayout(new java.awt.GridLayout(0, 1, 0, 8));
        lblDepartamentosTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblDepartamentosTitulo.setText("Departamentos");
        pnlDepartamentos.add(lblDepartamentosTitulo);
        lblDepartamentosValor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        lblDepartamentosValor.setText("—");
        pnlDepartamentos.add(lblDepartamentosValor);
        pnlTarjetas.add(pnlDepartamentos);

        pnlProyectos.setLayout(new java.awt.GridLayout(0, 1, 0, 8));
        lblProyectosTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblProyectosTitulo.setText("Proyectos activos");
        pnlProyectos.add(lblProyectosTitulo);
        lblProyectosValor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        lblProyectosValor.setText("—");
        pnlProyectos.add(lblProyectosValor);
        pnlTarjetas.add(pnlProyectos);

        pnlAsignaciones.setLayout(new java.awt.GridLayout(0, 1, 0, 8));
        lblAsignacionesTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblAsignacionesTitulo.setText("Asignaciones");
        pnlAsignaciones.add(lblAsignacionesTitulo);
        lblAsignacionesValor.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 32));
        lblAsignacionesValor.setText("—");
        pnlAsignaciones.add(lblAsignacionesValor);
        pnlTarjetas.add(pnlAsignaciones);
        getContentPane().add(pnlTarjetas, java.awt.BorderLayout.CENTER);

        pnlAccesos.setLayout(new java.awt.BorderLayout(0, 14));
        lblAccesos.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblAccesos.setText("Accesos rápidos");
        pnlAccesos.add(lblAccesos, java.awt.BorderLayout.NORTH);
        pnlBotones.setOpaque(false);
        pnlBotones.setLayout(new java.awt.GridLayout(1, 4, 12, 0));
        btnEmpleados.setText("Gestionar empleados");
        btnEmpleados.addActionListener(evt -> btnEmpleadosActionPerformed(evt));
        pnlBotones.add(btnEmpleados);
        btnProyectos.setText("Gestionar proyectos");
        btnProyectos.addActionListener(evt -> btnProyectosActionPerformed(evt));
        pnlBotones.add(btnProyectos);
        btnAsignaciones.setText("Gestionar asignaciones");
        btnAsignaciones.addActionListener(evt -> btnAsignacionesActionPerformed(evt));
        pnlBotones.add(btnAsignaciones);
        btnReportes.setText("Abrir reportería");
        btnReportes.addActionListener(evt -> btnReportesActionPerformed(evt));
        pnlBotones.add(btnReportes);
        pnlAccesos.add(pnlBotones, java.awt.BorderLayout.CENTER);
        lblPresupuesto.setText("Presupuesto acumulado de proyectos: —");
        pnlAccesos.add(lblPresupuesto, java.awt.BorderLayout.SOUTH);
        getContentPane().add(pnlAccesos, java.awt.BorderLayout.SOUTH);
        setSize(new java.awt.Dimension(1050, 590));
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        navegador.accept(new FrmEmpleado());
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProyectosActionPerformed
        navegador.accept(new FrmProyecto());
    }//GEN-LAST:event_btnProyectosActionPerformed

    private void btnAsignacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignacionesActionPerformed
        navegador.accept(new FrmAsignacion());
    }//GEN-LAST:event_btnAsignacionesActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        navegador.accept(new FrmReportes());
    }//GEN-LAST:event_btnReportesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignaciones;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnProyectos;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel lblAccesos;
    private javax.swing.JLabel lblAsignacionesTitulo;
    private javax.swing.JLabel lblAsignacionesValor;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblDepartamentosTitulo;
    private javax.swing.JLabel lblDepartamentosValor;
    private javax.swing.JLabel lblEmpleadosTitulo;
    private javax.swing.JLabel lblEmpleadosValor;
    private javax.swing.JLabel lblPresupuesto;
    private javax.swing.JLabel lblProyectosTitulo;
    private javax.swing.JLabel lblProyectosValor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAccesos;
    private javax.swing.JPanel pnlAsignaciones;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlDepartamentos;
    private javax.swing.JPanel pnlEmpleados;
    private javax.swing.JPanel pnlEncabezado;
    private javax.swing.JPanel pnlProyectos;
    private javax.swing.JPanel pnlTarjetas;
    // End of variables declaration//GEN-END:variables
}
