/*
Short Description: The purpose of the patient class is to create and maintain a patient's information.
Author:   Brian Wiatrek
Date of Creation: 09/22/2024
Version 1.0: Initial Creation
 */

import java.io.*;

public class patient implements Serializable {

    private String firstName;
    private String lastName;

    public String patientAddress = "";
    public String emergencyContactName = "";
    public String endocrinologistName = "";

    public patient(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public patient(String firstName, String lastName, String patientAddress, String emergencyContactName,
                   String endocrinologistName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientAddress = patientAddress;
        this.emergencyContactName = emergencyContactName;
        this.endocrinologistName = endocrinologistName;
    }

    public patient() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEndocrinologistName() {
        return endocrinologistName;
    }

    public void setEndocrinologistName(String endocrinologistName) {
        this.endocrinologistName = endocrinologistName;
    }

    public void writePatientToFile() {
        try {
            FileOutputStream patientFile = new FileOutputStream("patient.bin");
            ObjectOutputStream patientObject = new ObjectOutputStream(patientFile);
            patientObject.writeObject(this);
            patientObject.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "patient{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patientAddress='" + patientAddress + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", endocrinologistName='" + endocrinologistName + '\'' +
                '}';
    }
}
