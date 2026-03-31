package JasperComprobantes;

public class ComprobanteGeneral {
   private String dirLogo;
   private String ambiente;
   private String tipoEmision;
   private String razonSocial;
   private String nombreComercial;
   private String ruc;
   private String claveAcc;
   private String dirMatriz;
   private String dirEstablecimiento;
   private String contribuyenteEspecial;
   private String obligadoContabilidad;
   private String agenteRetencion;
   private String regimen;
   private String fechaEmision;
   private String fechaAutorizacion;
   private String numDocumento;
   private String numAutorizacion;
   private String subTotal15 = "0.00";
   private String subTotal5 = "0.00";
   private String subTotal0 = "0.00";
   private String subTotalNoObjetoIVA = "0.00";
   private String subTotalExentoIVA = "0.00";
   private String ICE = "";
   private String IRBPNR = "";
   private String IVA15 = "0.00";
   private String IVA5 = "0.00";
   private String tarifa = "0";
   private String subTotalSinImpuesto = "0.00";
   private String totalDescuento = "0.00";
   private String importeTotal = "0.00";

   public String getAmbiente() {
      return this.ambiente;
   }

   public void setAmbiente(String ambiente) {
      this.ambiente = ambiente;
   }

   public String getTipoEmision() {
      return this.tipoEmision;
   }

   public void setTipoEmision(String tipoEmision) {
      this.tipoEmision = tipoEmision;
   }

   public String getRazonSocial() {
      return this.razonSocial;
   }

   public void setRazonSocial(String razonSocial) {
      this.razonSocial = razonSocial;
   }

   public String getNombreComercial() {
      return this.nombreComercial;
   }

   public void setNombreComercial(String nombreComercial) {
      this.nombreComercial = nombreComercial;
   }

   public String getRuc() {
      return this.ruc;
   }

   public void setRuc(String ruc) {
      this.ruc = ruc;
   }

   public String getDirMatriz() {
      return this.dirMatriz;
   }

   public void setDirMatriz(String dirMatriz) {
      this.dirMatriz = dirMatriz;
   }

   public String getDirEstablecimiento() {
      return this.dirEstablecimiento;
   }

   public void setDirEstablecimiento(String dirEstablecimiento) {
      this.dirEstablecimiento = dirEstablecimiento;
   }

   public String getContribuyenteEspecial() {
      return this.contribuyenteEspecial;
   }

   public void setContribuyenteEspecial(String contribuyenteEspecial) {
      this.contribuyenteEspecial = contribuyenteEspecial;
   }

   public String getObligadoContabilidad() {
      return this.obligadoContabilidad;
   }

   public void setObligadoContabilidad(String obligadoContabilidad) {
      this.obligadoContabilidad = obligadoContabilidad;
   }

   public String getAgenteRetencion() {
      return this.agenteRetencion;
   }

   public void setAgenteRetencion(String agenteRetencion) {
      this.agenteRetencion = agenteRetencion;
   }

   public String getRegimen() {
      return this.regimen;
   }

   public void setRegimen(String regimen) {
      this.regimen = regimen;
   }

   public String getFechaEmision() {
      return this.fechaEmision;
   }

   public void setFechaEmision(String fechaEmision) {
      this.fechaEmision = fechaEmision;
   }

   public String getClaveAcc() {
      return this.claveAcc;
   }

   public void setClaveAcc(String claveAcc) {
      this.claveAcc = claveAcc;
   }

   public String getNumDocumento() {
      return this.numDocumento;
   }

   public void setNumDocumento(String numDocumento) {
      this.numDocumento = numDocumento;
   }

   public String getNumAutorizacion() {
      return this.numAutorizacion;
   }

   public void setNumAutorizacion(String numAutorizacion) {
      this.numAutorizacion = numAutorizacion;
   }

   public String getDirLogo() {
      return this.dirLogo;
   }

   public void setDirLogo(String dirLogo) {
      this.dirLogo = dirLogo;
   }

   public String getSubTotal15() {
      return this.subTotal15;
   }

   public void setSubTotal15(String subTotal15) {
      this.subTotal15 = subTotal15;
   }

   public String getSubTotal5() {
      return this.subTotal5;
   }

   public void setSubTotal5(String subTotal5) {
      this.subTotal5 = subTotal5;
   }

   public String getSubTotal0() {
      return this.subTotal0;
   }

   public void setSubTotal0(String subTotal0) {
      this.subTotal0 = subTotal0;
   }

   public String getSubTotalNoObjetoIVA() {
      return this.subTotalNoObjetoIVA;
   }

   public void setSubTotalNoObjetoIVA(String subTotalNoObjetoIVA) {
      this.subTotalNoObjetoIVA = subTotalNoObjetoIVA;
   }

   public String getSubTotalExentoIVA() {
      return this.subTotalExentoIVA;
   }

   public void setSubTotalExentoIVA(String subTotalExentoIVA) {
      this.subTotalExentoIVA = subTotalExentoIVA;
   }

   public String getICE() {
      return this.ICE;
   }

   public void setICE(String ICE) {
      this.ICE = ICE;
   }

   public String getIRBPNR() {
      return this.IRBPNR;
   }

   public void setIRBPNR(String IRBPNR) {
      this.IRBPNR = IRBPNR;
   }

   public String getIVA15() {
      return this.IVA15;
   }

   public void setIVA15(String IVA15) {
      this.IVA15 = IVA15;
   }

   public String getIVA5() {
      return this.IVA5;
   }

   public void setIVA5(String IVA5) {
      this.IVA5 = IVA5;
   }

   public String getTarifa() {
      return this.tarifa;
   }

   public void setTarifa(String tarifa) {
      this.tarifa = tarifa;
   }

   public String getSubTotalSinImpuesto() {
      return this.subTotalSinImpuesto;
   }

   public void setSubTotalSinImpuesto(String subTotalSinImpuesto) {
      this.subTotalSinImpuesto = subTotalSinImpuesto;
   }

   public String getImporteTotal() {
      return this.importeTotal;
   }

   public void setImporteTotal(String importeTotal) {
      this.importeTotal = importeTotal;
   }

   public String getTotalDescuento() {
      return this.totalDescuento;
   }

   public void setTotalDescuento(String totalDescuento) {
      this.totalDescuento = totalDescuento;
   }

   public String getFechaAutorizacion() {
      return this.fechaAutorizacion;
   }

   public void setFechaAutorizacion(String fechaAutorizacion) {
      this.fechaAutorizacion = fechaAutorizacion;
   }
}
