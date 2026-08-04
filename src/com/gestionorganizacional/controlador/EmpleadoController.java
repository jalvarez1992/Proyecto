package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.EmpleadoDAO;
import com.gestionorganizacional.modelo.Empleado;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class EmpleadoController extends ControladorCRUD<Empleado> {
    public EmpleadoController() { super(new EmpleadoDAO()); }
    @Override protected void validar(Empleado e) throws ValidacionException {
        e.setIdentidad(Validaciones.requerido(e.getIdentidad(), "identidad"));
        e.setNombres(Validaciones.requerido(e.getNombres(), "nombres"));
        e.setApellidos(Validaciones.requerido(e.getApellidos(), "apellidos"));
        e.setEmail(Validaciones.email(e.getEmail()));
        e.setTelefono(e.getTelefono() == null ? "" : e.getTelefono().trim());
        if (e.getFechaContratacion() == null) throw new ValidacionException("La fecha de contratación es obligatoria.");
        Validaciones.positivoOCero(e.getSalario(), "El salario");
        Validaciones.idSeleccionado(e.getDepartamentoId(), "un departamento");
        Validaciones.idSeleccionado(e.getCargoId(), "un cargo");
    }
}

