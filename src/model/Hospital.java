package model;

import au.com.bytecode.opencsv.CSVReader;
import javafx.beans.property.SetProperty;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Hospital {

    private String name;
    private Map<String,Patient> mapPatients = new HashMap<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Collection<Patient> loadPacients(String file) {
        CSVReader csvreader = null;
        String[] line;
        try {
            csvreader = new CSVReader(new FileReader(file));
            csvreader.readNext(); //saltem primera línia de titols
            while ((line = csvreader.readNext()) != null) {
                //System.out.println(line[0] + ":" + line[4]);
                mapPatients.putIfAbsent(line[0],
                        new Patient( line[0],
                                line[1],
                                line[2],
                                LocalDate.parse(line[3],formatter),
                                Persona.Genere.valueOf(line[4]),
                                line[5],
                                Float.valueOf(line[6]),
                                Integer.valueOf(line[7]))
                );
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        //Retornem la llista de valors
        return mapPatients.values();
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }

    public Map<String,Patient> getMapPatients() {
        return this.mapPatients;
    }
}
