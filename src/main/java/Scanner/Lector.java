/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Scanner;

import java.util.Scanner;

/**
 *
 * @author admin
 */
public class  Lector{
    private final Scanner scanner = new java.util.Scanner(System.in);
    
    private static Lector instancia;
    
    public static Lector getInstanciaLector (){
        if (instancia==null){
            instancia=new Lector();
        }
        return instancia; 
    }
    public int LeerEntero (){
        while (true){
            try {
                String salida=scanner.nextLine().trim();               
                return Integer.parseInt(salida);               
            }catch (NumberFormatException e){
                System.out.print("Ingrese un numero valido: ");
            }
        }
    }
    public double LeerDouble() {
        while (true) {
            try {
                String salida = scanner.nextLine().trim().toLowerCase();               
                return Double.parseDouble(salida);               
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un numero decimal valido: ");
            }
        }
    }
    public String LeerString () {
     while (true){
         String salida=scanner.nextLine().trim().toLowerCase();
            if (!salida.isEmpty()){
              return salida;
            } else {
                System.out.print("Ingrese un dato valido: ");
            }
        }
    }
    public String LeerStringMayuscula() {
     while (true){
         String salida=scanner.nextLine().trim().toLowerCase();
            if (!salida.isEmpty()){
              return Mayuscula(salida);
            } else {
                System.out.print("Ingrese un dato valido: ");
            }
        }
    }
    
    private String Mayuscula(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }
        // mensaje pa no olvidarme: (x,y) indice x si muestra, y "y" no muestra
        String textoMayuscula= texto.substring(0,1).toUpperCase();
        
        return textoMayuscula+texto.substring(1);
    }
}
