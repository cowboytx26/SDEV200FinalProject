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

        System.out.printf("%s\n", testPatient1.toString());
        System.out.printf("%s\n", testPatient2.toString());

        //Test Glucose Measurement Constructors
        glucoseMeasurement testGM1 = new glucoseMeasurement(105);
        glucoseMeasurement testGM2 = new glucoseMeasurement(105, "Glucose");

        System.out.printf("%s\n", testGM1.toString());
        System.out.printf("%s\n", testGM2.toString());

    }
}