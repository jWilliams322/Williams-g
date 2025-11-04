package pe.edu.upeu.asistencia.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.modelo.Cliente;
import pe.edu.upeu.asistencia.servicio.ClienteServicioI;

@Controller
public class ClienteController {

    @FXML
    TableView<Cliente> tableView;
    
    TableColumn<Cliente, String> idCol, nombreCol, cedulaCol, emailCol, telefonoCol, direccionCol, ciudadCol;
    TableColumn<Cliente, Void> opcionCol;
    
    @Autowired
    ClienteServicioI cs;
    
    @FXML
    TextField txtId, txtNombre, txtCedula, txtEmail, txtTelefono, txtDireccion, txtCiudad;
    
    ObservableList<Cliente> clientes;
    int indexE = -1;

    @FXML
    public void initialize() {
        System.out.println("🚀 Iniciando sistema de gestión de clientes...");
        definirNombresColumnas();
        listarClientes();
        System.out.println("✅ Sistema de clientes listo para usar");
    }

    public void definirNombresColumnas() {
        idCol = new TableColumn("ID");
        idCol.setMinWidth(60);
        
        nombreCol = new TableColumn("Nombre");
        nombreCol.setMinWidth(120);
        
        cedulaCol = new TableColumn("Cédula");
        cedulaCol.setMinWidth(100);
        
        emailCol = new TableColumn("Email");
        emailCol.setMinWidth(150);
        
        telefonoCol = new TableColumn("Teléfono");
        telefonoCol.setMinWidth(100);
        
        direccionCol = new TableColumn("Dirección");
        direccionCol.setMinWidth(150);
        
        ciudadCol = new TableColumn("Ciudad");
        ciudadCol.setMinWidth(100);
        
        opcionCol = new TableColumn("Opciones");
        opcionCol.setMinWidth(150);
        
        tableView.getColumns().addAll(idCol, nombreCol, cedulaCol, emailCol, telefonoCol, direccionCol, ciudadCol, opcionCol);
    }

    public void agregarAccionBotones() {
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellFactory =
                param -> new TableCell<>() {
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");
                    
                    {
                        btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                        
                        btnEditar.setOnAction((event) -> {
                            Cliente cliente = getTableView().getItems().get(getIndex());
                            editarCliente(cliente, getIndex());
                        });
                        
                        btnEliminar.setOnAction((event) -> {
                            if (confirmarEliminacion()) {
                                eliminarCliente(getIndex());
                            }
                        });
                    }
                    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hBox = new HBox(btnEditar, btnEliminar);
                            hBox.setSpacing(5);
                            setGraphic(hBox);
                        }
                    }
                };
        opcionCol.setCellFactory(cellFactory);
    }

    private boolean confirmarEliminacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este cliente?");
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public void editarCliente(Cliente c, int index) {
        txtId.setText(String.valueOf(c.getId()));
        txtNombre.setText(c.getNombre());
        txtCedula.setText(c.getCedula());
        txtEmail.setText(c.getEmail());
        txtTelefono.setText(c.getTelefono());
        txtDireccion.setText(c.getDireccion());
        txtCiudad.setText(c.getCiudad());
        indexE = index;
    }

    public void listarClientes() {
        tableView.getColumns().clear();
        
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        nombreCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        cedulaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCedula()));
        emailCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        telefonoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefono()));
        direccionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDireccion()));
        ciudadCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCiudad()));
        
        agregarAccionBotones();
        
        clientes = FXCollections.observableArrayList(cs.findAll());
        
        tableView.getColumns().addAll(idCol, nombreCol, cedulaCol, emailCol, telefonoCol, direccionCol, ciudadCol, opcionCol);
        
        tableView.setItems(clientes);
        System.out.println("📊 Tabla de clientes actualizada con " + clientes.size() + " clientes");
    }

    @FXML
    public void buscarClientePorDNI() {
        String dni = txtCedula.getText().trim();
        
        if (dni.isEmpty()) {
            listarClientes();
            return;
        }
        
        ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();
        
        for (Cliente c : clientes) {
            if (c.getCedula().equals(dni)) {
                clientesFiltrados.add(c);
                System.out.println("✅ Cliente encontrado: " + c.getNombre() + " (DNI: " + dni + ")");
            }
        }
        
        if (clientesFiltrados.isEmpty()) {
            mostrarMensaje("No se encontró cliente con DNI: " + dni, Alert.AlertType.WARNING);
            listarClientes();
        } else {
            tableView.setItems(clientesFiltrados);
        }
    }

    @FXML
    public void crearCliente() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            if (indexE == -1 && cs.findByCedula(txtCedula.getText().trim()).isPresent()) {
                mostrarMensaje("Error: Ya existe un cliente con esta cédula", Alert.AlertType.ERROR);
                return;
            }
            
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText().trim());
            cliente.setCedula(txtCedula.getText().trim());
            cliente.setEmail(txtEmail.getText().trim());
            cliente.setTelefono(txtTelefono.getText().trim());
            cliente.setDireccion(txtDireccion.getText().trim());
            cliente.setCiudad(txtCiudad.getText().trim());
            
            if (indexE == -1) {
                cs.save(cliente);
                mostrarMensaje("Cliente creado exitosamente", Alert.AlertType.INFORMATION);
            } else {
                Cliente clienteActual = clientes.get(indexE);
                cliente.setId(clienteActual.getId());
                cs.update(clienteActual.getId(), cliente);
                indexE = -1;
                mostrarMensaje("Cliente actualizado exitosamente", Alert.AlertType.INFORMATION);
            }
            
            limpiarFormulario();
            listarClientes();
            
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarMensaje("La cédula es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            mostrarMensaje("El email es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarMensaje("El teléfono es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Sistema de Clientes");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtCedula.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
    }

    public void eliminarCliente(int index) {
        if (index >= 0 && index < clientes.size()) {
            Cliente cliente = clientes.get(index);
            cs.delete(cliente.getId());
            listarClientes();
            mostrarMensaje("Cliente eliminado exitosamente", Alert.AlertType.INFORMATION);
        }
    }
}
