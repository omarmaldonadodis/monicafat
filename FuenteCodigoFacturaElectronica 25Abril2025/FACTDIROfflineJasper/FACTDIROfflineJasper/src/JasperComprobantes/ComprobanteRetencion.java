package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class ComprobanteRetencion extends ComprobanteGeneral {
   private String tipoIdentificacionSujetoRetenido;
   private String razonSocialSujetoRetenido;
   private String identificacionSujetoRetenido;
   private String periodoFiscal;
   private List<ImpuestoComprobanteRetencion> docsSustento = new ArrayList();
   private List<CampoAdicional> infoAdicional = new ArrayList();

   public String getTipoIdentificacionSujetoRetenido() {
      return this.tipoIdentificacionSujetoRetenido;
   }

   public void setTipoIdentificacionSujetoRetenido(String tipoIdentificacionSujetoRetenido) {
      this.tipoIdentificacionSujetoRetenido = tipoIdentificacionSujetoRetenido;
   }

   public String getRazonSocialSujetoRetenido() {
      return this.razonSocialSujetoRetenido;
   }

   public void setRazonSocialSujetoRetenido(String razonSocialSujetoRetenido) {
      this.razonSocialSujetoRetenido = razonSocialSujetoRetenido;
   }

   public String getIdentificacionSujetoRetenido() {
      return this.identificacionSujetoRetenido;
   }

   public void setIdentificacionSujetoRetenido(String identificacionSujetoRetenido) {
      this.identificacionSujetoRetenido = identificacionSujetoRetenido;
   }

   public String getPeriodoFiscal() {
      return this.periodoFiscal;
   }

   public void setPeriodoFiscal(String periodoFiscal) {
      this.periodoFiscal = periodoFiscal;
   }

   public List<ImpuestoComprobanteRetencion> getImpuestos() {
      return this.docsSustento;
   }

   public void setImpuestos(List<ImpuestoComprobanteRetencion> docsSustento) {
      this.docsSustento = docsSustento;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
      this.infoAdicional = infoAdicional;
   }
}
