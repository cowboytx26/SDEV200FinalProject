/*
Short Description: The purpose of this main class is to test the patient and glucoseMeasurement
                   classes.
Author:   Brian Wiatrek
Date of Creation: 09/22/2024
Version 1.0: Initial Creation
 */
public class Main {
    public static void main(String[] args) {

        //Test Patient Constructors
        patient testPatient1 = new patient("Brian", "Wiatrek");
        patient testPatient2 = new patient("Brian", "Wiatrek", "115 Testern Way", "Bubba", "Dr Smith");

        testPatient2.writePatientToFile();

        System.out.printf("%s\n", testPatient1.toString());
        System.out.printf("%s\n", testPatient2.toString());

        measurementList allMeasurements = new measurementList();

        //Test Glucose Measurement Constructors
        glucoseMeasurement testGM1 = new glucoseMeasurement(105);
        glucoseMeasurement testGM2 = new glucoseMeasurement(115, "glucose");

        allMeasurements.saveMeasurement(testGM1);
        allMeasurements.saveMeasurement(testGM2);

        System.out.printf("%s\n", testGM1.toString());
        System.out.printf("%s\n", testGM2.toString());
        System.out.printf("%s\n", allMeasurements.getMeasurement(0, "glucose"));
        System.out.printf("%d\n", allMeasurements.getMeasurementSize("glucose"));
        System.out.printf("%d\n", allMeasurements.getMeasurementSize("cholestoral"));

    }
}