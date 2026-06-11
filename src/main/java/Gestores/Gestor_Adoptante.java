/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Adoptantes;
import Entidades.Persona;
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
public class Gestor_Adoptante extends GestorBase<Adoptantes> {
    Lector lector = Lector.getInstanciaLector();
    private static Gestor_Adoptante instancia;

    public Gestor_Adoptante() {
        super("TXT/Adoptantes.txt");
    }

    public static Gestor_Adoptante getInstanciaAdoptantes() {
        if (instancia == null) {
            instancia = new Gestor_Adoptante();
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
                    String gato_Adoptado = datos[5];
                    Adoptantes adoptante = new Adoptantes(new Persona(dni, nombre, apellido, telefono, correo),
                            gato_Adoptado);

                    getElementos().put((String.valueOf(dni)), adoptante);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar adoptantes (puede que el archivo este vacio)");
        }
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Adoptantes adoptante : getElementos().values()) {
                String linea = String.format("%d,%s,%s,%d,%s,%s",
                        adoptante.getDni_persona(),
                        adoptante.getNombre(),
                        adoptante.getApellido(),
                        adoptante.getTelefono(),
                        adoptante.getCorreo(),
                        adoptante.getGato_Adoptado());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de adoptantes");
        }
    }

    @Override
    public boolean registrar(Adoptantes adoptante) {
        if (getElementos().containsKey(String.valueOf(adoptante.getDni_persona()))) {
            System.out.println("Este DNI ya esta registrado como adoptante");
            return false;
        }

        getElementos().put((String.valueOf(adoptante.getDni_persona())), adoptante);
        guardarCambios();
        return true;
    }

    @Override
    public boolean existe(String identificador) {
        boolean resultado = false;
        Adoptantes adoptante = retornarElemento(identificador);
        if (adoptante != null) {
            resultado = true;
        }
        return resultado;
    }

    @Override
    public void mostrar() {
        if (getElementos().isEmpty()) {
            System.out.println("No hay adoptantes registrados en el sistema");
            return;
        }
        System.out.println("\n=== LISTA DE ADOPTANTES REGISTRADOS ===");
        System.out.println("Total de adoptantes: " + getElementos().size());
        System.out.println("-----------------------------------");
        getElementos_listaporNombre().forEach(System.out::println);
    }

    @Override
    public void modificar(String datoModificar, int opcion) {
        Adoptantes adoptante = retornarElemento(datoModificar);
        Consumer<Adoptantes>[] modificador = new Consumer[3];
        if (adoptante != null) {
            modificador[1] = (a) -> {
                System.out.print("Nuevo telefono: ");
                a.setTelefono(lector.LeerEntero());
            };
            modificador[2] = (a) -> {
                System.out.print("Nuevo correo: ");
                a.setCorreo(lector.LeerString());
            };
        }
        modificador[opcion].accept(adoptante);
        guardarCambios();
    }

    @Override
    public void buscar(String identificador) {
        List<Adoptantes> resultados = new ArrayList<>();
        // Buscar por DNI (clave del HashMap)
        if (getElementos().containsKey(identificador)) {
            resultados.add(getElementos().get(identificador));
        } else {
            // Buscar por nombre o correo
            for (Adoptantes a : getElementos().values()) {
                if (identificador.equalsIgnoreCase(a.getNombre())) {
                    resultados.add(a);
                }
            }
        }
        System.out.println("Resultados: " + resultados.size());
        System.out.println("-----------------------------------");
        resultados.forEach(System.out::println);
    }

    @Override
    public boolean eliminar(String identificador) {
        Adoptantes adoptante = retornarElemento(identificador);
        if (adoptante != null) {
            getElementos().remove(String.valueOf(adoptante.getDni_persona()));
            guardarCambios();
        }
        return true;
    }
}