package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.ProyectoDAO;
import com.gestionorganizacional.modelo.Proyecto;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class ProyectoController extends ControladorCRUD<Proyecto> {
    public ProyectoController() { super(new ProyectoDAO()); }
    @Override protected void validar(Proyecto p) throws ValidacionException {
        p.setNombre(Validaciones.requerido(p.getNombre(), "nombre"));
        p.setDescripcion(p.getDescripcion() == null ? "" : p.getDescripcion().trim());
        Validaciones.rangoFechas(p.getFechaInicio(), p.getFechaFin());
        Validaciones.positivoOCero(p.getPresupuesto(), "El presupuesto");
        if (p.getEstado() == null) throw new ValidacionException("Seleccione el estado.");
    }
}

