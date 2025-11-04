package pe.edu.upeu.asistencia.control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;


@Controller
public class MainguiController {

    @FXML
    private MenuItem menuItem1, menuItem2, menuItem3, menuItem4;
    @FXML
    private MenuBar menuBar;

    private ComboBox<String> comboBox=new ComboBox<>();
    private CustomMenuItem customMenuEstilo=new CustomMenuItem(comboBox);
    private Menu menuEstilo=new Menu("Theme");
    @FXML
    private TabPane tabPane;
    @FXML
    BorderPane bp;

    @Autowired
    private ApplicationContext context;
    
    @FXML
    public void initialize() {

        MenuItemListener miL=new MenuItemListener();
        menuItem1.setOnAction(miL::handle);
        menuItem2.setOnAction(miL::handle);
        menuItem3.setOnAction(miL::handle);
        menuItem4.setOnAction(miL::handle);
    }

    class MenuItemListener{
        Map<String, String[]> menuConfig=Map.of(
                            "menuItem1",new String[]{"/fxml/main_producto.fxml","Productos","T"},
                            "menuItem2",new String[]{"/fxml/login.fxml","Salir","C"},
                            "menuItem3",new String[]{"/fxml/main_cliente.fxml","Clientes","T"},
                            "menuItem4",new String[]{"/fxml/main_venta.fxml","Ventas","T"}
                                );

        public void handle(ActionEvent e) {
            String id= ( (MenuItem) e.getSource() ).getId();
            if(menuConfig.containsKey(id)){
                String[] mi=menuConfig.get(id);
                if(mi[2].equals("C")){
                    Platform.exit();
                    System.exit(0);
                }else{
                    abrirArchivoFXML(mi[0],mi[1]);
                }
            }
        }

        public void abrirArchivoFXML(String filename, String tittle){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(new ClassPathResource(filename).getURL());
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();

                ScrollPane scrollPane = new ScrollPane(root);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                Tab newTab = new  Tab(tittle, scrollPane);
                tabPane.getTabs().clear();
                tabPane.getTabs().add(newTab);

            }catch (IOException ex) {
                System.err.println("❌ Error cargando FXML: " + ex.getMessage());
                ex.printStackTrace();
                showErrorAlert("No se pudo cargar el módulo " + tittle);
            }
        }
    }

    public void abrirArchivoFXML(String filename, String tittle){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new ClassPathResource(filename).getURL());
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();

            ScrollPane scrollPane = new ScrollPane(root);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            Tab newTab = new  Tab(tittle, scrollPane);
            tabPane.getTabs().clear();
            tabPane.getTabs().add(newTab);

        }catch (IOException ex) {
            System.err.println("❌ Error cargando FXML: " + ex.getMessage());
            ex.printStackTrace();
            showErrorAlert("No se pudo cargar el módulo " + tittle);
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error al cargar módulo");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void salir() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void abrirProductos() {
        abrirArchivoFXML("/fxml/main_producto.fxml", "Gestión de Productos");
    }

    @FXML
    public void abrirClientes() {
        abrirArchivoFXML("/fxml/main_cliente.fxml", "Gestión de Clientes");
    }

    @FXML
    public void abrirVentas() {
        abrirArchivoFXML("/fxml/main_venta.fxml", "Gestión de Ventas");
    }
}
