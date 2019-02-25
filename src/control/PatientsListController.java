package control;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;

import java.util.Collection;


public class PatientsListController {


    /*
    *  TODO: Poner botones de editar o borrar. (Seleccion multiple)
    *  TODO: Al hacer doble click, abrir ventana con los datos del paciente. (Y lo puedes gestionar)
    *  TODO: Paginación.
    * */

    @FXML
    TableView tablePatients;


    private static final ObservableList<Patient> data = FXCollections.observableArrayList();


    /*
    * Method that loads all the patients in the tableView.
    * All times that we create a new Hospital, this method it's called.
    *
    * PS. We check if the data it's empty, because we can create a new hospital, even we are working with one.
    * So, if you do it, you first need to delete all the content of the last hospital you had been working.
    * */
    public void init(Collection<Patient> patients) {
        if(!data.isEmpty()) {
            data.removeAll();
        }
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada());
            data.add(p);
        });
    }


    /*
    * Method that runs every time that the patientList.fxml is loaded.
    * The main function of this method, is create the TableView, and set the data of
    * each patient on it.
    * */
    public void initialize() {
        TableColumn DNI = new TableColumn("DNI");
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
        TableColumn Genre = new TableColumn("Gènere");
        Genre.setPrefWidth(100);
        Genre.setResizable(false);
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
        Genre.setCellValueFactory(new PropertyValueFactory<Patient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Patient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Patient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("Alçada"));
        CB.setCellFactory(column -> new CheckBoxTableCell<>());
        CB.setCellValueFactory(new PropertyValueFactory<CheckBox,BooleanProperty>(""));

        tablePatients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada,CB);
        tablePatients.setItems(data);
    }

}
