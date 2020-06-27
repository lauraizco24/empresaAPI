package ar.com.ada.api.empresa.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empresa.entities.Empleado;
import ar.com.ada.api.empresa.models.request.infoEmpleadaRequest;
import ar.com.ada.api.empresa.models.request.sueldoNuevoRequest;
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

    @GetMapping("/empleados/{id}")
    // la variable id va a estar en la ruta, por eso se coloca el path variable y el
    // parametro debe llamarse exactamente igual q en la ruta.
    public ResponseEntity<Empleado> obtenerEmpleadoPorID(@PathVariable int id) {
        Empleado e = empleadoService.buscarPorId(id);

        if (e == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(e);
    }

    @GetMapping("/empleados/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorCategoriaId(@PathVariable int categoriaId) {
        List<Empleado> listaEmpleados = catService.obtenerCategoriaporId(categoriaId).getEmpleados();

        return ResponseEntity.ok(listaEmpleados);

    }

    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable int id,
            @RequestBody sueldoNuevoRequest sueldoRequest) {
        Empleado e = empleadoService.buscarPorId(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        e.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoService.grabarEmpleado(e);

        GenericResponse gR = new GenericResponse();
        gR.id = id;
        gR.isOk = true;
        gR.message = "Sueldo del empleado con id: " + e.getId() + " actualizado con exito";
        return ResponseEntity.ok(gR);

    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleado(@PathVariable int id) {
        Empleado e = empleadoService.buscarPorId(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        e.setFechaDeBaja(new Date());
        e.setEstado(0); // en m caso 0 es inactivo o de baja en la base de datos
        empleadoService.grabarEmpleado(e);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = e.getId();
        gR.message = "El Empleado : " + e.getId() + " ha sido dado de baja";
        return ResponseEntity.ok(gR);
    }

}