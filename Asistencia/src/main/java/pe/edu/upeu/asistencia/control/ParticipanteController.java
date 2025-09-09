package pe.edu.upeu.asistencia.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.servicio.ParticipanteServicioImp;

@Controller
public class ParticipanteController {

    private final ParticipanteServicioImp participanteServicioImp;
    @FXML
    private ComboBox<Carrera> cbxCarrera;

    @FXML
    private ComboBox<TipoParticipante> cbxTipoParticipante;

    @FXML TableView<Participante> tableView;
    ObservableList<Participante> Participante;
    TableColumn<Participante,String> dniCol, nombreCol, apellidoCol, carreraCol, tipoPartCol;

    public ParticipanteController(ParticipanteServicioImp participanteServicioImp) {
        this.participanteServicioImp = participanteServicioImp;
    }

    @FXML
    public void initialize(){
        cbxCarrera.getItems().setAll(Carrera.values());
        cbxTipoParticipante.getItems().setAll(TipoParticipante.values());
        definirNombrseColumnas();
    }

    public void definirNombrseColumnas(){
        dniCol = new TableColumn("DNI");
        nombreCol = new TableColumn("Nombre");
        apellidoCol = new TableColumn("Apellido");
        apellidoCol.setMinWidth(200);
        carreraCol = new TableColumn("Carrera");
        tipoPartCol = new TableColumn("Tipo Participante");
        tipoPartCol.setMinWidth(160);
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoPartCol);
    }
    public void listarparticipante(){
        dniCol.setCellValueFactory(cellData ->
                cellData.getValue().getDni());
        Object participants = FXCollections.observableArrayList(ps.finadll());
        tableView.setItems(Participante);
    }
}
