package EstructurasDeDatos;

import Entidades.Gatos;

public class ArbolBinario {
    private Nodo<Gatos> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // Recorrido en Preorden (Raíz, Izquierdo, Derecho)
    public void preorden(Nodo nodo) {
        if(nodo!=null){
            System.out.println(nodo.dato + " ");
            preorden(raiz.izquierdo);
            preorden(raiz.derecho);
        }
    }

    public void imprimirPreorden() {
        System.out.print("Preorden Binario: ");
        preorden(this.raiz);
        System.out.println();
    }

    public Nodo<Gatos> getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo<Gatos> raiz) {
        this.raiz = raiz;
    }
}
