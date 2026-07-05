package EstructurasDeDatos;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista Doblemente Enlazada
 *
 * Reemplaza el uso de java.util.LinkedList en el proyecto. La diferencia
 * frente a una lista simplemente enlazada (ver ListaEnlazadaSimple) es que
 * cada nodo conoce tanto al nodo que lo sigue ("siguiente") como al nodo
 * que lo precede ("anterior"). Gracias a esto se puede:
 *  - Recorrer la lista en ambos sentidos (de cabeza a cola y viceversa).
 *  - Eliminar un nodo sin necesidad de recorrer desde la cabeza buscando
 *    a su nodo anterior (si ya se tiene el nodo, la eliminación es O(1)).
 *  - Insertar/eliminar en los dos extremos de forma eficiente O(1),
 *    porque se mantienen referencias tanto a la cabeza como a la cola.
 *
 * Reutiliza la clase Nodo<T> genérica del paquete EstructurasDeDatos
 * (la misma que usan Pila, Cola y ArbolBinario), en vez de declarar una
 * clase de nodo propia. Solo se usan los atributos "dato", "siguiente" y
 * "anterior" del Nodo; el resto de atributos (usados por el árbol) se
 * quedan simplemente sin usar.
 *
 * @param <T> tipo de dato que almacena la lista.
 */
public class ListaDobleEnlazada<T> {

    /** Primer nodo de la lista (null si la lista está vacía). */
    private Nodo<T> cabeza;

    /** Último nodo de la lista (null si la lista está vacía). */
    private Nodo<T> cola;

    /** Cantidad actual de elementos almacenados. */
    private int tamanio;

    /**
     * Crea una lista doblemente enlazada vacía.
     */
    public ListaDobleEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamanio = 0;
    }

    // ============================================================
    // OPERACIONES DE INSERCIÓN
    // ============================================================

    /**
     * Inserta un elemento al final de la lista.
     * Complejidad: O(1), porque siempre se tiene la referencia directa
     * a la cola (no es necesario recorrer la lista para llegar al final).
     *
     * @param dato elemento a insertar.
     */
    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            // Lista vacía: el nuevo nodo es a la vez cabeza y cola.
            cabeza = nuevo;
            cola = nuevo;
        } else {
            // Se enlaza el nuevo nodo después de la cola actual,
            // y se actualizan los dos punteros (siguiente / anterior).
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamanio++;
    }

    /**
     * Inserta un elemento en una posición específica (índice basado en 0),
     * desplazando hacia la derecha a los elementos que estén desde esa
     * posición en adelante. Se comporta igual que LinkedList.add(index, e).
     * Complejidad: O(n) en el peor caso, porque hay que avanzar nodo por
     * nodo hasta llegar a la posición deseada.
     *
     * @param indice posición donde se insertará el nuevo elemento
     *               (0 = inicio, tamanio = final).
     * @param dato   elemento a insertar.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    public void add(int indice, T dato) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        if (indice == tamanio) {
            // Insertar al final es un caso particular ya optimizado.
            add(dato);
            return;
        }

        if (indice == 0) {
            // Insertar al inicio: el nuevo nodo pasa a ser la cabeza.
            Nodo<T> nuevo = new Nodo<>(dato);
            nuevo.siguiente = cabeza;
            if (cabeza != null) {
                cabeza.anterior = nuevo;
            }
            cabeza = nuevo;
            if (cola == null) {
                cola = nuevo;
            }
            tamanio++;
            return;
        }

        // Caso general: se ubica el nodo que actualmente ocupa "indice"
        // y se inserta el nuevo nodo justo antes de él.
        Nodo<T> actual = obtenerNodo(indice);
        Nodo<T> nuevo = new Nodo<>(dato);
        Nodo<T> anterior = actual.anterior;

        nuevo.anterior = anterior;
        nuevo.siguiente = actual;
        anterior.siguiente = nuevo;
        actual.anterior = nuevo;

        tamanio++;
    }

    // ============================================================
    // OPERACIONES DE BÚSQUEDA / ACCESO
    // ============================================================

    /**
     * Devuelve el elemento ubicado en la posición indicada.
     * Complejidad: O(n) en el peor caso. Para optimizar un poco el
     * recorrido, si el índice pedido está en la segunda mitad de la
     * lista se recorre desde la cola hacia atrás; si está en la
     * primera mitad, se recorre desde la cabeza hacia adelante.
     *
     * @param indice posición del elemento (0 = primero).
     * @return el elemento en esa posición.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    public T get(int indice) {
        return obtenerNodo(indice).dato;
    }

    /**
     * Método auxiliar que ubica el Nodo en la posición pedida,
     * recorriendo desde el extremo más cercano (cabeza o cola).
     */
    private Nodo<T> obtenerNodo(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }

        Nodo<T> actual;
        if (indice <= tamanio / 2) {
            // Está más cerca de la cabeza: se recorre hacia adelante.
            actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.siguiente;
            }
        } else {
            // Está más cerca de la cola: se recorre hacia atrás
            // (ventaja propia de una lista doblemente enlazada).
            actual = cola;
            for (int i = tamanio - 1; i > indice; i--) {
                actual = actual.anterior;
            }
        }
        return actual;
    }

    /**
     * Indica si un elemento existe dentro de la lista, comparando con
     * equals(). Complejidad: O(n).
     *
     * @param dato elemento a buscar.
     * @return true si se encuentra, false en caso contrario.
     */
    public boolean contains(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (actual.dato == null ? dato == null : actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    // ============================================================
    // OPERACIONES DE ELIMINACIÓN
    // ============================================================

    /**
     * Elimina la primera aparición del elemento indicado (comparado con
     * equals(), igual que hacía java.util.LinkedList.remove(Object)).
     * Complejidad: O(n) para encontrar el nodo (hay que recorrer la
     * lista comparando dato por dato), pero una vez encontrado, la
     * desconexión del nodo es O(1) gracias a que cada nodo conoce a su
     * "anterior": no es necesario buscar por segunda vez el nodo previo
     * como sí sería obligatorio en una lista simplemente enlazada.
     *
     * @param dato elemento a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean remove(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            boolean coincide = (actual.dato == null) ? (dato == null) : actual.dato.equals(dato);
            if (coincide) {
                eliminarNodo(actual);
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    /**
     * Desconecta un nodo de la lista reacomodando los punteros de su
     * nodo anterior y su nodo siguiente. Complejidad: O(1).
     */
    private void eliminarNodo(Nodo<T> nodo) {
        Nodo<T> anterior = nodo.anterior;
        Nodo<T> siguiente = nodo.siguiente;

        if (anterior != null) {
            anterior.siguiente = siguiente;
        } else {
            // El nodo eliminado era la cabeza.
            cabeza = siguiente;
        }

        if (siguiente != null) {
            siguiente.anterior = anterior;
        } else {
            // El nodo eliminado era la cola.
            cola = anterior;
        }

        tamanio--;
    }

    /**
     * Vacía completamente la lista. Complejidad: O(1).
     */
    public void clear() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    // ============================================================
    // OPERACIONES DE RECORRIDO / VISUALIZACIÓN
    // ============================================================

    /**
     * Muestra por consola todos los elementos de la lista, de cabeza a
     * cola. Complejidad: O(n).
     */
    public void mostrar() {
        if (cabeza == null) {
            System.out.println("Lista doblemente enlazada vacía");
            return;
        }

        System.out.println("\n=== LISTA DOBLEMENTE ENLAZADA ===");
        System.out.println("Total: " + tamanio + " elementos");
        System.out.println("-----------------------------------");

        Nodo<T> actual = cabeza;
        int pos = 1;
        while (actual != null) {
            System.out.println(pos++ + ". " + actual.dato);
            actual = actual.siguiente;
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Muestra por consola todos los elementos de la lista, pero
     * recorriéndola desde la cola hacia la cabeza. Esto solo es posible
     * gracias a que la lista es doblemente enlazada (cada nodo conoce a
     * su nodo anterior). Complejidad: O(n).
     */
    public void mostrarDesdeElFinal() {
        if (cola == null) {
            System.out.println("Lista doblemente enlazada vacía");
            return;
        }

        System.out.println("\n=== LISTA DOBLEMENTE ENLAZADA (DESDE EL FINAL) ===");
        System.out.println("Total: " + tamanio + " elementos");
        System.out.println("-----------------------------------");

        Nodo<T> actual = cola;
        int pos = tamanio;
        while (actual != null) {
            System.out.println(pos-- + ". " + actual.dato);
            actual = actual.anterior;
        }
        System.out.println("-----------------------------------");
    }

    /**
     * Devuelve todos los elementos de la lista como una java.util.List,
     * útil para exportar a XML/BD o para recorridos con for-each.
     * Complejidad: O(n).
     */
    public List<T> obtenerTodos() {
        List<T> lista = new ArrayList<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    // ============================================================
    // MÉTODOS UTILITARIOS
    // ============================================================

    /** @return cantidad actual de elementos. Complejidad: O(1). */
    public int size() {
        return tamanio;
    }

    /** @return true si la lista no tiene elementos. Complejidad: O(1). */
    public boolean isEmpty() {
        return tamanio == 0;
    }

    /** @return el primer nodo de la lista (o null si está vacía). */
    public Nodo<T> getCabeza() {
        return cabeza;
    }

    /** @return el último nodo de la lista (o null si está vacía). */
    public Nodo<T> getCola() {
        return cola;
    }
}

