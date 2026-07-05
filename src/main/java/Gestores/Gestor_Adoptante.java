package Gestores;

import Entidades.Adoptantes;
import Entidades.Persona;
import EstructurasDeDatos.ListaEnlazadaSimple;
import Scanner.Lector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Gestor encargado de administrar todos los adoptantes registrados en el
 * sistema.
 *
 * Esta clase implementa la lógica de negocio relacionada con los
 * adoptantes, utilizando una Lista Enlazada Simple como estructura de
 * almacenamiento principal en memoria.
 *
 * Además de gestionar la estructura de datos, también es responsable de:
 * - Cargar los datos desde el archivo de texto.
 * - Registrar nuevos adoptantes.
 * - Buscar por DNI o nombre.
 * - Modificar información.
 * - Eliminar registros.
 * - Guardar automáticamente los cambios realizados.
 *
 * Gracias a esta separación, la estructura de datos únicamente almacena
 * información, mientras que el gestor se encarga de toda la lógica del
 * sistema y la persistencia de los datos.
 */

public class Gestor_Adoptante extends GestorBase<Adoptantes>{
     Lector lector = Lector.getInstanciaLector();
    private static Gestor_Adoptante instancia;
    
    //CAMBIO: Ahora usa ListaEnlazadaSimple en lugar de HashMap
    private ListaEnlazadaSimple listaAdoptantes;

    public Gestor_Adoptante() {
        super("TXT/Adoptantes.txt");
        this.listaAdoptantes = new ListaEnlazadaSimple();
        cargarDatos();
    }

    public static Gestor_Adoptante getInstanciaAdoptantes() {
        if (instancia == null) {
            instancia = new Gestor_Adoptante();
        }
        return instancia;
    }

    @Override
    public void cargarDatos() {
        // Limpiar la lista
        listaAdoptantes = new ListaEnlazadaSimple();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    int dni = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String apellido = datos[2];
                    int telefono = Integer.parseInt(datos[3]);
                    String correo = datos[4];
                    String gato_Adoptado = datos[5];
                    
                    Adoptantes adoptante = new Adoptantes(
                        new Persona(dni, nombre, apellido, telefono, correo),
                        gato_Adoptado
                    );
                    
                    // Insertar en la lista enlazada
                    listaAdoptantes.insertarOrdenado(adoptante);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar adoptantes (puede que el archivo este vacio)");
        }
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Obtener todos los adoptantes de la lista enlazada
            List<Adoptantes> todos = listaAdoptantes.obtenerTodos();
            
            for (Adoptantes adoptante : todos) {
                String linea = String.format("%d,%s,%s,%d,%s,%s",

                    adoptante.getDni_persona(),
                    adoptante.getNombre(),
                    adoptante.getApellido(),
                    adoptante.getTelefono(),
                    adoptante.getCorreo(),
                    adoptante.getGato_Adoptado()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de adoptantes");
        }
    }

    @Override
    public boolean registrar(Adoptantes adoptante) { 
        // Verificar si ya existe por DNI
        if (listaAdoptantes.buscarPorDNI(adoptante.getDni_persona()) != null) {
            System.out.println("Este DNI ya esta registrado como adoptante");
            return false;
        }

        // Insertar en la lista enlazada
        listaAdoptantes.insertarOrdenado(adoptante);
        guardarCambios();
        return true;
    }

    @Override
    public boolean existe(String identificador) {
        return retornarElemento(identificador) != null;
    }

    @Override
    public void mostrar() {

        listaAdoptantes.mostrar();
    }

    @Override
    public void modificar(String datoModificar, int opcion) {
        Adoptantes adoptante = retornarElemento(datoModificar);
        if (adoptante == null) {
            System.out.println("Adoptante no encontrado");
            return;
        }

        switch (opcion) {
            case 1:
                System.out.print("Nuevo telefono: ");
                adoptante.setTelefono(lector.LeerEntero());
                break;
            case 2:
                System.out.print("Nuevo correo: ");
                adoptante.setCorreo(lector.LeerString());
                break;
            default:
                System.out.println("Opción inválida");
                return;
        }

        guardarCambios();
        System.out.println("Datos modificados exitosamente");
    }
        
    @Override
    public void buscar(String identificador) {
        Adoptantes resultado = retornarElemento(identificador);
        if (resultado != null) {
            System.out.println("Resultados: 1");
            System.out.println("-----------------------------------");
            System.out.println(resultado);
        } else {
            System.out.println("Adoptante no encontrado");
        }
    }     

    @Override
    public boolean eliminar(String identificador) {
        Adoptantes adoptante = retornarElemento(identificador);
        if (adoptante != null) {
            boolean eliminado = listaAdoptantes.eliminar(adoptante.getDni_persona());
            if (eliminado) {
                guardarCambios();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Adoptantes retornarElemento(String identificador) {
        // Intentar buscar por DNI
        try {
            int dni = Integer.parseInt(identificador);
            return listaAdoptantes.buscarPorDNI(dni);
        } catch (NumberFormatException e) {
            // Buscar por nombre
            return listaAdoptantes.buscarPorNombre(identificador);
        }
    }
    
    // ===== MÉTODOS PARA COMPATIBILIDAD CON EL RESTO DEL SISTEMA =====
    
    /**
     * Obtiene todos los adoptantes como List para XML y BD
     */
    @Override
    public List<Adoptantes> getElementos_listaporNombre() {
        return listaAdoptantes.obtenerTodos();
    }
    
    /**
     * Obtiene la lista enlazada simple (para demostración)
     */
    public ListaEnlazadaSimple getListaAdoptantes() {
        return listaAdoptantes;
    }
}