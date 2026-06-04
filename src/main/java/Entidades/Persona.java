package Entidades;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class Persona{
    protected int dni_persona;
    protected String nombre; 
    protected String apellido;
    protected int telefono;
    protected String correo;

    // constructor para personas  como adoptantes y voluntarios
    public Persona(int dni_persona, String nombre, String apellido, int telefono, String correo) {
        this.dni_persona = dni_persona;
        this.nombre = nombre;
        this.apellido= apellido;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    // Constructor de copia
    public Persona(Persona otraPersona) {
        this.dni_persona = otraPersona.dni_persona;
        this.nombre = otraPersona.nombre;
        this.apellido= otraPersona.apellido;
        this.telefono = otraPersona.telefono;
        this.correo = otraPersona.correo;
    }

    public Persona() {
    }
    
    @Override
    public String toString() {
        return "\nDNI #" + dni_persona + "\n" +
           "  Nombre y Apellido: " + nombre +" "+ apellido +"\n" +
           "  Telefono: " + telefono + "\n" +
           "  Correo: " + correo + " \n";
    }

    public int getDni_persona() {
        return dni_persona;
    }

    public void setDni_persona(int dni_persona) {
        this.dni_persona = dni_persona;
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre;         
    }
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public int getTelefono() { 
        return telefono; 
    }
    public void setTelefono(int telefono) { 
        this.telefono = telefono; 
    }
    public String getCorreo() {
        return correo; 
    }
    public void setCorreo(String correo) {
        this.correo = correo; 
    }
}