/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Gatos;
import EstructurasDeDatos.Nodo;
import Scanner.Lector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author admin
 */
/*
 * esta clase tiene muchas intancias en acciones_adoptantes/gatos
 * en cada instancia el constructor vuelve a cargar la clase cargar datos (que
 * llena el map y la lista)
 * que hace que se lea el mismo archivo muchas veces haciendolo ineficiente
 * para eso aplico singleton, para tener solo una instancia
 */
public class Gestor_Gatos extends GestorBase<Gatos> {
    Lector lector = Lector.getInstanciaLector();
    private static Gestor_Gatos instancia;
    private Nodo<Gatos> raiz;

    private Gestor_Gatos() {
        super("TXT/gatos.txt");
    }

    // este sera elmetodo para obtener la instancia
    // una clase lo llama y este inicia como vacio (null)
    // entonces crea la instancia, pero si vuelvo a llamar el metodo, isntancia ya
    // esta lleno
    // entonces vuelve a retornar la misma instancia
    public static Gestor_Gatos getInstanciaGatos() {
        if (instancia == null) {
            instancia = new Gestor_Gatos();
        }
        return instancia;
    }

    @Override
    public void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 9) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    int edad = Integer.parseInt(datos[2]);
                    String raza = datos[3];
                    double peso = Double.parseDouble(datos[4]);
                    String genero = datos[5];
                    String esterilizacion = datos[6];
                    String estado_gato = datos[7];
                    String cuidado_requerido = datos[8];
                    Gatos gato = new Gatos(id, nombre, edad, raza, peso, genero, esterilizacion, estado_gato,
                            cuidado_requerido);

                    getElementos().put(String.valueOf(gato.getId()), gato);
                    InsertarenArbol(gato);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar los gatos (puede que el archivo este vacio).");
        }
    }

    @Override
    public void guardarCambios() { // despues de realizar una medificacion no se guardaga los datos en la lista
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Gatos gato : getElementos().values()) {
                String linea = String.format("%d,%s,%d,%s,%.2f,%s,%s,%s,%s",
                        gato.getId(),
                        gato.getNombre(),
                        gato.getEdad(),
                        gato.getRaza(),
                        gato.getPeso(),
                        gato.getGenero(),
                        gato.getEsterilizacion(),
                        gato.getEstado_gato(),
                        gato.getCuidado_requerido());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo.");
        }
    }

    @Override
    public boolean registrar(Gatos gatos) {
        if (getElementos().containsKey(String.valueOf(gatos.getId()))) { // verifica si el map contiene la clave
            System.out.println("ID duplicada! ingrese otro");
            return true;
        }

        getElementos().put(String.valueOf(gatos.getId()), gatos);
        guardarCambios();
        InsertarenArbol(gatos);
        return true;
    }

    public void InsertarenArbol(Gatos nuevoGato) {
        // Creamos el nodo internamente
        Nodo<Gatos> nuevoNodo = new Nodo<>(nuevoGato);

        if (raiz == null) {
            raiz = nuevoNodo; // Si está vacío, es la raíz
        } else {
            insertarRecursivo(raiz, nuevoNodo); // Si no, buscamos su lugar
        }
    }

    // decide si va a la izquierda o derecha
    private void insertarRecursivo(Nodo<Gatos> actual, Nodo<Gatos> nuevo) {
        // Según la teoría del BST, si el ID del nuevo gato es MENOR que el actual, va a la IZQUIERDA; 
        // si es MAYOR, va a la DERECHA
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

    @Override
    public void buscar(String identificador) {
        List<Gatos> resultados = new ArrayList<>();
        // Si la raíz es null, el árbol está vacío y no se puede buscar
        if (raiz == null) {
        System.out.println("El árbol está vacío. No se puede buscar.");
        return;
    }

        // Intentar buscar por ID (Aprovechando la eficiencia del BST)
        try {
            int idBuscado = Integer.parseInt(identificador);
            Nodo<Gatos> encontrado = buscarPorID(raiz, idBuscado);
            if (encontrado != null) {
                resultados.add(encontrado.dato);
            }
        } catch (NumberFormatException e) {
            // Si el identificador no es un número (ej. un nombre),
            // simplemente ignoramos la búsqueda por ID y pasamos al nombre.
        }

        // Si no se encontró por ID, buscar por nombre recorriendo TODO el árbol
        if (resultados.isEmpty()) {
            buscarPorNombre(raiz, identificador, resultados);
        }

        System.out.println("Resultados: " + resultados.size());
        System.out.println("-----------------------------------");
        resultados.forEach(System.out::println);
    }

    private Nodo<Gatos> buscarPorID(Nodo<Gatos> nodo, int idBuscado) {
        if (nodo == null) {
            return null; // No existe o llegamos a una hoja sin encontrarlo
        }

        if (nodo.dato.getId() == idBuscado) {
            return nodo; // Encontramos el gato con el ID buscado
        } else if (idBuscado < nodo.dato.getId()) {
            // Si el ID buscado es MENOR, según la teoría del BST, va a la IZQUIERDA
            return buscarPorID(nodo.izquierdo, idBuscado);
        } else {
            // Si el ID buscado es MAYOR, va a la DERECHA
            return buscarPorID(nodo.derecho, idBuscado);
        }
    }

    private void buscarPorNombre(Nodo<Gatos> nodo, String nombreBuscado, List<Gatos> resultados) {
        if (nodo == null) {
            return; // Llegamos al final de una rama (una hoja)
        }

        // 1. Revisar el nodo actual (Raíz)
        if (nodo.dato.getNombre().equalsIgnoreCase(nombreBuscado)) {
            resultados.add(nodo.dato);
        }

        // 2. Recorrer subárbol izquierdo
        buscarPorNombre(nodo.izquierdo, nombreBuscado, resultados);

        // 3. Recorrer subárbol derecho
        buscarPorNombre(nodo.derecho, nombreBuscado, resultados);
    }

    @Override
    // verificara si un gato existe >:( y si no piña
    public boolean existe(String identificador) {
        boolean resultado = false;
        Gatos gatos = retornarElemento(identificador);
        if (gatos != null) {
            resultado = true;
        }
        return resultado;
    }

    @Override
    // esta saliendo desordenado !!!
    public void mostrar() {
        if (getElementos().isEmpty()) {
            System.out.println("No hay gatos registrados en el sistema.");
            return;
        }
        System.out.println("\n=== LISTA DE GATOS REGISTRADOS ===");
        System.out.println("Total de gatos: " + getElementos().size());
        System.out.println("-----------------------------------");
        getElementos_listaporId().forEach(System.out::println);
    }

    // opciones que tenia, usar put() o usar replace()
    // mas segura? replace ya que si la llave no existe,
    // no aumenta uno y es la indicada ya que esta sera una opcion para SOLO
    // modificar
    // no put() porque si no existe la clave no aumenta nada (sirve como info pero
    // no para esta parte :()ERROR :(
    @Override
    public void modificar(String gatoModificar, int opcion) {
        Gatos gato = retornarElemento(gatoModificar);

        Consumer<Gatos>[] modificador = new Consumer[8];
        if (gato != null) {
            modificador[1] = g -> {
                System.out.print("Nuevo nombre: ");
                g.setNombre(lector.LeerStringMayuscula());
            };
            modificador[2] = g -> {
                System.out.print("Nueva edad: ");
                g.setEdad(lector.LeerEntero());
            };
            modificador[3] = g -> {
                System.out.print("Nueva raza: ");
                g.setRaza(lector.LeerStringMayuscula());
            };
            modificador[4] = g -> {
                System.out.print("Nuevo peso: ");
                g.setPeso(lector.LeerDouble());
            };
            modificador[5] = g -> {
                System.out.print("Esta esterilizado? (si/no): ");
                g.setEsterilizacion(lector.LeerStringMayuscula());
            };
            modificador[6] = g -> {
                System.out.print("Nuevo estado: ");
                g.setEstado_gato(lector.LeerStringMayuscula());
            };
            modificador[7] = g -> {
                System.out.print("Nuevo cuidado requerido: ");
                g.setCuidado_requerido(lector.LeerStringMayuscula());
            };
            // Como se esta modificando el objeto directamente, el Map se actualiza
            // automaticamente
            // porque hay una referencia al mismo objeto
        }
        modificador[opcion].accept(gato);
        guardarCambios();
    }

    @Override
    public boolean eliminar(String identificador) {
        Gatos gato = retornarElemento(identificador);
        if (gato != null) {
            getElementos().remove(String.valueOf(gato.getId()));
            guardarCambios();
        }
        return true;
    }

    // METODOS PARA LA CLASE ACCION ADOPTANTES
    // esta clase es para mostrar los gatos en adopcion, para usarla en
    // acciones_adoptantes
    public void mostrarGatos() {

        if (getElementos().isEmpty()) {
            System.out.println("No hay gatos registrados en el sistema.");
            return;
        }
        List<Gatos> Gatos_enAdopcion = new ArrayList<>();
        for (Gatos gato : getElementos_listaporId()) {
            if (gato.getEstado_gato().equalsIgnoreCase("En adopcion"))
                Gatos_enAdopcion.add(gato);
        }

        System.out.println("\n=== LISTA DE GATOS EN ADOPCION ===");
        System.out.println("Total de gatos en adopcion: " + Gatos_enAdopcion.size());
        System.out.println("-----------------------------------");

        Gatos_enAdopcion.sort((g1, g2) -> Integer.compare(g1.getId(), g2.getId()));
        Gatos_enAdopcion.forEach(gato -> System.out.println("Id: " + gato.getId() + "  Nombre: " + gato.getNombre()));
    }

    // este metodo verificara si el gato esta en adopcion, si es true entonces
    // cambia su estado
    // a adoptado, y si no entonces no realiza nada
    public boolean VerificadordeGatoEnAdopcion(String identificador) {
        boolean resultado = false;
        for (Gatos gato : getElementos().values()) {
            if (identificador.equalsIgnoreCase(gato.getNombre())) {
                if (gato.getEstado_gato().equalsIgnoreCase("en adopcion")) {
                    resultado = true;
                }
            }
        }
        return resultado;
    }

    public void CambiarEstadoAdoptado(String datoModificar) {
        Gatos gato = retornarElemento(datoModificar);
        gato.setEstado_gato("Adoptado");
        guardarCambios();
    }

    public void CambiarEstadoEnAdopcion(String datoModificar) {
        Gatos gato = retornarElemento(datoModificar);
        gato.setEstado_gato("En Adopcion");
        guardarCambios();
    }
}
