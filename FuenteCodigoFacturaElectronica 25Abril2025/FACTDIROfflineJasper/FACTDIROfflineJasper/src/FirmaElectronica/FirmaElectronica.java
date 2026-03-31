package FirmaElectronica;

import Respuesta.MensajeGenerado;
import Respuesta.RespuestaInterna;
import Util.Util;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.libreria.xades.elementos.xades.ObjectIdentifier;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.issues.PassStoreKS;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FirmaElectronica {
   private String direccionCertificado;
   private String passwordCertificado;
   private Properties configuracion;
   private String dirCarpetaAutorizados;
   private KeyStore ks;

   public FirmaElectronica(Properties configuracion) {
      this.configuracion = configuracion;
   }

   public RespuestaInterna FirmarComprobante(Document xmlComprobante) {
      String ruc = xmlComprobante.getElementsByTagName("ruc").item(0).getTextContent();
      this.direccionCertificado = this.configuracion.getProperty("dirFirma" + ruc);
      this.passwordCertificado = this.configuracion.getProperty("passFirma" + ruc);
      this.dirCarpetaAutorizados = this.configuracion.getProperty("dirAutorizados" + ruc);
      new Util();
      RespuestaInterna respuestaInterna = new RespuestaInterna();
      respuestaInterna.setEstadoComprobante("ERROR");
      respuestaInterna.setComprobante(xmlComprobante);
      IPKStoreManager storeManager = null;
      X509Certificate certificate = null;

      try {
         storeManager = this.getPKStoreManager();
      } catch (Exception var17) {
         respuestaInterna.addMensaje(new MensajeGenerado("1000", "EL GESTOR DE CLAVES NO SE HA OBTENIDO CORRECTAMENTE", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
         return respuestaInterna;
      }

      if (storeManager == null) {
         respuestaInterna.addMensaje(new MensajeGenerado("1000", "FALLO OBTENIENDO EL LISTADO DE CERTIFICADOS", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
         return respuestaInterna;
      } else {
         try {
            certificate = this.getFirstCertificate(storeManager);
         } catch (Exception var16) {
            respuestaInterna.addMensaje(new MensajeGenerado("1000", "FALLO OBTENIENDO EL LISTADO DE CERTIFICADOS", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
            return respuestaInterna;
         }

         if (certificate == null) {
            respuestaInterna.addMensaje(new MensajeGenerado("1000", "NO EXISTE NINGUN CERTIFICADO PARA FIRMAR", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
            return respuestaInterna;
         } else {
            PrivateKey privateKey = null;

            try {
               privateKey = storeManager.getPrivateKey(certificate);
            } catch (CertStoreException var15) {
               respuestaInterna.addMensaje(new MensajeGenerado("1000", "ERROR AL ACCEDER AL ALMACEN", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
               return respuestaInterna;
            }

            Provider provider = storeManager.getProvider(certificate);
            DataToSign dataToSign = null;

            try {
               dataToSign = this.createDataToSign(xmlComprobante);
            } catch (Exception var14) {
               respuestaInterna.addMensaje(new MensajeGenerado("1000", "ERROR PROCESANDO EL XML A FIRMAR", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
               return respuestaInterna;
            }

            FirmaXML firma = new FirmaXML();
            Document xmlFirmado = null;

            try {
               Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
               xmlFirmado = (Document)res[0];
            } catch (Exception var13) {
               respuestaInterna.addMensaje(new MensajeGenerado("1000", "ERROR REALIZANDO LA FIRMA DEL COMPROBANTE", (String)null, FirmaElectronica.TipoError.EXCEPCION.toString()));
               return respuestaInterna;
            }

            respuestaInterna.setComprobante(xmlFirmado);
            respuestaInterna.setEstadoComprobante("FIRMADO");
            return respuestaInterna;
         }
      }
   }

   private IPKStoreManager getPKStoreManager() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
      this.ks = KeyStore.getInstance("PKCS12");
      FileInputStream fis = new FileInputStream(this.direccionCertificado);
      this.ks.load(fis, this.passwordCertificado.toCharArray());
      IPKStoreManager storeManager = new KSStore(this.ks, new PassStoreKS(this.passwordCertificado));
      return storeManager;
   }

   private X509Certificate getFirstCertificate(IPKStoreManager storeManager) throws CertStoreException, KeyStoreException {
      Enumeration nombres = this.ks.aliases();

      String aliasKey;
      do {
         if (!nombres.hasMoreElements()) {
            List<X509Certificate> certs = storeManager.getSignCertificates();
            if (certs != null && !certs.isEmpty()) {
               return (X509Certificate)certs.get(0);
            }

            return null;
         }

         aliasKey = (String)nombres.nextElement();
      } while(!aliasKey.toLowerCase().contains("signing key"));

      return (X509Certificate)this.ks.getCertificate(aliasKey);
   }

   protected DataToSign createDataToSign(Document xmlComprobante) throws TransformerException, TransformerConfigurationException, IOException, ParserConfigurationException, SAXException {
      DataToSign datosAFirmar = new DataToSign();
      datosAFirmar.setXadesFormat(EnumFormatoFirma.XAdES_BES);
      datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
      datosAFirmar.setXMLEncoding("UTF-8");
      datosAFirmar.setEnveloped(true);
      datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", (ObjectIdentifier)null, "text/xml", (URI)null));
      datosAFirmar.setParentSignNode("comprobante");
      datosAFirmar.setDocument(this.getDocument(xmlComprobante));
      return datosAFirmar;
   }

   protected Document getDocument(Document xmlComprobante) throws TransformerConfigurationException, TransformerException, IOException, ParserConfigurationException, SAXException {
      Source source = new DOMSource(xmlComprobante);
      Random rnd = new Random();
      int sufijo = (int)(rnd.nextDouble() * 100.0D);
      String nombreArchivoTemp = "temp" + Integer.toString(sufijo) + ".xml";
      Path dir = (new File(this.dirCarpetaAutorizados)).toPath().resolve(nombreArchivoTemp);
      File temp = new File(dir.toString());
      Result result = new StreamResult(temp);
      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      transformer.transform(source, result);
      Document doc = null;
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(temp);
      temp.delete();
      return doc;
   }

   static enum TipoError {
      EXCEPCION;
   }
}
