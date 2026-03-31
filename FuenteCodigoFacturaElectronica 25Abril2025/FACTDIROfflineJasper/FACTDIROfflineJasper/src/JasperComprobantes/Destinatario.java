package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class Destinatario {
   private String identificacionDestinatario;
   private String razonSocialDestinatario;
   private String dirDestinatario;
   private String motivoTraslado;
   private String docAduaneroUnico;
   private String codEstabDestino;
   private String ruta;
   private String docSustento;
   private String numDocSustento;
   private String numAutDocSustento;
   private String fechaEmisionDocSustento;
   private List<DetalleGuiaRemision> detalles = new ArrayList();

   public String getIdentificacionDestinatario() {
      return this.identificacionDestinatario;
   }

   public void setIdentificacionDestinatario(String identificacionDestinatario) {
      this.identificacionDestinatario = identificacionDestinatario;
   }

   public String getRazonSocialDestinatario() {
      return this.razonSocialDestinatario;
   }

   public void setRazonSocialDestinatario(String razonSocialDestinatario) {
      this.razonSocialDestinatario = razonSocialDestinatario;
   }

   public String getDirDestinatario() {
      return this.dirDestinatario;
   }

   public void setDirDestinatario(String dirDestinatario) {
      this.dirDestinatario = dirDestinatario;
   }

   public String getMotivoTraslado() {
      return this.motivoTraslado;
   }

   public void setMotivoTraslado(String motivoTraslado) {
      this.motivoTraslado = motivoTraslado;
   }

   public String getDocAduaneroUnico() {
      return this.docAduaneroUnico;
   }

   public void setDocAduaneroUnico(String docAduaneroUnico) {
      this.docAduaneroUnico = docAduaneroUnico;
   }

   public String getCodEstabDestino() {
      return this.codEstabDestino;
   }

   public void setCodEstabDestino(String codEstabDestino) {
      this.codEstabDestino = codEstabDestino;
   }

   public String getRuta() {
      return this.ruta;
   }

   public void setRuta(String ruta) {
      this.ruta = ruta;
   }

   public String getDocSustento() {
      return this.docSustento;
   }

   public void setDocSustento(String docSustento) {
      this.docSustento = docSustento;
   }

   public String getNumDocSustento() {
      return this.numDocSustento;
   }

   public void setNumDocSustento(String numDocSustento) {
      this.numDocSustento = numDocSustento;
   }

   public String getNumAutDocSustento() {
      return this.numAutDocSustento;
   }

   public void setNumAutDocSustento(String numAutDocSustento) {
      this.numAutDocSustento = numAutDocSustento;
   }

   public String getFechaEmisionDocSustento() {
      return this.fechaEmisionDocSustento;
   }

   public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
      this.fechaEmisionDocSustento = fechaEmisionDocSustento;
   }

   public List<DetalleGuiaRemision> getDetalles() {
      return this.detalles;
   }

   public void setDetalles(List<DetalleGuiaRemision> detalles) {
      this.detalles = detalles;
   }
}
