package EstructurasDeDatos;

import Entidades.Voluntarios;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de una Lista Circular para almacenar los voluntarios del
 * sistema.
 *
 * En una lista circular el último nodo no apunta a null, sino que vuelve a
 * apuntar al primer nodo (cabeza), formando un ciclo continuo. Gracias a
 * esta característica es posible recorrer la estructura indefinidamente y
 * realizar rotaciones de manera muy eficiente sin mover los datos.
 *
 * En este proyecto la lista circular se utiliza para administrar los
 * voluntarios, permitiendo:
 * - Registrar voluntarios de forma ordenada.
 * - Buscar rápidamente por DNI o nombre.
 * - Eliminar registros.
 * - Recorrer todos los voluntarios.
 * - Rotar la lista para cambiar automáticamente el voluntario inicial,
 *   simulando la rotación de turnos de atención.
 *
 * La principal ventaja de esta estructura es que permite implementar
 * fácilmente sistemas de asignación por turnos o recorridos cíclicos,
 * donde después del último elemento siempre se vuelve al primero.
 */
public class ListaCircular {
    
    /** Primer nodo de la lista circular. */    
    private Nodo<Voluntarios> cabeza;
    /** Último nodo de la lista circular. */
    private Nodo<Voluntarios> cola;
    /** Cantidad de voluntarios almacenados. */
    private int tamaño;
    
    /**
    * Crea una lista circular vacía.
    */
    public ListaCircular() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }
    
    // ===== OPERACIONES DE INSERCIÓN =====
    
    /**
    * Inserta un voluntario al final de la lista.
    * Complejidad: O(1), ya que se mantiene una referencia
    * directa al último nodo (cola).
    */
    public void insertarAlFinal(Voluntarios voluntario) {
        Nodo<Voluntarios> nuevo = new Nodo<>(voluntario);
        
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
    
    /**
    * Inserta un voluntario manteniendo el orden alfabético
    * por nombre.
    * Complejidad: O(n).
    */
    public void insertarOrdenado(Voluntarios voluntario) {
        Nodo<Voluntarios> nuevo = new Nodo<>(voluntario);
        
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
            nuevo.siguiente = nuevo;
            tamaño++;
            return;
        }
        
        // Si el nuevo voluntario debe convertirse en la cabeza de la lista.
        if (voluntario.getNombre().compareTo(cabeza.dato.getNombre()) < 0) {
            nuevo.siguiente = cabeza;
            cola.siguiente = nuevo;
            cabeza = nuevo;
            tamaño++;
            return;
        }
        
        Nodo<Voluntarios> actual = cabeza;
        while (actual != cola && 
               voluntario.getNombre().compareTo(actual.siguiente.dato.getNombre()) > 0) {
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
    
    /**
    * Busca un voluntario por su DNI.
    * Complejidad: O(n).
    */
    public Voluntarios buscarPorDNI(int dni) {
        if (cabeza == null) return null;
        
        Nodo<Voluntarios> actual = cabeza;
        do {
            if (actual.dato.getDni_persona() == dni) {
                return actual.dato;
            }
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return null;
    }
    
    /**
    * Busca un voluntario por su nombre.
    * Complejidad: O(n).
    */
    public Voluntarios buscarPorNombre(String nombre) {
        if (cabeza == null) return null;
        
        Nodo<Voluntarios> actual = cabeza;
        do {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre)) {
                return actual.dato;
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
    
    /**
    * Elimina un voluntario utilizando su DNI.
    * Complejidad: O(n).
    */
    public boolean eliminar(int dni) {
        if (cabeza == null) return false;
        
        Nodo<Voluntarios> actual = cabeza;
        // En una lista circular, el nodo anterior a la cabeza siempre es la cola.
        Nodo<Voluntarios> anterior = cola;
        
        do {
            if (actual.dato.getDni_persona() == dni) {
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
    
    /**
    * Recorre la lista circular mostrando todos los
    * voluntarios registrados.
    * El recorrido finaliza cuando se vuelve nuevamente
    * al nodo cabeza.
    * Complejidad: O(n).
    */
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista circular vacía");
            return;
        }
        
        System.out.println("\nLISTA CIRCULAR DE VOLUNTARIOS");
        System.out.println("Total: " + tamaño + " voluntarios");
        System.out.println("El último apunta al primero (forma un ciclo)");
        System.out.println("-----------------------------------");
        
        Nodo<Voluntarios> actual = cabeza;
        int pos = 1;
        do {
            System.out.println(pos++ + ". " + actual.dato.getNombre() +
                             " (DNI: " + actual.dato.getDni_persona() + 
                             ") -> Horario: " + actual.dato.getHorarios_disponibles());
            actual = actual.siguiente;
        } while (actual != cabeza);
        System.out.println("-----------------------------------");
        System.out.println("Vuelve al inicio: " + cabeza.dato.getNombre());
    }
    
    // ===== MÉTODOS UTILITARIOS =====
    
    /**
    * Devuelve todos los voluntarios en una List.
    * Se utiliza para guardar datos en archivos
    * o recorrer la colección mediante foreach.
    * Complejidad: O(n).
    */
    public List<Voluntarios> obtenerTodos() {
        List<Voluntarios> lista = new ArrayList<>();
        if (cabeza == null) return lista;
        
        Nodo<Voluntarios> actual = cabeza;
        do {
            lista.add(actual.dato);
            actual = actual.siguiente;
        } while (actual != cabeza);
        
        return lista;
    }
    
    // ============================================================
    // MÉTODOS UTILITARIOS
    // ============================================================

    /** @return Cantidad de voluntarios registrados. */
    public int getTamaño() {
        return tamaño;
    }
    
    /** @return true si la lista está vacía. */
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    /** @return Primer nodo de la lista circular. */
    public Nodo<Voluntarios> getCabeza() {
        return cabeza;
    }
    
    /** @return Último nodo de la lista circular. */
    public Nodo<Voluntarios> getCola() {
        return cola;
    }
}
