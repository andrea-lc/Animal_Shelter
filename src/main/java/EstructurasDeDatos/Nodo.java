package EstructurasDeDatos;


import java.util.ArrayList;
import java.util.List;

public class Nodo<T> {
    public T dato;
    public Nodo<T> siguiente;
    
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