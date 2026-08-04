package com.gestionorganizacional.vista;

import com.gestionorganizacional.controlador.AuthController;
import com.gestionorganizacional.modelo.Usuario;
import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.util.Sesion;
import java.awt.Font;
import java.util.Arrays;
import javax.swing.SwingWorker;

public class FrmLogin extends javax.swing.JFrame {
    private final AuthController authController = new AuthController();
    private final char caracterOculto;

    public FrmLogin() {
        initComponents();
        caracterOculto = pwdClave.getEchoChar();
        aplicarEstilos();
        lblAyuda.setText("");
        getRootPane().setDefaultButton(btnIngresar);
        setLocationRelativeTo(null);
        txtUsuario.requestFocusInWindow();
    }

    private void aplicarEstilos() {
        getContentPane().setBackground(EstilosUI.FONDO);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(EstilosUI.TEXTO);
        lblSubtitulo.setForeground(EstilosUI.TEXTO_SUAVE);
        lblUsuario.setForeground(EstilosUI.TEXTO);
        lblClave.setForeground(EstilosUI.TEXTO);
        lblEstado.setForeground(EstilosUI.ERROR);
        //lblAyuda.setForeground(EstilosUI.TEXTO_SUAVE);
        EstilosUI.configurarCampo(txtUsuario);
        EstilosUI.configurarCampo(pwdClave);
        EstilosUI.configurarBotonPrimario(btnIngresar);
        txtUsuario.getAccessibleContext().setAccessibleName("Nombre de usuario");
        pwdClave.getAccessibleContext().setAccessibleName("Contraseña");
        btnIngresar.getAccessibleContext().setAccessibleDescription(
                "Valida las credenciales e ingresa al sistema");
    }

    private void ingresar() {
        lblEstado.setText(" ");
        btnIngresar.setEnabled(false);
        btnIngresar.setText("Verificando...");
        final String nombreUsuario = txtUsuario.getText();
        final char[] clave = pwdClave.getPassword();

        new SwingWorker<Usuario, Void>() {
            private Exception error;

            @Override
            protected Usuario doInBackground() {
                try {
                    return authController.autenticar(nombreUsuario, clave);
                } catch (Exception ex) {
                    error = ex;
                    return null;
                } finally {
                    Arrays.fill(clave, '\0');
                }
            }

            @Override
            protected void done() {
                btnIngresar.setEnabled(true);
                btnIngresar.setText("Ingresar al sistema");
                try {
                    Usuario usuario = get();
                    if (usuario == null) {
                        lblEstado.setText(error == null
                                ? "No se pudo iniciar sesión." : error.getMessage());
                        pwdClave.setText("");
                        pwdClave.requestFocusInWindow();
                        return;
                    }
                    Sesion.iniciar(usuario);
                    dispose();
                    new FrmPrincipal().setVisible(true);
                } catch (Exception ex) {
                    lblEstado.setText("No se pudo iniciar sesión.");
                }
            }
        }.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        pnlMarca = new com.gestionorganizacional.util.PanelDegradado();
        lblLogo = new javax.swing.JLabel();
        lblMarcaTitulo = new javax.swing.JLabel();
        lblMarcaDescripcion = new javax.swing.JLabel();
        lblCaracteristica1 = new javax.swing.JLabel();
        lblCaracteristica2 = new javax.swing.JLabel();
        lblCaracteristica3 = new javax.swing.JLabel();
        pnlFormulario = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblClave = new javax.swing.JLabel();
        pwdClave = new javax.swing.JPasswordField();
        chkMostrarClave = new javax.swing.JCheckBox();
        lblEstado = new javax.swing.JLabel();
        btnIngresar = new javax.swing.JButton();
        lblAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar sesión · Sistema de Gestión Organizacional");
        setMinimumSize(new java.awt.Dimension(920, 560));
        setResizable(false);

        pnlMarca.setBorder(javax.swing.BorderFactory.createEmptyBorder(58, 44, 58, 44));
        pnlMarca.setPreferredSize(new java.awt.Dimension(370, 560));
        pnlMarca.setLayout(new java.awt.GridLayout(0, 1, 0, 10));

        lblLogo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 56));
        lblLogo.setForeground(java.awt.Color.WHITE);
        lblLogo.setText("SGO");
        pnlMarca.add(lblLogo);

        lblMarcaTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 27));
        lblMarcaTitulo.setForeground(java.awt.Color.WHITE);
        lblMarcaTitulo.setText("Gestión Organizacional");
        pnlMarca.add(lblMarcaTitulo);

        lblMarcaDescripcion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 15));
        lblMarcaDescripcion.setForeground(new java.awt.Color(219, 234, 254));
        lblMarcaDescripcion.setText("<html>Administre personas, proyectos y la estructura de su empresa desde un solo lugar.</html>");
        pnlMarca.add(lblMarcaDescripcion);

        lblCaracteristica1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblCaracteristica1.setForeground(java.awt.Color.WHITE);
        lblCaracteristica1.setText("✓ Información centralizada");
        pnlMarca.add(lblCaracteristica1);

        lblCaracteristica2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblCaracteristica2.setForeground(java.awt.Color.WHITE);
        lblCaracteristica2.setText("✓ Acceso seguro");
        pnlMarca.add(lblCaracteristica2);

        lblCaracteristica3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblCaracteristica3.setForeground(java.awt.Color.WHITE);
        lblCaracteristica3.setText("✓ Operación simple y confiable");
        pnlMarca.add(lblCaracteristica3);
        getContentPane().add(pnlMarca, java.awt.BorderLayout.WEST);

        pnlFormulario.setBackground(java.awt.Color.WHITE);
        pnlFormulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(55, 78, 48, 78));
        pnlFormulario.setLayout(new java.awt.GridLayout(0, 1, 0, 8));

        lblTitulo.setText("Bienvenido");
        pnlFormulario.add(lblTitulo);

        lblSubtitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblSubtitulo.setText("Ingrese sus credenciales para continuar");
        pnlFormulario.add(lblSubtitulo);

        lblUsuario.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblUsuario.setText("Usuario");
        pnlFormulario.add(lblUsuario);

        txtUsuario.setText("admin");
        pnlFormulario.add(txtUsuario);

        lblClave.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        lblClave.setText("Contraseña");
        pnlFormulario.add(lblClave);
        pnlFormulario.add(pwdClave);

        chkMostrarClave.setBackground(java.awt.Color.WHITE);
        chkMostrarClave.setText("Mostrar contraseña");
        chkMostrarClave.addActionListener(evt -> chkMostrarClaveActionPerformed(evt));
        pnlFormulario.add(chkMostrarClave);

        lblEstado.setText(" ");
        pnlFormulario.add(lblEstado);

        btnIngresar.setText("Ingresar al sistema");
        btnIngresar.addActionListener(evt -> btnIngresarActionPerformed(evt));
        pnlFormulario.add(btnIngresar);

        lblAyuda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAyuda.setText("<html><center>Acceso inicial: <b>admin</b> / <b>Admin123*</b></center></html>");
        pnlFormulario.add(lblAyuda);

        getContentPane().add(pnlFormulario, java.awt.BorderLayout.CENTER);
        setSize(new java.awt.Dimension(920, 560));
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        ingresar();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void chkMostrarClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMostrarClaveActionPerformed
        pwdClave.setEchoChar(chkMostrarClave.isSelected() ? (char) 0 : caracterOculto);
    }//GEN-LAST:event_chkMostrarClaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JCheckBox chkMostrarClave;
    private javax.swing.JLabel lblAyuda;
    private javax.swing.JLabel lblCaracteristica1;
    private javax.swing.JLabel lblCaracteristica2;
    private javax.swing.JLabel lblCaracteristica3;
    private javax.swing.JLabel lblClave;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMarcaDescripcion;
    private javax.swing.JLabel lblMarcaTitulo;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlFormulario;
    private com.gestionorganizacional.util.PanelDegradado pnlMarca;
    private javax.swing.JPasswordField pwdClave;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
