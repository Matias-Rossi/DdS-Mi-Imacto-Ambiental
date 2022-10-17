package impacto_ambiental.models.entities.perfil;

public enum TipoDocumento {DNI,
    LIBRETA_CIVICA,
    LIBERTA_ENROLAMIENTO,
    CEDULA;
    public TipoDocumento toEnum(String tipo){
        switch (tipo){
            case "DNI":
                return DNI;
            case "LIBRETA_CIVICA":
                return LIBRETA_CIVICA;
            case "LIBERTA_ENROLAMIENTO":
                return LIBERTA_ENROLAMIENTO;
            case "CEDULA":
                return CEDULA;
            default:
                return null;
        }
    }
}

