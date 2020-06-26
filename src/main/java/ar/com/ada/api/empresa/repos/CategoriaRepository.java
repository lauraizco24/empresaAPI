package ar.com.ada.api.empresa.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.empresa.entities.Categoria;


@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    Categoria findById(int categoriaId);

    Categoria findByNombre(String nombreCategoria);
}