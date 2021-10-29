
package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Mascota;
import edu.egg.tinder.entidades.Voto;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.MascotaRepositorio;
import edu.egg.tinder.repositorios.VotoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoServicio {
    
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    @Autowired
    private VotoRepositorio votoRepositorio;
    
    @Autowired
    private NotificacionServicio notificacionServicio;
    
    //Metodo para votar una mascota
    public void votar(String id_usuario, String id_mascota1, String id_mascota2) throws ErrorServicio{
        Voto voto = new Voto();
        voto.setFecha(new Date());
        
        if(id_mascota1.equals(id_mascota2)){
             throw new ErrorServicio("No puede votarse a si mismo!");
        }
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(id_mascota1);
        if(respuesta.isPresent()){
            Mascota mascota1 = respuesta.get();
            if(mascota1.getUsuario().getId_usuario().equals(id_usuario)){
                voto.setMascota1(mascota1);
            }else{
            throw new ErrorServicio("No tiene permiso para realizar la operacion solicitada.");
            }
        }else{
            throw new ErrorServicio("No existe mascota vinculada a ese ID.");
        }
         Optional<Mascota> respuesta2 = mascotaRepositorio.findById(id_mascota2);
         if(respuesta2.isPresent()){
            Mascota mascota2 = respuesta.get();
            voto.setMascota1(mascota2);
            
            notificacionServicio.enviar("Tu mascota ha sido votada!", "Tinder de Mascotas", mascota2.getUsuario().getMail());
        }else{
            throw new ErrorServicio("No existe mascota vinculada a ese ID.");
        }
        votoRepositorio.save(voto);
    }
    
    //Metodo para recibir un voto
    public void responder(String id_usuario, String id_voto) throws ErrorServicio{
        Optional<Voto> respuesta = votoRepositorio.findById(id_voto);
        if(respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());
            
            if(voto.getMascota2().getUsuario().getId_usuario().equals(id_usuario)){
                notificacionServicio.enviar("Tu mascota tiene matching!", "Tinder de Mascotas", voto.getMascota1().getUsuario().getMail());
                votoRepositorio.save(voto);
            }else{
                throw new ErrorServicio("No tiene permiso esta operacion.");
            }
        }else{
                throw new ErrorServicio("No existe el voto solicitado.");
    }
    
  
    
}
}