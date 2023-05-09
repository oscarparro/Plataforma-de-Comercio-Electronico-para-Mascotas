package upm.es.isst.mascotmerciog17;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import upm.es.isst.mascotmerciog17.models.Establecimiento;
import  upm.es.isst.mascotmerciog17.models.Usuario;
import upm.es.isst.mascotmerciog17.Repository.EstablecimientoRepository;
import  upm.es.isst.mascotmerciog17.Repository.UsuarioRepository;

@SpringBootTest 

public class TestEstableciemientoService {

    @Autowired //inyectarle un repo

    private EstablecimientoRepository repo;
    
    // Añado esto que, OJO: BORRA TODAS LAS ENTRADAS A LA BD, porque si no al pasar varias veces la prueba 
// la da como erronea porque encuentra mas de un usuario con el mismo userName
@BeforeEach
void cleanUp() {
    repo.deleteAll();
}


@Test
final void testEstablecimiento() {

    // Creamos un objeto Establecimiento
    Establecimiento establecimiento = new Establecimiento();
    establecimiento.setNombre("Restaurante Prueba");
    establecimiento.setDireccion("Calle Prueba, 123");
    establecimiento.setDescripcion("Restaurante de prueba para testeo");
    establecimiento.setServicios("prueba");
    establecimiento.setHorario("Lunes");
    repo.save(establecimiento);

    // Buscamos el establecimiento en la base de datos
    Optional<Establecimiento> establecimientoEncontrado = repo.findById(establecimiento.getId());
    assertTrue(establecimientoEncontrado.isPresent());
    assertEquals(establecimientoEncontrado.get().getNombre(), "Restaurante Prueba");
    assertEquals(establecimientoEncontrado.get().getDireccion(), "Calle Prueba, 123");
    assertEquals(establecimientoEncontrado.get().getDescripcion(), "Restaurante de prueba para testeo");
}
}
