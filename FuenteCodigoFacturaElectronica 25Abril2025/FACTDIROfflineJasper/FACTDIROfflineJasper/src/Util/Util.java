package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Util {
   public Document convertirStringToXML(String comprobante) throws ParserConfigurationException, SAXException, IOException {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(comprobante));
      Document doc = db.parse(is);
      return doc;
   }

   public String convertirXMLToString(Document xmlComprobante) throws TransformerConfigurationException, TransformerException {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(xmlComprobante), new StreamResult(writer));
      String xmlString = writer.getBuffer().toString();
      return xmlString;
   }

   public static String convertirXMLToStringV2(Document xmlComprobante) throws TransformerConfigurationException, TransformerException {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      StringWriter writer = new StringWriter();
      transformer.transform(new DOMSource(xmlComprobante), new StreamResult(writer));
      String xmlString = writer.getBuffer().toString();
      return xmlString;
   }

   public static void printExceptionInFile(Exception e, String fileName, String path) {
      try {
         PrintWriter writer = new PrintWriter(new File(path.concat("\\").concat(fileName.concat(String.valueOf(Instant.now().getEpochSecond()))).concat("_error.txt")));
         Throwable var4 = null;

         try {
            e.printStackTrace(writer);
         } catch (Throwable var14) {
            var4 = var14;
            throw var14;
         } finally {
            if (writer != null) {
               if (var4 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var13) {
                     var4.addSuppressed(var13);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (FileNotFoundException var16) {
         System.out.println(var16.getMessage());
      }

   }

   public static void validarXML(String path) {
      try {
         String content = new String(Files.readAllBytes(Paths.get(path)));
         int index = content.indexOf("</factura>");
         if (index != -1) {
            content = content.substring(0, index + 10);
            Files.write(Paths.get(path), content.getBytes(), new OpenOption[0]);
         }
      } catch (IOException var3) {
      }

   }

   public static String convertToDate(String dateUTC) {
      DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
      OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateUTC, inputFormatter);
      LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
      DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      return localDateTime.format(outputFormatter);
   }
}
