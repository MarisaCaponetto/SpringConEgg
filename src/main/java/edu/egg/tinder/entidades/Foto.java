
package edu.egg.tinder.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Foto {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2")
    private String id_foto;
    
    private String nombre;
    private String mime; //Asigna el formato 
    
    @Lob @Basic(fetch = FetchType.LAZY) //Indica que cargue el contenido solo cuando lo pidamos, haciendo que las queries sean mas livianas
    private byte[] contenido;
    
}
