package ar.com.ada.api.empresa.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.empresa.entities.Empleado;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Integer> {

    Empleado findById(int id);

}