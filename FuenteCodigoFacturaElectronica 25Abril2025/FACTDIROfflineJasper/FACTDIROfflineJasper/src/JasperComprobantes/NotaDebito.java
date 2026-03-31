package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class NotaDebito extends ComprobanteGeneral {
   private String tipoIdentificacionComprador;
   private String razonSocialComprador;
   private String identificacionComprador;
   private String rise;
   private String comprobanteModificado;
   private String numDocModificado;
   private String fechaEmisionDocSustento;
   private String valorTotal;
   private List<Pago> pagos = new ArrayList();
   private List<Motivo> motivos = new ArrayList();
   private List<CampoAdicional> infoAdicional = new ArrayList();

   public String getTipoIdentificacionComprador() {
      return this.tipoIdentificacionComprador;
   }

   public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
      this.tipoIdentificacionComprador = tipoIdentificacionComprador;
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

   public String getRise() {
      return this.rise;
   }

   public void setRise(String rise) {
      this.rise = rise;
   }

   public String getComprobanteModificado() {
      return this.comprobanteModificado;
   }

   public void setComprobanteModificado(String comprobanteModificado) {
      this.comprobanteModificado = comprobanteModificado;
   }

   public String getNumDocModificado() {
      return this.numDocModificado;
   }

   public void setNumDocModificado(String numDocModificado) {
      this.numDocModificado = numDocModificado;
   }

   public String getFechaEmisionDocSustento() {
      return this.fechaEmisionDocSustento;
   }

   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
      this.fechaEmisionDocSustento = fechaEmisionDocSustento;
   }

   public List<Motivo> getMotivos() {
      return this.motivos;
   }

   public void setMotivos(List<Motivo> motivos) {
      this.motivos = motivos;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
      this.infoAdicional = infoAdicional;
   }

   public String getValorTotal() {
      return this.valorTotal;
   }

   public void setValorTotal(String valorTotal) {
      this.valorTotal = valorTotal;
   }

   public List<Pago> getPagos() {
      return this.pagos;
   }

   public void setPagos(List<Pago> pagos) {
      this.pagos = pagos;
   }
}
