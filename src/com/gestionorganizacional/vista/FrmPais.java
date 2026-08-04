package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.*;
import com.gestionorganizacional.modelo.*;
import com.gestionorganizacional.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmPais extends javax.swing.JInternalFrame {
    private final PaisController controller = new PaisController();
    private final DefaultTableModel modelo = VistaUtil.modeloTabla("ID", "Nombre", "Código ISO", "Activo");
    private List<Pais> registros = new ArrayList<>();
    private int idSeleccionado;

    public FrmPais() {
        initComponents();
        tblRegistros.setModel(modelo);
        tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        try {
            
            cargarTabla(controller.listar());
            limpiar();
        } catch (Exception ex) {
            VistaUtil.error(this, ex);
        }
    }

    private void cargarTabla(List<Pais> datos) {
        registros = datos;
        modelo.setRowCount(0);
        for (Pais p : datos) {
            modelo.addRow(new Object[]{p.getId(), p.getNombre(), p.getCodigoIso(), p.isActivo() ? "Sí" : "No"});
        }
    }

    private Pais leerFormulario() throws ValidacionException {
        Pais p = new Pais();
        p.setId(idSeleccionado);
        p.setNombre(txtNombre.getText());
        p.setCodigoIso(txtCodigoIso.getText());
        p.setActivo(chkActivo.isSelected());
        return p;
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
        Pais p = registros.get(fila);
        idSeleccionado = p.getId();
        txtId.setText(String.valueOf(p.getId()));
        txtNombre.setText(p.getNombre());
        txtCodigoIso.setText(p.getCodigoIso());
        chkActivo.setSelected(p.isActivo());
    }

    private void limpiar() {
        idSeleccionado = 0;
        tblRegistros.clearSelection();
        txtId.setText(""); txtNombre.setText(""); txtCodigoIso.setText("");
        chkActivo.setSelected(true); txtNombre.requestFocusInWindow();
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlFormulario = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel("ID:"); txtId = new javax.swing.JTextField(); txtId.setEditable(false); lblNombre = new javax.swing.JLabel("Nombre:"); txtNombre = new javax.swing.JTextField(); lblCodigoIso = new javax.swing.JLabel("Código ISO:"); txtCodigoIso = new javax.swing.JTextField(); lblActivo = new javax.swing.JLabel("Estado:"); chkActivo = new javax.swing.JCheckBox("Activo", true);
        scrRegistros = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        pnlAcciones = new javax.swing.JPanel();
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
        setTitle("Mantenimiento de Países");

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        pnlFormulario.setLayout(new java.awt.GridLayout(0, 4, 8, 8));
        pnlFormulario.add(lblId); pnlFormulario.add(txtId); pnlFormulario.add(lblNombre); pnlFormulario.add(txtNombre); pnlFormulario.add(lblCodigoIso); pnlFormulario.add(txtCodigoIso); pnlFormulario.add(lblActivo); pnlFormulario.add(chkActivo);
        getContentPane().add(pnlFormulario, java.awt.BorderLayout.NORTH);

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Nombre", "Código ISO", "Activo"}));
        tblRegistros.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) { tblRegistrosMouseClicked(evt); }
        });
        scrRegistros.setViewportView(tblRegistros);
        getContentPane().add(scrRegistros, java.awt.BorderLayout.CENTER);

        pnlAcciones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 6, 6));
        btnNuevo.addActionListener(evt -> btnNuevoActionPerformed(evt));
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed(evt));
        btnModificar.addActionListener(evt -> btnModificarActionPerformed(evt));
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed(evt));
        btnCancelar.addActionListener(evt -> btnCancelarActionPerformed(evt));
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));
        pnlAcciones.add(btnNuevo); pnlAcciones.add(btnGuardar); pnlAcciones.add(btnModificar);
        pnlAcciones.add(btnEliminar); pnlAcciones.add(btnCancelar); pnlAcciones.add(lblBuscar);
        pnlAcciones.add(txtBuscar); pnlAcciones.add(btnBuscar);
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
    private void tblRegistrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegistrosMouseClicked
        seleccionarFila();
    }//GEN-LAST:event_tblRegistrosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel lblId; private javax.swing.JTextField txtId; private javax.swing.JLabel lblNombre; private javax.swing.JTextField txtNombre; private javax.swing.JLabel lblCodigoIso; private javax.swing.JTextField txtCodigoIso; private javax.swing.JLabel lblActivo; private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrRegistros;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
