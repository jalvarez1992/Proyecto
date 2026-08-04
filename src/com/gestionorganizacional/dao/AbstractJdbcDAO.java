package com.gestionorganizacional.dao;

import com.gestionorganizacional.conexion.ConexionDB;
import com.gestionorganizacional.modelo.Entidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractJdbcDAO<T extends Entidad> implements DAO<T> {
    protected interface Binder {
        void bind(PreparedStatement ps) throws SQLException;
    }

    protected abstract String sqlListar();
    protected abstract String sqlInsertar();
    protected abstract String sqlActualizar();
    protected abstract String tabla();
    protected abstract T mapear(ResultSet rs) throws SQLException;
    protected abstract void enlazarInsertar(PreparedStatement ps, T entidad) throws SQLException;
    protected abstract void enlazarActualizar(PreparedStatement ps, T entidad) throws SQLException;

    protected List<T> consultar(String sql, Binder binder) throws SQLException {
        List<T> resultado = new ArrayList<>();
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            if (binder != null) {
                binder.bind(ps);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapear(rs));
                }
            }
        }
        return resultado;
    }

    @Override
    public List<T> listar() throws SQLException {
        return consultar(sqlListar(), null);
    }

    @Override
    public T buscarPorId(int id) throws SQLException {
        List<T> resultado = consultar(sqlListar() + " WHERE t.id = ?", ps -> ps.setInt(1, id));
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    @Override
    public int insertar(T entidad) throws SQLException {
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sqlInsertar(), Statement.RETURN_GENERATED_KEYS)) {
            enlazarInsertar(ps, entidad);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    entidad.setId(keys.getInt(1));
                    return entidad.getId();
                }
            }
        }
        throw new SQLException("No se obtuvo el ID generado.");
    }

    @Override
    public void actualizar(T entidad) throws SQLException {
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sqlActualizar())) {
            enlazarActualizar(ps, entidad);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("El registro ya no existe.");
            }
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM " + tabla() + " WHERE id = ?")) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("El registro ya no existe.");
            }
        }
    }
}

