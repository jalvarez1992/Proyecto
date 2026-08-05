package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.ReporteDAO;
import com.gestionorganizacional.modelo.ReporteData;
import com.gestionorganizacional.modelo.ResumenDashboard;

public final class ReporteController {
    public enum TipoReporte {
        EMPLEADOS("Empleados"),
        PROYECTOS("Proyectos"),
        ESTRUCTURA("Estructura organizacional"),
        ASIGNACIONES("Asignaciones por proyecto");

        private final String nombre;
        TipoReporte(String nombre) { this.nombre = nombre; }
        @Override public String toString() { return nombre; }
    }

    private final ReporteDAO dao = new ReporteDAO();

    public ReporteData generar(TipoReporte tipo) throws Exception {
        if (tipo == null) tipo = TipoReporte.EMPLEADOS;
        return switch (tipo) {
            case EMPLEADOS -> dao.empleados();
            case PROYECTOS -> dao.proyectos();
            case ESTRUCTURA -> dao.estructura();
            case ASIGNACIONES -> dao.asignaciones();
        };
    }

    public ResumenDashboard obtenerResumen() throws Exception {
        return dao.obtenerResumen();
    }
}
