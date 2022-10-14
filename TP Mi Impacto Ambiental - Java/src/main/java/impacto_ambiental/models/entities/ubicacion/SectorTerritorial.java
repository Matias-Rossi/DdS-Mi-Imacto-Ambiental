package impacto_ambiental.models.entities.ubicacion;

import impacto_ambiental.models.entities.EntidadPersistente;
import impacto_ambiental.models.entities.usuario.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SectorTerritorial extends EntidadPersistente {

    @Column(name = "perro")
    String perro;

    @OneToMany(mappedBy = "sectorTerritorial", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)

    private List<Usuario> solicitudes=new ArrayList<>();

    public void solicitarUnirse(Usuario usuario){
        solicitudes.add(usuario);
    }

    public void respuestaSolicitud(Usuario usuario){
        solicitudes.remove(usuario);
        usuario.agregarSector(this);
    }

}