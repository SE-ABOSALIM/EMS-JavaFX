package EMS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmployeeManagementSystem extends Application {
    
    double x = 0;
    double y = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
        
        root.setOnMousePressed((MouseEvent event) -> {
           x = event.getSceneX();
           y = event.getSceneY();
        });
        
        root.setOnMouseDragged((MouseEvent event) -> {
              stage.setX(event.getScreenX() - x);
              stage.setY(event.getScreenY() - y);
              stage.setOpacity(.8);
        });
        
        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
