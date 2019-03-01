package model;

import au.com.bytecode.opencsv.CSVReader;
import javafx.scene.control.CheckBox;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Hospital {

    private String name;
    private Map<String,Patient> mapPatients;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    CheckBox cb = null;

    public Collection<Patient> loadPacients(String file) {
        CSVReader csvreader = null;
        FileReader fr = null;
        String[] line;
        mapPatients  = new HashMap<>();
        try {
            fr = new FileReader(file);
            csvreader = new CSVReader(fr);
            csvreader.readNext(); //saltem primera línia de titols
            String[] date;
            while ((line = csvreader.readNext()) != null) {
                cb = new CheckBox();
                mapPatients.putIfAbsent(line[0],
                        new Patient( line[0],
                                line[1],
                                line[2],
                                LocalDate.parse(line[3],formatter),
                                Persona.Genere.valueOf(line[4]),
                                line[5],
                                Float.valueOf(line[6]),
                                Integer.valueOf(line[7]),cb
                                )
                );
            }
            csvreader.close();
            fr.close();
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
