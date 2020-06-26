package ar.com.ada.api.empresa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empresa.entities.Empleado;
import ar.com.ada.api.empresa.repos.EmpleadoRepo;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepo empleadoRepo;

    // Metodo para comprobar si el empleado existe por ID
    public boolean crearEmpleado(Empleado empleado) {
        if (existe(empleado.getId())) // Llamamos al metodo existe
            return false;

        grabarEmpleado(empleado);
        return true;

    }

    public void grabarEmpleado(Empleado empleado) {
        empleadoRepo.save(empleado);
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoRepo.findAll();

    }

    // Este metodo nos permite verificar si el empleado(a traves de su ID), existe
    // o no existe.
    public boolean existe(int empleadoId) {

        Empleado empleado = buscarPorId(empleadoId);

        return empleado != null;

    }

    public Empleado buscarPorId(int empleadoId) {
        return empleadoRepo.findById(empleadoId);

    }
}