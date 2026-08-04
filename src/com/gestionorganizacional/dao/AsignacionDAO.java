package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.Asignacion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AsignacionDAO extends AbstractJdbcDAO<Asignacion> {
    @Override protected String sqlListar() {
        return "SELECT t.id,t.empleado_id,CONCAT(e.nombres, ' ', e.apellidos) empleado_nombre,"
                + "t.proyecto_id,p.nombre proyecto_nombre,t.fecha_asignacion,t.horas_asignadas,t.rol "
                + "FROM asignacion t JOIN empleado e ON e.id=t.empleado_id JOIN proyecto p ON p.id=t.proyecto_id";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO asignacion(empleado_id,proyecto_id,fecha_asignacion,horas_asignadas,rol) VALUES(?,?,?,?,?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE asignacion SET empleado_id=?,proyecto_id=?,fecha_asignacion=?,horas_asignadas=?,rol=? WHERE id=?";
    }
    @Override protected String tabla() { return "asignacion"; }
    @Override protected Asignacion mapear(ResultSet rs) throws SQLException {
        Asignacion a = new Asignacion();
        a.setId(rs.getInt("id")); a.setEmpleadoId(rs.getInt("empleado_id"));
        a.setEmpleadoNombre(rs.getString("empleado_nombre")); a.setProyectoId(rs.getInt("proyecto_id"));
        a.setProyectoNombre(rs.getString("proyecto_nombre"));
        a.setFechaAsignacion(LocalDate.parse(rs.getString("fecha_asignacion")));
        a.setHorasAsignadas(rs.getInt("horas_asignadas")); a.setRol(rs.getString("rol"));
        return a;
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Asignacion a) throws SQLException {
        ps.setInt(1, a.getEmpleadoId()); ps.setInt(2, a.getProyectoId());
        ps.setString(3, a.getFechaAsignacion().toString()); ps.setInt(4, a.getHorasAsignadas());
        ps.setString(5, a.getRol());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Asignacion a) throws SQLException {
        enlazarInsertar(ps, a); ps.setInt(6, a.getId());
    }
    @Override public List<Asignacion> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE e.nombres LIKE ? OR e.apellidos LIKE ? "
                + "OR p.nombre LIKE ? OR t.rol LIKE ? ORDER BY t.fecha_asignacion DESC",
                ps -> { for (int i=1; i<=4; i++) ps.setString(i, patron); });
    }
}
