package com.gestionorganizacional.dao;

import com.gestionorganizacional.conexion.ConexionDB;
import com.gestionorganizacional.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UsuarioDAO {
    public Usuario buscarPorNombreUsuario(String nombreUsuario) throws SQLException {
        String sql = "SELECT id,nombre_usuario,nombre_completo,clave_hash,clave_salt,"
                + "iteraciones,rol,activo,ultimo_acceso FROM usuario "
                + "WHERE nombre_usuario = ?";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setClaveHash(rs.getString("clave_hash"));
                usuario.setClaveSalt(rs.getString("clave_salt"));
                usuario.setIteraciones(rs.getInt("iteraciones"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
                java.sql.Timestamp acceso = rs.getTimestamp("ultimo_acceso");
                if (acceso != null) usuario.setUltimoAcceso(acceso.toLocalDateTime());
                return usuario;
            }
        }
    }

    public void registrarUltimoAcceso(int usuarioId, LocalDateTime fecha) throws SQLException {
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE usuario SET ultimo_acceso=? WHERE id=?")) {
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(fecha));
            ps.setInt(2, usuarioId);
            ps.executeUpdate();
        }
    }
}
