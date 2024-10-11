/*
Short Description:  This program will display a form to the user who can enter a patient or a glucose measurement.  If
                    the user chooses to enter a patient, they can choose to enter a new one and save it, or they can
                    load the existing patient, make changes, and save it.  Similarly, if they choose glucose
                    measurement, they can enter a new measurement and save it, or load existing measurements.  If they
                    load existing measurements, then they can scroll through the measurements.
Author:  Brian Wiatrek
Date:  September 21, 2024
*/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class diabetesApp extends Application {

    private patient diabetesPatient;
    private measurementList allMeasurements = new measurementList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Button patientBtn = new Button("Create New Patient");
        Button glucoseMeasurement = new Button("Enter Glucose Measurement");
        Button exitBtn = new Button("Exit");

        patientBtn.setPrefWidth(175);
        glucoseMeasurement.setPrefWidth(175);
        exitBtn.setPrefWidth(175);

        Image t1dimage = new Image("T1D.png");
        ImageView t1dimageview = new ImageView(t1dimage);
        DateTimeFormatter formatObject = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        //The patientHandler is responsible for displaying the window that allows patient information to be entered
        class patientHandler implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent e){
                Label patientLastName = new Label("Patient Last Name:");
                Label patientFirstName = new Label("Patient First Name:");
                Label patientAddress = new Label("Patient Address:");
                Label patientEmergencyContact = new Label("Emergency Contact:");
                Label patientEndocrinologist = new Label("Endocrinologist:");

                TextField tfpatientLastName = new TextField();
                TextField tfpatientFirstName = new TextField();
                TextField tfpatientAddress = new TextField();
                TextField tfpatientEmergencyContact = new TextField();
                TextField tfpatientEndocrinologist = new TextField();

                VBox labelsVbox = new VBox();
                VBox tfVbox = new VBox();

                labelsVbox.setAlignment(Pos.CENTER_LEFT);
                labelsVbox.setSpacing(10);
                tfVbox.setAlignment(Pos.CENTER_RIGHT);

                labelsVbox.getChildren().addAll(patientLastName, patientFirstName, patientAddress,
                        patientEmergencyContact, patientEndocrinologist);
                tfVbox.getChildren().addAll(tfpatientLastName, tfpatientFirstName, tfpatientAddress,
                        tfpatientEmergencyContact, tfpatientEndocrinologist);

                //The savePatientHandler is responsible for instantiating a patient class
                class savePatientHandler implements EventHandler<ActionEvent> {
                    @Override
                    public void handle(ActionEvent e){
                        diabetesPatient = new patient(tfpatientLastName.getText().trim(),
                                tfpatientFirstName.getText().trim(),
                                tfpatientAddress.getText().trim(),
                                tfpatientEmergencyContact.getText().trim(),
                                tfpatientEndocrinologist.getText().trim());
                        System.out.printf("%s", diabetesPatient.toString());
                        diabetesPatient.writePatientToFile();
                    }
                }

                //The loadPatientHandler is responsible for loading a patient from a binary file
                class loadPatientHandler implements EventHandler<ActionEvent> {
                    @Override
                    public void handle(ActionEvent e){
                        try {
                            FileInputStream patientFile = new FileInputStream("patient.bin");
                            ObjectInputStream patientObject = new ObjectInputStream(patientFile);
                            diabetesPatient = (patient) patientObject.readObject();
                        } catch (IOException | ClassNotFoundException exception) {
                            throw new RuntimeException(exception);
                        }
                        tfpatientLastName.setText(diabetesPatient.getLastName());
                        tfpatientFirstName.setText(diabetesPatient.getFirstName());
                        tfpatientAddress.setText(diabetesPatient.getPatientAddress());
                        tfpatientEmergencyContact.setText(diabetesPatient.getEmergencyContactName());
                        tfpatientEndocrinologist.setText(diabetesPatient.getEndocrinologistName());
                    }
                }

                Button saveBtn = new Button("Save");
                saveBtn.setPrefWidth(150);
                saveBtn.setOnAction(new savePatientHandler());

                Button loadBtn = new Button("Load Patient");
                loadBtn.setPrefWidth(150);
                loadBtn.setOnAction(new loadPatientHandler());

                HBox patientEntryPane = new HBox();
                patientEntryPane.getChildren().addAll(labelsVbox, tfVbox);
                FlowPane patientPane = new FlowPane();
                patientPane.getChildren().addAll(patientEntryPane, saveBtn, loadBtn);

                Scene patientScene = new Scene(patientPane, 300, 200);

                Stage patientWindow = new Stage();
                patientWindow.setTitle("Enter Patient Information");
                patientWindow.setScene(patientScene);

                patientWindow.initModality(Modality.WINDOW_MODAL);
                patientWindow.initOwner(primaryStage);
                patientWindow.show();
            }
        }

        //The purpose of the glucoseMeasurementHandler is to display the Glucose Measurement window
        class glucoseMeasurementHandler implements EventHandler<ActionEvent>{
            @Override
            public void handle(ActionEvent e) {
                Label glucoseValue = new Label("Glucose Value:");
                Label measurementDate = new Label("Measurement Date:");

                TextField tfGlucoseValue = new TextField();
                TextField tfMeasurementDate = new TextField();
                TextField tfSystemMessage = new TextField("System normal");
                tfMeasurementDate.setEditable(false);
                tfSystemMessage.setEditable(false);

                //The purpose of the following event handler is to ensure that an integer is entered in the Glucose
                //Value Textfield
                EventHandler<ActionEvent> tfGlucoseValueEvent = ev -> {
                    try {
                        Integer.parseInt(tfGlucoseValue.getText());
                    } catch (NumberFormatException tfException) {
                        tfGlucoseValue.setText("");
                    }
                };

                tfGlucoseValue.setOnAction(tfGlucoseValueEvent);

                //The following scroll bar is responsible for scrolling through multiple glucose measurements
                ScrollBar sbMeasurements = new ScrollBar();
                sbMeasurements.setMax(0);
                sbMeasurements.valueProperty().addListener(ov -> {
                    tfGlucoseValue.setText(String.valueOf(((glucoseMeasurement)
                            allMeasurements.getMeasurement((int) sbMeasurements.getValue(),
                                "glucose")).getGlucose()));
                    tfMeasurementDate.setText(String.valueOf(((glucoseMeasurement)
                            allMeasurements.getMeasurement((int) sbMeasurements.getValue(),
                                    "glucose")).getDateOfMeasurement().format(formatObject)));
                        //System.out.printf("%d %d", ((glucoseMeasurement) allMeasurements.getMeasurement((int) sbMeasurements.getValue(), "glucose")).getGlucose(), (int) sbMeasurements.getValue());
                });

                VBox vboxLabels = new VBox();
                VBox vboxTextFields = new VBox();

                vboxLabels.setSpacing(10);
                vboxLabels.setAlignment(Pos.CENTER_RIGHT);
                vboxTextFields.setAlignment(Pos.CENTER_LEFT);

                vboxLabels.getChildren().addAll(glucoseValue, measurementDate);
                vboxTextFields.getChildren().addAll(tfGlucoseValue, tfMeasurementDate);

                //The saveMeasurementHandler is responsible for saving the glucose measurements to a collection and then
                //to a file.
                class saveMeasurementHandler implements EventHandler<ActionEvent> {
                    @Override
                    public void handle(ActionEvent e){
                        try {
                            glucoseMeasurement inputGlucoseMeasurement = new glucoseMeasurement(parseInt(tfGlucoseValue.getText().trim()));
                            System.out.printf("%s", inputGlucoseMeasurement.toString());
                            allMeasurements.saveMeasurement(inputGlucoseMeasurement);
                            allMeasurements.writeMeasurementToFile();
                            tfSystemMessage.setText("Saved values");
                        } catch (NumberFormatException saveException) {
                            tfGlucoseValue.requestFocus();
                            tfSystemMessage.setText("Please enter a number in the Glucose Value Field");
                        }
                    }
                }

                //The loadMeasurementHandler is responsible for loading measurements from a file.
                class loadMeasurementHandler implements EventHandler<ActionEvent> {
                    @Override
                    public void handle(ActionEvent e) {
                        try {
                            FileInputStream measurementFile = new FileInputStream("measurement.bin");
                            ObjectInputStream measurementObject = new ObjectInputStream(measurementFile);
                            ArrayList<measurement> inputArrayList = (ArrayList) measurementObject.readObject();
                            allMeasurements = new measurementList(inputArrayList);
                        } catch (IOException | ClassNotFoundException exception) {
                            throw new RuntimeException(exception);
                        }
                        glucoseMeasurement outputGlucoseMeasurement = (glucoseMeasurement) allMeasurements.getMeasurement(0, "glucose");
                        tfGlucoseValue.setText(String.valueOf(outputGlucoseMeasurement.getGlucose()));
                        tfMeasurementDate.setText(outputGlucoseMeasurement.getDateOfMeasurement().format(formatObject));
                        sbMeasurements.setMax(allMeasurements.getMeasurementSize("glucose")-1);
                    }
                }

                Button saveBtn = new Button("Save");
                saveBtn.setOnAction(new saveMeasurementHandler());
                saveBtn.setPrefWidth(170);
                Button loadBtn = new Button("Load Glucose Measurements");
                loadBtn.setOnAction(new loadMeasurementHandler());

                HBox measurementHbox = new HBox();
                measurementHbox.getChildren().addAll(vboxLabels, vboxTextFields);

                HBox buttonHbox = new HBox();
                buttonHbox.getChildren().addAll(saveBtn, loadBtn);

                VBox measurementPane = new VBox(measurementHbox,sbMeasurements, buttonHbox, tfSystemMessage);

                Scene measurementScene = new Scene(measurementPane, 350, 150);

                Stage measurementWindow = new Stage();
                measurementWindow.setTitle("Enter Measurement Information");
                measurementWindow.setScene(measurementScene);

                measurementWindow.initModality(Modality.WINDOW_MODAL);
                measurementWindow.initOwner(primaryStage);
                measurementWindow.show();

            }
        }

        class exitHandler implements EventHandler<ActionEvent>{
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        }

        patientBtn.setOnAction(new patientHandler());
        glucoseMeasurement.setOnAction(new glucoseMeasurementHandler());
        exitBtn.setOnAction(new exitHandler());

        VBox buttonPane = new VBox();
        buttonPane.getChildren().addAll(patientBtn, glucoseMeasurement, exitBtn);

        FlowPane startingWindow = new FlowPane();
        startingWindow.setPadding(new Insets(11, 12, 13, 14));
        startingWindow.getChildren().addAll(t1dimageview, buttonPane);

        Scene scene = new Scene(startingWindow, 610, 350);

        primaryStage.setTitle("Diabetes Monitoring App");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
