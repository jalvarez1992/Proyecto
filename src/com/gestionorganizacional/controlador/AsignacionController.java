package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.AsignacionDAO;
import com.gestionorganizacional.modelo.Asignacion;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class AsignacionController extends ControladorCRUD<Asignacion> {
    public AsignacionController() { super(new AsignacionDAO()); }
    @Override protected void validar(Asignacion a) throws ValidacionException {
        Validaciones.idSeleccionado(a.getEmpleadoId(), "un empleado");
        Validaciones.idSeleccionado(a.getProyectoId(), "un proyecto");
        if (a.getFechaAsignacion() == null) throw new ValidacionException("La fecha de asignación es obligatoria.");
        if (a.getHorasAsignadas() < 1 || a.getHorasAsignadas() > 200) {
            throw new ValidacionException("Las horas asignadas deben estar entre 1 y 200.");
        }
        a.setRol(Validaciones.requerido(a.getRol(), "rol"));
    }
}

