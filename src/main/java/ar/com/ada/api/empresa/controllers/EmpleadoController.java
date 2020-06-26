package ar.com.ada.api.empresa.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empresa.entities.Empleado;
import ar.com.ada.api.empresa.models.request.infoEmpleadaRequest;
import ar.com.ada.api.empresa.models.response.GenericResponse;
import ar.com.ada.api.empresa.services.CategoriaService;
import ar.com.ada.api.empresa.services.EmpleadoService;

@RestController
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService catService;

    @PostMapping("/empleados")
    public ResponseEntity<?> postEmpleados(@RequestBody infoEmpleadaRequest infoEmpleada) {
        Empleado empleado = new Empleado();
        empleado.setNombre(infoEmpleada.nombre);
        empleado.setEdad(infoEmpleada.edad);
        empleado.setSueldo(infoEmpleada.sueldo);
        empleado.setFechaDeAlta(new Date());
        empleado.setCategoria(catService.buscarPorId(infoEmpleada.categoriaId));
        empleado.setEstado(1);

        GenericResponse r = new GenericResponse(); // Instancio el objeto donde cree los atributos de mi respuesta
                                                   // generica

        boolean resultado = empleadoService.crearEmpleado(empleado);

        if (resultado) {
            r.isOk = true;
            r.id = empleado.getId();
            r.message = "Creaste el empleado: " + empleado.getId() + " exitosamente.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo crear el empleado! ya existe unempleado con ese Id";

            return ResponseEntity.badRequest().body(r);
        }

    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleado() {
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

}