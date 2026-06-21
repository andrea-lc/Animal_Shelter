/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menus;

import Entidades.Persona;
import Entidades.Voluntarios;
import Gestores.GestorInterface;
import Gestores.Gestor_Voluntario;
import Scanner.Lector;

/**
 *
 * @author admin
 */
public class Acciones_Voluntario implements Menu_Acciones{
    Lector lector = Lector.getInstanciaLector();
    GestorInterface<Voluntarios> gestor_voluntario = Gestor_Voluntario.getInstanciaVoluntarios();
    
    @Override
    public void registrar() {
        System.out.println("\n========== REGISTRO DE VOLUNTARIO ==========");
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
        
        // Mostrar horarios disponibles
        horariosDisponibles();
        System.out.print("Ingrese una opcion: ");
        int opcion;
        String horarios_disponibles;
        
        while (true) {
            opcion = lector.LeerEntero();
            if (opcion >= 1 && opcion <= 6) {
                horarios_disponibles = Gestor_Voluntario.gestionHorario(opcion);
                break;
            } else {
                System.out.print("Opción inválida, ingrese otra: ");
            }
        }
        
        Voluntarios nuevoVoluntario = new Voluntarios(
            new Persona(dni, nombre, apellido, telefono, correo),
            horarios_disponibles
        );
        
        if (gestor_voluntario.registrar(nuevoVoluntario)) {
            System.out.println("Voluntario registrado exitosamente!");
            System.out.println("Total voluntarios: " + 
                              ((Gestor_Voluntario)gestor_voluntario).getListaVoluntarios().getTamaño());
        }
    }

    @Override
    public void Listar() {
        gestor_voluntario.mostrar();
    }

    @Override
    public void Buscar() {
        System.out.println("\n======= BUSCAR VOLUNTARIOS =======");
        System.out.print("Ingrese el nombre o DNI del voluntario: ");
        String voluntarioBuscado = lector.LeerString();
        if (gestor_voluntario.existe(voluntarioBuscado)) {
            gestor_voluntario.buscar(voluntarioBuscado);
        } else {
            System.out.println("Voluntario no encontrado");
        }
        System.out.println("==================================");
    }

    @Override
    public void modificar() {
        System.out.println("\n======= MODIFICAR DATOS =======");
        System.out.print("Ingrese el nombre o DNI del voluntario: ");
        String voluntarioModificar = lector.LeerString();
        
        if (gestor_voluntario.existe(voluntarioModificar)) {
            boolean seguir;
            do {
                System.out.println("Que dato desea modificar: ");
                System.out.println("1) Telefono");
                System.out.println("2) Correo");
                System.out.println("3) Horario");
                System.out.print("Ingrese una opcion: ");
                int opcion = lector.LeerEntero();
                
                if (opcion == 3) {
                    horariosDisponibles();
                    System.out.print("Seleccione un horario: ");
                    int opcionHorario = lector.LeerEntero();
                    if (opcionHorario >= 1 && opcionHorario <= 6) {
                        gestor_voluntario.modificar(voluntarioModificar, 3);
                    } else {
                        System.out.println("Horario inválido");
                    }
                } else if (opcion >= 1 && opcion <= 2) {
                    gestor_voluntario.modificar(voluntarioModificar, opcion);
                } else {
                    System.out.println("Opción inválida");
                }
                
                System.out.print("Desea modificar otro dato? (si/no): ");
                seguir = lector.LeerString().equalsIgnoreCase("si");
            } while (seguir);
            System.out.println("Datos modificados exitosamente!");
        } else {
            System.out.println("Voluntario no encontrado");
        }
    }

    @Override
    public void eliminar() {
        System.out.println("\n======= ELIMINAR DATOS =======");
        System.out.print("Ingrese el nombre o DNI del voluntario: ");
        String datoEliminar = lector.LeerString();
        
        if (gestor_voluntario.existe(datoEliminar)) {
            if (gestor_voluntario.eliminar(datoEliminar)) {
                System.out.println("Voluntario eliminado exitosamente!");
            }
        } else {
            System.out.println("Voluntario no encontrado");
        }
    }
    
    private void horariosDisponibles() {
        String[] horarios = {
            "Lunes (Diurno): 9:00 - 11:00",
            "Miércoles (Diurno): 10:00 - 12:00",
            "Viernes (Diurno): 8:00 - 10:00",
            "Martes (Tarde): 13:00 - 15:00",
            "Jueves (Tarde): 15:00 - 17:00",
            "Sábado (Tarde): 16:00 - 18:00"
        };
        
        System.out.println("\n===== HORARIOS DISPONIBLES =====");
        for (int i = 0; i < horarios.length; i++) {
            System.out.println((i + 1) + ") " + horarios[i]);
        }
    }
}
