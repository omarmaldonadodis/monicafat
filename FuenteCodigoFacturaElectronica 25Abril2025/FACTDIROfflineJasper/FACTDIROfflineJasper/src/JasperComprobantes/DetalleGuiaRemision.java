package JasperComprobantes;

public class DetalleGuiaRemision {
   private String codigoInterno;
   private String codigoAdicional;
   private String descripcion;
   private String cantidad;

   public String getCodigoInterno() {
      return this.codigoInterno;
   }

   public void setCodigoInterno(String codigoInterno) {
      this.codigoInterno = codigoInterno;
   }

   public String getCodigoAdicional() {
      return this.codigoAdicional;
   }

   public void setCodigoAdicional(String codigoAdicional) {
      this.codigoAdicional = codigoAdicional;
   }

   public String getDescripcion() {
      return this.descripcion;
   }

   public void setDescripcion(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getCantidad() {
      return this.cantidad;
   }

   public void setCantidad(String cantidad) {
      this.cantidad = cantidad;
   }
}
