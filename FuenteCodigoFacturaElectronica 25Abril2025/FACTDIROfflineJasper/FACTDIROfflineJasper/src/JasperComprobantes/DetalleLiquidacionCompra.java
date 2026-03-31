package JasperComprobantes;

import java.util.List;

public class DetalleLiquidacionCompra {
   private String codigoPrincipal;
   private String codigoAuxiliar;
   private String descripcion;
   private String cantidad;
   private String precioUnitario;
   private String descuento;
   private String precioTotalSinImpuesto;
   private List<DetalleAdicional> detalleAdicional;

   public String getCodigoPrincipal() {
      return this.codigoPrincipal;
   }

   public void setCodigoPrincipal(String codigoPrincipal) {
      this.codigoPrincipal = codigoPrincipal;
   }

   public String getCodigoAuxiliar() {
      return this.codigoAuxiliar;
   }

   public void setCodigoAuxiliar(String codigoAuxiliar) {
      this.codigoAuxiliar = codigoAuxiliar;
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

   public String getPrecioUnitario() {
      return this.precioUnitario;
   }

   public void setPrecioUnitario(String precioUnitario) {
      this.precioUnitario = precioUnitario;
   }

   public String getDescuento() {
      return this.descuento;
   }

   public void setDescuento(String descuento) {
      this.descuento = descuento;
   }

   public String getPrecioTotalSinImpuesto() {
      return this.precioTotalSinImpuesto;
   }

   public void setPrecioTotalSinImpuesto(String precioTotalSinImpuesto) {
      this.precioTotalSinImpuesto = precioTotalSinImpuesto;
   }

   public List<DetalleAdicional> getDetalleAdicional() {
      return this.detalleAdicional;
   }

   public void setDetalleAdicional(List<DetalleAdicional> detalleAdicional) {
      this.detalleAdicional = detalleAdicional;
   }
}
