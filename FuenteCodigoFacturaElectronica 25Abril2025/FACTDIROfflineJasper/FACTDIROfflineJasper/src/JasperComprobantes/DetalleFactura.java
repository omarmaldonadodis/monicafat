package JasperComprobantes;

import java.util.List;

public class DetalleFactura {
   private String codigoPrincipal;
   private String codigoAuxiliar;
   private String descripcion;
   private String cantidad;
   private String precioUnitario;
   private String descuento;
   private String precioTotalSinImpuesto;
   private List<DetalleAdicional> detalleAdicional;
   private String precioSinSubsidio;
   private String detalleAdicional1;
   private String detalleAdicional2;
   private String detalleAdicional3;

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

   public String getPrecioSinSubsidio() {
      return this.precioSinSubsidio;
   }

   public void setPrecioSinSubsidio(String precioSinSubsidio) {
      this.precioSinSubsidio = precioSinSubsidio;
   }

   public String getDetalleAdicional1() {
      return this.detalleAdicional1;
   }

   public void setDetalleAdicional1(String detalleAdicional1) {
      this.detalleAdicional1 = detalleAdicional1;
   }

   public String getDetalleAdicional2() {
      return this.detalleAdicional2;
   }

   public void setDetalleAdicional2(String detalleAdicional2) {
      this.detalleAdicional2 = detalleAdicional2;
   }

   public String getDetalleAdicional3() {
      return this.detalleAdicional3;
   }

   public void setDetalleAdicional3(String detalleAdicional3) {
      this.detalleAdicional3 = detalleAdicional3;
   }
}
