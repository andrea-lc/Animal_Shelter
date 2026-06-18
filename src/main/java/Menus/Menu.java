/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import Clases_Persistente.BaseDatos;
import Gestores.Gestor_Adoptante;
import Gestores.Gestor_Gatos;
import Gestores.Gestor_Voluntario;
import Scanner.Lector;
import Clases_Persistente.SerializadorXML;

/**
 *
 * @author admin
 */
/* ACA IMPLEMENTO ESE PATRON SINGLETON >:(: menu sera el cliente, osea el que utiliza el algoritmo (menu acciones)
* menu acciones es una interfaz con metodos abstractos que luego es implementada por las clases
* acciones adoptante-gatos, cada una de estas clases implementa la interfaz de forma diferente 
* quiere decir que su algoritmo varia segun sus necesidades
*/
public class Menu {
    private Menu_Acciones acciones;  
    Lector lector=Lector.getInstanciaLector();
    
    // Menu_acciones puede tene multiples comportamientos, asi que segun 
    // la situacion, deberia poder variar, por eso este metodo
    private void setAcciones(Menu_Acciones nuevasAcciones) {
        this.acciones = nuevasAcciones;
    }
       
    // Se encarga de mostrar el menú principal en consola
    public void MainMenu() {    
        int opcion;
        do {
            System.out.println("\n========== Cat Haven ==========");
            System.out.println("1) Gestion de Gatos");
            System.out.println("2) Gestion Adopciones");
            System.out.println("3) Gestion Voluntariado");
            System.out.println("0) Cerrar sesion");
            System.out.println("================================");
            System.out.print("Elige una opcion: ");
            
            // Leer opción ingresada por el usuario
            opcion = lector.LeerEntero();

            switch (opcion) {
                case 1: {
                    // Caso 1: Llama al modulo de gestión de gatos
                    mostrarMenuGatos();
                    break;
                }
                case 2: {
                    // Caso 2: nodulo de adoptantes
                    mostrarMenuAdoptantes();
                    break;
                }
                case 3: {
                    // Caso 3: modulo de voluntarios
                    mostrarMenuVoluntarios();
                    break;
                }
                case 0: {
                    // Caso 0: Salir del sistema
                    System.out.println("Cerrando sesion...");
                    break;
                }
                default: {
                    // Opción inválida: mensaje de errror
                    System.out.println("Opcion invalida.");
                }
            }
        } while (opcion != 0); // Repite hasta que el usuario elija salir
        System.out.println("\n Creando respaldos XML...");
        SerializadorXML.guardarEnXML(Gestor_Gatos.getInstanciaGatos().getElementos_listaporId(),"xml/gatos.xml");
        SerializadorXML.guardarEnXML(Gestor_Adoptante.getInstanciaAdoptantes().getElementos_listaporNombre(),"xml/adoptantes.xml");
        SerializadorXML.guardarEnXML(Gestor_Voluntario.getInstanciaVoluntarios().getElementos_listaporNombre(),"xml/voluntarios.xml");
        
               
         System.out.println("\n Guardando respaldos en Base de Datos...");
        BaseDatos.guardarGatosEnBD(Gestor_Gatos.getInstanciaGatos().getElementos_listaporId());
        BaseDatos.guardarAdoptantesEnBD(Gestor_Adoptante.getInstanciaAdoptantes().getElementos_listaporNombre());
        BaseDatos.guardarVoluntariosEnBD(Gestor_Voluntario.getInstanciaVoluntarios().getElementos_listaporNombre());
    }
    
    private void mostrarMenuGatos() {
        int opcion;
        setAcciones(new Acciones_gatos());
        do {
            System.out.println("\n=== Gestion de Gatos ===");
            System.out.println("1) Registrar nuevo gato");
            System.out.println("2) Listar gatos");
            System.out.println("3) Buscar gato por nombre");
            System.out.println("4) Modificar algun dato");
            System.out.println("5) Eliminar algun dato");
            System.out.println("0) Volver");
            System.out.println("========================");
            System.out.print("Elige una opcion: ");
            
            // Leer la opcion elegida
            opcion = lector.LeerEntero();
            // Manejar la opción seleccionada
            switch (opcion) {
                case 1: {
                    // Registrar un nuevo gato
                    acciones.registrar();                   
                    break;
                }
                case 2: {
                    // Listar todos los gatos
                    acciones.Listar();
                    break;
                }
                case 3: {
                    // Buscar ung ato
                    acciones.Buscar();
                    break;
                }
                case 4: {
                    // Modificar algun dato
                    acciones.modificar();
                    break;
                }
                case 5: {
                    acciones.eliminar();
                }
                case 0: {
                    // Volver al menú principal
                    System.out.println("Volviendo al menu principal...");
                    break;
                }
                default: {
                    // Manejo de opción invalida
                    System.out.println("Opción invalida.");
                }
            }
        } while (opcion != 0); // Se repite hasta que el usuario decida salir
    }
    private void mostrarMenuAdoptantes (){
        int opcion;
        setAcciones(new Acciones_Adoptante());
        do {
            System.out.println("\n=== Gestion de Adoptantes ===");
            System.out.println("1) Registrar nueva Adopcion");
            System.out.println("2) Listar adoptantes");
            System.out.println("3) Buscar Adoptante");
            System.out.println("4) Modificar algun dato");
            System.out.println("5) Eliminar algun dato");
            System.out.println("0) Volver");
            System.out.println("========================");
            System.out.print("Elige una opcion: ");
            
            // Leer la opcion elegida
            opcion = lector.LeerEntero();
            // Manejar la opción seleccionada
            switch (opcion) {
                case 1: 
                    // Registrar un nuevo gato
                    acciones.registrar();                   
                    break;              
                case 2: 
                    // Listar todos los gatos
                    acciones.Listar();
                    break;              
                case 3: 
                    // Buscar un adoptante
                    acciones.Buscar();
                    break;
                case 4: 
                    // Modificar algun dato
                    acciones.modificar();
                    break;
                case 5:
                    // Eliminar algun dato
                    acciones.eliminar();
                    break;
                case 0: 
                    // Volver al menu principal
                    System.out.println("Volviendo al menu principal...");
                    break;
                default: 
                    // Manejo de opción invalida
                    System.out.println("Opcion invalida.");               
            }
        } while (opcion != 0); 
    }
    
    
    private void mostrarMenuVoluntarios(){
        int opcion;
        setAcciones(new Acciones_Voluntario());
        Gestor_Voluntario gestor = Gestor_Voluntario.getInstanciaVoluntarios();
        do {
            System.out.println("\n=== Gestion de Voluntarios ===");
            System.out.println("1) Registrar nuevo Voluntario");
            System.out.println("2) Listar volutarios");
            System.out.println("3) Buscar voluntario");
            System.out.println("4) Modificar algun dato");
            System.out.println("5) Eliminar algun dato");
            System.out.println("6) Rotar voluntarios (turnos)  ");
            System.out.println("7) Listar desde el FINAL");
            System.out.println("0) Volver");
            System.out.println("========================");
            System.out.print("Elige una opcion: ");
            
            // Leer la opcion elegida
            opcion = lector.LeerEntero();
            // Manejar la opción seleccionada
            switch (opcion) {
                case 1: {
                    acciones.registrar();                   
                    break;
                }
                case 2: {
                    acciones.Listar();
                    break;
                }
                case 3: {
                    acciones.Buscar();
                    break;
                }
                case 4: 
                    acciones.modificar();
                    break;
                case 5:
                    acciones.eliminar();
                    break;
                case 6:
                    gestor.rotarVoluntarios();
                    break;
                case 7:
                    gestor.mostrarDobleDesdeFinal();
                    break;
                case 0: {
                    System.out.println("Volviendo al menu principal...");
                    break;
                }
                default: {
                    System.out.println("Opcion invalida.");
                }
            }
        } while (opcion != 0); 
    }
}