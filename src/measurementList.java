/*
Short Description: The purpose of the measurementList class is to create and maintain a
                   collection of measurements.
Author:   Brian Wiatrek
Date of Creation: 10/01/2024
Version 1.0: Initial Creation
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class measurementList {
    public ArrayList<measurement> listOfMeasurements;

    public measurementList() {
        listOfMeasurements = new ArrayList<measurement>();
    }

    public measurementList(ArrayList inputArrayList) {
        listOfMeasurements = inputArrayList;
    }

    public void saveMeasurement(measurement M) {
        listOfMeasurements.add(M);
    }

    public int getMeasurementSize(String typeOfMeasurement) {
        int count = 0;
        for (measurement k : listOfMeasurements) {
            if (k.typeOfMeasurement.equals(typeOfMeasurement)) count++;
        }
        return count;
    }

    public measurement getMeasurement(int i, String typeOfMeasurement){
        int count = 0;
        for (measurement k : listOfMeasurements) {
            if ((k.typeOfMeasurement.equals(typeOfMeasurement)) & (count == i)) return k;
            if (k.typeOfMeasurement.equals(typeOfMeasurement)) {
                count++;
            }
        }
        return null;
    }

    public void writeMeasurementToFile() {
        try {
            FileOutputStream measurementFile = new FileOutputStream("measurement.bin");
            ObjectOutputStream measurementObject = new ObjectOutputStream(measurementFile);
            measurementObject.writeObject(listOfMeasurements);
            measurementObject.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
