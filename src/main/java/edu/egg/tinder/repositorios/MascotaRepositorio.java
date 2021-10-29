
package edu.egg.tinder.repositorios;

import edu.egg.tinder.entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String> {
    
    @Query("SELECT m FROM Mascota m WHERE m.usuario.id = :id")
    public List<Mascota> buscarMascotaPorUsuario(@Param("id_usuario") String id_usuario);
    
}
