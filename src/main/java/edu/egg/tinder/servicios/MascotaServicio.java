package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Mascota;
import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.enumeraciones.Sexo;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.MascotaRepositorio;
import edu.egg.tinder.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MascotaServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    
    public void agregarMascota(String id_usuario, String nombre, Sexo sexo) throws ErrorServicio{
        Usuario usuario = usuarioRepositorio.findById(id_usuario).get();
        validar(nombre, sexo);
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());
        mascotaRepositorio.save(mascota);
        
    }
    public void modificar(String id_usuario, String id_mascota, String nombre, Sexo sexo) throws ErrorServicio{
        validar(nombre, sexo);
        Optional<Mascota> respuesta = mascotaRepositorio.findById(id_mascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId_usuario().equals(id_usuario)){
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("No tiene permiso para realizar la operacion.");
            }
        }else{
            throw new ErrorServicio("No existe una mascota de ese ID.");
        }
    }
    public void eliminar(String id_usuario, String id_mascota) throws ErrorServicio{
        Optional<Mascota> respuesta = mascotaRepositorio.findById(id_mascota);
        if(respuesta.isPresent()){
            Mascota mascota = respuesta.get();
            if(mascota.getUsuario().getId_usuario().equals(id_usuario)){
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("No tiene permiso para modificar esta mascota.");
            }
            
        }else{
            throw new ErrorServicio("No existe una mascota con el ID indicado.");
        }
    }
    public void validar(String nombre, Sexo sexo) throws ErrorServicio{
        if(nombre==null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo.");
        }
         if(sexo==null){
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo.");
        }
    }
    
}
