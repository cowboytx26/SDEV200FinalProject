/*
Short Description: The purpose of the glucose measurement class is to create and maintain a
                   patient's glucose information.  This is just one concrete implementations of
                   the abstract measurement class.
Author:   Brian Wiatrek
Date of Creation: 09/22/2024
Version 1.0: Initial Creation
 */

import java.time.LocalDateTime;

public class glucoseMeasurement extends measurement {

    private int glucose;

    public glucoseMeasurement(int glucose) {
        this.glucose = glucose;
        this.typeOfMeasurement = "glucose";
        this.dateOfMeasurement = LocalDateTime.now();
    }

    public glucoseMeasurement(int glucose, String typeOfMeasurement) {
        this.glucose = glucose;
        this.typeOfMeasurement = typeOfMeasurement;
        this.dateOfMeasurement = LocalDateTime.now();
    }

    public glucoseMeasurement() {

    }

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    @Override
    public String toString() {
        return "glucoseMeasurement{" +
                "glucose=" + glucose +
                ", typeOfMeasurement='" + typeOfMeasurement + '\'' +
                ", dateOfMeasurement=" + dateOfMeasurement +
                '}';
    }
}
