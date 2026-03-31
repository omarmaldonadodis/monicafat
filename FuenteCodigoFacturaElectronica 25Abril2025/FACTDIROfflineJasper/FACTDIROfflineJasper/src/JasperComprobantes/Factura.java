package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class Factura extends ComprobanteGeneral {
   private String guiaRemision;
   private String razonSocialComprador;
   private String identificacionComprador;
   private String propina;
   private String importeTotal;
   private String direccionComprador;
   private List<DetalleFactura> detalles = new ArrayList();
   private List<CampoAdicional> infoAdicional = new ArrayList();
   private List<Pago> pagos = new ArrayList();
   private List<ReembolsoFactura> reembolso = new ArrayList();
   private String totalSinSubsidio;
   private String ahorroSubsidio;

   public String getGuiaRemision() {
      return this.guiaRemision;
   }

   public void setGuiaRemision(String guiaRemision) {
      this.guiaRemision = guiaRemision;
   }

   public String getRazonSocialComprador() {
      return this.razonSocialComprador;
   }

   public void setRazonSocialComprador(String razonSocialComprador) {
      this.razonSocialComprador = razonSocialComprador;
   }

   public String getIdentificacionComprador() {
      return this.identificacionComprador;
   }

   public void setIdentificacionComprador(String identificacionComprador) {
      this.identificacionComprador = identificacionComprador;
   }

   public String getPropina() {
      return this.propina;
   }

   public void setPropina(String propina) {
      this.propina = propina;
   }

   public String getImporteTotal() {
      return this.importeTotal;
   }

   public void setImporteTotal(String importeTotal) {
      this.importeTotal = importeTotal;
   }

   public List<DetalleFactura> getDetalles() {
      return this.detalles;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setDetalles(List<DetalleFactura> detalles) {
      this.detalles = detalles;
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

   public List<ReembolsoFactura> getReembolso() {
      return this.reembolso;
   }

   public void setReembolso(List<ReembolsoFactura> reembolso) {
      this.reembolso = reembolso;
   }

   public String getDireccionComprador() {
      return this.direccionComprador;
   }

   public void setDireccionComprador(String direccionComprador) {
      this.direccionComprador = direccionComprador;
   }

   public String getTotalSinSubsidio() {
      return this.totalSinSubsidio;
   }

   public void setTotalSinSubsidio(String totalSinSubsidio) {
      this.totalSinSubsidio = totalSinSubsidio;
   }

   public String getAhorroSubsidio() {
      return this.ahorroSubsidio;
   }

   public void setAhorroSubsidio(String ahorroSubsidio) {
      this.ahorroSubsidio = ahorroSubsidio;
   }
}
