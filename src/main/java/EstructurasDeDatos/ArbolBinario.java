package EstructurasDeDatos;

import Entidades.Gatos;
import java.util.ArrayList;
import java.util.List;

public class ArbolBinario {
    private Nodo<Gatos> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // ===== INSERCIÓN =====

    // Inserta un nuevo gato en el árbol (método público)
    public void insertar(Gatos nuevoGato) {
        Nodo<Gatos> nuevoNodo = new Nodo<>(nuevoGato);
        if (raiz == null) {
            raiz = nuevoNodo; // Si está vacío, es la raíz
        } else {
            insertarRecursivo(raiz, nuevoNodo); // Si no, buscamos su lugar
        }
    }

    // Decide si el nuevo nodo va a la izquierda o derecha según el ID
    private void insertarRecursivo(Nodo<Gatos> actual, Nodo<Gatos> nuevo) {
        if (nuevo.dato.getId() < actual.dato.getId()) {
            if (actual.izquierdo == null) {
                actual.izquierdo = nuevo;
            } else {
                insertarRecursivo(actual.izquierdo, nuevo);
            }
        } else {
            if (actual.derecho == null) {
                actual.derecho = nuevo;
            } else {
                insertarRecursivo(actual.derecho, nuevo);
            }
        }
    }

    // ===== BÚSQUEDAS =====

    // Busca un gato por su ID aprovechando la eficiencia del BST
    public Gatos buscarPorID(int idBuscado) {
        Nodo<Gatos> encontrado = buscarPorIDRecursivo(raiz, idBuscado);
        return encontrado != null ? encontrado.dato : null;
    }

    private Nodo<Gatos> buscarPorIDRecursivo(Nodo<Gatos> nodo, int idBuscado) {
        if (nodo == null) {
            return null; // No existe o llegamos a una hoja sin encontrarlo
        }
        if (nodo.dato.getId() == idBuscado) {
            return nodo;
        } else if (idBuscado < nodo.dato.getId()) {
            return buscarPorIDRecursivo(nodo.izquierdo, idBuscado);
        } else {
            return buscarPorIDRecursivo(nodo.derecho, idBuscado);
        }
    }

    // Busca gatos por nombre recorriendo TODO el árbol
    public List<Gatos> buscarPorNombre(String nombreBuscado) {
        List<Gatos> resultados = new ArrayList<>();
        buscarPorNombreRecursivo(raiz, nombreBuscado, resultados);
        return resultados;
    }

    private void buscarPorNombreRecursivo(Nodo<Gatos> nodo, String nombreBuscado, List<Gatos> resultados) {
        if (nodo == null) {
            return;
        }
        if (nodo.dato.getNombre().equalsIgnoreCase(nombreBuscado)) {
            resultados.add(nodo.dato);
        }
        buscarPorNombreRecursivo(nodo.izquierdo, nombreBuscado, resultados);
        buscarPorNombreRecursivo(nodo.derecho, nombreBuscado, resultados);
    }

    // ===== RECORRIDOS =====

    // Recorrido en Preorden (Raíz, Izquierdo, Derecho)
    // ✅ CORREGIDO: antes usaba raiz.izquierdo en lugar de nodo.izquierdo
    public void preorden(Nodo<Gatos> nodo) {
        if (nodo != null) {
            System.out.println(nodo.dato + " ");
            preorden(nodo.izquierdo);
            preorden(nodo.derecho);
        }
    }

    public void imprimirPreorden() {
        System.out.print("Preorden Binario: ");
        preorden(this.raiz);
        System.out.println();
    }

    // ===== GETTERS Y SETTERS =====

    public Nodo<Gatos> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<Gatos> raiz) {
        this.raiz = raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}