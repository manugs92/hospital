package control;


import alerts.Alerts;
import data.CSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Patient;
import model.Persona;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PatientsFilterListController extends PatientsListController {

    @FXML
    TableView<Patient> tablePatients;

    @FXML
    TextField name,lastname,dni,phone;

    @FXML
    CheckBox man,woman;

    @FXML
    DatePicker birthdate;

    protected static ObservableList<Patient> data2 = FXCollections.observableArrayList();
    private static List<Patient> patientsAL = new ArrayList<>();
    private static ObservableList<Patient> defaultData = FXCollections.observableArrayList();


    @Override
    public void initialize() {
        super.initialize();
        tablePatients.setItems(data2);
        defaultData.addAll(data2);
        birthdate.setDisable(true);
    }

    
    public static void init(Collection<Patient> patients) {
        data2.removeAll(data2);
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data2.add(p);
            patientsAL.add(p);
        });
    }


    @Override
    public void tableClicked(MouseEvent event) {
        super.tableClicked(event);
    }


    @Override
    public void manage(ActionEvent event) {
        ObservableList<Patient> dataToDelete = FXCollections.observableArrayList();
        tablePatients.getItems().forEach(patient -> {
            if(patient.getCb().isSelected()) {
                dataToDelete.add(patient);
            }
        });
        String[] boton = event.getSource().toString().split("'");
        switch(boton[1]) {
            case "Borrar":
                boolean confirmDelete = Alerts.confirmDelete();
                if(confirmDelete) {
                    data2.removeAll(dataToDelete);
                    PatientsListController.init(data2);
                    dataToDelete.forEach(patient -> {
                        String patientsLine = patient.getDNI()+","+patient.getNom()+","+patient.getCognoms()+","+patient.getDataNaixament()+","+patient.getGenere()+","+patient.getTelefon()+","+patient.getPes()+","+patient.getAlçada();
                        try {
                            CSV.delete(patientsLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    defaultData.removeAll(dataToDelete);
                    patientsAL.removeAll(dataToDelete);
                }
                break;
            case "Editar":
                //TODO: Método que nos cargue los pacientes seleccionados, abra una ventana y los puedas editar.
                Alerts.editAlert();

                break;
            case "Añadir a lista de espera":
                //TODO: Añadir a lista de espera.
                break;
        }
    }

    public void search() {
        List<Patient> pacients = patientsAL.stream()
                .filter(pacient -> pacient.getDNI().equals(dni.getText()))
                .collect(Collectors.toList());
        if(dni.getText().equals("")) {
            updateTable(patientsAL);
        }else updateTable(pacients);
    }

    public void setDefaultData() {
        updateTable(defaultData);
        name.setText("");
        lastname.setText("");
        man.setSelected(false);
        woman.setSelected(false);
        dni.setText("");
        phone.setText("");
    }

    private void updateTable(List<Patient> pacients) {
        data2.clear();
        data2.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
    }

     public void changeText(KeyEvent keyEvent) {
        data2.clear();
        List<Patient> pacients = patientsAL.stream()
                .filter(pacient -> pacient.getNom().contains(name.getText()))
                .filter((pacient -> pacient.getCognoms().contains(lastname.getText())))
                .filter(pacient -> pacient.getTelefon().contains(phone.getText()))
                .collect(Collectors.toList());
        data2.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
    }

    public void setGender(ActionEvent event){
        Persona.Genere selectedGender=null;
        if(event.getSource().equals(man)) {
            selectedGender=Patient.Genere.HOME;
            woman.setSelected(false);
        }else{
            selectedGender = Patient.Genere.DONA;
            man.setSelected(false);
        }
        filterByGender(selectedGender);
    }

    public void filterByGender(Persona.Genere selectedGender){
        data2.clear();
        List<Patient> pacients;
        pacients = patientsAL.stream()
                .filter(patient -> patient.getGenere().equals(selectedGender))
                .collect(Collectors.toList());
        data2.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
    }

}
