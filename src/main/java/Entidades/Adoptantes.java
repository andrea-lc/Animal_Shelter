/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author admin
 */
public class Adoptantes extends Persona {
    private String gato_Adoptado;
    
    public Adoptantes(Persona persona, String gato_Adoptado) {
        super (persona);
        this.gato_Adoptado = gato_Adoptado;
    }

    public String getGato_Adoptado() {
        return gato_Adoptado;
    }

    public void setGato_Adoptado(String gato_Adoptado) {
        this.gato_Adoptado = gato_Adoptado;
    }

    @Override
    public String toString() {
        return super.toString()+ "  Gato_Adoptado: "+ gato_Adoptado;
    } 
}
