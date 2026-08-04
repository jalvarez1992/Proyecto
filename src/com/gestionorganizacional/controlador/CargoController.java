package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.CargoDAO;
import com.gestionorganizacional.modelo.Cargo;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class CargoController extends ControladorCRUD<Cargo> {
    public CargoController() { super(new CargoDAO()); }
    @Override protected void validar(Cargo c) throws ValidacionException {
        c.setNombre(Validaciones.requerido(c.getNombre(), "nombre"));
        c.setDescripcion(c.getDescripcion() == null ? "" : c.getDescripcion().trim());
        Validaciones.positivoOCero(c.getSalarioMinimo(), "El salario mínimo");
        Validaciones.positivoOCero(c.getSalarioMaximo(), "El salario máximo");
        if (c.getSalarioMaximo() < c.getSalarioMinimo()) {
            throw new ValidacionException("El salario máximo no puede ser menor que el mínimo.");
        }
    }
}

