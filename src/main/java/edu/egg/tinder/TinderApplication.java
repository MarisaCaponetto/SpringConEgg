package edu.egg.tinder;

import edu.egg.tinder.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TinderApplication extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioServicio usuarioServicio;

    public static void main(String[] args) {
	SpringApplication.run(TinderApplication.class, args);
    }
    //El metodo ConfigureGlobal le dice a la configuracion de Spring Security cual es el servicio que vamos
    //a utilizar para autentificar el usuario y setea un encriptador de contrasenas al servicio de usuario    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
     }

}
