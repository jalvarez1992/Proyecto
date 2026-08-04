package com.gestionorganizacional.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Empresa {
    private String nombre;
    private String rtn;
    private final List<Pais> paises = new ArrayList<>();
    private final List<Departamento> departamentos = new ArrayList<>();
    private final List<Cargo> cargos = new ArrayList<>();
    private final List<Empleado> empleados = new ArrayList<>();
    private final List<Proyecto> proyectos = new ArrayList<>();
    private final List<Asignacion> asignaciones = new ArrayList<>();

    public Empresa(String nombre, String rtn) {
        this.nombre = nombre;
        this.rtn = rtn;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRtn() { return rtn; }
    public void setRtn(String rtn) { this.rtn = rtn; }
    public List<Pais> getPaises() { return Collections.unmodifiableList(paises); }
    public List<Departamento> getDepartamentos() { return Collections.unmodifiableList(departamentos); }
    public List<Cargo> getCargos() { return Collections.unmodifiableList(cargos); }
    public List<Empleado> getEmpleados() { return Collections.unmodifiableList(empleados); }
    public List<Proyecto> getProyectos() { return Collections.unmodifiableList(proyectos); }
    public List<Asignacion> getAsignaciones() { return Collections.unmodifiableList(asignaciones); }

    public void reemplazarDatos(List<Pais> nuevosPaises, List<Departamento> nuevosDepartamentos,
            List<Cargo> nuevosCargos, List<Empleado> nuevosEmpleados,
            List<Proyecto> nuevosProyectos, List<Asignacion> nuevasAsignaciones) {
        paises.clear(); paises.addAll(nuevosPaises);
        departamentos.clear(); departamentos.addAll(nuevosDepartamentos);
        cargos.clear(); cargos.addAll(nuevosCargos);
        empleados.clear(); empleados.addAll(nuevosEmpleados);
        proyectos.clear(); proyectos.addAll(nuevosProyectos);
        asignaciones.clear(); asignaciones.addAll(nuevasAsignaciones);
    }
}

