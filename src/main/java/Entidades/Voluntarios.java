/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author admin
 */
public class Voluntarios extends Persona {
    public String Horarios_disponibles;

    public Voluntarios() {
    }

    public Voluntarios(Persona persona, String Horarios_disponibles) {
        super(persona);
        this.Horarios_disponibles = Horarios_disponibles;
    }

    public String getHorarios_disponibles() {
        return Horarios_disponibles;
    }

    public void setHorarios_disponibles(String Horarios_disponibles) {
        this.Horarios_disponibles = Horarios_disponibles;
    }

    @Override
    public String toString() {
        return super.toString() + "  Horarios:" + Horarios_disponibles + "\n";
    }
}
