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
import Entidades.Adoptantes;
import EstructurasDeDatos.ListaEnlazadaSimple;

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
            System.out.println("4) ESTRUCTURAS DE DATOS");
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
                case 4: {
                    mostrarMenuEstructuras(); 
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
        do {
            System.out.println("\n=== Gestion de Voluntarios ===");
            System.out.println("1) Registrar nuevo Voluntario");
            System.out.println("2) Listar volutarios");
            System.out.println("3) Buscar voluntario");
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
    
    private void mostrarMenuEstructuras() {
    int opcion;
    
    // Crear instancias de las estructuras para practicar
    ListaEnlazadaSimple listaSimple = new ListaEnlazadaSimple();
    
    // CARGAR DATOS DESDE TU GESTOR EXISTENTE
    // Tomamos los adoptantes que ya estan guardados en tu sistema y los cargamos en la lista enlazada para poder trabajar con ellos
    System.out.println("Cargando datos");
    listaSimple.cargarDesdeLista(Gestor_Adoptante.getInstanciaAdoptantes().getElementos_listaporNombre());
    
    do {
        System.out.println("========================");
        System.out.println("ESTRUCTURAS DE DATOS");
        System.out.println("========================");
        System.out.println("LISTA ENLAZADA SIMPLE");
        System.out.println("1) Mostrar lista simple de adoptantes");
        System.out.println("2) Insertar adoptante al inicio");
        System.out.println("3) Insertar adoptante ordenado por nombre");
        System.out.println("4) Eliminar adoptante por DNI");
        System.out.println("5) Buscar adoptante por nombre");
        System.out.println("0) Volver al menu principal");
        System.out.println("========================");
        System.out.print("Elige una opcion: ");
        
        opcion = lector.LeerEntero();
         switch(opcion) {
            case 1:
                listaSimple.mostrar();
                break;
            case 2:
                System.out.println("\n=== INSERTAR ADOPTANTE AL INICIO ===");
                System.out.print("DNI: ");
                int dniInicio = lector.LeerEntero();
                
                System.out.print("Nombre: ");
                String nombreInicio = lector.LeerStringMayuscula();
                
                System.out.print("Gato adoptado: ");
                String gatoInicio = lector.LeerStringMayuscula();
                
                Adoptantes nuevoInicio = new Adoptantes(new Entidades.Persona(dniInicio, nombreInicio, "", 0, ""),gatoInicio);
                listaSimple.insertarAlInicio(nuevoInicio);
                break;
            case 3:
                System.out.println("\n=== INSERTAR ADOPTANTE ORDENADO ===");
                System.out.print("DNI: ");
                int dniOrden = lector.LeerEntero();
                
                System.out.print("Nombre: ");
                String nombreOrden = lector.LeerStringMayuscula();
                
                System.out.print("Gato adoptado: ");
                String gatoOrden = lector.LeerStringMayuscula();
                
                Adoptantes nuevoOrden = new Adoptantes(new Entidades.Persona(dniOrden, nombreOrden, "", 0, ""),gatoOrden);
                listaSimple.insertarOrdenado(nuevoOrden);
                break;
            case 4:
                System.out.print("Ingrese DNI del adoptante a eliminar: ");
                int dniEliminar = lector.LeerEntero();
                
                listaSimple.eliminar(dniEliminar);
                break;
            case 5:
                System.out.print("Ingrese nombre del adoptante a buscar: ");
                String nombreBuscar = lector.LeerString();
                Adoptantes encontrado = listaSimple.buscar(nombreBuscar);
                
                if (encontrado != null) {
                    System.out.println("Adoptante: " + encontrado.getNombre() + ", Gato: " + encontrado.getGato_Adoptado());
                }
                break;
            case 0:
                System.out.println("Volviendo al menu principal");
                break;
            default:
                System.out.println("Opcion invalida");
        }
    } while(opcion != 0);
}
}