package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteRepository {
   public List<Participante> participantes =  new ArrayList<Participante>();

    public List<Participante> finAll(){
        participantes.add(new Participante(
                new SimpleStringProperty("453127841"),
                new SimpleStringProperty("Juan"),
                new SimpleStringProperty("Apaza"),
                Carrera.SISTEMAS,
                TipoParticipante.ASISTENTE,
                new SimpleBooleanProperty(true)
                )
        );

        return participantes;
    }
}