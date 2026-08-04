package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.*;
import com.gestionorganizacional.modelo.*;
import com.gestionorganizacional.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class FrmEmpleado extends javax.swing.JInternalFrame {
    private final EmpleadoController controller = new EmpleadoController();
    private final DefaultTableModel modelo = VistaUtil.modeloTabla("ID", "Identidad", "Nombre", "Correo", "Fecha", "Salario", "Departamento", "Cargo", "Activo");
    private List<Empleado> registros = new ArrayList<>();
    private int idSeleccionado;

    public FrmEmpleado() {
        initComponents();
        tblRegistros.setModel(modelo);
        tblRegistros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        try {
            for (Departamento d : new DepartamentoController().listar()) cmbDepartamento.addItem(d);
        for (Cargo c : new CargoController().listar()) cmbCargo.addItem(c);
            cargarTabla(controller.listar());
            limpiar();
        } catch (Exception ex) {
            VistaUtil.error(this, ex);
        }
    }

    private void cargarTabla(List<Empleado> datos) {
        registros = datos;
        modelo.setRowCount(0);
        for (Empleado e : datos) {
            modelo.addRow(new Object[]{e.getId(), e.getIdentidad(), e.getNombreCompleto(), e.getEmail(),
                e.getFechaContratacion(), e.getSalario(), e.getDepartamentoNombre(), e.getCargoNombre(),
                e.isActivo() ? "Sí" : "No"});
        }
    }

    private Empleado leerFormulario() throws ValidacionException {
        Empleado e = new Empleado(); e.setId(idSeleccionado);
        e.setIdentidad(txtIdentidad.getText()); e.setNombres(txtNombres.getText()); e.setApellidos(txtApellidos.getText());
        e.setEmail(txtEmail.getText()); e.setTelefono(txtTelefono.getText());
        e.setFechaContratacion(VistaUtil.fecha(txtFechaContratacion.getText(), "fecha de contratación"));
        e.setSalario(VistaUtil.decimal(txtSalario.getText(), "salario"));
        Departamento d=(Departamento)cmbDepartamento.getSelectedItem(); Cargo c=(Cargo)cmbCargo.getSelectedItem();
        e.setDepartamentoId(d==null?0:d.getId()); e.setCargoId(c==null?0:c.getId());
        e.setActivo(chkActivo.isSelected()); return e;
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
        Empleado e = registros.get(fila);
        idSeleccionado = e.getId();
        txtId.setText(String.valueOf(e.getId())); txtIdentidad.setText(e.getIdentidad());
        txtNombres.setText(e.getNombres()); txtApellidos.setText(e.getApellidos()); txtEmail.setText(e.getEmail());
        txtTelefono.setText(e.getTelefono()); txtFechaContratacion.setText(e.getFechaContratacion().toString());
        txtSalario.setText(String.valueOf(e.getSalario())); seleccionarDepartamento(e.getDepartamentoId());
        seleccionarCargo(e.getCargoId()); chkActivo.setSelected(e.isActivo());
    }

    private void limpiar() {
        idSeleccionado = 0;
        tblRegistros.clearSelection();
        txtId.setText(""); txtIdentidad.setText(""); txtNombres.setText(""); txtApellidos.setText("");
        txtEmail.setText(""); txtTelefono.setText(""); txtFechaContratacion.setText(java.time.LocalDate.now().toString());
        txtSalario.setText("0.00"); chkActivo.setSelected(true);
        if(cmbDepartamento.getItemCount()>0)cmbDepartamento.setSelectedIndex(0);
        if(cmbCargo.getItemCount()>0)cmbCargo.setSelectedIndex(0); txtIdentidad.requestFocusInWindow();
    }

    private void seleccionarDepartamento(int id) {
        for(int i=0;i<cmbDepartamento.getItemCount();i++) if(cmbDepartamento.getItemAt(i).getId()==id){cmbDepartamento.setSelectedIndex(i);return;}
    }
    private void seleccionarCargo(int id) {
        for(int i=0;i<cmbCargo.getItemCount();i++) if(cmbCargo.getItemAt(i).getId()==id){cmbCargo.setSelectedIndex(i);return;}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlFormulario = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel("ID:"); txtId = new javax.swing.JTextField(); txtId.setEditable(false); lblIdentidad = new javax.swing.JLabel("Identidad:"); txtIdentidad = new javax.swing.JTextField(); lblNombres = new javax.swing.JLabel("Nombres:"); txtNombres = new javax.swing.JTextField(); lblApellidos = new javax.swing.JLabel("Apellidos:"); txtApellidos = new javax.swing.JTextField(); lblEmail = new javax.swing.JLabel("Correo:"); txtEmail = new javax.swing.JTextField(); lblTelefono = new javax.swing.JLabel("Teléfono:"); txtTelefono = new javax.swing.JTextField(); lblFechaContratacion = new javax.swing.JLabel("Fecha contratación:"); txtFechaContratacion = new javax.swing.JTextField(); lblSalario = new javax.swing.JLabel("Salario:"); txtSalario = new javax.swing.JTextField(); lblDepartamento = new javax.swing.JLabel("Departamento:"); cmbDepartamento = new javax.swing.JComboBox<>(); lblCargo = new javax.swing.JLabel("Cargo:"); cmbCargo = new javax.swing.JComboBox<>(); lblActivo = new javax.swing.JLabel("Estado:"); chkActivo = new javax.swing.JCheckBox("Activo", true);
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
        setTitle("Mantenimiento de Empleados");

        pnlFormulario.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));
        pnlFormulario.setLayout(new java.awt.GridLayout(0, 4, 8, 8));
        pnlFormulario.add(lblId); pnlFormulario.add(txtId); pnlFormulario.add(lblIdentidad); pnlFormulario.add(txtIdentidad); pnlFormulario.add(lblNombres); pnlFormulario.add(txtNombres); pnlFormulario.add(lblApellidos); pnlFormulario.add(txtApellidos); pnlFormulario.add(lblEmail); pnlFormulario.add(txtEmail); pnlFormulario.add(lblTelefono); pnlFormulario.add(txtTelefono); pnlFormulario.add(lblFechaContratacion); pnlFormulario.add(txtFechaContratacion); pnlFormulario.add(lblSalario); pnlFormulario.add(txtSalario); pnlFormulario.add(lblDepartamento); pnlFormulario.add(cmbDepartamento); pnlFormulario.add(lblCargo); pnlFormulario.add(cmbCargo); pnlFormulario.add(lblActivo); pnlFormulario.add(chkActivo);
        getContentPane().add(pnlFormulario, java.awt.BorderLayout.NORTH);

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Identidad", "Nombre", "Correo", "Fecha", "Salario", "Departamento", "Cargo", "Activo"}));
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
    private javax.swing.JLabel lblId; private javax.swing.JTextField txtId; private javax.swing.JLabel lblIdentidad; private javax.swing.JTextField txtIdentidad; private javax.swing.JLabel lblNombres; private javax.swing.JTextField txtNombres; private javax.swing.JLabel lblApellidos; private javax.swing.JTextField txtApellidos; private javax.swing.JLabel lblEmail; private javax.swing.JTextField txtEmail; private javax.swing.JLabel lblTelefono; private javax.swing.JTextField txtTelefono; private javax.swing.JLabel lblFechaContratacion; private javax.swing.JTextField txtFechaContratacion; private javax.swing.JLabel lblSalario; private javax.swing.JTextField txtSalario; private javax.swing.JLabel lblDepartamento; private javax.swing.JComboBox<Departamento> cmbDepartamento; private javax.swing.JLabel lblCargo; private javax.swing.JComboBox<Cargo> cmbCargo; private javax.swing.JLabel lblActivo; private javax.swing.JCheckBox chkActivo;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JPanel pnlAcciones;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JScrollPane scrRegistros;
    private javax.swing.JTable tblRegistros;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
