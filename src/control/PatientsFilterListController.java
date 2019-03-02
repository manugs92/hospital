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
    protected static List<Patient> patientsAL = new ArrayList<>();
    protected static ObservableList<Patient> defaultData = FXCollections.observableArrayList();


    /*
    * Method overriden from the extendes class PatiensListController.
    * It gonna be executed always at the first time that the program run.
    * It heredates all the jobs done by the extendes class, and add the next ones.
    * */
    @Override
    public void initialize() {
        super.initialize();
        tablePatients.setItems(data2);
        birthdate.setDisable(true);
    }


    /*
    * Function called "init" that clear all the data saved on data2, and put all the patiens passed from
    * the collectionsList.
    * It's very usefull when we overwrite the table from other controllers.
    * If we do it, we will refresh the table calling to the init method.
     */
    public static void init(Collection<Patient> patients) {
        data2.clear();
        defaultData.clear();
        patientsAL.clear();
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data2.add(p);
            patientsAL.add(p);
            defaultData.add(p);
        });
    }


    /*
    * Method that runs when you click in any place on the tablePatients.
    * */
    @Override
    public void tableClicked(MouseEvent event) {
        super.tableClicked(event);
    }


    /*
    * Method heredated and overriden to manage actions when we press any button from
    * management data (Edit, delete or add to the wait list).
    * */
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
                    PatientStatsController.init(data2);
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

    /*
    * Method made by Jordi, the teacher.
    * It allow to us to search in the tablePatients list.
    * */
    public void search() {
        List<Patient> pacients = patientsAL.stream()
                .filter(pacient -> pacient.getDNI().equals(dni.getText()))
                .collect(Collectors.toList());
        if(dni.getText().equals("")) {
            updateTable(patientsAL);
        }else updateTable(pacients);
    }

    /*
    * Method to set default data in the filter fields, and the patientList.
    * */
    public void setDefaultData() {
        name.setText("");
        lastname.setText("");
        man.setSelected(false);
        woman.setSelected(false);
        dni.setText("");
        phone.setText("");
        updateTable(defaultData);
    }

    /*
    * Method made by Jordi, the teacer, that allow to us to update the table.
    * */
    private void updateTable(List<Patient> pacients) {
        data2.clear();
        data2.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
        PatientStatsController.init(data2);
    }

    /*
    * Method made by Jordi, the teacher, that refresh the patientList table.
    * furthermore we call to the init method in PatienListStatsController, and PatientListController,
    * to update the list of these controllers.
    * */
     public void changeText(KeyEvent keyEvent) {
        if(name.getText().isEmpty() && lastname.getText().isEmpty() && phone.getText().isEmpty()) {
            patientsAL.clear();
            patientsAL.addAll(defaultData);
        }
        data2.clear();
        List<Patient> pacients = patientsAL.stream()
                .filter(pacient -> pacient.getNom().contains(name.getText()))
                .filter((pacient -> pacient.getCognoms().contains(lastname.getText())))
                .filter(pacient -> pacient.getTelefon().contains(phone.getText()))
                .collect(Collectors.toList());
        patientsAL.clear();
        data2.addAll(pacients);
        patientsAL.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
        PatientStatsController.init(data2);
    }

    /*
    * Method that set the gender value of the gender fields, and call
    * to the method filterByGender.
    *
    * FIXME: When we don't select any value, after have been selected one of them, the result of the filter isn't pushed by default.
    *
    * */
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

    /*
    * Method that filter the patients list by genders.
    * */
    public void filterByGender(Persona.Genere selectedGender){
        data2.clear();
        List<Patient> pacients;
        pacients = patientsAL.stream()
                .filter(patient -> patient.getGenere().equals(selectedGender))
                .collect(Collectors.toList());
        data2.addAll(pacients);
        tablePatients.setItems(data2);
        PatientsListController.init(data2);
        PatientStatsController.init(data2);
    }
}
