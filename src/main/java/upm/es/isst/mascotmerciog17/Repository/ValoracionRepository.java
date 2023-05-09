package upm.es.isst.mascotmerciog17.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import upm.es.isst.mascotmerciog17.models.Valoracion;

@Repository
public interface ValoracionRepository extends CrudRepository<Valoracion, Integer>{
    List <Valoracion> findAll();
    
    List <Valoracion> findByEstablecimientoId(Integer establecimientoId);
}

