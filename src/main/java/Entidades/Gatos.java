/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author admin
 */
public class Gatos {
    private int id;
    private String nombre;
    private int edad;
    private String raza;
    private double peso;
    private String genero;
    private String  esterilizacion;
    private String estado_gato;// Estado en el qe se encuentra el gato (En adopcion, Adoptado)
    private String cuidado_requerido;

    public Gatos() {
    }
    
    public Gatos(int id, String nombre, int edad, String raza, double peso, String genero,
            String esterilizacion, String estado_gato, String cuidado_requerido) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.peso = peso;
        this.genero = genero;
        this.esterilizacion= esterilizacion;
        this.estado_gato= estado_gato;
        this.cuidado_requerido= cuidado_requerido;                
    }

    public Gatos (Gatos gato){
        this.id = gato.id;
        this.nombre = gato.nombre;
        this.edad = gato.edad;
        this.raza = gato.raza;
        this.peso = gato.peso;
        this.genero = gato.genero;
        this.esterilizacion= gato.esterilizacion;
        this.estado_gato= gato.estado_gato;
        this.cuidado_requerido= gato.cuidado_requerido; 
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getEsterilizacion() {
        return esterilizacion;
    }
    public void setEsterilizacion(String esterilizacion) {
        this.esterilizacion = esterilizacion;
    }
    public String getEstado_gato() {
        return estado_gato;
    }
    public void setEstado_gato(String estado_gato) {
        this.estado_gato = estado_gato;
    }
    public String getCuidado_requerido() {
        return cuidado_requerido;
    }
    public void setCuidado_requerido(String cuidado_requerido) {
        this.cuidado_requerido = cuidado_requerido;
    }

    // Sobrescribe el metodo toString de la clase Object.
    // Permite mostrar la informacion del gato en formato legible al imprimir el objeto.
    @Override
    public String toString() {
        return  "\nGato #" + nombre + "\n" +
           "  ID: " + id + "\n" +
           "  Edad: " + edad + " años\n" +
           "  Raza: " + raza + "\n" +
           "  Peso: " + peso + " kg\n" +
           "  Genero: " + genero + "\n" +
           "  Esterilizacion: " + esterilizacion + "\n" +
           "  Estado: " + estado_gato + "\n" +
           "  Cuidados requeridos: " + cuidado_requerido;
    }
}