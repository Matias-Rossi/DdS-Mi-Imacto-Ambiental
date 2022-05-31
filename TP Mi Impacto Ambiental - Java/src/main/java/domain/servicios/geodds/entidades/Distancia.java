package domain.servicios.geodds.entidades;

import lombok.Getter;

public class Distancia {
  @Getter
  private double valor;
  @Getter
  private String unidad;


  public String texto() {
    return valor + " " + unidad;
  }
}