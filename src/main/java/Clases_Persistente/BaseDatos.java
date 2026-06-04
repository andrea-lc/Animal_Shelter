/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases_Persistente;

import Entidades.Administradores;
import Entidades.Adoptantes;
import Entidades.Gatos;
import Entidades.Voluntarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class BaseDatos {  
    private BaseDatos() {}
    
    
    /**
     * Guarda TODOS los gatos en la BD 
     * @param gatos //la lista que se quiere guarda en bd
     */
    public static void guardarGatosEnBD(List<Gatos> gatos) {
        try (Connection conn = ConexionDB.obtenerConexion()) { //obtiene la conexion a la bd
            // 1. Limpiar tabla gatos
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM gatos")) {
                stmt.executeUpdate(); // crea un comando en este caso delete y lo ejecuta con executeUpdate
            }
            
            // 2. Insertar todos los gatos y los?? marcan la posicion y luego se rellenan
            String sql = "INSERT INTO gatos (id, nombre, edad, raza, peso, genero, esterilizacion, estado_gato, cuidado_requerido) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Gatos gato : gatos) { // recorre la lista
                    stmt.setInt(1, gato.getId());  //setInt(posición, valor) posición 1 = primer ?
                    stmt.setString(2, gato.getNombre());
                    stmt.setInt(3, gato.getEdad());
                    stmt.setString(4, gato.getRaza());
                    stmt.setDouble(5, gato.getPeso());
                    stmt.setString(6, gato.getGenero());
                    stmt.setString(7, gato.getEsterilizacion());
                    stmt.setString(8, gato.getEstado_gato());
                    stmt.setString(9, gato.getCuidado_requerido());
                    stmt.executeUpdate();
                }
            }
            
            System.out.println( gatos.size() + " gatos guardados en BD");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar gatos en BD: ");
        }
    }
    
    /**
     * Guarda TODOS los adoptantes en la BD (igual que SerializadorXML)
     * @param adoptantes
     */
    public static void guardarAdoptantesEnBD(List<Adoptantes> adoptantes) {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            // 1. Limpiar tablas
            try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM adoptantes");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM personas WHERE tipo = 'Adoptante'")) {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
            }
            
            // 2. Insertar todos los adoptantes
            String sqlPersona = "INSERT INTO personas (dni, nombre, apellido, telefono, correo, tipo) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlAdoptante = "INSERT INTO adoptantes (dni, gato_adoptado) VALUES (?, ?)";
            
            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtAdoptante = conn.prepareStatement(sqlAdoptante)) {
                
                for (Entidades.Adoptantes adoptante : adoptantes) {
                    // Insertar en personas
                    stmtPersona.setInt(1, adoptante.getDni_persona());
                    stmtPersona.setString(2, adoptante.getNombre());
                    stmtPersona.setString(3, adoptante.getApellido());
                    stmtPersona.setInt(4, adoptante.getTelefono());
                    stmtPersona.setString(5, adoptante.getCorreo());
                    stmtPersona.setString(6, "Adoptante");
                    stmtPersona.executeUpdate();
                    
                    // Insertar en adoptantes
                    stmtAdoptante.setInt(1, adoptante.getDni_persona());
                    stmtAdoptante.setString(2, adoptante.getGato_Adoptado());
                    stmtAdoptante.executeUpdate();
                }
            }
            
            System.out.println(adoptantes.size() + " adoptantes guardados en BD");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar adoptantes en BD: ");
        }
    }
    
    /**
     * Guarda TODOS los voluntarios en la BD (igual que SerializadorXML)
     * @param voluntarios
     */
    public static void guardarVoluntariosEnBD(List<Voluntarios> voluntarios) {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            // 1. Limpiar tablas
            try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM voluntarios");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM personas WHERE tipo = 'Voluntario'")) {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
            }
            
            // 2. Insertar todos los voluntarios
            String sqlPersona = "INSERT INTO personas (dni, nombre, apellido, telefono, correo, tipo) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlVoluntario = "INSERT INTO voluntarios (dni, horarios_disponibles) VALUES (?, ?)";
            
            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtVoluntario = conn.prepareStatement(sqlVoluntario)) {
                
                for (Entidades.Voluntarios voluntario : voluntarios) {
                    // Insertar en personas
                    stmtPersona.setInt(1, voluntario.getDni_persona());
                    stmtPersona.setString(2, voluntario.getNombre());
                    stmtPersona.setString(3, voluntario.getApellido());
                    stmtPersona.setInt(4, voluntario.getTelefono());
                    stmtPersona.setString(5, voluntario.getCorreo());
                    stmtPersona.setString(6, "Voluntario");
                    stmtPersona.executeUpdate();
                    
                    // Insertar en voluntarios
                    stmtVoluntario.setInt(1, voluntario.getDni_persona());
                    stmtVoluntario.setString(2, voluntario.getHorarios_disponibles());
                    stmtVoluntario.executeUpdate();
                }
            }
            
            System.out.println(voluntarios.size() + " voluntarios guardados en BD");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar voluntarios en BD: ");
        }
    }
    
    /**
     * Guarda TODOS los administradores en la BD (si los necesitas)
     * @param administradores
     */
    public static void guardarAdministradoresEnBD(List<Administradores> administradores) {
        try (Connection conn = ConexionDB.obtenerConexion()) {
            // 1. Limpiar tablas
            try (PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM administradores");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM personas WHERE tipo = 'Administrador'")) {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
            }
            
            // 2. Insertar todos los administradores
            String sqlPersona = "INSERT INTO personas (dni, nombre, apellido, telefono, correo, tipo) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlAdmin = "INSERT INTO administradores (dni, contraseña) VALUES (?, ?)";
            
            try (PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona);
                 PreparedStatement stmtAdmin = conn.prepareStatement(sqlAdmin)) {
                
                for (Entidades.Administradores admin : administradores) {
                    // Insertar en personas
                    stmtPersona.setInt(1, admin.getDni_persona());
                    stmtPersona.setString(2, admin.getNombre());
                    stmtPersona.setString(3, admin.getApellido());
                    stmtPersona.setInt(4, admin.getTelefono());
                    stmtPersona.setString(5, admin.getCorreo());
                    stmtPersona.setString(6, "Administrador");
                    stmtPersona.executeUpdate();
                    
                    // Insertar en administradores
                    stmtAdmin.setInt(1, admin.getDni_persona());
                    stmtAdmin.setString(2, admin.getContraseña());
                    stmtAdmin.executeUpdate();
                }
            }
            
            System.out.println(administradores.size() + " administradores guardados en BD");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar administradores en BD: ");
        }
    }  
}
