package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.*;
import com.gestionorganizacional.modelo.*;
import com.gestionorganizacional.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmCargo extends javax.swing.JInternalFrame {
    private final CargoController controller = new CargoController();
    private final DefaultTableModel modelo = VistaUtil.modeloTabla("ID", "Nombre", "Descripción", "Salario mínimo", "Salario máximo", "Activo");
    private List<Cargo> registros = new ArrayList<>();
    private int idSeleccionado;

    public FrmCargo() {
        initComponents();
        tblRegistros.setModel(modelo);
        tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        EstilosUI.aplicarFormularioMantenimiento(this, pnlFormulario, pnlAcciones, tblRegistros, scrRegistros);
        pnlCentro.setOpaque(false);
        pnlCentro.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlExportacion.setOpaque(false);
        EstilosUI.aplicarControles(pnlExportacion);
        EstilosUI.configurarBoton(btnPdf, IconosUI.Tipo.PDF, EstilosUI.VarianteBoton.SECUNDARIO);
        EstilosUI.configurarBoton(btnCsv, IconosUI.Tipo.CSV, EstilosUI.VarianteBoton.SECUNDARIO);
        try {
            
            cargarTabla(controller.listar());
            limpiar();
        } catch (Exception ex) {
            VistaUtil.error(this, ex);
        }
    }

    private void cargarTabla(List<Cargo> datos) {
        registros = datos;
        modelo.setRowCount(0);
        for (Cargo c : datos) {
            modelo.addRow(new Object[]{c.getId(), c.getNombre(), c.getDescripcion(), c.getSalarioMinimo(), c.getSalarioMaximo(), c.isActivo() ? "Sí" : "No"});
        }
    }

    private Cargo leerFormulario() throws ValidacionException {
        Cargo c = new Cargo();
        c.setId(idSeleccionado); c.setNombre(txtNombre.getText()); c.setDescripcion(txtDescripcion.getText());
        c.setSalarioMinimo(VistaUtil.decimal(txtSalarioMinimo.getText(), "salario mínimo"));
        c.setSalarioMaximo(VistaUtil.decimal(txtSalarioMaximo.getText(), "salario máximo"));
        c.setActivo(chkActivo.isSelected()); return c;
    }

    private void guardar() {
        try {
            idSeleccionado = 0;
            controller.guardar(leerFormulario());
            cargarTabla(controller.listar()); limpiar();
            VistaUtil.exito(this, "Registro guardado correctamente.");
        } catch (Exception ex) { VistaUtil.error(this, ex); }
    }

    private void modificar() {
        try {
            controller.modificar(leerFormulario());
            cargarTabla(controller.listar()); limpiar();
            VistaUtil.exito(this, "Registro modificado correctamente.");
        } catch (Exception ex) { VistaUtil.error(this, ex); }
    }

    private void eliminar() {
        try {
            if (idSeleccionado <= 0) throw new ValidacionException("Seleccione un registro para eliminar.");
            if (VistaUtil.confirmarEliminar(this)) {
                controller.eliminar(idSeleccionado);
                cargarTabla(controller.listar()); limpiar();
                VistaUtil.exito(this, "Registro eliminado correctamente.");
            }
        } catch (Exception ex) { VistaUtil.error(this, ex); }
    }

    private void buscar() {
        try { cargarTabla(controller.buscar(txtBuscar.getText())); }
        catch (Exception ex) { VistaUtil.error(this, ex); }
    }

    private void seleccionarFila() {
        int fila = tblRegistros.getSelectedRow();
        if (fila < 0 || fila >= registros.size()) return;
        Cargo c = registros.get(fila);
        idSeleccionado = c.getId();
        txtId.setText(String.valueOf(c.getId())); txtNombre.setText(c.getNombre());
        txtDescripcion.setText(c.getDescripcion()); txtSalarioMinimo.setText(String.valueOf(c.getSalarioMinimo()));
        txtSalarioMaximo.setText(String.valueOf(c.getSalarioMaximo())); chkActivo.setSelected(c.isActivo());
    }

    private void limpiar() {
        idSeleccionado = 0;
        tblRegistros.clearSelection();
        txtId.setText(""); txtNombre.setText(""); txtDescripcion.setText("");
        txtSalarioMinimo.setText("0.00"); txtSalarioMaximo.setText("0.00");
        chkActivo.setSelected(true); txtNombre.requestFocusInWindow();
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlFormulario = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel("ID:"); txtId = new javax.swing.JTextField(); txtId.setEditable(false); lblNombre = new javax.swing.JLabel("Nombre:"); txtNombre = new javax.swing.JTextField(); lblDescripcion = new javax.swing.JLabel("Descripción:"); txtDescripcion = new javax.swing.JTextField(); lblSalarioMinimo = new javax.swing.JLabel("Salario mínimo:"); txtSalarioMinimo = new javax.swing.JTextField(); lblSalarioMaximo = new javax.swing.JLabel("Salario máximo:"); txtSalarioMaximo = new javax.swing.JTextField(); lblActivo = new javax.swing.JLabel("Estado:"); chkActivo = new javax.swing.JCheckBox("Activo", true);
        pnlCentro = new javax.swing.JPanel();
        pnlExportacion = new javax.swing.JPanel();
        lblExportar = new javax.swing.JLabel("Exportar datos:");
        btnPdf = new javax.swing.JButton("Exportar PDF");
        btnCsv = new javax.swing.JButton("Exportar CSV");
        scrRegistros = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        pnlAcciones = new javax.swing.JPanel();
        pnlCrud = new javax.swing.JPanel();
        pnlBusqueda = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton("Nuevo");
        btnGuardar = new javax.swing.JButton("Guardar");
        btnModificar = new javax.swing.JButton("Modificar");
        btnEliminar = new javax.swing.JButton("Eliminar");
        btnCancelar = new javax.swing.JButton("Cancelar");
        lblBuscar = new javax.swing.JLabel("Buscar:");
        txtBuscar = new javax.swing.JTextField(18);
        btnBuscar = new javax.swing.JButton("Buscar");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Mantenimiento de Cargos");

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        pnlFormulario.setLayout(new java.awt.GridLayout(0, 4, 8, 8));
        pnlFormulario.add(lblId); pnlFormulario.add(txtId); pnlFormulario.add(lblNombre); pnlFormulario.add(txtNombre); pnlFormulario.add(lblDescripcion); pnlFormulario.add(txtDescripcion); pnlFormulario.add(lblSalarioMinimo); pnlFormulario.add(txtSalarioMinimo); pnlFormulario.add(lblSalarioMaximo); pnlFormulario.add(txtSalarioMaximo); pnlFormulario.add(lblActivo); pnlFormulario.add(chkActivo);
        getContentPane().add(pnlFormulario, java.awt.BorderLayout.NORTH);

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Nombre", "Descripción", "Salario mínimo", "Salario máximo", "Activo"}));
        tblRegistros.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) { tblRegistrosMouseClicked(evt); }
        });
        scrRegistros.setViewportView(tblRegistros);

        pnlCentro.setLayout(new java.awt.BorderLayout(0, 8));
        pnlExportacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        pnlExportacion.add(lblExportar);
        btnPdf.addActionListener(evt -> btnPdfActionPerformed(evt));
        pnlExportacion.add(btnPdf);
        btnCsv.addActionListener(evt -> btnCsvActionPerformed(evt));
        pnlExportacion.add(btnCsv);
        pnlCentro.add(pnlExportacion, java.awt.BorderLayout.NORTH);
        pnlCentro.add(scrRegistros, java.awt.BorderLayout.CENTER);
        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pnlAcciones.setLayout(new java.awt.BorderLayout(10, 0));
        pnlCrud.setOpaque(false);
        pnlCrud.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 0));
        btnNuevo.addActionListener(evt -> btnNuevoActionPerformed(evt));
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));
        btnModificar.addActionListener(evt -> btnModificarActionPerformed(evt));
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        pnlCrud.add(btnNuevo);
        pnlCrud.add(btnGuardar);
        pnlCrud.add(btnModificar);
        pnlCrud.add(btnEliminar);
        pnlCrud.add(btnCancelar);
        pnlAcciones.add(pnlCrud, java.awt.BorderLayout.WEST);

        pnlBusqueda.setOpaque(false);
        pnlBusqueda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 0));
        pnlBusqueda.add(lblBuscar);
        txtBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        pnlBusqueda.add(txtBuscar);
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        pnlBusqueda.add(btnBuscar);
        pnlAcciones.add(pnlBusqueda, java.awt.BorderLayout.EAST);
        getContentPane().add(pnlAcciones, java.awt.BorderLayout.SOUTH);
        setSize(980, 620);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiar();
    }//GEN-LAST:event_btnNuevoActionPerformed
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        modificar();
    }//GEN-LAST:event_btnModificarActionPerformed
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        ExportacionUtil.exportarPdf(this, tblRegistros, getTitle());
    }//GEN-LAST:event_btnPdfActionPerformed
    private void btnCsvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCsvActionPerformed
        ExportacionUtil.exportarCsv(this, tblRegistros, getTitle());
    }//GEN-LAST:event_btnCsvActionPerformed
    private void tblRegistrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegistrosMouseClicked
        seleccionarFila();
    }//GEN-LAST:event_tblRegistrosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCsv;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel lblId; private javax.swing.JTextField txtId; private javax.swing.JLabel lblNombre; private javax.swing.JTextField txtNombre; private javax.swing.JLabel lblDescripcion; private javax.swing.JTextField txtDescripcion; private javax.swing.JLabel lblSalarioMinimo; private javax.swing.JTextField txtSalarioMinimo; private javax.swing.JLabel lblSalarioMaximo; private javax.swing.JTextField txtSalarioMaximo; private javax.swing.JLabel lblActivo; private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblExportar;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlBusqueda;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlCrud;
    private javax.swing.JPanel pnlExportacion;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrRegistros;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
