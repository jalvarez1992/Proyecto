package com.gestionorganizacional.modelo;

public final class ResumenDashboard {
    private final int empleadosActivos;
    private final int departamentos;
    private final int proyectosActivos;
    private final int asignaciones;
    private final double presupuestoTotal;

    public ResumenDashboard(int empleadosActivos, int departamentos, int proyectosActivos,
            int asignaciones, double presupuestoTotal) {
        this.empleadosActivos = empleadosActivos;
        this.departamentos = departamentos;
        this.proyectosActivos = proyectosActivos;
        this.asignaciones = asignaciones;
        this.presupuestoTotal = presupuestoTotal;
    }

    public int getEmpleadosActivos() { return empleadosActivos; }
    public int getDepartamentos() { return departamentos; }
    public int getProyectosActivos() { return proyectosActivos; }
    public int getAsignaciones() { return asignaciones; }
    public double getPresupuestoTotal() { return presupuestoTotal; }
}
