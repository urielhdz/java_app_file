/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package concurrentfinalproject;

/**
 *
 * @author urielhernandez
 */
import java.io.*;
import java.sql.*;

class Conexion {
    Connection          conexion = null;
    Statement           st = null;
    ResultSet           rs = null;
    javax.sql.DataSource ds=null;
    
  public Conexion(String host, String userDB, String passDB, String projectDB) throws ClassNotFoundException, SQLException{
      
    try {
            Class.forName("org.gjt.mm.mysql.Driver"); //Comprobar el conector
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+projectDB, userDB, passDB);
            st=conexion.createStatement();
        } catch (ClassNotFoundException ex) {
            throw new ClassNotFoundException("Conector no encontrado. Mensaje de error: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new SQLException("Error en SQL. Mensaje de error: " + ex.getMessage());
        }
  }
  
  public boolean insertar(String instruccion) {
        try{
            if (!conexion.isClosed()){
                st.execute(instruccion); //Ejecuta los commandos SQL
            }else{ // Error en la conexión
                System.out.println("Mensaje de error: La conexión con la Base de Datos está cerrada.");
                return false;
            }
        }catch(SQLException ex){
            System.out.println("Mensaje de error: " + ex.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Recibe alguna sentencia para borrar y la ejecuta
     * @param instruccion La sentencia SQL
     * @return Si se borró o no
     */
    public boolean borrar(String instruccion) {
        return insertar(instruccion);
    }
    
    /**
     * Recibe alguna sentencia para actualizar y la ejecuta
     * @param instruccion La sentencia SQL
     * @return Si se actualizó o no
     */
    public boolean actualizar(String instruccion) {
        try{
            if (!conexion.isClosed()){
                st.executeUpdate(instruccion); //Ejecuta el comando de actualización SQL
            }else{ // Error en la conexión
                System.out.println("Mensaje de error: La conexión con la Base de Datos está cerrada.");
                return false;
            }
        }catch(SQLException ex){
            System.out.println("Error "+ex.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Recibe alguna sentencia para hacer una selección y la ejecuta
     * @param instruccion La sentencia SQL
     * @return La selección hecha, si no hay registros devuelve null
     */
    public ResultSet buscar(String instruccion) {
        try{
            if (!conexion.isClosed()){
                ResultSet rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
                if(rs == null){
                    System.out.println("No hay resultados que coincidan con la búsqueda.");
                    return null;
                }

                return rs;
            }else{ //Error en la conexión
                System.out.println("La conexión con la Base de Datos está cerrada.");
                return null;
            }
        }catch(SQLException ex){
            System.out.println("Error " + ex.getMessage());
            return null;
        }
    }
   
   /* Busca la cantidad de registros que hay en la tabla especificada.
     * Devuelve -1 en caso de haber algún error
     * @param tabla La tabla donde contar registros
     * @return La cantidad de registros encontrados o -1 si hay algún error
     */
    public int contarRegistros(String tabla) {
        ResultSet rs = buscar("SELECT COUNT(*) FROM " + tabla);
        if (rs != null) {
            try {
                return rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println("Error " + ex.getMessage());
                return -1;
            }
        } else {
            return -1;
        }
    }
    
    /**
     * Cerrar la conexión con la BD.
     * Cuando ya no se necesite usar la conexión a la BD, es recomendable cerrarla.
     * No se puede volver a utilizar el objeto después de cerrar la conexión
     */
    public void cerrar() {
        try {
            st.close(); // Cerrar el Statement
            conexion.close(); // Cierre de la conexión
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            conexion=null;
            st=null;
        }
    }
  public static void main(String argv[]) {/*
    try { 
      System.out.println("\n Creating IBM DB2 JCC DataSource");
      

      
                  
      System.out.println("\n Connecting to database using JDBC Universal type 4 driver....");
      
      System.out.println("\n Connected to database successfully.");
           
     System.out.println("\n  Executing query....");
     stmt = con.createStatement();
     String query = "SELECT COUNT(*) FROM SYSCAT.TABLES";
     rs = stmt.executeQuery(query);
     while (rs.next()) 
     {
       System.out.println("\n " + query + " = " + rs.getInt(1));
     }

      rs.close();
      stmt.close();
      
      System.out.println("\n  Closing connection...");
      con.close();
      System.out.println("\n Connection closed.");
    
    } catch (Exception e) {

      System.out.println(e);
      e.printStackTrace();    }*/
  } // end main
} // end Jcctest 
