package JasperComprobantes;

import java.util.ArrayList;
import java.util.List;

public class GuiaRemision extends ComprobanteGeneral {
   private String dirPartida;
   private String razonSocialTransportista;
   private String rucTransportista;
   private String fechaIniTransporte;
   private String fechaFinTransporte;
   private String placa;
   private List<Destinatario> destinatarios = new ArrayList();
   private List<CampoAdicional> infoAdicional = new ArrayList();

   public String getDirPartida() {
      return this.dirPartida;
   }

   public void setDirPartida(String dirPartida) {
      this.dirPartida = dirPartida;
   }

   public String getRazonSocialTransportista() {
      return this.razonSocialTransportista;
   }

   public void setRazonSocialTransportista(String razonSocialTransportista) {
      this.razonSocialTransportista = razonSocialTransportista;
   }

   public String getRucTransportista() {
      return this.rucTransportista;
   }

   public void setRucTransportista(String rucTransportista) {
      this.rucTransportista = rucTransportista;
   }

   public String getFechaIniTransporte() {
      return this.fechaIniTransporte;
   }

   public void setFechaIniTransporte(String fechaIniTransporte) {
      this.fechaIniTransporte = fechaIniTransporte;
   }

   public String getFechaFinTransporte() {
      return this.fechaFinTransporte;
   }

   public void setFechaFinTransporte(String fechaFinTransporte) {
      this.fechaFinTransporte = fechaFinTransporte;
   }

   public String getPlaca() {
      return this.placa;
   }

   public void setPlaca(String placa) {
      this.placa = placa;
   }

   public List<CampoAdicional> getInfoAdicional() {
      return this.infoAdicional;
   }

   public void setInfoAdicional(List<CampoAdicional> infoAdicional) {
      this.infoAdicional = infoAdicional;
   }

   public List<Destinatario> getDestinatarios() {
      return this.destinatarios;
   }

   public void setDestinatarios(List<Destinatario> destinatarios) {
      this.destinatarios = destinatarios;
   }
}
