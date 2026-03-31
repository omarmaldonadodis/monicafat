package JasperComprobantes;

public class TotalImpuesto {
   private String codigo;
   private String codigoPorcentaje;
   private String descuentoAdicional;
   private String baseImponible;
   private String tarifa;
   private String valor;

   public String getCodigo() {
      return this.codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   public String getCodigoPorcentaje() {
      return this.codigoPorcentaje;
   }

   public void setCodigoPorcentaje(String codigoPorcentaje) {
      this.codigoPorcentaje = codigoPorcentaje;
   }

   public String getBaseImponible() {
      return this.baseImponible;
   }

   public void setBaseImponible(String baseImponible) {
      this.baseImponible = baseImponible;
   }

   public String getValor() {
      return this.valor;
   }

   public void setValor(String valor) {
      this.valor = valor;
   }

   public String getDescuentoAdicional() {
      return this.descuentoAdicional;
   }

   public void setDescuentoAdicional(String descuentoAdicional) {
      this.descuentoAdicional = descuentoAdicional;
   }

   public String getTarifa() {
      return this.tarifa;
   }

   public void setTarifa(String tarifa) {
      this.tarifa = tarifa;
   }
}
