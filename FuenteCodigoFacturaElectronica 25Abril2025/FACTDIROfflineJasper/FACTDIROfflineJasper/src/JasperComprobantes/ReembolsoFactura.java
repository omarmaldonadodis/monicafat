package JasperComprobantes;

public class ReembolsoFactura {
   private String identificacionProveedorReembolso;
   private String tipoDocumento;
   private String noDocumento;
   private String fechaEmisionDocReembolso;
   private String impuesto;
   private String porcentaje;
   private double baseImponible;
   private double valorImpuesto;
   private double total;

   public String getIdentificacionProveedorReembolso() {
      return this.identificacionProveedorReembolso;
   }

   public void setIdentificacionProveedorReembolso(String identificacionProveedorReembolso) {
      this.identificacionProveedorReembolso = identificacionProveedorReembolso;
   }

   public String getTipoDocumento() {
      return this.tipoDocumento;
   }

   public void setTipoDocumento(String tipoDocumento) {
      this.tipoDocumento = tipoDocumento;
   }

   public String getNoDocumento() {
      return this.noDocumento;
   }

   public void setNoDocumento(String noDocumento) {
      this.noDocumento = noDocumento;
   }

   public String getFechaEmisionDocReembolso() {
      return this.fechaEmisionDocReembolso;
   }

   public void setFechaEmisionDocReembolso(String fechaEmisionDocReembolso) {
      this.fechaEmisionDocReembolso = fechaEmisionDocReembolso;
   }

   public String getImpuesto() {
      return this.impuesto;
   }

   public void setImpuesto(String impuesto) {
      this.impuesto = impuesto;
   }

   public String getPorcentaje() {
      return this.porcentaje;
   }

   public void setPorcentaje(String porcentaje) {
      this.porcentaje = porcentaje;
   }

   public double getBaseImponible() {
      return this.baseImponible;
   }

   public void setBaseImponible(double baseImponible) {
      this.baseImponible = baseImponible;
   }

   public double getValorImpuesto() {
      return this.valorImpuesto;
   }

   public void setValorImpuesto(double valorImpuesto) {
      this.valorImpuesto = valorImpuesto;
   }

   public double getTotal() {
      return this.total;
   }

   public void setTotal(double total) {
      this.total = total;
   }
}
