package domain.importadorExcel;

public class ActividadLogisticaCargada extends ActividadBase {

  TipoProductoTransportado tipoProductoTransportado;
  TipoTransporteUtilizado medioDeTransporte;
  double distanciaMediaRecorrida;
  double pesoTotalTransportado;
  public ActividadLogisticaCargada(TipoActividad tipoActividad, TipoProductoTransportado tipoProductoTransportado, TipoTransporteUtilizado tipoTransporteUtilizado, double distanciaMediaRecorrida, double pesoTotalTransportado, TipoPeriodicidad tipoPeriodicidad, String periodicidadDeImputacion) {
    super(tipoActividad,tipoPeriodicidad,periodicidadDeImputacion);
    this.tipoProductoTransportado = tipoProductoTransportado;
    this.medioDeTransporte = tipoTransporteUtilizado;
    this.distanciaMediaRecorrida = distanciaMediaRecorrida;
    this.pesoTotalTransportado =  pesoTotalTransportado;
  }

  @Override
  public double calcularHC(){
    return 1;
  }

}
