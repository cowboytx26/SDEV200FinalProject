import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class diabetesApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Button patientBtn = new Button("Create New Patient");
        Button glucoseMeasurement = new Button("Enter Glucose Measurement");
        Button exitBtn = new Button("Exit");

        class patientHandler implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent e){
                Label patientLastName = new Label("Patient Last Name:");

                FlowPane patientPane = new FlowPane();
                patientPane.getChildren().addAll(patientLastName);

                Scene patientScene = new Scene(patientPane, 100, 100);

                Stage patientWindow = new Stage();
                patientWindow.setTitle("Enter Patient Information");
                patientWindow.setScene(patientScene);

                patientWindow.initModality(Modality.WINDOW_MODAL);
                patientWindow.initOwner(primaryStage);
                patientWindow.show();
            }
        }

        patientBtn.setOnAction(new patientHandler());

        FlowPane startingWindow = new FlowPane();
        startingWindow.setPadding(new Insets(11, 12, 13, 14));
        startingWindow.getChildren().addAll(patientBtn, glucoseMeasurement, exitBtn);

        Scene scene = new Scene(startingWindow, 200, 200);

        primaryStage.setTitle("Diabetes Monitoring App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
