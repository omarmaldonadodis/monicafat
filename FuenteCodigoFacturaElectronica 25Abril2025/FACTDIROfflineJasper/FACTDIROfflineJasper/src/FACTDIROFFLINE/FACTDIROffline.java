package FACTDIROFFLINE;

import Email.JCMail;
import Facturacion.Facturacion;
import FirmaElectronica.FirmaElectronica;
import JasperRide.JasperRide;
import Respuesta.MensajeGenerado;
import Respuesta.RespuestaInterna;
import Util.Util;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FACTDIROffline {
   private static Properties configuracion;
   private static Facturacion facturacion;
   private static JCMail jcmail;
   private static String tipoComprobante;
   private static String razonSocial;
   private static String razonSocialEmisor;
   private static String identificacionCliente;
   private static String emailCliente;
   private static String fechaEmision;
   private static Principal principal;
   private static FirmaElectronica firma;

   public static void main(String[] args) {
      try {
         Inicio inicio = new Inicio();
         inicio.setLocationRelativeTo((Component)null);
         inicio.setVisible(true);
         Animacion animacion = new Animacion();
         animacion.fade(inicio, true);
         Thread.sleep(4000L);
         animacion.fade(inicio, false);
         Thread.sleep(3000L);
         inicio.setVisible(false);
         jcmail = new JCMail();
         configuracion = new Properties();
         configuracion.load(FACTDIROffline.class.getResourceAsStream("/Util/configuracion.properties"));
         firma = new FirmaElectronica(configuracion);
         facturacion = new Facturacion(configuracion);
         Path dir = Paths.get(configuracion.getProperty("dirGenerado"));
         principal = new Principal(dir);
         principal.setLocationRelativeTo((Component)null);
         principal.setVisible(true);
         WatchService watcher = FileSystems.getDefault().newWatchService();
         principal.ActualizarText("MONICA Ecuador");
         principal.ActualizarText("Facturacion Electronica");
         principal.ActualizarText("www.monicaecuador.com");
         principal.ActualizarText("Telf : 0987362687 / 0958703111 /022263306 ");
         principal.ActualizarText("Av. 6 de Diciembre N30-105 y Republica ");
         principal.ActualizarText("Telf: 0987362687 ");
         ProcesarComprobantesPendientes(dir);
         ProcesarComprobantesPendientesSRI();
         ProcesarComprobantesPendientesAutorizacion();
         dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

         boolean valid;
         do {
            WatchKey key;
            try {
               key = watcher.take();
            } catch (InterruptedException var15) {
               return;
            }

            Iterator var6 = key.pollEvents().iterator();

            while(var6.hasNext()) {
               WatchEvent<?> event = (WatchEvent)var6.next();
               Kind<?> kind = event.kind();
               Path fileName = (Path)event.context();
               Path fullPath = dir.resolve(fileName);
               String extension = "";
               int i = fileName.toString().lastIndexOf(46);
               if (i > 0) {
                  extension = fileName.toString().substring(i + 1);
               }

               if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                  Thread.sleep(3500L);
                  File file = new File(fullPath.toString());
                  if (!extension.equals("") && extension.toLowerCase().equals("xml") && file.exists()) {
                     ProcesarComprobante(fullPath, fileName);
                     ProcesarComprobantesPendientes(dir);
                     ProcesarComprobantesPendientesSRI();
                     ProcesarComprobantesPendientesAutorizacion();
                  }
               }
            }

            valid = key.reset();
         } while(valid);
      } catch (Exception var16) {
         principal.ActualizarText(var16.getMessage());
      }

   }

   public static void ProcesarComprobantesPendientesAutorizacion() {
      try {
         File archivosSinProcesar = new File(configuracion.getProperty("dirProcesando"));
         File[] archivos = archivosSinProcesar.listFiles();
         if (archivos != null) {
            for(int x = 0; x < archivos.length; ++x) {
               Path fullPath = Paths.get(archivos[x].getAbsolutePath());
               Path fileName = Paths.get(archivos[x].getName());
               new File(fullPath.toString());
               DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               Document xmlComprobante = dBuilder.parse(new InputSource(fullPath.toString()));
               principal.ActualizarText("Procesando comprobantes con clave de acceso en proceso");
               String claveAcceso = xmlComprobante.getElementsByTagName("claveAcceso").item(0).getTextContent();
               Thread.sleep(1500L);
               principal.ActualizarText("Procesando Comprobante " + fileName + "..");
               RespuestaInterna respuestaInterna = facturacion.procesarComprobantesPendientesAutorizacion(claveAcceso, xmlComprobante);
               principal.ActualizarText("Comprobante " + fileName + " procesado. Estado: " + respuestaInterna.getEstadoComprobante());
               if (!respuestaInterna.getEstadoComprobante().equals("AUTORIZADO") && !respuestaInterna.getMensajes().isEmpty()) {
                  principal.ActualizarText("Mensaje " + ((MensajeGenerado)respuestaInterna.getMensajes().get(0)).getMensaje() + ". " + ((MensajeGenerado)respuestaInterna.getMensajes().get(0)).getInformacionAdicional());
               }

               String ruc = xmlComprobante.getElementsByTagName("ruc").item(0).getTextContent();
               ProcesarRespuesta(fullPath, fileName, respuestaInterna, ruc, true);
            }
         }
      } catch (Exception var12) {
         Util.printExceptionInFile(var12, "pendientesAutorizacion", configuracion.getProperty("dirErrores"));
         principal.ActualizarText("Error procesando los comprobantes con clave de acceso en proceso");
      }

   }

   public static void ProcesarComprobantesPendientesSRI() {
      try {
         File archivosSinProcesar = new File(configuracion.getProperty("dirPendienteSRI"));
         File[] archivos = archivosSinProcesar.listFiles();
         if (archivos != null) {
            for(int x = 0; x < archivos.length; ++x) {
               Path fullPath = Paths.get(archivos[x].getAbsolutePath());
               Path fileName = Paths.get(archivos[x].getName());
               new File(fullPath.toString());
               Util.validarXML(fullPath.toString());
               DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
               DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
               Document xmlComprobante = dBuilder.parse(new InputSource(fullPath.toString()));
               principal.ActualizarText("Procesando comprobantes pendiente de Autorizacion SRI");
               principal.ActualizarText("Procesando comprobante " + fileName + "...");
               Thread.sleep(1500L);
               RespuestaInterna respuestaInterna = facturacion.ProcesarComprobante(xmlComprobante);
               principal.ActualizarText("Comprobante " + fileName + " procesado. Estado: " + respuestaInterna.getEstadoComprobante());
               String ruc = xmlComprobante.getElementsByTagName("ruc").item(0).getTextContent();
               ProcesarRespuesta(fullPath, fileName, respuestaInterna, ruc, false);
            }
         }
      } catch (Exception var11) {
         Util.printExceptionInFile(var11, "pendientesSRI", configuracion.getProperty("dirErrores"));
         principal.ActualizarText("Error procesando los comprobantes pendiente de Autorizacion SRI" + var11.getMessage());
      }

   }

   public static void ProcesarComprobantesPendientes(Path dirGenerados) throws ParserConfigurationException, SAXException, IOException, TransformerException, TransformerConfigurationException, InterruptedException {
      File archivosSinProcesar = new File(dirGenerados.toString());
      File[] archivos = archivosSinProcesar.listFiles();
      if (archivos != null) {
         for(int x = 0; x < archivos.length; ++x) {
            Path fullPath = Paths.get(archivos[x].getAbsolutePath());
            Path fileName = Paths.get(archivos[x].getName());
            ProcesarComprobante(fullPath, fileName);
         }
      }

      principal.ActualizarText("Todos los comprobantes procesados. Esperando nuevo(s) comprobante(s)...");
      principal.ActualizarText("");
   }

   private static void ProcesarComprobante(Path fullPath, Path fileName) {
      File file = new File(fullPath.toString());
      DocumentBuilder dBuilder = null;

      try {
         if (file.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            Document xmlComprobante = dBuilder.parse(new InputSource(fullPath.toString()));
            principal.ActualizarText("Procesando comprobante: " + fileName + " ...");
            Thread.sleep(1500L);
            RespuestaInterna respuestaInterna = firma.FirmarComprobante(xmlComprobante);
            principal.ActualizarText("Comprobante " + fileName + " procesado. Estado: " + respuestaInterna.getEstadoComprobante());
            String ruc = xmlComprobante.getElementsByTagName("ruc").item(0).getTextContent();
            ProcesarRespuesta(fullPath, fileName, respuestaInterna, ruc, false);
         }
      } catch (Exception var8) {
         Util.printExceptionInFile(var8, "procesarComprobante", configuracion.getProperty("dirErrores"));
         principal.ActualizarText("Error procesando comprobante: " + fileName);
      }

   }

   private static void ProcesarRespuesta(Path fullPath, Path fileName, RespuestaInterna respuestaInterna, String ruc, boolean envioEmail) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException, IOException {
      principal.ActualizarDocNoAutorizados(obtenerCantidadArchivos(configuracion.getProperty("dirNoAutorizado" + ruc)));

      try {
         Util util = new Util();
         Document xmlComprobante = respuestaInterna.getComprobante();
         Document xmlOriginal = respuestaInterna.getDocAutorizado() != null ? util.convertirStringToXML(respuestaInterna.getDocAutorizado()) : xmlComprobante;
         String dirGuardarDoc = "";
         File file = new File(fullPath.toString());
         DocumentBuilderFactory factory;
         DocumentBuilder builder;
         DOMImplementation implementation;
         if (xmlComprobante != null && (respuestaInterna.getEstadoComprobante().equals("DEVUELTA") || respuestaInterna.getEstadoComprobante().equals("NO AUTORIZADO"))) {
            dirGuardarDoc = Paths.get(configuracion.getProperty("dirNoAutorizado" + ruc)).resolve(fileName).toString();
         } else if (xmlComprobante != null && respuestaInterna.getEstadoComprobante().equals("FIRMADO")) {
            dirGuardarDoc = Paths.get(configuracion.getProperty("dirPendienteSRI")).resolve(fileName).toString();
         } else if (xmlComprobante != null && respuestaInterna.getEstadoComprobante().equals("AUTORIZADO")) {
            dirGuardarDoc = Paths.get(configuracion.getProperty("dirAutorizados" + ruc)).resolve(fileName).toString();
         } else if (respuestaInterna.getEstadoComprobante().equals("PROCESANDOSE")) {
            dirGuardarDoc = Paths.get(configuracion.getProperty("dirProcesando")).resolve(fileName).toString();
            file.delete();
         } else {
            dirGuardarDoc = Paths.get(configuracion.getProperty("dirNoAutorizado" + ruc)).resolve(fileName).toString();
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            implementation = builder.getDOMImplementation();
            xmlComprobante = implementation.createDocument((String)null, "informacion", (DocumentType)null);
            xmlComprobante.setXmlVersion("1.0");
            Element tagRoot = xmlComprobante.getDocumentElement();
            Element tagError = xmlComprobante.createElement("error");
            Iterator it = respuestaInterna.getMensajes().iterator();

            while(it.hasNext()) {
               MensajeGenerado mensajeGenerado = (MensajeGenerado)it.next();
               Element tagIdentificador = xmlComprobante.createElement("identificador");
               tagIdentificador.setTextContent(mensajeGenerado.getIdentificador());
               Element tagMensaje = xmlComprobante.createElement("mensaje");
               tagMensaje.setTextContent(mensajeGenerado.getMensaje());
               Element tagInformacionAdicional = xmlComprobante.createElement("informacionAdicional");
               tagInformacionAdicional.setTextContent(mensajeGenerado.getInformacionAdicional());
               tagError.appendChild(tagIdentificador);
               tagError.appendChild(tagMensaje);
               tagError.appendChild(tagInformacionAdicional);
            }

            tagRoot.appendChild(tagError);
         }

         factory = null;
         builder = null;
         implementation = null;
         boolean claveRestringida = respuestaInterna.getMensajes().stream().anyMatch((value) -> {
            return value.getMensaje().equalsIgnoreCase("CLAVE ACCESO REGISTRADA") || value.getIdentificador().equalsIgnoreCase("43");
         });
         DOMSource source;
         StreamResult result;
         Transformer transformer;
         if (xmlOriginal != null && !respuestaInterna.getEstadoComprobante().equals("AUTORIZADO")) {
            source = new DOMSource(xmlComprobante);
            result = new StreamResult(new File(dirGuardarDoc));
            transformer = TransformerFactory.newInstance().newTransformer();
            if (!respuestaInterna.getEstadoComprobante().equals("FIRMADO")) {
               transformer.setOutputProperty("indent", "yes");
               transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            }

            if (!claveRestringida) {
               transformer.transform(source, result);
               principal.ActualizarText("Comprobante: " + fileName + " guardado en: " + dirGuardarDoc);
            }
         }

         if (respuestaInterna.getEstadoComprobante() != null && !respuestaInterna.getEstadoComprobante().equals("ERROR") && !respuestaInterna.getEstadoComprobante().equals("PROCESANDOSE")) {
            file.delete();
         }

         String numAutorizacion = "";
         String mensajeCorreo = crearMensajeCorreo(xmlOriginal);
         if (respuestaInterna.getEstadoComprobante().equals("AUTORIZADO")) {
            String directorioRaiz = Paths.get(configuracion.getProperty("dirAutorizados" + ruc)).resolve(crearNombreCarpeta(xmlOriginal)).toString();
            File directorio = new File(directorioRaiz);
            if (!directorio.exists()) {
               directorio.mkdir();
            }

            String dirDocAutorizado = Paths.get(directorioRaiz).resolve(fileName).toString();
            source = new DOMSource(xmlOriginal);
            result = new StreamResult(new File(dirDocAutorizado));
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, result);

            try {
               JasperRide jasperRide = new JasperRide();
               numAutorizacion = xmlOriginal.getElementsByTagName("claveAcceso").item(0).getTextContent();
               jasperRide.CrearRide(xmlOriginal, numAutorizacion, dirDocAutorizado, configuracion, ruc);
            } catch (Exception var24) {
               Util.printExceptionInFile(var24, "generacionPDF", configuracion.getProperty("dirErrores"));
               principal.ActualizarText("Error generando el pdf. Error: " + var24.getMessage());
            }

            String dirArchivoNoAutorizado = Paths.get(configuracion.getProperty("dirNoAutorizado" + ruc)).resolve(fileName).toString();
            File enNoAutorizado = new File(dirArchivoNoAutorizado);
            if (enNoAutorizado.exists()) {
               enNoAutorizado.delete();
            }

            String destinatarios = obtenerDestinatarios(xmlOriginal);
            boolean enviarEmail = false;
            if (configuracion.getProperty("correoSiAutorizado" + ruc).equals("si") && respuestaInterna.getEstadoComprobante().equals("AUTORIZADO")) {
               enviarEmail = true;
            } else if (configuracion.getProperty("correoSiAutorizado" + ruc).equals("no") && respuestaInterna.getEstadoComprobante().equals("FIRMADO")) {
               enviarEmail = true;
            }

            try {
               if (!destinatarios.equals("") && (enviarEmail || envioEmail)) {
                  jcmail.setHost(configuracion.getProperty("servidorCorreo" + ruc));
                  jcmail.setPort(configuracion.getProperty("puertoCorreo" + ruc));
                  jcmail.setFrom(configuracion.getProperty("correoRemitente" + ruc));
                  jcmail.setPassword(configuracion.getProperty("correoPass" + ruc).toCharArray());
                  jcmail.setSubject(configuracion.getProperty("correoAsunto" + ruc));
                  jcmail.setMessage(mensajeCorreo);
                  jcmail.setTo(destinatarios);
                  principal.ActualizarText("Enviando Correo al cliente");
                  principal.ActualizarText(jcmail.SEND(fileName.toString(), dirDocAutorizado));
               }
            } catch (Exception var25) {
               principal.ActualizarText("Error enviando el correo" + var25.getMessage());
            }
         }

         principal.ActualizarText(obtenerMensajeEstadoComprobante(respuestaInterna));
         principal.ActualizarText("Procesamiento de " + fileName + " completado.");
      } catch (Exception var26) {
         Util.printExceptionInFile(var26, "respuesta", configuracion.getProperty("dirErrores"));
         principal.ActualizarText("Error procesando la respuesta del comprobante: " + fileName);
         principal.ActualizarText("Error: " + var26.getMessage());
      }

      principal.ActualizarDocNoAutorizados(obtenerCantidadArchivos(configuracion.getProperty("dirNoAutorizado" + ruc)));
      principal.ActualizarText("");
   }

   private static String obtenerDestinatarios(Document xmlComprobante) {
      emailCliente = "";
      NodeList campoAdicional = xmlComprobante.getElementsByTagName("campoAdicional");
      int cantidad = campoAdicional.getLength();
      String destinatarios = "";

      for(int i = 0; i < cantidad; ++i) {
         Node node = campoAdicional.item(i);
         String nombre = node.getAttributes().item(0).getNodeValue();
         if (nombre.toLowerCase().equals("email")) {
            if (node.getTextContent() != null && !node.getTextContent().isEmpty()) {
               if (!destinatarios.equals("")) {
                  destinatarios = destinatarios + ",";
               }

               destinatarios = destinatarios + node.getTextContent();
               emailCliente = node.getTextContent();
            }
         } else if (nombre.equals("Email2")) {
            if (node.getTextContent() != null && !node.getTextContent().isEmpty()) {
               if (!destinatarios.equals("")) {
                  destinatarios = destinatarios + ",";
               }

               destinatarios = destinatarios + node.getTextContent();
            }
         } else if (nombre.equals("Email3") && node.getTextContent() != null && !node.getTextContent().isEmpty()) {
            if (!destinatarios.equals("")) {
               destinatarios = destinatarios + ",";
            }

            destinatarios = destinatarios + node.getTextContent();
         }
      }

      return destinatarios;
   }

   private static String crearMensajeCorreo(Document xmlComprobante) {
      String codDoc = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      tipoComprobante = "";
      String dirigido = "";
      String datos = "";
      String campoIdentificacion = "";
      String campoFecha = "fechaEmision";
      String importeTotal;
      if (codDoc.equals("01")) {
         campoIdentificacion = "identificacionComprador";
         tipoComprobante = "FACTURA";
         dirigido = "razonSocialComprador";
         importeTotal = xmlComprobante.getElementsByTagName("importeTotal").item(0).getTextContent();
         datos = "<strong>Valor Total: </strong>" + importeTotal + "<br /><br />";
      } else if (codDoc.equals("03")) {
         campoIdentificacion = "identificacionProveedor";
         tipoComprobante = "LIQUIDACIÓN DE COMPRA DE BIENES Y PRESTACIÓN DE SERVICIOS";
         dirigido = "razonSocialProveedor";
         importeTotal = xmlComprobante.getElementsByTagName("importeTotal").item(0).getTextContent();
         datos = "<strong>Valor Total: </strong>" + importeTotal + "<br /><br />";
      } else if (codDoc.equals("04")) {
         campoIdentificacion = "identificacionComprador";
         tipoComprobante = "NOTA DE CRÉDITO";
         dirigido = "razonSocialComprador";
      } else if (codDoc.equals("05")) {
         campoIdentificacion = "identificacionComprador";
         tipoComprobante = "NOTA DE DÉBITO";
         dirigido = "razonSocialComprador";
      } else if (codDoc.equals("06")) {
         campoIdentificacion = "rucTransportista";
         tipoComprobante = "GUÍA DE REMISIÓN";
         dirigido = "razonSocialDestinatario";
         campoFecha = "fechaIniTransporte";
      } else if (codDoc.equals("07")) {
         campoIdentificacion = "identificacionSujetoRetenido";
         tipoComprobante = "COMPROBANTE DE RETENCIÓN";
         dirigido = "razonSocialSujetoRetenido";
      }

      razonSocial = xmlComprobante.getElementsByTagName(dirigido).item(0).getTextContent();
      razonSocialEmisor = xmlComprobante.getElementsByTagName("razonSocial").item(0).getTextContent();
      identificacionCliente = xmlComprobante.getElementsByTagName(campoIdentificacion).item(0).getTextContent();
      fechaEmision = xmlComprobante.getElementsByTagName(campoFecha).item(0).getTextContent();
      importeTotal = "Estimado(a),<br /><br /><strong>" + razonSocial + "</strong>";
      importeTotal = importeTotal + "<br /><br />Esta es una notificación automática de un documento tributario electrónico emitido por <strong>" + razonSocialEmisor + "</strong><br /><br /> ";
      String establecimiento = xmlComprobante.getElementsByTagName("estab").item(0).getTextContent();
      String ptoEmision = xmlComprobante.getElementsByTagName("ptoEmi").item(0).getTextContent();
      String secuencial = xmlComprobante.getElementsByTagName("secuencial").item(0).getTextContent();
      importeTotal = importeTotal + "<strong>Tipo de Comprobante: </strong>" + tipoComprobante + "<br /><br />";
      importeTotal = importeTotal + "<strong>Nro de Comprobante: </strong>" + establecimiento + "-" + ptoEmision + "-" + secuencial + "<br /><br />";
      importeTotal = importeTotal + datos;
      importeTotal = importeTotal + "Los detalles generales del comprobante pueden ser consultados en el archivo pdf adjunto en este correo.<br /><br /><strong>Atentamente,</strong><br /><br />         <strong>" + razonSocialEmisor + "</strong><br /><br /><strong>Este documento fue generado con el respaldo del programa MONICA Ecuador </strong><br /><br /><strong>www.monicaecuador.com </strong><br /><br /><strong>Whastapp/Telf. :0987362687/0958703111 </strong><br /><br /><strong>MONICA Version 11</strong><br /><br />";
      return importeTotal;
   }

   private static String crearNombreCarpeta(Document xmlComprobante) {
      String nombre = "";
      String codDoc = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      if (codDoc.equals("01")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      } else if (codDoc.equals("04")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      } else if (codDoc.equals("05")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      } else if (codDoc.equals("06")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      } else if (codDoc.equals("07")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      } else if (codDoc.equals("03")) {
         nombre = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      }

      return nombre;
   }

   private static String obtenerMensajeEstadoComprobante(RespuestaInterna respuestaInterna) {
      if (respuestaInterna.getMensajes() != null && !respuestaInterna.getMensajes().isEmpty()) {
         StringBuilder buffer = new StringBuilder("MENSAJE DE ERROR SRI:");
         Iterator var2 = respuestaInterna.getMensajes().iterator();

         while(var2.hasNext()) {
            MensajeGenerado mensaje = (MensajeGenerado)var2.next();
            buffer.append(mensaje.toString());
         }

         return buffer.toString();
      } else {
         return "";
      }
   }

   private static String obtenerCantidadArchivos(String path) {
      File file = new File(path);
      return file.isDirectory() ? String.valueOf(file.listFiles().length) : "0";
   }
}
