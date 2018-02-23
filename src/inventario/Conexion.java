package inventario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author edson
 */
public final class Conexion {
    //VARIABLES CONEXION, CONSULTAS.
    private Connection cnx = null;
    private Statement sentencia = null;
    private ResultSet res = null;
    
    //CONSTRUCTOR
    public Conexion(){
        conectar();
    }
    //CONECTAR BASE DE DATOS
    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");//DRIVER
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/inventario", "root", "");//CONECCION (DATOS: DIRECCION DE LA BASE, USER, PASS)
            if(cnx != null){
                sentencia = cnx.createStatement();//CREAR LA CONECCION
                System.out.println("Coneccion exitosa");
            }
        }        
        catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex);//ERROR
        }
    }
    //CERRAR LA CONEXION
    public void cerrar(){
        if(cnx != null){
            try {
                cnx.close();
                System.out.println("Coneccion cerrada");
            } catch (SQLException ex) {
                System.err.println(ex);
            }
        }
    }
    //EJECUTAR SQL (UPDATE, INSERT, DELETE)
    public boolean ejecutar(String sql){
        boolean flag;
        try{
            sentencia.execute(sql);
            //sentencia.close();
            flag = true;
        }
        catch(SQLException ex){
            System.err.println(ex);
            flag = false;
        }
        return flag;
    }
    //CONSULTAS (SELECT, JOIN)
    public ResultSet consultar(String sql){
        try{
            res = sentencia.executeQuery(sql);
        }
        catch(SQLException ex){
            System.err.println(ex);
            return null;
        }
        catch(NullPointerException ex){
            System.err.println(ex);
        }
        return res;
    }
    public Statement getSentencia(){
        return sentencia;
    }
}
