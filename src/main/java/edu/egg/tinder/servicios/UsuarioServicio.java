
package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    //Metodo de registro usuario
    public void registrar(String nombre, String apellido, String mail, String clave) throws ErrorServicio{
        Usuario usuario = new Usuario();
        this.validar(nombre, apellido, mail, clave);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setClave(clave);
        usuario.setAlta(new Date());
        //Para guardar el usuario creado
        usuarioRepositorio.save(usuario);
    }
    //Metodo modificacion de usuario
    public void modificar(String id_usuario, String nombre, String apellido, String mail, String clave) throws ErrorServicio{
        this.validar(nombre, apellido, mail, clave);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setClave(clave);
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    //Metodo de deshabilitacion de usuario
    public void deshabilitar(String id_usuario) throws ErrorServicio{
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    //Metodo para habilitar usuario
     public void habilitar(String id_usuario) throws ErrorServicio{
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    
    //Metodo de validacion
    private void validar(String nombre, String apellido, String mail, String clave) throws ErrorServicio {
         if(nombre==null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
        }
        if(apellido==null || apellido.isEmpty()){
            throw new ErrorServicio("El apellido del usuario no puede ser nulo.");
        }
        if(mail==null || mail.isEmpty()){
            throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
        if(clave==null || clave.isEmpty() || clave.length()<=6){
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de 6 caracteres.");
        }
    }
}
