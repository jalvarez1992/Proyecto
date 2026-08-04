package com.gestionorganizacional.controlador;

import com.gestionorganizacional.modelo.Empresa;
import com.gestionorganizacional.util.PersistenciaException;

public class EmpresaController {
    private final Empresa empresa;

    public EmpresaController(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa cargar() throws PersistenciaException {
        empresa.reemplazarDatos(
                new PaisController().listar(),
                new DepartamentoController().listar(),
                new CargoController().listar(),
                new EmpleadoController().listar(),
                new ProyectoController().listar(),
                new AsignacionController().listar());
        return empresa;
    }
}

