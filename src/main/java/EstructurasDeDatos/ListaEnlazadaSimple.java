
package EstructurasDeDatos;

import Entidades.Adoptantes;


public class ListaEnlazadaSimple {
    
    // Clase Nodo interna
    public static class Nodo {
        public Adoptantes adoptante;
        public Nodo siguiente;
        
        public Nodo(Adoptantes adoptante) {
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
    
    // ===== MÉTODOS PRINCIPALES =====
    
    // Insertar al final (O(n))
    public void insertarAlFinal(Adoptantes adoptante) {
        Nodo nuevo = new Nodo(adoptante);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }
    
    // Insertar ordenado por nombre (O(n))
    public void insertarOrdenado(Adoptantes adoptante) {
        Nodo nuevo = new Nodo(adoptante);
        
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
    }
    
    // Buscar por DNI (O(n))
    public Adoptantes buscarPorDNI(int dni) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.adoptante.getDni_persona() == dni) {
                return actual.adoptante;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    // Buscar por nombre (O(n))
    public Adoptantes buscarPorNombre(String nombre) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.adoptante.getNombre().equalsIgnoreCase(nombre)) {
                return actual.adoptante;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    // Eliminar por DNI (O(n))
    public boolean eliminar(int dni) {
        if (cabeza == null) return false;
        
        // Si es la cabeza
        if (cabeza.adoptante.getDni_persona() == dni) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }
        
        Nodo actual = cabeza;
        while (actual.siguiente != null && 
               actual.siguiente.adoptante.getDni_persona() != dni) {
            actual = actual.siguiente;
        }
        
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            return true;
        }
        return false;
    }
    
    // Mostrar todos los elementos
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista vacía");
            return;
        }
        
        System.out.println("\n=== LISTA ENLAZADA SIMPLE ===");
        System.out.println("Total: " + tamaño + " adoptantes");
        System.out.println("-----------------------------------");
        
        Nodo actual = cabeza;
        int pos = 1;
        while (actual != null) {
            System.out.println(pos++ + ". " + actual.adoptante.getNombre() +
                             " (DNI: " + actual.adoptante.getDni_persona() + 
                             ") -> Gato: " + actual.adoptante.getGato_Adoptado());
            actual = actual.siguiente;//Avanza al siguiente
        }
        System.out.println("-----------------------------------");
        System.out.println("null (fin de la lista)"); //EVIDENCIA: El último apunta a null
    }
    
    // Obtener todos los elementos como lista (para XML/BD)
    public java.util.List<Adoptantes> obtenerTodos() {
        java.util.List<Adoptantes> lista = new java.util.ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            lista.add(actual.adoptante);
            actual = actual.siguiente;
        }
        return lista;
    }
    
    // Getters
    public Nodo getCabeza() { return cabeza; }
    public int getTamaño() { return tamaño; }
    public boolean estaVacia() { return cabeza == null; }
}