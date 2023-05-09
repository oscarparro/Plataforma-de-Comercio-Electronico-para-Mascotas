package upm.es.isst.mascotmerciog17.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import upm.es.isst.mascotmerciog17.models.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    public Usuario findByUsername(String username);
}
