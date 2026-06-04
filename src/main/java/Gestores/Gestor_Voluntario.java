/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestores;

import Entidades.Persona;
import Entidades.Voluntarios;
import Scanner.Lector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author admin
 */
public class Gestor_Voluntario extends GestorBase<Voluntarios> {
    private static Gestor_Voluntario instancia;
    Lector lector=Lector.getInstanciaLector();
            
    
    public Gestor_Voluntario() {
        super("TXT/Voluntarios.txt");
    }
    
    public static Gestor_Voluntario getInstanciaAdoptante (){
        if (instancia==null){
            instancia=new Gestor_Voluntario();
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
                    String apellido= datos[2];
                    int telefono = Integer.parseInt(datos[3]);
                    String correo = datos[4];
                    String horarios_disponibles = datos[5];
                    Voluntarios voluntario= new Voluntarios(new Persona(dni,nombre,apellido,telefono,correo)
                            ,horarios_disponibles);
                    
                    getElementos().put((String.valueOf(dni)),voluntario);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar voluntarios (puede que el archivo este vacio).");
        }
    }

    @Override
    public void guardarCambios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Voluntarios voluntario : getElementos().values()) {
                String linea = String.format("%d,%s,%s,%d,%s,%s",
                    voluntario.getDni_persona(),
                    voluntario.getNombre(),
                    voluntario.getApellido(),
                    voluntario.getTelefono(),
                    voluntario.getCorreo(),
                    voluntario.getHorarios_disponibles());
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error al guardar cambios en el archivo de voluntario.");
        }
    }

    @Override
    public boolean registrar(Voluntarios voluntario) {
        if (getElementos().containsKey(String.valueOf(voluntario.getDni_persona()))) {
            System.out.println("Este DNI ya esta registrado como voluntario.");
            return false;
        }      
        getElementos().put((String.valueOf(voluntario.getDni_persona())), voluntario);
        guardarCambios();
        return true;
    }

    @Override
    public boolean existe(String identificador) {
        boolean resultado=false;
        Voluntarios voluntario= retornarElemento(identificador);       
            if (voluntario!=null){
                resultado=true;
            }     
        return resultado;
    }

    @Override
    public void mostrar() {
        if (getElementos().isEmpty()) {
            System.out.println("No hay voluntarios registrados en el sistema.");
            return;
        }  
        
        System.out.println("\n=== LISTA DE VOLUNTARIOS REGISTRADOS ===");
        System.out.println("Total de voluntarios: " + getElementos().size());
        System.out.println("----------------------------------------");             
        getElementos_listaporNombre().forEach(System.out::println);      
    }

    @Override
    public void modificar(String datoModificar, int opcion) {
        Voluntarios voluntario = retornarElemento(datoModificar);

        Consumer <Voluntarios> [] modificador= new Consumer[4];
        if (voluntario != null){
            modificador[1]= (v) ->{ System.out.print("Nuevo telefono: "); 
                                    v.setTelefono(lector.LeerEntero());};
            modificador[2]= (v)->{ System.out.print("Nuevo correo: ");
                                    v.setCorreo(lector.LeerString());};      
            modificador[3]= (v) ->{System.out.print("Nuevo horario: ");                                    
                                    v.setHorarios_disponibles(gestionHorario(lector.LeerEntero()));
                                    };
        }
        modificador[opcion].accept(voluntario);      
        guardarCambios();
    }

    @Override
    public void buscar(String identificador) {        
        List<Voluntarios> resultados= new ArrayList <>();
        // Buscar por DNI (clave del HashMap)
        if (getElementos().containsKey(identificador)) {
            resultados.add(getElementos().get(identificador));
        } else {
            // Buscar por nombre o correo
            for (Voluntarios v : getElementos().values()) {
                if (identificador.equalsIgnoreCase(v.getNombre())) {
                    resultados.add(v);
                }
            }
        }
        System.out.println("Resultados: "+ resultados.size());
        System.out.println("-----------------------------------");
        resultados.forEach(System.out::println);      
    } 
    
        @Override
    public boolean eliminar(String identificador) {
        Voluntarios voluntario= retornarElemento(identificador);
        if (voluntario!=null){
            getElementos().remove(String.valueOf(voluntario.getDni_persona()));
            guardarCambios();
        }
        return true;
    }
    
    public static String gestionHorario(int opcion){  
        String [] horarios={"Lunes (Diurno): 9:00 - 11:00", 
                            "Miércoles (Diurno): 10:00 - 12:00",
                            "Viernes (Diurno): 8:00 - 10:00",
                            "Martes (Tarde): 13:00 - 15:00",
                            "Jueves (Tarde): 15:00 - 17:00", 
                            "Sábado (Tarde): 16:00 - 18:00" };
        Supplier<String>[] HorarioEscogido= new Supplier[6];  
        HorarioEscogido[0]= ()->{return horarios[0];};
        HorarioEscogido[1]= ()->{return horarios[1];};
        HorarioEscogido[2]= ()->{return horarios[2];};
        HorarioEscogido[3]= ()->{return horarios[3];};
        HorarioEscogido[4]= ()->{return horarios[4];};
        HorarioEscogido[5]= ()->{return horarios[5];};
             
        return HorarioEscogido[opcion-1].get();
    } 


}
