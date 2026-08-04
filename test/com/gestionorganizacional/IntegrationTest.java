package com.gestionorganizacional;

import com.gestionorganizacional.conexion.ConexionDB;
import com.gestionorganizacional.controlador.*;
import com.gestionorganizacional.modelo.*;
import com.gestionorganizacional.util.ValidacionException;
import java.time.LocalDate;

public final class IntegrationTest {
    private IntegrationTest() {}

    public static void main(String[] args) throws Exception {
        ConexionDB.inicializar();
        String marca = String.valueOf(System.nanoTime());

        AuthController autenticacion = new AuthController();
        Usuario administrador = autenticacion.autenticar("admin", "Admin123*".toCharArray());
        exigir(administrador != null, "No se autenticó el usuario predeterminado.");
        exigir("ADMINISTRADOR".equals(administrador.getRol()), "El rol predeterminado no es correcto.");
        boolean rechazoClaveInvalida = false;
        try {
            autenticacion.autenticar("admin", "incorrecta".toCharArray());
        } catch (ValidacionException ex) {
            rechazoClaveInvalida = true;
        }
        exigir(rechazoClaveInvalida, "Se aceptó una contraseña incorrecta.");

        PaisController paises = new PaisController();
        Pais pais = new Pais(0, "País Prueba " + marca, "XZ", true);
        paises.guardar(pais);
        exigir(pais.getId() > 0, "No se insertó el país.");

        DepartamentoController departamentos = new DepartamentoController();
        Departamento departamento = new Departamento(0, "Departamento " + marca, pais.getId(), true);
        departamentos.guardar(departamento);

        CargoController cargos = new CargoController();
        Cargo cargo = new Cargo(0, "Cargo " + marca, "Prueba", 1000, 2000, true);
        cargos.guardar(cargo);

        EmpleadoController empleados = new EmpleadoController();
        Empleado empleado = new Empleado();
        empleado.setIdentidad("ID-" + marca);
        empleado.setNombres("Ana"); empleado.setApellidos("Prueba");
        empleado.setEmail("ana." + marca + "@example.com"); empleado.setTelefono("9999-9999");
        empleado.setFechaContratacion(LocalDate.now()); empleado.setSalario(1500);
        empleado.setDepartamentoId(departamento.getId()); empleado.setCargoId(cargo.getId());
        empleado.setActivo(true);
        empleados.guardar(empleado);

        ProyectoController proyectos = new ProyectoController();
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto " + marca); proyecto.setDescripcion("Integración");
        proyecto.setFechaInicio(LocalDate.now()); proyecto.setPresupuesto(5000);
        proyecto.setEstado(EstadoProyecto.ACTIVO);
        proyectos.guardar(proyecto);

        AsignacionController asignaciones = new AsignacionController();
        Asignacion asignacion = new Asignacion();
        asignacion.setEmpleadoId(empleado.getId()); asignacion.setProyectoId(proyecto.getId());
        asignacion.setFechaAsignacion(LocalDate.now()); asignacion.setHorasAsignadas(40);
        asignacion.setRol("Desarrollo");
        asignaciones.guardar(asignacion);

        exigir(!empleados.buscar(marca).isEmpty(), "La búsqueda de empleados no devolvió resultados.");
        empleado.setTelefono("2222-2222");
        empleados.modificar(empleado);
        exigir("2222-2222".equals(empleados.buscar(marca).get(0).getTelefono()), "No se actualizó el empleado.");

        asignaciones.eliminar(asignacion.getId());
        empleados.eliminar(empleado.getId());
        proyectos.eliminar(proyecto.getId());
        departamentos.eliminar(departamento.getId());
        cargos.eliminar(cargo.getId());
        paises.eliminar(pais.getId());

        System.out.println("IntegrationTest OK");
    }

    private static void exigir(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError(mensaje);
    }
}
