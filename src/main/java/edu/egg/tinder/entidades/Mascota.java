
package edu.egg.tinder.entidades;

import edu.egg.tinder.enumeraciones.Sexo;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Mascota {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_mascota;
    private String nombre;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToOne
    private Foto foto;
    
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
}
