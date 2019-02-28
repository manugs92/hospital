package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Patient;

import java.util.Collection;


public class PatientsListController {


    /*
    *  TODO: Poner botones de editar o borrar. (Seleccion multiple)
    *  TODO: Al hacer doble click, abrir ventana con los datos del paciente. (Y lo puedes gestionar)
    *  TODO: Amplicar tamaño de tabla y colocar scroll.
    * */

    @FXML
    TableView<Patient> tablePatients;


    public    static  ObservableList<Patient> data = FXCollections.observableArrayList();

    /*
    * Method that loads all the patients in the tableView.
    * All times that we create a new Hospital, this method it's called.
    * */
    public static void init(Collection<Patient> patients) {
        data.removeAll(data);
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data.add(p);
        });
    }


    /*
    * Method that runs every time that the patientList.fxml is loaded.
    * The main function of this method, is create the TableView, and set the data of
    * each patient on it.
    * */
    public void initialize() {
        TableColumn DNI = new TableColumn<Patient,String>("DNI");
        DNI.setPrefWidth(80);
        DNI.setResizable(false);
        TableColumn Nom = new TableColumn("Nom");
        Nom.setPrefWidth(80);
        Nom.setResizable(false);
        TableColumn Cognoms = new TableColumn("Cognoms");
        Cognoms.setPrefWidth(100);
        Cognoms.setResizable(false);
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        DataNaix.setPrefWidth(100);
        DataNaix.setResizable(false);
        TableColumn Gender = new TableColumn("Gènere");
        Gender.setPrefWidth(100);
        Gender.setResizable(false);
        TableColumn Telefon = new TableColumn("Telèfon");
        Telefon.setPrefWidth(100);
        Telefon.setResizable(false);
        TableColumn pes = new TableColumn("Pes");
        pes.setPrefWidth(70);
        pes.setResizable(false);
        TableColumn Alçada = new TableColumn("Alçada");
        Alçada.setPrefWidth(70);
        Alçada.setResizable(false);
        TableColumn CB = new TableColumn("Gestionar");
        CB.setPrefWidth(90);
        CB.setResizable(false);
        tablePatients.setEditable(true);

        DNI.setCellValueFactory(new PropertyValueFactory<Patient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Patient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Patient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Patient, String>("DataNaixament"));
        Gender.setCellValueFactory(new PropertyValueFactory<Patient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Patient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Patient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("Alçada"));
        CB.setCellValueFactory(new PropertyValueFactory<Patient, CheckBox>("cb"));
        tablePatients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Gender, Telefon, pes, Alçada,CB);
        tablePatients.setItems(data);
    }

    //TODO: Que pasa cuando haces doble click en la tabla.
    // (Preguntar si quieres editarlo o borrarlo o salir o añadirlo a lista de espera.
    public void tableClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && !tablePatients.getSelectionModel().isEmpty()){
            System.out.println(tablePatients.getSelectionModel().getSelectedItem().getNom());
        }
    }

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
                data.removeAll(dataToDelete);
                PatientsFilterListController.init(data);
                break;
            case "Editar":
                //TODO: Ventana de edición de datos.
                break;
            case "Añadir a lista de espera":
                //TODO: Añadir a lista de espera.
                break;
        }
    }
}
