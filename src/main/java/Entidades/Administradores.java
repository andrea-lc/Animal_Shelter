/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa a los administradores del sistema Cat Haven.
 * Hereda de Persona y agrega funcionalidades específicas como contraseña,
 * control de login y bloqueo de cuenta por intentos fallidos.
 *
 * @author admin
 */
public class Administradores extends Persona {

    // Contraseña que usa el administrador para iniciar sesión
    private String contraseña;

    // Fecha y hora del último login exitoso del administrador
    private LocalDateTime fechaUltimoLogin;

    // Nuevo atributo para indicar si la cuenta está bloqueada
    private boolean bloqueado;

    // Hora a partir de la cual la cuenta bloqueada podrá volver a usarse
    private LocalDateTime horaDesbloqueo;

    // Constructor vacío
    public Administradores() {
    }

    // Constructor que recibe una Persona existente y una contraseña.
    // Inicializa la fecha de login en null y la cuenta como no bloqueada.
    public Administradores(Persona otraPersona, String contraseña) {
        super(otraPersona);
        this.contraseña = contraseña;
        this.fechaUltimoLogin = null;
        this.bloqueado = false; // Inicialmente la cuenta no esta bloqueada
    }

    // ===== GETTERS Y SETTERS =====

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

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public LocalDateTime getHoraDesbloqueo() {
        return horaDesbloqueo;
    }

    public void setHoraDesbloqueo(LocalDateTime horaDesbloqueo) {
        this.horaDesbloqueo = horaDesbloqueo;
    }

    // ===== MÉTODOS AUXILIARES =====

    // Devuelve la fecha del último login formateada como "dd/MM/yyyy HH:mm:ss".
    // Si nunca ha iniciado sesión, devuelve "Sin registro".
    public String getFechaFormateada() {
        if (this.fechaUltimoLogin == null) {
            return "Sin registro";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return this.fechaUltimoLogin.format(formatter);
    }

    // Representación en texto del administrador, incluye los datos de Persona
    // más la fecha del último login.
    @Override
    public String toString() {
        return super.toString() + String.format("  Ultimo Login: %s", getFechaFormateada());
    }

    // Versión extendida de toString usada para guardar en archivos de texto.
    // Incluye también el estado de bloqueo del usuario.
    public String aTexto() {
        return super.toString()
                + String.format("  Ultimo Login: %s\n  Usuario Bloqueado: %s ",
                        getFechaFormateada(),
                        isBloqueado() ? "Usuario Bloqueado" : "El usuario no esta bloqueado");
    }
}