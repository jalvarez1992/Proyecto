package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.*;
import com.gestionorganizacional.modelo.*;
import com.gestionorganizacional.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmProyecto extends javax.swing.JInternalFrame {
    private final ProyectoController controller = new ProyectoController();
    private final DefaultTableModel modelo = VistaUtil.modeloTabla("ID", "Nombre", "Descripción", "Inicio", "Fin", "Presupuesto", "Estado");
    private List<Proyecto> registros = new ArrayList<>();
    private int idSeleccionado;

    public FrmProyecto() {
        initComponents();
        tblRegistros.setModel(modelo);
        tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        try {
            for (EstadoProyecto estado : EstadoProyecto.values()) cmbEstado.addItem(estado);
            cargarTabla(controller.listar());
            limpiar();
        } catch (Exception ex) {
            VistaUtil.error(this, ex);
        }
    }

    private void cargarTabla(List<Proyecto> datos) {
        registros = datos;
        modelo.setRowCount(0);
        for (Proyecto p : datos) {
            modelo.addRow(new Object[]{p.getId(),p.getNombre(),p.getDescripcion(),p.getFechaInicio(),
                p.getFechaFin(),p.getPresupuesto(),p.getEstado()});
        }
    }

    private Proyecto leerFormulario() throws ValidacionException {
        Proyecto p=new Proyecto(); p.setId(idSeleccionado); p.setNombre(txtNombre.getText());
        p.setDescripcion(txtDescripcion.getText()); p.setFechaInicio(VistaUtil.fecha(txtFechaInicio.getText(), "fecha de inicio"));
        p.setFechaFin(VistaUtil.fechaOpcional(txtFechaFin.getText(), "fecha final"));
        p.setPresupuesto(VistaUtil.decimal(txtPresupuesto.getText(), "presupuesto"));
        p.setEstado((EstadoProyecto)cmbEstado.getSelectedItem()); return p;
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
        Proyecto p = registros.get(fila);
        idSeleccionado = p.getId();
        txtId.setText(String.valueOf(p.getId())); txtNombre.setText(p.getNombre());
        txtDescripcion.setText(p.getDescripcion()); txtFechaInicio.setText(p.getFechaInicio().toString());
        txtFechaFin.setText(p.getFechaFin()==null?"":p.getFechaFin().toString());
        txtPresupuesto.setText(String.valueOf(p.getPresupuesto())); cmbEstado.setSelectedItem(p.getEstado());
    }

    private void limpiar() {
        idSeleccionado = 0;
        tblRegistros.clearSelection();
        txtId.setText(""); txtNombre.setText(""); txtDescripcion.setText("");
        txtFechaInicio.setText(java.time.LocalDate.now().toString()); txtFechaFin.setText("");
        txtPresupuesto.setText("0.00"); cmbEstado.setSelectedItem(EstadoProyecto.PLANIFICADO);
        txtNombre.requestFocusInWindow();
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlFormulario = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel("ID:"); txtId = new javax.swing.JTextField(); txtId.setEditable(false); lblNombre = new javax.swing.JLabel("Nombre:"); txtNombre = new javax.swing.JTextField(); lblDescripcion = new javax.swing.JLabel("Descripción:"); txtDescripcion = new javax.swing.JTextField(); lblFechaInicio = new javax.swing.JLabel("Fecha inicio:"); txtFechaInicio = new javax.swing.JTextField(); lblFechaFin = new javax.swing.JLabel("Fecha fin (opcional):"); txtFechaFin = new javax.swing.JTextField(); lblPresupuesto = new javax.swing.JLabel("Presupuesto:"); txtPresupuesto = new javax.swing.JTextField(); lblEstado = new javax.swing.JLabel("Estado:"); cmbEstado = new javax.swing.JComboBox<>();
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
        setTitle("Mantenimiento de Proyectos");

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        pnlFormulario.setLayout(new java.awt.GridLayout(0, 4, 8, 8));
        pnlFormulario.add(lblId); pnlFormulario.add(txtId); pnlFormulario.add(lblNombre); pnlFormulario.add(txtNombre); pnlFormulario.add(lblDescripcion); pnlFormulario.add(txtDescripcion); pnlFormulario.add(lblFechaInicio); pnlFormulario.add(txtFechaInicio); pnlFormulario.add(lblFechaFin); pnlFormulario.add(txtFechaFin); pnlFormulario.add(lblPresupuesto); pnlFormulario.add(txtPresupuesto); pnlFormulario.add(lblEstado); pnlFormulario.add(cmbEstado);
        getContentPane().add(pnlFormulario, java.awt.BorderLayout.NORTH);

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Nombre", "Descripción", "Inicio", "Fin", "Presupuesto", "Estado"}));
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
    private javax.swing.JLabel lblId; private javax.swing.JTextField txtId; private javax.swing.JLabel lblNombre; private javax.swing.JTextField txtNombre; private javax.swing.JLabel lblDescripcion; private javax.swing.JTextField txtDescripcion; private javax.swing.JLabel lblFechaInicio; private javax.swing.JTextField txtFechaInicio; private javax.swing.JLabel lblFechaFin; private javax.swing.JTextField txtFechaFin; private javax.swing.JLabel lblPresupuesto; private javax.swing.JTextField txtPresupuesto; private javax.swing.JLabel lblEstado; private javax.swing.JComboBox<EstadoProyecto> cmbEstado;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrRegistros;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
