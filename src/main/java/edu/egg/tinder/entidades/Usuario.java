
package edu.egg.tinder.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_usuario;
    private String nombre;
    private String apellido;
    private String mail;
    private String clave;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    
    @ManyToOne
    private Zona zona;
    
    
}
