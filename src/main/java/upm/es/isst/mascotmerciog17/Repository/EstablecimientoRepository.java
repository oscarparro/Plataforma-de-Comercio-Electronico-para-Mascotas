package upm.es.isst.mascotmerciog17.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import upm.es.isst.mascotmerciog17.models.Establecimiento;

@Repository
public interface EstablecimientoRepository extends CrudRepository<Establecimiento, Integer> {
    List<Establecimiento> findAll();
    @Query ("SELECT e FROM Establecimiento e WHERE e.direccion LIKE %?1%"
            + " OR e.nombre LIKE  %?1%" )
    List<Establecimiento> findByNombreDireccion(String calleClave);

    @Query ("SELECT e FROM Establecimiento e WHERE e.servicios LIKE %?1%")
    List<Establecimiento> findByServicios(String servicios);
}

