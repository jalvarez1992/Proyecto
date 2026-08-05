package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.ReporteController;
import com.gestionorganizacional.controlador.ReporteController.TipoReporte;
import com.gestionorganizacional.modelo.ReporteData;
import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.util.ExportacionUtil;
import com.gestionorganizacional.util.ExportadorCSV;
import com.gestionorganizacional.util.ExportadorPDF;
import com.gestionorganizacional.util.IconosUI;
import java.nio.file.Path;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class FrmReportes extends javax.swing.JInternalFrame {
    private final ReporteController controller = new ReporteController();
    private ReporteData reporteCompleto;
    private ReporteData reporteVisible;

    public FrmReportes() {
        initComponents();
        cmbTipoReporte.setModel(new DefaultComboBoxModel<>(TipoReporte.values()));
        EstilosUI.configurarTabla(tblReporte, scrReporte);
        EstilosUI.aplicarControles(pnlFiltros);
        EstilosUI.configurarBoton(btnGenerar, IconosUI.Tipo.ACTUALIZAR, EstilosUI.VarianteBoton.PRIMARIO);
        EstilosUI.configurarBoton(btnFiltrar, IconosUI.Tipo.BUSCAR, EstilosUI.VarianteBoton.FANTASMA);
        EstilosUI.configurarBoton(btnPdf, IconosUI.Tipo.PDF, EstilosUI.VarianteBoton.SECUNDARIO);
        EstilosUI.configurarBoton(btnCsv, IconosUI.Tipo.CSV, EstilosUI.VarianteBoton.SECUNDARIO);
        getContentPane().setBackground(EstilosUI.FONDO);
        pnlCabecera.setBackground(java.awt.Color.WHITE);
        pnlCabecera.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, EstilosUI.BORDE),
                javax.swing.BorderFactory.createEmptyBorder(16, 18, 14, 18)));
        pnlFiltros.setOpaque(false);
        pnlPie.setBackground(java.awt.Color.WHITE);
        pnlPie.setBorder(javax.swing.BorderFactory.createEmptyBorder(9, 14, 9, 14));
        lblTitulo.setForeground(EstilosUI.TEXTO);
        lblSubtitulo.setForeground(EstilosUI.TEXTO_SUAVE);
        lblResumen.setForeground(EstilosUI.TEXTO_SUAVE);
        generarReporte();
    }

    private void generarReporte() {
        TipoReporte tipo = (TipoReporte) cmbTipoReporte.getSelectedItem();
        cambiarEstado(true, "Generando reporte...");
        new SwingWorker<ReporteData, Void>() {
            private Exception error;
            @Override protected ReporteData doInBackground() {
                try { return controller.generar(tipo); }
                catch (Exception ex) { error = ex; return null; }
            }
            @Override protected void done() {
                cambiarEstado(false, "");
                if (error != null) {
                    JOptionPane.showMessageDialog(FrmReportes.this,
                            "No se pudo generar el reporte:\n" + error.getMessage(),
                            "Reportería", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    reporteCompleto = get();
                    aplicarFiltro();
                    lblTitulo.setText(reporteCompleto.getTitulo());
                    lblSubtitulo.setText(reporteCompleto.getSubtitulo());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FrmReportes.this, ex.getMessage(),
                            "Reportería", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void aplicarFiltro() {
        if (reporteCompleto == null) return;
        reporteVisible = reporteCompleto.filtrar(txtFiltro.getText());
        DefaultTableModel modelo = new DefaultTableModel(reporteVisible.getColumnas().toArray(), 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        for (var fila : reporteVisible.getFilas()) modelo.addRow(fila.toArray());
        tblReporte.setModel(modelo);
        lblResumen.setText(reporteVisible.getTotalFilas() + " registro(s) mostrados de "
                + reporteCompleto.getTotalFilas());
        btnPdf.setEnabled(true);
        btnCsv.setEnabled(true);
    }

    private void exportarPdf() {
        if (reporteVisible == null) return;
        try {
            Path archivo = ExportacionUtil.seleccionarArchivo(this, reporteVisible.getTitulo(), "pdf", "Documento PDF");
            if (archivo == null) return;
            ExportadorPDF.exportar(archivo, reporteVisible);
            ExportacionUtil.validarArchivoGenerado(archivo);
            mostrarExportacion(archivo);
        } catch (Exception ex) { mostrarErrorExportacion(ex); }
    }

    private void exportarCsv() {
        if (reporteVisible == null) return;
        try {
            Path archivo = ExportacionUtil.seleccionarArchivo(this, reporteVisible.getTitulo(), "csv", "Archivo CSV");
            if (archivo == null) return;
            ExportadorCSV.exportar(archivo, reporteVisible);
            ExportacionUtil.validarArchivoGenerado(archivo);
            mostrarExportacion(archivo);
        } catch (Exception ex) { mostrarErrorExportacion(ex); }
    }

    private void cambiarEstado(boolean ocupado, String texto) {
        btnGenerar.setEnabled(!ocupado);
        btnFiltrar.setEnabled(!ocupado);
        btnPdf.setEnabled(!ocupado && reporteVisible != null);
        btnCsv.setEnabled(!ocupado && reporteVisible != null);
        if (!texto.isEmpty()) lblResumen.setText(texto);
    }

    private void mostrarExportacion(Path archivo) {
        JOptionPane.showMessageDialog(this, "Archivo generado correctamente:\n" + archivo.toAbsolutePath(),
                "Exportación completada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarErrorExportacion(Exception ex) {
        JOptionPane.showMessageDialog(this, "No se pudo exportar el reporte:\n" + ex.getMessage(),
                "Error de exportación", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlCabecera = new javax.swing.JPanel();
        pnlTitulos = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        pnlFiltros = new javax.swing.JPanel();
        lblTipo = new javax.swing.JLabel();
        cmbTipoReporte = new javax.swing.JComboBox<>();
        btnGenerar = new javax.swing.JButton();
        lblFiltro = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        btnFiltrar = new javax.swing.JButton();
        btnPdf = new javax.swing.JButton();
        btnCsv = new javax.swing.JButton();
        scrReporte = new javax.swing.JScrollPane();
        tblReporte = new javax.swing.JTable();
        pnlPie = new javax.swing.JPanel();
        lblResumen = new javax.swing.JLabel();
        pnlExportacion = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Centro de Reportería");
        setMinimumSize(new java.awt.Dimension(900, 600));

        pnlCabecera.setLayout(new java.awt.BorderLayout(0, 12));
        pnlTitulos.setOpaque(false);
        pnlTitulos.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        lblTitulo.setText("Centro de Reportería");
        pnlTitulos.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        lblSubtitulo.setText("Genere, filtre y exporte información del sistema");
        pnlTitulos.add(lblSubtitulo);
        pnlCabecera.add(pnlTitulos, java.awt.BorderLayout.CENTER);

        pnlFiltros.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
        lblTipo.setText("Reporte:");
        pnlFiltros.add(lblTipo);
        cmbTipoReporte.setPreferredSize(new java.awt.Dimension(210, 38));
        pnlFiltros.add(cmbTipoReporte);
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(evt -> btnGenerarActionPerformed(evt));
        pnlFiltros.add(btnGenerar);
        lblFiltro.setText("Buscar:");
        pnlFiltros.add(lblFiltro);
        txtFiltro.setColumns(18);
        txtFiltro.addActionListener(evt -> txtFiltroActionPerformed(evt));
        pnlFiltros.add(txtFiltro);
        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(evt -> btnFiltrarActionPerformed(evt));
        pnlFiltros.add(btnFiltrar);
        pnlCabecera.add(pnlFiltros, java.awt.BorderLayout.SOUTH);
        getContentPane().add(pnlCabecera, java.awt.BorderLayout.NORTH);

        tblReporte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, new String [] {"Reporte"}
        ) { public boolean isCellEditable(int rowIndex, int columnIndex) { return false; } });
        scrReporte.setViewportView(tblReporte);
        getContentPane().add(scrReporte, java.awt.BorderLayout.CENTER);

        pnlPie.setLayout(new java.awt.BorderLayout(12, 0));
        lblResumen.setText("Seleccione un reporte para comenzar");
        pnlPie.add(lblResumen, java.awt.BorderLayout.WEST);
        pnlExportacion.setOpaque(false);
        pnlExportacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        btnPdf.setText("Exportar PDF");
        btnPdf.setEnabled(false);
        btnPdf.addActionListener(evt -> btnPdfActionPerformed(evt));
        pnlExportacion.add(btnPdf);
        btnCsv.setText("Exportar CSV");
        btnCsv.setEnabled(false);
        btnCsv.addActionListener(evt -> btnCsvActionPerformed(evt));
        pnlExportacion.add(btnCsv);
        pnlPie.add(pnlExportacion, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlPie, java.awt.BorderLayout.SOUTH);
        setSize(new java.awt.Dimension(1120, 680));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        generarReporte();
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        aplicarFiltro();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void txtFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroActionPerformed
        aplicarFiltro();
    }//GEN-LAST:event_txtFiltroActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        exportarPdf();
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCsvActionPerformed
        exportarCsv();
    }//GEN-LAST:event_btnCsvActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCsv;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnPdf;
    private javax.swing.JComboBox<TipoReporte> cmbTipoReporte;
    private javax.swing.JLabel lblFiltro;
    private javax.swing.JLabel lblResumen;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlCabecera;
    private javax.swing.JPanel pnlFiltros;
    private javax.swing.JPanel pnlExportacion;
    private javax.swing.JPanel pnlPie;
    private javax.swing.JPanel pnlTitulos;
    private javax.swing.JScrollPane scrReporte;
    private javax.swing.JTable tblReporte;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
