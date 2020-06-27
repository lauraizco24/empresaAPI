package ar.com.ada.api.empresa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empresa.entities.Categoria;
import ar.com.ada.api.empresa.repos.CategoriaRepository;
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    // Creamos una nueva categoria
    public boolean crearCategoria(Categoria categoria) {
        if (existe(categoria.getId())) // Llamamos al metodo existe
            return false;

        grabarCategoria(categoria); // Llamamos al metodo grabarCategoria
        return true;
    }

    //creamos una Categoria en base a su nombre
    public boolean crearCategoriaPorNombre(Categoria categoria){
        if(existePorNombre(categoria.getNombre()))
        return false;

        grabarCategoria(categoria);
        return true;
    }

    // Este metodo graba la categoria en la base de datos
    public void grabarCategoria(Categoria categoria) {
        categoriaRepo.save(categoria);
    }

    // Este metodo nos permite verificar si la categoria(a traves de su ID), existe
    // o no existe.
    public boolean existe(int categoriaId) {

        Categoria categoria = buscarPorId(categoriaId);

        return categoria != null;

    }

    //Metodo para verificar si existe una categoria con ese nombre
    public boolean existePorNombre(String nombreCategoria){
        Categoria categoria = buscarPorNombre(nombreCategoria);
        return categoria != null;
    }

    // Este metodo llama al metodo definido en el repositorio para uscar una
    // categoria en la base de datos a traves de su ID.
    public Categoria buscarPorId(int categoriaId) {
        return categoriaRepo.findById(categoriaId);

    }

    //este metodo llama al metodo definido en el repositorio para buscar una categoria por nombre
    public Categoria buscarPorNombre(String nombreCategoria){
        return categoriaRepo.findByNombre(nombreCategoria);
    }


    public boolean actualizarCategoria(Categoria categoriaOriginal, Categoria categoriaActualizada) {
        // Aca solo dejamos que se actualize el nombre y domicilio solamente

        categoriaOriginal.setId(categoriaActualizada.getId());
        categoriaOriginal.setNombre(categoriaActualizada.getNombre());
        categoriaOriginal.setSueldoBase(categoriaActualizada.getSueldoBase());

        grabarCategoria(categoriaOriginal);

        return true;
    }

    public List<Categoria> obtenerCategoria(){
        return categoriaRepo.findAll();
    }
    public Categoria obtenerCategoriaporId(int categoriaId){
        return categoriaRepo.findById(categoriaId);
    }

}