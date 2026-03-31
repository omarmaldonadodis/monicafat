package SRI.Autorizacion;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
   name = "AutorizacionComprobantesOfflineService",
   targetNamespace = "http://ec.gob.sri.ws.autorizacion",
   wsdlLocation = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl"
)
public class AutorizacionComprobantesOfflineService extends Service {
   private static URL AUTORIZACIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
   private static WebServiceException AUTORIZACIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
   private static final QName AUTORIZACIONCOMPROBANTESOFFLINESERVICE_QNAME = new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService");

   public AutorizacionComprobantesOfflineService(String ambiente) {
      super(__getWsdlLocation(ambiente), AUTORIZACIONCOMPROBANTESOFFLINESERVICE_QNAME);
   }

   @WebEndpoint(
      name = "AutorizacionComprobantesOfflinePort"
   )
   public AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort() {
      return (AutorizacionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflinePort"), AutorizacionComprobantesOffline.class);
   }

   @WebEndpoint(
      name = "AutorizacionComprobantesOfflinePort"
   )
   public AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort(WebServiceFeature... features) {
      return (AutorizacionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflinePort"), AutorizacionComprobantesOffline.class, features);
   }

   private static URL __getWsdlLocation(String ambiente) {
      URL url = null;
      WebServiceException e = null;

      try {
         if (ambiente.equals("2")) {
            url = new URL("https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
         } else {
            url = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl");
         }
      } catch (MalformedURLException var4) {
         e = new WebServiceException(var4);
      }

      AUTORIZACIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION = url;
      AUTORIZACIONCOMPROBANTESOFFLINESERVICE_EXCEPTION = e;
      if (AUTORIZACIONCOMPROBANTESOFFLINESERVICE_EXCEPTION != null) {
         throw AUTORIZACIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
      } else {
         return AUTORIZACIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
      }
   }
}
