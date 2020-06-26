package ar.com.ada.api.empresa.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empresa.entities.Categoria;
import ar.com.ada.api.empresa.models.response.GenericResponse;
import ar.com.ada.api.empresa.services.CategoriaService;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    // Con este metodo puedo crear una categoria y verificar si la categoria con ese
    // mismo nombre ya existe
    @PostMapping("/categorias")
    public ResponseEntity<?> postCategoria(@RequestBody Categoria categoriaReq) {

        GenericResponse r = new GenericResponse(); // Instancio el objeto donde cree los atributos de mi respuesta
                                                   // generica

        boolean resultado = categoriaService.crearCategoriaPorNombre(categoriaReq);

        if (resultado) {
            r.isOk = true;
            r.id = categoriaReq.getId();
            r.message = "Creaste la categoria: " + categoriaReq.getId() + " exitosamente.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo crear la categoria! ya existe una categoria con ese nombre";

            return ResponseEntity.badRequest().body(r);
        }

    }

    // Con este metodo puedo actualizar una categoria
    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> postCategoria(@PathVariable int categoriaId, @RequestBody Categoria categoriaReq) {

        GenericResponse r = new GenericResponse(); // Instancio el objeto donde cree los atributos de mi respuesta
                                                   // generica

        Categoria categoriaOriginal = categoriaService.buscarPorId(categoriaId);

        if (categoriaOriginal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean resultado = false;
        resultado = categoriaService.actualizarCategoria(categoriaOriginal, categoriaReq);

        if (resultado) {
            r.isOk = true;
            r.id = categoriaId;
            r.message = "La Categoria: " + categoriaId + " fue actualizada con éxito.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo actualizar la categoria.";

            return ResponseEntity.badRequest().body(r);
        }

    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        return ResponseEntity.ok(categoriaService.obtenerCategoria());
    }

}