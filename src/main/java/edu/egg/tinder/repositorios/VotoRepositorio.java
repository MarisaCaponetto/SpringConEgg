
package edu.egg.tinder.repositorios;

import edu.egg.tinder.entidades.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String> {
    
    @Query("SELECT v FROM Voto v WHERE v.mascota1.id_mascota = :id_mascota ORDER BY v.fecha DESC")
    public List<Voto> buscarVotosPropios(@Param("id_mascota")String id_mascota);
    
    @Query("SELECT v FROM Voto v WHERE v.mascota2.id_mascota = :id_mascota ORDER BY v.fecha DESC")
    public List<Voto> buscarVotosRecibidos(@Param("id_mascota")String id_mascota);
    
}
