package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.Departamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartamentoDAO extends AbstractJdbcDAO<Departamento> {
    @Override protected String sqlListar() {
        return "SELECT t.id, t.nombre, t.pais_id, p.nombre pais_nombre, t.activo "
                + "FROM departamento t JOIN pais p ON p.id=t.pais_id";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO departamento(nombre, pais_id, activo) VALUES(?, ?, ?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE departamento SET nombre=?, pais_id=?, activo=? WHERE id=?";
    }
    @Override protected String tabla() { return "departamento"; }
    @Override protected Departamento mapear(ResultSet rs) throws SQLException {
        Departamento d = new Departamento(rs.getInt("id"), rs.getString("nombre"),
                rs.getInt("pais_id"), rs.getBoolean("activo"));
        d.setPaisNombre(rs.getString("pais_nombre"));
        return d;
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Departamento d) throws SQLException {
        ps.setString(1, d.getNombre()); ps.setInt(2, d.getPaisId()); ps.setBoolean(3, d.isActivo());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Departamento d) throws SQLException {
        enlazarInsertar(ps, d); ps.setInt(4, d.getId());
    }
    @Override public List<Departamento> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE t.nombre LIKE ? OR p.nombre LIKE ? ORDER BY t.nombre",
                ps -> { ps.setString(1, patron); ps.setString(2, patron); });
    }
}

