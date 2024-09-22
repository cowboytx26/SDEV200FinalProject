/*
Short Description: The purpose of the abstract measurement class is to create and maintain a
                   patient's health information.  There can be many types of measurements.  So, this
                   is an abstract class.
Author:   Brian Wiatrek
Date of Creation: 09/22/2024
Version 1.0: Initial Creation
 */
import java.time.LocalDateTime;

abstract class measurement {
    public String typeOfMeasurement;
    public LocalDateTime dateOfMeasurement;

    public String getTypeOfMeasurement() {
        return typeOfMeasurement;
    }

    public void setTypeOfMeasurement(String typeOfMeasurement) {
        this.typeOfMeasurement = typeOfMeasurement;
    }

    public abstract boolean saveMeasurement();

    public LocalDateTime getDateOfMeasurement() {
        return dateOfMeasurement;
    }

    public void setDateOfMeasurement(LocalDateTime dateOfMeasurement) {
        this.dateOfMeasurement = dateOfMeasurement;
    }
}
