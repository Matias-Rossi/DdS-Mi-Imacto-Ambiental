package impacto_ambiental.controllers;



import impacto_ambiental.models.entities.perfil.*;
import impacto_ambiental.models.entities.usuario.Usuario;
import impacto_ambiental.models.repositorios.RepositorioAreas;
import impacto_ambiental.models.repositorios.RepositorioMiembros;
import impacto_ambiental.models.repositorios.RepositorioOrganizaciones;
import impacto_ambiental.models.repositorios.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;


import java.util.HashMap;
import java.util.List;

public class OrganizacionController {
    private RepositorioOrganizaciones repositorioOrganizaciones = new RepositorioOrganizaciones();
    private RepositorioMiembros repositorioMiembros = new RepositorioMiembros();
    private RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
    private RepositorioAreas repositorioAreas = new RepositorioAreas();
    private RepositorioSolicitudes repositorioSolicitudes =  new RepositorioSolicitudes();

    public ModelAndView mostrarTodas(Request request, Response response){

        Miembro unMiembro = repositorioMiembros.buscarPorIDUsuario(Integer.valueOf(request.session().attribute("id"));

        List<Solicitud> solicitudes = repositorioSolicitudes.buscarSolicitudesAceptadasPorIDMiembro(unMiembro.getId());

        solicitudes.stream().map(sol->sol.)
        

        List<Organizacion> organizacionesDeMiembro =   unMiembro.getSolicitudes().stream()
                .filter(unaSolicitud-> unaSolicitud.getEstado() == SolicitudEstado.ACEPTADA )
                .map(unaSolicitud-> unaSolicitud.getArea().getOrganizacion() ).toList() ;



        return new ModelAndView(new HashMap<String, Object>(){{
            put("organizaciones",organizacionesDeMiembro );
        }}, "oraganizaciones.hbs"); //TODO Implementar este .hbs, ya existe el .html

    }

            //Spark.get("/organiazciones/vincularse", organizacionController::pantallaVincularse, engine);

    public ModelAndView pantallaVincularse(Request request, Response response){

        List<Area> todasLasAreas = this.repositorioAreas.buscarTodos() ;


        return new ModelAndView(new HashMap<String, Object>(){{
                    put("areas",todasLasAreas );
        }}, "oraganizaciones.hbs");
    }
    //Spark.post ("/organiazciones/vincularse/:id", organizacionController::vincularseOrganizacion, engine);
    public Response vincularseOrganizacion(Request request, Response response){
        //RECUPERO EL AREA
        Integer idArea = Integer.valueOf(request.queryParams("area"));
        Area area = this.repositorioAreas.buscar(idArea);
        //RECUPERO EL MIEMBRO
        Usuario unUsuario = repositorioUsuarios.buscar(Integer.valueOf(request.session().attribute("id")) );
        String queryParaBuscarDedeOtroId = "SELECT e FROM " + unUsuario.getClass() + " WHERE id_="+unUsuario.getId();
        Miembro unMiembro = repositorioMiembros.buscar(queryParaBuscarDedeOtroId);

        //LO DAMOS DE ALTA EN AREA Y ACTUALIZAMOS BD
        unMiembro.darseAltaEnOrganizacion(area);
        repositorioMiembros.actualizar(unMiembro);

        response.redirect("/organiazciones");
        return response;
    }

    //TODO como hacemos para desvincularnos sin romper, por que si cambiamos la solicitud a rechazado perdemos los hc de los trayectos anteriores
    //Spark.post("/organiazciones/:id/desvincularse", organizacionController::desvincularse, engine);
    public Response desvincularseOrganizacion(Request request, Response response){
        //RECUPERO EL AREA
        Integer idArea = Integer.valueOf(request.queryParams("area"));
        Area area = this.repositorioAreas.buscar(idArea);
        //RECUPERO EL MIEMBRO
        Usuario unUsuario = repositorioUsuarios.buscar(Integer.valueOf(request.session().attribute("id")) );
        String queryParaBuscarDedeOtroId = "SELECT e FROM " + unUsuario.getClass() + " WHERE id_="+unUsuario.getId();
        Miembro unMiembro = repositorioMiembros.buscar(queryParaBuscarDedeOtroId);

        //LO DESVINCULAMES
        area.desvincularMiembro(unMiembro);

        response.redirect("/organiazciones");
        return response;
    }

}