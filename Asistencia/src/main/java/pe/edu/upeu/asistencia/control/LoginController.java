package pe.edu.upeu.asistencia.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private CheckBox chkRecordar;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private Label lblMensaje;

    @Autowired
    private ApplicationContext context;

    @FXML
    public void iniciarSesion() {
        System.out.println("Intentando iniciar sesión...");
        
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        
        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor completa todos los campos");
            System.out.println("Campos vacíos");
            return;
        }
        
        if (validarCredenciales(usuario, password)) {
            System.out.println("Credenciales válidas - Usuario: " + usuario);
            lblMensaje.setText("¡Bienvenido " + usuario + "!");
            
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(this::abrirVentanaPrincipal);
                } catch (InterruptedException e) {
                    System.err.println("❌ Error en thread de login: " + e.getMessage());
                    Platform.runLater(() -> lblMensaje.setText("❌ Error cargando ventana principal"));
                }
            }).start();
        } else {
            lblMensaje.setText("Usuario o contraseña inválidos");
            System.out.println("Credenciales inválidas");
            txtPassword.clear();
        }
    }

    private boolean validarCredenciales(String usuario, String password) {
        // Demo credentials: admin / 123456
        return usuario.equals("quihue") && password.equals("123");
    }

    private void abrirVentanaPrincipal() {
        try {
            System.out.println("Abriendo ventana principal...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/maingui.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            
            Scene scene = new Scene(root, 1200, 700);
            stage.setScene(scene);
            stage.setTitle("Sistema de Gestión Empresarial - Panel Principal");
            stage.setMaximized(true);
            
            System.out.println("Ventana principal abierta exitosamente");
        } catch (Exception e) {
            System.out.println("Error abriendo ventana principal: " + e.getMessage());
            e.printStackTrace();
            lblMensaje.setText("Error cargando aplicación");
        }
    }
}
