/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Administradores;
import Entidades.Persona;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Stack;

/**
 *
 * @author admin
 */
// Clase Gestor_usuarios
public class Gestor_usuarios extends GestorBase<Administradores> {
    /**
     * Idea de cambio:
     * Colas: Intento de login hasta 5 veces,si falla la cuenta se bloqueara
     * Pilas: Historial de logins
     */

    private static Gestor_usuarios instancia;

    // Pila para el historial de loggins
    private Stack<Administradores> historialLogins = new Stack<>();

    public Gestor_usuarios() {
        super("TXT/Administradores.txt");
    }

    public static Gestor_usuarios getInstanciaUsuario() {
        if (instancia == null) {
            instancia = new Gestor_usuarios();
        }
        return instancia;
    }

    @Override
    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    int dni = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String apellido = datos[2];
                    int telefono = Integer.parseInt(datos[3]);
                    String correo = datos[4];
                    String contraseña = datos[5];
                    Administradores administradores = new Administradores(
                            new Persona(dni, nombre, apellido, telefono, correo), contraseña);

                    getElementos().put(correo, administradores);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar voluntarios (puede que el archivo este vacio)");
        }
    }

    @Override
    public boolean registrar(Administradores administrador) {
        if (getElementos().containsKey(administrador.getCorreo())) { // verifica si el map contiene la clave
            System.out.println("Ese correo ya esta registrado.");
            return false;
        }

        System.out.println("Usuario agregado correctamente.");

        // Guardar en el HashMap (usando el correo como clave y la Persona como valor)
        getElementos().put(administrador.getCorreo(), administrador);
        guardarCambios();
        return true;
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Administradores admin : getElementos().values()) {
                String linea = String.format("%d,%s,%s,%d,%s,%s",
                        admin.getDni_persona(),
                        admin.getNombre(),
                        admin.getApellido(),
                        admin.getTelefono(),
                        admin.getCorreo(),
                        admin.getContraseña());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de admiistradores");
        }
    }

    public boolean login(String correo, String contraseña) {
        // Verificar si existe algun correo en el sistema
        if (getElementos().isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return false;
        }
        // Verificar si el correo existe en el sistema( en el hashmap)
        if (!getElementos().containsKey(correo)) {
            return false; // correo (usuario) no encontrado
        }
        Administradores valor = getElementos().get(correo); // devolvera el valor asociado a la clave "correo"
        historialLogins.push(valor);// Agregar al historial de logins

        if (valor != null && valor.getContraseña().equals(contraseña)) {
            System.out.println("Bienvenid@ " + valor.getNombre());
            valor.setFechaUltimoLogin(LocalDateTime.now()); // Actualizar la fecha del ultimo login exitoso
            guardarHistorial(); // Guardar el historial de logins exitoso en el archivo
            return true; // login correcto
        } else {
            return false; // contraseña incorrecta o usuario no encontrado
        }
    }

    public void guardarHistorial() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT/HistorialLogins.txt",false))) {
            for(Administradores admin : historialLogins) {
                String linea = String.format("%s,%s,%s,%d,%s,%s, %s",
                        admin.getDni_persona(),
                        admin.getNombre(),
                        admin.getApellido(),
                        admin.getTelefono(),
                        admin.getCorreo(),
                        admin.getContraseña(),
                        admin.getFechaFormateada());
                bw.write(linea);
                bw.newLine();
            }
            
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de historial de logins");
        }
    }

    @Override
    public boolean existe(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modificar(String datoModificar, int opcion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void buscar(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}