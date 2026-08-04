package com.gestionorganizacional.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    List<T> listar() throws SQLException;
    List<T> buscar(String texto) throws SQLException;
    T buscarPorId(int id) throws SQLException;
    int insertar(T entidad) throws SQLException;
    void actualizar(T entidad) throws SQLException;
    void eliminar(int id) throws SQLException;
}

