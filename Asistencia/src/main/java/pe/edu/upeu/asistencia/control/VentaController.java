package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.modelo.Venta;
import pe.edu.upeu.asistencia.modelo.Cliente;
import pe.edu.upeu.asistencia.modelo.Producto;
import pe.edu.upeu.asistencia.servicio.VentaServicioI;
import pe.edu.upeu.asistencia.servicio.ClienteServicioI;
import pe.edu.upeu.asistencia.servicio.ProductoServicioI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Controller
@SuppressWarnings("unchecked")
public class VentaController {

    @FXML
    TableView<Venta> tableView;
    
    TableColumn<Venta, String> idCol, numeroCol, fechaCol, clienteCol, productoCol, cantidadCol, precioCol, totalCol;
    TableColumn<Venta, Void> opcionCol;
    
    @Autowired
    VentaServicioI vs;
    
    @Autowired
    ClienteServicioI cs;
    
    @Autowired
    ProductoServicioI ps;
    
    @FXML
    TextField txtNumero, txtCliente, txtProducto, txtCantidad, txtPrecio;
    
    @FXML
    DatePicker dpFecha;
    
    @FXML
    Label lblTotal;
    
    ObservableList<Venta> ventas;
    int indexE = -1;
    private Cliente clienteSeleccionado = null;

    @FXML
    public void initialize() {
        System.out.println("Iniciando sistema de gestión de ventas...");
        definirNombresColumnas();
        listarVentas();
        actualizarTotalVentas();
        System.out.println("✅ Sistema de ventas listo para usar");
    }

    public void definirNombresColumnas() {
        idCol = new TableColumn("ID");
        idCol.setMinWidth(80);
        
        numeroCol = new TableColumn("Número");
        numeroCol.setMinWidth(80);
        
        fechaCol = new TableColumn("Fecha");
        fechaCol.setMinWidth(100);
        
        clienteCol = new TableColumn("Cliente");
        clienteCol.setMinWidth(120);
        
        productoCol = new TableColumn("Producto");
        productoCol.setMinWidth(120);
        
        cantidadCol = new TableColumn("Cantidad");
        cantidadCol.setMinWidth(80);
        
        precioCol = new TableColumn("Precio Unit.");
        precioCol.setMinWidth(100);
        
        totalCol = new TableColumn("Total");
        totalCol.setMinWidth(100);
        
        opcionCol = new TableColumn("Opciones");
        opcionCol.setMinWidth(150);
        
        tableView.getColumns().addAll(idCol, numeroCol, fechaCol, clienteCol, productoCol, cantidadCol, precioCol, totalCol, opcionCol);
    }

    public void agregarAccionBotones() {
        Callback<TableColumn<Venta, Void>, TableCell<Venta, Void>> cellFactory =
                param -> new TableCell<>() {
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");
                    
                    {
                        btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                        
                        btnEditar.setOnAction((event) -> {
                            Venta venta = getTableView().getItems().get(getIndex());
                            editarVenta(venta, getIndex());
                        });
                        
                        btnEliminar.setOnAction((event) -> {
                            if (confirmarEliminacion()) {
                                eliminarVenta(getIndex());
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
        alert.setHeaderText("¿Estás seguro de que quieres eliminar esta venta?");
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public void editarVenta(Venta v, int index) {
        txtNumero.setText(v.getNumeroVenta());
        dpFecha.setValue(v.getFecha().toLocalDate());
        txtCliente.setText(v.getCliente() != null ? v.getCliente().getNombre() : "");
        txtProducto.setText(v.getProducto() != null ? v.getProducto().getNombre() : "");
        txtCantidad.setText(String.valueOf(v.getCantidad()));
        txtPrecio.setText(String.valueOf(v.getPrecioUnitario()));
        indexE = index;
    }

    public void listarVentas() {
        tableView.getColumns().clear();
        
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        numeroCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroVenta()));
        fechaCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha().toString()));
        clienteCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getCliente() != null ? cellData.getValue().getCliente().getNombre() : "N/A"));
        productoCol.setCellValueFactory(cellData -> new SimpleStringProperty(
            cellData.getValue().getProducto() != null ? cellData.getValue().getProducto().getNombre() : "N/A"));
        cantidadCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidad())));
        precioCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty("$" + String.format("%.2f", cellData.getValue().getPrecioUnitario())));
        totalCol.setCellValueFactory(cellData -> 
                new SimpleStringProperty("$" + String.format("%.2f", cellData.getValue().getTotal())));
        
        agregarAccionBotones();
        
        ventas = FXCollections.observableArrayList(vs.findAll());
        
        tableView.getColumns().addAll(idCol, numeroCol, fechaCol, clienteCol, productoCol, cantidadCol, precioCol, totalCol, opcionCol);
        
        tableView.setItems(ventas);
        System.out.println("Tabla de ventas actualizada con " + ventas.size() + " ventas");
    }

    @FXML
    public void crearVenta() {
        try {
            if (!validarCampos()) {
                return;
            }
            
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            double total = cantidad * precio;
            
            if (cantidad <= 0 || precio <= 0) {
                mostrarMensaje("La cantidad y precio deben ser mayores a 0", Alert.AlertType.WARNING);
                return;
            }
            
            Venta venta = new Venta();
            
            if (indexE == -1) {
                System.out.println("[v0] 🆔 Creando nueva venta");
            } else {
                Venta ventaActual = ventas.get(indexE);
                venta.setId(ventaActual.getId());
            }
            
            venta.setNumeroVenta(txtNumero.getText().trim());
            venta.setFecha(LocalDateTime.now());
            
            if (clienteSeleccionado != null) {
                venta.setCliente(clienteSeleccionado);
            }
            
            venta.setCantidad(cantidad);
            venta.setPrecioUnitario(precio);
            venta.setTotal(total);
            
            if (indexE == -1) {
                vs.save(venta);
                mostrarMensaje("✅ Venta registrada exitosamente\n\nNúmero: " + venta.getNumeroVenta() + 
                              "\nTotal: $" + String.format("%.2f", total), Alert.AlertType.INFORMATION);
            } else {
                Venta ventaActual = ventas.get(indexE);
                vs.update(ventaActual.getId(), venta);
                indexE = -1;
                mostrarMensaje("Venta actualizada exitosamente", Alert.AlertType.INFORMATION);
            }
            
            limpiarFormulario();
            listarVentas();
            actualizarTotalVentas();
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Ingrese números válidos", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (txtNumero.getText().trim().isEmpty()) {
            mostrarMensaje("El número de venta es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtCliente.getText().trim().isEmpty()) {
            mostrarMensaje("El cliente es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtProducto.getText().trim().isEmpty()) {
            mostrarMensaje("El producto es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtCantidad.getText().trim().isEmpty()) {
            mostrarMensaje("La cantidad es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarMensaje("El precio es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Sistema de Ventas");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void limpiarFormulario() {
        txtNumero.setText("");
        txtCliente.setText("");
        txtProducto.setText("");
        txtCantidad.setText("");
        txtPrecio.setText("");
        dpFecha.setValue(LocalDate.now());
        clienteSeleccionado = null;
    }

    public void eliminarVenta(int index) {
        if (index >= 0 && index < ventas.size()) {
            Venta venta = ventas.get(index);
            vs.delete(venta.getId());
            listarVentas();
            actualizarTotalVentas();
            mostrarMensaje("Venta eliminada exitosamente", Alert.AlertType.INFORMATION);
        }
    }
    
    private void actualizarTotalVentas() {
        double total = vs.calcularTotalVentas();
        lblTotal.setText("Total de Ventas: $" + String.format("%.2f", total));
    }
}
