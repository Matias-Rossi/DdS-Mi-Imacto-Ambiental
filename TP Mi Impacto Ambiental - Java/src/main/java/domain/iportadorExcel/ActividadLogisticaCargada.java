package domain.iportadorExcel;

public class ActividadLogisticaCargada extends ActividadBase implements ActividadCargada {

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
    System.out.println(tipoProductoTransportado);
    System.out.println(tipoTransporteUtilizado);
    System.out.println(distanciaMediaRecorrida);
    System.out.println(pesoTotalTransportado);

  }
  public double calcularHC(){
    return 1;
  }
}
