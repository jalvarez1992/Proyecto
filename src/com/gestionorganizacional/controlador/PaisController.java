package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.PaisDAO;
import com.gestionorganizacional.modelo.Pais;
import com.gestionorganizacional.util.ValidacionException;
import com.gestionorganizacional.util.Validaciones;

public class PaisController extends ControladorCRUD<Pais> {
    public PaisController() { super(new PaisDAO()); }
    @Override protected void validar(Pais p) throws ValidacionException {
        p.setNombre(Validaciones.requerido(p.getNombre(), "nombre"));
        String iso = Validaciones.requerido(p.getCodigoIso(), "código ISO").toUpperCase();
        if (!iso.matches("[A-Z]{2,3}")) {
            throw new ValidacionException("El código ISO debe contener 2 o 3 letras.");
        }
        p.setCodigoIso(iso);
    }
}

