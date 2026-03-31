package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConexionMySql {
   private Map<String, Connection> conexiones = new HashMap();
   private String host = null;
   private String bd = null;
   private String usuario = null;
   private String password = null;

   public void cerrarConexiones() {
      Iterator var1 = this.conexiones.values().iterator();

      while(var1.hasNext()) {
         Connection value = (Connection)var1.next();

         try {
            value.close();
         } catch (Exception var4) {
         }
      }

      this.conexiones.clear();
   }

   public boolean conectar(String host, String bd, String usuario, String password) {
      this.host = host;
      this.bd = bd;
      this.usuario = usuario;
      this.password = password;

      try {
         if (!this.conexiones.containsKey(bd)) {
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://" + host + ":3306/" + bd + "?autoReconnect=true&failOverReadOnly=false&maxReconnects=3";
            Class.forName(driver);
            this.conexiones.put(bd, DriverManager.getConnection(url, usuario, password));
         }

         return true;
      } catch (Exception var7) {
         System.out.println("Error conectando a la base " + bd);
         System.out.println(var7.getMessage());
         return false;
      }
   }

   public Connection getConnection() {
      return (Connection)this.conexiones.get(this.bd);
   }

   public String ejecutar(String SQL) {
      try {
         if (this.getConnection() != null) {
            PreparedStatement query = this.getConnection().prepareStatement(SQL);
            query.execute();
            return "";
         } else {
            return "No se ha establecido conexion con la Base de Datos";
         }
      } catch (Exception var3) {
         return "Error ejecutando la accion sobre la base de datos. Error: " + var3.getMessage();
      }
   }

   public ResultSet consultar(String SQL) {
      try {
         if (this.getConnection() != null) {
            PreparedStatement query = this.getConnection().prepareStatement(SQL);
            ResultSet resultado = query.executeQuery();
            return resultado;
         } else {
            return null;
         }
      } catch (Exception var4) {
         return null;
      }
   }
}
