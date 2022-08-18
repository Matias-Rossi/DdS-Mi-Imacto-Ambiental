package domain.perfil;

import domain.calculadorHC.CalculadorDeHC;
import domain.importadorExcel.ActividadBase;
import domain.notificaciones.Contacto;
import domain.persistenceExtend.EntidadPersistente;
import domain.ubicacion.Ubicacion;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Entity
@Table(name = "organizaciones")
public class Organizacion extends EntidadPersistente {
    @Getter
    @Transient
    public List<ActividadBase> actividadesCargadas= new ArrayList<ActividadBase>();
    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "tipo")
    private Tipo tipo;
    @OneToMany(mappedBy = "id", cascade = {CascadeType.ALL})
    private List<Area> areas= new ArrayList<Area>();
    @ManyToOne
    @JoinColumn(name = "clasificaciones_id", referencedColumnName = "id")
    private Clasificacion clasificacion;
    @Transient
    private Ubicacion ubicacion;
    @Transient
    private Importador moduloImportador;
    @Transient
    private domain.perfil.Organizacion Organizacion;
    @Transient
    @Getter
    private List<Contacto> contactos = new ArrayList<Contacto>();

    public Organizacion(){
    }
    public Organizacion(Importador moduloImportador, Ubicacion ubicacion,String razonSocial,Tipo tipo,Clasificacion clasificacion) {
        this.ubicacion=ubicacion;
        this.razonSocial=razonSocial;
        this.tipo=tipo;
        this.clasificacion=clasificacion;
        this.moduloImportador = moduloImportador;
    }

    public Area darAltaArea(String nombreArea){
        Area nuevaArea = new Area(nombreArea, this );
        this.areas.add(nuevaArea);
        return nuevaArea;
    }

    public double calcularHC(Integer anio,Integer mes){
        return this.calcularHCConsumos(anio,mes)+this.calcularHCViajes(anio,mes);
    }
    private double calcularHCConsumos(Integer anio,Integer mes){
        List<Double> mapped = actividadesCargadas.stream().map(e->e.calcularHC(anio,mes)).collect(Collectors.toList());
        return mapped.stream().reduce(0.0, (a, b) ->a+b);
    }
    private double calcularHCViajes(Integer anio,Integer mes){
        List<Double> mapped = areas.stream().map(e->e.calcularHC(anio,mes)).collect(Collectors.toList());
        return mapped.stream().reduce(0.0, (a, b) ->a+b);
    }

    public void cargarMediciones(String nombreArchivo, CalculadorDeHC calculadorDeHC){
        actividadesCargadas.addAll(moduloImportador.importarDatos(nombreArchivo,calculadorDeHC));
    }

    public void agregarContacto(String telefono, String email){
        Contacto nuevoContacto = new Contacto(telefono, email);
        this.contactos.add(nuevoContacto);
    }
}