
package edu.egg.tinder.servicios;

import edu.egg.tinder.entidades.Foto;
import edu.egg.tinder.errores.ErrorServicio;
import edu.egg.tinder.repositorios.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    //Si el metodo se ejecuta sin lanzar excepciones, entonces se hace un comit a la base de datos
    //y se aplican todos los cambios
    //Si el metodo lanza una excepcion y no es atrapada, se vuelve atras con la excepcion y no se
    //aplica nada en la base de datos
    @Transactional
    //MultipartFile es el archivo donde se almacena el archivo de la foto
    public Foto guardar(MultipartFile archivo) throws ErrorServicio{
        
        if(archivo != null){
            try{
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;  
    }
    
    @Transactional
    public Foto actualizar(String id_foto, MultipartFile archivo) throws ErrorServicio{
          if(archivo != null){
            try{
                Foto foto = new Foto();
                if(id_foto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(id_foto);
                    if(respuesta.isPresent()){
                        foto = respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
