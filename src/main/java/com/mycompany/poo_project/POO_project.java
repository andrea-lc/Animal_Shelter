/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poo_project;
import Entidades.Administradores;
import Entidades.Persona;
import Menus.Menu;
import Gestores.*;
import Scanner.Lector;

/**
 *
 * @author andrea
 */
// Clase principal del proyecto
public class POO_project {
    public static void main(String[] args) { 
        
        Lector lector=Lector.getInstanciaLector();
        // Instancia del gestor de usuarios (encargado de manejar login y registro)
        Gestor_usuarios usuarios= Gestor_usuarios.getInstanciaUsuario();
        Menu menu = new Menu();
        int opcion;
       
        do {
            System.out.println("\n========== Cat Haven ==========");
            System.out.println("1) Iniciar sesion");
            System.out.println("2) Registrar nuevo Usuario");
            System.out.println("3) Salir");
            System.out.println("================================");
            System.out.print("Ingrese una opcion: ");
            opcion = lector.LeerEntero();
            
            switch (opcion) {
                case 1: { 
                    while (true) {
                        System.out.print("Correo: ");
                        String correo = lector.LeerString();
                        System.out.print("Contraseña: ");
                        String contraseña = lector.LeerString();
                        if (usuarios.login(correo, contraseña)) {
                            menu.MainMenu();
                            break;
                        }

                        System.out.println("Credenciales incorrectas");
                        System.out.print("Reintentar? (si/no): ");
                        if (lector.LeerString().equalsIgnoreCase("no")) {
                            break;
                        }
                    }
                    break; 
                }
                case 2: {
                    // Caso 2: Registrar un nuevo usuario       
                    System.out.print("Correo: ");
                    String correo = lector.LeerString();             
                    System.out.print("Nombre de Usuario: ");
                    String nombre = lector.LeerStringMayuscula();
                    System.out.print("Apellido: ");  
                    String apellido = lector.LeerStringMayuscula();                    
                    System.out.print("Dni: ");
                    int dni= lector.LeerEntero();                 
                    System.out.print("Telefono: ");
                    int telefono= lector.LeerEntero();                
                    System.out.print("Cree una contraseña: ");
                    String contraseña = lector.LeerString();                  
                    Administradores admin=new Administradores (new Persona(dni,nombre,apellido,telefono,correo)
                            ,contraseña);
                    // Llamar al metodo del gestor para registrar un nuevo usuario
                    usuarios.registrar(admin);
                    break;
                }
                case 3: { System.out.println("Saliendo...");
                    break;
                    }
                    // Caso 3: Salir del programa
                    // No se hace nada, el bucle terminará porque opcion = 3
                
                default:
                    // Manejo de entrada invalida
                    System.out.println("Ingrese una opcion valida");
            }
        } while (opcion != 3); // Condicion de salida: si elige "3", termina el programa
    }
}