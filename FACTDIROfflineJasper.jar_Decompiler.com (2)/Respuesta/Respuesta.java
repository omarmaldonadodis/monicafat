package Respuesta;

import java.util.ArrayList;
import java.util.List;

public class Respuesta {
   private String estadoComprobante;
   private List<MensajeGenerado> mensajes = new ArrayList();
   private String comprobante;

   public List<MensajeGenerado> getMensajes() {
      return this.mensajes;
   }

   public void setMensajes(List<MensajeGenerado> mensajes) {
      this.mensajes = mensajes;
   }

   public void addMensaje(MensajeGenerado mensaje) {
      this.mensajes.add(mensaje);
   }

   public String getEstadoComprobante() {
      return this.estadoComprobante;
   }

   public void setEstadoComprobante(String estadoComprobante) {
      this.estadoComprobante = estadoComprobante;
   }

   public String getComprobante() {
      return this.comprobante;
   }

   public void setComprobante(String comprobante) {
      this.comprobante = comprobante;
   }
}
