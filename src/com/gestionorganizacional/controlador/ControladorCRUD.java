package com.gestionorganizacional.controlador;

import com.gestionorganizacional.dao.DAO;
import com.gestionorganizacional.modelo.Entidad;
import com.gestionorganizacional.util.PersistenciaException;
import com.gestionorganizacional.util.ValidacionException;
import java.sql.SQLException;
import java.util.List;

public abstract class ControladorCRUD<T extends Entidad> {
    private final DAO<T> dao;

    protected ControladorCRUD(DAO<T> dao) {
        this.dao = dao;
    }

    protected abstract void validar(T entidad) throws ValidacionException;

    public List<T> listar() throws PersistenciaException {
        try { return dao.listar(); } catch (SQLException ex) { throw convertir(ex); }
    }

    public List<T> buscar(String texto) throws PersistenciaException {
        try { return texto == null || texto.isBlank() ? dao.listar() : dao.buscar(texto); }
        catch (SQLException ex) { throw convertir(ex); }
    }

    public int guardar(T entidad) throws ValidacionException, PersistenciaException {
        validar(entidad);
        try { return dao.insertar(entidad); } catch (SQLException ex) { throw convertir(ex); }
    }

    public void modificar(T entidad) throws ValidacionException, PersistenciaException {
        if (entidad.getId() <= 0) throw new ValidacionException("Seleccione un registro para modificar.");
        validar(entidad);
        try { dao.actualizar(entidad); } catch (SQLException ex) { throw convertir(ex); }
    }

    public void eliminar(int id) throws ValidacionException, PersistenciaException {
        if (id <= 0) throw new ValidacionException("Seleccione un registro para eliminar.");
        try { dao.eliminar(id); } catch (SQLException ex) { throw convertir(ex); }
    }

    private PersistenciaException convertir(SQLException ex) {
        String detalle = ex.getMessage() == null ? "" : ex.getMessage();
        String mensaje;
        if (ex.getErrorCode() == 1062 || detalle.contains("Duplicate entry")) {
            mensaje = "Ya existe un registro con uno de los valores únicos ingresados.";
        } else if (ex.getErrorCode() == 1451 || ex.getErrorCode() == 1452
                || detalle.contains("foreign key constraint fails")) {
            mensaje = "No se puede completar la operación porque el registro está relacionado con otros datos.";
        } else if (ex.getErrorCode() == 3819 || detalle.contains("Check constraint")) {
            mensaje = "Uno de los valores no cumple las reglas de la base de datos.";
        } else {
            mensaje = "No se pudo completar la operación en la base de datos.";
        }
        return new PersistenciaException(mensaje, ex);
    }
}
