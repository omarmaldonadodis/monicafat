package Facturacion;

import FirmaElectronica.FirmaElectronica;
import Respuesta.MensajeGenerado;
import Respuesta.RespuestaInterna;
import SRI.Autorizacion.Autorizacion;
import SRI.Autorizacion.AutorizacionComprobantesOffline;
import SRI.Autorizacion.AutorizacionComprobantesOfflineService;
import SRI.Autorizacion.RespuestaComprobante;
import SRI.Recepcion.Comprobante;
import SRI.Recepcion.Mensaje;
import SRI.Recepcion.RecepcionComprobantesOffline;
import SRI.Recepcion.RecepcionComprobantesOfflineService;
import SRI.Recepcion.RespuestaSolicitud;
import Util.Util;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

public class Facturacion {
   private final Properties configuracion;
   private final FirmaElectronica firma;
   private Document xmlComprobanteRecibido = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
   private String ambiente;
   private Util util;

   public String getAmbiente() {
      return this.ambiente;
   }

   public void setAmbiente(String ambiente) {
      this.ambiente = ambiente;
   }

   public Facturacion(Properties configuracion) throws ParserConfigurationException {
      this.configuracion = configuracion;
      this.util = new Util();
      this.firma = new FirmaElectronica(configuracion);
   }

   public RespuestaInterna ProcesarComprobante(Document xmlComprobante) {
      this.xmlComprobanteRecibido = xmlComprobante;
      this.EliminarCertificado();
      RespuestaSolicitud respuestaRecepcion = null;

      try {
         this.ambiente = xmlComprobante.getElementsByTagName("ambiente").item(0).getTextContent();
         respuestaRecepcion = this.ValidarComprobante(this.util.convertirXMLToString(xmlComprobante));
      } catch (Exception var7) {
         Util.printExceptionInFile(var7, "pendientesSRI", this.configuracion.getProperty("dirErrores"));
         return this.CrearRespuestaException("EL SERVIDOR DEL SRI NO ESTA DISPONIBLE ESTE MOMENTO REENVIAR COMPROBANTE MAS TARDE");
      }

      if (respuestaRecepcion.getEstado().equals("DEVUELTA") && !respuestaRecepcion.getComprobantes().getComprobante().isEmpty()) {
         try {
            return this.CrearRespuestaRecepcion(respuestaRecepcion, xmlComprobante);
         } catch (Exception var5) {
            Util.printExceptionInFile(var5, "pendientesSRI", this.configuracion.getProperty("dirErrores"));
            return this.CrearRespuestaException("PROBLEMAS CREANDO LA RESPUESTA DE RECEPCION DEL SRI");
         }
      } else if (respuestaRecepcion.getEstado().equals("DEVUELTA")) {
         return this.CrearRespuestaException("RESPUESTA DE RECEPCION CON ARREGLO DE COMPROBANTES VACIOS");
      } else {
         RespuestaComprobante respuestaAutorizacion = null;

         try {
            String claveAcceso = xmlComprobante.getElementsByTagName("claveAcceso").item(0).getTextContent();
            Thread.sleep(1500L);
            respuestaAutorizacion = this.AutorizarComprobante(claveAcceso);
         } catch (Exception var6) {
            Util.printExceptionInFile(var6, "pendientesSRI", this.configuracion.getProperty("dirErrores"));
            return this.CrearRespuestaException("EL SERVIDOR DEL SRI NO ESTA DISPONIBLE ESTE MOMENTO REENVIAR COMPROBANTE MAS TARDE");
         }

         return this.CrearRespuestaAutorizacion(respuestaAutorizacion);
      }
   }

   public RespuestaInterna procesarComprobantesPendientesAutorizacion(String claveAcceso, Document xmlComprobante) {
      this.xmlComprobanteRecibido = xmlComprobante;
      RespuestaComprobante respuestaAutorizacion = null;

      try {
         respuestaAutorizacion = this.AutorizarComprobante(claveAcceso);
      } catch (Exception var5) {
         return this.CrearRespuestaException("EL SERVIDOR DEL SRI NO ESTA DISPONIBLE ESTE MOMENTO REENVIAR COMPROBANTE MAS TARDE");
      }

      return this.CrearRespuestaAutorizacion(respuestaAutorizacion);
   }

   private RespuestaSolicitud ValidarComprobante(String comprobante) {
      RecepcionComprobantesOfflineService service = new RecepcionComprobantesOfflineService(this.ambiente);
      RecepcionComprobantesOffline port = service.getRecepcionComprobantesOfflinePort();
      return port.validarComprobante(comprobante.getBytes(Charset.forName("UTF-8")));
   }

   private RespuestaComprobante AutorizarComprobante(String claveAcceso) {
      AutorizacionComprobantesOfflineService service = new AutorizacionComprobantesOfflineService(this.ambiente);
      AutorizacionComprobantesOffline port = service.getAutorizacionComprobantesOfflinePort();
      return port.autorizacionComprobante(claveAcceso);
   }

   private RespuestaInterna CrearRespuestaRecepcion(RespuestaSolicitud respuestaRecepcion, Document xmlComprobante) {
      RespuestaInterna respuestaInterna = new RespuestaInterna();
      respuestaInterna.setEstadoComprobante(respuestaRecepcion.getEstado());
      Comprobante comprobante = (Comprobante)respuestaRecepcion.getComprobantes().getComprobante().get(0);
      Element factura = xmlComprobante.getDocumentElement();
      Element tagRespuesta = xmlComprobante.createElement("respuestaSolicitud");
      Element tagEstado = xmlComprobante.createElement("estado");
      tagEstado.appendChild(xmlComprobante.createTextNode(respuestaRecepcion.getEstado()));
      tagRespuesta.appendChild(tagEstado);
      Element tagComprobantes = xmlComprobante.createElement("comprobantes");
      Element tagComprobante = xmlComprobante.createElement("comprobante");
      Element tagClaveAcceso = xmlComprobante.createElement("claveAcceso");
      tagClaveAcceso.appendChild(xmlComprobante.createTextNode(comprobante.getClaveAcceso()));
      tagComprobante.appendChild(tagClaveAcceso);
      Element tagMensajes = xmlComprobante.createElement("mensajes");
      List<Mensaje> mensajes = comprobante.getMensajes().getMensaje();
      Iterator it = mensajes.iterator();

      while(it.hasNext()) {
         Mensaje mensaje = (Mensaje)it.next();
         Element tagMensaje = xmlComprobante.createElement("mensaje");
         Element tagIdentificador = xmlComprobante.createElement("identificador");
         tagIdentificador.appendChild(xmlComprobante.createTextNode(mensaje.getIdentificador()));
         tagMensaje.appendChild(tagIdentificador);
         Element tagMensajeDatos = xmlComprobante.createElement("mensaje");
         tagMensajeDatos.appendChild(xmlComprobante.createTextNode(mensaje.getMensaje()));
         tagMensaje.appendChild(tagMensajeDatos);
         Element tagTipo;
         if (mensaje.getInformacionAdicional() != null && !mensaje.getInformacionAdicional().equals("")) {
            tagTipo = xmlComprobante.createElement("informacionAdicional");
            tagTipo.appendChild(xmlComprobante.createTextNode(mensaje.getInformacionAdicional()));
            tagMensaje.appendChild(tagTipo);
         }

         tagTipo = xmlComprobante.createElement("tipo");
         tagTipo.appendChild(xmlComprobante.createTextNode(mensaje.getTipo()));
         tagMensaje.appendChild(tagTipo);
         tagMensajes.appendChild(tagMensaje);
         if (mensaje.getIdentificador().equals("70")) {
            respuestaInterna.setEstadoComprobante("PROCESANDOSE");
            respuestaInterna.addMensaje(new MensajeGenerado("3000", "EL COMPROBANTE SE ENCUENTRA SIENDO PROCESADO POR EL SRI. ENVIAR NUEVAMENTE EN UNOS 2 MIN. RESPUESTA DEL SRI", (String)null, "PROCESANDOSE"));
            respuestaInterna.setComprobante(this.xmlComprobanteRecibido);
         } else {
            respuestaInterna.addMensaje(new MensajeGenerado(mensaje.getIdentificador(), mensaje.getMensaje(), mensaje.getInformacionAdicional(), mensaje.getTipo()));
         }
      }

      tagComprobante.appendChild(tagMensajes);
      tagComprobantes.appendChild(tagComprobante);
      tagRespuesta.appendChild(tagComprobantes);
      factura.appendChild(tagRespuesta);
      respuestaInterna.setComprobante(xmlComprobante);
      return respuestaInterna;
   }

   private RespuestaInterna CrearRespuestaAutorizacion(RespuestaComprobante respuestaAutorizacion) {
      RespuestaInterna respuestaInterna = new RespuestaInterna();

      try {
         List<Autorizacion> autorizaciones = null;
         Autorizacion autorizacion = null;

         try {
            autorizaciones = respuestaAutorizacion.getAutorizaciones().getAutorizacion();
            autorizacion = (Autorizacion)autorizaciones.get(0);
         } catch (Exception var24) {
            respuestaInterna.setEstadoComprobante("PROCESANDOSE");
            respuestaInterna.addMensaje(new MensajeGenerado("3000", "EL COMPROBANTE SE ENCUENTRA SIENDO PROCESADO POR EL SRI O NUNCA HA SIDO ENVIADO AL SRI, DE SER EL CASO COPIAR A LA CARPETA GENERADOS", (String)null, "PROCESANDOSE"));
            respuestaInterna.setComprobante(this.xmlComprobanteRecibido);
            return respuestaInterna;
         }

         if (autorizacion.getEstado().equals("EN PROCESO")) {
            respuestaInterna.setEstadoComprobante("PROCESANDOSE");
            respuestaInterna.addMensaje(new MensajeGenerado("3000", "EL COMPROBANTE SE ENCUENTRA SIENDO PROCESADO POR EL SRI O NUNCA HA SIDO ENVIADO AL SRI, DE SER EL CASO COPIAR A LA CARPETA GENERADOS", (String)null, "PROCESANDOSE"));
            respuestaInterna.setComprobante(this.xmlComprobanteRecibido);
            return respuestaInterna;
         } else {
            if (!autorizacion.getEstado().equals("AUTORIZADO")) {
               int cantidad = autorizaciones.size();

               for(int i = 0; i < cantidad; ++i) {
                  if (((Autorizacion)autorizaciones.get(i)).getEstado().equals("AUTORIZADO")) {
                     autorizacion = (Autorizacion)autorizaciones.get(i);
                     break;
                  }
               }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            GregorianCalendar gc = autorizacion.getFechaAutorizacion().toGregorianCalendar();
            String formatted_string = sdf.format(gc.getTime());
            respuestaInterna.setFechaAutorizacion(formatted_string);
            respuestaInterna.setEstadoComprobante(autorizacion.getEstado());
            respuestaInterna.setDocAutorizado(autorizacion.getComprobante());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document xmlComprobante = implementation.createDocument((String)null, "autorizacion", (DocumentType)null);
            xmlComprobante.setXmlVersion("1.0");
            Element tagAutorizacion = xmlComprobante.getDocumentElement();
            Element tagEstado = xmlComprobante.createElement("estado");
            tagEstado.appendChild(xmlComprobante.createTextNode(autorizacion.getEstado()));
            tagAutorizacion.appendChild(tagEstado);
            respuestaInterna.setEstadoComprobante(autorizacion.getEstado());
            Element tagFechaAutorizacion;
            if (autorizacion.getNumeroAutorizacion() != null && !autorizacion.getNumeroAutorizacion().equals("")) {
               tagFechaAutorizacion = xmlComprobante.createElement("numeroAutorizacion");
               tagFechaAutorizacion.appendChild(xmlComprobante.createTextNode(autorizacion.getNumeroAutorizacion()));
               tagAutorizacion.appendChild(tagFechaAutorizacion);
            }

            tagFechaAutorizacion = xmlComprobante.createElement("fechaAutorizacion");
            tagFechaAutorizacion.appendChild(xmlComprobante.createTextNode(formatted_string));
            tagAutorizacion.appendChild(tagFechaAutorizacion);
            tagFechaAutorizacion.setAttribute("class", "fechaAutorizacion");
            Element tagComprobante = xmlComprobante.createElement("comprobante");
            tagComprobante.appendChild(xmlComprobante.createCDATASection(autorizacion.getComprobante()));
            tagAutorizacion.appendChild(tagComprobante);
            respuestaInterna.setDocAutorizado(autorizacion.getComprobante());
            List<SRI.Autorizacion.Mensaje> mensajes = autorizacion.getMensajes().getMensaje();
            if (mensajes != null) {
               Element tagMensajes = xmlComprobante.createElement("mensajes");
               Iterator it = mensajes.iterator();

               while(it.hasNext()) {
                  SRI.Autorizacion.Mensaje mensaje = (SRI.Autorizacion.Mensaje)it.next();
                  Element tagMensaje = xmlComprobante.createElement("mensaje");
                  Element tagIdentificador = xmlComprobante.createElement("identificador");
                  tagIdentificador.appendChild(xmlComprobante.createTextNode(mensaje.getIdentificador()));
                  tagMensaje.appendChild(tagIdentificador);
                  Element tagMensajeDatos = xmlComprobante.createElement("mensaje");
                  tagMensajeDatos.appendChild(xmlComprobante.createTextNode(mensaje.getMensaje()));
                  tagMensaje.appendChild(tagMensajeDatos);
                  Element tagTipo;
                  if (mensaje.getInformacionAdicional() != null && !mensaje.getInformacionAdicional().equals("")) {
                     tagTipo = xmlComprobante.createElement("informacionAdicional");
                     tagTipo.appendChild(xmlComprobante.createTextNode(mensaje.getInformacionAdicional()));
                     tagMensaje.appendChild(tagTipo);
                  }

                  tagTipo = xmlComprobante.createElement("tipo");
                  tagTipo.appendChild(xmlComprobante.createTextNode(mensaje.getTipo()));
                  tagMensaje.appendChild(tagTipo);
                  tagMensajes.appendChild(tagMensaje);
                  respuestaInterna.addMensaje(new MensajeGenerado(mensaje.getIdentificador(), mensaje.getMensaje(), mensaje.getInformacionAdicional(), mensaje.getTipo()));
               }

               tagAutorizacion.appendChild(tagMensajes);
            }

            respuestaInterna.setComprobante(xmlComprobante);
            return respuestaInterna;
         }
      } catch (Exception var25) {
         return this.CrearRespuestaException("ERROR CREANDO EL XML DE RESPUESTA DE AUTORIZACION");
      }
   }

   private RespuestaInterna CrearRespuestaException(String mensaje) {
      RespuestaInterna respuestaInterna = new RespuestaInterna();
      respuestaInterna.setEstadoComprobante("ERROR");
      respuestaInterna.addMensaje(new MensajeGenerado("1000", mensaje, (String)null, Facturacion.TipoError.EXCEPCION.toString()));
      respuestaInterna.setComprobante(this.xmlComprobanteRecibido);
      return respuestaInterna;
   }

   private void EliminarCertificado() {
      TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }

         public void checkClientTrusted(X509Certificate[] certs, String authType) {
         }

         public void checkServerTrusted(X509Certificate[] certs, String authType) {
         }
      }};
      HostnameVerifier hv = new HostnameVerifier() {
         public boolean verify(String urlHostName, SSLSession session) {
            return true;
         }
      };

      try {
         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         HttpsURLConnection.setDefaultHostnameVerifier(hv);
      } catch (Exception var4) {
      }

   }

   static enum TipoError {
      EXCEPCION,
      AUTORIZACION;
   }
}
