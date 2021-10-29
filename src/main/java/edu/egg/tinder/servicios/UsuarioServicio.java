
package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Foto;
import edu.egg.tinder.entidades.Usuario;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    @Autowired
    private NotificacionServicio notificacionServicio;
    
    //Metodo de registro usuario
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave) throws ErrorServicio{
        
        this.validar(nombre, apellido, mail, clave);
        
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
                
        usuario.setAlta(new Date());
        
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        
        //Para guardar el usuario creado
        usuarioRepositorio.save(usuario);
        
        notificacionServicio.enviar("Bienvenidos al tinder de Mascotas!", "Tinder de Mascotas", usuario.getMail());
    }
    
    //Metodo modificacion de usuario
    @Transactional
    public void modificar(MultipartFile archivo, String id_usuario, String nombre, String apellido, String mail, String clave) throws ErrorServicio{
        this.validar(nombre, apellido, mail, clave);
        
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id_usuario);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);
           
            String id_foto = null;
            if(usuario.getFoto() != null){
                id_foto = usuario.getFoto().getId_foto();
            } 
            Foto foto = fotoServicio.actualizar(id_foto, archivo);
            usuario.setFoto(foto);
            
            usuarioRepositorio.save(usuario);
        } else{
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    
    @Transactional
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
    
    @Transactional
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
    
    //La interfaz obliga a implementar el siguiente metodo abstracto.
    //Este metodo recibe el nombre de usuario, en este caso, el mail, lo busca en el repositorio
    //y lo transforma en un usuario de Spring Security
    //El metodo LoadByUsername es llamado cuando el usuario quiere autentificarse en la plataforma

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if(usuario != null){
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
            permisos.add(p1);
            
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);
            
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);
            
            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        }else{
            return null;
        }
    }
}
