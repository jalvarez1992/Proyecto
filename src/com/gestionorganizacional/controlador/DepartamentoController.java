package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.DepartamentoDAO;
import com.gestionorganizacional.modelo.Departamento;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class DepartamentoController extends ControladorCRUD<Departamento> {
    public DepartamentoController() { super(new DepartamentoDAO()); }
    @Override protected void validar(Departamento d) throws ValidacionException {
        d.setNombre(Validaciones.requerido(d.getNombre(), "nombre"));
        Validaciones.idSeleccionado(d.getPaisId(), "un país");
    }
}

