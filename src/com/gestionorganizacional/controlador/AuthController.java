package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.UsuarioDAO;
import com.gestionorganizacional.modelo.Usuario;
import com.gestionorganizacional.util.PasswordUtil;
import com.gestionorganizacional.util.PersistenciaException;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AuthController {
    private static final String SALT_SIMULADO = "nyOhxdAuS3eIyasQ70NSZw==";
    private static final String HASH_SIMULADO = "mW/4zievKvMth5o5gACOeOHU9IC89BmlqZVYcvKhxs4=";
    private static final int ITERACIONES_SIMULADAS = 210000;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String nombreUsuario, char[] clave)
            throws ValidacionException, PersistenciaException {
        String usuarioLimpio = Validaciones.requerido(nombreUsuario, "usuario");
        if (clave == null || clave.length == 0) {
            throw new ValidacionException("Ingrese su contraseña.");
        }
        try {
            Usuario usuario = usuarioDAO.buscarPorNombreUsuario(usuarioLimpio);
            String salt = usuario == null ? SALT_SIMULADO : usuario.getClaveSalt();
            String hash = usuario == null ? HASH_SIMULADO : usuario.getClaveHash();
            int iteraciones = usuario == null ? ITERACIONES_SIMULADAS : usuario.getIteraciones();
            boolean claveValida = PasswordUtil.verificar(clave, salt, hash, iteraciones);
            if (usuario == null || !usuario.isActivo() || !claveValida) {
                throw new ValidacionException("Usuario o contraseña incorrectos.");
            }
            LocalDateTime ahora = LocalDateTime.now();
            usuarioDAO.registrarUltimoAcceso(usuario.getId(), ahora);
            usuario.setUltimoAcceso(ahora);
            usuario.limpiarCredenciales();
            return usuario;
        } catch (GeneralSecurityException ex) {
            throw new PersistenciaException("No se pudo verificar la contraseña.", ex);
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar el usuario.", ex);
        }
    }
}
