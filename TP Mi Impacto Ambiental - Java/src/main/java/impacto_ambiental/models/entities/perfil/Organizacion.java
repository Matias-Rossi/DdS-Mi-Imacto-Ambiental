package impacto_ambiental.models.entities.perfil;

import impacto_ambiental.models.entities.importadorExcel.ActividadBase;
import impacto_ambiental.models.entities.notificaciones.Contacto;
import impacto_ambiental.models.entities.EntidadPersistente;
import impacto_ambiental.models.entities.reportes.HChistorico;
import impacto_ambiental.models.entities.ubicacion.MunicipiosODepartamentos;
import impacto_ambiental.models.entities.ubicacion.Ubicacion;
import lombok.Getter;
import impacto_ambiental.models.entities.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "organizaciones")
public class Organizacion extends EntidadPersistente {

    @ManyToOne
            @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    Usuario usuario;

    @Transient
    List<HChistorico> HChistoricos = new ArrayList<HChistorico>();

    @Getter
    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    private MunicipiosODepartamentos municipioODepartamento;
    @Getter
    @OneToMany(mappedBy = "organizacion",cascade = javax.persistence.CascadeType.ALL,fetch = javax.persistence.FetchType.LAZY)
    public List<ActividadBase> actividadesCargadas= new ArrayList<ActividadBase>();
    @Getter
    @Column(name = "razon_social",unique = true)
    private String razonSocial;

    @Column(name = "tipo")
    private Tipo tipo;
    @OneToMany(mappedBy = "id", cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Area> areas= new ArrayList<Area>();
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "clasificaciones_id", referencedColumnName = "id")
    private Clasificacion clasificacion;
    @OneToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    private Ubicacion ubicacion;
    @Transient
    private Importador moduloImportador;

    @Getter
    @OneToMany(mappedBy = "organizacion",cascade = javax.persistence.CascadeType.ALL,fetch = javax.persistence.FetchType.LAZY)
    private List<Contacto> contactos = new ArrayList<Contacto>();

    public Organizacion(){
    }

    public Organizacion(Usuario usuario){
        this.usuario = usuario;
        }

    public Organizacion(Importador moduloImportador, Ubicacion ubicacion,String razonSocial,Tipo tipo,Clasificacion clasificacion) {
        this.ubicacion=ubicacion;
        this.razonSocial=razonSocial;
        this.tipo=tipo;
        this.clasificacion=clasificacion;
        this.moduloImportador = moduloImportador;
        this.municipioODepartamento=ubicacion.getMunicipalidad();
    }

    public Area darAltaArea(String nombreArea){
        Area nuevaArea = new Area(nombreArea, this );
        this.areas.add(nuevaArea);
        return nuevaArea;
    }

    public void agregarReporte(HChistorico HChistorico){
        this.HChistoricos.add(HChistorico);
    }

    public void calcularHC(){
        this.calcularHCConsumos();
        this.calcularHCViajes();
    }
    private void calcularHCConsumos(){
        actividadesCargadas.stream().forEach(e->e.calcularHC());
    }
    private void calcularHCViajes(){
        areas.stream().forEach(e->e.calcularHC());
    }

    public void cargarMediciones(String nombreArchivo){
        actividadesCargadas.addAll(moduloImportador.importarDatos(nombreArchivo,this));
    }

    public void agregarContacto(String telefono, String email){
        Contacto nuevoContacto = new Contacto(telefono, email,null);
        this.contactos.add(nuevoContacto);
    }

    public double getHCTotal() {
        double total = HChistoricos.stream().mapToDouble(rep -> rep.getHuellaDeCarbono()).sum();
        return total;
    }

    public List<HChistorico> getHChistoricos() {
        return HChistoricos;
    }

}