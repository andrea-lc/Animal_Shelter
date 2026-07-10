/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 * Clase que representa a los adoptantes del sistema Cat Haven.
 * Hereda de Persona y agrega información sobre el gato que ha sido adoptado.
 *
 * @author admin
 */
public class Adoptantes extends Persona {

    // Nombre del gato que ha sido adoptado por esta persona
    private String gato_Adoptado;

    // Constructor que recibe una Persona existente y el nombre del gato adoptado
    public Adoptantes(Persona persona, String gato_Adoptado) {
        super(persona);
        this.gato_Adoptado = gato_Adoptado;
    }

    // ===== GETTERS Y SETTERS =====

    public String getGato_Adoptado() {
        return gato_Adoptado;
    }

    public void setGato_Adoptado(String gato_Adoptado) {
        this.gato_Adoptado = gato_Adoptado;
    }

    // ===== MÉTODOS AUXILIARES =====

    // Representación en texto del adoptante, incluye los datos de Persona
    // más el nombre del gato adoptado
    @Override
    public String toString() {
        return super.toString() + "  Gato_Adoptado: " + gato_Adoptado;
    }
}