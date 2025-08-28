package pe.edu.upeu.asistencia.modelo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class Estudiante {
    private StringProperty Nombre;
    private BooleanProperty Estado;

}
