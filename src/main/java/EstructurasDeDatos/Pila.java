package EstructurasDeDatos;

import java.util.NoSuchElementException;

public class Pila<T> {
    private Nodo<T> tope;   // Apunta al elemento más reciente
    private int tamano;  // Para llevar la cuenta del size()

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    // Push: Agrega un elemento al tope de la pila
    public void push(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.siguiente = tope; // El nuevo nodo apunta al antiguo tope
        tope = nuevoNodo;           // El nuevo nodo se convierte en el tope
        tamano++;
    }

    // Peek: Hallar/ver el elemento del tope sin sacarlo
    public T peek() {
        if (estaVacia()) {
            throw new NoSuchElementException("La pila está vacía");
        }
        return tope.dato;
    }

    // Método auxiliar útil
    public boolean estaVacia() {
        return tamano == 0;
    }

}
