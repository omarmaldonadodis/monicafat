package SRI.Recepcion;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
   name = "RecepcionComprobantesOfflineService",
   targetNamespace = "http://ec.gob.sri.ws.recepcion",
   wsdlLocation = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl"
)
public class RecepcionComprobantesOfflineService extends Service {
   private static URL RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
   private static WebServiceException RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
   private static final QName RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");

   public RecepcionComprobantesOfflineService(String ambiente) {
      super(__getWsdlLocation(ambiente), RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME);
   }

   @WebEndpoint(
      name = "RecepcionComprobantesOfflinePort"
   )
   public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort() {
      return (RecepcionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflinePort"), RecepcionComprobantesOffline.class);
   }

   @WebEndpoint(
      name = "RecepcionComprobantesOfflinePort"
   )
   public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort(WebServiceFeature... features) {
      return (RecepcionComprobantesOffline)super.getPort(new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflinePort"), RecepcionComprobantesOffline.class, features);
   }

   private static URL __getWsdlLocation(String ambiente) {
      URL url = null;
      WebServiceException e = null;

      try {
         if (ambiente.equals("2")) {
            url = new URL("https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
         } else {
            url = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
         }
      } catch (MalformedURLException var4) {
         e = new WebServiceException(var4);
      }

      RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION = url;
      RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION = e;
      if (RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION != null) {
         throw RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
      } else {
         return RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
      }
   }
}
