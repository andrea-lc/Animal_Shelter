package EstructurasDeDatos;

import Entidades.Administradores;

public class Cola {
    Administradores[] datos;
    int frente;
    int fin;
    int tamanio;
    int capacidad;

    public Cola() {
        this.capacidad = 5;
        this.datos = new Administradores[capacidad]; // ← Aquí se crea el arreglo
        this.frente = 0;
        this.fin = -1;
        this.tamanio = 0;
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
}