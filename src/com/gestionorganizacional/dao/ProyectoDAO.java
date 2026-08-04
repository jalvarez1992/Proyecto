package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.EstadoProyecto;
import com.gestionorganizacional.modelo.Proyecto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;

public class ProyectoDAO extends AbstractJdbcDAO<Proyecto> {
    @Override protected String sqlListar() {
        return "SELECT t.id,t.nombre,t.descripcion,t.fecha_inicio,t.fecha_fin,t.presupuesto,t.estado FROM proyecto t";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO proyecto(nombre,descripcion,fecha_inicio,fecha_fin,presupuesto,estado) VALUES(?,?,?,?,?,?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE proyecto SET nombre=?,descripcion=?,fecha_inicio=?,fecha_fin=?,presupuesto=?,estado=? WHERE id=?";
    }
    @Override protected String tabla() { return "proyecto"; }
    @Override protected Proyecto mapear(ResultSet rs) throws SQLException {
        Proyecto p = new Proyecto();
        p.setId(rs.getInt("id")); p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setFechaInicio(LocalDate.parse(rs.getString("fecha_inicio")));
        String fin = rs.getString("fecha_fin");
        p.setFechaFin(fin == null || fin.isBlank() ? null : LocalDate.parse(fin));
        p.setPresupuesto(rs.getDouble("presupuesto"));
        p.setEstado(EstadoProyecto.valueOf(rs.getString("estado")));
        return p;
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Proyecto p) throws SQLException {
        ps.setString(1, p.getNombre()); ps.setString(2, p.getDescripcion());
        ps.setString(3, p.getFechaInicio().toString());
        if (p.getFechaFin() == null) ps.setNull(4, Types.VARCHAR); else ps.setString(4, p.getFechaFin().toString());
        ps.setDouble(5, p.getPresupuesto()); ps.setString(6, p.getEstado().name());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Proyecto p) throws SQLException {
        enlazarInsertar(ps, p); ps.setInt(7, p.getId());
    }
    @Override public List<Proyecto> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE t.nombre LIKE ? OR t.descripcion LIKE ? OR t.estado LIKE ? ORDER BY t.nombre",
                ps -> { ps.setString(1, patron); ps.setString(2, patron); ps.setString(3, patron); });
    }
}

