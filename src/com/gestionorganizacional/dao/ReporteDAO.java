package com.gestionorganizacional.dao;

import com.gestionorganizacional.conexion.ConexionDB;
import com.gestionorganizacional.modelo.ReporteData;
import com.gestionorganizacional.modelo.ResumenDashboard;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ReporteDAO {
    private static final NumberFormat MONEDA = NumberFormat.getCurrencyInstance(new Locale("es", "HN"));

    public ResumenDashboard obtenerResumen() throws SQLException {
        String sql = "SELECT "
                + "(SELECT COUNT(*) FROM empleado WHERE activo=1) empleados, "
                + "(SELECT COUNT(*) FROM departamento WHERE activo=1) departamentos, "
                + "(SELECT COUNT(*) FROM proyecto WHERE estado='ACTIVO') proyectos, "
                + "(SELECT COUNT(*) FROM asignacion) asignaciones, "
                + "(SELECT COALESCE(SUM(presupuesto),0) FROM proyecto) presupuesto";
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return new ResumenDashboard(rs.getInt("empleados"), rs.getInt("departamentos"),
                    rs.getInt("proyectos"), rs.getInt("asignaciones"), rs.getDouble("presupuesto"));
        }
    }

    public ReporteData empleados() throws SQLException {
        String sql = "SELECT e.id AS ID, CONCAT(e.nombres,' ',e.apellidos) AS Empleado, "
                + "e.identidad AS Identidad, e.email AS Correo, d.nombre AS Departamento, "
                + "c.nombre AS Cargo, e.salario AS Salario, "
                + "CASE WHEN e.activo=1 THEN 'Activo' ELSE 'Inactivo' END AS Estado "
                + "FROM empleado e JOIN departamento d ON d.id=e.departamento_id "
                + "JOIN cargo c ON c.id=e.cargo_id ORDER BY e.apellidos,e.nombres";
        return consultar("Reporte de empleados", "Directorio, estructura y salario registrado", sql,
                List.of("ID", "Empleado", "Identidad", "Correo", "Departamento", "Cargo", "Salario", "Estado"),
                6);
    }

    public ReporteData proyectos() throws SQLException {
        String sql = "SELECT p.id AS ID, p.nombre AS Proyecto, p.estado AS Estado, "
                + "p.fecha_inicio AS Inicio, p.fecha_fin AS Fin, p.presupuesto AS Presupuesto, "
                + "COUNT(DISTINCT a.empleado_id) AS Empleados, COALESCE(SUM(a.horas_asignadas),0) AS Horas "
                + "FROM proyecto p LEFT JOIN asignacion a ON a.proyecto_id=p.id "
                + "GROUP BY p.id,p.nombre,p.estado,p.fecha_inicio,p.fecha_fin,p.presupuesto "
                + "ORDER BY p.fecha_inicio DESC,p.nombre";
        return consultar("Reporte de proyectos", "Presupuesto, estado y carga de asignaciones", sql,
                List.of("ID", "Proyecto", "Estado", "Inicio", "Fin", "Presupuesto", "Empleados", "Horas"),
                5);
    }

    public ReporteData estructura() throws SQLException {
        String sql = "SELECT p.nombre AS Pais, d.nombre AS Departamento, "
                + "COUNT(e.id) AS Empleados, SUM(CASE WHEN e.activo=1 THEN 1 ELSE 0 END) AS Activos, "
                + "COALESCE(SUM(e.salario),0) AS Nomina "
                + "FROM departamento d JOIN pais p ON p.id=d.pais_id "
                + "LEFT JOIN empleado e ON e.departamento_id=d.id "
                + "GROUP BY p.nombre,d.nombre ORDER BY p.nombre,d.nombre";
        return consultar("Estructura organizacional", "Distribución de empleados y nómina por departamento", sql,
                List.of("País", "Departamento", "Empleados", "Activos", "Nómina"), 4);
    }

    public ReporteData asignaciones() throws SQLException {
        String sql = "SELECT a.id AS ID, CONCAT(e.nombres,' ',e.apellidos) AS Empleado, "
                + "p.nombre AS Proyecto, a.fecha_asignacion AS Fecha, a.horas_asignadas AS Horas, a.rol AS Rol "
                + "FROM asignacion a JOIN empleado e ON e.id=a.empleado_id "
                + "JOIN proyecto p ON p.id=a.proyecto_id ORDER BY p.nombre,e.apellidos,e.nombres";
        return consultar("Asignaciones por proyecto", "Participación, rol y horas asignadas", sql,
                List.of("ID", "Empleado", "Proyecto", "Fecha", "Horas", "Rol"), -1);
    }

    private ReporteData consultar(String titulo, String subtitulo, String sql,
            List<String> columnas, int columnaMoneda) throws SQLException {
        List<List<String>> filas = new ArrayList<>();
        try (Connection cn = ConexionDB.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                List<String> fila = new ArrayList<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    Object valor = rs.getObject(i);
                    if (i - 1 == columnaMoneda && valor != null) {
                        BigDecimal decimal = valor instanceof BigDecimal bd ? bd : BigDecimal.valueOf(rs.getDouble(i));
                        fila.add(MONEDA.format(decimal));
                    } else {
                        fila.add(valor == null ? "" : String.valueOf(valor));
                    }
                }
                filas.add(fila);
            }
        }
        return new ReporteData(titulo, subtitulo, columnas, filas);
    }
}
