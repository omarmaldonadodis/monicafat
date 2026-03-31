package recepcion.ws.sri.gob.ec;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "comprobante",
   propOrder = {"claveAcceso", "mensajes"}
)
public class Comprobante {
   protected String claveAcceso;
   protected Comprobante.Mensajes mensajes;

   public String getClaveAcceso() {
      return this.claveAcceso;
   }

   public void setClaveAcceso(String value) {
      this.claveAcceso = value;
   }

   public Comprobante.Mensajes getMensajes() {
      return this.mensajes;
   }

   public void setMensajes(Comprobante.Mensajes value) {
      this.mensajes = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"mensaje"}
   )
   public static class Mensajes {
      @XmlElement(
         namespace = "http://ec.gob.sri.ws.recepcion"
      )
      protected List<Mensaje> mensaje;

      public List<Mensaje> getMensaje() {
         if (this.mensaje == null) {
            this.mensaje = new ArrayList();
         }

         return this.mensaje;
      }
   }
}
