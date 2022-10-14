package impacto_ambiental.controllers;

import impacto_ambiental.db.EntityManagerHelper;
import impacto_ambiental.models.entities.usuario.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginControllerOK {

    public ModelAndView pantallaDeLogin(Request request, Response response) {
        return new ModelAndView(null, "loginOK.hbs");
    }

    public Response login(Request request, Response response) {
        try {
            String query = "from "
                    + Usuario.class.getName()
                    + " WHERE usuario = '"
                    + request.queryParams("email")
                    + "' AND contrasenia='"
                    + request.queryParams("contrasenia")
                    + "'";

            Usuario email = (Usuario) EntityManagerHelper
                    .getEntityManager()
                    .createQuery(query)
                    .getSingleResult();

            if(email != null) {
                request.session(true);
                request.session().attribute("id", email.getId());
                response.redirect("/login/");
            }
            else {
                response.redirect("/login");
            }
        }
        catch (Exception ex) {
            response.redirect("/login");
        }
        return response;
    }

    public Response logout(Request request, Response response) {
        request.session().invalidate();
        response.redirect("/home");
        return response;
    }

    public ModelAndView prohibido(Request request, Response response) {
        return new ModelAndView(null, "prohibido.hbs");
    }
}