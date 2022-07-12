package domain.perfil;

import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Miembro {
    private String nombre;
    private String apellido;
    private TipoDocumento tipoDocumento;
    private Integer numeroDocumento;
    private List<Area> areas = new ArrayList<Area>();

    private List<Trayecto> trayectos = new ArrayList<Trayecto>();
    @Getter
    private List<Tramo> tramosCompartidosAAceptar = new ArrayList<Tramo>();

    public Miembro(String nombre, String apellido, TipoDocumento tipoDocumento, Integer numeroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }

    public void aniadirArea(Area area){
        this.areas.add(area);
    }
    public void darseAltaEnOrganizacion(Area area){
        area.agregarAMiembroPendiente(this);
    }

    public double calcularHC(Integer anio,Integer mes,Organizacion organizacion){
        List<Double> mapped = trayectos.stream().map(e->e.calcularHC(anio,mes,organizacion)).collect(Collectors.toList());
        return mapped.stream().reduce(0.0, (a, b) ->a+b);
    }
    public double calcularHCPorcentual(Integer anio,Integer mes,Area area){
        return (this.calcularHC(anio,mes,this.decirOrganizacion(area))/this.decirOrganizacion(area).calcularHC(anio,mes))*100;
    }

    public Organizacion decirOrganizacion(Area area){
        return area.getOrganizacion(); //TODO consultar si es necesario que el miembro diga su organizacion, si lo evitamos mejoramos el encapsulamiento
    }

    public void recibirSolicitud(Tramo tramo){
        this.tramosCompartidosAAceptar.add(tramo);
    }

    public void gestionarTramosCompartidos(Integer indiceSolicitud, Integer indiceTrayecto, boolean respuesta ){
        Tramo tramoCompartido = tramosCompartidosAAceptar.get(indiceSolicitud);
        Trayecto trayectoParaTramo = trayectos.get(indiceTrayecto);
        this.tramosCompartidosAAceptar.remove(tramoCompartido);
        if(respuesta) {
            trayectoParaTramo.agregarIntegranteATramo(tramoCompartido);
        }
        //en caso de que sea rechazado no lo va a hacer nada
    }

    public Trayecto generarTrayecto(String descripcion,List<Integer> indicesOrganizaciones,Integer anio, Integer semestre, Integer diasAlMes){
        List<Organizacion> organizaciones = new ArrayList<Organizacion>();
        indicesOrganizaciones.forEach(e->organizaciones.add(this.decirOrganizacion(this.areas.get(e))));
        Trayecto nuevoTrayecto = new Trayecto(descripcion,organizaciones,diasAlMes,anio,semestre);
        trayectos.add(nuevoTrayecto);
        return nuevoTrayecto;
    }
}