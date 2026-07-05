package EstructurasDeDatos;


import java.util.ArrayList;
import java.util.List;

/**
 * Nodo genérico reutilizable para las distintas estructuras de datos del
 * proyecto (Pila, Cola, ArbolBinario, ListaDobleEnlazada, etc).
 *
 * En vez de que cada estructura declare su propia clase interna de nodo,
 * todas comparten esta clase y solo usan los atributos que necesitan:
 *  - Pila / Cola (hacia adelante): usan "siguiente".
 *  - ListaDobleEnlazada: además usa "anterior" para poder recorrer el
 *    nodo hacia atrás (esa es la diferencia clave frente a una lista
 *    simplemente enlazada).
 *  - ArbolBinario: usa "izquierdo" y "derecho".
 *  - Estructuras de árboles N-arios (si las hubiera): usan "hijos".
 *
 * Dejar los atributos que no se usan en null/vacíos no genera ningún
 * problema ni gasta memoria relevante, y evita duplicar la misma clase
 * Nodo una y otra vez en cada archivo.
 */

public class Nodo<T> {
    /** Valor / dato real que guarda el nodo. */
    public T dato;

    /** Puntero al siguiente nodo (listas, pilas, colas). */
    public Nodo<T> siguiente;

    /**
     * Puntero al nodo anterior. Solo lo utiliza la ListaDobleEnlazada;
     * en el resto de estructuras (Pila, Cola, ListaCircular, etc.)
     * simplemente se queda en null.
     */
    public Nodo<T> anterior;
    
    // Atributos para uso de Arboles
    public Nodo<T> izquierdo; 
    public Nodo<T> derecho;
    public List<Nodo> hijos; 

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
        this.izquierdo = null;
        this.derecho = null;
        this.hijos = new ArrayList<>();
    }
    
    public void agregarHijo(Nodo hijo) {
        this.hijos.add(hijo);
    }
}