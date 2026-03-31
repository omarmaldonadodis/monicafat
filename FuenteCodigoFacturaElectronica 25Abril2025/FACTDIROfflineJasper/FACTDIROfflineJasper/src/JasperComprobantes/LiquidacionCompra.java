package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class LiquidacionCompra extends ComprobanteGeneral {
   private String razonSocialProveedor;
   private String identificacionProveedor;
   private String importeTotal;
   private String direccionProveedor;
   private List<DetalleLiquidacionCompra> detalles = new ArrayList();
   private List<CampoAdicional> infoAdicional = new ArrayList();
   private List<Pago> pagos = new ArrayList();

   public String getRazonSocialProveedor() {
      return this.razonSocialProveedor;
   }

   public void setRazonSocialProveedor(String razonSocialProveedor) {
      this.razonSocialProveedor = razonSocialProveedor;
   }

   public String getIdentificacionProveedor() {
      return this.identificacionProveedor;
   }

   public void setIdentificacionProveedor(String identificacionProveedor) {
      this.identificacionProveedor = identificacionProveedor;
   }

   public String getImporteTotal() {
      return this.importeTotal;
   }

   public void setImporteTotal(String importeTotal) {
      this.importeTotal = importeTotal;
   }

   public List<DetalleLiquidacionCompra> getDetalles() {
      return this.detalles;
   }

   public void setDetalles(List<DetalleLiquidacionCompra> detalles) {
      this.detalles = detalles;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
      this.infoAdicional = infoAdicional;
   }

   public List<Pago> getPagos() {
      return this.pagos;
   }

   public void setPagos(List<Pago> pagos) {
      this.pagos = pagos;
   }

   public String getDireccionProveedor() {
      return this.direccionProveedor;
   }

   public void setDireccionProveedor(String direccionProveedor) {
      this.direccionProveedor = direccionProveedor;
   }
}
