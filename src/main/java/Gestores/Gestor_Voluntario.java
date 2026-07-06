package Gestores;

import Entidades.Persona;
import Entidades.Voluntarios;
import EstructurasDeDatos.ListaCircular;
import EstructurasDeDatos.ListaDobleEnlazada;
import EstructurasDeDatos.Nodo;
import Scanner.Lector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Gestor encargado de administrar todos los voluntarios registrados en el
 * sistema.
 *
 * Esta clase utiliza dos estructuras de datos que trabajan de manera
 * sincronizada:
 *
 * - Lista Circular:
 *   Se utiliza como estructura principal para registrar, buscar,
 *   recorrer y rotar los voluntarios, aprovechando que el último nodo
 *   vuelve al primero para simular la rotación de turnos.
 *
 * - Lista Doble Enlazada:
 *   Se mantiene sincronizada con la lista circular y permite realizar
 *   recorridos desde el final hacia el inicio, además de facilitar las
 *   operaciones de eliminación y navegación en ambos sentidos.
 *
 * También es responsable de:
 * - Cargar los datos desde el archivo.
 * - Registrar voluntarios.
 * - Buscar por nombre o DNI.
 * - Modificar información.
 * - Eliminar registros.
 * - Guardar automáticamente los cambios.
 * - Mantener sincronizadas ambas estructuras después de cada operación.
 *
 * De esta manera se aprovechan las ventajas particulares de cada estructura
 * de datos dentro del proyecto.
 */
public class Gestor_Voluntario extends GestorBase<Voluntarios> {
        
    Lector lector = Lector.getInstanciaLector();
    private static Gestor_Voluntario instancia;
    
    //ListaCircular (para búsqueda e inserción)
    private ListaCircular listaVoluntarios;
    
    // ListaDobleEnlazada propia (para eliminación y recorrido)
    private ListaDobleEnlazada<Voluntarios> listaDoble;
    
     public Gestor_Voluntario() {
        super("TXT/Voluntarios.txt");
        this.listaVoluntarios = new ListaCircular();
        this.listaDoble = new ListaDobleEnlazada<>(); 
        cargarDatos();
    }
    
    public static Gestor_Voluntario getInstanciaVoluntarios() {
        if (instancia == null) {
            instancia = new Gestor_Voluntario();
        }
        return instancia;
    }

    @Override
    public void cargarDatos() {
        if (listaDoble == null) {
            listaDoble = new ListaDobleEnlazada<>();
        }
        if (listaVoluntarios == null) {
            listaVoluntarios = new ListaCircular();
        }
        // Limpiar ambas listas
        listaVoluntarios = new ListaCircular();
        listaDoble.clear();
        
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
                    String horarios_disponibles = datos[5];
                    
                    Voluntarios voluntario = new Voluntarios(
                        new Persona(dni, nombre, apellido, telefono, correo),
                        horarios_disponibles
                    );
                    
                    // Insertar en LISTA CIRCULAR para búsqueda e inserción
                    listaVoluntarios.insertarOrdenado(voluntario);
                    
                    // Insertar en LISTA DOBLE para eliminación y recorrido
                    insertarOrdenadoEnDoble(voluntario);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar voluntarios (puede que el archivo este vacio)");
        }
    }
    
    /**
     * Método auxiliar para insertar ordenado en la ListaDobleEnlazada propia
     */
    private void insertarOrdenadoEnDoble(Voluntarios voluntario) {
        // Verificar que listaDoble no sea null
        if (listaDoble == null) {
            listaDoble = new ListaDobleEnlazada<>();
        }
        
        int posicion = 0;
        while (posicion < listaDoble.size() && 
               listaDoble.get(posicion).getNombre()
                       .compareTo(voluntario.getNombre()) < 0) {
            posicion++;
        }
        listaDoble.add(posicion, voluntario);
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Guardar desde la lista circular (la principal)
            List<Voluntarios> todos = listaVoluntarios.obtenerTodos();
            
            for (Voluntarios voluntario : todos) {
                String linea = String.format("%d,%s,%s,%d,%s,%s",
                    voluntario.getDni_persona(),
                    voluntario.getNombre(),
                    voluntario.getApellido(),
                    voluntario.getTelefono(),
                    voluntario.getCorreo(),
                    voluntario.getHorarios_disponibles()
                );
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de voluntarios");
        }
    }

    // ============================================================
    // OPERACIONES CON LISTA CIRCULAR BÚSQUEDA E INSERCIÓN
    // ============================================================
    
    @Override
    public boolean registrar(Voluntarios voluntario) {
        // Verificar en LISTA CIRCULAR
        if (listaVoluntarios.buscarPorDNI(voluntario.getDni_persona()) != null) {
            System.out.println("Este DNI ya esta registrado como voluntario");
            return false;
        }
        
        // Insertar en LISTA CIRCULAR (principal)
        listaVoluntarios.insertarOrdenado(voluntario);
        
        // Insertar en LISTA DOBLE (secundaria, sincronizada)
        insertarOrdenadoEnDoble(voluntario);
        
        guardarCambios();
        System.out.println("Voluntario registrado exitosamente");
        return true;
    }

    @Override
    public boolean existe(String identificador) {
        // Buscar en LISTA CIRCULAR
        return retornarElemento(identificador) != null;
    }

    @Override
    public void mostrar() {
        // Mostrar la LISTA CIRCULAR (principal)
        listaVoluntarios.mostrar();
    }

    @Override
    public void modificar(String datoModificar, int opcion) {
        // Buscar en LISTA CIRCULAR
        Voluntarios voluntario = retornarElemento(datoModificar);
        if (voluntario == null) {
            System.out.println("Voluntario no encontrado");
            return;
        }

        switch (opcion) {
            case 1:
                System.out.print("Nuevo telefono: ");
                voluntario.setTelefono(lector.LeerEntero());
                break;
            case 2:
                System.out.print("Nuevo correo: ");
                voluntario.setCorreo(lector.LeerString());
                break;
            case 3:
                System.out.print("Nuevo horario: ");
                voluntario.setHorarios_disponibles(gestionHorario(lector.LeerEntero()));
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
        // Buscar en LISTA CIRCULAR
        Voluntarios resultado = retornarElemento(identificador);
        if (resultado != null) {
            System.out.println("Resultados: 1");
            System.out.println("-----------------------------------");
            System.out.println(resultado);
        } else {
            System.out.println("Voluntario no encontrado");
        }
    } 
    
    @Override
    public Voluntarios retornarElemento(String identificador) {
        // Buscar en LISTA CIRCULAR (principal)
        try {
            int dni = Integer.parseInt(identificador);
            return listaVoluntarios.buscarPorDNI(dni);
        } catch (NumberFormatException e) {
            return listaVoluntarios.buscarPorNombre(identificador);
        }
    }

    
    // ============================================================
    // 2. OPERACIONES CON LISTA DOBLE ELIMINACIÓN Y RECORRIDO
    // ============================================================
    
    @Override
    public boolean eliminar(String identificador) {
        // Buscar en LISTA CIRCULAR primero
        Voluntarios voluntario = retornarElemento(identificador);
        if (voluntario != null) {
            // Eliminar de la LISTA CIRCULAR
            boolean eliminadoCircular = listaVoluntarios.eliminar(voluntario.getDni_persona());
            
            // Eliminar de la LISTA DOBLE (ventaja: O(1) si tenemos el objeto)
            boolean eliminadoDoble = listaDoble.remove(voluntario);
            
            if (eliminadoCircular && eliminadoDoble) {
                guardarCambios();
                System.out.println("Voluntario eliminado exitosamente");
                return true;
            }
        }
        System.out.println("Voluntario no encontrado");
        return false;
    }
    
    /**
     * Mostrar desde el FINAL usando la lista doble (ventaja de doble enlazada)
     * Este método se usa para recorridos inversos cuando sea necesario
     * Esto demuestra que es Doble Enlazada ya que recorre hacia atras a lo contrario de una simple
     * Ya que cada nodo sabe quién está antes
     */
    public void mostrarDobleDesdeFinal() {
        // Verificar que listaDoble no sea null
        if (listaDoble == null) {
            listaDoble = new ListaDobleEnlazada<>();
        }
        
        if (listaDoble.isEmpty()) {
            System.out.println("No hay voluntarios registrados");
            return;
        }
        
        System.out.println("\nLISTA DE VOLUNTARIOS (DESDE EL FINAL)");
        System.out.println("Total: " + listaDoble.size() + " voluntarios");
        System.out.println("-----------------------------------");
        
        int pos = listaDoble.size();
        for (int i = listaDoble.size() - 1; i >= 0; i--) { // Recorrido INVERSO que se puede hacer en una Doble enlazada
            Voluntarios v = listaDoble.get(i);
            System.out.println(pos-- + ". " + v.getNombre() +
                             " (DNI: " + v.getDni_persona() + 
                             ") -> Horario: " + v.getHorarios_disponibles());
        }
        System.out.println("-----------------------------------");
    }
    
    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
    
    public static String gestionHorario(int opcion) {
        String[] horarios = {
            "Lunes (Diurno): 9:00 - 11:00",
            "Miércoles (Diurno): 10:00 - 12:00",
            "Viernes (Diurno): 8:00 - 10:00",
            "Martes (Tarde): 13:00 - 15:00",
            "Jueves (Tarde): 15:00 - 17:00",
            "Sábado (Tarde): 16:00 - 18:00"
        };
        
        if (opcion >= 1 && opcion <= horarios.length) {
            return horarios[opcion - 1];
        }
        return horarios[0];
    }
    
    // ============================================================
    // MÉTODOS PARA COMPATIBILIDAD
    // ============================================================
    
    @Override
    public List<Voluntarios> getElementos_listaporNombre() {
        return listaVoluntarios.obtenerTodos();
    }
    
    public ListaCircular getListaVoluntarios() {
        return listaVoluntarios;
    }
    
    public ListaDobleEnlazada<Voluntarios> getListaDoble() {
        if (listaDoble == null) {
            listaDoble = new ListaDobleEnlazada<>();
        }
        return listaDoble;
    }
    
    /**
     * Rotar voluntarios (ventaja de lista circular)
     * Útil para rotar turnos o horarios
     */
    public void rotarVoluntarios() {
        if (listaVoluntarios.estaVacia()) {
            System.out.println("No hay voluntarios para rotar");
            return;
        }

        // 1. Rotar la lista circular
        listaVoluntarios.rotar();//La cabeza cambia
        String nuevaCabeza = listaVoluntarios.getCabeza().dato.getNombre();

        // 2. SINCRONIZAR: Reconstruir la lista doble desde la circular
        listaDoble.clear();

        Nodo<Voluntarios> actual = listaVoluntarios.getCabeza();
        do {
            listaDoble.add(actual.dato);
            actual = actual.siguiente;
        } while (actual != listaVoluntarios.getCabeza());

        System.out.println("Lista rotada. Nueva cabeza: " + nuevaCabeza);
        System.out.println(listaDoble.size() + " voluntarios sincronizados en lista doble");
    }
}