
package edu.egg.tinder.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {
    
    @Autowired
    private JavaMailSender mailSender;
    //Asincrono: el hilo de ejecucion no espera a que se termine de enviar el mail, lo ejecuta
    //en un hilo paralelo. De esta forma, el usuario tiene respuesta inmediata, no tiene que esperar
    //a que se envie el mail
    @Async
    public void enviar(String cuerpo, String titulo, String mail){
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom("noreply@tinder-mascota.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
    }
    
}
