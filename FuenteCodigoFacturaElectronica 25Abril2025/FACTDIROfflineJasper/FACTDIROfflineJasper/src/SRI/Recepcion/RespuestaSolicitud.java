package SRI.Recepcion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "respuestaSolicitud",
   propOrder = {"estado", "comprobantes"}
)
public class RespuestaSolicitud {
   protected String estado;
   protected RespuestaSolicitud.Comprobantes comprobantes;

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(String value) {
      this.estado = value;
   }

   public RespuestaSolicitud.Comprobantes getComprobantes() {
      return this.comprobantes;
   }

   public void setComprobantes(RespuestaSolicitud.Comprobantes value) {
      this.comprobantes = value;
   }

   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(
      name = "",
      propOrder = {"comprobante"}
   )
   public static class Comprobantes {
      protected List<Comprobante> comprobante;

      public List<Comprobante> getComprobante() {
         if (this.comprobante == null) {
            this.comprobante = new ArrayList();
         }

         return this.comprobante;
      }
   }
}
