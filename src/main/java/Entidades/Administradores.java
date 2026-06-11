/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author admin
 */
public class Administradores extends Persona {
    public String contraseña;
    public LocalDateTime fechaUltimoLogin;

    public Administradores() {
    }


    public Administradores(Persona otraPersona,String contraseña) {
        super(otraPersona);
        this.contraseña= contraseña;
        this.fechaUltimoLogin = null; 
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }


    public LocalDateTime getFechaUltimoLogin() {
        return fechaUltimoLogin;
    }


    public void setFechaUltimoLogin(LocalDateTime fechaUltimoLogin) {
        this.fechaUltimoLogin = fechaUltimoLogin;
    }
    
    public String getFechaFormateada() {
        if (this.fechaUltimoLogin == null) {
            return "Sin registrar"; 
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.fechaUltimoLogin.format(formatter);
    }


    @Override
    public String toString() {
        return super.toString() + String.format("  Ultimo Login: %s", getFechaFormateada());
    }

    
}
