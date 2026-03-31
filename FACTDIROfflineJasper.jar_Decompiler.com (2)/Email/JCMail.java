package Email;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class JCMail {
   private String from = "";
   private String password = "";
   private String addressTo;
   private String Subject = "";
   private String MessageMail = "";
   private String host = "";
   private String port = "";

   public String SEND(String nombreFactura, String dirFacturaAutorizada) throws Exception {
      Properties props = new Properties();
      props.put("mail.smtp.host", this.getHost());
      props.put("mail.smtp.port", this.getPort());
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.ssl.trust", this.getHost());
      SMTPAuthenticator auth = new SMTPAuthenticator(this.getFrom(), this.getPassword());
      Session session = Session.getInstance(props, auth);
      session.setDebug(false);
      MimeMessage mimemessage = new MimeMessage(session);
      InternetAddress addressFrom = new InternetAddress(this.getFrom());
      mimemessage.setFrom(addressFrom);
      mimemessage.setRecipients(RecipientType.TO, this.getAddressTo());
      mimemessage.setSubject(this.getSubject());
      MimeBodyPart mimebodypart = new MimeBodyPart();
      mimebodypart.setText(this.getMessage());
      mimebodypart.setContent(this.getMessage(), "text/html");
      BodyPart adjunto = new MimeBodyPart();
      adjunto.setDataHandler(new DataHandler(new FileDataSource(dirFacturaAutorizada)));
      adjunto.setFileName(nombreFactura);
      Multipart multipart = new MimeMultipart();
      dirFacturaAutorizada = dirFacturaAutorizada.replace(".xml", ".pdf");
      dirFacturaAutorizada = dirFacturaAutorizada.replace(".XML", ".pdf");
      nombreFactura = nombreFactura.replace(".xml", ".pdf");
      nombreFactura = nombreFactura.replace(".XML", ".pdf");
      BodyPart adjunto2 = new MimeBodyPart();
      adjunto2.setDataHandler(new DataHandler(new FileDataSource(dirFacturaAutorizada)));
      adjunto2.setFileName(nombreFactura);
      multipart.addBodyPart(adjunto2);
      multipart.addBodyPart(mimebodypart);
      multipart.addBodyPart(adjunto);
      mimemessage.setContent(multipart);
      mimemessage.setSentDate(new Date());
      Transport.send(mimemessage);
      return "Correo enviado con éxito";
   }

   public void setFrom(String mail) {
      this.from = mail;
   }

   public String getFrom() {
      return this.from;
   }

   public void setPassword(char[] value) {
      this.setPassword(new String(value));
   }

   public String getPassword() {
      return this.password;
   }

   public void setTo(String mails) {
      this.setAddressTo(mails);
   }

   public String getTo() {
      return this.getAddressTo();
   }

   public void setSubject(String value) {
      this.Subject = value;
   }

   public String getSubject() {
      return this.Subject;
   }

   public void setMessage(String value) {
      this.setMessageMail(value);
   }

   public String getMessage() {
      return this.getMessageMail();
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getAddressTo() {
      return this.addressTo;
   }

   public void setAddressTo(String addressTo) {
      this.addressTo = addressTo;
   }

   public String getMessageMail() {
      return this.MessageMail;
   }

   public void setMessageMail(String MessageMail) {
      this.MessageMail = MessageMail;
   }

   public String getHost() {
      return this.host;
   }

   public void setHost(String host) {
      this.host = host;
   }

   public String getPort() {
      return this.port;
   }

   public void setPort(String port) {
      this.port = port;
   }
}
