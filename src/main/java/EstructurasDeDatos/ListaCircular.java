/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDeDatos;
import Entidades.Voluntarios;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ListaCircular {
    public static class Nodo {
        public Voluntarios voluntario;
        public Nodo siguiente;
        
        public Nodo(Voluntarios voluntario) {
            this.voluntario = voluntario;
            this.siguiente = null;
        }
    }
    
    private Nodo cabeza;
    private Nodo cola;
    private int tamaño;
    
    public ListaCircular() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }
    
    // ===== OPERACIONES DE INSERCIÓN =====
    
    public void insertarAlFinal(Voluntarios voluntario) {
        Nodo nuevo = new Nodo(voluntario);
        
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
            nuevo.siguiente = nuevo; // Apunta a sí mismo (circular)
        } else {
            cola.siguiente = nuevo;
            nuevo.siguiente = cabeza; // El último apunta a la cabeza
            cola = nuevo;
        }
        tamaño++;
    }
    
    public void insertarOrdenado(Voluntarios voluntario) {
        Nodo nuevo = new Nodo(voluntario);
        
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
            nuevo.siguiente = nuevo;
            tamaño++;
            return;
        }
        
        // Si debe ir al inicio
        if (voluntario.getNombre().compareTo(cabeza.voluntario.getNombre()) < 0) {
            nuevo.siguiente = cabeza;
            cola.siguiente = nuevo;
            cabeza = nuevo;
            tamaño++;
            return;
        }
        
        Nodo actual = cabeza;
        while (actual != cola && 
               voluntario.getNombre().compareTo(actual.siguiente.voluntario.getNombre()) > 0) {
            actual = actual.siguiente;
        }
        
        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
        
        if (actual == cola) {
            cola = nuevo;
        }
        tamaño++;
    }
    
    // ===== OPERACIONES DE BÚSQUEDA =====
    
    public Voluntarios buscarPorDNI(int dni) {
        if (cabeza == null) return null;
        
        Nodo actual = cabeza;
        do {
            if (actual.voluntario.getDni_persona() == dni) {
                return actual.voluntario;
            }
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return null;
    }
    
    public Voluntarios buscarPorNombre(String nombre) {
        if (cabeza == null) return null;
        
        Nodo actual = cabeza;
        do {
            if (actual.voluntario.getNombre().equalsIgnoreCase(nombre)) {
                return actual.voluntario;
            }
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return null;
    }
    
    // ===== OPERACIONES DE ROTACIÓN (VENTAJA DE LA CIRCULAR) =====
    
    /**
     * Ventaja de la lista circular: Rotar la lista
     * Útil para rotar turnos, horarios, etc.
     */
    public void rotar() {
        if (cabeza != null && tamaño > 1) {
            cabeza = cabeza.siguiente;
            cola = cola.siguiente;
        }
    }
    
    // ===== OPERACIONES DE ELIMINACIÓN =====
    
    public boolean eliminar(int dni) {
        if (cabeza == null) return false;
        
        Nodo actual = cabeza;
        Nodo anterior = cola; // El anterior al inicio es la cola
        
        do {
            if (actual.voluntario.getDni_persona() == dni) {
                // Si es el único elemento
                if (tamaño == 1) {
                    cabeza = null;
                    cola = null;
                } else {
                    anterior.siguiente = actual.siguiente;
                    if (actual == cabeza) {
                        cabeza = actual.siguiente;
                    }
                    if (actual == cola) {
                        cola = anterior;
                    }
                }
                tamaño--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return false;
    }
    
    // ===== OPERACIONES DE VISUALIZACIÓN =====
    
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista circular vacía");
            return;
        }
        
        System.out.println("\nLISTA CIRCULAR DE VOLUNTARIOS");
        System.out.println("Total: " + tamaño + " voluntarios");
        System.out.println("El último apunta al primero (forma un ciclo)");
        System.out.println("-----------------------------------");
        
        Nodo actual = cabeza;
        int pos = 1;
        do {
            System.out.println(pos++ + ". " + actual.voluntario.getNombre() +
                             " (DNI: " + actual.voluntario.getDni_persona() + 
                             ") -> Horario: " + actual.voluntario.getHorarios_disponibles());
            actual = actual.siguiente;
        } while (actual != cabeza);
        System.out.println("-----------------------------------");
        System.out.println("Vuelve al inicio: " + cabeza.voluntario.getNombre());
    }
    
    // ===== MÉTODOS UTILITARIOS =====
    
    public List<Voluntarios> obtenerTodos() {
        List<Voluntarios> lista = new ArrayList<>();
        if (cabeza == null) return lista;
        
        Nodo actual = cabeza;
        do {
            lista.add(actual.voluntario);
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return lista;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public Nodo getCabeza() {
        return cabeza;
    }
    
    public Nodo getCola() {
        return cola;
    }
}
