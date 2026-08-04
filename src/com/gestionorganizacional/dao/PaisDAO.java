package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.Pais;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PaisDAO extends AbstractJdbcDAO<Pais> {
    @Override protected String sqlListar() {
        return "SELECT t.id, t.nombre, t.codigo_iso, t.activo FROM pais t";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO pais(nombre, codigo_iso, activo) VALUES(?, ?, ?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE pais SET nombre=?, codigo_iso=?, activo=? WHERE id=?";
    }
    @Override protected String tabla() { return "pais"; }
    @Override protected Pais mapear(ResultSet rs) throws SQLException {
        return new Pais(rs.getInt("id"), rs.getString("nombre"),
                rs.getString("codigo_iso"), rs.getBoolean("activo"));
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Pais p) throws SQLException {
        ps.setString(1, p.getNombre()); ps.setString(2, p.getCodigoIso()); ps.setBoolean(3, p.isActivo());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Pais p) throws SQLException {
        enlazarInsertar(ps, p); ps.setInt(4, p.getId());
    }
    @Override public List<Pais> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE t.nombre LIKE ? OR t.codigo_iso LIKE ? ORDER BY t.nombre",
                ps -> { ps.setString(1, patron); ps.setString(2, patron); });
    }
}

