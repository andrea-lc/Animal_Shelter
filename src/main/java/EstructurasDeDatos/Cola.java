package EstructurasDeDatos;

import Entidades.Administradores;

public class Cola {
    Administradores[] datos;
    int frente;
    int fin;
    int tamanio;
    int capacidad;

    // --- Para la Cola Dinámica (nuevos) ---
    private Nodo<Administradores> frenteDinamico; 
    private Nodo<Administradores> finDinamico;   
    private int tamanioDinamico; 

    public Cola() {
        //estatico
        this.capacidad = 5;
        this.datos = new Administradores[capacidad]; //
        this.frente = 0;
        this.fin = -1;
        this.tamanio = 0;
        //Dinamico 
        this.frenteDinamico = null;
        this.finDinamico = null;
        this.tamanioDinamico = 0;
    }

    public void enqueue(Administradores valor) {
        if (tamanio == capacidad) {
            return;  // cola llena
        }
        fin++;
        if (fin == capacidad) {
            fin = 0; // Circular: vuelve al inicio
        }
        datos[fin] = valor;
        tamanio++;
    }

    public boolean isempty() {
        return tamanio == 0;
    }

    public int size() {
        return tamanio; // ← Retorna cuántos elementos hay, no la capacidad
    }

    public void clear() {
        for (int i = 0; i < capacidad; i++) {
            datos[i] = null;
        }
        frente = 0;
        fin = -1;
        tamanio = 0;
    }

    //metodos para cola dinamica
    
       // Enqueue Dinámico: Agregar un elemento al final de la cola
    public void enqueueDinamico(Administradores valor) {
        Nodo<Administradores> nuevo = new Nodo<>(valor);
        
        if (estaVaciaDinamica()) {
            // Si está vacía, tanto el frente como el fin apuntan al nuevo nodo
            frenteDinamico = nuevo;
            finDinamico = nuevo;
        } else {
            // Si no está vacía, el nodo actual del final apunta al nuevo, y el nuevo pasa a ser el fin
            finDinamico.siguiente = nuevo;
            finDinamico = nuevo;
        }
        tamanioDinamico++;
    }

    // Método auxiliar para saber si la cola dinámica está vacía
    public boolean estaVaciaDinamica() {
        return tamanioDinamico == 0;
    }

    // Método auxiliar para obtener el tamaño de la cola dinámica
    public int sizeDinamico() {
        return tamanioDinamico;
    }

    // Método para vaciar la cola dinámica
    public void clearDinamico() {
        frenteDinamico = null;
        finDinamico = null;
        tamanioDinamico = 0;
    }
}