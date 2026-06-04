package Clases_Persistente;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class ConexionDB {
    //jdbc:sqlserver:/ Indica que usamos el driver JDBC de Microsoft SQL Server.
    // localhost\\SQLEXPRESSEl servidor está en tu maquina (localhost) y la instancia de SQL Server se llama SQLEXPRESS
    //:1433 Puerto estandar de SQL Server.
    // databaseName=cat_haven Nombre de la base de datos a la que te conectas.
    // encrypt=true;trustServerCertificate=true; : Configuraciones de seguridad

    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=cat_haven;encrypt=true;trustServerCertificate=true;";
    private static final String USUARIO = "andrea";
    private static final String CONTRASENA = "123456";
    
    // Es una referencia  al objeto Connection de JDBC
    // Connection es una interfaz de Java (java.sql.Connection) que representa
    // una sesion con la base de datos
    private static Connection conexion;
    

    public static Connection obtenerConexion() {
        //Toda operación con base de datos puede fallar:contraseña incorrecta,etc.
        try {
            // conexion == null :La primera vez que se llama al método. Nadie ha creado una conexión todavia
            // isClosed: Ya existia una conexion, pero fue cerrada (por ejemplo, por un reinicio de la base de datos=
            if (conexion == null || conexion.isClosed()) {
                // DriverManager es una clase de Java que gestiona los drivers de bases de datos( el puente en el programa y la bd)
                // get conecction, abre la conexion con la bd
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA); 
                System.out.println("Conexion exitosa a la base de datos");
            }
            return conexion; // Devuelve la conexion 
            // El codigo que llama a este metodo recibe un objeto Connection que puede usar para hacer SELECT, INSERT, etc.

        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;//en el caso que no se pueda conectar, devolvemos null para indicar que no hay conexion
        }
    }
 
}
