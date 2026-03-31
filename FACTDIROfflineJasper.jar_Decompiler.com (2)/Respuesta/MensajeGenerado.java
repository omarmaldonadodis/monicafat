package Respuesta;

public class MensajeGenerado {
   private String identificador;
   private String mensaje;
   private String informacionAdicional;
   private String tipo;

   public MensajeGenerado(String identificador, String mensaje, String informacionAdicional, String tipo) {
      this.identificador = identificador;
      this.mensaje = mensaje;
      this.informacionAdicional = informacionAdicional;
      this.tipo = tipo;
   }

   public MensajeGenerado() {
   }

   public String getIdentificador() {
      return this.identificador;
   }

   public void setIdentificador(String identificador) {
      this.identificador = identificador;
   }

   public String getMensaje() {
      return this.mensaje;
   }

   public void setMensaje(String mensaje) {
      this.mensaje = mensaje;
   }

   public String getInformacionAdicional() {
      return this.informacionAdicional;
   }

   public void setInformacionAdicional(String informacionAdicional) {
      this.informacionAdicional = informacionAdicional;
   }

   public String getTipo() {
      return this.tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public String toString() {
      return "Identificador: " + this.identificador + ", Mensaje: " + this.mensaje + ", Información adicional: " + this.informacionAdicional + ". ";
   }
}
