/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDeDatos;

import Entidades.Adoptantes;
import java.util.List;

/**
 *
 * @author USER
 */
public class ListaEnlazadaSimple {
    
    // Clase Nodo interna
    private static class Nodo {
        Adoptantes adoptante;
        Nodo siguiente;
        
        Nodo(Adoptantes adoptante) {
            this.adoptante = adoptante;
            this.siguiente = null;
        }
    }
    
    private Nodo cabeza;
    private int tamaño;
    
    public ListaEnlazadaSimple() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    /**
     * CARGA DATOS DESDE TU GESTOR EXISTENTE
     * Toma todos los adoptantes que ya tienes guardados y los inserta uno por uno al final de esta lista enlazada.
     */
    public void cargarDesdeLista(List<Adoptantes> listaAdoptantes) {
        for (Adoptantes adoptante : listaAdoptantes) {
            insertarAlFinal(adoptante);// Inserta cada adoptante al final
        }
    }
    
    /**
     * INSERCION AL INICIO (O(1))
     */
    public void insertarAlInicio(Adoptantes adoptante) {
        Nodo nuevo = new Nodo(adoptante); // PASO 1: Crear nuevo nodo
        nuevo.siguiente = cabeza;// PASO 2: Nuevo nodo apunta a la antigua cabeza
        cabeza = nuevo;// PASO 3: La cabeza ahora es el nuevo nodo
        tamaño++;// PASO 4: Incrementar tamaño
    }
    
    /**
     * INSERCION AL FINAL (O(n))
     */
    public void insertarAlFinal(Adoptantes adoptante) {
        Nodo nuevo = new Nodo(adoptante);// PASO 1: Crear nuevo nodo
        
        if (cabeza == null) { // PASO 2: ¿Lista vacía?
            cabeza = nuevo; //   Sí: el nuevo es el primer y único nodo
        } else {
            Nodo actual = cabeza;// PASO 3: Empezar desde la cabeza
            // Recorrer hasta encontrar el último nodo (el que tiene siguiente = null)
            while (actual.siguiente != null) {
                actual = actual.siguiente;// Avanzar al siguiente nodo
            }
            actual.siguiente = nuevo; // PASO 4: El último nodo apunta al nuevo
        }
        tamaño++; // PASO 5: Incrementar tamaño
    }
    
    /**
     * INSERCION ORDENADA POR NOMBRE
     */
    public void insertarOrdenado(Adoptantes adoptante) {
        Nodo nuevo = new Nodo(adoptante);
        
        // Si la lista está vacía o el nuevo va al inicio
        if (cabeza == null || 
            adoptante.getNombre().compareTo(cabeza.adoptante.getNombre()) < 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null && 
                   adoptante.getNombre().compareTo(actual.siguiente.adoptante.getNombre()) > 0) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamaño++;
        System.out.println("Adoptante insertado ordenadamente: " + adoptante.getNombre());
    }
    
    /**
     * ELIMINACION POR DNI
     */
    public boolean eliminar(int dni) {
        if (cabeza == null) {
            System.out.println("Lista vacia");
            return false;
        }
        
        // Si el elemento a eliminar es la cabeza
        if (cabeza.adoptante.getDni_persona() == dni) {
            cabeza = cabeza.siguiente;
            tamaño--;
            System.out.println("Adoptante eliminado (era la cabeza)");
            return true;
        }
        
        // Buscar en el resto de la lista
        Nodo actual = cabeza;
        while (actual.siguiente != null && 
               actual.siguiente.adoptante.getDni_persona() != dni) {
            actual = actual.siguiente;
        }
        
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            System.out.println("Adoptante eliminado");
            return true;
        }
        
        System.out.println("Adoptante no encontrado");
        return false;
    }
    
    /**
     * BUUSQUEDA POR NOMBRE
     */
    public Adoptantes buscar(String nombre) {
        int posicion = 0;
        Nodo actual = cabeza;
        
        while (actual != null) {
            posicion++;
            if (actual.adoptante.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Adoptante encontrado en posicion " + posicion);
                return actual.adoptante;
            }
            actual = actual.siguiente;
        }
        
        System.out.println("Adoptante no encontrado");
        return null;
    }
    
    /**
     * RECORRIDO COMPLETO (MOSTRAR LISTA)
     */
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista enlazada simple vacia");
            return;
        }
        
        System.out.println("\n=== LISTA ENLAZADA SIMPLE DE ADOPTANTES ===");
        System.out.println("Total: " + tamaño + " adoptantes");
        System.out.println("Recorrido: CABEZA → NODOS → FINAL");
        System.out.println("-----------------------------------");
        
        Nodo actual = cabeza;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion++ + ". " + actual.adoptante.getNombre() + 
                             " (DNI: " + actual.adoptante.getDni_persona() + 
                             ") → Gato: " + actual.adoptante.getGato_Adoptado());
            actual = actual.siguiente;
        }
        System.out.println("-----------------------------------");
        System.out.println("null (fin de la lista)");
    }
    
    /**
     * OBTENER ELEMENTO EN POSICION ESPECIFICA
     */
    public Adoptantes obtenerPosicion(int posicion) {
        if (posicion < 1 || posicion > tamaño) {
            System.out.println("Posicion invalida");
            return null;
        }
        
        Nodo actual = cabeza;
        for (int i = 1; i < posicion; i++) {
            actual = actual.siguiente;
        }
        
        System.out.println("En posicion " + posicion + ": " + actual.adoptante.getNombre());
        return actual.adoptante;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
}
