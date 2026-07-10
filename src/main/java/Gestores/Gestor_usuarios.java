package Gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import Entidades.Administradores;
import Entidades.Persona;
import EstructurasDeDatos.Cola;
import EstructurasDeDatos.Pila;
import Scanner.Lector;

/**
 *
 * @author admin
 */
// Clase Gestor_usuarios
public class Gestor_usuarios extends GestorBase<Administradores> {

    /**
     * colas estatica pila dinamica Idea de cambio: Colas: Intento de login
     * hasta 5 veces,si falla la cuenta se bloqueara Si se usa una cola, el
     * intento mas antiguio osea el primero que entro sera el primero en salir
     * para darle lugar a un intento nuevo. Pilas: Historial de logins Si un
     * administrador inicia sesion, ese es el evento mas reciente. Al usar una
     * pila, cuando quieras mostrar el historial, el ultimo login estara hasta
     * arriba, osea el mas facil de encontrar con peek()
     */
    private static Gestor_usuarios instancia;
    Lector lector = Lector.getInstanciaLector();

    // Pila para el historial de loggins
    private final Pila historialLogins = new Pila();
    private final Pila historialLoginsEstatico = new Pila(2);
    private final Map<Administradores, Cola> intentosFallidosPorUsuario = new HashMap<>();
    private final Map<Administradores, Cola> intentosFallidosPorUsuarioDinamico = new HashMap<>();
    String tipoPila;
    String tipoCola;

    public Gestor_usuarios() {
        super("TXT/Administradores.txt");
    }

    public static Gestor_usuarios getInstanciaUsuario() {
        if (instancia == null) {
            instancia = new Gestor_usuarios();
        }
        return instancia;
    }

    @Override
    public void cargarDatos() {
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
                    String contraseña = datos[5];
                    Administradores administradores = new Administradores(
                            new Persona(dni, nombre, apellido, telefono, correo), contraseña);

                    getElementos().put(correo, administradores);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar voluntarios (puede que el archivo este vacio)");
        }
    }

    @Override
    public boolean registrar(Administradores administrador) {
        if (getElementos().containsKey(administrador.getCorreo())) { // verifica si el map contiene la clave
            System.out.println("Ese correo ya esta registrado.");
            return false;
        }

        System.out.println("Usuario agregado correctamente.");

        // Guardar en el HashMap (usando el correo como clave y la Persona como valor)
        getElementos().put(administrador.getCorreo(), administrador);
        guardarCambios();
        return true;
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {

            int cantidadAdmins = getElementos().size();
            String[][] matrizAdmins = new String[cantidadAdmins][6];

            // Llenar la matriz con los datos de cada administrador
            int fila = 0;
            for (Administradores admin : getElementos().values()) {
                matrizAdmins[fila][0] = String.valueOf(admin.getDni_persona());
                matrizAdmins[fila][1] = admin.getNombre();
                matrizAdmins[fila][2] = admin.getApellido();
                matrizAdmins[fila][3] = String.valueOf(admin.getTelefono());
                matrizAdmins[fila][4] = admin.getCorreo();
                matrizAdmins[fila][5] = admin.getContraseña();
                fila++;
            }

            // Recorrer la matriz para escribir cada fila al archivo
            for (int i = 0; i < matrizAdmins.length; i++) {
                String linea = String.format("%s,%s,%s,%s,%s,%s",
                        matrizAdmins[i][0],
                        matrizAdmins[i][1],
                        matrizAdmins[i][2],
                        matrizAdmins[i][3],
                        matrizAdmins[i][4],
                        matrizAdmins[i][5]);
                bw.write(linea);
                bw.newLine();
            }

        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de administradores");
        }
    }

    public void TipoEstructuraUsada() {
        System.out.println("Pilas:");
        System.out.println("1) Dinamicas");
        System.out.println("2) Estaticas");
        System.out.print("Opcion: ");
        this.tipoPila = lector.LeerString();
        System.out.println("Colas:");
        System.out.println("1) Dinamicas");
        System.out.println("2) Estaticas");
        System.out.print("Opcion: ");
        this.tipoCola = lector.LeerString();
    }

    public boolean login(String correo, String contraseña) {
        // Verificar si existe algun correo en el sistema
        if (getElementos().isEmpty()) {
            System.out.println("No hay usuarios registrados en el sistema.");
            return false;
        }
        // Verificar si el correo existe en el sistema (en el hashmap)
        if (!getElementos().containsKey(correo)) {
            return false; // correo (usuario) no encontrado
        }

        Administradores valor = getElementos().get(correo); // devolverá el valor asociado a la clave "correo"

        // Verifica si el usuario está bloqueado, si la hora de desbloqueo está
        // configurada, y si la hora de desbloqueo ya pasó
        if (valor.isBloqueado() && valor.getHoraDesbloqueo() != null
                && LocalDateTime.now().isAfter(valor.getHoraDesbloqueo())) {
            valor.setBloqueado(false);
            valor.setHoraDesbloqueo(null); // Limpiamos la hora para la próxima vez

            // Limpiamos la cola de intentos fallidos para que empiece de cero
            if (intentosFallidosPorUsuario.containsKey(valor)) {
                intentosFallidosPorUsuario.get(valor).clear();
            }
            if (intentosFallidosPorUsuarioDinamico.containsKey(valor)) {
                intentosFallidosPorUsuarioDinamico.get(valor).clear();
            }
            System.out.println("La cuenta de " + valor.getNombre() + " ha sido desbloqueada.");
        }

        // Ahora sí, validamos si sigue bloqueado (por si aún no pasa el tiempo)
        if (valor.isBloqueado()) {
            System.out.println("La cuenta de " + valor.getNombre() + " esta bloqueada. Pruebe denuevo más tarde.");
            return false;
        }

        // VALIDACION DE CONTRASEÑA
        if (valor != null && valor.getContraseña().equals(contraseña)) {
            // Verificamos si hay espacio en la pila estática
            if (tipoPila.equalsIgnoreCase("2") && historialLoginsEstatico.estaLlenaEstatica()) {
                System.out.println("El historial estático está lleno. No se puede registrar este login ni permitir el acceso.");
                return false; // Sale inmediatamente sin dar la bienvenida
            }

            System.out.println("Bienvenid@ " + valor.getNombre());
            valor.setFechaUltimoLogin(LocalDateTime.now()); // Actualizar la fecha del último login exitoso

            if (tipoPila.equalsIgnoreCase("1")) {
                historialLogins.push(valor); // Agrega a la pila el administrador que ha iniciado sesión exitosamente
            } else if (tipoPila.equalsIgnoreCase("2")) {
                historialLoginsEstatico.pushEstatico(valor);
            }

            if (tipoCola.equalsIgnoreCase("1")) {
                if (intentosFallidosPorUsuarioDinamico.containsKey(valor)) {
                    intentosFallidosPorUsuarioDinamico.get(valor).clear();
                }
            } else if (tipoCola.equalsIgnoreCase("2")) {
                if (intentosFallidosPorUsuario.containsKey(valor)) {
                    intentosFallidosPorUsuario.get(valor).clear();
                }
            }

            guardarHistorial(); // Guardar el historial de logins exitoso en el archivo
            return true; // login correcto

        } else {
            // CONTRASEÑA INCORRECTA
            IntentosLoginFallidos(valor);
            usuarioBloquados(valor);
            return false; // contraseña incorrecta
        }
    }

// Metodo para guardar el historial de logins exitosos en un archivo de texto
// (PILAS)
    private void guardarHistorial() {
        if (tipoPila.equalsIgnoreCase("1")) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT/HistorialLogins.txt", true))) {
                bw.write(historialLogins.peek().toString()); // escribe en el archivo el ultimo elemento en la pila
                bw.newLine();
            } catch (IOException ex) {
                System.out.println("Error al guardar cambios en el archivo de historial de logins");
            }
        } else if (tipoPila.equalsIgnoreCase("2")) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT/HistorialLoginsEstatico.txt", true))) {
                bw.write(historialLoginsEstatico.peekEstatico().toString()); // escribe en el archivo el ultimo elemento en la pila
                bw.newLine();
            } catch (IOException ex) {
                System.out.println("Error al guardar cambios en el archivo de historial de logins");
            }
        }
    }

    private void IntentosLoginFallidos(Administradores valor) {
        LocalDateTime horabloqueada;

        if (tipoCola.equalsIgnoreCase("1")) {
            // Verificar si el usuario ya tiene una cola de intentos fallidos
            intentosFallidosPorUsuarioDinamico.putIfAbsent(valor, new Cola());
            // COLA
            Cola intentosFallidos = intentosFallidosPorUsuarioDinamico.get(valor);

            intentosFallidos.enqueueDinamico(valor); // Agrega un intento fallido a la cola del usuario

            if (intentosFallidos.sizeDinamico() >= 2) { // Si el usuario ha tenido 2 intentos fallidos
                valor.setBloqueado(true); // Bloquea la cuenta del usuario
                System.out.println("La cuenta de " + valor.getNombre()
                        + " ha sido bloqueada debido a multiples intentos fallidos de inicio de sesion.");
                System.out.println("Pruebe denuevo en 5 minutos.");
                horabloqueada = LocalDateTime.now();
                valor.setHoraDesbloqueo(horabloqueada.plusSeconds(20));
            } else {
                return;
            }

        } else if (tipoCola.equalsIgnoreCase("2")) {
            // Verificar si el usuario ya tiene una cola de intentos fallidos
            intentosFallidosPorUsuario.putIfAbsent(valor, new Cola());
            // COLA
            Cola intentosFallidos = intentosFallidosPorUsuario.get(valor);

            intentosFallidos.enqueue(valor); // Agrega un intento fallido a la cola del usuario

            if (intentosFallidos.size() >= 2) { // Si el usuario ha tenido 2 intentos fallidos
                valor.setBloqueado(true); // Bloquea la cuenta del usuario
                System.out.println("La cuenta de " + valor.getNombre()
                        + " ha sido bloqueada debido a multiples intentos fallidos de inicio de sesion.");
                System.out.println("Pruebe denuevo en 5 minutos.");
                horabloqueada = LocalDateTime.now();
                valor.setHoraDesbloqueo(horabloqueada.plusSeconds(20));
            } else {
                return;
            }
        }
    }

    private void usuarioBloquados(Administradores admin) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("TXT/UsuariosBloqueados.txt", true))) {
            bw.write(admin.aTexto()); // escribe en el archivo el ultimo elemento en la pila
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de historial de logins");
        }
    }

    @Override
    public boolean existe(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    // Hola we, como se abre el chat nose

    @Override
    public void modificar(String datoModificar, int opcion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void buscar(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(String identificador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
