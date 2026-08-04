package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.Cargo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CargoDAO extends AbstractJdbcDAO<Cargo> {
    @Override protected String sqlListar() {
        return "SELECT t.id, t.nombre, t.descripcion, t.salario_minimo, t.salario_maximo, t.activo FROM cargo t";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO cargo(nombre, descripcion, salario_minimo, salario_maximo, activo) VALUES(?, ?, ?, ?, ?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE cargo SET nombre=?, descripcion=?, salario_minimo=?, salario_maximo=?, activo=? WHERE id=?";
    }
    @Override protected String tabla() { return "cargo"; }
    @Override protected Cargo mapear(ResultSet rs) throws SQLException {
        return new Cargo(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                rs.getDouble("salario_minimo"), rs.getDouble("salario_maximo"), rs.getBoolean("activo"));
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Cargo c) throws SQLException {
        ps.setString(1, c.getNombre()); ps.setString(2, c.getDescripcion());
        ps.setDouble(3, c.getSalarioMinimo()); ps.setDouble(4, c.getSalarioMaximo());
        ps.setBoolean(5, c.isActivo());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Cargo c) throws SQLException {
        enlazarInsertar(ps, c); ps.setInt(6, c.getId());
    }
    @Override public List<Cargo> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE t.nombre LIKE ? OR t.descripcion LIKE ? ORDER BY t.nombre",
                ps -> { ps.setString(1, patron); ps.setString(2, patron); });
    }
}

