package upm.es.isst.mascotmerciog17;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import  upm.es.isst.mascotmerciog17.models.Usuario;
import  upm.es.isst.mascotmerciog17.Repository.UsuarioRepository;

@SpringBootTest 


public class TestUsuarioService {

 
    @Autowired //inyectarle un repo

    private UsuarioRepository repo;


// Añado esto que, OJO: BORRA TODAS LAS ENTRADAS A LA BD, porque si no al pasar varias veces la prueba 
// la da como erronea porque encuentra mas de un usuario con el mismo userName
    @BeforeEach
    void cleanUp() {
        repo.deleteAll();
    }

    
    //Prueba crear un usuario con rol cliente
    @Test
    final void testUsuarioCliente() {

        Usuario cliente1 = new Usuario();

        cliente1.setUsername("cliente1@gmail.com");
        cliente1.setPassword("cliente1");
        cliente1.setRol("ROLE_CLIENTE");
        repo.save(cliente1);

        Usuario cliente2 = repo.findByUsername("cliente1@gmail.com");

        assertEquals(cliente2.getUsername(), cliente1.getUsername());
        assertEquals(cliente2.getPassword(), cliente1.getPassword());
        assertNotEquals("ROLE_PROPIETARIO", cliente2.getRol());
        
    }

    //Prueba crear un usuario con rol cliente
    @Test
    final void testUsuarioPropietario() {

        Usuario propietario1 = new Usuario();

        propietario1.setUsername("propietario1@gmail.com");
        propietario1.setPassword("propietario1");
        propietario1.setRol("ROLE_PROPIETARIO");
        repo.save(propietario1);

        Usuario propietario2 = repo.findByUsername("propietario1@gmail.com");

        assertEquals(propietario2.getUsername(), propietario1.getUsername());
        assertEquals(propietario2.getPassword(), propietario1.getPassword());
        assertNotEquals("ROLE_CLIENTE", propietario2.getRol());
        
    }

}

