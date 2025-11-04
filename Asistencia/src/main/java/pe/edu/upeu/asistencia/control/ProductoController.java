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
import pe.edu.upeu.asistencia.modelo.Producto;
import pe.edu.upeu.asistencia.servicio.ProductoServicioI;

@Controller
public class ProductoController {

    @FXML
    TableView<Producto> tableView;

    ObservableList<Producto> productos; // Lista que se actualiza automáticamente en la tabla
    TableColumn<Producto, String> codigoCol, nombreCol, descripcionCol, marcaCol; // Columnas de texto
    TableColumn<Producto, String> precioCol, stockCol, costoCol; // Columnas de números (como String para mostrar)
    TableColumn<Producto, Void> opcionCol; // Columna para botones de editar/eliminar

    @Autowired
    ProductoServicioI ps; // Inyección de dependencia - Spring nos da el servicio automáticamente

    @FXML 
    TextField txtCodigo, txtNombre, txtDescripcion, txtMarca, txtTalla, txtColor, txtTipo, txtPrecio, txtStock, txtCosto;

    @FXML
    TextField txtBuscar; // Campo para buscar productos

    @FXML
    ComboBox<String> cmbFiltro; // Filtro por categoría

    int indexE = -1; // -1 significa que no estamos editando, otro número es el índice del elemento a editar

    @FXML
    public void initialize() {
        System.out.println("Iniciando sistema de gestión de productos...");

        definirNombresColumnas();

        listarProductos();

        cargarCategorias();
        
        System.out.println("✅ Sistema de productos listo para usar");
    }

    public void definirNombresColumnas() {
      codigoCol = new TableColumn("Código");
        codigoCol.setMinWidth(80);
        
        nombreCol = new TableColumn("Nombre");
        nombreCol.setMinWidth(150);
        
        descripcionCol = new TableColumn("Descripción");
        descripcionCol.setMinWidth(200);
        
        marcaCol = new TableColumn("Marca");
        marcaCol.setMinWidth(120);
        
        precioCol = new TableColumn("Precio");
        precioCol.setMinWidth(80);
        
        stockCol = new TableColumn("Stock");
        stockCol.setMinWidth(60);
        
        costoCol = new TableColumn("Costo");
        costoCol.setMinWidth(80);
        
        opcionCol = new TableColumn("Opciones");
        opcionCol.setMinWidth(150);

        tableView.getColumns().addAll(codigoCol, nombreCol, descripcionCol, marcaCol, 
                                     precioCol, stockCol, costoCol, opcionCol);
        
        System.out.println("📋 Columnas de la tabla configuradas");
    }

    public void agregarAccionBotones() {
        Callback<TableColumn<Producto, Void>, TableCell<Producto, Void>> cellFactory =
                param -> new TableCell<>() {
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");

                    {
                        btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                        btnEliminar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                        
                        btnEditar.setOnAction((event) -> {
                            Producto producto = getTableView().getItems().get(getIndex());
                            editarProducto(producto, getIndex());
                        });
                        
                        btnEliminar.setOnAction((event) -> {
                            if (confirmarEliminacion()) {
                                eliminarProducto(getIndex());
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // Si la fila está vacía, no mostrar nada
                        } else {
                            HBox hBox = new HBox(btnEditar, btnEliminar);
                            hBox.setSpacing(5); // Espacio entre botones
                            setGraphic(hBox); // Mostrar los botones
                        }
                    }
                };
        opcionCol.setCellFactory(cellFactory);
    }


    private boolean confirmarEliminacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este producto?");
        alert.setContentText("Esta acción no se puede deshacer.");
        
        return alert.showAndWait().get() == ButtonType.OK;
    }


    public void editarProducto(Producto p, int index) {
        System.out.println("Editando zapatilla: " + p.getNombre());
        
        txtCodigo.setText(p.getCodigo());
        txtNombre.setText(p.getNombre());
        txtDescripcion.setText(p.getDescripcion());
        txtMarca.setText(p.getMarca());
        txtTalla.setText(p.getTalla());
        txtColor.setText(p.getColor());
        txtTipo.setText(p.getTipo());
        txtPrecio.setText(String.valueOf(p.getPrecio()));
        txtStock.setText(String.valueOf(p.getStock()));
        txtCosto.setText(String.valueOf(p.getCosto()));
        
        indexE = index;
    }

    public void listarProductos() {
        tableView.getColumns().clear();
        
        codigoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCodigo()));
        nombreCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        descripcionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescripcion()));
        marcaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
        
        precioCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty("$" + String.format("%.2f", cellData.getValue().getPrecio())));
        stockCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
        costoCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty("$" + String.format("%.2f", cellData.getValue().getCosto())));
        
        agregarAccionBotones();

        productos = FXCollections.observableArrayList(ps.findAll());
        
        tableView.getColumns().addAll(codigoCol, nombreCol, descripcionCol, marcaCol, 
                                     precioCol, stockCol, costoCol, opcionCol);
        
        tableView.setItems(productos);
        
        System.out.println("📊 Tabla actualizada con " + productos.size() + " zapatillas");
    }

    private boolean validarCodigoDuplicado(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo) && indexE == -1) {
                mostrarMensaje("Error: Ya existe un producto con el código " + codigo, Alert.AlertType.ERROR);
                return false;
            }
        }
        return true;
    }

    private boolean validarLongitudCampos() {
        if (txtCodigo.getText().length() < 2 || txtCodigo.getText().length() > 10) {
            mostrarMensaje("El código debe tener entre 2 y 10 caracteres", Alert.AlertType.WARNING);
            return false;
        }
        if (txtNombre.getText().length() < 3 || txtNombre.getText().length() > 100) {
            mostrarMensaje("El nombre debe tener entre 3 y 100 caracteres", Alert.AlertType.WARNING);
            return false;
        }
        if (txtMarca.getText().length() < 2 || txtMarca.getText().length() > 50) {
            mostrarMensaje("La marca debe tener entre 2 y 50 caracteres", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }


    private boolean validarFormatoPrecio(String precio) {
        if (!precio.matches("^\\d+(\\.\\d{1,2})?$")) {
            mostrarMensaje("El precio debe tener máximo 2 decimales (ej: 25.50)", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private boolean validarStockMinimo(int stock) {
        if (stock <= 0) {
            mostrarMensaje("El stock debe ser mayor a 0", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    public void buscarProducto() {
        String textoBusqueda = txtBuscar.getText().toLowerCase().trim();
        
        if (textoBusqueda.isEmpty()) {
            listarProductos();
            return;
        }
        
        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();
        
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(textoBusqueda) ||
                p.getCodigo().toLowerCase().contains(textoBusqueda)) {
                productosFiltrados.add(p);
            }
        }
        
        tableView.setItems(productosFiltrados);
        System.out.println("🔍 Búsqueda realizada: " + productosFiltrados.size() + " productos encontrados");
    }

    @FXML
    public void filtrarPorCategoria() {
        String categoriaSeleccionada = cmbFiltro.getValue();
        
        if (categoriaSeleccionada == null || categoriaSeleccionada.equals("Todas")) {
            listarProductos();
            return;
        }
        
        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();
        
        for (Producto p : productos) {
            if (p.getTipo().equals(categoriaSeleccionada)) {
                productosFiltrados.add(p);
            }
        }
        
        tableView.setItems(productosFiltrados);
        System.out.println("Filtro aplicado: " + productosFiltrados.size() + " zapatillas de tipo " + categoriaSeleccionada);
    }


    private void cargarCategorias() {
        ObservableList<String> categorias = FXCollections.observableArrayList("Todas");
        
        for (Producto p : productos) {
            String tipo = p.getTipo();
            if (!categorias.contains(tipo)) {
                categorias.add(tipo);
            }
        }
        
        cmbFiltro.setItems(categorias);
        cmbFiltro.setValue("Todas");
    }

    @FXML
    public void crearProducto() {
        try {
            if (!validarCampos()) {
                return;
            }

            if (!validarLongitudCampos()) {
                return;
            }

            if (!validarCodigoDuplicado(txtCodigo.getText().trim())) {
                return;
            }
            
            double precio, costo;
            int stock;
            
            try {
                if (!validarFormatoPrecio(txtPrecio.getText().trim())) {
                    return;
                }
                if (!validarFormatoPrecio(txtCosto.getText().trim())) {
                    return;
                }
                
                precio = Double.parseDouble(txtPrecio.getText().trim());
                stock = Integer.parseInt(txtStock.getText().trim());
                costo = Double.parseDouble(txtCosto.getText().trim());

                if (precio < 0 || costo < 0) {
                    mostrarMensaje("Los precios no pueden ser negativos", Alert.AlertType.WARNING);
                    return;
                }

                if (!validarStockMinimo(stock)) {
                    return;
                }

                if (precio <= costo) {
                    mostrarMensaje("El precio de venta debe ser mayor al costo", Alert.AlertType.WARNING);
                    return;
                }
                
            } catch (NumberFormatException e) {
                mostrarMensaje("Error: Ingrese números válidos en precio, stock y costo", Alert.AlertType.ERROR);
                return;
            }

            Producto producto = new Producto();
            
            producto.setCodigo(txtCodigo.getText().trim());
            producto.setNombre(txtNombre.getText().trim());
            producto.setDescripcion(txtDescripcion.getText().trim());
            producto.setMarca(txtMarca.getText().trim());
            producto.setTalla(txtTalla.getText().trim());
            producto.setColor(txtColor.getText().trim());
            producto.setTipo(txtTipo.getText().trim());
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setCosto(costo);

            if (indexE == -1) {
                ps.save(producto);
                mostrarMensaje("✅ Zapatilla creada exitosamente\n\nCódigo: " + producto.getCodigo() + 
                              "\nNombre: " + producto.getNombre() + 
                              "\nMarca: " + producto.getMarca() +
                              "\nPrecio: $" + String.format("%.2f", precio), Alert.AlertType.INFORMATION);
            } else {
                Producto productoActual = productos.get(indexE);
                producto.setId(productoActual.getId());
                ps.update(productoActual.getId(), producto);
                indexE = -1;
                mostrarMensaje("Zapatilla actualizada exitosamente\n\nNombre: " + producto.getNombre() +
                              "\nNuevo Precio: $" + String.format("%.2f", precio), Alert.AlertType.INFORMATION);
            }

            limpiarFormulario();
            listarProductos();
            cargarCategorias();
            
        } catch (Exception e) {
            mostrarMensaje("Error inesperado: " + e.getMessage(), Alert.AlertType.ERROR);
            System.err.println("❌ Error en crearProducto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            mostrarMensaje("El código de la zapatilla es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensaje("El nombre de la zapatilla es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtMarca.getText().trim().isEmpty()) {
            mostrarMensaje("La marca de la zapatilla es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtTalla.getText().trim().isEmpty()) {
            mostrarMensaje("La talla de la zapatilla es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (txtPrecio.getText().trim().isEmpty()) {
            mostrarMensaje("El precio de la zapatilla es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtStock.getText().trim().isEmpty()) {
            mostrarMensaje("El stock de la zapatilla es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (txtCosto.getText().trim().isEmpty()) {
            mostrarMensaje("El costo de la zapatilla es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Sistema de Productos");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtMarca.setText("");
        txtTalla.setText("");
        txtColor.setText("");
        txtTipo.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtCosto.setText("");
        
        System.out.println("Formulario limpiado");
    }

    public void eliminarProducto(int index) {
        if (index < 0 || index >= productos.size()) {
            mostrarMensaje("Error: Producto no válido para eliminar", Alert.AlertType.ERROR);
            return;
        }
        
        Producto producto = productos.get(index);
        ps.delete(producto.getId());
        listarProductos();
        mostrarMensaje("Producto eliminado exitosamente", Alert.AlertType.INFORMATION);
    }
}
