package com.gestionorganizacional.dao;

import com.gestionorganizacional.modelo.Empleado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpleadoDAO extends AbstractJdbcDAO<Empleado> {
    @Override protected String sqlListar() {
        return "SELECT t.id, t.identidad, t.nombres, t.apellidos, t.email, t.telefono, "
                + "t.fecha_contratacion, t.salario, t.departamento_id, d.nombre departamento_nombre, "
                + "t.cargo_id, c.nombre cargo_nombre, t.activo FROM empleado t "
                + "JOIN departamento d ON d.id=t.departamento_id JOIN cargo c ON c.id=t.cargo_id";
    }
    @Override protected String sqlInsertar() {
        return "INSERT INTO empleado(identidad,nombres,apellidos,email,telefono,fecha_contratacion,"
                + "salario,departamento_id,cargo_id,activo) VALUES(?,?,?,?,?,?,?,?,?,?)";
    }
    @Override protected String sqlActualizar() {
        return "UPDATE empleado SET identidad=?,nombres=?,apellidos=?,email=?,telefono=?,"
                + "fecha_contratacion=?,salario=?,departamento_id=?,cargo_id=?,activo=? WHERE id=?";
    }
    @Override protected String tabla() { return "empleado"; }
    @Override protected Empleado mapear(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setId(rs.getInt("id")); e.setIdentidad(rs.getString("identidad"));
        e.setNombres(rs.getString("nombres")); e.setApellidos(rs.getString("apellidos"));
        e.setEmail(rs.getString("email")); e.setTelefono(rs.getString("telefono"));
        e.setFechaContratacion(LocalDate.parse(rs.getString("fecha_contratacion")));
        e.setSalario(rs.getDouble("salario")); e.setDepartamentoId(rs.getInt("departamento_id"));
        e.setDepartamentoNombre(rs.getString("departamento_nombre"));
        e.setCargoId(rs.getInt("cargo_id")); e.setCargoNombre(rs.getString("cargo_nombre"));
        e.setActivo(rs.getBoolean("activo"));
        return e;
    }
    @Override protected void enlazarInsertar(PreparedStatement ps, Empleado e) throws SQLException {
        ps.setString(1, e.getIdentidad()); ps.setString(2, e.getNombres());
        ps.setString(3, e.getApellidos()); ps.setString(4, e.getEmail());
        ps.setString(5, e.getTelefono()); ps.setString(6, e.getFechaContratacion().toString());
        ps.setDouble(7, e.getSalario()); ps.setInt(8, e.getDepartamentoId());
        ps.setInt(9, e.getCargoId()); ps.setBoolean(10, e.isActivo());
    }
    @Override protected void enlazarActualizar(PreparedStatement ps, Empleado e) throws SQLException {
        enlazarInsertar(ps, e); ps.setInt(11, e.getId());
    }
    @Override public List<Empleado> buscar(String texto) throws SQLException {
        String patron = "%" + texto.trim() + "%";
        return consultar(sqlListar() + " WHERE t.identidad LIKE ? OR t.nombres LIKE ? "
                + "OR t.apellidos LIKE ? OR t.email LIKE ? ORDER BY t.apellidos, t.nombres",
                ps -> { for (int i=1; i<=4; i++) ps.setString(i, patron); });
    }
}

