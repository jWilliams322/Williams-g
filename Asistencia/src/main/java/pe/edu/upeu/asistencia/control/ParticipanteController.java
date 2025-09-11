    package pe.edu.upeu.asistencia.control;

    import javafx.beans.property.SimpleStringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.layout.HBox;
    import javafx.util.Callback;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import pe.edu.upeu.asistencia.enums.Carrera;
    import pe.edu.upeu.asistencia.enums.TipoParticipante;
    import pe.edu.upeu.asistencia.modelo.Participante;
    import pe.edu.upeu.asistencia.servicio.ParticipanteServicioI;
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
        TableColumn<Participante, Void> opcionCol;

        public ParticipanteController(ParticipanteServicioImp participanteServicioImp) {
            this.participanteServicioImp = participanteServicioImp;
        }
        @Autowired
        ParticipanteServicioI ps;
        @FXML TextField txtDNI, txtNombres, txtApellidos;
        int indexE =-1;
        @FXML
        public void initialize(){
            cbxCarrera.getItems().setAll(Carrera.values());
            cbxTipoParticipante.getItems().setAll(TipoParticipante.values());
            definirNombrseColumnas();
            listarParticipante();
        }
        public void definirNombrseColumnas(){
            dniCol = new TableColumn("DNI");
            nombreCol = new TableColumn("Nombre");
            apellidoCol = new TableColumn("Apellido");
            apellidoCol.setMinWidth(180);
            carreraCol = new TableColumn("Carrera");
            tipoPartCol = new TableColumn("Tipo Participante");
            tipoPartCol.setMinWidth(160);
            opcionCol = new TableColumn("Opcion");
            opcionCol.setMinWidth(200);
            tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoPartCol, opcionCol);
        }

        public void agregarAccionBotones(){
            Callback<TableColumn<Participante, Void>, TableCell<Participante,Void>> cellFactory =
                    param-> new TableCell<Participante,Void>() {
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");
                        {
                            btnEditar.setOnAction((event)->{
                                Participante participante = getTableView().getItems().get(getIndex());
                                editarParticipante(participante, getIndex());
                            });
                            btnEliminar.setOnAction((event)->{
                              eliminarParticipante(getIndex());
                            });
                        }
                    @Override
                    protected void updateItem(Void item, boolean empy){
                            super.updateItem(item, empy);
                            if(empy){
                                setGraphic(null);
                            }else{
                                HBox hBox = new HBox(btnEditar, btnEliminar);
                                hBox.setSpacing(10);
                                setGraphic(hBox);
                            }
                    }

                    };
            opcionCol.setCellFactory(cellFactory);
        }
        public void editarParticipante(Participante p, int index){
            txtDNI.setText(p.getDni().getValue());
            txtNombres.setText(p.getNombre().getValue());
            txtApellidos.setText(p.getApellido().getValue());
            cbxCarrera.getSelectionModel().select(p.getCarrera());
            cbxTipoParticipante.getSelectionModel().select(p.getTipoParticipante());
            indexE = index;
        }

        public void listarParticipante(){
            dniCol.setCellValueFactory(cellData ->
                    cellData.getValue().getDni());
            nombreCol.setCellValueFactory(cellData ->
                    cellData.getValue().getNombre());
            apellidoCol.setCellValueFactory(cellData ->
                    cellData.getValue().getApellido());
            carreraCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getCarrera().name()));
            tipoPartCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getTipoParticipante().name()));
            agregarAccionBotones();

            Participante = FXCollections.observableArrayList(ps.findAll());
            tableView.setItems(Participante);
        }

        @FXML
        public void crearParticipante(){
            Participante participante = new Participante();
            participante.setDni(new SimpleStringProperty(txtDNI.getText()));
            participante.setNombre(new SimpleStringProperty(txtNombres.getText()));
            participante.setApellido(new SimpleStringProperty(txtApellidos.getText()));
            participante.setCarrera(cbxCarrera.getValue());
            participante.setTipoParticipante(cbxTipoParticipante.getValue());
            if(indexE == -1){
                ps.save(participante);
            }else{
                ps.update(participante, indexE);
                indexE=-1;
            }
            limpiarFormulario();
            listarParticipante();
        }

        public void limpiarFormulario(){
            txtDNI.setText("");
            txtNombres.setText("");
            txtApellidos.setText("");
            cbxCarrera.getSelectionModel().clearSelection();

        }



        public void eliminarParticipante(int index){
            ps.delete(index);
            listarParticipante();

        }
    }
