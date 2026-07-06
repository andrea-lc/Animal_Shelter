
package EstructurasDeDatos;

import Entidades.Adoptantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de una Lista Enlazada Simple para almacenar los adoptantes
 * registrados en el sistema.
 *
 * Una lista simplemente enlazada está formada por nodos donde cada uno
 * conoce únicamente al siguiente nodo. El último elemento apunta a null,
 * indicando el final de la estructura.
 *
 * En este proyecto esta estructura reemplaza al uso de HashMap para poder
 * demostrar el funcionamiento de una estructura de datos lineal creada
 * manualmente.
 *
 * Permite realizar operaciones como:
 * - Registrar adoptantes.
 * - Insertar registros de forma ordenada.
 * - Buscar por DNI o nombre.
 * - Eliminar registros.
 * - Recorrer todos los elementos desde el primero hasta el último.
 *
 * Su principal ventaja es su simplicidad y el bajo consumo de memoria, ya
 * que cada nodo únicamente mantiene una referencia hacia el siguiente
 * elemento.
 */
public class ListaEnlazadaSimple {
    
    private Nodo<Adoptantes> cabeza;
    private int tamaño;
    
    public ListaEnlazadaSimple() {
        this.cabeza = null;
        this.tamaño = 0;
    }
    
    // ============================================================
    // OPERACIONES DE INSERCIÓN
    // ============================================================

    /**
     * Inserta un adoptante al final de la lista.
     * Complejidad: O(n), ya que es necesario recorrer la lista
     * hasta encontrar el último nodo.
     */
    public void insertarAlFinal(Adoptantes adoptante) {
        Nodo<Adoptantes> nuevo = new Nodo<>(adoptante);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<Adoptantes> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }
    
    /**
    * Inserta un adoptante manteniendo la lista ordenada
    * alfabéticamente por nombre.
    * Complejidad: O(n).
    */
    public void insertarOrdenado(Adoptantes adoptante) {
        Nodo<Adoptantes> nuevo = new Nodo<>(adoptante);
        
        if (cabeza == null || 
            adoptante.getNombre().compareTo(cabeza.dato.getNombre()) < 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo<Adoptantes> actual = cabeza;
            while (actual.siguiente != null && 
                   adoptante.getNombre().compareTo(actual.siguiente.dato.getNombre()) > 0) {
                actual = actual.siguiente;
            }
            nuevo.siguiente = actual.siguiente;
            actual.siguiente = nuevo;
        }
        tamaño++;
    }
    
    // ============================================================
    // OPERACIONES DE BÚSQUEDA
    // ============================================================

    /**
     * Busca un adoptante por su DNI.
     * Complejidad: O(n).
     */
    public Adoptantes buscarPorDNI(int dni) {
        Nodo<Adoptantes> actual = cabeza;
        while (actual != null) {
            if (actual.dato.getDni_persona() == dni) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    /**
    * Busca un adoptante por su nombre.
    * Complejidad: O(n).
    */
    public Adoptantes buscarPorNombre(String nombre) {
        Nodo<Adoptantes> actual = cabeza;
        while (actual != null) {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }
    
    // ============================================================
    // OPERACIONES DE ELIMINACIÓN
    // ============================================================

    /**
     * Elimina un adoptante utilizando su DNI.
     * Complejidad: O(n), ya que puede ser necesario recorrer
     * toda la lista hasta encontrar el elemento.
     */
    public boolean eliminar(int dni) {
        if (cabeza == null) return false;
        
        // Si es la cabeza
        if (cabeza.dato.getDni_persona() == dni) {
            cabeza = cabeza.siguiente;
            tamaño--;
            return true;
        }
        
        Nodo<Adoptantes> actual = cabeza;
        while (actual.siguiente != null && 
               actual.siguiente.dato.getDni_persona() != dni) {
            actual = actual.siguiente;
        }
        
        if (actual.siguiente != null) {
            actual.siguiente = actual.siguiente.siguiente;
            tamaño--;
            return true;
        }
        return false;
    }
    
    // ============================================================
    // OPERACIONES DE RECORRIDO
    // ============================================================

    /**
     * Recorre la lista desde la cabeza hasta el último nodo,
     * mostrando la información de cada adoptante.
     * El recorrido termina cuando el siguiente nodo es null.
     * Complejidad: O(n).
     */
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista vacía");
            return;
        }
        
        System.out.println("\n=== LISTA ENLAZADA SIMPLE ===");
        System.out.println("Total: " + tamaño + " adoptantes");
        System.out.println("-----------------------------------");
        
        Nodo<Adoptantes> actual = cabeza;
        int pos = 1;
        while (actual != null) {
            System.out.println(pos++ + ". " + actual.dato.getNombre() +
                             " (DNI: " + actual.dato.getDni_persona() + 
                             ") -> Gato: " + actual.dato.getGato_Adoptado());
            actual = actual.siguiente;//Avanza al siguiente
        }
        System.out.println("-----------------------------------");
        System.out.println("null (fin de la lista)"); //EVIDENCIA: El último apunta a null
    }
    
    /**
    * Devuelve todos los adoptantes almacenados en una List.
    * Se utiliza para exportar información o guardar en archivo.
    * Complejidad: O(n).
    */
    public List<Adoptantes> obtenerTodos() {
        List<Adoptantes> lista = new ArrayList<>();
        Nodo<Adoptantes> actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }
    
    // ============================================================
    // MÉTODOS UTILITARIOS
    // ============================================================

    /** @return Primer nodo de la lista. */
    public Nodo<Adoptantes> getCabeza() { return cabeza; }
    /** @return Cantidad de adoptantes registrados. */
    public int getTamaño() { return tamaño; }
    /** @return true si la lista está vacía. */
    public boolean estaVacia() { return cabeza == null; }
}