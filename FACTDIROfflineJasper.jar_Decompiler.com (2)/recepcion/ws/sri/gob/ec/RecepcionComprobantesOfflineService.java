package recepcion.ws.sri.gob.ec;

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
   private static final URL RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
   private static final WebServiceException RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
   private static final QName RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");

   public RecepcionComprobantesOfflineService() {
      super(__getWsdlLocation(), RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME);
   }

   public RecepcionComprobantesOfflineService(WebServiceFeature... features) {
      super(__getWsdlLocation(), RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME, features);
   }

   public RecepcionComprobantesOfflineService(URL wsdlLocation) {
      super(wsdlLocation, RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME);
   }

   public RecepcionComprobantesOfflineService(URL wsdlLocation, WebServiceFeature... features) {
      super(wsdlLocation, RECEPCIONCOMPROBANTESOFFLINESERVICE_QNAME, features);
   }

   public RecepcionComprobantesOfflineService(URL wsdlLocation, QName serviceName) {
      super(wsdlLocation, serviceName);
   }

   public RecepcionComprobantesOfflineService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
      super(wsdlLocation, serviceName, features);
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

   private static URL __getWsdlLocation() {
      if (RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION != null) {
         throw RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION;
      } else {
         return RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION;
      }
   }

   static {
      URL url = null;
      WebServiceException e = null;

      try {
         url = new URL("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
      } catch (MalformedURLException var3) {
         e = new WebServiceException(var3);
      }

      RECEPCIONCOMPROBANTESOFFLINESERVICE_WSDL_LOCATION = url;
      RECEPCIONCOMPROBANTESOFFLINESERVICE_EXCEPTION = e;
   }
}
