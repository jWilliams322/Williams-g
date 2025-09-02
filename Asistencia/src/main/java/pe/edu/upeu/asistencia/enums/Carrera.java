package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public enum Carrera {
    SISTEMAS(Facultad.FIA, "Ing. Sistemas"),
    CIVIL(Facultad.FIA, "Ing. Civil"),
    AMBIENTAL(Facultad.FIA, "Ing. Ambiental"),

    ADMINISTRACION(Facultad.FCE, "Administracion"),
    NUTRICION(Facultad.FCS, "Nutricion"),
    Educacion(Facultad.FACIHED, "Educacion"),;

    private Facultad facultad;
    private String descripcion;
}
