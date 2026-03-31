package JasperRide;

import JasperComprobantes.CampoAdicional;
import JasperComprobantes.ComprobanteRetencion;
import JasperComprobantes.Destinatario;
import JasperComprobantes.DetalleFactura;
import JasperComprobantes.DetalleGuiaRemision;
import JasperComprobantes.DetalleLiquidacionCompra;
import JasperComprobantes.DetalleNotaCredito;
import JasperComprobantes.Factura;
import JasperComprobantes.GuiaRemision;
import JasperComprobantes.ImpuestoComprobanteRetencion;
import JasperComprobantes.LiquidacionCompra;
import JasperComprobantes.Motivo;
import JasperComprobantes.NotaCredito;
import JasperComprobantes.NotaDebito;
import JasperComprobantes.Pago;
import JasperComprobantes.ReembolsoFactura;
import Util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JasperRide {
   public void CrearRide(Document xmlComprobante, String numAutorizacion, String dirGuardarDoc, Properties configuracion, String rucInstalacion) throws FileNotFoundException, JRException {
      String dirLogo = configuracion.getProperty("dirLogo" + rucInstalacion);
      String ireportDir = configuracion.getProperty("dirIreport" + rucInstalacion);
      dirGuardarDoc = dirGuardarDoc.replace(".xml", ".pdf");
      String ruc = xmlComprobante.getElementsByTagName("ruc").item(0).getTextContent();
      String establecimiento = xmlComprobante.getElementsByTagName("estab").item(0).getTextContent();
      String ptoEmision = xmlComprobante.getElementsByTagName("ptoEmi").item(0).getTextContent();
      String secuencial = xmlComprobante.getElementsByTagName("secuencial").item(0).getTextContent();
      String noComprobante = establecimiento + "-" + ptoEmision + "-" + secuencial;
      String razonSocial = xmlComprobante.getElementsByTagName("razonSocial").item(0).getTextContent();
      String claveAcceso = xmlComprobante.getElementsByTagName("claveAcceso").item(0).getTextContent();
      String codDoc = xmlComprobante.getElementsByTagName("codDoc").item(0).getTextContent();
      String ambiente = xmlComprobante.getElementsByTagName("ambiente").item(0).getTextContent();
      String nombreComercial = "";
      if (xmlComprobante.getElementsByTagName("nombreComercial").getLength() != 0) {
         nombreComercial = xmlComprobante.getElementsByTagName("nombreComercial").item(0).getTextContent();
      }

      String dirMatriz = xmlComprobante.getElementsByTagName("dirMatriz").item(0).getTextContent();
      String dirEstablecimiento = "";
      if (xmlComprobante.getElementsByTagName("dirEstablecimiento").getLength() != 0) {
         dirEstablecimiento = xmlComprobante.getElementsByTagName("dirEstablecimiento").item(0).getTextContent();
      }

      String contribuyenteEspecial = "";
      if (xmlComprobante.getElementsByTagName("contribuyenteEspecial").getLength() != 0) {
         contribuyenteEspecial = "Contribuyente Especial Nro: ".concat(xmlComprobante.getElementsByTagName("contribuyenteEspecial").item(0).getTextContent());
      }

      String obligadoContabilidad = xmlComprobante.getElementsByTagName("obligadoContabilidad").item(0).getTextContent();
      String agendeRetencion = configuracion.getProperty("agenteRetencion" + rucInstalacion);
      String regimen = configuracion.getProperty("regimen" + rucInstalacion);
      List data = new ArrayList();
      String dirPlantillas = this.dirPlantillaJasper(ruc, ireportDir);
      String dirPlantilla = "";
      String subTotal15 = "0.00";
      String subTotal5 = "0.00";
      String subTotal0 = "0.00";
      String subTotalNoObjetoIVA = "0.00";
      String subTotalExentoIVA = "0.00";
      String ICE = "";
      String IRBPNR = "";
      String IVA15 = "0.00";
      String IVA5 = "0.00";
      String importeTotal = "0.00";
      String tarifa = "";
      String fechaIniTransporte;
      String codigoPorcentaje;
      String baseImponible;
      String valor;
      if (!codDoc.equals("07")) {
         NodeList totalImpuesto = xmlComprobante.getElementsByTagName("totalImpuesto");
         int cantidadImpuesto = totalImpuesto.getLength();
         boolean presentaDescuento = true;
         if (cantidadImpuesto == 0) {
            presentaDescuento = false;
            totalImpuesto = xmlComprobante.getElementsByTagName("impuesto");
            cantidadImpuesto = totalImpuesto.getLength();
         }

         for(int i = 0; i < cantidadImpuesto; ++i) {
            Element element = (Element)totalImpuesto.item(i);
            fechaIniTransporte = element.getElementsByTagName("codigo").item(0).getTextContent();
            codigoPorcentaje = element.getElementsByTagName("codigoPorcentaje").item(0).getTextContent();
            baseImponible = element.getElementsByTagName("baseImponible").item(0).getTextContent();
            valor = element.getElementsByTagName("valor").item(0).getTextContent();
            if (fechaIniTransporte.equals("2")) {
               if (codigoPorcentaje.equals("0")) {
                  subTotal0 = baseImponible;
               } else if (codigoPorcentaje.equals("6")) {
                  subTotalNoObjetoIVA = baseImponible;
               } else if (codigoPorcentaje.equals("7")) {
                  subTotalExentoIVA = baseImponible;
               } else if (codigoPorcentaje.equals("4")) {
                  subTotal15 = baseImponible;
                  IVA15 = valor;
               } else if (codigoPorcentaje.equals("5")) {
                  subTotal5 = baseImponible;
                  IVA5 = valor;
               }
            } else if (!fechaIniTransporte.equals("3")) {
               if (fechaIniTransporte.equals("5")) {
                  ;
               }
            }
         }

         if (xmlComprobante.getElementsByTagName("importeTotal").getLength() != 0) {
            importeTotal = xmlComprobante.getElementsByTagName("importeTotal").item(0).getTextContent();
         } else if (xmlComprobante.getElementsByTagName("valorTotal").getLength() != 0) {
            importeTotal = xmlComprobante.getElementsByTagName("valorTotal").item(0).getTextContent();
         } else if (xmlComprobante.getElementsByTagName("valorModificacion").getLength() != 0) {
            importeTotal = xmlComprobante.getElementsByTagName("valorModificacion").item(0).getTextContent();
         }
      }

      String totalSubsidioSinIva;
      String docSustento;
      String noDocumento;
      String razonSocialTransportista;
      String rucTransportista;
      String placa;
      String dirPartida;
      NodeList docsSustento;
      int cantidad;
    
      NodeList detalles = null;
      int i;
      NodeList nodoRetenciones;
      Element element = null;
      int j;
      Element elementRetencion;
      NodeList reembolsos;
      double ahorroPorSubsidio;
  
  
      if (codDoc.equals("01")) {
         dirPlantilla = dirPlantillas + "/factura.jrxml";
         Factura factura = new Factura();
         factura.setDirLogo(dirLogo);
         factura.setRazonSocial(razonSocial);
         factura.setNombreComercial(nombreComercial);
         factura.setDirMatriz(dirMatriz);
         factura.setDirEstablecimiento(dirEstablecimiento);
         factura.setContribuyenteEspecial(contribuyenteEspecial);
         factura.setObligadoContabilidad(obligadoContabilidad);
         factura.setAgenteRetencion(agendeRetencion);
         factura.setRegimen(regimen);
         factura.setRuc(ruc);
         factura.setNumDocumento(noComprobante);
         factura.setNumAutorizacion(numAutorizacion);
         factura.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         factura.setTipoEmision("NORMAL");
         factura.setClaveAcc(claveAcceso);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialComprador").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("identificacionComprador").item(0).getTextContent();
         if (xmlComprobante.getElementsByTagName("direccionComprador").getLength() != 0) {
            placa = xmlComprobante.getElementsByTagName("direccionComprador").item(0).getTextContent();
            factura.setDireccionComprador(placa);
         }

         placa = xmlComprobante.getElementsByTagName("fechaEmision").item(0).getTextContent();
         dirPartida = Util.convertToDate(xmlComprobante.getElementsByTagName("etsi:SigningTime").item(0).getTextContent());
         fechaIniTransporte = "";
         if (xmlComprobante.getElementsByTagName("guiaRemision").getLength() != 0) {
            fechaIniTransporte = xmlComprobante.getElementsByTagName("guiaRemision").item(0).getTextContent();
         }

         factura.setRazonSocialComprador(razonSocialTransportista);
         factura.setIdentificacionComprador(rucTransportista);
         factura.setFechaEmision(placa);
         factura.setFechaAutorizacion(dirPartida);
         factura.setGuiaRemision(fechaIniTransporte);
         docsSustento = xmlComprobante.getElementsByTagName("campoAdicional");
         cantidad = docsSustento.getLength();

         for(cantidad = 0; cantidad < cantidad; ++cantidad) {
            Node node = docsSustento.item(cantidad);
            totalSubsidioSinIva = node.getAttributes().item(0).getNodeValue();
            docSustento = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(totalSubsidioSinIva);
            nuevoCampoAdicional.setValor(docSustento);
            factura.getInfoAdicional().add(nuevoCampoAdicional);
         }

         detalles = xmlComprobante.getElementsByTagName("pago");
         cantidad = detalles.getLength();

         for(i = 0; i < cantidad; ++i) {
            element = (Element)detalles.item(i);
            Pago nuevoPago = new Pago();
            nuevoPago.setFormaPago(this.formaDePago(element.getElementsByTagName("formaPago").item(0).getTextContent()));
            nuevoPago.setTotal(element.getElementsByTagName("total").item(0).getTextContent());
            if (element.getElementsByTagName("plazo").getLength() != 0) {
               nuevoPago.setPlazo(element.getElementsByTagName("plazo").item(0).getTextContent());
            }

            if (element.getElementsByTagName("unidadTiempo").getLength() != 0) {
               nuevoPago.setUnidadTiempo(element.getElementsByTagName("unidadTiempo").item(0).getTextContent());
            }

            factura.getPagos().add(nuevoPago);
         }

         nodoRetenciones = xmlComprobante.getElementsByTagName("detalle");
         cantidad = nodoRetenciones.getLength();

         for(j = 0; j < cantidad; ++j) {
            elementRetencion = (Element)nodoRetenciones.item(j);
            DetalleFactura nuevoDetalle = new DetalleFactura();
            nuevoDetalle.setCodigoPrincipal(elementRetencion.getElementsByTagName("codigoPrincipal").item(0).getTextContent());
            if (elementRetencion.getElementsByTagName("codigoAuxiliar").getLength() != 0) {
               nuevoDetalle.setCodigoAuxiliar(elementRetencion.getElementsByTagName("codigoAuxiliar").item(0).getTextContent());
            }

            nuevoDetalle.setDescuento(elementRetencion.getElementsByTagName("descuento").item(0).getTextContent());
            nuevoDetalle.setCantidad(elementRetencion.getElementsByTagName("cantidad").item(0).getTextContent());
            nuevoDetalle.setDescripcion(elementRetencion.getElementsByTagName("descripcion").item(0).getTextContent());
            nuevoDetalle.setPrecioUnitario(elementRetencion.getElementsByTagName("precioUnitario").item(0).getTextContent());
            nuevoDetalle.setPrecioTotalSinImpuesto(elementRetencion.getElementsByTagName("precioTotalSinImpuesto").item(0).getTextContent());
            NodeList detallesAdicionales = elementRetencion.getElementsByTagName("detallesAdicionales");
            nuevoDetalle.setDetalleAdicional1(detallesAdicionales.getLength() >= 1 ? ((Element)((Element)detallesAdicionales.item(0)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre") : "");
            nuevoDetalle.setDetalleAdicional2(detallesAdicionales.getLength() >= 2 ? ((Element)((Element)detallesAdicionales.item(1)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre") : "");
            nuevoDetalle.setDetalleAdicional3(detallesAdicionales.getLength() >= 3 ? ((Element)((Element)detallesAdicionales.item(2)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre") : "");
            factura.getDetalles().add(nuevoDetalle);
         }

         factura.setSubTotal0(subTotal0);
         factura.setSubTotal15(subTotal15);
         factura.setSubTotal5(subTotal5);
         factura.setSubTotalExentoIVA(subTotalExentoIVA);
         factura.setSubTotalNoObjetoIVA(subTotalNoObjetoIVA);
         factura.setIVA15(IVA15);
         factura.setIVA5(IVA5);
         factura.setTarifa(tarifa);
         factura.setImporteTotal(subTotal0);
         factura.setSubTotalSinImpuesto(xmlComprobante.getElementsByTagName("totalSinImpuestos").item(0).getTextContent());
         factura.setTotalDescuento(xmlComprobante.getElementsByTagName("totalDescuento").item(0).getTextContent());
         factura.setImporteTotal(importeTotal);
         if (xmlComprobante.getElementsByTagName("totalSubsidio").getLength() != 0) {
            totalSubsidioSinIva = xmlComprobante.getElementsByTagName("totalSubsidio").item(0).getTextContent();
            ahorroPorSubsidio = Double.parseDouble(totalSubsidioSinIva) * 0.12D + Double.parseDouble(totalSubsidioSinIva);
            double totalSinSubsidio = Double.parseDouble(importeTotal) + ahorroPorSubsidio;
            factura.setTotalSinSubsidio(formatDouble(totalSinSubsidio));
            factura.setAhorroSubsidio(formatDouble(ahorroPorSubsidio));
         }

         reembolsos = xmlComprobante.getElementsByTagName("reembolsoDetalle");
         if (reembolsos.getLength() > 0) {
            cantidad = reembolsos.getLength();

            for(i = 0; i < cantidad; ++i) {
               element = (Element)reembolsos.item(i);
               ReembolsoFactura reembolsoFactura = new ReembolsoFactura();
               reembolsoFactura.setIdentificacionProveedorReembolso(element.getElementsByTagName("identificacionProveedorReembolso").item(0).getTextContent());
               reembolsoFactura.setTipoDocumento("FACTURA");
               noDocumento = element.getElementsByTagName("estabDocReembolso").item(0).getTextContent() + "-" + element.getElementsByTagName("ptoEmiDocReembolso").item(0).getTextContent() + "-" + element.getElementsByTagName("secuencialDocReembolso").item(0).getTextContent();
               reembolsoFactura.setNoDocumento(noDocumento);
               reembolsoFactura.setFechaEmisionDocReembolso(element.getElementsByTagName("fechaEmisionDocReembolso").item(0).getTextContent());
               NodeList impuestos = element.getElementsByTagName("detalleImpuestos");
               Element impuesto = (Element)impuestos.item(0);
               reembolsoFactura.setImpuesto("IVA");
               reembolsoFactura.setPorcentaje(impuesto.getElementsByTagName("tarifa").item(0).getTextContent() + "%");
               reembolsoFactura.setBaseImponible(Double.parseDouble(impuesto.getElementsByTagName("baseImponibleReembolso").item(0).getTextContent()));
               reembolsoFactura.setValorImpuesto(Double.parseDouble(impuesto.getElementsByTagName("impuestoReembolso").item(0).getTextContent()));
               double total = reembolsoFactura.getBaseImponible() + reembolsoFactura.getValorImpuesto();
               reembolsoFactura.setTotal(total);
               factura.getReembolso().add(reembolsoFactura);
            }
         }

         data.add(factura);
      }

     
      NodeList destinatarios;
     
      if (codDoc.equals("03")) {
         dirPlantilla = dirPlantillas + "/liquidacionCompra.jrxml";
         LiquidacionCompra liqCompra = new LiquidacionCompra();
         liqCompra.setDirLogo(dirLogo);
         liqCompra.setRazonSocial(razonSocial);
         liqCompra.setNombreComercial(nombreComercial);
         liqCompra.setDirMatriz(dirMatriz);
         liqCompra.setDirEstablecimiento(dirEstablecimiento);
         liqCompra.setContribuyenteEspecial(contribuyenteEspecial);
         liqCompra.setObligadoContabilidad(obligadoContabilidad);
         liqCompra.setAgenteRetencion(agendeRetencion);
         liqCompra.setRegimen(regimen);
         liqCompra.setRuc(ruc);
         liqCompra.setNumDocumento(noComprobante);
         liqCompra.setNumAutorizacion(numAutorizacion);
         liqCompra.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         liqCompra.setTipoEmision("NORMAL");
         liqCompra.setClaveAcc(claveAcceso);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialProveedor").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("identificacionProveedor").item(0).getTextContent();
         placa = xmlComprobante.getElementsByTagName("fechaEmision").item(0).getTextContent();
         dirPartida = "";
         if (xmlComprobante.getElementsByTagName("direccionProveedor").getLength() != 0) {
            dirPartida = xmlComprobante.getElementsByTagName("direccionProveedor").item(0).getTextContent();
         }

         liqCompra.setRazonSocialProveedor(razonSocialTransportista);
         liqCompra.setIdentificacionProveedor(rucTransportista);
         liqCompra.setFechaEmision(placa);
         liqCompra.setDireccionProveedor(dirPartida);
         NodeList campoAdicional = xmlComprobante.getElementsByTagName("campoAdicional");
         cantidad = campoAdicional.getLength();

         for(cantidad = 0; cantidad < cantidad; ++cantidad) {
            Node node = campoAdicional.item(cantidad);
            valor = node.getAttributes().item(0).getNodeValue();
            totalSubsidioSinIva = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(valor);
            nuevoCampoAdicional.setValor(totalSubsidioSinIva);
            liqCompra.getInfoAdicional().add(nuevoCampoAdicional);
         }

         destinatarios = xmlComprobante.getElementsByTagName("pago");
         cantidad = destinatarios.getLength();

         for(cantidad = 0; cantidad < cantidad; ++cantidad) {
            
            Pago nuevoPago = new Pago();
            nuevoPago.setFormaPago(this.formaDePago(element.getElementsByTagName("formaPago").item(0).getTextContent()));
            nuevoPago.setTotal(element.getElementsByTagName("total").item(0).getTextContent());
            if (element.getElementsByTagName("plazo").getLength() != 0) {
               nuevoPago.setPlazo(element.getElementsByTagName("plazo").item(0).getTextContent());
            }

            if (element.getElementsByTagName("unidadTiempo").getLength() != 0) {
               nuevoPago.setUnidadTiempo(element.getElementsByTagName("unidadTiempo").item(0).getTextContent());
            }

            liqCompra.getPagos().add(nuevoPago);
         }

         detalles = xmlComprobante.getElementsByTagName("detalle");
         cantidad = detalles.getLength();

         for(i = 0; i < cantidad; ++i) {
            element = (Element)detalles.item(i);
            DetalleLiquidacionCompra nuevoDetalle = new DetalleLiquidacionCompra();
            nuevoDetalle.setCodigoPrincipal(element.getElementsByTagName("codigoPrincipal").item(0).getTextContent());
            if (element.getElementsByTagName("codigoAuxiliar").getLength() != 0) {
               nuevoDetalle.setCodigoAuxiliar(element.getElementsByTagName("codigoAuxiliar").item(0).getTextContent());
            }

            nuevoDetalle.setDescuento(element.getElementsByTagName("descuento").item(0).getTextContent());
            nuevoDetalle.setCantidad(element.getElementsByTagName("cantidad").item(0).getTextContent());
            nuevoDetalle.setDescripcion(element.getElementsByTagName("descripcion").item(0).getTextContent());
            nuevoDetalle.setPrecioUnitario(element.getElementsByTagName("precioUnitario").item(0).getTextContent());
            nuevoDetalle.setPrecioTotalSinImpuesto(element.getElementsByTagName("precioTotalSinImpuesto").item(0).getTextContent());
            liqCompra.getDetalles().add(nuevoDetalle);
         }

         liqCompra.setSubTotal0(subTotal0);
         liqCompra.setSubTotal15(subTotal15);
         liqCompra.setSubTotal5(subTotal5);
         liqCompra.setSubTotalExentoIVA(subTotalExentoIVA);
         liqCompra.setSubTotalNoObjetoIVA(subTotalNoObjetoIVA);
         liqCompra.setIVA15(IVA15);
         liqCompra.setIVA5(IVA5);
         liqCompra.setTarifa(tarifa);
         liqCompra.setImporteTotal(subTotal0);
         liqCompra.setSubTotalSinImpuesto(xmlComprobante.getElementsByTagName("totalSinImpuestos").item(0).getTextContent());
         liqCompra.setTotalDescuento(xmlComprobante.getElementsByTagName("totalDescuento").item(0).getTextContent());
         liqCompra.setImporteTotal(importeTotal);
         data.add(liqCompra);
      }

      String numDocSustento;
      String nombre;
      if (codDoc.equals("04")) {
         dirPlantilla = dirPlantillas + "/notaCredito.jrxml";
         NotaCredito notaCredito = new NotaCredito();
         notaCredito.setDirLogo(dirLogo);
         notaCredito.setRazonSocial(razonSocial);
         notaCredito.setNombreComercial(nombreComercial);
         notaCredito.setDirMatriz(dirMatriz);
         notaCredito.setDirEstablecimiento(dirEstablecimiento);
         notaCredito.setContribuyenteEspecial(contribuyenteEspecial);
         notaCredito.setObligadoContabilidad(obligadoContabilidad);
         notaCredito.setAgenteRetencion(agendeRetencion);
         notaCredito.setRegimen(regimen);
         notaCredito.setRuc(ruc);
         notaCredito.setNumDocumento(noComprobante);
         notaCredito.setNumAutorizacion(numAutorizacion);
         notaCredito.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         notaCredito.setTipoEmision("NORMAL");
         notaCredito.setClaveAcc(claveAcceso);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialComprador").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("identificacionComprador").item(0).getTextContent();
         placa = xmlComprobante.getElementsByTagName("fechaEmision").item(0).getTextContent();
         dirPartida = this.tipoComprobante(xmlComprobante.getElementsByTagName("codDocModificado").item(0).getTextContent());
         fechaIniTransporte = xmlComprobante.getElementsByTagName("numDocModificado").item(0).getTextContent();
         codigoPorcentaje = xmlComprobante.getElementsByTagName("fechaEmisionDocSustento").item(0).getTextContent();
         baseImponible = xmlComprobante.getElementsByTagName("motivo").item(0).getTextContent();
         notaCredito.setRazonSocialComprador(razonSocialTransportista);
         notaCredito.setIdentificacionComprador(rucTransportista);
         notaCredito.setFechaEmision(placa);
         notaCredito.setComprobanteModificado(dirPartida);
         notaCredito.setNumDocModificado(fechaIniTransporte);
         notaCredito.setFechaEmisionDocSustento(codigoPorcentaje);
         notaCredito.setMotivo(baseImponible);
         detalles = xmlComprobante.getElementsByTagName("campoAdicional");
         i = detalles.getLength();

         for(j = 0; j < i; ++j) {
            Node node = detalles.item(j);
            numDocSustento = node.getAttributes().item(0).getNodeValue();
            nombre = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(numDocSustento);
            nuevoCampoAdicional.setValor(nombre);
            notaCredito.getInfoAdicional().add(nuevoCampoAdicional);
         }

         reembolsos = xmlComprobante.getElementsByTagName("detalle");
         i = reembolsos.getLength();
         ahorroPorSubsidio = 0.0D;

     for (int reembolsoIndex = 0; reembolsoIndex < reembolsos.getLength(); ++reembolsoIndex) { // Renombrar "i" a "reembolsoIndex"
    Element reembolsoElement = (Element) reembolsos.item(reembolsoIndex); // Renombrar "element" a "reembolsoElement"
    DetalleNotaCredito nuevoDetalle = new DetalleNotaCredito();
    nuevoDetalle.setCodigoInterno(reembolsoElement.getElementsByTagName("codigoInterno").item(0).getTextContent());
    if (reembolsoElement.getElementsByTagName("codigoAdicional").getLength() != 0) {
        nuevoDetalle.setCodigoAdicional(reembolsoElement.getElementsByTagName("codigoAdicional").item(0).getTextContent());
    }

    nuevoDetalle.setDescuento(reembolsoElement.getElementsByTagName("descuento").item(0).getTextContent());
    nuevoDetalle.setCantidad(reembolsoElement.getElementsByTagName("cantidad").item(0).getTextContent());
    nuevoDetalle.setDescripcion(reembolsoElement.getElementsByTagName("descripcion").item(0).getTextContent());
    nuevoDetalle.setPrecioUnitario(reembolsoElement.getElementsByTagName("precioUnitario").item(0).getTextContent());
    nuevoDetalle.setPrecioTotalSinImpuesto(reembolsoElement.getElementsByTagName("precioTotalSinImpuesto").item(0).getTextContent());

    NodeList detallesAdicionales = reembolsoElement.getElementsByTagName("detallesAdicionales");
    nuevoDetalle.setDetalleAdicional1(detallesAdicionales.getLength() >= 1
            ? ((Element) ((Element) detallesAdicionales.item(0)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre")
            : "");
    nuevoDetalle.setDetalleAdicional2(detallesAdicionales.getLength() >= 2
            ? ((Element) ((Element) detallesAdicionales.item(1)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre")
            : "");
    nuevoDetalle.setDetalleAdicional3(detallesAdicionales.getLength() >= 3
            ? ((Element) ((Element) detallesAdicionales.item(2)).getElementsByTagName("detAdicional").item(0)).getAttribute("nombre")
            : "");

    notaCredito.getDetalles().add(nuevoDetalle);
    ahorroPorSubsidio += Double.parseDouble(nuevoDetalle.getDescuento());
}

         notaCredito.setSubTotal0(subTotal0);
         notaCredito.setSubTotal15(subTotal15);
         notaCredito.setSubTotal5(subTotal5);
         notaCredito.setSubTotalExentoIVA(subTotalExentoIVA);
         notaCredito.setSubTotalNoObjetoIVA(subTotalNoObjetoIVA);
         notaCredito.setIVA15(IVA15);
         notaCredito.setIVA5(IVA5);
         notaCredito.setTarifa(tarifa);
         notaCredito.setImporteTotal(subTotal0);
         notaCredito.setSubTotalSinImpuesto(xmlComprobante.getElementsByTagName("totalSinImpuestos").item(0).getTextContent());
         notaCredito.setImporteTotal(importeTotal);
         notaCredito.setTotalDescuento(String.valueOf(ahorroPorSubsidio));
         data.add(notaCredito);
      }

      if (codDoc.equals("05")) {
         dirPlantilla = dirPlantillas + "/notaDebito.jrxml";
         NotaDebito notaDebito = new NotaDebito();
         notaDebito.setDirLogo(dirLogo);
         notaDebito.setRazonSocial(razonSocial);
         notaDebito.setNombreComercial(nombreComercial);
         notaDebito.setDirMatriz(dirMatriz);
         notaDebito.setDirEstablecimiento(dirEstablecimiento);
         notaDebito.setContribuyenteEspecial(contribuyenteEspecial);
         notaDebito.setObligadoContabilidad(obligadoContabilidad);
         notaDebito.setAgenteRetencion(agendeRetencion);
         notaDebito.setRegimen(regimen);
         notaDebito.setRuc(ruc);
         notaDebito.setNumDocumento(noComprobante);
         notaDebito.setNumAutorizacion(numAutorizacion);
         notaDebito.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         notaDebito.setTipoEmision("NORMAL");
         notaDebito.setClaveAcc(claveAcceso);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialComprador").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("identificacionComprador").item(0).getTextContent();
         placa = xmlComprobante.getElementsByTagName("fechaEmision").item(0).getTextContent();
         dirPartida = this.tipoComprobante(xmlComprobante.getElementsByTagName("codDocModificado").item(0).getTextContent());
         fechaIniTransporte = xmlComprobante.getElementsByTagName("numDocModificado").item(0).getTextContent();
         codigoPorcentaje = xmlComprobante.getElementsByTagName("fechaEmisionDocSustento").item(0).getTextContent();
         notaDebito.setRazonSocialComprador(razonSocialTransportista);
         notaDebito.setIdentificacionComprador(rucTransportista);
         notaDebito.setFechaEmision(placa);
         notaDebito.setComprobanteModificado(dirPartida);
         notaDebito.setNumDocModificado(fechaIniTransporte);
         notaDebito.setFechaEmisionDocSustento(codigoPorcentaje);
         destinatarios = xmlComprobante.getElementsByTagName("campoAdicional");
         cantidad = destinatarios.getLength();

         for(i = 0; i < cantidad; ++i) {
            Node node = destinatarios.item(i);
            docSustento = node.getAttributes().item(0).getNodeValue();
            numDocSustento = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(docSustento);
            nuevoCampoAdicional.setValor(numDocSustento);
            notaDebito.getInfoAdicional().add(nuevoCampoAdicional);
         }

         nodoRetenciones = xmlComprobante.getElementsByTagName("pago");
         cantidad = nodoRetenciones.getLength();

         for(j = 0; j < cantidad; ++j) {
            elementRetencion = (Element)nodoRetenciones.item(j);
            Pago nuevoPago = new Pago();
            nuevoPago.setFormaPago(this.formaDePago(elementRetencion.getElementsByTagName("formaPago").item(0).getTextContent()));
            nuevoPago.setTotal(elementRetencion.getElementsByTagName("total").item(0).getTextContent());
            if (elementRetencion.getElementsByTagName("plazo").getLength() != 0) {
               nuevoPago.setPlazo(elementRetencion.getElementsByTagName("plazo").item(0).getTextContent());
            }

            if (elementRetencion.getElementsByTagName("unidadTiempo").getLength() != 0) {
               nuevoPago.setUnidadTiempo(elementRetencion.getElementsByTagName("unidadTiempo").item(0).getTextContent());
            }

            notaDebito.getPagos().add(nuevoPago);
         }

         reembolsos = xmlComprobante.getElementsByTagName("motivo");
         cantidad = reembolsos.getLength();

         for(i = 0; i < cantidad; ++i) {
            element = (Element)reembolsos.item(i);
            Motivo nuevoMotivo = new Motivo();
            nuevoMotivo.setRazon(element.getElementsByTagName("razon").item(0).getTextContent());
            nuevoMotivo.setValor(element.getElementsByTagName("valor").item(0).getTextContent());
            notaDebito.getMotivos().add(nuevoMotivo);
         }

         notaDebito.setSubTotal0(subTotal0);
         notaDebito.setSubTotal15(subTotal15);
         notaDebito.setSubTotal5(subTotal5);
         notaDebito.setSubTotalExentoIVA(subTotalExentoIVA);
         notaDebito.setSubTotalNoObjetoIVA(subTotalNoObjetoIVA);
         notaDebito.setIVA15(IVA15);
         notaDebito.setIVA5(IVA5);
         notaDebito.setTarifa(tarifa);
         notaDebito.setImporteTotal(subTotal0);
         notaDebito.setSubTotalSinImpuesto(xmlComprobante.getElementsByTagName("totalSinImpuestos").item(0).getTextContent());
         notaDebito.setImporteTotal(importeTotal);
         data.add(notaDebito);
      }

      if (codDoc.equals("07")) {
         dirPlantilla = dirPlantillas + "/retencion.jrxml";
         ComprobanteRetencion retencion = new ComprobanteRetencion();
         retencion.setDirLogo(dirLogo);
         retencion.setRazonSocial(razonSocial);
         retencion.setNombreComercial(nombreComercial);
         retencion.setDirMatriz(dirMatriz);
         retencion.setDirEstablecimiento(dirEstablecimiento);
         retencion.setContribuyenteEspecial(contribuyenteEspecial);
         retencion.setObligadoContabilidad(obligadoContabilidad);
         retencion.setRuc(ruc);
         retencion.setNumDocumento(noComprobante);
         retencion.setNumAutorizacion(numAutorizacion);
         retencion.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         retencion.setTipoEmision("NORMAL");
         retencion.setClaveAcc(claveAcceso);
         retencion.setRegimen(regimen);
         retencion.setAgenteRetencion(agendeRetencion);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialSujetoRetenido").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("identificacionSujetoRetenido").item(0).getTextContent();
         placa = xmlComprobante.getElementsByTagName("fechaEmision").item(0).getTextContent();
         retencion.setRazonSocialSujetoRetenido(razonSocialTransportista);
         retencion.setIdentificacionSujetoRetenido(rucTransportista);
         retencion.setFechaEmision(placa);
         NodeList campoAdicional = xmlComprobante.getElementsByTagName("campoAdicional");
      

         for(cantidad = 0; cantidad < cantidad; ++cantidad) {
            Node node = campoAdicional.item(cantidad);
            valor = node.getAttributes().item(0).getNodeValue();
            valor = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(valor);
            nuevoCampoAdicional.setValor(valor);
            retencion.getInfoAdicional().add(nuevoCampoAdicional);
         }

         docsSustento = xmlComprobante.getElementsByTagName("docsSustento");
         cantidad = docsSustento.getLength();

for (int docIndex = 0; docIndex < docsSustento.getLength(); ++docIndex) { // Renombrar "cantidad" a "docIndex"
    Element docSustentoElement = (Element) docsSustento.item(docIndex); // Renombrar "element" a "docSustentoElement"
    nodoRetenciones = docSustentoElement.getElementsByTagName("retencion");

            for(j = 0; j < nodoRetenciones.getLength(); ++j) {
               elementRetencion = (Element)nodoRetenciones.item(j);
               ImpuestoComprobanteRetencion nuevoImpuestoComprobanteRetencion = new ImpuestoComprobanteRetencion();
               nuevoImpuestoComprobanteRetencion.setNumDocSustento(element.getElementsByTagName("numDocSustento").item(0).getTextContent());
               nuevoImpuestoComprobanteRetencion.setEjercicioFiscal(xmlComprobante.getElementsByTagName("periodoFiscal").item(0).getTextContent());
               nuevoImpuestoComprobanteRetencion.setTipoImpuesto(this.impuestoRetencion(elementRetencion.getElementsByTagName("codigo").item(0).getTextContent()));
               nuevoImpuestoComprobanteRetencion.setBaseImponible(elementRetencion.getElementsByTagName("baseImponible").item(0).getTextContent());
               nuevoImpuestoComprobanteRetencion.setPorcentajeRetener(elementRetencion.getElementsByTagName("porcentajeRetener").item(0).getTextContent());
               nuevoImpuestoComprobanteRetencion.setValorRetenido(elementRetencion.getElementsByTagName("valorRetenido").item(0).getTextContent());
               nuevoImpuestoComprobanteRetencion.setCodigoRetencion(elementRetencion.getElementsByTagName("codigoRetencion").item(0).getTextContent());
               retencion.getImpuestos().add(nuevoImpuestoComprobanteRetencion);
            }
         }

         data.add(retencion);
      }

      if (codDoc.equals("06")) {
         dirPlantilla = dirPlantillas + "/guia.jrxml";
         GuiaRemision guia = new GuiaRemision();
         guia.setDirLogo(dirLogo);
         guia.setRazonSocial(razonSocial);
         guia.setNombreComercial(nombreComercial);
         guia.setDirMatriz(dirMatriz);
         guia.setDirEstablecimiento(dirEstablecimiento);
         guia.setContribuyenteEspecial(contribuyenteEspecial);
         guia.setObligadoContabilidad(obligadoContabilidad);
         guia.setRuc(ruc);
         guia.setNumDocumento(noComprobante);
         guia.setNumAutorizacion(numAutorizacion);
         guia.setAmbiente(ambiente.equals("1") ? "PRUEBA" : "PRODUCCION");
         guia.setTipoEmision("NORMAL");
         guia.setClaveAcc(claveAcceso);
         guia.setAgenteRetencion(agendeRetencion);
         guia.setRegimen(regimen);
         razonSocialTransportista = xmlComprobante.getElementsByTagName("razonSocialTransportista").item(0).getTextContent();
         rucTransportista = xmlComprobante.getElementsByTagName("rucTransportista").item(0).getTextContent();
         placa = xmlComprobante.getElementsByTagName("placa").item(0).getTextContent();
         dirPartida = xmlComprobante.getElementsByTagName("dirPartida").item(0).getTextContent();
         fechaIniTransporte = xmlComprobante.getElementsByTagName("fechaIniTransporte").item(0).getTextContent();
         codigoPorcentaje = xmlComprobante.getElementsByTagName("fechaFinTransporte").item(0).getTextContent();
         guia.setRazonSocialTransportista(razonSocialTransportista);
         guia.setRucTransportista(rucTransportista);
         guia.setPlaca(placa);
         guia.setDirPartida(dirPartida);
         guia.setFechaIniTransporte(fechaIniTransporte);
         guia.setFechaFinTransporte(codigoPorcentaje);
         destinatarios = xmlComprobante.getElementsByTagName("destinatario");
         cantidad = destinatarios.getLength();

         for(i = 0; i < cantidad; ++i) {
            element = (Element)destinatarios.item(i);
            docSustento = "FACTURA";
            numDocSustento = element.getElementsByTagName("numDocSustento").getLength() > 0 ? element.getElementsByTagName("numDocSustento").item(0).getTextContent() : null;
            nombre = element.getElementsByTagName("fechaEmisionDocSustento").getLength() > 0 ? element.getElementsByTagName("fechaEmisionDocSustento").item(0).getTextContent() : null;
            noDocumento = element.getElementsByTagName("numAutDocSustento").getLength() > 0 ? element.getElementsByTagName("numAutDocSustento").item(0).getTextContent() : null;
            String motivoTraslado = element.getElementsByTagName("motivoTraslado").item(0).getTextContent();
            String dirDestinatario = element.getElementsByTagName("dirDestinatario").item(0).getTextContent();
            String identificacionDestinatario = element.getElementsByTagName("identificacionDestinatario").item(0).getTextContent();
            String razonSocialDestinatario = element.getElementsByTagName("razonSocialDestinatario").item(0).getTextContent();
            String docAduaneroUnico = element.getElementsByTagName("docAduaneroUnico").getLength() > 0 ? element.getElementsByTagName("docAduaneroUnico").item(0).getTextContent() : null;
            String codEstabDestino = element.getElementsByTagName("codEstabDestino").getLength() > 0 ? element.getElementsByTagName("codEstabDestino").item(0).getTextContent() : null;
            String ruta = element.getElementsByTagName("ruta").getLength() > 0 ? element.getElementsByTagName("ruta").item(0).getTextContent() : null;
            Destinatario destinatarioReport = new Destinatario();
            destinatarioReport.setDocSustento(docSustento);
            destinatarioReport.setNumDocSustento(numDocSustento);
            destinatarioReport.setFechaEmisionDocSustento(nombre);
            destinatarioReport.setNumAutDocSustento(noDocumento);
            destinatarioReport.setMotivoTraslado(motivoTraslado);
            destinatarioReport.setDirDestinatario(dirDestinatario);
            destinatarioReport.setIdentificacionDestinatario(identificacionDestinatario);
            destinatarioReport.setRazonSocialDestinatario(razonSocialDestinatario);
            destinatarioReport.setDocAduaneroUnico(docAduaneroUnico);
            destinatarioReport.setCodEstabDestino(codEstabDestino);
            destinatarioReport.setRuta(ruta);
         
            int cantidadDetalles = detalles.getLength();

for (int detalleIndex = 0; detalleIndex < cantidadDetalles; ++detalleIndex) { // Renombrar "j" a "detalleIndex"
    Element detalleElement = (Element) detalles.item(detalleIndex);
    DetalleGuiaRemision nuevoDetalle = new DetalleGuiaRemision();
    nuevoDetalle.setCodigoInterno(detalleElement.getElementsByTagName("codigoInterno").item(0).getTextContent());
    if (detalleElement.getElementsByTagName("codigoAdicional").getLength() != 0) {
        nuevoDetalle.setCodigoAdicional(detalleElement.getElementsByTagName("codigoAdicional").item(0).getTextContent());
    }

    nuevoDetalle.setCantidad(detalleElement.getElementsByTagName("cantidad").item(0).getTextContent());
    nuevoDetalle.setDescripcion(detalleElement.getElementsByTagName("descripcion").item(0).getTextContent());
    destinatarioReport.getDetalles().add(nuevoDetalle);
}

            guia.getDestinatarios().add(destinatarioReport);
         }

         nodoRetenciones = xmlComprobante.getElementsByTagName("campoAdicional");
         j = nodoRetenciones.getLength();

         for(i = 0; i < j; ++i) {
            Node node = nodoRetenciones.item(i);
            nombre = node.getAttributes().item(0).getNodeValue();
            noDocumento = node.getTextContent();
            CampoAdicional nuevoCampoAdicional = new CampoAdicional();
            nuevoCampoAdicional.setNombre(nombre);
            nuevoCampoAdicional.setValor(noDocumento);
            guia.getInfoAdicional().add(nuevoCampoAdicional);
         }

         data.add(guia);
      }

      InputStream inputStream = new FileInputStream(new File(dirPlantilla));
      JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
      JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
      HashMap parameters = new HashMap();
      parameters.put("DIR_PLANTILLAS", dirPlantillas);
      JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(data);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
      JasperExportManager.exportReportToPdfFile(jasperPrint, dirGuardarDoc);
   }

   private String dirPlantillaJasper(String ruc, String ireportDir) {
      File f = new File(ireportDir + "/" + ruc);
      if (f.exists()) {
         return f.getAbsolutePath();
      } else {
         f = new File(ireportDir + "/Default");
         return f.getAbsolutePath();
      }
   }

   private String formaDePago(String formaPago) {
      String formaDePago = "";
      if (formaPago.equals("01")) {
         formaDePago = "SIN UTILIZACION DEL SISTEMA FINANCIERO";
      } else if (formaPago.equals("15")) {
         formaDePago = "COMPENSACIÓN DE DEUDAS";
      } else if (formaPago.equals("16")) {
         formaDePago = "TARJETA DE DÉBITO";
      } else if (formaPago.equals("17")) {
         formaDePago = "DINERO ELECTRÓNICO";
      } else if (formaPago.equals("18")) {
         formaDePago = "TARJETA PREPAGO";
      } else if (formaPago.equals("19")) {
         formaDePago = "TARJETA DE CRÉDITO";
      } else if (formaPago.equals("20")) {
         formaDePago = "OTROS CON UTILIZACION DEL SISTEMA FINANCIERO";
      } else if (formaPago.equals("21")) {
         formaDePago = "ENDOSO DE TÍTULOS";
      }

      return formaDePago;
   }

   private String tipoComprobante(String codDoc) {
      String tipoComprobante = "";
      if (codDoc.equals("01")) {
         tipoComprobante = "FACTURA";
      } else if (codDoc.equals("04")) {
         tipoComprobante = "NOTA DE CRÉDITO";
      } else if (codDoc.equals("05")) {
         tipoComprobante = "NOTA DE DÉBITO";
      } else if (codDoc.equals("06")) {
         tipoComprobante = "GUÍA DE REMISIÓN";
      } else if (codDoc.equals("07")) {
         tipoComprobante = "COMPROBANTE DE RETENCIÓN";
      }

      return tipoComprobante;
   }

   private String impuestoRetencion(String codigo) {
      return codigo.equals("1") ? "RENTA" : (codigo.equals("2") ? "IVA" : "ISD");
   }

   private static String formatDouble(double num) {
      return String.format("%.2f", num).replace(",", ".");
   }

   private static String obtenerTarifa(String codigo) {
      String resultado = "";
      if (codigo.equals("2")) {
         resultado = "12";
      } else if (codigo.equals("3")) {
         resultado = "14";
      } else if (codigo.equals("4")) {
         resultado = "15";
      } else if (codigo.equals("5")) {
         resultado = "5";
      }

      return resultado;
   }
}
