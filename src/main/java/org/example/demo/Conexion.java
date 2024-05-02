package org.example.demo;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion
{
  Connection conectar = null;

  String user = "root";
  String password = "";
  String bd= "proyectoderecho";
  String ip = "localhost";
  String puerto = "3306";

  String cadena =  "jdbc:mysql://"+ip+":"+puerto+"/"+bd;

  public Connection establecerConexion() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conectar = DriverManager.getConnection(cadena,user,password);
    //JOptionPane.showMessageDialog(null,"conectada");
    }catch (Exception e){
      JOptionPane.showMessageDialog(null,"error al conectarse a la base de datos "+e);
    }
    return  conectar;
  }

  public void desconectarConexion(){
    conectar = null;
  }
}

