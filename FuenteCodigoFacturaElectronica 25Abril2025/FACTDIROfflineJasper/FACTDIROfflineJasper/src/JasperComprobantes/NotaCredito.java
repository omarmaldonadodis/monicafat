package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class NotaCredito extends ComprobanteGeneral {
   private String tipoIdentificacionComprador;
   private String razonSocialComprador;
   private String identificacionComprador;
   private String rise;
   private String comprobanteModificado;
   private String numDocModificado;
   private String fechaEmisionDocSustento;
   private String valorModificacion;
   private String motivo;
   private List<DetalleNotaCredito> detalles = new ArrayList();
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

   public String getValorModificacion() {
      return this.valorModificacion;
   }

   public void setValorModificacion(String valorModificacion) {
      this.valorModificacion = valorModificacion;
   }

   public String getMotivo() {
      return this.motivo;
   }

   public void setMotivo(String motivo) {
      this.motivo = motivo;
   }

   public List<DetalleNotaCredito> getDetalles() {
      return this.detalles;
   }

   public void setDetalles(List<DetalleNotaCredito> detalles) {
      this.detalles = detalles;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
      this.infoAdicional = infoAdicional;
   }
}
