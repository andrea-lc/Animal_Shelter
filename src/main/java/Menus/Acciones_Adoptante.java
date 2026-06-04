/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import Entidades.Adoptantes;
import Entidades.Persona;
import Gestores.GestorInterface;
import Gestores.Gestor_Adoptante;
import Gestores.Gestor_Gatos;
import Scanner.Lector;

/**
 *
 * @author admin
 */
public class Acciones_Adoptante implements Menu_Acciones {

    Lector lector = Lector.getInstanciaLector();
    GestorInterface<Adoptantes> gestor_adoptante = Gestor_Adoptante.getInstanciaAdoptantes();
    Gestor_Gatos gestor_gatos = Gestor_Gatos.getInstanciaGatos();

    @Override
    /*
     * aca ocurre un error, que pasa si se ingresa un gato pero este no esta en
     * la lista de los adoptables, ocurre un error, por eso puse un try porque
     * si no se cerraba el programa abrutamente :(
     * 
     * otro problema :(, primero hace todo este proceso y al final comprueba si el
     * dni
     * ya esta registrado, y durante todo el proceso el gato que se escogia para ser
     * adoptado
     * ya se sobre escribia como adoptado, aun asi el proceso completo de registrar
     * no se realizaba
     * por duplicado de dni, entonce sla solucion es remplazar las anteriores
     * clases, y ahora tener
     * un metodo que verifique que el gato esta en adopcion, y otro que cambie su
     * estado a adoptado
     */

    public void registrar() { // registrara el proceso para adoptar
        System.out.println("\n========== REGISTRO ==========");
        System.out.print("Dni: ");
        int dni = lector.LeerEntero();
        System.out.print("Nombre: ");
        String nombre = lector.LeerStringMayuscula();
        System.out.print("Apellido: ");
        String apellido = lector.LeerStringMayuscula();
        System.out.print("Correo: ");
        String correo = lector.LeerString();
        System.out.print("Telefono: ");
        int telefono = lector.LeerEntero();
        gestor_gatos.mostrarGatos(); // muestra gatos en adopcion :0
        System.out.print("Ingrese el nombre del gato que desea adoptar: ");
        String gato_Adoptado;
        while (true) {
            gato_Adoptado = lector.LeerStringMayuscula();
            if (gestor_gatos.VerificadordeGatoEnAdopcion(gato_Adoptado)) {
                break;
            } else {
                System.out.print("Invalido, ingrese un nombre: ");
            }
        }
        System.out.println("==================================");
        Adoptantes nuevoAdoptante = new Adoptantes(
                new Persona(dni, nombre, apellido, telefono, correo),
                gato_Adoptado);

        // Si el registro es exitoso, cambiar el estado del gato
        if (gestor_adoptante.registrar(nuevoAdoptante)) {
            gestor_gatos.CambiarEstadoAdoptado(gato_Adoptado);
            System.out.println("==================================");
            System.out.println("Adopcion registrada exitosamente");
        }
    }

    @Override
    public void Listar() {
        gestor_adoptante.mostrar();
    }

    @Override
    public void Buscar() {
        System.out.println("\n======= BUSCAR ADOPTANTES =======");
        System.out.print("Ingrese el nombre o dni del adoptante que desea buscar: ");
        String adoptanteBuscado = lector.LeerString();
        if (gestor_adoptante.existe(adoptanteBuscado) == true) {
            gestor_adoptante.buscar(adoptanteBuscado);
        } else {
            System.out.println("Adoptante no encontrado");
        }
        System.out.println("==================================");
    }

    @Override
    public void modificar() { // aqui se modificara el proceso a rechzado o aceptado
        System.out.println("\n======= MODIFICAR DATOS =======");
        System.out.print("Ingrese el nombre o ID del adoptante que desea modificar: ");
        String adoptanteModificar = lector.LeerString();
        int opcion;

        if (gestor_adoptante.existe(adoptanteModificar) == true) {
            boolean seguir; // va a iniciar como si se quisiera continuar modificando
            do {
                System.out.println("Que dato desea modificar: ");
                System.out.println("1) Telefono: ");
                System.out.println("2) Correo: ");

                while (true) {
                    System.out.print("Ingrese una opcion: ");
                    opcion = lector.LeerEntero();
                    if (opcion > 2 || opcion < 1) {
                        System.out.println("Opcion invalida");
                    } else {
                        break;
                    }
                }

                gestor_adoptante.modificar(adoptanteModificar, opcion);
                System.out.print("Desea modificar otro dato? (si/no): ");
                String respuesta = lector.LeerString();
                seguir = respuesta.equalsIgnoreCase("si"); // ? true:false
                System.out.println("===============================");
            } while (seguir);
            System.out.println("Datos modificados exitosamente! ");
        } else {
            System.out.println("Adoptante no encontrado");
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n======= ELIMINAR DATOS =======");
        System.out.print("Ingrese el nombre o ID del adoptante que desea eliminar: ");
        String datoEliminar = lector.LeerString();
        if (gestor_adoptante.existe(datoEliminar)) {
            Adoptantes adoptante = gestor_adoptante.retornarElemento(datoEliminar);

            if (adoptante != null) {
                String nombreGatoAdoptado = adoptante.getGato_Adoptado();

                if (gestor_adoptante.eliminar(datoEliminar)) {
                    gestor_gatos.CambiarEstadoEnAdopcion(nombreGatoAdoptado);
                }

                System.out.println("==================================");
                System.out.println("Adoptante eliminado exitosamente!");
            }
        } else {
            System.out.println("Adoptante no encontrado");
        }
    }

}
