package pe.edu.upeu.asistencia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VentasApplication extends Application {

    private ConfigurableApplicationContext configurableApplicationContext;
    private Parent parent;

    public static void main(String[] args) {
        System.out.println("Starting Enterprise Management System...");
        System.out.println("Loading Spring Boot and JavaFX...");
        
        launch(args);
    }

    @Override
    public void init() throws Exception {
        System.out.println("Configuring Spring Boot...");
        
        SpringApplicationBuilder builder = new SpringApplicationBuilder(VentasApplication.class);
        builder.application().setWebApplicationType(WebApplicationType.NONE);
        configurableApplicationContext = builder.run(getParameters().getRaw().toArray(new String[0]));

        System.out.println("Loading login interface...");
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setControllerFactory(configurableApplicationContext::getBean);
        parent = fxmlLoader.load();
        
        System.out.println("System initialized successfully");
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("🖥Showing login window...");
        
        stage.setScene(new Scene(parent, 600, 700));
        stage.setTitle("Enterprise Management System - Login");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
        
        System.out.println("Login screen ready for use!");
    }
}
