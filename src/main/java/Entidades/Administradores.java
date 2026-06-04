/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author admin
 */
public class Administradores extends Persona {
    public String contraseña;

    public Administradores() {
    }


    public Administradores(Persona otraPersona,String contraseña) {
        super(otraPersona);
        this.contraseña= contraseña;
    }

  
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    } 
}
