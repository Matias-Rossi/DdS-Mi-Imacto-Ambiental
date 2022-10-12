package domain.servicios.twilio;

import proservices.models.entities.importadorExcel.ApachePOI;
import proservices.models.entities.notificaciones.Contacto;
import proservices.models.entities.notificaciones.Difusor;
import proservices.models.entities.notificaciones.GestorNotificaciones;
import proservices.models.entities.notificaciones.Notificacion;
import proservices.models.entities.perfil.Clasificacion;
import proservices.models.entities.perfil.Organizacion;
import proservices.models.entities.perfil.Tipo;
import proservices.models.entities.servicios.twilio.ServicioTwilio;
import proservices.models.entities.ubicacion.MunicipiosODepartamentos;
import proservices.models.entities.ubicacion.NombreProvincia;
import proservices.models.entities.ubicacion.Provincia;
import proservices.models.entities.ubicacion.Ubicacion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestServicioTwilio {

  @Test
  public void testDifusor() {
    //ATENCIÓN: este test incurre un gasto en la cuenta vinculada de Twilio
    GestorNotificaciones gestorNotificaciones = new ServicioTwilio();
    Difusor difusor = new Difusor(gestorNotificaciones);
    MunicipiosODepartamentos moron = new MunicipiosODepartamentos(new Provincia(NombreProvincia.Buenos_Aires), "Morón");

    Organizacion org = new Organizacion(
            new ApachePOI(),
            new Ubicacion(
                    moron,
                    "b",
                    "c",
                    "d",
                    1
            ),
            "apache inc",
            Tipo.EMPRESA,
            new Clasificacion("dasdsa")
    );
    org.agregarContacto("+" + obtenerNumeroTelefonico(), obtenerEmail());

    //difusor.agregarOrganizacion(org); //DESCOMENTAR PARA FUNCIONALIDAD

    //difusor.difundirRecomendaciones(); //DESCOMENTAR PARA FUNCIONALIDAD

  }

  @Test
  public void testWhatsapp() {
    //ATENCIÓN: este test incurre un gasto en la cuenta vinculada de Twilio
    String tel = obtenerNumeroTelefonico();
    ServicioTwilio servicioTwilio = new ServicioTwilio();
    Contacto contacto = new Contacto("+" + tel, null,null);
    Notificacion notificacion = new Notificacion("Test asunto", "Test contenido");
    //servicioTwilio.enviarNotificacion(contacto, notificacion); //DESCOMENTAR PARA FUNCIONALIDAD
  }

  @Test
  public void testEmail() {
    //ATENCIÓN: este test incurre un gasto en la cuenta vinculada de SendGrid
    String email = obtenerEmail();
    ServicioTwilio servicioTwilio = new ServicioTwilio();
    Contacto contacto = new Contacto(null, email,null);
    Notificacion notificacion = new Notificacion("Mi Impacto Ambiental - Actualización de Guía de Recomendaciones", "<Enlace al contenido>");
    Boolean exito = true; //servicioTwilio.enviarNotificacion(contacto, notificacion); //DESCOMENTAR PARA FUNCIONALIDAD
    assert(exito);
  }

  public String obtenerNumeroTelefonico(){
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("tokens.properties");
    try {
      prop.load(stream);
      return prop.getProperty("numeroCelularTests");
    } catch(IOException e) {
      return "";
    }
  }

  public String obtenerEmail(){
    Properties prop = new Properties();
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    InputStream stream = loader.getResourceAsStream("tokens.properties");
    try {
      prop.load(stream);
      return prop.getProperty("emailTestsTo");
    } catch(IOException e) {
      return "";
    }
  }

}
