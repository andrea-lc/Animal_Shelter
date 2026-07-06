package EstructurasDeDatos;


import java.util.ArrayList;
import java.util.List;

/**
 * Nodo genérico reutilizable para las distintas estructuras de datos del
 * proyecto (Pila, Cola, Árbol Binario, Lista Enlazada Simple,
 * Lista Circular y Lista Doble Enlazada).
 *
 * En lugar de que cada estructura declare su propia clase Nodo, todas
 * reutilizan esta clase genérica y emplean únicamente los atributos que
 * necesitan.
 *
 * Según la estructura utilizada:
 * - Pila y Cola: utilizan "dato" y "siguiente".
 * - Lista Enlazada Simple: utiliza "dato" y "siguiente".
 * - Lista Circular: utiliza "dato" y "siguiente", haciendo que el último
 *   nodo vuelva a apuntar al primero.
 * - Lista Doble Enlazada: utiliza "dato", "siguiente" y "anterior".
 * - Árbol Binario: utiliza "dato", "izquierdo" y "derecho".
 * - Árboles N-arios: utilizan "dato" y la lista "hijos".
 *
 * Los atributos que una estructura no necesita permanecen en null o vacíos,
 * lo que evita duplicar código y permite reutilizar una única clase Nodo
 * en todo el proyecto.
 *
 * @param <T> Tipo de dato almacenado en el nodo.
 */
public class Nodo<T> {
    /** Dato almacenado por el nodo. */
    public T dato;

    /** Referencia al siguiente nodo (listas, pilas y colas). */
    public Nodo<T> siguiente;

    /** Referencia al nodo anterior (solo utilizada por la lista doblemente enlazada). */
    public Nodo<T> anterior;
    
    // Atributos para uso de Arboles
    public Nodo<T> izquierdo; 
    public Nodo<T> derecho;
    public List<Nodo> hijos; 

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
        this.izquierdo = null;
        this.derecho = null;
        this.hijos = new ArrayList<>();
    }
    
    public void agregarHijo(Nodo hijo) {
        this.hijos.add(hijo);
    }
}