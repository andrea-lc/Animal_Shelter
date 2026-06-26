package EstructurasDeDatos;

import java.util.NoSuchElementException;

import Entidades.Administradores;

public class Pila {
    //Pila Dinamica 
    private Nodo<Administradores> tope;   // Apunta al elemento más reciente
    private int tamano;  // Para llevar la cuenta del size()

     // --- Para la Pila Estática ---
    private Administradores[] pilaEstatica; // El array que pedirá el profesor
    private int topeEstatico;               // Índice del último elemento agregado
    private int capacidadEstatica;

    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    public Pila(int capacidad) {
        this.pilaEstatica = new Administradores[capacidad]; // Se crea el array con el tamaño exacto
        this.topeEstatico = -1; // Usamos -1 para indicar que está vacía (no hay índices usados aún)
        this.capacidadEstatica = capacidad;
    }

    //OPERACIONES PARA PILA  DINAMICA
    // Push: Agrega un elemento al tope de la pila
    public void push(Administradores elemento) {
        Nodo<Administradores> nodo= new Nodo<>(elemento);
        nodo.siguiente= tope;
        tope=nodo;
        tamano++;
    }

    // Peek: Hallar/ver el elemento del tope sin sacarlo
    public Administradores peek() {
        if (estaVacia()) {
            throw new NoSuchElementException("La pila está vacía");
        }
        return tope.dato;
    }

    // Método auxiliar útil
    public boolean estaVacia() {
        return tamano == 0;
    }

    //OPERACIONES PARA UNA PILA ESTATICA 
       // Push Estático: Agrega un elemento al array
    public void pushEstatico(Administradores elemento) {
        // Validación por si usaste el constructor dinámico por error
        if (pilaEstatica == null) {
            throw new IllegalStateException("Esta pila no es estática. Usa el constructor Pila(int capacidad).");
        }
        // Validación de Overflow (pila llena)
        if (topeEstatico == capacidadEstatica - 1) {
            throw new IllegalStateException("La pila estática está llena (Overflow). No caben más elementos.");
        }
        topeEstatico++; // Subimos el índice
        pilaEstatica[topeEstatico] = elemento; // Guardamos el dato
    }

    // Peek Estático: Ver el elemento del tope sin sacarlo
    public Administradores peekEstatico() {
        if (estaVaciaEstatica()) {
            throw new NoSuchElementException("La pila estática está vacía");
        }
        return pilaEstatica[topeEstatico];
    }

    // Método auxiliar para saber si la pila estática está vacía
    public boolean estaVaciaEstatica() {
        return topeEstatico == -1; // Si el índice volvió a -1, es que no hay nada
    }
    
    // Método auxiliar para saber si la pila estática está llena
    public boolean estaLlenaEstatica() {
        return topeEstatico == capacidadEstatica - 1; // Si el índice llegó al límite del array
    }

}
