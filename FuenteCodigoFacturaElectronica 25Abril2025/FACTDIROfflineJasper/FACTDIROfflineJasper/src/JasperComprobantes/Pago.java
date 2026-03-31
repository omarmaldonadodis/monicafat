package JasperComprobantes;

public class Pago {
   private String formaPago;
   private String total;
   private String plazo;
   private String unidadTiempo;

   public String getFormaPago() {
      return this.formaPago;
   }

   public void setFormaPago(String formaPago) {
      this.formaPago = formaPago;
   }

   public String getTotal() {
      return this.total;
   }

   public void setTotal(String total) {
      this.total = total;
   }

   public String getPlazo() {
      return this.plazo;
   }

   public void setPlazo(String plazo) {
      this.plazo = plazo;
   }

   public String getUnidadTiempo() {
      return this.unidadTiempo;
   }

   public void setUnidadTiempo(String unidadTiempo) {
      this.unidadTiempo = unidadTiempo;
   }
}
