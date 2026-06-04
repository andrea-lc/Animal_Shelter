/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases_Persistente;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author admin
 */
public class SerializadorXML {
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static <T> void guardarEnXML(List<T> lista, String nombreArchivo) {
        try {
            //convierte la lista delos elementos en xml y lo convierte en texto con su formato
            // y lo guarda en una variable string, luego crea el archivo y escirbe el xml dentro de el 
            String xml = xmlMapper.writeValueAsString(lista);

            
            
            FileWriter archivo = new FileWriter(nombreArchivo);
            archivo.write(xml);
            archivo.close();
            System.out.println("Guardado en XML" );
            
        } catch (IOException e) {
            System.out.println("Error al guardar XML ");
        }       
    }
}
