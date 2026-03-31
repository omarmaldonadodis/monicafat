package Respuesta;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;

public class RespuestaInterna {
   private String estadoComprobante;
   private List<MensajeGenerado> mensajes = new ArrayList();
   private Document comprobante;
   private String docAutorizado;
   private String fechaAutorizacion;

   public String getEstadoComprobante() {
      return this.estadoComprobante;
   }

   public void setEstadoComprobante(String estadoComprobante) {
      this.estadoComprobante = estadoComprobante;
   }

   public List<MensajeGenerado> getMensajes() {
      return this.mensajes;
   }

   public void addMensaje(MensajeGenerado mensaje) {
      this.getMensajes().add(mensaje);
   }

   public void setMensajes(List<MensajeGenerado> mensajes) {
      this.mensajes = mensajes;
   }

   public Document getComprobante() {
      return this.comprobante;
   }

   public void setComprobante(Document comprobante) {
      this.comprobante = comprobante;
   }

   public String getDocAutorizado() {
      return this.docAutorizado;
   }

   public void setDocAutorizado(String docAutorizado) {
      this.docAutorizado = docAutorizado;
   }

   public String getFechaAutorizacion() {
      return this.fechaAutorizacion;
   }

   public void setFechaAutorizacion(String fechaAutorizacion) {
      this.fechaAutorizacion = fechaAutorizacion;
   }
}
